package lk.javainstitute.melo.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import lk.javainstitute.melo.R;
import lk.javainstitute.melo.fragments.HomeFragment;

public class TestActivity extends AppCompatActivity {


    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);




                replaceFragment(new HomeFragment());
                





}

    private void replaceFragment(HomeFragment homeFragment) {
        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.teastFrame,homeFragment);
        fragmentTransaction.commit();
    }
    }