package com.cyberburyatenterprise.pepegacafe.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.cyberburyatenterprise.pepegacafe.R;
import com.cyberburyatenterprise.pepegacafe.adapters.MealRecyclerViewAdapter;
import com.cyberburyatenterprise.pepegacafe.databinding.FragmentFoodCategoryBinding;
import com.cyberburyatenterprise.pepegacafe.model.models.Meal;
import com.cyberburyatenterprise.pepegacafe.viewmodel.SharedViewModel;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FoodCategoryFragment extends Fragment {

    private SharedViewModel sharedViewModel;
    private FragmentFoodCategoryBinding binding;
    private MealRecyclerViewAdapter mealRecyclerViewAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_food_category, container,false);
        setMealList();
        return binding.getRoot();
        //return inflater.inflate(R.layout.fragment_food_category, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mealRecyclerViewAdapter.clearMealArrayList();
        binding.foodTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //Log.d( "Tab", tab.getText().toString());
                sharedViewModel.setChosenCategory(tab.getText().toString());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                mealRecyclerViewAdapter.clearMealArrayList();

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        assert getParentFragment() != null;
        sharedViewModel = new ViewModelProvider(getParentFragment()).get(SharedViewModel.class);
        //sharedViewModel.clearCategories();

        sharedViewModel.updateCategories();
        sharedViewModel.getCategories().observe(getViewLifecycleOwner(), (categories)->{
            binding.foodTabLayout.removeAllTabs();
            for (String category:
                 categories) {
                TabLayout.Tab tab = binding.foodTabLayout.newTab().setText(category);
                binding.foodTabLayout.addTab(binding.foodTabLayout.newTab().setText(category));
                /*if(category == sharedViewModel.getChosenCategory().getValue())
                    binding.foodTabLayout.selectTab(tab);*/
            }
        });

        sharedViewModel.getChosenCategory().observe(getViewLifecycleOwner(),  (chosenCategory)->{
            mealRecyclerViewAdapter.clearMealArrayList();
            sharedViewModel.updateMealsByCategory(chosenCategory);
        });

        sharedViewModel.getMealsByCategory().observe(getViewLifecycleOwner(), meals -> {
            mealRecyclerViewAdapter.setMealArrayList((ArrayList<Meal>) meals);
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        sharedViewModel.getCategories().removeObservers(getViewLifecycleOwner());
        sharedViewModel.getChosenCategory().removeObservers(getViewLifecycleOwner());
        sharedViewModel.getMealsByCategory().removeObservers(getViewLifecycleOwner());
        //sharedViewModel.clearCategories();
        binding.foodTabLayout.clearOnTabSelectedListeners();
        //mealRecyclerViewAdapter.clearMealArrayList();
    }

    @Override
    public void onStop() {
        super.onStop();
        sharedViewModel.getCategories().removeObservers(getViewLifecycleOwner());
        sharedViewModel.getChosenCategory().removeObservers(getViewLifecycleOwner());
        sharedViewModel.getMealsByCategory().removeObservers(getViewLifecycleOwner());
        sharedViewModel.clearMealsByCategory();
        sharedViewModel.clearCategories();
        //sharedViewModel.clearCategories();
        binding.foodTabLayout.clearOnTabSelectedListeners();
        binding.foodTabLayout.removeAllTabs();
        //mealRecyclerViewAdapter.clearMealArrayList();

    }

    private void setMealList() {
        RecyclerView mealRecyclerView = binding.mealRecyclerView;
        mealRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), getResources().getInteger(R.integer.recyclerview_column_number)));
       // mealRecyclerView.setHasFixedSize(true);

        mealRecyclerViewAdapter = new MealRecyclerViewAdapter();
        mealRecyclerView.setAdapter(mealRecyclerViewAdapter);

        mealRecyclerViewAdapter.setOnItemClickListener((meal ->{
            Log.d("Click", meal.getStrMeal().toString());}));
    }

}