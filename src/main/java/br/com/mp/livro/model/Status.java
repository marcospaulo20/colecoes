package br.com.mp.livro.model;

public enum Status {

	EDICAO_UNICA("Edi��o �nica"),
	EM_CIRCULACAO("Em circula��o"),
	SERIE_COMPLETA("S�rie completa"),
	TITULO_ENCERRADO("T�tulo encerrado");
	
	private String descricao;
	
	Status(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
}
