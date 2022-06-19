package com.example.projetodum;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class perfilu extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfilu);

        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.smallsquare);

        tabLayout.setupWithViewPager(viewPager);

        ProfileTabsMY profileTabsMY = new ProfileTabsMY(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        profileTabsMY.addFragment(new MyExercises(),"ex");
        profileTabsMY.addFragment(new MyInfos(),"infos");
        profileTabsMY.addFragment(new CalculateThings(),"calc");
        viewPager.setAdapter(profileTabsMY);
}}