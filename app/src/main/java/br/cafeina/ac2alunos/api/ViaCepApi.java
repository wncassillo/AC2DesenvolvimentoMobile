package br.cafeina.ac2alunos.api;

import br.cafeina.ac2alunos.models.ViaCepEndereco;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ViaCepApi {
    @GET("ws/{cep}/json/")
    Call<ViaCepEndereco> getCepInfo(@Path("cep") String cep);
}
