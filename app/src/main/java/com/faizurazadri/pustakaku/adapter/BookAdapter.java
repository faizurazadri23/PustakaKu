package com.faizurazadri.pustakaku.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.faizurazadri.pustakaku.databinding.ListBookBinding;
import com.faizurazadri.pustakaku.model.BookModel;
import com.faizurazadri.pustakaku.ui.book.DetailBookActivity;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolderBook> {

    private List<BookModel> bookModels;
    private Context context;

    public BookAdapter(List<BookModel> bookModels, Context context) {
        this.bookModels = bookModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderBook onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListBookBinding bookBinding = ListBookBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolderBook(bookBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderBook holder, int position) {
        BookModel bookModel = bookModels.get(position);
        holder.setBookBinding(bookModel);
    }

    @Override
    public int getItemCount() {
        return bookModels == null ? 0 : bookModels.size();
    }

    static class ViewHolderBook extends RecyclerView.ViewHolder {

        private final ListBookBinding bookBinding;

        ViewHolderBook(ListBookBinding bookBinding) {
            super(bookBinding.getRoot());
            this.bookBinding = bookBinding;
        }

        void setBookBinding(BookModel bookModel){
            bookBinding.judulBuku.setText(bookModel.getJudul_buku());
            bookBinding.namaPengarang.setText(bookModel.getNama_pengarang());
            bookBinding.tahunTerbit.setText(bookModel.getTahun_terbit());

            itemView.setOnClickListener(v -> {
                Intent detailBook = new Intent(v.getContext(), DetailBookActivity.class);
                detailBook.putExtra("DATA", bookModel);
                v.getContext().startActivity(detailBook);
            });
        }
    }
}
