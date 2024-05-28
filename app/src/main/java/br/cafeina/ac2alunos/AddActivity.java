package br.cafeina.ac2alunos;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import br.cafeina.ac2alunos.api.ApiService;
import br.cafeina.ac2alunos.api.MockApiService;
import br.cafeina.ac2alunos.api.ViaCepApi;
import br.cafeina.ac2alunos.api.ViaCepService;
import br.cafeina.ac2alunos.models.Aluno;
import br.cafeina.ac2alunos.models.ViaCepEndereco;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddActivity extends AppCompatActivity {
    private EditText editNome, editRa, editCep, editLogradouro, editComplemento, editBairro, editCidade, editUf;
    private FloatingActionButton buttonBuscarCep;
    private Button buttonSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        editNome = (EditText)findViewById(R.id.editNome);
        editRa = (EditText)findViewById(R.id.editRa);
        editCep = (EditText)findViewById(R.id.editCep);
        editLogradouro = (EditText)findViewById(R.id.editLogradouro);
        editComplemento = (EditText)findViewById(R.id.editComplemento);
        editBairro = (EditText)findViewById(R.id.editBairro);
        editCidade = (EditText)findViewById(R.id.editCidade);
        editUf = (EditText)findViewById(R.id.editUf);
        buttonSalvar = (Button)findViewById(R.id.buttonSalvar);

        buttonSalvar.setOnClickListener(v ->{
            String nome = editNome.getText().toString();
            int ra = Integer.valueOf(editRa.getText().toString());
            String cep = editCep.getText().toString();
            String logradouro = editLogradouro.getText().toString();
            String complemento = editComplemento.getText().toString();
            String bairro = editNome.getText().toString();
            String cidade = editCidade.getText().toString();
            String uf = editUf.getText().toString();

            Aluno aluno = new Aluno(0,ra,nome,cep,logradouro,complemento,bairro,cidade,uf);

            enviarAluno(aluno);
        });

        buttonBuscarCep = (FloatingActionButton)findViewById(R.id.buttonBuscarCep);
        buttonBuscarCep.setOnClickListener(v -> {
            String cep = editCep.getText().toString().trim();
            Toast.makeText(AddActivity.this, cep, Toast.LENGTH_SHORT).show();
            if (!cep.isEmpty()){
                buscarCep(cep);
            } else {
                Toast.makeText(AddActivity.this, "Por favor, insira um CEP válido.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void buscarCep(String cep) {
        ViaCepApi viaCepApi = ViaCepService.create();
        viaCepApi.getCepInfo(cep).enqueue(new Callback<ViaCepEndereco>() {
            @Override
            public void onResponse(Call<ViaCepEndereco> call, Response<ViaCepEndereco> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ViaCepEndereco cepInfo = response.body();
                    editLogradouro.setText(cepInfo.getLogradouro());
                    editComplemento.setText(cepInfo.getComplemento());
                    editBairro.setText(cepInfo.getBairro());
                    editCidade.setText(cepInfo.getLocalidade());
                    editUf.setText(cepInfo.getUf());
                } else {
                    Log.e(TAG, "Erro na resposta: " + response.message());
                    Toast.makeText(AddActivity.this, "CEP não encontrado.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ViaCepEndereco> call, Throwable t) {
                Log.e(TAG, "Erro ao buscar CEP: " + t.getMessage(), t);
                Toast.makeText(AddActivity.this, "Erro ao buscar CEP.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void enviarAluno(Aluno aluno){
        ApiService apiService = MockApiService.create();
        apiService.createAluno(aluno).enqueue(new Callback<Aluno>() {
            @Override
            public void onResponse(Call<Aluno> call, Response<Aluno> response) {
                if (response.isSuccessful()){
                    Toast.makeText(AddActivity.this, "Aluno Salvo!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.putExtra("aluno_adicionado", true);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(AddActivity.this, "Houve um erro ao salvar aluno!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Aluno> call, Throwable t) {
                Toast.makeText(AddActivity.this, "Não foi possível acessar o servidor", Toast.LENGTH_SHORT).show();

            }
        });
    }
}