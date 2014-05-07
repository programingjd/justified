package com.uncopt.android.example.justify;

import android.content.Context;
import android.util.AttributeSet;

import com.uncopt.android.widget.text.justify.JustifiedEditText;


public class MyJustifiedEditText extends JustifiedEditText {

  public MyJustifiedEditText(final @NotNull Context context, final AttributeSet attrs) {
    super(context, attrs);
    if (!isInEditMode()) {
      final ExampleActivity activity = (ExampleActivity)context;
      setTypeface(activity.getTypeface());
    }
  }

}
