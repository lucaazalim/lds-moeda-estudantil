package br.pucminas.moeda.utils;

import java.security.SecureRandom;

public class GeradorCupom {

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int TAMANHO = 8;

    public static String gerarCupom() {

        StringBuilder sb = new StringBuilder(TAMANHO);

        for (int i = 0; i < TAMANHO; i++) {
            int randomIndex = RANDOM.nextInt(CARACTERES.length());
            sb.append(CARACTERES.charAt(randomIndex));
        }

        return sb.toString();

    }

}
