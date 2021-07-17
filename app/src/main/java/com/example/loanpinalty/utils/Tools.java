package com.example.loanpinalty.utils;

import android.annotation.SuppressLint;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class Tools {
    public static String getPriceFormat(double amount, boolean showCurrency) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        formatRupiah.setMaximumFractionDigits(0);
        if (!showCurrency) {
            return formatRupiah.format(amount).replace("Rp", "");
        }
        return formatRupiah.format(amount);
    }

    @SuppressLint("SimpleDateFormat")
    public static String formatDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy", new Locale("in", "ID"));
        return format.format(date);
    }

    public static int getDiffDay(Date from, Date to){

        //cutoff time information
        final Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(from);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        //cutoff time information
        final Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(to);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);
        //Comparing dates
        long difference = Math.abs(fromCalendar.getTime().getTime() - toCalendar.getTime().getTime());
        long differenceDates = difference / (24 * 60 * 60 * 1000);

        //Convert long to String
        return (int) differenceDates;
    }

    public static String generateUUID(){
        return  UUID.randomUUID().toString();
    }
}
