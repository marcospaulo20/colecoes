package br.com.mp.tv.serie.repository;

import java.util.List;

import br.com.mp.tv.serie.model.Serie;
import br.com.mp.tv.serie.model.Temporada;
import br.com.mp.util.RegraNegocioException;

public interface Temporadas {

	List<Temporada> todos();
	List<Temporada> todosPorSerie(Serie serie);

	Temporada porId(Long id);
	
	Temporada salvar(Temporada temporada) throws RegraNegocioException;
	public void salvarTemporadaEpisodios(Temporada temporada) throws RegraNegocioException;
	void remover(Temporada temporada) throws RegraNegocioException;
	
}
