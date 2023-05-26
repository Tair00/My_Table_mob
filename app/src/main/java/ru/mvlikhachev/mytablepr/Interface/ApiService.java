package ru.mvlikhachev.mytablepr.Interface;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import ru.mvlikhachev.mytablepr.Domain.RestoranDomain;

public interface ApiService {
    @GET("/restaurant")
    Call<List<RestoranDomain>> getRestaurants();
}
