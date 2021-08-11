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

    private final Repository repository;
    private MutableLiveData<List<String>> categories = new MutableLiveData<>();
    //private MutableLiveData<List<Meal>> MealsByCategory = new MutableLiveData<>();
    private MutableLiveData<String> searchQuery = new MutableLiveData<>();
    private MutableLiveData<String> chosenCategory = new MutableLiveData<>();


    public SharedViewModel(Application application) {
        super(application);
        repository = Repository.getInstance(application);
    }

    public LiveData<List<String>> getCategories() {
        return repository.getCategories();
        //return categories;
    }

    public void updateCategories(){
        repository.updateCategories();
    }

    public LiveData<List<Meal>> getMealsByCategory() {
        return repository.getMealsByCategory();

    }

    public void updateMealsByCategory(String category){
        repository.updateMealsByCategory(category);
    }

    public LiveData<List<Meal>> getMealsByQuery() {
        return repository.getMealsByQuery();

    }

    public void updateMealsByQuery(String query){
        repository.updateMealsByQuery(query);
    }

    public LiveData<String> getChosenCategory(){
        return chosenCategory;
    }

    public void setChosenCategory(String category){
        chosenCategory.setValue(category);
    }


    public void clearMealsByCategory(){
        repository.clearMealsByCategory();
    }
    public void clearCategories(){
        repository.clearCategories();
    }
    public void clearMealsByQuery(){
        repository.clearMealsByQuery();
    }
}