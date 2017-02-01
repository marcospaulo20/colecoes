package br.com.mp.livro.manga.repository;

import java.util.List;

import br.com.mp.livro.manga.model.Manga;
import br.com.mp.livro.model.Tipo;
import br.com.mp.util.RegraNegocioException;

public interface Mangas {

	List<Manga> todos();
	List<Manga> todosPorTipo(Tipo tipo);
	Manga porId(Long id);
	
	Manga salvar(Manga manga) throws RegraNegocioException;
	void remover(Manga manga) throws RegraNegocioException;
}
