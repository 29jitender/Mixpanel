package com.mixpanel.src;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class Font1 extends TextView {

public Font1(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init();
}

public Font1(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
}

public Font1(Context context) {
    super(context);
    init();
}

private void init() {
    if (!isInEditMode()) {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "Albatross-Bold.ttf");
        setTypeface(tf);
    }
}}