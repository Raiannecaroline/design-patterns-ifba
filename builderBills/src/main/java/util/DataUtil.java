package util;


import java.util.Calendar;
import java.util.Date;

public class DataUtil {

    private static final Calendar DATA_BASE = Calendar.getInstance();

    static {
        DATA_BASE.set(1997, Calendar.OCTOBER, 7);
    }

    public static String calcularVencimento(Date vencimento) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(vencimento);

        long diff = calendar.getTimeInMillis() - DATA_BASE.getTimeInMillis();
        long dias = diff / (1000 * 60 * 60 * 24);
        return String.format("%04d", dias);
    }

}
