package utils;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class DatesUtill {

    public static int parameterToInt(int hour, int minute) throws NumberFormatException {

        int h = hour;

        h *= 60;

        h /= 5;

        int m = minute;

        m /= 5;

        h += m;

        return h;

    }

    public static int[] intToParameter(int time) {

        int[] ret = new int[2];
        int minute = (time * 5) % 60;
        int hour = (time*5 - minute) / 60 ;

        ret[0] = hour;
        ret[1] = minute;

        return ret;

    }

    public static int getMicrosecondsDays(int days) {

        return (days * 24 * 60 * 60 * 1000);
    }

    public static int getMicrosecondsFiveMinutes(int number) {

        return (number * 5 * 60 * 1000);
    }

    public static int getFirstDay(GregorianCalendar calendar, GregorianCalendar first, int quantityWorkingDay, int quantityWeekends) {

        Calendar comparison = Calendar.getInstance();
        comparison.setTimeInMillis(first.getTimeInMillis() - calendar.getTimeInMillis());

        int difference = comparison.get(Calendar.DAY_OF_YEAR) - 1;

        return difference % (quantityWorkingDay + quantityWeekends);

    }

    public static int parameterToInt(GregorianCalendar time){
        return parameterToInt(time.get(Calendar.HOUR_OF_DAY),time.get(Calendar.MINUTE));
    }

}

