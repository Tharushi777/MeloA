package lk.javainstitute.melo.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.firebase.database.core.Context;

import lk.javainstitute.melo.fragments.HomeFragment;

public class SliderAdapter extends FragmentStateAdapter {


    public SliderAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
       switch (position){
           case 0:
               return  new HomeFragment();
           default:
               return new HomeFragment();
       }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
