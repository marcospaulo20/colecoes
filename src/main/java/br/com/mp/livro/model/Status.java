package br.com.mp.livro.model;

public enum Status {

	EDICAO_UNICA("Edição única"),
	EM_CIRCULACAO("Em circulação"),
	SERIE_COMPLETA("Série completa"),
	TITULO_ENCERRADO("Título encerrado");
	
	private String descricao;
	
	Status(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
}
