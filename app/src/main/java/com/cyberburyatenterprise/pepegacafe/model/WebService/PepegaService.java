package com.cyberburyatenterprise.pepegacafe.model.WebService;

import com.cyberburyatenterprise.pepegacafe.model.models.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PepegaService {

    @GET("list.php?c=list")
    Call<Category> getCategories();

    @GET("search.php")
    Call<Category> getSearchMeals(@Query("s") String query);

    @GET("filter.php")
    Call<Category> getCategoryMeals(@Query("c") String category);

}
