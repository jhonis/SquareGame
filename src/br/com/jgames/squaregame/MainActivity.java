package br.com.jgames.squaregame;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

    private final static String CATEGORIA = "squareGame";

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.i(CATEGORIA, "xxxxxx");
    }
}
