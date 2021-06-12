package com.faizurazadri.pustakaku.ui.book;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.faizurazadri.pustakaku.R;
import com.faizurazadri.pustakaku.api.ApiClient;
import com.faizurazadri.pustakaku.databinding.ActivityBookBinding;
import com.faizurazadri.pustakaku.model.GetAuthor;
import com.faizurazadri.pustakaku.model.PostPutDelBook;
import com.faizurazadri.pustakaku.ui.home.HomeActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookActivity extends AppCompatActivity {

    ActivityBookBinding bookBinding;
    private int id_pengarang;
    private String jumlah_halaman, judulbuku, isbn, tahun;
    private String listpengrang[] = {"Pilih Pengarang"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bookBinding = ActivityBookBinding.inflate(getLayoutInflater());
        setContentView(bookBinding.getRoot());


        getPengarang();

        bookBinding.btnSubmit.setOnClickListener(v -> {
            jumlah_halaman = bookBinding.jumlahHalaman.getText().toString();

            judulbuku = bookBinding.judulBuku.getText().toString();
            isbn = bookBinding.nomorIsbn.getText().toString();
            tahun = bookBinding.tahunTerbit.getText().toString();

            if (jumlah_halaman.isEmpty()) {
                bookBinding.jumlahHalaman.setError(getResources().getString(R.string.wajib));
                bookBinding.jumlahHalaman.requestFocus();
                return;
            }

            if (judulbuku.isEmpty()) {
                bookBinding.judulBuku.setError(getResources().getString(R.string.wajib));
                bookBinding.judulBuku.requestFocus();
                return;
            }

            if (isbn.isEmpty()) {
                bookBinding.nomorIsbn.setError(getResources().getString(R.string.wajib));
                bookBinding.nomorIsbn.requestFocus();
                return;
            }

            if (tahun.isEmpty()) {
                bookBinding.tahunTerbit.setError(getResources().getString(R.string.wajib));
                bookBinding.tahunTerbit.requestFocus();
                return;
            }

            if (bookBinding.idPengarang.equals(""))
            bookBinding.progressBook.setVisibility(View.VISIBLE);
            insertData();
        });
    }

    private void insertData() {
        Call<PostPutDelBook> postBook = ApiClient.getApiInterface().postBook(judulbuku, id_pengarang, Integer.parseInt(jumlah_halaman), isbn, tahun);
        postBook.enqueue(new Callback<PostPutDelBook>() {
            @Override
            public void onResponse(Call<PostPutDelBook> call, Response<PostPutDelBook> response) {
                bookBinding.progressBook.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (!response.body().isError()) {
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                        startActivity(new Intent(BookActivity.this, HomeActivity.class));
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.permintaan_gagal), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PostPutDelBook> call, Throwable t) {
                bookBinding.progressBook.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.cek_koneksi), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getPengarang() {
        Call<GetAuthor> getAuthorCall = ApiClient.getApiInterface().getDataAuthor();
        getAuthorCall.enqueue(new Callback<GetAuthor>() {
            @Override
            public void onResponse(Call<GetAuthor> call, Response<GetAuthor> response) {
                if (response.isSuccessful()) {
                    if (!response.body().isError()) {
                        List<String> listAuthor = new ArrayList<>();


                        for (int i = 0; i < response.body().getAuthorModels().size(); i++) {
                            listAuthor.add(response.body().getAuthorModels().get(i).getNama_pengarang());
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(BookActivity.this, R.layout.support_simple_spinner_dropdown_item, listAuthor);
                        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                        bookBinding.idPengarang.setAdapter(adapter);


                        bookBinding.idPengarang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                String selectedName = parent.getItemAtPosition(position).toString();
                                int idpengrang = response.body().getAuthorModels().get(position).getId_pengarang();

                                id_pengarang = idpengrang;

                                if (selectedName != null) {
                                    bookBinding.idPengarang.setEnabled(true);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }else {
                        defAuthor();
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.failed_getId) , Toast.LENGTH_LONG).show();
                    }

                }else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.permintaan_gagal) , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<GetAuthor> call, Throwable t) {

            }
        });
    }

    private void defAuthor(){
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listpengrang);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bookBinding.idPengarang.setAdapter(arrayAdapter);
    }
}