package com.example.anteproyectoidea.soluciones;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class EditGridView extends GridView {
    public EditGridView(Context context) {
        super(context);
    }

    public EditGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSpec;

        if (getLayoutParams().height == LayoutParams.WRAP_CONTENT) {
            // The great Android "hackatlon", the love, the magic.
            // The two leftmost bits in the height measure spec have
            // a special meaning, hence we can't use them to describe height.
            heightSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 5,MeasureSpec.AT_MOST);
        } else {
            heightSpec = heightMeasureSpec;
        }

        super.onMeasure(widthMeasureSpec, heightSpec);
    }
}