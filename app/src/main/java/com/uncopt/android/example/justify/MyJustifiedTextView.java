package com.uncopt.android.example.justify;

import android.content.Context;
import android.os.Build;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.util.Linkify;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.uncopt.android.widget.text.justify.JustifiedTextView;


public class MyJustifiedTextView extends JustifiedTextView {

  public MyJustifiedTextView(final @NotNull Context context, final AttributeSet attrs) {
    super(context, attrs);
    if (!isInEditMode()) {
      final ExampleActivity activity = (ExampleActivity)context;
      setTypeface(activity.getTypeface());
    }
  }

  @Override
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    // setTextIsSelectable doesn't work unless the text view is attached to the window
    // because it uses the window layout params to check if it can display the handles.
    if (Build.VERSION.SDK_INT > 10) {
      setTextIsSelectable(true);
    }
  }

  @Override
  public boolean onTouchEvent(final @NotNull MotionEvent event) {
    final Spannable text = (Spannable)getText();
    if (text != null) {
      if (event.getAction() == MotionEvent.ACTION_DOWN) {
        final int pos = getOffsetForPosition(event.getX(), event.getY());
        final ClickableSpan[] links = text.getSpans(pos, pos, ClickableSpan.class);
        if (links != null && links.length > 0) {
          links[0].onClick(this);
          return true;
        }
      }
    }
    return super.onTouchEvent(event);
  }

}
