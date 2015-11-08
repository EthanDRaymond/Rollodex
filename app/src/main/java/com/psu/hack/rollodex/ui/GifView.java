package com.psu.hack.rollodex.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.media.Image;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;

import com.psu.hack.rollodex.R;

/**
 * Created by ethanraymond on 11/8/15.
 */
public class GifView extends View {

    private Movie movie;
    private int width, height;
    private long nMovieStart;

    public GifView(Context context) {
        super(context);
        init();
    }

    public GifView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GifView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.movie = Movie.decodeStream(getContext().getResources().openRawResource(R.raw.rolodex));
        this.width = BitmapFactory.decodeStream(
                getContext().getResources().openRawResource(R.raw.rolodex)
        ).getWidth();
        this.height = BitmapFactory.decodeStream(
                getContext().getResources().openRawResource(R.raw.rolodex)
        ).getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        final int SCALE = 6;
        if (movie != null) {
            if (nMovieStart == 0) {
                nMovieStart = System.currentTimeMillis();
            }
            canvas.save();
            canvas.scale(SCALE, SCALE);
            final int relTime = (int)((System.currentTimeMillis() - nMovieStart) % movie.duration());
            movie.setTime(relTime);
            movie.draw(
                    canvas,
                    (getWidth() - width * SCALE) / (2.0f * SCALE),
                    (getHeight() - height * SCALE) / (2.0f * SCALE)
            );
            canvas.restore();
        }
        invalidate();
    }

}
