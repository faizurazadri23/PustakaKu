package com.faizurazadri.pustakaku.ui.home;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.faizurazadri.pustakaku.R;
import com.faizurazadri.pustakaku.ui.fragment.AuthorFragment;
import com.faizurazadri.pustakaku.ui.fragment.BookFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {


    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.buku, R.string.pengarang};
    private final Context mcontext;


    SectionsPagerAdapter(@NonNull FragmentManager fm, Context mcontext) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new BookFragment();
            case 1:
                return new AuthorFragment();
            default:
                return new Fragment();
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mcontext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
