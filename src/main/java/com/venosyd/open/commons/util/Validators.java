package com.venosyd.open.commons.util;

import java.text.SimpleDateFormat;

import ru.lanwen.verbalregex.VerbalExpression;

/**
 * @author sergio lisan <sels@venosyd.com>
 * 
 *         Colecao de validadores
 */
public abstract class Validators {

    /**
     * validacao de texto
     */
    public static abstract class Text {

        /**
         * validacao basica de texto
         */
        public static boolean validateText(String toValidate) {
            return VerbalExpression.regex().word().build().test(toValidate) && !toValidate.isEmpty();
        }

        /**
         * validacao basica de texto com limite de tamanho
         */
        public static boolean validateTextWithLimit(String toValidate, int limit) {
            return VerbalExpression.regex().word().build().test(toValidate) && !toValidate.isEmpty()
                    && toValidate.length() <= limit;
        }

        /**
         * validacao de nomes ocidentais
         */
        public static boolean validateName(String toValidate) {
            return !VerbalExpression.regex().add("[0-9 !@#$%\"&.*()-=_+\\[\\]{}<>|\\\\/\\^]+").build().test(toValidate)
                    && !toValidate.isEmpty();
        }

        /**
         * validacao de nomes ocidentais com espaco
         */
        public static boolean validateNameWithSpace(String toValidate) {
            return !VerbalExpression.regex().add("[0-9!@#$%\"&.*()-=_+\\[\\]{}<>|\\\\/\\^]+").build().test(toValidate)
                    && !toValidate.isEmpty();
        }

        /**
         * validacao de email
         */
        public static boolean validateEmail(String toValidate) {
            return VerbalExpression.regex().add("\\b[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}\\b").build()
                    .test(toValidate) && !toValidate.isEmpty();
        }

    }

    /**
     * validacao de campos com caracteres especiais
     */
    public static abstract class SpecialChar {

        /**
         * validacao de endereco
         */
        public static boolean validateAddress(String toValidate) {
            return !VerbalExpression.regex().add("[!@#$%\"&*()=_+\\[\\]{}<>|\\\\/\\^]+").build().test(toValidate)
                    && !toValidate.isEmpty();
        }

        /**
         * validacao de numero de telefone
         */
        public static boolean validatePhone(String toValidate) {
            return !VerbalExpression.regex().add("[a-zA-Z!@#$%\"&.*=_\\[\\]{}<>|\\\\/\\^]+").build().test(toValidate)
                    && !toValidate.isEmpty();
        }

        /**
         * validacao basica de texto
         */
        public static boolean validateMoney(String toValidate) {
            toValidate = toValidate.replace(" ", "").replace(",", ".");

            try {
                // faz a conversao pra saber se eh parseavel ou nao
                Double.parseDouble(toValidate);

                // verifica se tem ate 8 subdigitos
                return toValidate.split("[.]")[1].length() <= 8;
            } catch (Exception e) {
                return false;
            }
        }

        /**
         * validacao basica de texto
         */
        public static boolean validateMoney(String code, String toValidate) {
            return validateMoney(toValidate.replace(code, ""));
        }

    }

    /**
     * comparacao de datas
     */
    public static abstract class Date {

        /**
         * verifica se a data passada esta no futuro
         */
        public static boolean isTomorrowAndBeyond(String toValidate) throws Exception {
            var now = new java.util.Date();
            var date = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(toValidate);

            return date.compareTo(now) > 0;
        }

    }

    /**
     * validacao de campos com numeros
     */
    public static abstract class Number {

        /**
         * validacao basica de texto
         */
        public static boolean validateOnlyDigits(String toValidate) {
            return !VerbalExpression.regex().add("[a-zA-Z!@#$%\"&,.*=_+\\[\\]{}<>|\\\\/\\^]+").build().test(toValidate)
                    && !toValidate.isEmpty();
        }

        /**
         * verifica se a string com numero passado esta dentro do range estabelecido
         */
        public static boolean validateRange(String toValidate) {
            return validateRange(toValidate, 4, 0, 1000);
        }

        /**
         * verifica se a string com numero passado esta dentro do range estabelecido
         */
        public static boolean validateRange(String toValidate, int digits, int init, int end) {
            boolean valid = validateOnlyDigits(toValidate);

            if (valid) {
                toValidate = toValidate.length() >= digits ? toValidate.substring(0, digits) : toValidate;
                int intValue = Integer.parseInt(toValidate);
                return intValue > init && intValue <= end;
            }

            return false;
        }

    }

    /**
     * valida tanto CPF quanto CNPJ
     */
    public static abstract class CNP {

        private static final int[] pesoCPF = { 11, 10, 9, 8, 7, 6, 5, 4, 3, 2 };
        private static final int[] pesoCNPJ = { 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };

        /**
         * funcao que faz o calculo de validacao
         */
        private static int calcularDigito(String str, int[] peso) {
            int soma = 0;
            for (int indice = str.length() - 1, digito; indice >= 0; indice--) {
                digito = Integer.parseInt(str.substring(indice, indice + 1));
                soma += digito * peso[peso.length - str.length() + indice];
            }
            soma = 11 - soma % 11;
            return soma > 9 ? 0 : soma;
        }

        /**
         * valida o cpf
         */
        public static boolean validateCPF(String cpf) {
            cpf = cpf.replaceAll("\\D+", "");

            if ((cpf == null) || (cpf.length() != 11))
                return false;

            var digito1 = calcularDigito(cpf.substring(0, 9), pesoCPF);
            var digito2 = calcularDigito(cpf.substring(0, 9) + digito1, pesoCPF);
            return cpf.equals(cpf.substring(0, 9) + digito1 + digito2);
        }

        /**
         * valida o cnpj
         */
        public static boolean validateCNPJ(String cnpj) {
            cnpj = cnpj.replaceAll("\\D+", "");

            if ((cnpj == null) || (cnpj.length() != 14))
                return false;

            var digito1 = calcularDigito(cnpj.substring(0, 12), pesoCNPJ);
            var digito2 = calcularDigito(cnpj.substring(0, 12) + digito1, pesoCNPJ);
            return cnpj.equals(cnpj.substring(0, 12) + digito1 + digito2);
        }

    }

}
