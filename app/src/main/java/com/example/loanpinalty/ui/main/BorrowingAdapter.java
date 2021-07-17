package com.example.loanpinalty.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loanpinalty.R;
import com.example.loanpinalty.data.local.LocalData;
import com.example.loanpinalty.data.model.Borrowing;
import com.example.loanpinalty.utils.Tools;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BorrowingAdapter extends RecyclerView.Adapter<BorrowingAdapter.BorrowingViewHolder> {

    //Handle click on item
    private final OnBorrowingClickListener listener;

    //store list
    private List<Borrowing> borrowings;

    //constructor inject with click listener
    public BorrowingAdapter(OnBorrowingClickListener listener) {
        this.listener = listener;
    }

    //set list borrowing to adapter
    public void setBorrowings(List<Borrowing> list) {
        //ternary condition : if list parameter null then create empty list, if not null set list to variable borrowings
        this.borrowings = list == null ? new ArrayList<>() : list;

        //notifyDataSetChanged to update list recyclerview
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BorrowingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //create viewholder
        return new BorrowingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_borrowing, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BorrowingViewHolder holder, int position) {
        //bind item to viewholder
        holder.bind(borrowings.get(position));
    }

    @Override
    public int getItemCount() {
        return borrowings.size();
    }

    public double getTotalPenalty(){
        double total = 0;

        for (Borrowing borrowing : borrowings){

            total += calculatePenalty(borrowing);
        }
        return total;
    }


    private double calculatePenalty(Borrowing borrowing){

        //set penalty
        double penalty = 0;
        //get differences between today and createdAt borrowing
        int diff = Tools.getDiffDay(new Date(), borrowing.getCreatedAt());

        //if differences is greater than equals 7 show penalty
        if(diff >= 7){
            double fee = LocalData.getFee();

            int penaltyDay = diff - 6;

             penalty = fee *  penaltyDay;

            return penalty;
        }

        return penalty;

    }
    class BorrowingViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvName, tvBorrowingDate, tvDayCount, tvPenalty;
        public BorrowingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvBorrowingDate = itemView.findViewById(R.id.tvBorrowingDate);
            tvDayCount = itemView.findViewById(R.id.tvDayCount);
            tvPenalty = itemView.findViewById(R.id.tvPenalty);
        }

        public void bind(Borrowing borrowing){
            //set name to tvName
            tvName.setText(borrowing.getName());

            //set borrowing date to tvBorrowingDate
            tvBorrowingDate.setText(Tools.formatDate(borrowing.getCreatedAt()));

            //get differences between today and createdAt borrowing
            int diff = Tools.getDiffDay(new Date(), borrowing.getCreatedAt());

            //show differences days as penalty day
            tvDayCount.setText(itemView.getResources().getString(R.string.pinalty_days, diff));

            double penalty = calculatePenalty(borrowing);
            tvPenalty.setText(Tools.getPriceFormat(penalty, true));

            itemView.getRootView().setOnClickListener(v -> {
                //invoke listener onClick;
                listener.onClick(borrowing);
            });

        }
    }

    interface OnBorrowingClickListener {
        void onClick(Borrowing borrowing);
    }
}
