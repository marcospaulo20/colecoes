package br.com.mp.livro.manga.view;

import java.io.IOException;

import javax.inject.Inject;

import org.omnifaces.cdi.GraphicImageBean;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Utils;

import br.com.mp.livro.manga.repository.ImagensVolumes;

@GraphicImageBean
public class ImagensVolumesGraphicBean {

	@Inject
	private ImagensVolumes imagensVolumes;
	
	public byte[] get(Long id) throws IOException {
		try {
			return imagensVolumes.porVolume(id).getImagem();
		} catch(Exception e) {
			return Utils.toByteArray(Faces.getResourceAsStream("/resources/imagens/image-default.jpg"));
		}
	}	
}