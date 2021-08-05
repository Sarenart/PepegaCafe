package com.cyberburyatenterprise.pepegacafe.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyberburyatenterprise.pepegacafe.R;
import com.cyberburyatenterprise.pepegacafe.databinding.FragmentFoodSearchBinding;


public class FoodSearchFragment extends Fragment {

    private FragmentFoodSearchBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_search, container, false);
    }
}