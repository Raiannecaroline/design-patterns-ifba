package org.example;

import builder.*;
import model.Boleto;
import pdf.GeradorBoletoPdf;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("=== GERADOR DE BOLETOS BANCÁRIOS ===");
        System.out.println("Selecione o banco:");
        System.out.println("1 - Banco do Brasil");
        System.out.println("2 - Itaú");
        System.out.println("3 - Bradesco");
        System.out.print("Opção: ");

        int opcao = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer

        BoletoBuilder builder = selecionarBuilder(opcao);
        if (builder == null) {
            System.out.println("Opção inválida!");
            return;
        }

        // Coletar dados do boleto
        System.out.println("\nInforme os dados do boleto:");

        System.out.print("Beneficiário (Nome): ");
        String nomeBeneficiario = scanner.nextLine();

        System.out.print("Beneficiário (CNPJ/CPF): ");
        String docBeneficiario = scanner.nextLine();

        System.out.print("Beneficiário (Endereço): ");
        String endBeneficiario = scanner.nextLine();

        System.out.print("Sacado (Nome): ");
        String nomeSacado = scanner.nextLine();

        System.out.print("Sacado (CNPJ/CPF): ");
        String docSacado = scanner.nextLine();

        System.out.print("Sacado (Endereço): ");
        String endSacado = scanner.nextLine();

        System.out.print("Número do Documento: ");
        String numDoc = scanner.nextLine();

        System.out.print("Data Vencimento (dd/MM/yyyy): ");
        Date vencimento = parseData(scanner.nextLine());

        System.out.print("Valor: ");
        BigDecimal valor = new BigDecimal(scanner.nextLine());

        System.out.print("Agência: ");
        String agencia = scanner.nextLine();

        System.out.print("Conta Corrente: ");
        String conta = scanner.nextLine();

        System.out.print("Carteira: ");
        String carteira = scanner.nextLine();

        System.out.print("Nosso Número: ");
        String nossoNumero = scanner.nextLine();

        // Construir o boleto
        Boleto boleto = builder
                .comBeneficiario(nomeBeneficiario, docBeneficiario, endBeneficiario)
                .comSacado(nomeSacado, docSacado, endSacado)
                .comDocumento(numDoc, new Date())
                .comVencimento(vencimento)
                .comValor(valor)
                .comAgencia(agencia)
                .comContaCorrente(conta)
                .comContaCarteira(carteira)
                .comNossoNumero(nossoNumero)
                .construirBoleto();

        // Exibir resultados
        System.out.println("\n=== BOLETO GERADO ===");
        System.out.println("Banco: " + getNomeBanco(opcao));
        System.out.println("Linha Digitável: " + boleto.getLinhaDigitavel());
        System.out.println("Código de Barras: " + boleto.getCodigoBarras());

        // Gerar PDF
        System.out.print("\nDeseja gerar PDF do boleto? (S/N): ");
        String gerarPdf = scanner.nextLine();
        if (gerarPdf.equalsIgnoreCase("S")) {
            System.out.print("Nome do arquivo (sem extensão): ");
            String nomeArquivo = scanner.nextLine();
            try {
                GeradorBoletoPdf.gerarBoleto(boleto, nomeArquivo + ".pdf");
                System.out.println("PDF gerado com sucesso!");
            } catch (Exception e) {
                System.err.println("Erro ao gerar PDF: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private static BoletoBuilder selecionarBuilder(int opcao) {
        switch (opcao) {
            case 1: return new BancoBrasilBuilder();
            case 2: return new ItauBuilder();
            case 3: return new BradescoBuilder();
            default: return null;
        }
    }

    private static String getNomeBanco(int opcao) {
        switch (opcao) {
            case 1: return "Banco do Brasil (001)";
            case 2: return "Itaú (341)";
            case 3: return "Bradesco (237)";
            default: return "Desconhecido";
        }
    }

    private static Date parseData(String dataStr) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(dataStr);
        } catch (ParseException e) {
            System.out.println("Data inválida, usando data atual");
            return new Date();
        }
    }
}