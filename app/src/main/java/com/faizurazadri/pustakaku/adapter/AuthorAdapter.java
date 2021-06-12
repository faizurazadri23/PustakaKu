package com.faizurazadri.pustakaku.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.faizurazadri.pustakaku.databinding.ListAuthorBinding;
import com.faizurazadri.pustakaku.model.AuthorModel;
import com.faizurazadri.pustakaku.ui.author.DetailAuthorActivity;

import java.util.List;

public class AuthorAdapter extends RecyclerView.Adapter<AuthorAdapter.ViewHolderAuthor> {

    private List<AuthorModel> authorModels;
    private Context context;

    public AuthorAdapter(List<AuthorModel> authorModels, Context context) {
        this.authorModels = authorModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderAuthor onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListAuthorBinding authorBinding = ListAuthorBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolderAuthor(authorBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAuthor holder, int position) {
        AuthorModel authorModel = authorModels.get(position);
        holder.setAuthorBinding(authorModel);
    }

    @Override
    public int getItemCount() {
        return authorModels == null ? 0 : authorModels.size();
    }

    static class ViewHolderAuthor extends RecyclerView.ViewHolder {
        ListAuthorBinding authorBinding;

        ViewHolderAuthor(ListAuthorBinding authorBinding) {
            super(authorBinding.getRoot());
            this.authorBinding = authorBinding;
        }

        void setAuthorBinding(AuthorModel authorModel){
            authorBinding.namaPengarang.setText(authorModel.getNama_pengarang());
            authorBinding.alamat.setText(authorModel.getAlamat());
            authorBinding.email.setText(authorModel.getEmail());

            itemView.setOnClickListener(v -> {
                Intent detaiAuthor = new Intent(v.getContext(), DetailAuthorActivity.class);
                detaiAuthor.putExtra("DATA", authorModel);
                v.getContext().startActivity(detaiAuthor);
            });
        }
    }
}
