package br.com.jgames.squaregame.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import br.com.jgames.squaregame.R;
import br.com.jgames.squaregame.enums.Dificuldade;
import br.com.jgames.squaregame.enums.Direcao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TouchImageView extends View {
    private static final String CATEGORIA = "squareGame";
    private Context context;
    private List<Rect> coords;
    private Integer[] positions;
    private Dificuldade dificuldade;
    private int x, y, h, w, index = -1;
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

    public void startGame(Dificuldade dificuldade, int barHeight, int width, int height) {
        this.dificuldade = dificuldade;
        coords = new ArrayList<Rect>();
        Log.i(CATEGORIA, ">>>> Teste XXX");
        height -= barHeight;
        h = height;
        w = width;
        int a, b;
        for (int i = 0; i < dificuldade.getTamanhoVetor(); i++) {
            a = (dificuldade.getMagicMod(i) * dificuldade.getSize(w, h));
            b = dificuldade.getMagicDiv(i) * dificuldade.getSize(w, h);
            coords.add(new Rect(a + 1, b + 1, a + dificuldade.getSize(w, h) - 1, b + dificuldade.getSize(w, h) - 1));
        }

        positions = new Integer[dificuldade.getTamanhoVetor()];
        for (int i = 0; i < positions.length; i++) {
            positions[i] = i;
        }
        random();
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
            paintMove.setColor(Color.LTGRAY);
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
                int sizeTemp = dificuldade.getSize(w, h);
                canvas.drawRect(x, y, x + sizeTemp, y + sizeTemp, paintMove);
//                canvas.drawText(String.valueOf(positions[index]), coords.get(index).left + 50, coords.get(index).top + 50, paintText);
//                images.get(index).setBounds(x, y, x + dificuldade.getSize(w, h), y + dificuldade.getSize(w, h));
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
        int xMove = (int) motionEvent.getX();
        int yMove = (int) motionEvent.getY();
        Rect r = null;
        for (int i = 0; i < coords.size(); i++) {
            r = coords.get(i);
            if (r.contains(xMove, yMove)) {
                int tamanhoMedio = dificuldade.getSize(w, h) / 2;
                y = r.top;
                x = r.left;
                if (!selecionou) {
                    index = i;
                }
                if (dificuldade.canChange(index, getIndexNone())) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            selecionou = true;
                            break;
                        case MotionEvent.ACTION_MOVE:
                            Direcao direcao = dificuldade.getDirecaoIndexNone(index, getIndexNone());
                            Rect rn = coords.get(getIndexNone());
                            Rect moveField = new Rect(Math.min(rn.left, r.left), Math.min(rn.top, r.top),
                                    Math.max(rn.right, r.right), Math.max(rn.bottom, r.bottom));
                            if (moveField.contains(xMove, yMove)) {
                                if (direcao.equals(Direcao.DIREITA) || direcao.equals(Direcao.ESQUERDA)) {
                                    y = rn.top;
                                    x = xMove - tamanhoMedio;
                                    if (direcao.equals(Direcao.DIREITA)) {
                                        if (xMove > rn.right) {
                                            x = rn.left + tamanhoMedio;
                                        }
                                        if (xMove < r.left) {
                                            x = r.left - tamanhoMedio;
                                        }
                                    }
                                    if (direcao.equals(Direcao.ESQUERDA)) {
                                        if (xMove < rn.left) {
                                            x = rn.left - tamanhoMedio;
                                        }
                                        if (xMove > r.right) {
                                            x = r.left + tamanhoMedio;
                                        }
                                    }
                                }
                                if (direcao.equals(Direcao.ABAIXO) || direcao.equals(Direcao.ACIMA)) {
                                    x = rn.left;
                                    y = yMove - tamanhoMedio;
                                    if (direcao.equals(Direcao.ACIMA)) {
                                        if (yMove < rn.top) {
                                            y = rn.top - tamanhoMedio;
                                        }
                                        if (yMove > r.bottom) {
                                            y = r.top + tamanhoMedio;
                                        }
                                    }
                                    if (direcao.equals(Direcao.ABAIXO)) {
                                        if (yMove > rn.bottom) {
                                            y = rn.top + tamanhoMedio;
                                        }
                                        if (yMove < r.top) {
                                            y = r.top - tamanhoMedio;
                                        }
                                    }
                                }
                            }
                            break;
                        case MotionEvent.ACTION_UP:
                            if ((positions[index] != 0) && (positions[i] == 0) && dificuldade.canChange(index, getIndexNone())) {
                                int temp = positions[i];
                                positions[i] = positions[index];
                                positions[index] = temp;
                                setMovimentos(getMovimentos() + 1);
                                checkWin();
                            }
                            index = -1;
                            selecionou = false;
                            break;
                    }
                    invalidate();
                    return true;
                } else {
                    index = -1;
                    selecionou = false;
                }
            }
        }
        if (r == null) {
            selecionou = false;
            index = -1;
            invalidate();
            return true;
        }
        return false;
    }

    public Integer getMovimentos() {
        TextView mov = (TextView) ((View) this.getParent()).findViewById(R.id.movimentos);
        return Integer.parseInt(mov.getText().toString());
    }

    public void setMovimentos(Integer movimentos) {
        TextView mov = (TextView) ((View) this.getParent()).findViewById(R.id.movimentos);
        mov.setText("" + movimentos);
    }

    public int getBottomBorder() {
        int retorno = 0;
        for (Rect r : coords) {
            retorno = Math.max(r.bottom, retorno);
        }
        return retorno;
    }

    public int getTopBorder() {
        int retorno = 100000;
        for (Rect r : coords) {
            retorno = Math.min(r.top, retorno);
        }
        return retorno;
    }

    public int getRightBorder() {
        int retorno = 0;
        for (Rect r : coords) {
            retorno = Math.max(r.right, retorno);
        }
        return retorno;
    }

    public int getLeftBorder() {
        int retorno = 100000;
        for (Rect r : coords) {
            retorno = Math.min(r.left, retorno);
        }
        return retorno;
    }

    public void stopMove() {
        selecionou = false;
        index = -1;
        invalidate();
    }

    public String getPositions() {
        String s = Arrays.toString(positions);
        return s.substring(1, s.length() - 1);
    }

    public void setPositions(String sequencia) {
        if (sequencia != null && !sequencia.equals("")) {
            String[] temp = sequencia.split(",");
            for (int i = 0; i < temp.length; i++) {
                positions[i] = Integer.valueOf(temp[i].trim());
            }
            invalidate();
        }
    }
}