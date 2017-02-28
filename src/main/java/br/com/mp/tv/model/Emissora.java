package br.com.mp.tv.model;

public enum Emissora {

	ABC("ABC"),	
	ADULT_SWIM("Adult Swin"),
	AMC("AMC"),
	BBC("BBC"),
	BET("BET"),
	CBS("CBS"),
	COMEDY_CENTRAL("Comedy Central"),
	CW("The CW"),
	EL_REY_NETWORK("El Rey Network"),
	FOX("Fox"),
	FXX("Fxx"),
	HBO("HBO"),
	IFC("IFC"),
	LIFETIME("Lifetime"),
	MTV("MTV"),
	NETFLIX("Netflix"),
	NBC("NBC"),
	SHOWTIME("Showtime"),
	SYFY("Syfy"),
	TBS("TBS"),
	TNT("TNT"),
	USA_NETWORK("USA Network"),
	YOMIURI_TV("Yomiuri TV");
	
	private String descricao;
	
	Emissora(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
