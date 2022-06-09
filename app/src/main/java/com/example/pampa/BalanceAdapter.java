package com.example.pampa;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class BalanceAdapter extends RecyclerView.Adapter<BalanceAdapter.BalanceViewHolder> {
    ArrayList<DaysModel> list;
    private NumberFormat currformat;

    public BalanceAdapter(ArrayList<DaysModel> ls){
        this.list = ls;
        currformat = NumberFormat.getCurrencyInstance(new Locale("in","ID"));
    }

    @NonNull
    @Override
    public BalanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater lf = LayoutInflater.from(parent.getContext());
        View vw = lf.inflate(R.layout.item,parent,false);
        return new BalanceViewHolder(vw);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull BalanceViewHolder holder, int position) {

        String[] tanggal = list.get(position).getTanggal().split(" ");
        holder.monyear.setText(tanggal[1]+" "+tanggal[2]);
        holder.spend.setText(currformat.format(list.get(position).getSpend()));
        holder.income.setText(currformat.format(list.get(position).getIncome()));
        holder.tanggal.setText(tanggal[0]);

        if (list.get(position).isStatus()){
                holder.balance.setText("+"+currformat.format(Integer.parseInt(list.get(position).getBalance())));
                holder.item.setBackgroundResource(R.drawable.bg_item_p);
            }else if(!list.get(position).isStatus()){
                holder.balance.setText("-"+currformat.format(Integer.parseInt(list.get(position).getBalance())*(-1)));
                holder.tvinc.setTextColor(Color.parseColor("#D96A84"));
                holder.tvspend.setTextColor(Color.parseColor("#D96A84"));
                holder.tvbal.setTextColor(Color.parseColor("#D96A84"));
                holder.item.setBackgroundResource(R.drawable.bg_item_r);
        }

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    }

    @Override
    public int getItemCount() {
        return (list != null) ? list.size():0;
    }

    public class BalanceViewHolder extends RecyclerView.ViewHolder{
        private TextView tanggal,monyear,balance,spend,income,tvbal,tvspend,tvinc;
        private LinearLayout item;

        public BalanceViewHolder(@NonNull View itemView) {
            super(itemView);

            this.item = (LinearLayout) itemView.findViewById(R.id.bg_item);
            this.tanggal = (TextView) itemView.findViewById(R.id.tanggal);
            this.monyear = (TextView) itemView.findViewById(R.id.monyear);
            this.balance = (TextView) itemView.findViewById(R.id.balance);
            this.spend = (TextView) itemView.findViewById(R.id.spend);
            this.income = (TextView) itemView.findViewById(R.id.income);
            this.tvbal = (TextView) itemView.findViewById(R.id.tvibal);
            this.tvspend = (TextView) itemView.findViewById(R.id.tvispend);
            this.tvinc = (TextView) itemView.findViewById(R.id.tvincome);

        }
    }
}
