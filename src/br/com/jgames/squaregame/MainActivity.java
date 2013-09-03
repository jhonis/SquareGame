package br.com.jgames.squaregame;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import br.com.jgames.squaregame.views.TouchImageView;

public class MainActivity extends Activity {

    private final static String CATEGORIA = "squareGame";

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.main);

        RelativeLayout root = (RelativeLayout) findViewById(R.id.main_layout);
        Context ctx = getApplicationContext();

        TouchImageView img1 = new TouchImageView(ctx, R.drawable.img1, 0, 0);
        img1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.i(CATEGORIA, "teste dddd!! ");
                return false;
            }
        });
        root.addView(img1);

        TouchImageView img2 = new TouchImageView(ctx, R.drawable.img2, 230, 0);
        root.addView(img2);

        TouchImageView img3 = new TouchImageView(ctx, R.drawable.img3, 460, 0);
        root.addView(img3);

        TouchImageView img4 = new TouchImageView(ctx, R.drawable.img4, 0, 230);
        root.addView(img4);

        TouchImageView img5 = new TouchImageView(ctx, R.drawable.img5, 230, 230);
        root.addView(img5);

        TouchImageView img6 = new TouchImageView(ctx, R.drawable.img6, 460, 230);
        root.addView(img6);

        TouchImageView img7 = new TouchImageView(ctx, R.drawable.img7, 0, 460);
        root.addView(img7);

        TouchImageView img8 = new TouchImageView(ctx, R.drawable.img8, 230, 460);
        root.addView(img8);
        img8.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.i(CATEGORIA, "teste yyyyy!! ");
                return false;
            }
        });

        Log.i(CATEGORIA, "xxxxxx");
    }
}
