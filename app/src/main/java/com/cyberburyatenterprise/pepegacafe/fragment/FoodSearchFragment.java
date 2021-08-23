package com.cyberburyatenterprise.pepegacafe.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.cyberburyatenterprise.pepegacafe.R;
import com.cyberburyatenterprise.pepegacafe.adapters.MealRecyclerViewAdapter;
import com.cyberburyatenterprise.pepegacafe.databinding.FragmentFoodCategoryBinding;
import com.cyberburyatenterprise.pepegacafe.databinding.FragmentFoodSearchBinding;
import com.cyberburyatenterprise.pepegacafe.model.models.Meal;
import com.cyberburyatenterprise.pepegacafe.viewmodel.SharedViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class FoodSearchFragment extends BaseFragment {

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
        setSearchView();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        assert getParentFragment() != null;

        getSharedViewModel().getMealsByQuery().observe(getViewLifecycleOwner(), meals -> {
            mealRecyclerViewAdapter.setMealArrayList((ArrayList<Meal>) meals);
        });

    }

    private void setSearchList(){
        RecyclerView mealRecyclerView = binding.searchRecyclerView;
        mealRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), getResources().getInteger(R.integer.recyclerview_column_number)));
        mealRecyclerView.setHasFixedSize(true);

        mealRecyclerViewAdapter = new MealRecyclerViewAdapter();
        mealRecyclerView.setAdapter(mealRecyclerViewAdapter);

        mealRecyclerViewAdapter.setOnItemClickListener((meal ->{
            Log.d("Click", meal.getStrMeal().toString());}));
    }
    private void setSearchView(){
        binding.querySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(!newText.equals("")) {
                    getSharedViewModel().updateMealsByQuery(newText);
                    Log.d("Search", newText);
                }
                else getSharedViewModel().clearMealsByQuery();
                return true;

            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        binding.querySearchView.post(()->{binding.querySearchView.requestFocusFromTouch();});
    }
}