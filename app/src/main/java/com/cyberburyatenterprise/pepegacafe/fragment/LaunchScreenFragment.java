package com.cyberburyatenterprise.pepegacafe.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyberburyatenterprise.pepegacafe.R;

public class LaunchScreenFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_launch_screen, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

        NavHostFragment navHostFragment = (NavHostFragment) fragmentManager.findFragmentById(R.id.main_screen_container_view);

        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();

        new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                NavDirections toMainActivity = LaunchScreenFragmentDirections
                        .actionLaunchScreenFragmentToGetStartedFragment();
                navController.navigate(toMainActivity);
            }
        }.start();
    }
}