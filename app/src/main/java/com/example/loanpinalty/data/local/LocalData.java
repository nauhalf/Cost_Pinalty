package com.example.loanpinalty.data.local;

import com.example.loanpinalty.data.model.Loan;
import com.orhanobut.hawk.Hawk;

public class LocalData {
    private static String LOAN = "LOAN";

    public static Loan getLoan() {
        return Hawk.get(LOAN);
    }

    public static void setLoan(Loan loan) {
        Hawk.put(LOAN, loan);
    }

    public static void deleteLoan(){
        Hawk.delete(LOAN);
    }
}
