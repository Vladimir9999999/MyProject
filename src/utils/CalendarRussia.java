package utils;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by admin on 14.02.2018.
 */

public class CalendarRussia extends GregorianCalendar {

    public CalendarRussia(int year, int month, int day) {
        super(year, month, day);
    }

    public CalendarRussia() {
        super();
    }

    @Override
    public int get(int field){
        switch (field) {
            case DAY_OF_WEEK:
                if (super.get(Calendar.DAY_OF_WEEK) == 1) {
                    return 7;
                } else {
                    return super.get(Calendar.DAY_OF_WEEK) - 1;
                }
            case MONTH:
                return super.get(field) + 1;
            default:
                return super.get(field);
        }
    }

    @Override
    public void set(int field, int value) {
        if (field == MONTH) {
            super.set(field, value - 1);
        } else {
            super.set(field, value);
        }
    }

    @Override
    public void add(int field, int amount) {
        if (field == MONTH) {
            super.set(field, super.get(Calendar.MONTH) + amount);
        } else {
            super.add(field, amount);
        }
    }
}
