package com.example.project_mobileprog_aa.data;

import com.example.project_mobileprog_aa.presentation.model.RestZeldaGamesResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface LozApi {

    @GET("fakeZeldaGamesAPI.json")
    Call<RestZeldaGamesResponse> getZeldaGamesResponse();
}
