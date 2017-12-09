package br.com.demo.groovy.domain.enums;

public enum Operacao {

	SOMA("Adi��o", "adicao.groovy"), //
	SUBTRACAO("Subtra��o", "subtracao.groovy"), //
	MULTIPLICACAO("Multiplica��o", "multiplicacao.groovy"), //
	DIVISAO("Divis�o", "divisao.groovy");

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
