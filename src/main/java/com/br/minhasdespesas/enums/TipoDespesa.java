package com.br.minhasdespesas.enums;

public enum TipoDespesa {

	FIXAS(1L, "Fixa"),
	VARIAVEIS(2L, "Variavel");
	
	public Long key;
	public String value;
	
	TipoDespesa(Long key, String value){
		this.key = key;
		this.value = value;
	}

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
