package com.br.minhasdespesas.bll;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.br.minhasdespesas.enums.TipoDespesa;
import com.br.minhasdespesas.model.Despesa;

public class GerenciaDespesas {

	// lista de despesas a serem gerenciadas
	private List<Despesa> totalDespesas = new ArrayList<Despesa>();

	// id das despesas.
	int id = 0;

	/**
	 * Adiciona uma despesa com autoincremento do ID.
	 * 
	 * @param despesa
	 */
	public void adicionaDespesa(Despesa despesa) {
		despesa.setId(id);
		totalDespesas.add(despesa);
		id++;
	}

	/**
	 * Remove uma despesa atraves do id fornecido.
	 * 
	 * @param id
	 */
	public Despesa removeDespesa(int id) {
		Despesa despesa = obtemDespesa(id);
		totalDespesas.remove(totalDespesas.indexOf(despesa));
		return despesa;
	}

	/**
	 * Retorna uma despesa atraves do id fornecido.
	 * 
	 * @param id
	 * @return
	 */
	public Despesa obtemDespesa(int id) {
		List<Despesa> despesas = totalDespesas.stream().filter(x -> x.getId() == id)
					.collect(Collectors.toList());
		if (!despesas.isEmpty()) {
			return despesas.get(0);
		} else {
			return null;
		}
	}

	/**
	 * Retorna uma lista de despesas filtradas por um tipo de despesa.
	 * 
	 * @param tpDespesa
	 * @return
	 */
	public List<Despesa> listaDespesasPorTipo(TipoDespesa tpDespesa) {
		return totalDespesas.stream().filter(x -> x.getTipoDespesa() == tpDespesa)
					.collect(Collectors.toList());
	}

	/**
	 * Atualiza uma despesa através do id e dos dados fornecidos.
	 * 
	 * @param despesa
	 * @return
	 */
	public Despesa atualizaDespesa(Despesa despesa) {
		removeDespesa(despesa.getId());
		totalDespesas.add(despesa);
		return despesa;
	}

	/**
	 * Retorna a lista de despesas ordenadas por id.
	 * 
	 * @return
	 */
	public List<Despesa> obtemDespesas() {
		totalDespesas.sort((e1, e2) -> {
            if (e1.getId() < e2.getId()) {
                return 1;
            } else {
                return -1;
            }
        });
		return totalDespesas;
	}

	/**
	 * Retorna o valor total por um tipo de despesa.
	 * 
	 * @param tipoDespesa
	 * @return
	 */
	public Double retornaDespesasPorTipo(TipoDespesa tipoDespesa) {
		Double total = 0D;
		for (Despesa desp : totalDespesas) {
			if (desp.getTipoDespesa() == tipoDespesa) {
				total += desp.getValor();
			}
		}
		return total;
	}

	/**
	 * Retorna a soma das despesas na lista.
	 * 
	 * @return
	 */
	public Double retornaTotalDespesas() {
		Double total = 0D;
		for (Despesa desp : totalDespesas) {
			total += desp.getValor();
		}
		return total;
	}

	/**
	 * Busca por uma string entre os nomes das despesas dentro da lista.
	 * @param nome
	 * @return
	 */
	public boolean validaSeNomeExisteNaLista(String nome) {
		return !this.totalDespesas.stream().filter(x -> x.getNome().equalsIgnoreCase(nome))
						.collect(Collectors.toList())
						.isEmpty();
	}

}
