package builder;

import util.Modulo11;

public class BradescoBuilder extends BaseBoletoBuilder {

    public BradescoBuilder() {
        super("237");
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

        if (carteiraLimpa.length() != 2) {
            throw new IllegalArgumentException
                    ("Carteira deve conter exatamente 2 digitos númericos: " + boleto.getCarteira());
        }

        boleto.setCarteira(carteiraLimpa);

        if (boleto.getNossoNumero() == null || boleto.getNossoNumero().isEmpty()) {
            throw new IllegalArgumentException("Nosso número não informado");
        }
    }

    @Override
    protected String gerarCampoLivre() {
        String agencia = formatarNumero(boleto.getAgencia(), 4);
        String carteira = formatarNumero(boleto.getCarteira(), 2);
        String nossoNumero = formatarNumero(boleto.getNossoNumero(), 11);
        String conta = formatarNumero(boleto.getContaCorrente(), 7);

        String bloco = agencia + carteira + nossoNumero + conta;
        String dac = String.valueOf(Modulo11.calcular(bloco));

        // Montagem do campo livre
        String campoLivre = agencia + carteira + nossoNumero + conta + dac;

        // Verificação do tamanho
        if (campoLivre.length() != 25) {
            throw new IllegalStateException("Campo livre do Bradesco deve ter 25 dígitos. Gerado: " + campoLivre.length());
        }

        return campoLivre;
    }

    private String formatarNumero(String valor, int tamanho) {
        String apenasDigitos = valor.replaceAll("[^0-9]", "");
        return String.format("%" + tamanho + "s", apenasDigitos).replace(' ', '0');
    }

}
