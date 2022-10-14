package task2;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateCalendar {
    private Date date;
    private Calendar calendar;

    public DateCalendar(int year, int month, int day, int hours, int minutes) {
        date = new Date(year, month, day, hours, minutes);
        calendar = new GregorianCalendar(year, month, day, hours, minutes);
    }

}
