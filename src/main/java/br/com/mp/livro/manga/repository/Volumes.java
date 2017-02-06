package br.com.mp.livro.manga.repository;

import java.util.List;

import br.com.mp.livro.manga.model.Manga;
import br.com.mp.livro.manga.model.Volume;
import br.com.mp.util.RegraNegocioException;

public interface Volumes {

	List<Volume> todos();
	List<Volume> todosPorManga(Manga manga);

	Volume porId(Long id);
	
	Volume salvar(Volume volume) throws RegraNegocioException;
	void remover(Volume volume) throws RegraNegocioException;
}
