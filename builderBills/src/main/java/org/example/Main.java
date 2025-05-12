package org.example;

import builder.BancoBrasilBuilder;
import model.Boleto;
import pdf.GeradorBoletoPdf;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

public class Main {
    public static void main(String[] args) {

        try {
            System.out.println("Iniciando geração de boleto...");

            // Configuração com data de vencimento válida
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, 30); // 30 dias a partir de hoje
            Date vencimento = calendar.getTime();

            Boleto boleto = new BancoBrasilBuilder()
                    .comBeneficiario("Fantury Software LTDA", "12.346.678/0001-99", "Endereço")
                    .comSacado("Cliente", "123.456.789-09", "Endereço")
                    .comDocumento("123456", new Date())
                    .comVencimento(vencimento) // Data dentro do limite
                    .comValor(new BigDecimal("1500.00"))
                    .comAgencia("1234")
                    .comContaCorrente("1234567")
                    .comContaCarteira("17")
                    .comNossoNumero("12345678901")
                    .construirBoleto();

            System.out.println("Emitindo o boleto...");
            GeradorBoletoPdf.gerarBoleto(boleto, "boleto_banco_brasil.pdf");
            System.out.println("Boleto gerado com sucesso!");
            System.out.println("Código de Barras: " + boleto.getCodigoBarras());
            System.out.println("Linha Digitável: " + boleto.getLinhaDigitavel());
            System.out.println("PDF salvo como: boleto_banco_brasil.pdf");

        } catch (IllegalArgumentException e) {
            System.err.println("ERRO DE VALIDAÇÃO: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("ERRO INESPERADO: " + e.getMessage());
            e.printStackTrace();
        }
    }
}