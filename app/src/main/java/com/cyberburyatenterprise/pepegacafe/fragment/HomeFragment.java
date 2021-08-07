package com.cyberburyatenterprise.pepegacafe.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.cyberburyatenterprise.pepegacafe.R;
import com.cyberburyatenterprise.pepegacafe.databinding.FragmentHomeBinding;
import com.cyberburyatenterprise.pepegacafe.viewmodel.SharedViewModel;

import org.jetbrains.annotations.NotNull;

public class HomeFragment extends Fragment {

    private SharedViewModel sharedViewModel;
    private FragmentHomeBinding binding;
    private NavController navController;
    private FragmentManager fragmentManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sharedViewModel =
                new ViewModelProvider(this).get(SharedViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        fragmentManager = getChildFragmentManager();

        NavHostFragment navHostFragment = (NavHostFragment) fragmentManager.findFragmentById(R.id.HomeContainerView);

        assert navHostFragment != null;
        navController = navHostFragment.getNavController();
        //binding.FoodTabLayout.addTab(binding.FoodTabLayout.newTab().setText("Welcome"));
        //binding.FoodTabLayout.addTab(binding.FoodTabLayout.newTab().setText("to"));
        //binding.FoodTabLayout.addTab(binding.FoodTabLayout.newTab().setText("the"));
        //binding.FoodTabLayout.addTab(binding.FoodTabLayout.newTab().setText("club,"));
        //binding.FoodTabLayout.addTab(binding.FoodTabLayout.newTab().setText("buddy"));

        //binding.FoodTabLayout.se
        /*final TextView textView = binding.textHome;
        sharedViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return root;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.SearchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    NavDirections toFoodSearchFragment = FoodCategoryFragmentDirections
                            .actionFoodCategoryFragmentToFoodSearchFragment();
                    navController.navigate(toFoodSearchFragment);
                }

                else{
                    NavDirections toFoodCategoryFragment = FoodSearchFragmentDirections
                            .actionFoodSearchFragmentToFoodCategoryFragment();
                    navController.navigate(toFoodCategoryFragment);
                }

                Log.d("Focus", hasFocus ? "Focused" : "Not focused");
            }
        });
        binding.SearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                sharedViewModel.updateMealsByQuery(newText);
                Log.d("Search", newText);
                return true;
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}