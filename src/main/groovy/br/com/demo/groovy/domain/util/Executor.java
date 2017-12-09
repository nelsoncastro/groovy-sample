package br.com.demo.groovy.domain.util;

import br.com.demo.groovy.domain.enums.Operacao;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;

public class Executor {

	public Object execute(final double x, final double y, final Operacao operacao) {
		String code = new ScriptLoader().getScript(operacao);

		Binding binding = new Binding();
		binding.setVariable("x", x);
		binding.setVariable("y", y);
		GroovyShell shell = new GroovyShell(binding);

		return shell.evaluate(code);
	}

}
