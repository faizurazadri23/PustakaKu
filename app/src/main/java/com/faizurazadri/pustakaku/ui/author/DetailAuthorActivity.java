package com.faizurazadri.pustakaku.ui.author;

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
import com.faizurazadri.pustakaku.databinding.ActivityDetailAuthorBinding;
import com.faizurazadri.pustakaku.model.AuthorModel;
import com.faizurazadri.pustakaku.model.PostPutDelAuthor;
import com.faizurazadri.pustakaku.ui.fragment.DetailAuthorFragment;
import com.faizurazadri.pustakaku.ui.fragment.DetailBookFragment;
import com.faizurazadri.pustakaku.ui.home.HomeActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailAuthorActivity extends AppCompatActivity {

    ActivityDetailAuthorBinding detailAuthorBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailAuthorBinding = ActivityDetailAuthorBinding.inflate(getLayoutInflater());
        setContentView(detailAuthorBinding.getRoot());


        FragmentManager fragmentManager = getSupportFragmentManager();
        DetailAuthorFragment detailAuthorFragment = new DetailAuthorFragment();
        Fragment fragment = fragmentManager.findFragmentByTag(DetailBookFragment.class.getSimpleName());

        if (!(fragment instanceof DetailBookFragment)) {
            fragmentManager.beginTransaction()
                    .add(R.id.frame_detailauthor, detailAuthorFragment, DetailAuthorFragment.class.getSimpleName())
                    .commit();
        }

    }
}