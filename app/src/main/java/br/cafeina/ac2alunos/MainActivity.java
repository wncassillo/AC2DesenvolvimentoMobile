package br.cafeina.ac2alunos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import br.cafeina.ac2alunos.adapters.AlunoAdapter;
import br.cafeina.ac2alunos.api.ApiService;
import br.cafeina.ac2alunos.api.MockApiService;
import br.cafeina.ac2alunos.models.Aluno;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AlunoAdapter alunoAdapter;
    private List<Aluno> alunoList = new ArrayList<>();
    private FloatingActionButton buttonAddAluno;

    private static final int REQUEST_CODE_ADD_ALUNO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonAddAluno = findViewById(R.id.ButtonAddAluno);
        recyclerView = findViewById(R.id.recyclerViewAlunos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        alunoAdapter = new AlunoAdapter(alunoList);
        recyclerView.setAdapter(alunoAdapter);

        buttonAddAluno.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_ALUNO);
        });

        loadData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_ALUNO && resultCode == RESULT_OK) {
            boolean alunoAdicionado = data.getBooleanExtra("aluno_adicionado", false);
            if (alunoAdicionado) {
                loadData();
            }
        }
    }

    private void loadData() {
        ApiService apiService = MockApiService.create();
        apiService.getAlunos().enqueue(new Callback<List<Aluno>>() {
            @Override
            public void onResponse(Call<List<Aluno>> call, Response<List<Aluno>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    alunoList.clear();
                    alunoList.addAll(response.body());
                    alunoAdapter.notifyDataSetChanged();
                    for (Aluno aluno : response.body()) {
                        Log.d("MainActivity", "Aluno: " + aluno.getNome());
                    }
                } else {
                    Log.e("MainActivity", "Ocorreu um erro.");
                }
            }

            @Override
            public void onFailure(Call<List<Aluno>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Houve uma falha ao obter os dados.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
