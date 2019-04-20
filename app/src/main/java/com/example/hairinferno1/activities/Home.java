package com.example.hairinferno1.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.hairinferno1.fragments.FeedFragment;
import com.example.hairinferno1.R;

import com.example.hairinferno1.fragments.Homefragment;
import com.example.hairinferno1.R;

public class Home extends AppCompatActivity {

   private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNavigationView = findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.action_item1:
                                selectedFragment = Homefragment.newInstance();
                                break;
                            case R.id.action_item2:
                                selectedFragment = FeedFragment.newInstance();
                                break;
                            case R.id.action_item3:
                                selectedFragment = FeedFragment.newInstance();
                                break;

                            case R.id.action_item4:
                                selectedFragment = FeedFragment.newInstance();
                                break;
                            case R.id.action_item5:
                                selectedFragment = FeedFragment.newInstance();
                                break;

//
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });

        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, Homefragment.newInstance());
        transaction.commit();



    }

//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//
//                if (tab.getPosition() == 0) {
//                    viewPager.setCurrentItem(0);
//
//                }
//
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
//
//        setTabs();
//
//    }
//
//    private void tabSelectedListener() {
//
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//    }
//
//    private void adaptorsetViewpager() {
//        pagerViewAdaptor = new PagerViewAdaptor(getSupportFragmentManager(), tabLayout.getTabCount());
//        viewPager.setAdapter(pagerViewAdaptor);
//
//    }
//
//
//    private void setTabs() {
//        TextView tv1 = (TextView) LayoutInflater.from(Home.this).inflate(R.layout.custom_tab, null);
//        tv1.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_home, 0, 0);
//        //tv1.setText("Jobs");
//        tabLayout.getTabAt(0).setCustomView(tv1);
//        tabLayout.addTab(tabLayout.newTab().setText("Tab1"));
//
//        TextView tv2 = (TextView) LayoutInflater.from(Home.this).inflate(R.layout.custom_tab, null);
//        //tv2.setText("Jobs");
//        tv2.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_feeds, 0, 0);
//        tv2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(Home.this, "Work under progress", Toast.LENGTH_SHORT).show();
//            }
//        });
//        tabLayout.getTabAt(1).setCustomView(tv2);
//
//
//
//
//
//
//
//
//
//        TextView tv3 = (TextView) LayoutInflater.from(Home.this).inflate(R.layout.custom_tab, null);
//       // tv3.setText("Teams");
//        tv3.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_chat, 0, 0);
//        tv3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(Home.this, "Work under progress", Toast.LENGTH_SHORT).show();
//            }
//        });
//        tabLayout.getTabAt(3).setCustomView(tv3);
//
//
//        TextView tv4 = (TextView) LayoutInflater.from(Home.this).inflate(R.layout.custom_tab, null);
//       // tv4.setText("More");
//        tv4.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_profile, 0, 0);
//        tv4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(Home.this, "Work under progress", Toast.LENGTH_SHORT).show();
//            }
//        });
//        tabLayout.getTabAt(4).setCustomView(tv4);
//
//
////        tabLayout.getTabAt(0).setIcon(ICONS[0]);
////        tabLayout.getTabAt(1).setIcon(ICONS[1]);
////        tabLayout.getTabAt(2).setIcon(ICONS[2]);
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
//
//
//    }

}
