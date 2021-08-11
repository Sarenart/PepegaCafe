package com.cyberburyatenterprise.pepegacafe.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.activity.OnBackPressedCallback;
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
    private FragmentManager parentFragmentManager;
    private FragmentManager fragmentManager;
    private boolean hasOrientationChanged;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       /* OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                getActivity().finishAndRemoveTask();
                Log.d("State", "Back pressed");
            }
        };
        getActivity().getOnBackPressedDispatcher().addCallback(this,callback);*/
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
    }

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



        return root;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hasOrientationChanged = savedInstanceState != null;
        binding.SearchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    if(!hasOrientationChanged) {
                        NavDirections toFoodSearchFragment = FoodCategoryFragmentDirections
                                .actionFoodCategoryFragmentToFoodSearchFragment();
                        navController.navigate(toFoodSearchFragment);
                    }
                    hasOrientationChanged = false;
                }

                else{
                    if(!hasOrientationChanged) {
                        NavDirections toFoodCategoryFragment = FoodSearchFragmentDirections
                                .actionFoodSearchFragmentToFoodCategoryFragment();
                        navController.navigate(toFoodCategoryFragment);
                    }
                    hasOrientationChanged = false;
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
                if(!newText.equals("")) {
                    sharedViewModel.updateMealsByQuery(newText);
                    Log.d("Search", newText);
                }
                else sharedViewModel.clearMealsByQuery();
                return true;

            }
        });

        parentFragmentManager = getParentFragmentManager();
        NavHostFragment nhf =(NavHostFragment) parentFragmentManager.findFragmentById(R.id.mainScreenContainerView);
        assert nhf != null;
        NavController nvctr = nhf.getNavController();
        NavDirections tosmw = HomeFragmentDirections.actionNavigationHomeToGetStartedFragment2();
        nvctr.navigate(tosmw);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}