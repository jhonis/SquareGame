package br.com.jgames.squaregame;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import br.com.jgames.squaregame.views.TouchImageView;

public class MainActivity extends Activity {

    private final static String CATEGORIA = "squareGame";

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.main);

        Button btn = (Button) findViewById(R.id.btn_novo_jogo);
        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    TouchImageView touchImageView = (TouchImageView) findViewById(R.id.touch_image_view);
                    touchImageView.startGame();
                    touchImageView.invalidate();
                    return true;
                }
                return false;
            }
        });
    }
}
