package br.com.mp.livro.hq.repository;

import java.util.List;

import br.com.mp.livro.hq.model.Edicao;
import br.com.mp.livro.hq.model.HQ;
import br.com.mp.util.RegraNegocioException;

public interface Edicoes {

	List<Edicao> todos();
	List<Edicao> todosPorHQ(HQ hq);

	Edicao porId(Long id);
	
	Edicao salvar(Edicao edicao) throws RegraNegocioException;
	void remover(Edicao edicao) throws RegraNegocioException;
	
}
