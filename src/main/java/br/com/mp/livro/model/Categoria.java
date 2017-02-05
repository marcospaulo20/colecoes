package br.com.mp.livro.model;

public enum Categoria {
	
	ALBUM_LUXO("Álbum de Luxo"),
	EDICAO_ENCADERNADA("Edição Encadernada"),
	EDICAO_ESPECIAL("Edição Especial"),
	GRAPHIC_NOVEL("Graphic Novel"),
	MINISSERIE("Minissérie"),
	REVISTA_PERIODICA("Revista Periódica");	
	
	private String descricao;
	
	Categoria(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
}