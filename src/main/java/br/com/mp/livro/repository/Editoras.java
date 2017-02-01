package br.com.mp.livro.repository;

import java.util.List;

import br.com.mp.livro.model.Editora;
import br.com.mp.util.RegraNegocioException;

public interface Editoras {

	List<Editora> todas();
	Editora porId(Long id);
	
	Editora salvar(Editora editora) throws RegraNegocioException;
	void remover(Editora editora) throws RegraNegocioException;
}
