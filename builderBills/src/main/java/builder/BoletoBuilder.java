package builder;

import model.Boleto;

import java.math.BigDecimal;
import java.util.Date;

public interface BoletoBuilder {

    BoletoBuilder comBeneficiario(String nome, String documento, String endereco);
    BoletoBuilder comSacado(String nome, String documento, String endereco);
    BoletoBuilder comDocumento(String numero, Date data);
    BoletoBuilder comVencimento(Date data);
    BoletoBuilder comValor(BigDecimal valor);
    BoletoBuilder comInstrucoes(String instrucoes);
    BoletoBuilder comLocalPagamento(String local);
    BoletoBuilder comDesconto(BigDecimal desconto);
    BoletoBuilder comMulta(BigDecimal multa);
    BoletoBuilder comJuros(BigDecimal juros);
    BoletoBuilder comAgencia(String agencia);
    BoletoBuilder comContaCorrente(String conta);
    BoletoBuilder comContaCarteira(String carteira);
    BoletoBuilder comNossoNumero(String nossoNumero);
    Boleto construirBoleto();


}
