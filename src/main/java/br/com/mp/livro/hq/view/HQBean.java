package br.com.mp.livro.hq.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Messages;

import br.com.mp.livro.hq.model.Edicao;
import br.com.mp.livro.hq.model.HQ;
import br.com.mp.livro.hq.repository.HQs;
import br.com.mp.livro.model.Categoria;
import br.com.mp.livro.model.Editora;
import br.com.mp.livro.model.Status;
import br.com.mp.livro.model.Tipo;
import br.com.mp.livro.repository.Editoras;
import br.com.mp.util.RegraNegocioException;

@Named(value="hqBean")
@ViewScoped
public class HQBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private HQs hqs;
	@Inject
	private Editoras editoras;

	private HQ hq;
	private HQ hqSelecionado;

	private Tipo tipoSelecionado;

	private boolean isFisico;

	private List<HQ> todosHQs;
	private List<Editora> todasEditoras;

	@PostConstruct
	private void init() {
		this.hq = new HQ();
		this.todosHQs = new ArrayList<HQ>();
		this.tipoSelecionado = Tipo.VIRTUAL;
		this.todosHQs = this.hqs.todosPorTipo(tipoSelecionado);
		this.isFisico = false;
	}

	public void prepararCadastro() {
		this.hq = new HQ();
		this.carregarListas();
		this.isFisico = false;
	}

	public void carregarListas() {
		this.todasEditoras = this.editoras.todas();
	}

	public void mundarTipoHQ() {
		this.todosHQs = this.hqs.todosPorTipo(this.tipoSelecionado);
	}

	public void tipoModificadao(AjaxBehaviorEvent event) {
		if (this.hq.getTipo().equals(Tipo.FISICO)) {
			isFisico = true;
		} else {
			isFisico = false;
		}
	}

	public void salvar() {
		try {
			this.hqs.salvar(this.hq);

			this.todosHQs = this.hqs.todosPorTipo(this.tipoSelecionado);
			this.hq = new HQ();

			Messages.addFlashGlobalInfo("O titulo foi salvo com sucesso!");
		} catch (RegraNegocioException e) {
			e.printStackTrace();
			Messages.addGlobalError("Não foi possivel salvar titulo!");
		}
	}

	public void excluir() {
		try {
			this.hqs.remover(this.hqSelecionado);
			this.todosHQs = this.hqs.todosPorTipo(this.tipoSelecionado);
			Messages.addFlashGlobalInfo("Titulo excluido com sucesso!");
		} catch (RegraNegocioException e) {
			e.printStackTrace();
			Messages.addGlobalError("Ocorreu um error ao excluir titulo!");
		}
	}

	public HQ getHq() {
		return hq;
	}

	public void setHq(HQ hq) {
		this.hq = hq;
	}

	public HQ getHqSelecionado() {
		return hqSelecionado;
	}

	public void setHqSelecionado(HQ hqSelecionado) {
		this.hqSelecionado = hqSelecionado;
	}

	public Tipo getTipoSelecionado() {
		return tipoSelecionado;
	}

	public void setTipoSelecionado(Tipo tipoSelecionado) {
		this.tipoSelecionado = tipoSelecionado;
	}

	public boolean isFisico() {
		return isFisico;
	}

	public List<HQ> getTodosHQs() {
		return todosHQs;
	}

	public List<Editora> getTodasEditoras() {
		return todasEditoras;
	}
	
	public Tipo[] getTodosTipos() {
		return Tipo.values();
	}
	
	public Status[] getTodosStatus() {
		return Status.values();
	}
	
	public Categoria[] getTodasCategorias() {
		return Categoria.values();
	}
	
	public String quantidadeTotalEdicoesTem(List<Edicao> listaEdicoes) {
		long totalTem = 0, total = 0;
		
		if(listaEdicoes.size() > 0) {
			total = listaEdicoes.stream().count();
			for(Edicao edicao : listaEdicoes)
				if(edicao.isTem() == true)
					totalTem += 1;
		}		
		
		return totalTem + " / " + total;
	}
}
