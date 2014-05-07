package com.uncopt.android.example.justify;

import java.lang.reflect.Method;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;


public class MyViewPager extends ViewPager {

  private static final String FIRST_RUN_KEY = "FIRST_RUN";

  public MyViewPager(final @NotNull Context context, final AttributeSet attrs) {
    super(context, attrs);
    final ViewTreeObserver observer = getViewTreeObserver();
    if (observer != null && observer.isAlive()) {
      observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
        @Override
        public boolean onPreDraw() {
          final ViewTreeObserver observer = getViewTreeObserver();
          if (observer != null && observer.isAlive()) {
            observer.removeOnPreDrawListener(this);
          }
          new Thread() {
            @Override
            public void run() {
              final SharedPreferences preferences =
                PreferenceManager.getDefaultSharedPreferences(context);
              if (preferences != null) {
                if (preferences.getBoolean(FIRST_RUN_KEY, true)) {
                  preferences.edit().putBoolean(FIRST_RUN_KEY, false).commit();
                  post(new Runnable() {
                    @Override
                    public void run() {
                      final Activity activity = (Activity)context;
                      final View helpView = activity.findViewById(R.id.help);
                      if (helpView != null) {
                        helpView.setVisibility(View.VISIBLE);
                        animate(getWidth() / 4);
                      }
                    }
                  });
                }
              }
            }
          }.start();
          return true;
        }
      });
    }
  }

  private void animate(final int offset) {
    try {
      final Method m = ViewPager.class.getDeclaredMethod("smoothScrollTo",
        Integer.TYPE, Integer.TYPE, Integer.TYPE);
      m.setAccessible(true);
      postDelayed(new Runnable() {
        @Override
        public void run() {
          try {
            m.invoke(MyViewPager.this, offset, 0, 200);
          }
          catch (final @NotNull Exception ignored) {}
        }
      }, 1000L);
      postDelayed(new Runnable() {
        @Override
        public void run() {
          try {
            m.invoke(MyViewPager.this, 0, 0, 200);
          }
          catch (final @NotNull Exception ignored) {}
        }
      }, 3000L);
    }
    catch (final @NotNull Exception ignored) {}
  }

}
