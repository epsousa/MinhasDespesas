package com.br.minhasdespesas.start;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import com.br.minhasdespesas.bll.GerenciaDespesas;
import com.br.minhasdespesas.enums.TipoDespesa;
import com.br.minhasdespesas.model.Despesa;
import com.br.minhasdespesas.model.Responsavel;

public class StartDespesas {
	// scanner para ler entrada do usuário
	static Scanner sc = new Scanner(System.in);
	// classe responsavel por gerencia as despesas
	static GerenciaDespesas calc = new GerenciaDespesas();
	// classe despesa para gerenciar os dados de despesa.
	static Despesa desp = new Despesa();
	// classe Reponsavel para gerenciar os dados do responsavel.
	static Responsavel resp = new Responsavel();

	// classe principal.
	public static void main(String[] args) {

		retornaLinha();
		System.out.println("Seja bem vindo para o gerenciador de despesas:");
		retornaLinha();

		System.out.println("Por favor, insira seu nome:");
		String nome = sc.next();
		resp.setNome(nome);
		int idade = -1;
		System.out.println("Por favor, Digite sua idade:");
		while (idade == -1) {
			try {
				idade = sc.nextInt();
				if (idade <= 0) {
					System.out.println("Digite uma idade valida.");
					idade = -1;
				} else {
					resp.setIdade(idade);
				}
			} catch (Exception ex) {
				System.out.println("Digite uma idade valida.");
				sc.next();
				idade = -1;
			}
		}
		int resp = 1;

		// enquanto a resposta enviada pela função menu() for diferente de zero
		// o sistema fica em funcionamento.
		while (resp != 0) {

			resp = menu();

			switch (resp) {
			case 1:
				adicionarDespesa();
				break;
			case 2:
				alterarDespesa();
				break;
			case 3:
				deletarDespesa();
				break;
			case 4:
				selecionarDespesa();
				break;
			case 0:
				System.out.println("Obrigado por usar nosso sistema! :-)");
				salvarArquivoDeDespesas();
				System.out.println("Seu relatorio foi gerado com sucesso!");
				break;
			}

		}

	}

	/**
	 * Grava as despesas inseridas pelo usuário em um arquivo txt com o nome
	 * gerado atraves da data e hora de execução na mesma pasta de execução do
	 * sistema.
	 */
	@SuppressWarnings("resource")
	private static void salvarArquivoDeDespesas() {

		if (calc.obtemDespesas().isEmpty()) {
			return;
		}

		FileWriter writer = null;
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss_SSS");
			writer = new FileWriter("MinhasDespesas" + LocalDateTime.now().format(formatter) + ".txt");
		} catch (IOException e) {
			e.printStackTrace();
			return;
		} finally {
			if(writer != null){
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}



		try {
			writer.write("Despesas geradas por: " + resp.getNome() + ", cuja a idade é: " + resp.getIdade() + "\n");
			writer.write("-------------------------------------------------------------------------------------\n");
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		for (Despesa desp : calc.obtemDespesas()) {
			try {
				writer.write("Despesa: " + desp.getNome() + " Valor: R$ " + desp.getValor() + " Tipo da despesa: "
						+ desp.getTipoDespesa().getValue() + "\n");
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}

		try {
			writer.write("-------------------------------------------------------------------------------------\n");
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		try {
			writer.write("Valor total das despesas Fixas: R$ " + calc.retornaDespesasPorTipo(TipoDespesa.FIXAS) + "\n");
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			writer.write("-------------------------------------------------------------------------------------\n");
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		try {
			writer.write("Valor total das despesas Variaveis: R$ " + calc.retornaDespesasPorTipo(TipoDespesa.VARIAVEIS)
					+ ".\n");
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			writer.write("-------------------------------------------------------------------------------------\n");
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		try {
			writer.write("Valor total de todas as despesas: R$ " + calc.retornaTotalDespesas() + "\n");
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}

	/**
	 * Retorna o valor inteiro inserido pelo usuário sobre a decisão de
	 * atividade.
	 * 
	 * @return
	 */
	public static int menu() {
		int decisao = -1;
		System.out.println("O que deseja executar agora?");
		retornaLinha();

		System.out.println("1 - Adicionar nova despesa.");
		System.out.println("2 - Alterar uma despesa.");
		System.out.println("3 - Deletar uma despesa.");
		System.out.println("4 - Selecionar uma despesa.");
		System.out.println("0 - Sair.");
		while (decisao == -1) {
			try {
				decisao = sc.nextInt();
				if (decisao < 0 || decisao > 4) {
					decisao = -1;
					System.out.println("Insira um valor entre 0 e 4 inclusive");
				}
			} catch (Exception ex) {
				System.out.println("Insira um valor entre 0 e 4 inclusive");
				sc.next();
			}
		}
		return decisao;
	}

	/**
	 * Permite o usuário adicionar uma despesa.
	 */
	public static void adicionarDespesa() {

		String dsDesp = "";
		System.out.println("Por favor, adicione um nome de uma despesa:");
		while (dsDesp.equals("")) {
			dsDesp = sc.next();
			if (calc.validaSeNomeExisteNaLista(dsDesp)) {
				System.out.println("Despesa já cadastrada com o nome " + dsDesp
						+ ", Por favor, digite uma despesa ainda não cadastrada.");
				dsDesp = "";
			}
		}
		desp.setNome(dsDesp);
		retornaLinha();
		while (desp.getValor() == null) {
			System.out.println("Por favor, adicione o valor da despesa:");
			try {
				double val = sc.nextDouble();
				desp.setValor(val);
			} catch (Exception ex) {
				System.out.println("Adicione um valor numérico.");
				sc.next();
			}
		}

		while (desp.getTipoDespesa() == null) {
			System.out.println("Por favor, adicione o tipo da despesa, 1 para fixo, 2 para variável.:");
			int i = sc.nextInt();
			try {
				if (i == 1) {
					desp.setTipoDespesa(TipoDespesa.FIXAS);
				} else if (i == 2) {
					desp.setTipoDespesa(TipoDespesa.VARIAVEIS);
				} else {
					System.out.println("Por favor, insira 1 para fixo, 2 para variável.:");
				}
			} catch (Exception ex) {
				System.out.println("Selecione 1 para fixo ou 2 para variavel");
				sc.next();
			}
		}

		calc.adicionaDespesa(desp);
		retornaLinha();
		desp = new Despesa();
		System.out.println("Sua lista de despesas: ");
		listaDespesas(-1);
	}

	/**
	 * Permite o usuário alterar uma despesa.
	 */
	private static void alterarDespesa() {
		// verifica se as despesas estao vazias, se estiver, exibe mensagem e
		// encerra a execução da função.
		if (calc.obtemDespesas().isEmpty()) {
			System.out.println("Não há despesas para serem alteradas.");
			return;
		}

		System.out.println("Insira o id da despesa que deseja alterar conforme abaixo:");
		listaDespesas(-1);
		Integer v = null;

		// faz com que o usuário digite um valor inteiro dentre os existentes na
		// listagem de despesas.
		while (v == null) {
			try {
				v = sc.nextInt();
			} catch (Exception ex) {
				System.out.println("Digite um valor inteiro dentre os id's mostrados abaixo");
				listaDespesas(-1);
			}
		}
		desp.setId(v);
		System.out.println("Deseja alterar esse produto? " + calc.obtemDespesa(v) + " 1 - Sim, 0 - Não");
		try {
			if (sc.nextInt() == 1) {
				System.out.println("Digite um novo nome para essa despesa");
				String dsDesp = "";
				System.out.println("Por favor, adicione um nome de uma despesa:");
				while (dsDesp.equals("")) {
					dsDesp = sc.next();
					if (calc.validaSeNomeExisteNaLista(dsDesp)) {
						System.out.println("Despesa já cadastrada com o nome " + dsDesp
								+ ", Por favor, digite uma despesa ainda não cadastrada.");
						dsDesp = "";
					}
				}
				desp.setNome(dsDesp);
				System.out.println("Digite um novo valor para essa despesa");
				Double valor = null;
				while (valor == null) {
					try {
						valor = sc.nextDouble();
					} catch (Exception ex) {
						System.out.println("Digite um valor numérico.");
					}
				}
				desp.setValor(valor);

				while (desp.getTipoDespesa() == null) {
					System.out.println("Por favor, adicione o tipo da despesa, 1 para fixo, 2 para variável.:");
					int i = sc.nextInt();
					try {
						if (i == 1) {
							desp.setTipoDespesa(TipoDespesa.FIXAS);
						} else if (i == 2) {
							desp.setTipoDespesa(TipoDespesa.VARIAVEIS);
						} else {
							System.out.println("Por favor, insira 1 para fixo, 2 para variável.:");
						}
					} catch (Exception ex) {
						System.out.println("Selecione 1 para fixo ou 2 para variavel");
						sc.next();
					}
				}

				calc.atualizaDespesa(desp);
				desp = new Despesa();
				listaDespesas(-1);
			}
		} catch (Exception ex) {

		}
	}

	/**
	 * Permite deletar uma despesa através do id passado pelo usuário.
	 */
	private static void deletarDespesa() {
		// verifica se as despesas estao vazias, se estiver, exibe mensagem e
		// encerra a execução da função.
		if (calc.obtemDespesas().isEmpty()) {
			System.out.println("Não há despesas para serem deletadas.");
			return;
		}

		System.out.println("Insira o id da despesa que deseja deletar conforme abaixo:");
		listaDespesas(-1);
		Integer v = null;

		// faz com que o usuário digite um valor inteiro.
		while (v == null) {
			try {
				v = sc.nextInt();
				if (calc.obtemDespesa(v) == null) {
					v = null;
					System.out.println("Digite um valor inteiro dentre os id's mostrados abaixo");
					listaDespesas(-1);
					sc.next();
				}
			} catch (Exception ex) {
				System.out.println("Digite um valor inteiro dentre os id's mostrados abaixo");
				listaDespesas(-1);
				sc.next();
			}
		}
		System.out.println("Deseja deletar esse produto? " + calc.obtemDespesa(v) + " 1 - Sim, 0 - Não");
		// confirma se o usuário deseja realmente deletar o produto selecionado.
		try {
			if (sc.nextInt() == 1) {
				calc.removeDespesa(v);
				listaDespesas(-1);
			}
		} catch (Exception ex) {

		}
	}

	/**
	 * Permite fazer busca por id dentre a lista de despesas.
	 */
	private static void selecionarDespesa() {
		// verifica se as despesas estao vazias, se estiver, exibe mensagem e
		// encerra a execução da função.
		if (calc.obtemDespesas().isEmpty()) {
			System.out.println("Não há despesas para serem listadas.");
			return;
		}

		System.out.println("Insira o id de alguma despesa que deseja selecionar:");
		Integer v = null;

		// faz com que o usuário digite um valor inteiro.
		while (v == null) {
			try {
				v = sc.nextInt();
			} catch (Exception ex) {
				System.out.println("Digite um valor inteiro.");
			}
		}

		// busca pelo id da despesa.
		try {
			listaDespesas(v);
		} catch (Exception ex) {
			System.out.println("Id não encontrado");
		}
	}

	/**
	 * Se o valor de v for igual a -1, lista todos as despesas se não, lista
	 * apenas o correspondente ao valor de V
	 * 
	 * @param v
	 */
	private static void listaDespesas(int v) {
		retornaLinha();
		if (v == -1) {
			for (Despesa despesa : calc.obtemDespesas()) {
				System.out.println(despesa);
			}
		} else {
			System.out.println(calc.obtemDespesa(v));
		}
		retornaLinha();
	}

	/**
	 * Escreve no console o texto:
	 * "-------------------------------------------------------------------------------------"
	 */
	public static void retornaLinha() {
		// Escreve no console o texto abaixo.
		System.out.println("-------------------------------------------------------------------------------------");
	}

}
