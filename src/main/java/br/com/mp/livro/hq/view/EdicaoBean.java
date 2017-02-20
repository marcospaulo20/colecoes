package br.com.mp.livro.hq.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Messages;

import br.com.mp.livro.hq.model.Edicao;
import br.com.mp.livro.hq.model.HQ;
import br.com.mp.livro.hq.repository.Edicoes;
import br.com.mp.livro.model.Tipo;
import br.com.mp.model.Pessoa;
import br.com.mp.model.TipoPessoa;
import br.com.mp.repository.Pessoas;
import br.com.mp.util.RegraNegocioException;

@Named(value = "edicaoBean")
@ViewScoped
public class EdicaoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Edicoes edicoes;
	@Inject
	private Pessoas pessoas;

	private Edicao edicao;
	private Edicao edicaoSelecionado;
	private HQ hq;

	private List<Edicao> todosEdicoes;
	private List<Pessoa> todosArtistas;
	private List<Pessoa> todosCapistas;
	private List<Pessoa> todosEscritores;
	private List<Pessoa> todosEditores;

	@PostConstruct
	public void init() {
		this.edicao = new Edicao();
		this.todosEdicoes = new ArrayList<Edicao>();
		this.carregarListaEdicaos();
	}

	public void prepararCadastro() {
		this.edicao = new Edicao();
		this.edicao.setHq(this.hq);
		carregarListas();
	}

	public void carregarListas() {
		this.todosArtistas = pessoas.todasPorTipo(TipoPessoa.ARTISTA);
		this.todosCapistas = pessoas.todasPorTipo(TipoPessoa.CAPISTA);
		this.todosEscritores = pessoas.todasPorTipo(TipoPessoa.ESCRITOR);
		this.todosEditores = pessoas.todasPorTipo(TipoPessoa.EDITOR);
	}

	private void carregarListaEdicaos() {
		if (this.isNuloHQ())
			this.todosEdicoes = edicoes.todosPorHQ(this.hq);
	}

	private boolean isNuloHQ() {
		if (this.hq != null)
			return true;
		return false;
	}

	public boolean isHQFisico() {
		if (this.hq != null)
			return this.hq.getTipo().equals(Tipo.FISICO);
		return false;
	}

	public void salvar() {
		try {
			this.edicoes.salvar(this.edicao);

			this.carregarListaEdicaos();
			this.edicao = new Edicao();
			this.edicao.setHq(this.hq);

			Messages.addFlashGlobalInfo("O edicao foi salvo com sucesso!");
		} catch (RegraNegocioException e) {
			e.printStackTrace();
			Messages.addGlobalError("Não foi possivel salvar edicao!");
		}
	}

	public void excluir() {
		try {
			this.edicoes.remover(this.edicaoSelecionado);
			this.carregarListaEdicaos();
			Messages.addFlashGlobalInfo("Edicao excluido com sucesso!");
		} catch (RegraNegocioException e) {
			e.printStackTrace();
			Messages.addGlobalError("Ocorreu um error ao excluir edicao!");
		}
	}

	public void atualizarEdicaoTem(Edicao edicao, boolean selecionado) {
		if (edicao != null) {
			edicao.setTem(selecionado);
			try {
				this.edicoes.salvar(edicao);

				Messages.addFlashGlobalInfo("O edicao foi atualizado com sucesso!");
			} catch (RegraNegocioException e) {
				e.printStackTrace();
				Messages.addGlobalError("Não foi possivel atualizar edicao!");
			}
		}
	}

	public void atualizarEdicaoLeu(Edicao edicao, boolean selecionado) {
		if (edicao != null) {
			edicao.setLeu(selecionado);
			try {
				this.edicoes.salvar(edicao);

				Messages.addFlashGlobalInfo("O edicao foi atualizado com sucesso!");
			} catch (RegraNegocioException e) {
				e.printStackTrace();
				Messages.addGlobalError("Não foi possivel atualizar edicao!");
			}
		}
	}

	
	public Edicao getEdicao() {
		return edicao;
	}

	public void setEdicao(Edicao edicao) {
		this.edicao = edicao;
	}

	public Edicao getEdicaoSelecionado() {
		return edicaoSelecionado;
	}

	public void setEdicaoSelecionado(Edicao edicaoSelecionado) {
		this.edicaoSelecionado = edicaoSelecionado;
	}

	public HQ getHq() {
		return hq;
	}
	
	public void setHq(HQ hq) {
		this.hq = hq;
	}

	public List<Edicao> getTodosEdicoes() {
		return todosEdicoes;
	}

	public List<Pessoa> getTodosArtistas() {
		return todosArtistas;
	}

	public List<Pessoa> getTodosCapistas() {
		return todosCapistas;
	}

	public List<Pessoa> getTodosEditores() {
		return todosEditores;
	}

	public List<Pessoa> getTodosEscritores() {
		return todosEscritores;
	}
}
