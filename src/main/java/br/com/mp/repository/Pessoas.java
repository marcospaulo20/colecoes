package br.com.mp.repository;

import java.util.List;

import br.com.mp.model.Pessoa;
import br.com.mp.model.TipoPessoa;
import br.com.mp.util.RegraNegocioException;

public interface Pessoas {

	List<Pessoa> todas();
	List<Pessoa> todasPorTipo(TipoPessoa tipoPessoa);
	Pessoa porId(Long id);
	
	Pessoa salvar(Pessoa pessoa) throws RegraNegocioException;
	void remover(Pessoa pessoa) throws RegraNegocioException;
	List<Pessoa> todosPorTipo(TipoPessoa tipoSelecionado);
}
