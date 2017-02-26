package br.com.mp.tv.model;

public enum TipoCategoria {

	ANIME("Animes"),
	CARTOON("Cartoons"),	
	SERIE("Series"),
	TOKUSATSU("Tokusatsus");
	
	private String descricao;
	
	TipoCategoria(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
