package br.com.mp.livro.hq.view;

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

import br.com.mp.livro.hq.model.Edicao;
import br.com.mp.livro.hq.model.HQ;
import br.com.mp.livro.hq.model.ImagemEdicoes;
import br.com.mp.livro.hq.repository.Edicoes;
import br.com.mp.livro.hq.repository.HQs;
import br.com.mp.livro.hq.repository.ImagensEdicoes;
import br.com.mp.livro.model.Tipo;
import br.com.mp.util.RegraNegocioException;

@Named
@ViewScoped
public class ImagensEdicoesBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ImagensEdicoes imagensEdicaos;
	
	@Inject
	private HQs hqs;
	@Inject
	private Edicoes edicoes;
	
	private ImagemEdicoes imagemEdicoes;
	private HQ hqSelecionado;
	private Edicao volumeSelecionado;
	
	private Tipo tipoSelecionado;

	private List<HQ> todosHQs;
	private List<Edicao> todosEdicoes;
	
	@PostConstruct
	private void init() {
		this.hqSelecionado = new HQ();
		this.volumeSelecionado = new Edicao();
		
		this.tipoSelecionado = Tipo.FISICO;
		
		this.imagemEdicoes = new ImagemEdicoes();
		
		this.todosHQs = new ArrayList<HQ>();
		this.todosHQs = this.hqs.todosPorTipo(this.tipoSelecionado);
		this.todosEdicoes = new ArrayList<Edicao>();
	}
	
	public void onRowSelect(SelectEvent event) {
        HQ hq = (HQ) event.getObject();
        if(hq != null)
        	this.todosEdicoes = this.edicoes.todosPorHQ(hq);        	
    }
	
	public void mundarTipoHQ() {
		this.todosHQs = this.hqs.todosPorTipo(this.tipoSelecionado);
		this.hqSelecionado = new HQ();
		this.todosEdicoes = new ArrayList<Edicao>();
	}
	
	public void salvar(FileUploadEvent event) throws IOException {
		InputStream is = event.getFile().getInputstream();
		byte[] imagemBytes = ByteStreams.toByteArray(is);
		
		Long codigoEdicao = this.volumeSelecionado.getId(); 
		
		boolean seExistir = this.imagensEdicaos.isExistirPorEdicao(codigoEdicao);
		
		if(!seExistir){
			
 			this.imagemEdicoes.setEdicao(volumeSelecionado);
			this.imagemEdicoes.setUltimaModificacao(obterDataHoraAtual());
			
			this.imagemEdicoes.setImagem(imagemBytes);
			try {
				this.imagensEdicaos.salvar(this.imagemEdicoes);
				Messages.addFlashGlobalInfo("A capa foi salvo com sucesso!");
			} catch (RegraNegocioException e) {
				e.printStackTrace();
				Messages.addGlobalError("Não foi possivel salvar capa!");
			}
		} else {
			this.imagemEdicoes = imagensEdicaos.porEdicao(codigoEdicao);
			
			this.imagemEdicoes.setUltimaModificacao(obterDataHoraAtual());
			this.imagemEdicoes.setImagem(imagemBytes);
			try {
				this.imagensEdicaos.salvar(this.imagemEdicoes);
				Messages.addFlashGlobalInfo("A capa foi atualizada com sucesso!");
			} catch (RegraNegocioException e) {
				e.printStackTrace();
				Messages.addGlobalError("Não foi possivel atualizar a nova capa!");
			}
			
			this.imagemEdicoes = new ImagemEdicoes();
		}
		RequestContext.getCurrentInstance().update("formEdicao");
		RequestContext.getCurrentInstance().execute("PF('dlgImagensEdicoes').hide();");
		
	}
	
	public Date ultimaModificaoFoto(Edicao edicao) { 
		try {			
			Date dataHoraFoto = imagensEdicaos.porEdicao(edicao.getId()).getUltimaModificacao();
			return dataHoraFoto == null ? obterDataHoraAtual() : dataHoraFoto;
		} catch(Exception e) {
			return null;
		}
	}
	
	private Date obterDataHoraAtual() {
		return new Date();
	}
	
	public HQ getHqSelecionado() {
		return hqSelecionado;
	}
	
	public void setHqSelecionado(HQ hqSelecionado) {
		this.hqSelecionado = hqSelecionado;
	}
	
	public Edicao getEdicaoSelecionado() {
		return volumeSelecionado;
	}
	
	public void setEdicaoSelecionado(Edicao volumeSelecionado) {
		this.volumeSelecionado = volumeSelecionado;
	}

	public Tipo getTipoSelecionado() {
		return tipoSelecionado;
	}
	
	public void setTipoSelecionado(Tipo tipoSelecionado) {
		this.tipoSelecionado = tipoSelecionado;
	}
	
	public List<HQ> getTodosHQs() {
		return todosHQs;
	}
	
	public List<Edicao> getTodosEdicaos() {
		return todosEdicoes;
	}
	
	public Tipo[] getTodosTipos() {
		return Tipo.values();
	}
}
