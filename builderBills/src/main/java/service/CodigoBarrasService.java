package service;

import model.Boleto;
import util.DataUtil;
import util.Modulo11;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static util.DataUtil.calcularFatorVencimento;

public class CodigoBarrasService {

    public static String gerarCodigoBarras(String codigoBanco, Date vencimento,
                                           BigDecimal valor, String campoLivre) {

        validarDados(codigoBanco, vencimento, valor, campoLivre);

        String fatorVencimento = calcularFatorVencimento(vencimento);
        String valorFormatado = formatarValor(valor);

        StringBuilder codigo = new StringBuilder()
                .append(codigoBanco)
                .append("9")
                .append("0")
                .append(fatorVencimento)
                .append(valorFormatado)
                .append(campoLivre);

        int dv = Modulo11.calcular(codigo.toString());
        codigo.setCharAt(4, Character.forDigit(dv, 10));

        return codigo.toString();

    }

    private static void validarDados(String codigoBanco, Date vencimento, BigDecimal valor, String campoLivre) {
        if (codigoBanco == null || codigoBanco.length() != 3) {
            throw new IllegalArgumentException("Código do banco inválido");
        }
        if (vencimento == null) {
            throw new IllegalArgumentException("Data de vencimento não informada");
        }
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor inválido");
        }
        if (campoLivre == null || campoLivre.length() != 25) {
            throw new IllegalArgumentException("Campo livre deve ter 25 dígitos");
        }
    }

    private static String formatarValor(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor do boleto inválido");
        }
        return String.format("%010d", valor.multiply(new BigDecimal("100")).intValue());
    }
}
