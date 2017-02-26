package br.com.mp.tv.serie.repository;

import br.com.mp.tv.serie.model.ImagemSerie;
import br.com.mp.util.RegraNegocioException;

public interface ImagensSeries {

	ImagemSerie porSerie(Long id);
	ImagemSerie porId(Long id);
	
	boolean isExistirPorSerie(Long id);
	
	ImagemSerie salvar(ImagemSerie imagemSerie) throws RegraNegocioException;
	
}
