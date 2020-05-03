package com.example.project_mobileprog_aa;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LozApi {

    @GET("fakeZeldaGamesAPI.json")
    Call<RestZeldaGamesResponse> getZeldaGamesResponse();
}
