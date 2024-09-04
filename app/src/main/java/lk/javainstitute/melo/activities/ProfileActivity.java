package lk.javainstitute.melo.activities;

import static com.github.dhaval2404.imagepicker.ImagePicker.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

import lk.javainstitute.melo.R;

public class ProfileActivity extends AppCompatActivity {

    EditText username,password,mobile,address;
    ImageView image,slect_img;
    TextView email;
    Button save,update;

    FirebaseFirestore firestore;
    FirebaseAuth auth;
    String url=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        username= findViewById(R.id.ad_username);
        password= findViewById(R.id.ad_password);
        mobile= findViewById(R.id.ad_mobile);
        address= findViewById(R.id.ad_address);
        email= findViewById(R.id.profile_email);
        save= findViewById(R.id.profile_save);

        slect_img= findViewById(R.id.imageView5);
        image= findViewById(R.id.imageView4);

        if (auth.getCurrentUser() != null) {
            String userEmail = auth.getCurrentUser().getEmail();
            email.setText(userEmail);
          loadExistingData(userEmail);
        }

        slect_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ImagePicker.with(ProfileActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String prouserName = username.getText().toString();
                String propassword = password.getText().toString();
                String promobile = mobile.getText().toString();
                String proaddress = address.getText().toString();
                String proemail = email.getText().toString();

                if (!prouserName.isEmpty() && !propassword.isEmpty() && !promobile.isEmpty() && !proaddress.isEmpty() && !proemail.isEmpty()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("prouserName", prouserName);
                    map.put("propassword", propassword);
                    map.put("promobile", promobile);
                    map.put("proaddress", proaddress);
                    map.put("proemail", proemail);
                    map.put("uri", url);

                    // Check if the document exists based on the user's email
                    firestore.collection("User")
                            .document(auth.getCurrentUser().getUid())
                            .collection("details")
                            .whereEqualTo("proemail", proemail)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        QuerySnapshot querySnapshot = task.getResult();
                                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                                            // Document exists, update the data
                                            firestore.collection("User")
                                                    .document(auth.getCurrentUser().getUid())
                                                    .collection("details")
                                                    .document(querySnapshot.getDocuments().get(0).getId())
                                                    .update(map)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> updateTask) {
                                                            if (updateTask.isSuccessful()) {
                                                                Toast.makeText(ProfileActivity.this, "Update Successful", Toast.LENGTH_SHORT).show();
                                                                loadDataIntoFields(querySnapshot.getDocuments().get(0).getId());
                                                            } else {
                                                                Toast.makeText(ProfileActivity.this, "Update Failed", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                        } else {
                                            // Document does not exist, add a new document
                                            firestore.collection("User")
                                                    .document(auth.getCurrentUser().getUid())
                                                    .collection("details")
                                                    .add(map)
                                                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<DocumentReference> addTask) {
                                                            if (addTask.isSuccessful()) {
                                                                Toast.makeText(ProfileActivity.this, "Save Successful", Toast.LENGTH_SHORT).show();
                                                                loadDataIntoFields(addTask.getResult().getId());
                                                            } else {
                                                                Toast.makeText(ProfileActivity.this, "Save Failed", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                        }
                                    }
                                }
                            });
                } else {
                    Toast.makeText(ProfileActivity.this, "Kindly Fill All Field", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void loadDataIntoFields(String documentId) {
        firestore.collection("User")
                .document(auth.getCurrentUser().getUid())
                .collection("details")
                .document(documentId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                // Populate text fields with retrieved data
                                username.setText(document.getString("prouserName"));
                                password.setText(document.getString("propassword"));
                                mobile.setText(document.getString("promobile"));
                                address.setText(document.getString("proaddress"));
                                email.setText(document.getString("proemail"));
                                // Assuming "uri" is a string field, you can load it into the ImageView accordingly
                                String uriString = document.getString("uri");
                                if (uriString != null) {
                                    Uri uri = Uri.parse(uriString);
                                    image.setImageURI(uri);
                                }
                            }
                        }
                    }
                });
    }
    private void loadExistingData(String userEmail) {
        firestore.collection("User")
                .document(auth.getCurrentUser().getUid())
                .collection("details")
                .whereEqualTo("proemail", userEmail)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            if (querySnapshot != null && !querySnapshot.isEmpty()) {
                                // Document exists, load data into text fields
                                DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                                username.setText(document.getString("prouserName"));

                                // Load email into the email field
                                email.setText(userEmail);

                                // Load password into the password field
                                password.setText(document.getString("propassword"));
                                mobile.setText(document.getString("promobile"));
                                address.setText(document.getString("proaddress"));

                                // Assuming "uri" is a string field, you can load it into the ImageView accordingly
                                String uriString = document.getString("uri");
                                if (uriString != null) {
                                    Uri uri = Uri.parse(uriString);
                                    image.setImageURI(uri);
                                }
                            }
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            url = uri.toString();
            image.setImageURI(uri);
        }
    }
}