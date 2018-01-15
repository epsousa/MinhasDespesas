package com.br.minhasdespesas.model;

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
		return "Despesa: id=" + id + ", nome=" + nome + ", valor=" + valor;
	}

}
