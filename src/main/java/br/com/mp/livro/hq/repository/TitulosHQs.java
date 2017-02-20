package br.com.mp.livro.hq.repository;

import java.util.List;

import br.com.mp.livro.hq.model.TituloHQ;
import br.com.mp.util.RegraNegocioException;

public interface TitulosHQs {

	List<TituloHQ> raizes();
	TituloHQ todosHQsPorTituloHQ(TituloHQ tituloHQ);
	
	TituloHQ porId(Long id);
	
	TituloHQ salvar(TituloHQ tituloHQ) throws RegraNegocioException;
	void remover(TituloHQ tituloHQ) throws RegraNegocioException;
}