package com.uncopt.android.example.justify;

import java.util.Locale;

import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ExampleActivity extends FragmentActivity {

  private Typeface mTypeface = null;

  public Typeface getTypeface() {
    return mTypeface;
  }

  @Override
  protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    try {
      mTypeface = Typeface.createFromAsset(getAssets(), "RobotoSlab-Regular.ttf");
    }
    catch (final Exception ignore) {}
    setContentView(R.layout.activity_example);
    final MyViewPager viewPager = (MyViewPager)findViewById(R.id.pager);
    if (viewPager != null) {
      viewPager.setAdapter(new SectionsPagerAdapter(getSupportFragmentManager()));
    }
  }

  public void closeHelp(final @NotNull View v) {
    findViewById(R.id.help).setVisibility(View.GONE);
  }

  public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(final @NotNull FragmentManager fm) {
      super(fm);
    }

    @Override
    public Fragment getItem(final int position) {
      return ExampleFragment.newInstance(position);
    }

    @Override
    public int getCount() {
      return 2;
    }

    @Override
    public CharSequence getPageTitle(final int position) {
      Locale l = Locale.getDefault();
      switch (position) {
        case 0:
          return getString(R.string.title_section1).toUpperCase(l);
        case 1:
          return getString(R.string.title_section2).toUpperCase(l);
      }
      return null;
    }
  }

  public static class ExampleFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final int[] LAYOUT_RES_IDS = new int[] {
      R.layout.fragment_example1,
      R.layout.fragment_example2,
    };

    public static ExampleFragment newInstance(final int sectionNumber) {
      final ExampleFragment fragment = new ExampleFragment();
      final Bundle args = new Bundle();
      args.putInt(ARG_SECTION_NUMBER, sectionNumber);
      fragment.setArguments(args);
      return fragment;
    }

    @Override
    public View onCreateView(final @NotNull LayoutInflater inflater,
                             final @NotNull ViewGroup container,
                             final Bundle savedInstanceState) {
      final int layoutResId = LAYOUT_RES_IDS[getArguments().getInt(ARG_SECTION_NUMBER)];
      return inflater.inflate(layoutResId, container, false);
    }

  }

}
