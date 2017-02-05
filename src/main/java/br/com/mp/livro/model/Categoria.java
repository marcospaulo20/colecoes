package br.com.mp.livro.model;

public enum Categoria {
	
	ALBUM_LUXO("�lbum de Luxo"),
	EDICAO_ENCADERNADA("Edi��o Encadernada"),
	EDICAO_ESPECIAL("Edi��o Especial"),
	GRAPHIC_NOVEL("Graphic Novel"),
	MINISSERIE("Miniss�rie"),
	REVISTA_PERIODICA("Revista Peri�dica");	
	
	private String descricao;
	
	Categoria(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
}