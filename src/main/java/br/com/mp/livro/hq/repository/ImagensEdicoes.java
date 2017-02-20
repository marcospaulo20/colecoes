package br.com.mp.livro.hq.repository;

import br.com.mp.livro.hq.model.ImagemEdicoes;
import br.com.mp.util.RegraNegocioException;

public interface ImagensEdicoes {

	ImagemEdicoes porEdicao(Long id);
	ImagemEdicoes porId(Long id);
	
	boolean isExistirPorEdicao(Long id);
	
	ImagemEdicoes salvar(ImagemEdicoes imagemEdicoes) throws RegraNegocioException;
	
}
