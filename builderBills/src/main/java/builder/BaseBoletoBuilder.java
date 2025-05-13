package builder;


import model.Beneficiario;
import model.Boleto;
import model.Sacado;
import service.CodigoBarrasService;
import service.LinhaDigitavelService;

import java.math.BigDecimal;
import java.util.Date;

public abstract class BaseBoletoBuilder implements BoletoBuilder {

    protected final Boleto boleto;

    public BaseBoletoBuilder(String codigoBanco) {
        this.boleto = new Boleto();
        this.boleto.setBanco(codigoBanco);
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
        validarDadosComuns();
        validarDadosEspecificos();

        String codigoBarras = CodigoBarrasService.gerarCodigoBarras(
                boleto.getBanco(),
                boleto.getDataVencimento(),
                boleto.getValor(),
                gerarCampoLivre()
        );

        String linhaDigitavel = LinhaDigitavelService.gerarLinhaDigitavel(codigoBarras);

        boleto.setCodigoBarras(codigoBarras);
        boleto.setLinhaDigitavel(linhaDigitavel);

        return boleto;

    }

    protected void validarDadosComuns() {
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
    }

    protected abstract void validarDadosEspecificos();

    protected abstract String gerarCampoLivre();
}
