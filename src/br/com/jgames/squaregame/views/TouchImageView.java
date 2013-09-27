package br.com.jgames.squaregame.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import br.com.jgames.squaregame.enums.Dificuldade;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TouchImageView extends View {
    private static final String CATEGORIA = "squareGame";
    private Context context;
    private List<Rect> coords;
    private Integer[] positions;
    private Dificuldade dificuldade;
    private int x, y, index = -1;
    private boolean selecionou;

    public TouchImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    public TouchImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public TouchImageView(Context context) {
        super(context);
        this.context = context;
    }

    public void startGame(Dificuldade dificuldade) {
        this.dificuldade = dificuldade;
        coords = new ArrayList<Rect>();
        Log.i(CATEGORIA, ">>>> Teste XXX");
        int a = 0, b = 0;
        for (int i = 0; i < dificuldade.getTamanhoVetor(); i++) {
            a = dificuldade.getMagicMod(i) * dificuldade.getSize();
            b = dificuldade.getMagicDiv(i) * dificuldade.getSize();
            coords.add(new Rect(a, b, a + dificuldade.getSize(), b + dificuldade.getSize()));
        }

        positions = new Integer[dificuldade.getTamanhoVetor()];
        for (int i = 0; i < positions.length; i++) {
            positions[i] = i;
        }
//        random();
        setFocusable(true);
    }

    private int getIndexNone() {
        for (int i = 0; i < positions.length; i++) {
            if (positions[i] == 0) {
                return i;
            }
        }
        return -1;
    }

    private void random() {
        for (int i = 0; i < positions.length; i++) {
            Random r = new Random();
            int p = r.nextInt(positions.length);
            int temp = positions[i];
            positions[i] = positions[p];
            positions[p] = temp;
        }
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
        if (coords != null) {
            Paint paint = new Paint();
            paint.setColor(Color.BLUE);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);

            Paint paintMove = new Paint();
            paintMove.setColor(Color.RED);
            paintMove.setStyle(Paint.Style.FILL_AND_STROKE);

            Paint paintText = new Paint();
            paintText.setColor(Color.WHITE);
            paintText.setStyle(Paint.Style.STROKE);
            paintText.setTextSize(50);

            for (int i = 0; i < positions.length; i++) {
                if (i != index && positions[i] != 0) {
                    canvas.drawRect(coords.get(i), paint);
                    canvas.drawText(String.valueOf(positions[i]), coords.get(i).left + dificuldade.getTextoPaddingLeft(positions[i]), coords.get(i).top + dificuldade.getTextoPaddingTop(), paintText);
//                    images.get(i).setBounds(coords.get(i));
//                    images.get(i).draw(canvas);
                }
            }
            if (selecionou && (positions[index] != 0) && dificuldade.canChange(index, getIndexNone())) {
                canvas.drawRect(x, y, x + dificuldade.getSize(), y + dificuldade.getSize(), paintMove);
//                canvas.drawText(String.valueOf(positions[index]), coords.get(index).left + 50, coords.get(index).top + 50, paintText);
//                images.get(index).setBounds(x, y, x + dificuldade.getSize(), y + dificuldade.getSize());
//                images.get(index).draw(canvas);
            }
        }
    }

    public void checkWin() {
        boolean resultado = true;
        for (int i = 0; i < positions.length - 1; i++) {
            if (i != (positions[i] - 1)) {
                resultado = false;
            }
        }
        if (positions[positions.length - 1] != 0) {
            resultado = false;
        }
        if (resultado) {
            Log.i(CATEGORIA, "Parabéns, Você ganhou!!!");
            Toast.makeText(context, "Parabéns, Você ganhou!!!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        Rect r;
        for (int i = 0; i < coords.size(); i++) {
            r = coords.get(i);
            if (r.contains((int) x, (int) y)) {
                this.x = r.left;
                this.y = r.top;
                if (!selecionou) {
                    index = i;
                }
                if (dificuldade.canChange(index, getIndexNone())) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            selecionou = true;
                            break;
                        case MotionEvent.ACTION_MOVE:
                            this.x = (int) x - (dificuldade.getSize() / 2);
                            this.y = (int) y - (dificuldade.getSize() / 2);
                            break;
                        case MotionEvent.ACTION_UP:
                            if ((positions[index] != 0) && (positions[i] == 0) && dificuldade.canChange(index, getIndexNone())) {
                                int temp = positions[i];
                                positions[i] = positions[index];
                                positions[index] = temp;
                                checkWin();
                            }
                            index = -1;
                            selecionou = false;
                            break;
                    }
                    invalidate();
                    return true;
                }
            }
        }
        return false;
    }
}
