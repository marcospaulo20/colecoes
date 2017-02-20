package br.com.mp.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Messages;

import br.com.mp.model.Pessoa;
import br.com.mp.model.TipoPessoa;
import br.com.mp.repository.Pessoas;
import br.com.mp.util.RegraNegocioException;

@Named
@ViewScoped
public class PessoaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Pessoas pessoas;

	private Pessoa pessoa;
	private Pessoa pessoaSelecionada;

	private TipoPessoa tipoSelecionado;
	
	List<Pessoa> todasPessoas;

	@PostConstruct
	private void init() {
		this.pessoa = new Pessoa();
		this.todasPessoas = new ArrayList<Pessoa>();
		this.todasPessoas = this.pessoas.todas();
	}

	public void salvar() {
		try {
			this.pessoas.salvar(this.pessoa);
			
			this.todasPessoas = this.pessoas.todas();
			this.pessoa = new Pessoa();

			Messages.addFlashGlobalInfo("A pessoa foi salvo com sucesso!");
		} catch (RegraNegocioException e) {
			e.printStackTrace();
			Messages.addGlobalError("Não foi possivel salvar pessoa!");
		}
	}

	public void excluir() {
		try {
			this.pessoas.remover(this.pessoaSelecionada);
			this.todasPessoas = this.pessoas.todas();
			Messages.addFlashGlobalInfo("Pessoa excluida com sucesso!");
		} catch (RegraNegocioException e) {
			e.printStackTrace();
			Messages.addGlobalError("Ocorreu um error ao excluir pessoa!");
		}
	}
	
	public void mundarTipoManga() {
		this.todasPessoas = this.pessoas.todosPorTipo(this.tipoSelecionado);
	}
	
	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Pessoa getPessoaSelecionada() {
		return pessoaSelecionada;
	}

	public void setPessoaSelecionada(Pessoa pessoaSelecionada) {
		this.pessoaSelecionada = pessoaSelecionada;
	}

	public TipoPessoa getTipoSelecionado() {
		return tipoSelecionado;
	}
	
	public void setTipoSelecionado(TipoPessoa tipoSelecionado) {
		this.tipoSelecionado = tipoSelecionado;
	}
	
	public List<Pessoa> getTodasPessoas() {
		return todasPessoas;
	}

	public TipoPessoa[] getTiposPessoas() {
		return TipoPessoa.values();
	}

}
