package com.cyberburyatenterprise.pepegacafe.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cyberburyatenterprise.pepegacafe.model.Repository;
import com.cyberburyatenterprise.pepegacafe.model.models.Meal;

import java.util.List;

public class SharedViewModel extends AndroidViewModel {

    private Repository repository;
    private MutableLiveData<List<String>> categories = new MutableLiveData<>();
    private MutableLiveData<List<Meal>> MealsByCategory = new MutableLiveData<>();
    private MutableLiveData<String> searchQuery = new MutableLiveData<>();


    public SharedViewModel(Application application) {
        super(application);
        repository = Repository.getInstance(application);
    }

    public LiveData<List<String>> getCategories() {
        return repository.getCategories();
        //return categories;
    }

    public LiveData<List<Meal>> getMealsByCategories(String category) {
        return repository.getMealsByCategory(category);
        //return categories;
    }


}