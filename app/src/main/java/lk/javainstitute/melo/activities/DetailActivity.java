package lk.javainstitute.melo.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import lk.javainstitute.melo.R;
import lk.javainstitute.melo.model.NewProductModel;
import lk.javainstitute.melo.model.PopulatProModel;
import lk.javainstitute.melo.model.ShowAllModel;

public class DetailActivity extends AppCompatActivity {

    ImageView detailImg;
    TextView rating,name,description,price,qty;
    Button addtocart,buy_now;
    ImageView addItems,removeItems;

    int totalQuantity=1;
    int totalPrice=0;

    YouTubePlayerView youTubePlayerView;

    //new Products
    NewProductModel newProductModel= null;;

    //popular Product
    PopulatProModel populatProModel= null;

    //ShowAll
    ShowAllModel showAllModel;

    FirebaseAuth auth;

    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        firestore= FirebaseFirestore.getInstance();
        auth= FirebaseAuth.getInstance();

        final Object obj= getIntent().getSerializableExtra("detailed");
        if(obj instanceof NewProductModel){
            newProductModel= (NewProductModel) obj;

        } else if (obj instanceof  PopulatProModel) {
            populatProModel=(PopulatProModel) obj;

        }else if (obj instanceof  ShowAllModel) {
            showAllModel=(ShowAllModel) obj;

        }
        qty= findViewById(R.id.qunatity);
        detailImg= findViewById(R.id.detailed_img);
        name= findViewById(R.id.detailed_name);
        rating= findViewById(R.id.rating);
        description= findViewById(R.id.detailed_desc);
        price= findViewById(R.id.detailed_price);

       youTubePlayerView= findViewById(R.id.youtube_player_view);
       getLifecycle().addObserver(youTubePlayerView);
       youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
           @Override
           public void onReady(@NonNull YouTubePlayer youTubePlayer) {
               String videoId="nDd5kIWtd9o";
               youTubePlayer.loadVideo(videoId,0);
           }
       });

        addtocart= findViewById(R.id.add_to_cart);
        buy_now= findViewById(R.id.buy_now);

        addItems= findViewById(R.id.add_item);
        removeItems= findViewById(R.id.remove_item);

        //New Products
        if(newProductModel != null){
            Glide.with(getApplicationContext()).load(newProductModel.getImg_url()).into(detailImg);
            name.setText(newProductModel.getName());
            rating.setText(newProductModel.getRating());
            description.setText(newProductModel.getDescription());
            price.setText(String.valueOf(newProductModel.getPrice()));
            totalPrice= newProductModel.getPrice() *totalQuantity;


        }
        //Popular Product
        if(populatProModel != null){
            Glide.with(getApplicationContext()).load(populatProModel.getImg_url()).into(detailImg);
            name.setText(populatProModel.getName());
            rating.setText(populatProModel.getRating());
            description.setText(populatProModel.getDescription());
            price.setText(String.valueOf(populatProModel.getPrice()));
            totalPrice= populatProModel.getPrice() *totalQuantity;


        }
        //showAll
        if(showAllModel != null){
            Glide.with(getApplicationContext()).load(showAllModel .getImg_url()).into(detailImg);
            name.setText(showAllModel .getName());
            rating.setText(showAllModel .getRating());
            description.setText(showAllModel .getDescription());
            price.setText(String.valueOf(showAllModel .getPrice()));
            totalPrice= showAllModel.getPrice() *totalQuantity;


        }
        //Buy Now
        buy_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent= new Intent(DetailActivity.this,AddressActivity.class);
               if(newProductModel !=null){
                   intent.putExtra("item",newProductModel);
               }
                if(populatProModel !=null){
                    intent.putExtra("item",populatProModel);
                }
                if(showAllModel !=null){
                    intent.putExtra("item",showAllModel);
                }
                startActivity(intent);
            }
        });
        //Add to cart
        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addtocart();
            }
        });

  addItems.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
          if(totalQuantity <10){
              totalQuantity++;
              qty.setText(String.valueOf(totalQuantity));

              if(newProductModel != null){
                  totalPrice= newProductModel.getPrice() *totalQuantity;
              }
              if(populatProModel != null){
                  totalPrice= populatProModel.getPrice() *totalQuantity;
              }
              if (showAllModel != null){
                  totalPrice= showAllModel.getPrice() *totalQuantity;
              }
          }

      }

     });

        removeItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(totalQuantity >1){
                    totalQuantity--;
                    qty.setText(String.valueOf(totalQuantity));
                }

            }

        });

    }

    private void addtocart() {
        String saveCurrentTime,saveCurrentDate;
        Calendar calForDate= Calendar.getInstance();

        SimpleDateFormat currentDate= new SimpleDateFormat("MM dd,yyyy");
        saveCurrentDate= currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime= new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime =currentTime.format(calForDate.getTime());

        final HashMap<String,Object> cartMap =new HashMap<>();
        cartMap.put("productName",name.getText().toString());
        cartMap.put("productPrice",price.getText().toString());
        cartMap.put("currentTime",saveCurrentTime);
        cartMap.put("currentDate",saveCurrentDate);
        cartMap.put("totalQuantity",qty.getText().toString());
        cartMap.put("totalPrice",totalPrice);
        Toast.makeText(DetailActivity.this,"ok",Toast.LENGTH_SHORT).show();
        firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("User").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {

                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(DetailActivity.this,"Added To A Cart",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

    }

}