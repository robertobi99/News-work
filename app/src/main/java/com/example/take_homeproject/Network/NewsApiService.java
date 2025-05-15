package com.example.take_homeproject.network;

import com.example.take_homeproject.models.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApiService {
    @GET("search")
    Call<NewsResponse> getNews(
            @Query("q") String query,
            @Query("max") int maxResults,
            @Query("apikey") String apiKey
    );
}