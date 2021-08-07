package com.cyberburyatenterprise.pepegacafe.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyberburyatenterprise.pepegacafe.R;
import com.cyberburyatenterprise.pepegacafe.adapters.MealRecyclerViewAdapter;
import com.cyberburyatenterprise.pepegacafe.databinding.FragmentFoodCategoryBinding;
import com.cyberburyatenterprise.pepegacafe.databinding.FragmentFoodSearchBinding;
import com.cyberburyatenterprise.pepegacafe.model.models.Meal;
import com.cyberburyatenterprise.pepegacafe.viewmodel.SharedViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class FoodSearchFragment extends Fragment {

    private SharedViewModel sharedViewModel;
    private MealRecyclerViewAdapter mealRecyclerViewAdapter;
    private FragmentFoodSearchBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_food_search, container, false);
        setSearchList();
        return binding.getRoot();
        //return inflater.inflate(R.layout.fragment_food_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        assert getParentFragment() != null;
        sharedViewModel = new ViewModelProvider(getParentFragment()).get(SharedViewModel.class);

        sharedViewModel.getMealsByQuery().observe(getViewLifecycleOwner(), meals -> {
            mealRecyclerViewAdapter.setMealArrayList((ArrayList<Meal>) meals);
        });
    }

    private void setSearchList(){
        RecyclerView mealRecyclerView = binding.searchRecyclerView;
        mealRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 4));
        mealRecyclerView.setHasFixedSize(true);

        mealRecyclerViewAdapter = new MealRecyclerViewAdapter();
        mealRecyclerView.setAdapter(mealRecyclerViewAdapter);

        mealRecyclerViewAdapter.setOnItemClickListener((meal ->{
            Log.d("Click", meal.getStrMeal().toString());}));
    }
}