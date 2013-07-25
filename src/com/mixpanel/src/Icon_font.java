package com.mixpanel.src;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class Icon_font extends TextView {

public Icon_font(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init();
}

public Icon_font(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
}

public Icon_font(Context context) {
    super(context);
    init();
}

private void init() {
    if (!isInEditMode()) {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fontawesome-webfont.ttf");
        setTypeface(tf);
    }
}}