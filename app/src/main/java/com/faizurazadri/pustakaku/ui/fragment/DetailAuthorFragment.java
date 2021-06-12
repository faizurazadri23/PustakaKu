package com.faizurazadri.pustakaku.ui.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.faizurazadri.pustakaku.R;
import com.faizurazadri.pustakaku.api.ApiClient;
import com.faizurazadri.pustakaku.databinding.FragmentDetailAuthorBinding;
import com.faizurazadri.pustakaku.model.AuthorModel;
import com.faizurazadri.pustakaku.model.PostPutDelAuthor;
import com.faizurazadri.pustakaku.ui.home.HomeActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailAuthorFragment extends Fragment {

    FragmentDetailAuthorBinding detailAuthorBinding;
    private AuthorModel authorModel;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        detailAuthorBinding = FragmentDetailAuthorBinding.inflate(inflater, container, false);
        return detailAuthorBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressDialog = new ProgressDialog(getActivity());

        authorModel = getActivity().getIntent().getParcelableExtra("DATA");

        if (((AppCompatActivity)getActivity()).getSupportActionBar()!=null){
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Detail " + authorModel.getNama_pengarang());
        }

        detailAuthorBinding.namaPengarang.setText(authorModel.getNama_pengarang());
        detailAuthorBinding.email.setText(authorModel.getEmail());
        detailAuthorBinding.notelpn.setText(authorModel.getNotelp());
        detailAuthorBinding.alamat.setText(authorModel.getAlamat());

        detailAuthorBinding.layoutEdit.setOnClickListener(new proses_aksi());
        detailAuthorBinding.btnDelete.setOnClickListener(new proses_aksi());
    }

    class proses_aksi implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.layout_edit:
                    EditAuthorFragment editAuthorFragment = new EditAuthorFragment();

                    FragmentManager fragmentManager = getFragmentManager();

                    Bundle bundle = new Bundle();
                    bundle.putParcelable("DATA", authorModel);

                    EditAuthorFragment editAuthorFragment1 = new EditAuthorFragment();
                    editAuthorFragment1.setArguments(bundle);

                    if (fragmentManager!=null){
                        fragmentManager.beginTransaction()
                                .replace(R.id.frame_detailauthor, editAuthorFragment, EditAuthorFragment.class.getSimpleName())
                                .commit();
                    }
                    break;

                case R.id.btn_delete:
                    progressDialog.setTitle(getResources().getString(R.string.onprogress_update));
                    progressDialog.setMessage(getResources().getString(R.string.please_wait));
                    progressDialog.setCancelable(false);
                    progressDialog.setIndeterminate(true);
                    progressDialog.show();
                    deleteAuthor();
                    break;
            }
        }
    }

    private void deleteAuthor(){
        Call<PostPutDelAuthor> delAuthorCall = ApiClient.getApiInterface().delAuthor(authorModel.getId_pengarang());
        delAuthorCall.enqueue(new Callback<PostPutDelAuthor>() {
            @Override
            public void onResponse(Call<PostPutDelAuthor> call, Response<PostPutDelAuthor> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    if (!response.body().isError()){
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
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
                Toast.makeText(getActivity(), getResources().getString(R.string.cek_koneksi) + t.getMessage() , Toast.LENGTH_LONG).show();
            }
        });
    }
}