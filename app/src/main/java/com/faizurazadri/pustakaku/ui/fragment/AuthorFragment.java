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
import com.faizurazadri.pustakaku.api.ApiClient;
import com.faizurazadri.pustakaku.databinding.FragmentAuthorBinding;
import com.faizurazadri.pustakaku.model.AuthorModel;
import com.faizurazadri.pustakaku.model.GetAuthor;
import com.faizurazadri.pustakaku.ui.author.AuthorActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AuthorFragment extends Fragment {

    private FragmentAuthorBinding authorBinding;
    private RecyclerView.LayoutManager layoutManager;
    private AuthorAdapter authorAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        authorBinding = FragmentAuthorBinding.inflate(inflater, container, false);
        return authorBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layoutManager = new LinearLayoutManager(getActivity());
        authorBinding.rvAuthor.setLayoutManager(layoutManager);
        authorBinding.rvAuthor.setHasFixedSize(true);


        authorBinding.swipeAuthor.setOnRefreshListener(() -> new Handler().postDelayed(() -> {
            getDataAuthor();
        }, 5000));

        getDataAuthor();

        authorBinding.fabAdd.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), AuthorActivity.class));
        });
    }

    private void getDataAuthor() {

        Call<GetAuthor> getAuthorCall = ApiClient.getApiInterface().getDataAuthor();
        getAuthorCall.enqueue(new Callback<GetAuthor>() {
            @Override
            public void onResponse(Call<GetAuthor> call, Response<GetAuthor> response) {
                authorBinding.progressAuthor.setVisibility(View.GONE);
                if (response.isSuccessful()){
                    List<AuthorModel> authorModels= response.body().getAuthorModels();
                    authorAdapter = new AuthorAdapter(authorModels, getActivity());
                    authorBinding.rvAuthor.setAdapter(authorAdapter);
                    authorAdapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.permintaan_gagal) , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<GetAuthor> call, Throwable t) {
                authorBinding.progressAuthor.setVisibility(View.GONE);
                Toast.makeText(getActivity(), getResources().getString(R.string.cek_koneksi) + t.getMessage() , Toast.LENGTH_LONG).show();
            }
        });
    }
}