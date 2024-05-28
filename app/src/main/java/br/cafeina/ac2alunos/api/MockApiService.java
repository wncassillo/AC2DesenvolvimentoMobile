package br.cafeina.ac2alunos.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MockApiService {

    public static ApiService create() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://651224a0b8c6ce52b3955b6a.mockapi.io/api/v1/") // Use a URL válida de Mock API
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(ApiService.class);
    }
}

