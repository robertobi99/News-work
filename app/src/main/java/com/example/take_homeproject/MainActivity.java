package com.example.take_homeproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.take_homeproject.models.Article;
import com.example.take_homeproject.models.NewsResponse;
import com.example.take_homeproject.network.ApiClient;
import com.example.take_homeproject.network.NewsApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText searchInput;
    private Button searchButton;
    private RecyclerView recyclerView;
    private NewsAdapter adapter;

    private final String API_KEY = "bb04fd363677f576b98dde0b341b8d45"; // GNews API Key
    private final int MAX_RESULTS = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchInput = findViewById(R.id.searchInput);
        searchButton = findViewById(R.id.searchButton);
        recyclerView = findViewById(R.id.newsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initial fetch
        fetchNews("android");

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = searchInput.getText().toString().trim();
                if (!query.isEmpty()) {
                    fetchNews(query);
                } else {
                    Toast.makeText(MainActivity.this, "Enter a search keyword", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void fetchNews(String query) {
        NewsApiService apiService = ApiClient.getRetrofitInstance().create(NewsApiService.class);
        Call<NewsResponse> call = apiService.getNews(query, MAX_RESULTS, API_KEY);

        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Article> articles = response.body().getArticles();
                    adapter = new NewsAdapter(MainActivity.this, articles);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(MainActivity.this, "No results found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}