package br.com.mp.model;

public enum TipoPessoa {

	AUTOR("Autor"),
	ATOR("Ator"),
	ATRIZ("Atriz"),
	ARTISTA("Artista"),
	CAPISTA("Capista"),
	DIRETOR("Diretor"),
	EDITOR("Editor"),
	ESCRITOR("Escritor"),
	ILUSTRADOR("Ilustrador");
	
	private String descricao;
	
	TipoPessoa(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
