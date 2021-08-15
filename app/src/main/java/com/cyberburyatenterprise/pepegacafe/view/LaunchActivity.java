package com.cyberburyatenterprise.pepegacafe.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.View;

import com.cyberburyatenterprise.pepegacafe.R;
import com.cyberburyatenterprise.pepegacafe.databinding.ActivityLaunchBinding;
import com.cyberburyatenterprise.pepegacafe.fragment.FoodCategoryFragmentDirections;
import com.cyberburyatenterprise.pepegacafe.viewmodel.SharedViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;


public class LaunchActivity extends AppCompatActivity {

    private ActivityLaunchBinding binding;
    private FragmentManager fragmentManager;
    private NavController navController;
    private AppBarConfiguration appBarConfiguration;
    private SharedViewModel sharedViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLaunchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        Objects.requireNonNull(getSupportActionBar()).hide();

        fragmentManager = getSupportFragmentManager();

        NavHostFragment navHostFragment = (NavHostFragment) fragmentManager
                .findFragmentById(R.id.main_screen_container_view);

        assert navHostFragment != null;
        navController = navHostFragment.getNavController();

        appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph()).build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);


        BottomNavigationView navView = binding.bottomNavigationView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.home,
                R.id.favorite,
                R.id.profile,
                R.id.recent_orders)
                .build();
        navController = Navigation.findNavController(this, R.id.main_screen_container_view);
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);

        binding.bottomNavigationView.setVisibility(View.GONE);
        sharedViewModel.isDataLoaded().observe(this, aBoolean -> {
            if(aBoolean)
                binding.bottomNavigationView.setVisibility(View.VISIBLE);
        });
        NavDirections toLaunchScreen = FoodCategoryFragmentDirections
                .actionFoodCategoryFragmentToNavigation();
        navController.navigate(toLaunchScreen);
    }
}