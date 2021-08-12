package com.cyberburyatenterprise.pepegacafe.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
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

public class FoodCategoryFragment extends BaseFragment {

    //private SharedViewModel sharedViewModel;
    private FragmentFoodCategoryBinding binding;
    private MealRecyclerViewAdapter mealRecyclerViewAdapter;
    private NavController navController;

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

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment) fragmentManager.findFragmentById(R.id.main_screen_container_view);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();

        //sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        mealRecyclerViewAdapter.clearMealArrayList();

        binding.foodTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //Log.d( "Tab", tab.getText().toString());
                getSharedViewModel().setChosenCategory(tab.getText().toString());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                mealRecyclerViewAdapter.clearMealArrayList();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        getSharedViewModel().updateCategories();

        getSharedViewModel().getCategories().observe(getViewLifecycleOwner(), (categories)->{
            binding.foodTabLayout.removeAllTabs();
            for (String category:
                 categories) {
                binding.foodTabLayout.addTab(binding.foodTabLayout.newTab().setText(category));
            }
        });

        getSharedViewModel().getChosenCategory().observe(getViewLifecycleOwner(),  (chosenCategory)->{
            mealRecyclerViewAdapter.clearMealArrayList();
            getSharedViewModel().updateMealsByCategory(chosenCategory);
        });

        getSharedViewModel().getMealsByCategory().observe(getViewLifecycleOwner(), meals -> {
            mealRecyclerViewAdapter.setMealArrayList((ArrayList<Meal>) meals);
        });
        binding.categorySearchView.setOnQueryTextFocusChangeListener((v, hasFocus) -> {
            if(hasFocus){
                binding.categorySearchView.clearFocus();
                NavDirections toFoodSearch = FoodCategoryFragmentDirections.actionFoodCategoryFragmentToFoodSearchFragment();
                navController.navigate(toFoodSearch);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getSharedViewModel().getCategories().removeObservers(getViewLifecycleOwner());
        getSharedViewModel().getChosenCategory().removeObservers(getViewLifecycleOwner());
        getSharedViewModel().getMealsByCategory().removeObservers(getViewLifecycleOwner());
        getSharedViewModel().clearMealsByCategory();
        //sharedViewModel.clearCategories();
        binding.foodTabLayout.clearOnTabSelectedListeners();
        //mealRecyclerViewAdapter.clearMealArrayList();
    }

    @Override
    public void onStop() {
        super.onStop();
        getSharedViewModel().getCategories().removeObservers(getViewLifecycleOwner());
        getSharedViewModel().getChosenCategory().removeObservers(getViewLifecycleOwner());
        getSharedViewModel().getMealsByCategory().removeObservers(getViewLifecycleOwner());
        getSharedViewModel().clearMealsByCategory();
        getSharedViewModel().clearCategories();
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