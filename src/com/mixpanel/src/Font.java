package com.mixpanel.src;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class Font extends TextView {

public Font(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init();
}

public Font(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
}

public Font(Context context) {
    super(context);
    init();
}

private void init() {
    if (!isInEditMode()) {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "Bariol_Regular.otf");
        setTypeface(tf);
    }
}}