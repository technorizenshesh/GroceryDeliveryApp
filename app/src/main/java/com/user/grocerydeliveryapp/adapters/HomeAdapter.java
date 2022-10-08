package com.user.grocerydeliveryapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.gson.Gson;
import com.user.grocerydeliveryapp.R;
import com.user.grocerydeliveryapp.activities.OrderAct;
import com.user.grocerydeliveryapp.activities.RideCompletedAct;
import com.user.grocerydeliveryapp.activities.TripDetail;
import com.user.grocerydeliveryapp.model.SuccessResGetOrders;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.SelectTimeViewHolder> {

    private Context context;

    private ArrayList<SuccessResGetOrders.Result>  requestList;

    public HomeAdapter(Context context,ArrayList<SuccessResGetOrders.Result>  requestList)
    {
        this.context = context;
        this.requestList = requestList;
    }

    @NonNull
    @Override
    public SelectTimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.home_item, parent, false);
        SelectTimeViewHolder viewHolder = new SelectTimeViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SelectTimeViewHolder holder, int position) {

        RelativeLayout rlParent = holder.itemView.findViewById(R.id.rlParent);

        TextView tvCompleted = holder.itemView.findViewById(R.id.tvCompleted);

        TextView tvNew = holder.itemView.findViewById(R.id.tvNew);

        TextView tvPrice = holder.itemView.findViewById(R.id.tvPrice);

        TextView tvInProgress = holder.itemView.findViewById(R.id.tvInProgress);

        tvPrice.setText("$"+requestList.get(position).getPrice());

        if(requestList.get(position).getBookigStatus().equalsIgnoreCase("ACCEPTED_BY_ADMIN"))
        {
            tvNew.setVisibility(View.VISIBLE);
            tvCompleted.setVisibility(View.GONE);
            tvInProgress.setVisibility(View.GONE);
        }else if(requestList.get(position).getBookigStatus().equalsIgnoreCase("PROCESSING") || requestList.get(position).getBookigStatus().equalsIgnoreCase("PICKUP"))
        {
            tvNew.setVisibility(View.GONE);
            tvCompleted.setVisibility(View.GONE);
            tvInProgress.setVisibility(View.VISIBLE);
        } else if(requestList.get(position).getBookigStatus().equalsIgnoreCase("DELIVERED"))
        {
            tvNew.setVisibility(View.GONE);
            tvCompleted.setVisibility(View.VISIBLE);
            tvInProgress.setVisibility(View.GONE);
        }
        TextView tvTime = holder.itemView.findViewById(R.id.tvTime);
        TextView tvFromLocation = holder.itemView.findViewById(R.id.tvFromLocation);
        TextView tvtoLocation = holder.itemView.findViewById(R.id.tvtoLocation);
        tvtoLocation.setText(requestList.get(position).getUserAddress());
        tvFromLocation.setText(requestList.get(position).getCompanyAddress());
        tvTime.setText(requestList.get(position).getBookingDeliveryTime());
        rlParent.setOnClickListener(v ->
                {
                    if(requestList.get(position).getBookigStatus().equalsIgnoreCase("ACCEPTED_BY_ADMIN"))
                    {
                        context.startActivity(new Intent(context, OrderAct.class).putExtra("data",new Gson().toJson(requestList.get(position))));
                    }else if(requestList.get(position).getBookigStatus().equalsIgnoreCase("PROCESSING") || requestList.get(position).getBookigStatus().equalsIgnoreCase("PICKUP"))
                    {
                        context.startActivity(new Intent(context, TripDetail.class).putExtra("data",new Gson().toJson(requestList.get(position))));
                    }
                }
                );
    }
    @Override
    public int getItemCount() {
        return requestList.size();
    }
    public class SelectTimeViewHolder extends RecyclerView.ViewHolder {
        public SelectTimeViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
