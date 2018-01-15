package com.br.minhasdespesas.bll;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.br.minhasdespesas.model.Despesa;

public class GerenciaDespesas {

	//lista de despesas a serem gerenciadas
	private List<Despesa> totalDespesas = new ArrayList<Despesa>();
	
	//id das despesas.
	int id = 0;
	
	/**
	 * Adiciona uma despesa com autoincremento do ID.
	 * @param despesa
	 */
	public void adicionaDespesa(Despesa despesa) {
		despesa.setId(id);
		if (despesa != null) {
			totalDespesas.add(despesa);
		}
		id++;
	}
	
	/**
	 * Remove uma despesa atraves do id fornecido.
	 * @param despesa
	 */
	public Despesa removeDespesa(int id) {
		Despesa despesa = obtemDespesa(id);
		totalDespesas.remove(totalDespesas.indexOf(despesa));
		return despesa;
	}

	/**
	 * retorna uma despesa atraves do id fornecido.
	 * @param id
	 * @return
	 */
	public Despesa obtemDespesa(int id) {
		return totalDespesas.stream().filter(x -> x.getId() == id).collect(Collectors.toList()).get(0);
	}
	
	/**
	 * Atualiza uma despesa através do id e dos dados fornecidos.
	 * @param despesa
	 * @return
	 */
	public Despesa atualizaDespesa(Despesa despesa){
		removeDespesa(despesa.getId());
		totalDespesas.add(despesa);
		return despesa;
	}
	
	/**
	 * retorna a lista de despesas ordenadas por id.
	 * @return
	 */
	public List<Despesa> obtemDespesas(){
		Collections.sort(totalDespesas, new Comparator<Despesa>(){ 
			@Override
		    public int compare(Despesa e1, Despesa e2) {
		        if(e1.getId() < e2.getId()){
		            return 1;
		        } else {
		            return -1;
		        }
		    }
		});
		return totalDespesas;
	}
	
	/**
	 * retorna a soma das despesas na lista.
	 * @return
	 */
	public Double retornaTotalDespesas(){
		Double total = 0D;	
		for(Despesa desp: totalDespesas){
			total += desp.getValor();
		}
		return total;
	}
	
}
