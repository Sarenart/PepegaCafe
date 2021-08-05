package com.cyberburyatenterprise.pepegacafe.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.cyberburyatenterprise.pepegacafe.databinding.FragmentHomeBinding;
import com.cyberburyatenterprise.pepegacafe.viewmodel.SharedViewModel;

public class HomeFragment extends Fragment {

    private SharedViewModel sharedViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sharedViewModel =
                new ViewModelProvider(this).get(SharedViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

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
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}