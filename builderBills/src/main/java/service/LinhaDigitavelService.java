package service;

import util.Modulo10;

public class LinhaDigitavelService {

    public static String gerarLinhaDigitavel(String codigoBarras) {
        if (codigoBarras == null || codigoBarras.length() != 44) {
            throw new IllegalArgumentException("Código de Barras Inválido");
        }

        String campo1 = codigoBarras.substring(0, 3) + codigoBarras.substring(19, 24);
        String campo2 = codigoBarras.substring(24, 34);
        String campo3 = codigoBarras.substring(34, 44);
        String campo4 = codigoBarras.substring(4, 5);
        String campo5 = codigoBarras.substring(5, 9) + codigoBarras.substring(9, 19);

        int dv1 = Modulo10.calcular(campo1);
        int dv2 = Modulo10.calcular(campo2);
        int dv3 = Modulo10.calcular(campo3);

        return String.format("%s.%s%s %s.%s%s %s.%s%s %s %s",
                campo1.substring(0, 5), campo1.substring(5), dv1,
                campo2.substring(0, 5), campo2.substring(5), dv2,
                campo3.substring(0, 5), campo3.substring(5), dv3,
                campo4, campo5);

    }

}
