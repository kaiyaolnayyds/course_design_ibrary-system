package com.example.booktracker.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.example.booktracker.R;
import com.example.booktracker.fragment.UserFragment;
import com.example.booktracker.fragment.BookFragment;

import java.util.ArrayList;
import java.util.List;

public class NavActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new BookFragment());
        fragments.add(new UserFragment());

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction tran3 = getSupportFragmentManager().beginTransaction();
                tran3.replace(R.id.main, fragments.get(item.getOrder()));
                tran3.commit();
                return true;
            }
        });
        navigation.setSelectedItemId(R.id.home);
    }
}
