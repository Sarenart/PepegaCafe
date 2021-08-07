package com.cyberburyatenterprise.pepegacafe.view;

import android.os.Bundle;

import com.cyberburyatenterprise.pepegacafe.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.cyberburyatenterprise.pepegacafe.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private NavController navController;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.BottomNavigationView);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home,
                R.id.navigation_like,
                R.id.navigation_person,
                R.id.navigation_timer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.mainScreenContainerView);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.BottomNavigationView, navController);
    }

}