package com.example.eugene;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.eugene.Fragment.TambahanMenuMakanan;
import com.example.eugene.Fragment.TambahanMenuMinuman;
import com.example.eugene.Fragment.TambahanMenuTambahan;
import com.google.android.material.tabs.TabLayout;

public class TambahanMenu extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambahan_menu);

        tabLayout = findViewById(R.id.tablayoutTambahanMenu);
        viewPager = findViewById(R.id.viewPagerTambahanMenu);

        tabLayout.addTab(tabLayout.newTab().setText("Makanan"));
        tabLayout.addTab(tabLayout.newTab().setText("Minuman"));
        tabLayout.addTab(tabLayout.newTab().setText("Tambahan"));

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(),FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                switch (position){
                    case 0:
                        TambahanMenuMakanan tambahanMenuMakanan = new TambahanMenuMakanan();
                        return tambahanMenuMakanan;
                    case 1:
                        TambahanMenuMinuman tambahanMenuMinuman = new TambahanMenuMinuman();
                        return tambahanMenuMinuman;
                    case 2:
                        TambahanMenuTambahan tambahanMenuTambahan = new TambahanMenuTambahan();
                        return tambahanMenuTambahan;
                    default:
                        return null;
                }
            }

            @Override
            public int getCount() {
                return tabLayout.getTabCount();
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}