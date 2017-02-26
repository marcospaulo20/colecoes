package br.com.mp.tv.serie.view;

import java.io.IOException;

import javax.inject.Inject;

import org.omnifaces.cdi.GraphicImageBean;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Utils;

import br.com.mp.tv.serie.repository.ImagensSeries;

@GraphicImageBean
public class ImagensSerieGraphicBean {

	@Inject
	private ImagensSeries imagensSeries;
	
	public byte[] get(Long id) throws IOException {
		try {
			return imagensSeries.porSerie(id).getImagem();
		} catch(Exception e) {
			return Utils.toByteArray(Faces.getResourceAsStream("/resources/imagens/image-default.jpg"));
		}
	}
}