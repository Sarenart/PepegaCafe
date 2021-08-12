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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyberburyatenterprise.pepegacafe.R;
import com.cyberburyatenterprise.pepegacafe.databinding.FragmentGetStartedBinding;
import com.cyberburyatenterprise.pepegacafe.viewmodel.SharedViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class GetStartedFragment extends BaseFragment {

    //private SharedViewModel sharedViewModel;
    private FragmentGetStartedBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_get_started, container, false);


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        assert getParentFragment() != null;
        //sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        binding.gettingStartedButton.setOnClickListener(v -> {

            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

            NavHostFragment navHostFragment = (NavHostFragment) fragmentManager.findFragmentById(R.id.main_screen_container_view);

            assert navHostFragment != null;
            NavController navController = navHostFragment.getNavController();

            getSharedViewModel().setDataLoaded(true);

            NavDirections toFoodCategory = GetStartedFragmentDirections.actionGetStartedFragmentToFoodCategoryFragment();
            navController.navigate(toFoodCategory);
        });
    }
}