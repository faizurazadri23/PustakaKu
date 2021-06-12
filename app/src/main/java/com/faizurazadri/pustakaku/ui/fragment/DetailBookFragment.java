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
import com.faizurazadri.pustakaku.databinding.FragmentDetailBookBinding;
import com.faizurazadri.pustakaku.model.BookModel;
import com.faizurazadri.pustakaku.model.PostPutDelBook;
import com.faizurazadri.pustakaku.ui.home.HomeActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailBookFragment extends Fragment {

    FragmentDetailBookBinding detailBookBinding;
    private BookModel bookModel;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        detailBookBinding = FragmentDetailBookBinding.inflate(inflater, container, false);
        return detailBookBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressDialog = new ProgressDialog(getActivity());

        bookModel = getActivity().getIntent().getParcelableExtra("DATA");



        if (((AppCompatActivity)getActivity()).getSupportActionBar()!=null){
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Detail " + bookModel.getJudul_buku());
        }

        detailBookBinding.judulBuku.setText(bookModel.getJudul_buku());
        detailBookBinding.namaPengarang.setText(bookModel.getNama_pengarang());
        detailBookBinding.jumlahHalaman.setText(String.valueOf(bookModel.getJml_halaman()));
        detailBookBinding.nomorIsbn.setText(bookModel.getNomor_isbn());
        detailBookBinding.tahunTerbit.setText(bookModel.getTahun_terbit());

        detailBookBinding.layoutEdit.setOnClickListener(new proses_aksi());
        detailBookBinding.btnDelete.setOnClickListener(new proses_aksi());

    }

    class proses_aksi implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.layout_edit:
                    EditBookFragment editBookFragment = new EditBookFragment();

                    FragmentManager fragmentManager = getFragmentManager();

                    Bundle bundle = new Bundle();
                    bundle.putParcelable("DATA", bookModel);

                    EditBookFragment editBookFragment1 = new EditBookFragment();
                    editBookFragment1.setArguments(bundle);

                    if (fragmentManager!=null){
                        fragmentManager.beginTransaction()
                                .replace(R.id.frame_detailbook, editBookFragment, EditBookFragment.class.getSimpleName())
                                .commit();
                    }
                    break;

                case R.id.btn_delete:
                    progressDialog.setTitle(getResources().getString(R.string.onprogress_del));
                    progressDialog.setMessage(getResources().getString(R.string.please_wait));
                    progressDialog.setCancelable(false);
                    progressDialog.setIndeterminate(true);
                    progressDialog.show();
                    deleteBook();
                    break;
            }
        }
    }

    private void deleteBook(){
        Call<PostPutDelBook> delBookCall = ApiClient.getApiInterface().delBook(bookModel.getId_buku());
        delBookCall.enqueue(new Callback<PostPutDelBook>() {
            @Override
            public void onResponse(Call<PostPutDelBook> call, Response<PostPutDelBook> response) {
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
            public void onFailure(Call<PostPutDelBook> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), getResources().getString(R.string.cek_koneksi) + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}