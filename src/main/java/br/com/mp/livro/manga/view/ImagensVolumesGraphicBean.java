package br.com.mp.livro.manga.view;

import javax.inject.Inject;

import org.omnifaces.cdi.GraphicImageBean;

import br.com.mp.livro.manga.repository.ImagensVolumes;

@GraphicImageBean
public class ImagensVolumesGraphicBean {

	@Inject
	private ImagensVolumes imagensVolumes;
	
	public byte[] get(Long id) {
		try {
			return imagensVolumes.todosPorVolume(id).getImagem();
		} catch(Exception e) {
			return null;
		}
	}	
}