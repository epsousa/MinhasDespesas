package com.br.minhasdespesas.model;

import com.br.minhasdespesas.enums.TipoDespesa;

/**
 * Classe para representar despesa.
 * @author evair
 *
 */
public class Despesa {

	/**
	 * Identificador unico da despesa.
	 */
	private int id;

	/**
	 * Nome da despesa.
	 */
	private String nome;
	
	/**
	 * valor da despesa.
	 */
	private Double valor;
	
	/**
	 * Tipo da despesa cadastrada.
	 */
	private TipoDespesa tipoDespesa;		
	
	public TipoDespesa getTipoDespesa() {
		return tipoDespesa;
	}

	public void setTipoDespesa(TipoDespesa tipoDespesa) {
		this.tipoDespesa = tipoDespesa;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		return "Despesa= id:" + id + ", nome:" + nome + ", valor:" + valor + ", Tipo de despesa:" + tipoDespesa.getValue();
	}

	

	

}
