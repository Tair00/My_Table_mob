package ru.mvlikhachev.mytablepr.Adapter;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class ImgurApiClient {
    private static final String BASE_URL = "https://api.imgur.com/3/";
    private static ImgurApiService imgurApiService;

    public static ImgurApiService getClient() {
        if (imgurApiService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            imgurApiService = retrofit.create(ImgurApiService.class);
        }
        return imgurApiService;
    }

    public interface ImgurApiService {
        @GET("image/{imageId}")
        Call<ImgurResponse> getImgurImage(@Path("imageId") String imageId);
    }
}