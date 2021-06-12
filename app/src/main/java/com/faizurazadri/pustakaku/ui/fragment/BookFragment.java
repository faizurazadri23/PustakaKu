package com.faizurazadri.pustakaku.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.faizurazadri.pustakaku.R;
import com.faizurazadri.pustakaku.adapter.AuthorAdapter;
import com.faizurazadri.pustakaku.adapter.BookAdapter;
import com.faizurazadri.pustakaku.api.ApiClient;
import com.faizurazadri.pustakaku.databinding.FragmentBookBinding;
import com.faizurazadri.pustakaku.model.AuthorModel;
import com.faizurazadri.pustakaku.model.BookModel;
import com.faizurazadri.pustakaku.model.GetAuthor;
import com.faizurazadri.pustakaku.model.GetBook;
import com.faizurazadri.pustakaku.ui.book.BookActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookFragment extends Fragment {

    private FragmentBookBinding bookBinding;
    private RecyclerView.LayoutManager layoutManager;
    private BookAdapter bookAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bookBinding = FragmentBookBinding.inflate(inflater, container, false);
        return bookBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layoutManager = new LinearLayoutManager(getActivity());
        bookBinding.rvBook.setLayoutManager(layoutManager);
        bookBinding.rvBook.setHasFixedSize(true);

        bookBinding.swipeBook.setOnRefreshListener(() -> new Handler().postDelayed(() -> {
            getDataAuthor();
        }, 5000));


        getDataAuthor();

        bookBinding.fabAdd.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), BookActivity.class));
        });
    }

    private void getDataAuthor() {

        Call<GetBook> getBookCall = ApiClient.getApiInterface().getDataBook();
        getBookCall.enqueue(new Callback<GetBook>() {
            @Override
            public void onResponse(Call<GetBook> call, Response<GetBook> response) {
                bookBinding.progressBook.setVisibility(View.GONE);
                if (response.isSuccessful()){
                    List<BookModel> BookModels= response.body().getBookModelList();
                    bookAdapter = new BookAdapter(BookModels, getActivity());
                    bookBinding.rvBook.setAdapter(bookAdapter);
                    bookAdapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.permintaan_gagal) , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<GetBook> call, Throwable t) {
                bookBinding.progressBook.setVisibility(View.GONE);
                Toast.makeText(getActivity(), getResources().getString(R.string.cek_koneksi) + t.getMessage() , Toast.LENGTH_LONG).show();
            }
        });
    }
}