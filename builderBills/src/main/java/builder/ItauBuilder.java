package builder;

import util.Modulo10;

public class ItauBuilder extends BaseBoletoBuilder {

    public ItauBuilder() {
        super("341");
    }

    @Override
    protected void validarDadosEspecificos() {
        if (boleto.getAgencia() == null || !boleto.getAgencia().matches("\\d{4}")) {
            throw new IllegalArgumentException("Agência deve ter 4 digitos");
        }

        if (boleto.getContaCorrente() == null || boleto.getContaCorrente().isEmpty()) {
            throw new IllegalArgumentException("Conta corrente não foi informada");
        }

        if (boleto.getCarteira() == null || boleto.getCarteira().isEmpty()) {
            throw new IllegalArgumentException("Carteira não informada");
        }

        String carteiraLimpa = boleto.getCarteira().replaceAll("[^0-9]", "");

        if (carteiraLimpa.length() != 3) {
            throw new IllegalArgumentException
                    ("Carteira deve conter exatamente 3 digitos númericos: " + boleto.getCarteira());
        }

        boleto.setCarteira(carteiraLimpa);

        if (boleto.getNossoNumero() == null || boleto.getNossoNumero().isEmpty()) {
            throw new IllegalArgumentException("Nosso número não informado");
        }
    }

    @Override
    protected String gerarCampoLivre() {
        String carteira = formatarNumero(boleto.getCarteira(), 3);
        String nossoNumero = formatarNumero(boleto.getNossoNumero(), 8);
        String agencia = formatarNumero(boleto.getAgencia(), 4);
        String conta = formatarNumero(boleto.getContaCorrente(), 5);

        String dacNossoNumero = String.valueOf(Modulo10.calcular(carteira + nossoNumero));
        String dacAgenciaConta = String.valueOf(Modulo10.calcular(agencia + conta));

        return carteira + nossoNumero + dacNossoNumero + agencia + conta + dacAgenciaConta + "000";
    }

    private String formatarNumero(String valor, int tamanho) {
        String apenasDigitos = valor.replaceAll("[^0-9]", "");
        return String.format("%" + tamanho + "s", apenasDigitos).replace(' ', '0');
    }
}
