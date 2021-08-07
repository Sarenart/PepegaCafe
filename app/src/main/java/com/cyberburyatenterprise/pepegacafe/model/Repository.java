package com.cyberburyatenterprise.pepegacafe.model;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.cyberburyatenterprise.pepegacafe.model.WebService.PepegaService;
import com.cyberburyatenterprise.pepegacafe.model.WebService.RetrofitInstance;
import com.cyberburyatenterprise.pepegacafe.model.models.Category;
import com.cyberburyatenterprise.pepegacafe.model.models.Meal;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    private static Repository instance;

    private ExecutorService executorService;
    private final PepegaService pepegaService;

    MutableLiveData<List<String>> categories = new MutableLiveData<>();
    MutableLiveData<List<Meal>> mealsByCategory = new MutableLiveData<>();
    MutableLiveData<List<Meal>> mealsByQuery = new MutableLiveData<>();
    // private ArrayList<Object> foodFromS

    public static Repository getInstance(Application application){
        if (instance == null){
            instance = new Repository(application);
        }
        return instance;
    }

    private Repository(Application application){
        pepegaService = RetrofitInstance.getService();
    }

    public MutableLiveData<List<String>> getCategories(){
        Call<Category> call = pepegaService.getCategories();
        call.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                Category categories = response.body();

                if(categories != null && categories.getMeals() != null){
                    List<String> categoryList = new ArrayList<String>();
                    for (Meal category:
                            categories.getMeals()) {
                        categoryList.add(category.getStrCategory());
                        Log.d("Category", category.getStrCategory());
                    }
                    Repository.this.categories.postValue(categoryList);
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                Log.d("FOOD", "Failure: " + t.getMessage());
            }
        });
        return Repository.this.categories;
    }


    public MutableLiveData<List<Meal>> getMealsByCategory(){

        return this.mealsByCategory;
    }

    public void updateMealsByCategory(String category){
        clearMealsByCategory();
        Call<Category> call = pepegaService.getCategoryMeals(category);
        call.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(@NotNull Call<Category> call, @NotNull Response<Category> response) {
                Category meals = response.body();

                if(meals != null && meals.getMeals() != null){
                    List<Meal> mealList = new ArrayList<Meal>();
                    for (Meal meal:
                            meals.getMeals()) {
                        mealList.add(meal);
                        Log.d("Meal", meal.getStrMeal() + ", "+ meal.getStrCategory());
                    }
                    Repository.this.mealsByCategory.postValue(mealList);
                }
            }

            @Override
            public void onFailure(@NotNull Call<Category> call, @NotNull Throwable t) {

            }
        });
    }


    public MutableLiveData<List<Meal>> getMealsByQuery(){
        return this.mealsByQuery;
    }

    public void updateMealsByQuery(String query){
        clearMealsByQuery();
        Call<Category> call = pepegaService.getSearchMeals(query);
        call.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(@NotNull Call<Category> call, @NotNull Response<Category> response) {
                Category meals = response.body();

                if(meals != null && meals.getMeals() != null){
                    List<Meal> mealList = new ArrayList<Meal>();
                    for (Meal meal:
                            meals.getMeals()) {
                        mealList.add(meal);
                        Log.d("Meal", meal.getStrMeal() + ", "+ meal.getStrCategory());
                    }
                Repository.this.mealsByQuery.postValue(mealList);
                }
            }

            @Override
            public void onFailure(@NotNull Call<Category> call, @NotNull Throwable t) {

            }
        });
    }

    public void clearCategories(){
        mealsByCategory.setValue(new ArrayList<>());
    }

    public void clearMealsByCategory(){
        mealsByCategory.setValue(new ArrayList<>());
    }

    public void clearMealsByQuery(){
        mealsByQuery.setValue(new ArrayList<>());
    }
}
