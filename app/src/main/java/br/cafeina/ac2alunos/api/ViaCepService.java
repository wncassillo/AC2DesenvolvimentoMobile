package br.cafeina.ac2alunos.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViaCepService {
    private static final String BASE_URL = "https://viacep.com.br/";

    public static ViaCepApi create() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(ViaCepApi.class);
    }
}
