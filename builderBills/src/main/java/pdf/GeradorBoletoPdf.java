package pdf;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import model.Boleto;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class GeradorBoletoPdf {

    private static final Font FONT_NORMAL = new Font(Font.FontFamily.COURIER, 8, Font.NORMAL);
    private static final Font FONT_BOLD = new Font(Font.FontFamily.COURIER, 8, Font.BOLD);
    private static final Font FONT_TITLE = new Font(Font.FontFamily.COURIER, 8, Font.BOLD);

    public static void gerarBoleto(Boleto boleto, String arquivo) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4, 20, 20, 20, 20);
        PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(arquivo));

        document.open();

        Paragraph titulo = new Paragraph("BOLETO BANCÁRIO", FONT_TITLE);
        titulo.setAlignment(Element.ALIGN_CENTER);
        document.add(titulo);

        document.add(createBarCode(boleto.getLinhaDigitavel(), pdfWriter));

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{1, 3});

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        addCell(table, "Vencimento:", sdf.format(boleto.getDataVencimento()), false);
        addCell(table, "Valor:", "R$ " + boleto.getValor(), false);
        addCell(table, "N° Documento:", boleto.getNumeroDocumento(), false);
        addCell(table, "Nosso Número:", boleto.getNossoNumero(), false);


        addCell(table, "Agência/Código Beneficiário:",
                boleto.getAgencia() + "/" + boleto.getContaCorrente(), false);

        document.add(table);

        Paragraph codigo = new Paragraph("Código de Barras: " + boleto.getCodigoBarras(), FONT_NORMAL);
        document.add(codigo);
        document.close();
    }

    private static void addCell(PdfPTable table, String label, String value, boolean bold) {
        Font font = bold ? FONT_BOLD : FONT_NORMAL;
        table.addCell(new Phrase(label, FONT_BOLD));
        table.addCell(new Phrase(value, font));
    }

    public static Image createBarCode(String code, PdfWriter pdfWriter) throws DocumentException {
        Barcode128 barcode = new Barcode128();
        barcode.setCodeType(Barcode128.CODE128);
        barcode.setCode(code.replace(" ", ""));
        barcode.setFont(null);
        return barcode.createImageWithBarcode(pdfWriter.getDirectContent(), null, null);
    }

}
