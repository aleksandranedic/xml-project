package ftn.xml.autor.utils;

import javax.xml.datatype.XMLGregorianCalendar;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String convertToString(XMLGregorianCalendar xmlCalendar) {
        Date date = xmlCalendar.toGregorianCalendar().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }
}
