package br.com.mp.livro.manga.repository;

import java.util.List;

import br.com.mp.livro.manga.model.Capitulo;
import br.com.mp.livro.manga.model.Manga;
import br.com.mp.livro.manga.model.Volume;
import br.com.mp.util.RegraNegocioException;

public interface Capitulos {

	List<Capitulo> todos();
	List<Capitulo> todosPorVolume(Volume volume);
	List<Capitulo> todosCapitulosPorManga(Manga manga);
	Capitulo porId(Long id);
	
	Capitulo salvar(Capitulo capitulo) throws RegraNegocioException;
	void remover(Capitulo capitulo) throws RegraNegocioException;
}
