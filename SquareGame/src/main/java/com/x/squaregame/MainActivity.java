package com.x.squaregame;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * This activity presents a screen with a grid on which images can be added and moved around.
 * It also defines areas on the screen where the dragged views can be dropped. Feedback is
 * provided to the user as the objects are dragged over these drop zones.
 * <p/>
 * <p> Like the DragActivity in the previous version of the DragView example application, the
 * code here is derived from the Android Launcher code.
 * <p/>
 * <p> The original Launcher code required a long click (press) to initiate a drag-drop sequence.
 * If you want to see that behavior, set the variable mLongClickStartsDrag to true.
 * It is set to false below, which means that any touch event starts a drag-drop.
 */

public class MainActivity extends Activity {

    private ArrayAdapter<ImageView> adapterTest;

    public static final boolean Debugging = false;

    public void addNewImageToScreen(int resourceId) {
        ImageView newView = new ImageView(this);
        newView.setImageResource(resourceId);
        GridView grid = (GridView) findViewById(R.id.imageGridView);
        grid.setAdapter(new ImageAdapter(this));
    }

    public void addImagesToScreen() {

        addNewImageToScreen(R.drawable.img1);
//        addNewImageToScreen(R.drawable.img2);
//        addNewImageToScreen(R.drawable.img3);
//        addNewImageToScreen(R.drawable.img4);
//        addNewImageToScreen(R.drawable.img5);
//        addNewImageToScreen(R.drawable.img6);
//        addNewImageToScreen(R.drawable.img7);
//        addNewImageToScreen(R.drawable.img8);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapterTest = new ArrayAdapter<ImageView>(this, android.R.layout.simple_list_item_1);
        addImagesToScreen();
    }

    public void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void trace(String msg) {
        Log.d("DragActivity", msg);
        if (!Debugging) return;
        toast(msg);
    }
}
