package br.com.jgames.squaregame.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import br.com.jgames.squaregame.R;
import br.com.jgames.squaregame.enums.Dificuldade;
import br.com.jgames.squaregame.views.TouchImageView;

public class PlayActivity extends Activity {

    private final static String CATEGORIA = "squareGame";

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.play);

        Dificuldade dificuldade = (Dificuldade) getIntent().getExtras().get("DIFICULDADE");
        TouchImageView touch = (TouchImageView) findViewById(R.id.touch_image_view);
        touch.startGame(dificuldade);
    }
}
