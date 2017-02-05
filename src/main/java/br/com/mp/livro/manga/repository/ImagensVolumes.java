package br.com.mp.livro.manga.repository;

import br.com.mp.livro.manga.model.ImagemVolumes;
import br.com.mp.util.RegraNegocioException;

public interface ImagensVolumes {

	ImagemVolumes todosPorVolume(Long id);
	ImagemVolumes porId(Long id);
	
	ImagemVolumes salvar(ImagemVolumes imagemVolumes) throws RegraNegocioException;
}
