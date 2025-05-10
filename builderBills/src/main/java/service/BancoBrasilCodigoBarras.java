package service;

import model.Boleto;
import util.DataUtil;
import util.Modulo11;

import java.math.BigDecimal;

public class BancoBrasilCodigoBarras {

    public static String gerarCodigoBarras(Boleto boleto) {
        StringBuilder codigoBarras = new StringBuilder(44);

        codigoBarras.append("001");
        codigoBarras.append("9");
        codigoBarras.append("0");
        codigoBarras.append(DataUtil.calcularVencimento(boleto.getDataVencimento()));
        codigoBarras.append(String.format("%010d", boleto.getValor().multiply(new BigDecimal("100")).intValue()));
        codigoBarras.append(gerarCampoLivre(boleto));

        int dv = Modulo11.calcular(codigoBarras.toString());
        codigoBarras.setCharAt(4, Character.forDigit(dv, 10));

        return codigoBarras.toString();
    }

    public static String gerarCampoLivre(Boleto boleto) {
        StringBuilder campoLivre = new StringBuilder(25);

        campoLivre.append(String.format("%02d", Integer.parseInt(boleto.getCarteira())));

        String nossoNumero = boleto.getNossoNumero().replaceAll("[^0-9]", "");
        campoLivre.append(String.format("%011d", Long.parseLong(nossoNumero)));

        campoLivre.append(boleto.getAgencia());

        String conta = boleto.getContaCorrente().replaceAll("[^0-9]", "");
        campoLivre.append(String.format("%08d", Long.parseLong(conta)));

        if (!conta.isEmpty()) {
            campoLivre.append(conta.charAt(conta.length() - 1));
        } else {
            campoLivre.append("0");
        }

        while (campoLivre.length() < 25) {
            campoLivre.append("0");
        }

        return campoLivre.substring(0, 25);
    }
}
