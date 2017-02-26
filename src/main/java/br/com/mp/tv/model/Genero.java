package br.com.mp.tv.model;

public enum Genero {

	ANIMACAO("Animação"),
	AVENTURA("Aventura"),
	ACAO("Ação"),
	COMEDIA("Comédia"),
	CRIME("Crime"),
	DRAMA("Drama"),
	FANTASIA("Fantasia"),
	FAROESTE("Faroeste"),
	FICCAO_CIENTIFICA("Ficção Cientifica"),
	GUERRA("Guerra"),
	MISTERIO("Mistério"),
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
