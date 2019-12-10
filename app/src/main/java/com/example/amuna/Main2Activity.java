package com.example.amuna;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.amuna.LoginSplash.where;

public class Main2Activity extends AppCompatActivity {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private HomeFragment fragmentHome = new HomeFragment();
    private ChatFragment fragmentChat = new ChatFragment();
    private FavFragment fragmentFav = new FavFragment();
    //    private AccFragment fragmentAcc = new AccFragment();
    private AccTestFragment fragmentAcc = new AccTestFragment();
    public static List<Data> userList2;

    public static Activity main2activity;

    private ListAdapter adapter;
    final long INTERVAL_TIME = 1000;
    long previousTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        main2activity = Main2Activity.this;

        userList2 = new ArrayList<Data>();

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());

        if(where==4){
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.frameLayout, fragmentAcc).commitAllowingStateLoss();
            bottomNavigationView.setSelectedItemId(R.id.navigation_account);
            where=0;
        }
        else if(where==2){
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.frameLayout, fragmentChat).commitAllowingStateLoss();
            bottomNavigationView.setSelectedItemId(R.id.navigation_chat);
            where=0;
        }
        else{
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.frameLayout, fragmentHome).commitAllowingStateLoss();
        }


    }

    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch(menuItem.getItemId())
            {
                case R.id.navigation_account:
                    transaction.replace(R.id.frameLayout,fragmentAcc).commitAllowingStateLoss();
                    break;
                case R.id.navigation_home:
                    transaction.replace(R.id.frameLayout, fragmentHome).commitAllowingStateLoss();
                    break;
                case R.id.navigation_chat:
                    transaction.replace(R.id.frameLayout, fragmentChat).commitAllowingStateLoss();
                    break;
                case R.id.navigation_favorites:
                    transaction.replace(R.id.frameLayout, fragmentFav).commitAllowingStateLoss();
                    break;
            }
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();

        if((currentTime - previousTime) <= INTERVAL_TIME) {
            finishAffinity();
            System.runFinalization();
            System.exit(0);
        } else {
            previousTime = currentTime;
            Toast.makeText(this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }
}