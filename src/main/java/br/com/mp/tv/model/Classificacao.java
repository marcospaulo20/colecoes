package br.com.mp.tv.model;

public enum Classificacao {

	LIVRE("Livre"),
	DEZ("10 anos"),
	DOZE("12 anos"),
	CATORZE("14 anos"),
	DEZESSEIS("16 anos"),
	DEZOITO("18 anos");
	
	private String descricao;
	
	Classificacao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
