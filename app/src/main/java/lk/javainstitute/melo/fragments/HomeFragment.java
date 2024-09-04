package lk.javainstitute.melo.fragments;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import lk.javainstitute.melo.R;
import lk.javainstitute.melo.activities.ShowAllActivity;
import lk.javainstitute.melo.adapter.CategorAdapter;
import lk.javainstitute.melo.adapter.NewProductAdapter;
import lk.javainstitute.melo.adapter.PopularProAdapter;
import lk.javainstitute.melo.model.CategoryModel;
import lk.javainstitute.melo.model.NewProductModel;
import lk.javainstitute.melo.model.PopulatProModel;


public class HomeFragment extends Fragment {

    TextView catShowAll,popularShowAll,newProductShowAll;

    RecyclerView catRecyclerview,newProducrRecyclerview,popularRecyclerview;
// category recyclerview
    CategorAdapter categoryAdapter;
    List<CategoryModel> categoryModelList;

    // new Product recyclerview
    NewProductAdapter newProductAdapter;
    List<NewProductModel>newProductModelList;
    // popular product recyclerview
    PopularProAdapter popularProAdapter;
    List<PopulatProModel> populatProModelList;
//FireStore
    FirebaseFirestore db;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_home, container, false);
        db= FirebaseFirestore.getInstance();

        catRecyclerview = root.findViewById(R.id.rec_category);
        newProducrRecyclerview= root.findViewById(R.id.new_product_rec);
        popularRecyclerview= root.findViewById(R.id.popular_rec);
        catShowAll= root.findViewById(R.id.category_see_all);
        popularShowAll= root.findViewById(R.id.popular_see_all);
        newProductShowAll= root.findViewById(R.id.category_see_all);




        catShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);

            }
        });

        newProductShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);

            }
        });
        popularShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);

            }
        });

        //image slider
        ImageSlider imageSlider= root.findViewById(R.id.image_slider);
        List<SlideModel> slideModels= new ArrayList<>();

        slideModels.add(new SlideModel("https://t3.ftcdn.net/jpg/01/85/03/88/360_F_185038897_yzi7ChFzajbXHc1cekwvqknu27bxP9BE.jpg","Discount on Music Items", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel("https://storage.needpix.com/rsynced_images/musical-background-2842924_1280.jpg","Discount on 70% OFF ", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel("https://images.squarespace-cdn.com/content/v1/59272c39893fc0394ec07ff4/1495798078092-2I76TJPWU4LDGQVN877V/music-studio-hd-wallpapers.jpg","Discount on Elrctronic Items", ScaleTypes.CENTER_CROP));

        imageSlider.setImageList(slideModels);

        //Categpry
        catRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        categoryModelList= new ArrayList<>();
        categoryAdapter= new CategorAdapter(getContext(),categoryModelList);
        catRecyclerview.setAdapter(categoryAdapter);

        db.collection("Category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for(QueryDocumentSnapshot document:task.getResult()){
                                CategoryModel categoryModel= document.toObject(CategoryModel.class);
                                categoryModelList.add(categoryModel);
                                categoryAdapter.notifyDataSetChanged();


                            }
                        }else{
                            Toast.makeText(getActivity(), ""+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        //// New Products

        newProducrRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        newProductModelList= new ArrayList<>();
        newProductAdapter= new NewProductAdapter(getContext(),newProductModelList);
        newProducrRecyclerview.setAdapter(newProductAdapter);

        db.collection("New_Product")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for(QueryDocumentSnapshot document:task.getResult()){
                                NewProductModel newProductModel= document.toObject(NewProductModel.class);
                                newProductModelList.add(newProductModel);
                                newProductAdapter.notifyDataSetChanged();

                            }
                        }else{
                            Toast.makeText(getActivity(), ""+task.getException(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
        //////popular product
        popularRecyclerview.setLayoutManager(new GridLayoutManager(getActivity(),2));
        populatProModelList= new ArrayList<>();
        popularProAdapter =new PopularProAdapter(getContext(),populatProModelList);
        popularRecyclerview.setAdapter(popularProAdapter);

        db.collection("PopularProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for(QueryDocumentSnapshot document:task.getResult()){
                                PopulatProModel populatProModel= document.toObject(PopulatProModel.class);
                                populatProModelList.add(populatProModel);
                               popularProAdapter.notifyDataSetChanged();

                            }
                        }else{
                            Toast.makeText(getActivity(), ""+task.getException(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });

        return  root;
    }
}