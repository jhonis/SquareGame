package br.com.jgames.squaregame.activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.Chronometer;
import br.com.jgames.squaregame.R;
import br.com.jgames.squaregame.dao.PartidaDAO;
import br.com.jgames.squaregame.enums.Dificuldade;
import br.com.jgames.squaregame.views.TouchImageView;

public class PlayActivity extends Activity {

    private final static String CATEGORIA = "squareGame";
    private TouchImageView touch;
    private Chronometer chronometer;
    private PartidaDAO partidaDAO = new PartidaDAO(this);

    @Override
    protected void onCreate(Bundle bundle) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(bundle);
        setContentView(R.layout.play);

        Dificuldade dificuldade = (Dificuldade) getIntent().getExtras().get("DIFICULDADE");
        touch = (TouchImageView) findViewById(R.id.touch_image_view);
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        touch.startGame(dificuldade, result, getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels);
    }

    public void startChronometer() {
        PartidaDAO.CursorPartida tempSquare = partidaDAO.buscarPartida();
        chronometer = (Chronometer) findViewById(R.id.chronometer);
        touch.setMovimentos(tempSquare.getMovimentos());
        touch.setPositions(tempSquare.getSequencia());
        chronometer.setBase(SystemClock.elapsedRealtime() + tempSquare.getTempo());
        chronometer.start();
    }
    public void stopChronometer() {
        Long tempo = chronometer.getBase() - SystemClock.elapsedRealtime();
        partidaDAO.atualizarPartida(tempo, touch.getMovimentos(), touch.getPositions());
        chronometer.stop();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopChronometer();
    }

    @Override
    public void onResume() {
        super.onResume();
        startChronometer();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int xMove = (int) motionEvent.getX();
        int yMove = (int) motionEvent.getY();
        int t = touch.getTop();
        int l = touch.getLeft();
        if (yMove > touch.getBottomBorder() + t || yMove < t || xMove > touch.getRightBorder() + l || xMove < l) {
            touch.stopMove();
        }
        return true;
    }
}
