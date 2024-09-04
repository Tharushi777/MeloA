package lk.javainstitute.melo.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lk.javainstitute.melo.R;
import lk.javainstitute.melo.activities.MapActivity;
import lk.javainstitute.melo.activities.ProfileActivity;
import lk.javainstitute.melo.activities.ShowAllActivity;


public class ProfileFragment extends Fragment {

    AppCompatButton profile,map;





    public ProfileFragment() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root= inflater.inflate(R.layout.fragment_profile, container, false);
        profile= root.findViewById(R.id.profile_btn);
        map= root.findViewById(R.id.button5);

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(), MapActivity.class);
                startActivity(intent);
            }
        });



        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });
        return  root;
    }

}