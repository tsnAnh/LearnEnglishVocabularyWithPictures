package dev.tsnanh.education.englishvocabularywithpictures.controller;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class VocabularyViewPagerAdapter extends FragmentPagerAdapter {
    private List<String> titleList = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();

    public VocabularyViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }

    public void addFragment(Fragment fragment, String title) {
        titleList.add(title);
        fragments.add(fragment);
    }
}
