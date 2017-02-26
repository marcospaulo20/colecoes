package br.com.mp.tv.serie.repository;

import java.util.List;

import br.com.mp.tv.serie.model.Episodio;
import br.com.mp.tv.serie.model.Serie;
import br.com.mp.tv.serie.model.Temporada;
import br.com.mp.util.RegraNegocioException;

public interface Episodios {

	List<Episodio> todos();

	List<Episodio> todosPorTemporada(Temporada temporada);

	List<Episodio> todosEpisodiosPorSerie(Serie serie);

	Episodio porId(Long id);

	Episodio salvar(Episodio episodio) throws RegraNegocioException;

	void remover(Episodio episodio) throws RegraNegocioException;

}
