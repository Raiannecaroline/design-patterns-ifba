package service;

import model.Boleto;
import util.Modulo10;

public class BancoBrasilLinhaDigitavel {

    public static String gerarLinhaDigitavel(String codigoBarras) {
        if (codigoBarras == null || codigoBarras.length() != 44) {
            throw new IllegalArgumentException("Código de Barras Inválido");
        }

        StringBuilder linhaDigitavel = new StringBuilder();

        String campo1 = codigoBarras.substring(0, 3) +
                codigoBarras.substring(3, 4) +
                codigoBarras.substring(19, 24);

        campo1 = campo1.substring(0, 5) + "." + campo1.substring(5);
        int dv1 = Modulo10.calcular(campo1.replace(".", ""));

        String campo2 = codigoBarras.substring(24, 34);
        campo2 = campo2.substring(0, 5) + "." + campo2.substring(5);
        int dv2 = Modulo10.calcular(campo2.replace(".", ""));
        campo2 += dv2;

        String campo3 = codigoBarras.substring(34);
        campo3 = campo3.substring(0, 5) + "." + campo3.substring(5);
        int dv3 = Modulo10.calcular(campo3.replace(".", ""));
        campo3 += dv3;

        String campo4 = codigoBarras.substring(4, 5);

        String campo5 = codigoBarras.substring(5, 9) + codigoBarras.substring(9, 19);

        linhaDigitavel.append(campo1).append(" ")
                .append(campo2).append(" ")
                .append(campo3).append(" ")
                .append(campo4).append(" ")
                .append(campo5);

        return linhaDigitavel.toString();
    }

}
