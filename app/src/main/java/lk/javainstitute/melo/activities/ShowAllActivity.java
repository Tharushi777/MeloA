package lk.javainstitute.melo.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import lk.javainstitute.melo.R;
import lk.javainstitute.melo.adapter.ShowAllAdapter;
import lk.javainstitute.melo.model.ShowAllModel;

public class ShowAllActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ShowAllAdapter showAllAdapter;
    List<ShowAllModel>  showAllModelList;

    FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all);

        String type= getIntent().getStringExtra("type");

        firestore= FirebaseFirestore.getInstance();
        recyclerView= findViewById(R.id.show_all_rec);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        showAllModelList= new ArrayList<>();
        showAllAdapter=new ShowAllAdapter(this,showAllModelList);
        recyclerView.setAdapter(showAllAdapter);



        if(type == null || type.isEmpty()){
            firestore.collection("New_Product")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for (DocumentSnapshot doc:task.getResult().getDocuments()){
                                    ShowAllModel showAllModel= doc.toObject(ShowAllModel.class);
                                    showAllModelList.add(showAllModel);
                                    showAllAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    });
        }
        if(type != null && type.equalsIgnoreCase("mens")){
            firestore.collection("New_Product").whereEqualTo("type","mens")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for (DocumentSnapshot doc:task.getResult().getDocuments()){
                                    ShowAllModel showAllModel= doc.toObject(ShowAllModel.class);
                                    showAllModelList.add(showAllModel);
                                    showAllAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    });
        }
        if(type != null && type.equalsIgnoreCase("woman")){
            firestore.collection("New_Product").whereEqualTo("type","woman")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for (DocumentSnapshot doc:task.getResult().getDocuments()){
                                    ShowAllModel showAllModel= doc.toObject(ShowAllModel.class);
                                    showAllModelList.add(showAllModel);
                                    showAllAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    });
        }
        if(type != null && type.equalsIgnoreCase("watch")){
            firestore.collection("New_Product").whereEqualTo("type","watch")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for (DocumentSnapshot doc:task.getResult().getDocuments()){
                                    ShowAllModel showAllModel= doc.toObject(ShowAllModel.class);
                                    showAllModelList.add(showAllModel);
                                    showAllAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    });
        }
        if(type != null && type.equalsIgnoreCase("camera")){
            firestore.collection("New_Product").whereEqualTo("type","camera")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for (DocumentSnapshot doc:task.getResult().getDocuments()){
                                    ShowAllModel showAllModel= doc.toObject(ShowAllModel.class);
                                    showAllModelList.add(showAllModel);
                                    showAllAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    });
        }
        if(type != null && type.equalsIgnoreCase("kids")){
            firestore.collection("New_Product").whereEqualTo("type","kids")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for (DocumentSnapshot doc:task.getResult().getDocuments()){
                                    ShowAllModel showAllModel= doc.toObject(ShowAllModel.class);
                                    showAllModelList.add(showAllModel);
                                    showAllAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    });
        }
        if(type != null && type.equalsIgnoreCase("shoes")){
            firestore.collection("New_Product").whereEqualTo("type","shoes")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for (DocumentSnapshot doc:task.getResult().getDocuments()){
                                    ShowAllModel showAllModel= doc.toObject(ShowAllModel.class);
                                    showAllModelList.add(showAllModel);
                                    showAllAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    });
        }
    }
}