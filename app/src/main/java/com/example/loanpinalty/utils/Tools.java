package com.example.loanpinalty.utils;

import android.annotation.SuppressLint;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

        //Comparing dates
        long difference = Math.abs(from.getTime() - to.getTime());
        long differenceDates = difference / (24 * 60 * 60 * 1000);

        //Convert long to String
        return (int) differenceDates;
    }
}
