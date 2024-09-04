package lk.javainstitute.melo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import lk.javainstitute.melo.R;
import lk.javainstitute.melo.activities.DetailActivity;
import lk.javainstitute.melo.model.PopulatProModel;

public class PopularProAdapter extends RecyclerView.Adapter<PopularProAdapter.ViewHo1der> {
    private Context context;
    private List<PopulatProModel> populatProModelList;

    public PopularProAdapter(Context context, List<PopulatProModel> populatProModelList) {
        this.context = context;
        this.populatProModelList = populatProModelList;
    }

    @NonNull
    @Override
    public PopularProAdapter.ViewHo1der onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new ViewHo1der(LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_items,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull PopularProAdapter.ViewHo1der holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(populatProModelList.get(position).getImg_url()).into(holder.imageView);
        holder.name.setText(populatProModelList.get(position).getName());
        holder.price.setText(String.valueOf(populatProModelList.get(position).getPrice()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(context, DetailActivity.class);
                intent.putExtra("detailed",populatProModelList.get(position));
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return populatProModelList.size();
    }

    public class ViewHo1der extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name,price;
        public ViewHo1der(@NonNull View itemView) {
            super(itemView);
            imageView= itemView.findViewById(R.id.all_img);
            name= itemView.findViewById(R.id.all_product_name);
            price= itemView.findViewById(R.id.all_price);

        }
    }
}
