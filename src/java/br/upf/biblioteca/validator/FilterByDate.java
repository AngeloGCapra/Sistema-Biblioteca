package br.upf.biblioteca.validator;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named("filterByDate")
@SessionScoped
public class FilterByDate implements Serializable {

    public FilterByDate() {
    }

    public static String datePattern() {
        return "dd/MM/yyyy";
    }

    public static String datePatternHora() {
        return "dd/MM/yyyy HH:mm";
    }
    
    public static String datePatternHoraOptimize() {
        return "dd/MM/yy HH:mm";
    }

    public static String customFormatDate(Date date) {
        if (date != null) {
            DateFormat format = new SimpleDateFormat(datePattern());
            return format.format(date);
        }
        return "";
    }

    public static String customFormatDateTime(Date date) {
        if (date != null) {
            DateFormat format = new SimpleDateFormat(datePatternHora());
            return format.format(date);
        }
        return "";
    }

}
