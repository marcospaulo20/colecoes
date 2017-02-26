package br.com.mp.tv.serie.view;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;

import com.google.common.io.ByteStreams;

import br.com.mp.tv.model.TipoCategoria;
import br.com.mp.tv.serie.model.ImagemSerie;
import br.com.mp.tv.serie.model.Serie;
import br.com.mp.tv.serie.repository.ImagensSeries;
import br.com.mp.tv.serie.repository.Series;
import br.com.mp.util.RegraNegocioException;

@Named
@ViewScoped
public class ImagensSerieBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ImagensSeries imagensSeries;

	@Inject
	private Series series;

	private ImagemSerie imagemSerie;
	private Serie serieSelecionado;

	private TipoCategoria tipoCategoriaSelecionado;

	private List<Serie> todasSeries;

	@PostConstruct
	private void init() {
		this.serieSelecionado = new Serie();

		this.tipoCategoriaSelecionado = TipoCategoria.SERIE;

		this.imagemSerie = new ImagemSerie();

		this.todasSeries = new ArrayList<Serie>();
		this.todasSeries = this.series.todosPorCategoria(tipoCategoriaSelecionado);
	}

	public void mundarTipoSerie() {
		this.todasSeries = this.series.todosPorCategoria(this.tipoCategoriaSelecionado);
	}

	public void salvar(FileUploadEvent event) throws IOException {
		InputStream is = event.getFile().getInputstream();
		byte[] imagemBytes = ByteStreams.toByteArray(is);

		Long codigoSerie = this.serieSelecionado.getId();

		boolean seExistir = this.imagensSeries.isExistirPorSerie(codigoSerie);

		if (!seExistir) {

			this.imagemSerie.setSerie(serieSelecionado);
			this.imagemSerie.setUltimaModificacao(obterDataHoraAtual());

			this.imagemSerie.setImagem(imagemBytes);
			try {
				this.imagensSeries.salvar(this.imagemSerie);
				Messages.addFlashGlobalInfo("A capa foi salvo com sucesso!");
			} catch (RegraNegocioException e) {
				e.printStackTrace();
				Messages.addGlobalError("Não foi possivel salvar capa!");
			}
		} else {
			this.imagemSerie = imagensSeries.porSerie(codigoSerie);

			this.imagemSerie.setUltimaModificacao(obterDataHoraAtual());
			this.imagemSerie.setImagem(imagemBytes);
			try {
				this.imagensSeries.salvar(this.imagemSerie);
				Messages.addFlashGlobalInfo("A capa foi atualizada com sucesso!");
			} catch (RegraNegocioException e) {
				e.printStackTrace();
				Messages.addGlobalError("Não foi possivel atualizar a nova capa!");
			}

			this.imagemSerie = new ImagemSerie();
		}
		RequestContext.getCurrentInstance().update("formSerie");
		RequestContext.getCurrentInstance().execute("PF('dlgImagensSeries').hide();");

	}

	public Date ultimaModificaoFoto(Serie serie) {
		try {
			Date dataHoraFoto = imagensSeries.porSerie(serie.getId()).getUltimaModificacao();
			return dataHoraFoto == null ? obterDataHoraAtual() : dataHoraFoto;
		} catch (Exception e) {
			return null;
		}
	}

	private Date obterDataHoraAtual() {
		return new Date();
	}

	public ImagemSerie getImagemSerie() {
		return imagemSerie;
	}

	public void setImagemSerie(ImagemSerie imagemSerie) {
		this.imagemSerie = imagemSerie;
	}

	public Serie getSerieSelecionado() {
		return serieSelecionado;
	}

	public void setSerieSelecionado(Serie serieSelecionado) {
		this.serieSelecionado = serieSelecionado;
	}

	public TipoCategoria getTipoCategoriaSelecionado() {
		return tipoCategoriaSelecionado;
	}

	public void setTipoCategoriaSelecionado(TipoCategoria tipoCategoriaSelecionado) {
		this.tipoCategoriaSelecionado = tipoCategoriaSelecionado;
	}

	public List<Serie> getTodasSeries() {
		return todasSeries;
	}

	public TipoCategoria[] getTodosTipoCategorias() {
		return TipoCategoria.values();
	}

}