package br.com.jgames.squaregame.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class TouchImageView extends View {
    private static final String CATEGORIA = "squareGame";
    private Drawable img;
    int x, y;
    private boolean selecionou;

    public TouchImageView(Context context, int idImagem, int x, int y) {
        super(context);
        this.x = x;
        this.y = y;

        img = context.getResources().getDrawable(idImagem);
//        setFocusable(true);
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldw, int oldh) {
        super.onSizeChanged(width, height, oldw, oldh);
        // here i can take the size of screen
//        this.larguraTela = width;
//        this.alturaTela = height;
//        x = width / 2 - (larguraTela / 2);
//        y = height / 2 - (alturaTela / 2);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint pincel = new Paint();
        Rect r = new Rect(x, y, x + img.getIntrinsicWidth(), y + img.getIntrinsicHeight());
        canvas.drawRect(r, pincel);
        img.setBounds(r);
        img.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        Log.i(CATEGORIA, "teste: "+ img.toString());
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                selecionou = img.copyBounds().contains((int) x, (int) y);
                break;
            case MotionEvent.ACTION_MOVE:
                if (selecionou) {
                    this.x = (int) x - (img.getIntrinsicWidth() / 2);
                    this.y = (int) y - (img.getIntrinsicHeight() / 2);
                }
                break;
            case MotionEvent.ACTION_UP:
                selecionou = false;
                break;
        }
        invalidate();
        return true;
    }
}
