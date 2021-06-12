package com.faizurazadri.pustakaku.ui.book;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.faizurazadri.pustakaku.R;
import com.faizurazadri.pustakaku.api.ApiClient;
import com.faizurazadri.pustakaku.databinding.ActivityDetailBookBinding;
import com.faizurazadri.pustakaku.model.BookModel;
import com.faizurazadri.pustakaku.model.PostPutDelBook;
import com.faizurazadri.pustakaku.ui.author.DetailAuthorActivity;
import com.faizurazadri.pustakaku.ui.fragment.DetailBookFragment;
import com.faizurazadri.pustakaku.ui.home.HomeActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailBookActivity extends AppCompatActivity {

    ActivityDetailBookBinding bookBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bookBinding = ActivityDetailBookBinding.inflate(getLayoutInflater());
        setContentView(bookBinding.getRoot());


        FragmentManager fragmentManager = getSupportFragmentManager();
        DetailBookFragment detailBookFragment = new DetailBookFragment();
        Fragment fragment = fragmentManager.findFragmentByTag(DetailBookFragment.class.getSimpleName());

        if (!(fragment instanceof DetailBookFragment)) {
            fragmentManager.beginTransaction()
                    .add(R.id.frame_detailbook, detailBookFragment, DetailBookFragment.class.getSimpleName())
                    .commit();
        }
    }
}