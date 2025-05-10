package service;

import model.Boleto;
import util.DataUtil;

public class BancoBrasilCodigoBarras {

    public static String gerarCodigoBarras(Boleto boleto) {
        StringBuilder codigoBarras = new StringBuilder(44);

        codigoBarras.append("001");
        codigoBarras.append("9");
        codigoBarras.append("0");
        codigoBarras.append(DataUtil.calcularVencimento(boleto.getDataVencimento()));
    }

}
