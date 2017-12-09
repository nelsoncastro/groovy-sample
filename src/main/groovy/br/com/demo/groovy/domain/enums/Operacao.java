package br.com.demo.groovy.domain.enums;

public enum Operacao {

	SOMA("Adição", "adicao.groovy"), //
	SUBTRACAO("Subtração", "subtracao.groovy"), //
	MULTIPLICACAO("Multiplicação", "multiplicacao.groovy"), //
	DIVISAO("Divisão", "divisao.groovy");

	String nome;
	String arquivo;

	public String getArquivo() {
		return arquivo;
	}

	Operacao(String nome, String arquivo) {
		this.nome = nome;
		this.arquivo = arquivo;
	}

	@Override public String toString() {
		return nome;
	}
}
