package br.com.mp.livro.model;

public enum Tipo {

	FISICO("Fisico"),
	VIRTUAL("Virtual");
	
	private String descricao;
	
	Tipo(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
}
