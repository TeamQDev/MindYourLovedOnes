package com.mindyourelders.MyHealthCareWishes.customview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by shadaf on 12/1/2016.
 */

public class TextViewRomanS extends TextView {

    public TextViewRomanS(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TextViewRomanS(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextViewRomanS(Context context) {
        super(context);
        init();
    }

    static Typeface tf;

    public void init() {
        if (tf == null) {
            tf = Typeface.createFromAsset(getContext().getAssets(), "RomanS.ttf");
        }

        setTypeface(tf);

    }


}
