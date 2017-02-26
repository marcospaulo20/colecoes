package br.com.mp.tv.serie.repository;

import java.util.List;

import br.com.mp.tv.model.TipoCategoria;
import br.com.mp.tv.serie.model.Serie;
import br.com.mp.util.RegraNegocioException;

public interface Series {

	List<Serie> todos();
	List<Serie> todosPorCategoria(TipoCategoria tipoCategoria);
	Serie porId(Long id);
	
	Serie salvar(Serie serie) throws RegraNegocioException;
	void remover(Serie serie) throws RegraNegocioException;
}
