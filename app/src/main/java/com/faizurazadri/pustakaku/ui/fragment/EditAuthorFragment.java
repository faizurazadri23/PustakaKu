package com.faizurazadri.pustakaku.ui.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.faizurazadri.pustakaku.R;
import com.faizurazadri.pustakaku.api.ApiClient;
import com.faizurazadri.pustakaku.api.ApiService;
import com.faizurazadri.pustakaku.databinding.FragmentEditAuthorBinding;
import com.faizurazadri.pustakaku.model.AuthorModel;
import com.faizurazadri.pustakaku.model.PostPutDelAuthor;
import com.faizurazadri.pustakaku.ui.home.HomeActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditAuthorFragment extends Fragment {

    FragmentEditAuthorBinding editAuthorBinding;
    private AuthorModel authorModel;
    private int idAuthor;
    private String nama,email,telp,alamat;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        editAuthorBinding = FragmentEditAuthorBinding.inflate(inflater, container, false);
        return editAuthorBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getActivity().getIntent().getExtras();

        progressDialog = new ProgressDialog(getActivity());

        authorModel = bundle.getParcelable("DATA");

        editAuthorBinding.namaPengarang.setText(authorModel.getNama_pengarang());
        editAuthorBinding.email.setText(authorModel.getEmail());
        editAuthorBinding.notelpn.setText(authorModel.getNotelp());
        editAuthorBinding.alamat.setText(authorModel.getAlamat());

        editAuthorBinding.btnUpdate.setOnClickListener(v -> {
            idAuthor = authorModel.getId_pengarang();
            nama = editAuthorBinding.namaPengarang.getText().toString();
            email = editAuthorBinding.email.getText().toString();
            telp = editAuthorBinding.notelpn.getText().toString();
            alamat = editAuthorBinding.alamat.getText().toString();

            if (nama.isEmpty()){
                editAuthorBinding.namaPengarang.setError(getResources().getString(R.string.wajib));
                editAuthorBinding.namaPengarang.requestFocus();
                return;
            }

            if (email.isEmpty()){
                editAuthorBinding.email.setError(getResources().getString(R.string.wajib));
                editAuthorBinding.email.requestFocus();
                return;
            }

            if (telp.isEmpty()){
                editAuthorBinding.notelpn.setError(getResources().getString(R.string.wajib));
                editAuthorBinding.notelpn.requestFocus();
                return;
            }

            if (alamat.isEmpty()){
                editAuthorBinding.alamat.setError(getResources().getString(R.string.wajib));
                editAuthorBinding.alamat.requestFocus();
                return;
            }

            progressDialog.setTitle(getResources().getString(R.string.onprogress_update));
            progressDialog.setMessage(getResources().getString(R.string.please_wait));
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(true);
            progressDialog.show();

            updateData();

        });
    }

    private void updateData(){
        Call<PostPutDelAuthor> putAuthorCall = ApiClient.getApiInterface().putAuthor(idAuthor, nama, email, telp, alamat);
        putAuthorCall.enqueue(new Callback<PostPutDelAuthor>() {
            @Override
            public void onResponse(Call<PostPutDelAuthor> call, Response<PostPutDelAuthor> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    if (!response.body().isError()){
                        Toast.makeText(getActivity(), response.body().getMessage() , Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getActivity(), HomeActivity.class));
                        getActivity().finish();
                    }else {
                        Toast.makeText(getActivity(), response.body().getMessage() , Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.permintaan_gagal) , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PostPutDelAuthor> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), getResources().getString(R.string.cek_koneksi) , Toast.LENGTH_LONG).show();
            }
        });
    }

}