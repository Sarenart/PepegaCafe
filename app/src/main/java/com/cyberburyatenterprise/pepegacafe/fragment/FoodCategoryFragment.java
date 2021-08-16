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
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;

import com.cyberburyatenterprise.pepegacafe.GridRecyclerView;
import com.cyberburyatenterprise.pepegacafe.R;
import com.cyberburyatenterprise.pepegacafe.adapters.MealRecyclerViewAdapter;
import com.cyberburyatenterprise.pepegacafe.databinding.FragmentFoodCategoryBinding;
import com.cyberburyatenterprise.pepegacafe.model.models.Meal;
import com.cyberburyatenterprise.pepegacafe.viewmodel.SharedViewModel;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class FoodCategoryFragment extends BaseFragment {

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

        binding.foodTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                getSharedViewModel().setChosenCategory(Objects.requireNonNull(tab.getText()).toString());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

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

        binding.foodTabLayout.clearOnTabSelectedListeners();

    }

    @Override
    public void onStop() {
        super.onStop();
        getSharedViewModel().getCategories().removeObservers(getViewLifecycleOwner());
        getSharedViewModel().getChosenCategory().removeObservers(getViewLifecycleOwner());
        getSharedViewModel().getMealsByCategory().removeObservers(getViewLifecycleOwner());
        getSharedViewModel().clearMealsByCategory();
        getSharedViewModel().clearCategories();
        binding.foodTabLayout.clearOnTabSelectedListeners();
        binding.foodTabLayout.removeAllTabs();
    }

    private void setMealList() {
        GridRecyclerView mealRecyclerView = binding.mealRecyclerView;
        mealRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), getResources().getInteger(R.integer.recyclerview_column_number)));
        LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(requireContext(),R.anim.layout_animation);

        mealRecyclerViewAdapter = new MealRecyclerViewAdapter();
        mealRecyclerView.setLayoutAnimation(animationController);
        mealRecyclerView.setAdapter(mealRecyclerViewAdapter);

        mealRecyclerViewAdapter.setOnItemClickListener((meal ->{
            Log.d("Click", meal.getStrMeal());}));
    }


}