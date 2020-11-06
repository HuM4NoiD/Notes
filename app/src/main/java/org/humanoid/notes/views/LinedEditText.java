package org.humanoid.notes.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;

/**
 * Created by Jugal Mistry on 2/8/2019.
 */
public class LinedEditText extends AppCompatEditText {

    private Rect mRect;
    private Paint mPaint;

    public LinedEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        mRect = new Rect();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        mPaint.setColor(0x88ffffff);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int height = ((View) this).getHeight();
        int lineHeight = getLineHeight();
        int lines = height / lineHeight;

        Rect rect = mRect;
        Paint paint = mPaint;

        int baseline = getLineBounds(0, rect);
        for (int i = 0; i < lines; ++i) {
            canvas.drawLine(rect.left, baseline + 1,
                    rect.right, baseline + 1, paint);

            baseline += lineHeight;
        }

        super.onDraw(canvas);
    }
}
