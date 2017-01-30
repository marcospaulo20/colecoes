package br.com.mp.livro.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.mp.livro.model.Editora;
import br.com.mp.livro.repository.Editoras;
import br.com.mp.util.RegraNegocioException;

public class GestaoEditoras implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Editoras editoras;
	
	public void salvar(Editora editora) throws RegraNegocioException {
		if (existeEditoraSemelhante(editora)) {
			throw new RegraNegocioException("Já existe uma editora igual a este.");
		}
		
		this.editoras.salvar(editora);
	}
	
	private boolean existeEditoraSemelhante(Editora editora) {
		Editora editoraSemelhante = this.editoras.comDadosIguais(editora);
		
		return editoraSemelhante != null && !editoraSemelhante.equals(editora);
	}
}
