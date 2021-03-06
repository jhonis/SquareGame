package br.com.jgames.squaregame.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import br.com.jgames.squaregame.R;
import br.com.jgames.squaregame.enums.Dificuldade;

public class MenuActivity extends Activity {

    private final static String CATEGORIA = "squareGame";

    @Override
    protected void onCreate(Bundle bundle) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(bundle);
        setContentView(R.layout.menu);

        Dificuldade dificuldade = (Dificuldade) getIntent().getExtras().get("dificuldade");
        switch (dificuldade) {
            case FACIL:
                ((RadioButton) findViewById(R.id.btn_3)).setChecked(true);
                break;
            case MEDIO:
                ((RadioButton) findViewById(R.id.btn_4)).setChecked(true);
                break;
            case DIFICIL:
                ((RadioButton) findViewById(R.id.btn_5)).setChecked(true);
                break;
        }

        Button btnOk = (Button) findViewById(R.id.btn_menu_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dificuldade dificuldade = Dificuldade.FACIL;
                RadioButton rb3 = (RadioButton) findViewById(R.id.btn_3);
                if (rb3.isChecked()) {
                    dificuldade = Dificuldade.FACIL;
                } else {
                    RadioButton rb4 = (RadioButton) findViewById(R.id.btn_4);
                    if (rb4.isChecked()) {
                        dificuldade = Dificuldade.MEDIO;
                    } else {
                        RadioButton rb5 = (RadioButton) findViewById(R.id.btn_5);
                        if (rb5.isChecked()) {
                            dificuldade = Dificuldade.DIFICIL;
                        }
                    }
                }
                Intent resultIntent = new Intent();
                resultIntent.putExtra("dificuldade", dificuldade);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
