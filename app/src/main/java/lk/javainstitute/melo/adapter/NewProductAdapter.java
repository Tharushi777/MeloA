package lk.javainstitute.melo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.List;

import lk.javainstitute.melo.R;
import lk.javainstitute.melo.activities.DetailActivity;
import lk.javainstitute.melo.model.NewProductModel;
import lk.javainstitute.melo.model.VideoModel;

public class NewProductAdapter  extends RecyclerView.Adapter<NewProductAdapter.ViewHolder> {

  private Context context;
  private List<NewProductModel> list;
  AdapterView.OnItemClickListener onItemClickListener;

    public NewProductAdapter(Context context, List<NewProductModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public NewProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.new_products,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewProductAdapter.ViewHolder holder, int position) {

        Glide.with(context).load(list.get(position).getImg_url()).into(holder.newImg);
        holder.newname.setText(list.get(position).getName());
        holder.newPrice.setText(String.valueOf(list.get(position).getPrice()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(context, DetailActivity.class);
                intent.putExtra("detailed",list.get(position));
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView newImg;
        TextView newname,newPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            newImg= itemView.findViewById(R.id.Po_img);
            newname= itemView.findViewById(R.id.po_product_name);
            newPrice= itemView.findViewById(R.id.ponew_price);



        }

    }
}
