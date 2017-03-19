package br.com.mp.tv.model;

public enum TipoIdioma {

	DUBLADO("Dublado"),
	DUALAUDIO("Dual Audio"),
	LEGENDADO("Legendado");
	
	private String descricao;
	
	TipoIdioma(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
}
