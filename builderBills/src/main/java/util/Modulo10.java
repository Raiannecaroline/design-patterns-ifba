package util;

public class Modulo10 {

    public static int calcular(String numero) {
        int soma = 0;
        int peso = 2;

        for (int i = numero.length() - 1; i >= 0; i--) {
            int digito = Character.getNumericValue(numero.charAt(i));
            int resultado = digito * peso;

            if (resultado > 9) {
                resultado = (resultado / 10) + (resultado % 10);
            }

            soma += resultado;
            peso = peso == 2 ? 1 : 2;
        }

        int resto = soma % 10;
        return resto == 0 ? 0 : 10 - resto;
    }

}
