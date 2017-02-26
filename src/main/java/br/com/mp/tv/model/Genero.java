package br.com.mp.tv.model;

public enum Genero {

	ANIMACAO("Anima��o"),
	AVENTURA("Aventura"),
	ACAO("A��o"),
	COMEDIA("Com�dia"),
	CRIME("Crime"),
	DRAMA("Drama"),
	FANTASIA("Fantasia"),
	FAROESTE("Faroeste"),
	FICCAO_CIENTIFICA("Fic��o Cientifica"),
	GUERRA("Guerra"),
	MISTERIO("Mist�rio"),
	NACIONAL("Nacional"),
	NOVELAS("Novelas"),
	ROMANCE("Romance"),
	SUSPENSE("Suspense"),
	TERROR("Terror");
	
	private String descricao;
	
	Genero(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
