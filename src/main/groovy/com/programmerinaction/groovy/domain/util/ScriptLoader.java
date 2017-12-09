package com.programmerinaction.groovy.domain.util;

import com.programmerinaction.groovy.domain.enums.Operacao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ScriptLoader {

	public String getScript(Operacao operacao) {
		StringBuilder builder = new StringBuilder();
		try (InputStream input = getClass().getResourceAsStream("/" + operacao.getArquivo())) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			String linha = null;
			while ((linha = reader.readLine()) != null) {
				builder.append(linha).append("\n");
			}
		} catch (IOException e) {
			return null;
		}
		return builder.toString();
	}
}
