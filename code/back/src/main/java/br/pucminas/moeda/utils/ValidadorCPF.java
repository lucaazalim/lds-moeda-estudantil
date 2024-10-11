package br.pucminas.moeda.utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidadorCPF implements ConstraintValidator<CPF, String> {

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {

        if (cpf == null) {
            return false;
        }

        return cpf.matches("\\d{11}") && validarCPF(cpf);

    }

    private boolean validarCPF(String cpf) {
        // Calcular o primeiro dígito verificador
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += (cpf.charAt(i) - '0') * (10 - i);
        }

        int firstVerifier = 11 - (sum % 11);
        if (firstVerifier >= 10) {
            firstVerifier = 0;
        }

        // Verifica se o primeiro dígito verificador está correto
        if (firstVerifier != (cpf.charAt(9) - '0')) {
            return false;
        }

        // Calcular o segundo dígito verificador
        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += (cpf.charAt(i) - '0') * (11 - i);
        }

        int secondVerifier = 11 - (sum % 11);
        if (secondVerifier >= 10) {
            secondVerifier = 0;
        }

        // Verifica se o segundo dígito verificador está correto
        return secondVerifier == (cpf.charAt(10) - '0');
    }

}
