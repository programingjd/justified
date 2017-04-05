package com.uncopt.android.example.justify;

import android.content.Context;
import android.os.Build;
import android.text.Layout;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.util.Linkify;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.uncopt.android.widget.text.justify.JustifiedTextView;


public class MyJustifiedTextView extends JustifiedTextView {

  public MyJustifiedTextView(final @NotNull Context context, final AttributeSet attrs) {
    super(context, attrs);
    if (!isInEditMode()) {
      final ExampleActivity activity = (ExampleActivity)context;
      setTypeface(activity.getTypeface());
    }
    setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(final @NotNull View v) {
        Toast.makeText(context, "Click", Toast.LENGTH_SHORT).show();
      }
    });
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

  // We want our text to be selectable, but we still want links to be clickable.
  @Override
  public boolean onTouchEvent(final @NotNull MotionEvent event) {
    final Spannable text = (Spannable)getText();
    if (text != null) {
      if (event.getAction() == MotionEvent.ACTION_DOWN) {
        final Layout layout = getLayout();
        if (layout != null) {
          // final int pos = getOffsetForPosition(event.getX(), event.getY()); // API >= 14 only
          final int line = getLineAtCoordinate(layout, event.getY());
          final int pos = getOffsetAtCoordinate(layout, line, event.getX());
          final ClickableSpan[] links = text.getSpans(pos, pos, ClickableSpan.class);
          if (links != null && links.length > 0) {
            links[0].onClick(this);
            return true;
          }
        }
      }
    }
    return super.onTouchEvent(event);
  }

  private int getLineAtCoordinate(final @NotNull Layout layout, final float y) {
    final int max = getHeight() - getTotalPaddingBottom() - 1;
    final int v = Math.min(max, Math.max(0, (int)y - getTotalPaddingTop())) + getScrollY();
    return layout.getLineForVertical(v);
  }

  private int getOffsetAtCoordinate(final @NotNull Layout layout, final int line, final float x) {
    final int  max = getWidth() - getTotalPaddingRight() - 1;
    final int v = Math.min(max, Math.max(0, (int)x - getTotalPaddingLeft())) + getScrollX();
    return layout.getOffsetForHorizontal(line, v);
  }

}
