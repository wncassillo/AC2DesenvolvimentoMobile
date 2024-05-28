package br.cafeina.ac2alunos.api;

import br.cafeina.ac2alunos.models.Aluno;
import retrofit2.Call;
import retrofit2.http.GET;
import java.util.List;

public interface ApiService {
    @GET("Aluno")
    Call<List<Aluno>> getAlunos();
}
