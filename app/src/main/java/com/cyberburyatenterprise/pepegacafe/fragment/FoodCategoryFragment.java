package com.cyberburyatenterprise.pepegacafe.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyberburyatenterprise.pepegacafe.R;
import com.cyberburyatenterprise.pepegacafe.databinding.FragmentFoodCategoryBinding;
import com.cyberburyatenterprise.pepegacafe.viewmodel.SharedViewModel;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

public class FoodCategoryFragment extends Fragment {

    private SharedViewModel sharedViewModel;
    private FragmentFoodCategoryBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_food_category, container,false);
        return binding.getRoot();
        //return inflater.inflate(R.layout.fragment_food_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.FoodTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //Log.d( "Tab", tab.getText().toString());
                sharedViewModel.getMealsByCategories(tab.getText().toString());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        assert getParentFragment() != null;
        sharedViewModel = new ViewModelProvider(getParentFragment()).get(SharedViewModel.class);
        sharedViewModel.getCategories().observe(getViewLifecycleOwner(), (categories)->{
            for (String category:
                 categories) {
                binding.FoodTabLayout.addTab(binding.FoodTabLayout.newTab().setText(category));
            }

        } );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        sharedViewModel.getCategories().removeObservers(getViewLifecycleOwner());
    }
}