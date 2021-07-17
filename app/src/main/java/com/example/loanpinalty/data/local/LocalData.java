package com.example.loanpinalty.data.local;

import com.example.loanpinalty.data.model.Borrowing;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

public class LocalData {
    //LIST_BORROWING key for shared preferences
    private static String LIST_BORROWING = "LIST_BORROWING";

    //FEE key fro shared preferences
    private static String FEE = "FEE";

    //get fee from shared preferences
    public static Double getFee() { return Hawk.get(FEE); }

    //set fee to shared preferences
    public static void setFee(double value) { Hawk.put(FEE, value); }

    //get list borrowing from shared preferences
    public static List<Borrowing> getListBorrowing() { return Hawk.get(LIST_BORROWING); }

    //set list borrowing to shared preferences
    public static void setListBorrowing(List<Borrowing> listBorrowing) { Hawk.put(LIST_BORROWING, listBorrowing); }

    //add new borrowing
    public static void addBorrowing(Borrowing borrowing) {
        //get list from shared preferences via getListBorrowing() method
        List<Borrowing> list = getListBorrowing();

        //if list is null
        if(list == null){
            //create new empty list
            list = new ArrayList<>();
        }

        //add new borrowing to list
        list.add(borrowing);

        //save list to shared preferences
        setListBorrowing(list);
    }

    //remove borrowing
    public static void removeBorrowing(Borrowing borrowing){
        //get list from shared preferences via getListBorrowing() method
        List<Borrowing> list = getListBorrowing();

        //if list is null or empty
        if(list == null || list.isEmpty()){
            //stop execution removeBorrowing by return
            return;
        }

        //loop for searching borrowing in list
        for(int i = 0; i < list.size(); i++){
            //get temp of borrowing current index loop
            Borrowing temp = list.get(i);

            //if temp.getId() equals to borrowing.getId()
            if(temp.getId().equals(borrowing.getId())){
                //remove borrowing by list.remove current index
                list.remove(i);
                break;
            }
        }
        //save list to shared preferences
        setListBorrowing(list);
    }
}
