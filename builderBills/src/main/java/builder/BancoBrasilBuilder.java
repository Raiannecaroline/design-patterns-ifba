package builder;


import model.Beneficiario;
import model.Boleto;
import model.Sacado;
import service.BancoBrasilCodigoBarras;
import service.BancoBrasilLinhaDigitavel;

import java.math.BigDecimal;
import java.util.Date;

public class BancoBrasilBuilder implements BoletoBuilder {

    private final Boleto boleto;

    public BancoBrasilBuilder() {
        this.boleto = new Boleto();
        this.boleto.setBanco("001");
    }

    @Override
    public BoletoBuilder comBeneficiario(String nome, String documento, String endereco) {
        this.boleto.setBeneficiario(new Beneficiario(nome, documento, endereco));
        return this;
    }

    @Override
    public BoletoBuilder comSacado(String nome, String documento, String endereco) {
        this.boleto.setSacado(new Sacado(nome, documento, endereco));
        return this;
    }

    @Override
    public BoletoBuilder comDocumento(String numero, Date data) {
        this.boleto.setNumeroDocumento(numero);
        this.boleto.setDataDocumento(data);
        return this;
    }

    @Override
    public BoletoBuilder comVencimento(Date data) {
        this.boleto.setDataVencimento(data);
        return this;
    }

    @Override
    public BoletoBuilder comValor(BigDecimal valor) {
        this.boleto.setValor(valor);
        return this;
    }

    @Override
    public BoletoBuilder comInstrucoes(String instrucoes) {
        this.boleto.setInstrucoes(instrucoes);
        return this;
    }

    @Override
    public BoletoBuilder comLocalPagamento(String local) {
        this.boleto.setLocalPagamento(local);
        return this;
    }

    @Override
    public BoletoBuilder comDesconto(BigDecimal desconto) {
        this.boleto.setValorDesconto(desconto);
        return this;
    }

    @Override
    public BoletoBuilder comMulta(BigDecimal multa) {
        this.boleto.setValorMulta(multa);
        return this;
    }

    @Override
    public BoletoBuilder comJuros(BigDecimal juros) {
        this.boleto.setValorJuros(juros);
        return this;
    }

    @Override
    public BoletoBuilder comAgencia(String agencia) {
        this.boleto.setAgencia(agencia);
        return this;
    }

    @Override
    public BoletoBuilder comContaCorrente(String conta) {
        this.boleto.setContaCorrente(conta);
        return this;
    }

    @Override
    public BoletoBuilder comContaCarteira(String carteira) {
        this.boleto.setCarteira(carteira);
        return this;
    }

    @Override
    public BoletoBuilder comNossoNumero(String nossoNumero) {
        this.boleto.setNossoNumero(nossoNumero);
        return this;
    }

    @Override
    public Boleto construirBoleto() {
        validarDados();

        this.boleto.setCodigoBarras(BancoBrasilCodigoBarras.gerarCodigoBarras(this.boleto));
        this.boleto.setLinhaDigitavel(BancoBrasilLinhaDigitavel.gerarLinhaDigitavel(this.boleto.getCodigoBarras()));

        return this.boleto;

    }

    private void validarDados() {
        if (boleto.getBeneficiario() == null) {
            throw new IllegalArgumentException("Beneficiário não informado");
        }

        if (boleto.getSacado() == null) {
            throw new IllegalArgumentException("Sacado não informado");
        }

        if (boleto.getDataVencimento() == null) {
            throw new IllegalArgumentException("Data de vencimento não informada");
        }

        if (boleto.getValor() == null || boleto.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor é inválido");
        }

        if (boleto.getAgencia() == null || !boleto.getAgencia().matches("\\d{4}")) {
            throw new IllegalArgumentException("Agência deve ter 4 digitos");
        }

        if (boleto.getContaCorrente() == null || boleto.getContaCorrente().isEmpty()) {
            throw new IllegalArgumentException("Conta corrente não foi informada");
        }

        if (boleto.getCarteira() == null || boleto.getCarteira().matches("\\d{2}")) {
            throw new IllegalArgumentException("Carteira deve ter 2 digitos");
        }

        if (boleto.getNossoNumero() == null || boleto.getNossoNumero().isEmpty()) {
            throw new IllegalArgumentException("Nosso número não informado");
        }
    }

}
