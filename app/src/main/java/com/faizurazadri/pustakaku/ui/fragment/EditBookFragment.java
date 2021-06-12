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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.faizurazadri.pustakaku.R;
import com.faizurazadri.pustakaku.api.ApiClient;
import com.faizurazadri.pustakaku.databinding.FragmentEditBookBinding;
import com.faizurazadri.pustakaku.model.BookModel;
import com.faizurazadri.pustakaku.model.GetAuthor;
import com.faizurazadri.pustakaku.model.PostPutDelBook;
import com.faizurazadri.pustakaku.ui.book.BookActivity;
import com.faizurazadri.pustakaku.ui.home.HomeActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditBookFragment extends Fragment {

    FragmentEditBookBinding editBookBinding;
    private int id_pengarang, id_book;
    BookModel bookModel;
    private String listpengrang[] = {"Pilih Pengarang"};
    private String judul,jumlah_halamn,isbn, tahun;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        editBookBinding = FragmentEditBookBinding.inflate(inflater, container, false);
        return editBookBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getActivity().getIntent().getExtras();

        bookModel = bundle.getParcelable("DATA");

        progressDialog = new ProgressDialog(getActivity());

        getPengarang();

        editBookBinding.judulBuku.setText(bookModel.getJudul_buku());
        editBookBinding.jumlahHalaman.setText(String.valueOf(bookModel.getJml_halaman()));
        editBookBinding.nomorIsbn.setText(bookModel.getNomor_isbn());
        editBookBinding.tahunTerbit.setText(bookModel.getTahun_terbit());

        editBookBinding.btnUpdate.setOnClickListener(v -> {
            id_book = bookModel.getId_buku();
            judul = editBookBinding.judulBuku.getText().toString();
            jumlah_halamn = editBookBinding.jumlahHalaman.getText().toString();
            isbn = editBookBinding.nomorIsbn.getText().toString();
            tahun = editBookBinding.tahunTerbit.getText().toString();

            if (judul.isEmpty()){
                editBookBinding.judulBuku.setError(getResources().getString(R.string.wajib));
                editBookBinding.judulBuku.requestFocus();
                return;
            }

            if (jumlah_halamn.isEmpty()){
                editBookBinding.jumlahHalaman.setError(getResources().getString(R.string.wajib));
                editBookBinding.jumlahHalaman.requestFocus();
                return;
            }

            if (isbn.isEmpty()){
                editBookBinding.nomorIsbn.setError(getResources().getString(R.string.wajib));
                editBookBinding.nomorIsbn.requestFocus();
                return;
            }

            if (tahun.isEmpty()){
                editBookBinding.tahunTerbit.setError(getResources().getString(R.string.wajib));
                editBookBinding.tahunTerbit.requestFocus();
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
        Call<PostPutDelBook> putBookCall = ApiClient.getApiInterface().putBook(id_book, judul, id_pengarang, Integer.parseInt(jumlah_halamn), isbn, tahun);
        putBookCall.enqueue(new Callback<PostPutDelBook>() {
            @Override
            public void onResponse(Call<PostPutDelBook> call, Response<PostPutDelBook> response) {
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
            public void onFailure(Call<PostPutDelBook> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), getResources().getString(R.string.cek_koneksi) , Toast.LENGTH_LONG).show();
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

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, listAuthor);
                        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                        editBookBinding.idPengarang.setAdapter(adapter);

                        editBookBinding.idPengarang.setSelection(adapter.getPosition(bookModel.getNama_pengarang()));


                        editBookBinding.idPengarang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                String selectedName = parent.getItemAtPosition(position).toString();
                                int idpengrang = response.body().getAuthorModels().get(position).getId_pengarang();

                                id_pengarang = idpengrang;

                                if (selectedName != null) {
                                    editBookBinding.idPengarang.setEnabled(true);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }else {
                        defAuthor();
                        Toast.makeText(getActivity(), getResources().getString(R.string.failed_getId) , Toast.LENGTH_LONG).show();
                    }

                }else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.permintaan_gagal) , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<GetAuthor> call, Throwable t) {

            }
        });
    }

    private void defAuthor(){
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, listpengrang);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editBookBinding.idPengarang.setAdapter(arrayAdapter);
    }
}