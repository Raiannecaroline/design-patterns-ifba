package service;

import model.Boleto;
import util.DataUtil;
import util.Modulo11;

import java.math.BigDecimal;

public class BancoBrasilCodigoBarras {



    private static String formatarValor(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor do boleto inválido");
        }
        return String.format("%010d", valor.multiply(new BigDecimal("100")).intValue());
    }

    public static String gerarCodigoBarras(Boleto boleto) {

        try {
            // Validação inicial
            if (boleto == null) {
                throw new IllegalArgumentException("Boleto não pode ser nulo");
            }

            // Geração dos componentes
            String fatorVencimento = DataUtil.calcularFatorVencimento(boleto.getDataVencimento());
            String valorFormatado = formatarValor(boleto.getValor());
            String campoLivre = gerarCampoLivre(boleto);

            // Verificação dos tamanhos
            if (fatorVencimento.length() != 4 ||
                    valorFormatado.length() != 10 ||
                    campoLivre.length() != 25) {
                throw new IllegalStateException("Componentes com tamanhos inválidos: " +
                        "Vencimento=" + fatorVencimento.length() + ", " +
                        "Valor=" + valorFormatado.length() + ", " +
                        "CampoLivre=" + campoLivre.length());
            }

            // Montagem do código
            StringBuilder codigo = new StringBuilder()
                    .append("001")   // Código do banco
                    .append("9")     // Código da moeda
                    .append("0")     // DV provisório
                    .append(fatorVencimento)
                    .append(valorFormatado)
                    .append(campoLivre);

            // Cálculo do dígito verificador
            int dv = Modulo11.calcular(codigo.toString());
            codigo.setCharAt(4, Character.forDigit(dv, 10));

            String codigoFinal = codigo.toString();

            if (codigoFinal.length() != 44) {
                throw new IllegalStateException("Código final com tamanho inválido: " + codigoFinal.length());
            }

            return codigoFinal;

        } catch (Exception e) {
            throw new IllegalArgumentException("Erro ao gerar código de barras: " + e.getMessage(), e);
        }

    }

    public static String gerarCampoLivre(Boleto boleto) {

        if (boleto.getCarteira() == null || boleto.getNossoNumero() == null ||
                boleto.getAgencia() == null || boleto.getContaCorrente() == null) {
            throw new IllegalArgumentException("Dados incompletos para gerar campo livre");
        }

        // 1. Formatar cada componente individualmente
        String carteira = String.format("%02d", Integer.parseInt(boleto.getCarteira().replaceAll("[^0-9]", "")));
        String nossoNumero = String.format("%011d", Long.parseLong(boleto.getNossoNumero().replaceAll("[^0-9]", "")));
        String agencia = boleto.getAgencia().replaceAll("[^0-9]", "");

        // 2. Extrair conta e DAC
        String contaCompleta = boleto.getContaCorrente().replaceAll("[^0-9]", "");
        String conta = contaCompleta.length() >= 7 ?
                contaCompleta.substring(0, 7) :
                String.format("%07d", Long.parseLong(contaCompleta));
        char dac = contaCompleta.isEmpty() ? '0' : contaCompleta.charAt(contaCompleta.length() - 1);

        // 3. Montar campo livre
        String campoLivre = carteira + nossoNumero + agencia + conta + dac;

        // Debug detalhado
        System.out.println("\nDebug Campo Livre:");
        System.out.println("Carteira (2): " + carteira);
        System.out.println("Nosso Número (11): " + nossoNumero);
        System.out.println("Agência (4): " + agencia);
        System.out.println("Conta (7): " + conta);
        System.out.println("DAC (1): " + dac);
        System.out.println("TOTAL: " + campoLivre.length() + " dígitos");

        if (campoLivre.length() != 25) {
            throw new IllegalStateException("Campo livre inválido. Esperado 25 dígitos, obtido " + campoLivre.length());
        }

        return campoLivre;
    }
}
