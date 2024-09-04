package lk.javainstitute.melo.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import lk.javainstitute.melo.R;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getName();
    EditText inputEmail,inputPassword;
    Button signinbtn;
    String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageView googlebtn;

    FirebaseDatabase database;

    SharedPreferences sharedPreferences;
    private  static  final String SHARED_PREF_NAME="mypref";
    private static final String KEY_NAME="name";
    private static final String KEY_EMAIL="email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Melo_FullScreen);
        setContentView(R.layout.activity_login);

        TextView sign_in= findViewById(R.id.textView4);
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);

            }
        });


        signinbtn= findViewById(R.id.button);
        inputEmail= findViewById(R.id.editTextText2);
        inputPassword= findViewById(R.id.editTextMobile);
        progressDialog= new ProgressDialog(this);
        mAuth= FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        database= FirebaseDatabase.getInstance();

        googlebtn= findViewById(R.id.google_btn);
        gso= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc= GoogleSignIn.getClient(this,gso);

        sharedPreferences= getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        String email= sharedPreferences.getString(KEY_EMAIL,null);

        if(email!= null){
            signin();
        }


        googlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor= sharedPreferences.edit();
                editor.putString(KEY_EMAIL, inputEmail.getText().toString());
                editor.apply();
                signin();
            }
        });

        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                perforLogin();
            }
        });
    }

    private void perforLogin() {

        String email= inputEmail.getText().toString();

        String password= inputPassword.getText().toString();


        if (!email.matches(emailPattern)) {
            inputEmail.setError("Enter Valid Email");
        } else if (password.isEmpty()|| password.length()<6) {
            inputEmail.setError("password Invalid");

        }else{
            progressDialog.setMessage("Please Wait While Login...");
            progressDialog.setTitle("Login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

          mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
              @Override
              public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    if (mAuth.getCurrentUser().isEmailVerified()) {
                        sendUserToNextAcrivity();
                        Toast.makeText(LoginActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(LoginActivity.this,"Please verify your email",Toast.LENGTH_SHORT).show();

                    }


                   // updateUI(mAuth.getCurrentUser());


                }else {
                    progressDialog.dismiss();

                    Toast.makeText(LoginActivity.this,""+task.getException(),Toast.LENGTH_SHORT).show();
                }
              }
          });
        }
    }

    private void sendUserToNextAcrivity( ) {


        Intent intent= new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }



    void  signin(){
        Intent signInIntent= gsc.getSignInIntent();
        startActivityForResult(signInIntent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100){

            Task<GoogleSignInAccount> task= GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                Toast.makeText(getApplicationContext(),"okkk",Toast.LENGTH_SHORT).show();
                sendUserToNextAcrivity();

            }catch (ApiException e){
                Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
            }
            }

        }

}