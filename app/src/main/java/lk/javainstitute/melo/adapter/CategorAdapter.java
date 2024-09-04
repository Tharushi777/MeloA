package lk.javainstitute.melo.adapter;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ViewTarget;
import android.content.Context;

import java.util.List;

import lk.javainstitute.melo.R;
import lk.javainstitute.melo.activities.ShowAllActivity;
import lk.javainstitute.melo.model.CategoryModel;

public class CategorAdapter extends RecyclerView.Adapter<CategorAdapter.ViewHolder> {
    private Context context;
    List<CategoryModel>list;

    public CategorAdapter(Context context, List<CategoryModel>list){
        this.context= context;
        this.list=list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


       Glide.with(context).load(list.get(position).getImg_url()).into(holder.catImg);
        holder.catname.setText(list.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context, ShowAllActivity.class);
                intent.putExtra("type",list.get(position).getType());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView catImg;
        TextView catname;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            catImg= itemView.findViewById(R.id.cat_img);
            catname= itemView.findViewById(R.id.cat_name);
        }
    }
}
