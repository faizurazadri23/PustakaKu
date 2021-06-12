package com.faizurazadri.pustakaku.ui.author;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.faizurazadri.pustakaku.R;
import com.faizurazadri.pustakaku.api.ApiClient;
import com.faizurazadri.pustakaku.databinding.ActivityAuthorBinding;
import com.faizurazadri.pustakaku.model.PostPutDelAuthor;
import com.faizurazadri.pustakaku.ui.book.BookActivity;
import com.faizurazadri.pustakaku.ui.home.HomeActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthorActivity extends AppCompatActivity {

    ActivityAuthorBinding authorBinding;
    private String nama_pengarang,email,notelp,alamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authorBinding = ActivityAuthorBinding.inflate(getLayoutInflater());
        setContentView(authorBinding.getRoot());

        authorBinding.btnSubmit.setOnClickListener(v -> {

            nama_pengarang = authorBinding.namaPengarang.getText().toString();
            email = authorBinding.email.getText().toString();
            notelp = authorBinding.notelpn.getText().toString();
            alamat = authorBinding.alamat.getText().toString();

            if (nama_pengarang.isEmpty()){
                authorBinding.namaPengarang.setError(getResources().getString(R.string.wajib));
                authorBinding.namaPengarang.requestFocus();
                return;
            }

            if (email.isEmpty()){
                authorBinding.email.setError(getResources().getString(R.string.wajib));
                authorBinding.email.requestFocus();
                return;
            }

            if (notelp.isEmpty()){
                authorBinding.notelpn.setError(getResources().getString(R.string.wajib));
                authorBinding.notelpn.requestFocus();
                return;
            }

            if (alamat.isEmpty()){
                authorBinding.alamat.setError(getResources().getString(R.string.wajib));
                authorBinding.alamat.requestFocus();
                return;
            }

            authorBinding.progressAuthor.setVisibility(View.VISIBLE);

            insertData();
        });

    }

    private void insertData(){
        Call<PostPutDelAuthor> postAuthor = ApiClient.getApiInterface().postAuthor(nama_pengarang, email, notelp, alamat);
        postAuthor.enqueue(new Callback<PostPutDelAuthor>() {
            @Override
            public void onResponse(Call<PostPutDelAuthor> call, Response<PostPutDelAuthor> response) {
                authorBinding.progressAuthor.setVisibility(View.GONE);
                if (response.isSuccessful()){
                    if (!response.body().isError()){
                        Toast.makeText(getApplicationContext(), response.body().getMessage() , Toast.LENGTH_LONG).show();
                        startActivity(new Intent(AuthorActivity.this, HomeActivity.class));
                        finish();
                    }else {
                        Toast.makeText(getApplicationContext(), response.body().getMessage() , Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.permintaan_gagal) , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PostPutDelAuthor> call, Throwable t) {
                authorBinding.progressAuthor.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.cek_koneksi) , Toast.LENGTH_LONG).show();
            }
        });
    }
}