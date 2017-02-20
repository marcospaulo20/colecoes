package br.com.mp.livro.hq.view;

import java.io.IOException;

import javax.inject.Inject;

import org.omnifaces.cdi.GraphicImageBean;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Utils;

import br.com.mp.livro.hq.repository.ImagensEdicoes;

@GraphicImageBean
public class ImagensEdicoesGraphicBean {

	@Inject
	private ImagensEdicoes imagensEdicoes;
	
	public byte[] get(Long id) throws IOException {
		try {
			return imagensEdicoes.porEdicao(id).getImagem();
		} catch(Exception e) {
			return Utils.toByteArray(Faces.getResourceAsStream("/resources/imagens/image-default.jpg"));
		}
	}	
}