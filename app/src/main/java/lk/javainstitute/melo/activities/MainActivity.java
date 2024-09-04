package lk.javainstitute.melo.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener;
import com.google.android.material.navigation.NavigationView;

import lk.javainstitute.melo.fragments.ProfileFragment;
import lk.javainstitute.melo.R;
//import lk.javainstitute.melo.adapter.SliderAdapter;
import lk.javainstitute.melo.fragments.HomeFragment;

public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener, OnItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private MaterialToolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    TextView name, temail;
    Button SignOutbtn;
    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";



//    SliderAdapter sliderAdapter;
    ViewPager2 viewPager2;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Fragment homefragment;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationview);
        toolbar = findViewById(R.id.toolbar);
        bottomNavigationView = findViewById(R.id.bottomnavigation);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);



// Check if the user is logged in
        boolean isLoggedIn = checkUserLoggedIn();



        // Update visibility of menu items based on login status
        Menu navMenu = navigationView.getMenu();
        navMenu.findItem(R.id.i2).setVisible(isLoggedIn); // Profile
        navMenu.findItem(R.id.i3).setVisible(isLoggedIn); // Orders
        navMenu.findItem(R.id.SideNavaboutus).setVisible(isLoggedIn); // Wishlist
        navMenu.findItem(R.id.SideNavSetting).setVisible(isLoggedIn); // Setting
        navMenu.findItem(R.id.SideNavSignout).setVisible(isLoggedIn); // Logout

        navMenu.findItem(R.id.SideNavLogin).setVisible(!isLoggedIn); // Login
        navMenu.findItem(R.id.SideNavLogout).setVisible(isLoggedIn); // Logout


      loadFragment(new HomeFragment());


        SignOutbtn = findViewById(R.id.signoutbtn);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);


        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String persongmail = acct.getEmail();
//            name.setText(personName);
//            email.setText(persongmail);
        }

        SignOutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkUserLoggedIn()){
                    signout();
                    updateLoginStatus(false);
                }else{
                    updateLoginStatus(true);
                    Intent intent= new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);

                }

            }
        });

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("MainActivity", "Toolbar Clicked");

                drawerLayout.open();
                temail = findViewById(R.id.useremail);


                String email = sharedPreferences.getString(KEY_EMAIL, null);
                Log.d("MainActivity", "Stored Email: " + email);
                if (email != null) {
                    temail.setText("tharushipiyumika12@gmail.com");
                    Log.d("MainActivity", "Setting Email in TextView: " + email);
                } else {

                    temail.setText("Sign in now");
                    Log.d("MainActivity", "User is not logged in");
                }





            }
        });
        navigationView.setNavigationItemSelectedListener(this);
        bottomNavigationView.setOnItemSelectedListener(item -> {
           int id=item.getItemId();
                if(id == R.id.bottomNavHome){
                    loadFragment(new HomeFragment());
                    Toast.makeText(MainActivity.this, "bottomNavHome ok", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.bottomNavShop) {
                    loadFragment(new HomeFragment());
                    Toast.makeText(MainActivity.this, "bottomNavShop ok", Toast.LENGTH_SHORT).show();
                }else if (id == R.id.bottomNavCart) {
                    startActivity(new Intent(MainActivity.this,CartActivity.class));
                    Toast.makeText(MainActivity.this, "bottomNavCart ok", Toast.LENGTH_SHORT).show();
                }else if (id == R.id.bottomNavAccount) {
                    loadFragment(new ProfileFragment());
                    Toast.makeText(MainActivity.this, "bottomNavAccount ok", Toast.LENGTH_SHORT).show();
                }

            return true;
        });
    }
    private boolean checkUserLoggedIn() {
        // Example: Check if there is a user email in shared preferences
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String email = sharedPreferences.getString(KEY_EMAIL, null);
        return email != null;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id= item.getItemId();
        if (id == R.id.bottomNavCart){
            Toast.makeText(MainActivity.this, "1 ok", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this,CartActivity.class));
            loadFragment(new HomeFragment());
        } else if (id == R.id.bottomNavHome) {
            Toast.makeText(MainActivity.this, "2 ok", Toast.LENGTH_SHORT).show();
            loadFragment(new HomeFragment());
        }else if (id == R.id.bottomNavShop) {
            Toast.makeText(MainActivity.this, "3 ok", Toast.LENGTH_SHORT).show();
        }else if (id == R.id.bottomNavAccount) {
            Toast.makeText(MainActivity.this, "4 ok", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        // Handle other menu items as needed

        if (id == R.id.SideNavLogin) {
            Intent intent= new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        } else if (id == R.id.SideNavLogout) {
            // Handle logout
            signout();
        }
        else if (id == R.id.SideNavaboutus) {
            Intent intent= new Intent(MainActivity.this, AboutusActivity.class);
            startActivity(intent);

        }
        else if (id == R.id.SideNavSetting) {
            // Handle logout

        }
        else if (id == R.id.i2) {
            // profile
            Intent intent= new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);

        }
        else if (id == R.id.i3) {
            // Handle logout

        }


        // Close the drawer after handling the selection
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }



    void signout(){
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete( Task<Void> task) {
                SharedPreferences.Editor editor= sharedPreferences.edit();
                editor.clear();
                editor.commit();
//                finish();
                Toast.makeText(MainActivity.this,"Log out successfully",Toast.LENGTH_SHORT).show();
                SignOutbtn.setText("Sign In");

            }
        });
    }
    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.contahome, fragment);
        transaction.commit();

    }
    private void updateLoginStatus(boolean isLoggedIn) {
        // You can use SharedPreferences or any other method to store the login status
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", isLoggedIn);
        editor.apply();

        // If logged in, update the useremail TextView in the toolbar
        if (isLoggedIn) {
            String email = sharedPreferences.getString(KEY_EMAIL, null);
            TextView useremailTextView = findViewById(R.id.useremail);
            if (email != null) {
                useremailTextView.setText(email);
            } else {
                useremailTextView.setText("Sign in now");
            }
        }
    }
}