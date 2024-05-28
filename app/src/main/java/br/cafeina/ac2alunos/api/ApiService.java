package br.cafeina.ac2alunos.api;

import br.cafeina.ac2alunos.models.Aluno;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface ApiService {
    @GET("Aluno")
    Call<List<Aluno>> getAlunos();

    @POST("Aluno")
    Call<Aluno> createAluno(@Body Aluno aluno);
}
