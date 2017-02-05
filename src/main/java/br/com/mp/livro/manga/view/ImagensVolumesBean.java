package br.com.mp.livro.manga.view;

import java.io.Serializable;
import java.util.Date;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.mp.livro.manga.model.Volume;
import br.com.mp.livro.manga.repository.ImagensVolumes;

@Named
@ViewScoped
public class ImagensVolumesBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ImagensVolumes imagensVolumes;		
	
	public Date ultimaModificaoFoto(Volume volume) { 
		try {			
			Date dataHoraFoto = imagensVolumes.todosPorVolume(volume.getId()).getUltimaModificacao();
			return dataHoraFoto == null ? obterDataHoraAtual() : dataHoraFoto;
		} catch(Exception e) {
			return null;
		}
	}
	
	private Date obterDataHoraAtual() {
		return new Date();
	}
}