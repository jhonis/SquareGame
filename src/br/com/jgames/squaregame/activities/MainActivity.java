package br.com.jgames.squaregame.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import br.com.jgames.squaregame.R;
import br.com.jgames.squaregame.dao.PartidaDAO;
import br.com.jgames.squaregame.enums.Dificuldade;
import com.google.analytics.tracking.android.EasyTracker;

public class MainActivity extends Activity {

    private Dificuldade dificuldade = Dificuldade.FACIL;
    private final int RESULT_MENU = 1;
    private PartidaDAO partidaDAO = new PartidaDAO(this);

    @Override
    protected void onCreate(Bundle bundle) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(bundle);
        setContentView(R.layout.main);

        EasyTracker.getInstance(this).activityStart(this);
        ImageButton btn_novo_jogo = (ImageButton) findViewById(R.id.btn_novo_jogo);
        btn_novo_jogo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    Intent it = new Intent(getApplicationContext(), PlayActivity.class);
                    it.putExtra("DIFICULDADE", dificuldade);
                    startActivity(it);
                    partidaDAO.inserirPartida(0l, 0, "");
                    return true;
                }
                return false;
            }
        });

        ImageButton btn_menu = (ImageButton) findViewById(R.id.btn_menu);
        btn_menu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                    intent.putExtra("dificuldade", dificuldade);
                    startActivityForResult(intent, RESULT_MENU);
                    return true;
                }
                return false;
            }
        });

        ImageButton btn_amigos = (ImageButton) findViewById(R.id.btn_amigos);
        btn_amigos.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    startActivity(new Intent(getApplicationContext(), AmigosActivity.class));
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_MENU) {
            if (resultCode == RESULT_OK) {
                dificuldade = (Dificuldade) data.getExtras().get("dificuldade");
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        EasyTracker.getInstance(this).activityStop(this);
    }
}
