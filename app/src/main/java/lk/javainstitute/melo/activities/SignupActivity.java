package lk.javainstitute.melo.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import lk.javainstitute.melo.R;

public class SignupActivity extends AppCompatActivity {

    EditText inputUsername,inputEmail,inputMobile,inputPassword;
    Button signup;
    String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String mobilePattern="^(?:7|0|(?:\\+94))[0-9]{9,10}$";

    ProgressDialog progressDialog;



    FirebaseAuth mAuth;
    FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Melo_FullScreen);
        setContentView(R.layout.activity_signup);

        TextView sign_up= findViewById(R.id.textView4);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        signup= findViewById(R.id.button);
        inputUsername= findViewById(R.id.editText);
        inputEmail= findViewById(R.id.editTextText2);
        inputMobile= findViewById(R.id.editTextMobile);
        inputPassword= findViewById(R.id.editTextTextPassword2);
        progressDialog= new ProgressDialog(this);
        mAuth= FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerforAuth();
            }
        });

    }
    private void PerforAuth() {
        String username=inputUsername.getText().toString();
        String email= inputEmail.getText().toString();
        String mobile=inputMobile.getText().toString();
        String password= inputPassword.getText().toString();

        if(username.isEmpty()) {
            inputEmail.setError(" Please Enter username ");
        } else if (!email.matches(emailPattern)) {
            inputEmail.setError("Enter Valid Email");
        } else if (!mobile.matches(mobilePattern)|| mobile.isEmpty()) {
            inputEmail.setError("mobile Invalid");
        } else if (password.isEmpty()|| password.length()<6) {
            inputEmail.setError("password Invalid");

        }else{
           progressDialog.setMessage("Please While Registration...");
           progressDialog.setTitle("Registration");
           progressDialog.setCanceledOnTouchOutside(false);
           progressDialog.show();

           mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
               @Override
               public void onComplete(@NonNull Task<AuthResult> task) {
                   if(task.isSuccessful()){
                       progressDialog.dismiss();

                       mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                           @Override
                           public void onComplete(@NonNull Task<Void> task) {
                               if(task.isSuccessful()){
                                   sendUserToNextAcrivity();
                                   Toast.makeText(SignupActivity.this,"Registration Successful.Please Verify your Email address",Toast.LENGTH_SHORT).show();
                               }else{
                                   Toast.makeText(SignupActivity.this,""+task.getException(),Toast.LENGTH_SHORT).show();
                               }

                           }
                       });


                   }else {
                       progressDialog.dismiss();
                       Toast.makeText(SignupActivity.this,""+task.getException(),Toast.LENGTH_SHORT).show();
                   }
               }
           });
        }
    }

    private void sendUserToNextAcrivity() {
        Intent intent= new Intent(SignupActivity.this,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}