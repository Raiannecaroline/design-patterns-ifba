package util;


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DataUtil {

    private static final Calendar DATA_BASE = Calendar.getInstance();

    static {
        DATA_BASE.set(1997, Calendar.OCTOBER, 7);
    }

    public static String calcularFatorVencimento(Date dataVencimento) {
        if (dataVencimento == null) return "0000";

        // Data base: 07/10/1997
        long dataBase = new GregorianCalendar(1997, Calendar.OCTOBER, 7).getTimeInMillis();
        long diffDias = (dataVencimento.getTime() - dataBase) / (24 * 60 * 60 * 1000);

        // Limita a 4 dígitos (0-9999)
        diffDias = Math.max(0, Math.min(diffDias, 9999));

        return String.format("%04d", diffDias);
    }

}
