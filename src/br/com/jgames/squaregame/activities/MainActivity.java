package br.com.jgames.squaregame.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import br.com.jgames.squaregame.R;
import br.com.jgames.squaregame.enums.Dificuldade;

public class MainActivity extends Activity {

    private Dificuldade dificuldade = Dificuldade.FACIL;
    private final int RESULT_MENU = 1;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.main);

        Button btn_novo_jogo = (Button) findViewById(R.id.btn_novo_jogo);
        btn_novo_jogo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    Intent it = new Intent(getApplicationContext(), PlayActivity.class);
                    it.putExtra("DIFICULDADE", dificuldade);
                    startActivity(it);
                    return true;
                }
                return false;
            }
        });

        Button btn_menu = (Button) findViewById(R.id.btn_menu);
        btn_menu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    startActivityForResult(new Intent(getApplicationContext(), MenuActivity.class), RESULT_MENU);
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
}
