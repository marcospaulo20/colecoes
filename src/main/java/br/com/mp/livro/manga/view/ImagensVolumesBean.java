package br.com.mp.livro.manga.view;

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
import org.primefaces.event.SelectEvent;

import com.google.common.io.ByteStreams;

import br.com.mp.livro.manga.model.ImagemVolumes;
import br.com.mp.livro.manga.model.Manga;
import br.com.mp.livro.manga.model.Volume;
import br.com.mp.livro.manga.repository.ImagensVolumes;
import br.com.mp.livro.manga.repository.Mangas;
import br.com.mp.livro.manga.repository.Volumes;
import br.com.mp.livro.model.Tipo;
import br.com.mp.util.RegraNegocioException;

@Named
@ViewScoped
public class ImagensVolumesBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ImagensVolumes imagensVolumes;
	
	@Inject
	private Mangas mangas;
	@Inject
	private Volumes volumes;
	
	private ImagemVolumes imagemVolumes;
	private Manga mangaSelecionado;
	private Volume volumeSelecionado;
	
	private Tipo tipoSelecionado;

	private List<Manga> todosMangas;
	private List<Volume> todosVolumes;
	
	@PostConstruct
	private void init() {
		this.mangaSelecionado = new Manga();
		this.volumeSelecionado = new Volume();
		
		this.tipoSelecionado = Tipo.FISICO;
		
		this.imagemVolumes = new ImagemVolumes();
		
		this.todosMangas = new ArrayList<Manga>();
		this.todosMangas = this.mangas.todosPorTipo(this.tipoSelecionado);
		this.todosVolumes = new ArrayList<Volume>();
	}
	
	public void onRowSelect(SelectEvent event) {
        Manga manga = (Manga) event.getObject();
        if(manga != null)
        	this.todosVolumes = this.volumes.todosPorManga(manga);        	
    }
	
	public void mundarTipoManga() {
		this.todosMangas = this.mangas.todosPorTipo(this.tipoSelecionado);
		this.mangaSelecionado = new Manga();
		this.todosVolumes = new ArrayList<Volume>();
	}
	
	public void salvar(FileUploadEvent event) throws IOException {
		InputStream is = event.getFile().getInputstream();
		byte[] imagemBytes = ByteStreams.toByteArray(is);
		
		Long codigoVolume = this.volumeSelecionado.getId(); 
		
		boolean seExistir = this.imagensVolumes.isExistirPorVolume(codigoVolume);
		
		if(!seExistir){
			
 			this.imagemVolumes.setVolume(volumeSelecionado);
			this.imagemVolumes.setUltimaModificacao(obterDataHoraAtual());
			
			this.imagemVolumes.setImagem(imagemBytes);
			try {
				this.imagensVolumes.salvar(this.imagemVolumes);
				Messages.addFlashGlobalInfo("A capa foi salvo com sucesso!");
			} catch (RegraNegocioException e) {
				e.printStackTrace();
				Messages.addGlobalError("Não foi possivel salvar capa!");
			}
		} else {
			this.imagemVolumes = imagensVolumes.porVolume(codigoVolume);
			
			this.imagemVolumes.setUltimaModificacao(obterDataHoraAtual());
			this.imagemVolumes.setImagem(imagemBytes);
			try {
				this.imagensVolumes.salvar(this.imagemVolumes);
				Messages.addFlashGlobalInfo("A capa foi atualizada com sucesso!");
			} catch (RegraNegocioException e) {
				e.printStackTrace();
				Messages.addGlobalError("Não foi possivel atualizar a nova capa!");
			}
			
			this.imagemVolumes = new ImagemVolumes();
		}
		RequestContext.getCurrentInstance().update("formVolume");
		RequestContext.getCurrentInstance().execute("PF('dlgImagensVolumes').hide();");
		
	}
	
	public Date ultimaModificaoFoto(Volume volume) { 
		try {			
			Date dataHoraFoto = imagensVolumes.porVolume(volume.getId()).getUltimaModificacao();
			return dataHoraFoto == null ? obterDataHoraAtual() : dataHoraFoto;
		} catch(Exception e) {
			return null;
		}
	}
	
	private Date obterDataHoraAtual() {
		return new Date();
	}
	
	public Manga getMangaSelecionado() {
		return mangaSelecionado;
	}
	
	public void setMangaSelecionado(Manga mangaSelecionado) {
		this.mangaSelecionado = mangaSelecionado;
	}
	
	public Volume getVolumeSelecionado() {
		return volumeSelecionado;
	}
	
	public void setVolumeSelecionado(Volume volumeSelecionado) {
		this.volumeSelecionado = volumeSelecionado;
	}

	public Tipo getTipoSelecionado() {
		return tipoSelecionado;
	}
	
	public void setTipoSelecionado(Tipo tipoSelecionado) {
		this.tipoSelecionado = tipoSelecionado;
	}
	
	public List<Manga> getTodosMangas() {
		return todosMangas;
	}
	
	public List<Volume> getTodosVolumes() {
		return todosVolumes;
	}
	
	public Tipo[] getTodosTipos() {
		return Tipo.values();
	}
}
