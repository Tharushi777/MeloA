package lk.javainstitute.melo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import lk.javainstitute.melo.R;
import lk.javainstitute.melo.activities.CartActivity;
import lk.javainstitute.melo.model.MycartModel;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {

    Context context;
    List<MycartModel>list;
    int totalAmount= 0;

    public MyCartAdapter(Context context, List<MycartModel> list) {
        this.context= context;
        this.list=list;
    }

    @NonNull
    @Override
    public MyCartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cart_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyCartAdapter.ViewHolder holder, int position) {
        holder.date.setText(list.get(position).getCurrentDate());
        holder.time.setText(list.get(position).getCurrentTime());
        holder.price.setText(list.get(position).getProductPrice()+"Rs.");
        holder.name.setText(list.get(position).getProductName());
        holder.totalPrice.setText(String.valueOf(list.get(position).getTotalPrice())+"Rs.");
        holder.totalQuantity.setText(list.get(position).getTotalQunatity());


       //Total amount pass to Cart Activity

        totalAmount= totalAmount + list.get(position).getTotalPrice();
        Intent intent= new Intent("MyTotalAmount");
        intent.putExtra("totalAmount",totalAmount);

        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView name,price,date,time,totalQuantity,totalPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name= itemView.findViewById(R.id.product_name);
            price= itemView.findViewById(R.id.product_price);
            date= itemView.findViewById(R.id.current_date);
            time= itemView.findViewById(R.id.current_time);
            totalQuantity= itemView.findViewById(R.id.total_quantity);
            totalPrice= itemView.findViewById(R.id.total_price);
        }
    }
}
