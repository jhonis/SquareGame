package br.com.jgames.squaregame.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import br.com.jgames.squaregame.R;

import java.util.*;

public class TouchImageView extends View {
    private static final String CATEGORIA = "squareGame";
    private Context context;
    private Map<Integer, Drawable> images;
    private List<Rect> coords;
    private Integer[] positions;
    int x, y, index, index_none, magic, d_size;
    private boolean selecionou;

    public TouchImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        startGame();
    }

    public TouchImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        startGame();
    }

    public TouchImageView(Context context) {
        super(context);
        this.context = context;
        startGame();
    }

    public void startGame() {
        coords = new ArrayList<Rect>();
        coords.add(new Rect(0, 0, 150, 150));
        coords.add(new Rect(150, 0, 300, 150));
        coords.add(new Rect(300, 0, 450, 150));
        coords.add(new Rect(0, 150, 150, 300));
        coords.add(new Rect(150, 150, 300, 300));
        coords.add(new Rect(300, 150, 450, 300));
        coords.add(new Rect(0, 300, 150, 450));
        coords.add(new Rect(150, 300, 300, 450));
        coords.add(new Rect(300, 300, 450, 450));

        positions = new Integer[9];
        for (int i = 0; i < positions.length; i++) {
            positions[i] = i;
        }
        random();
        fillImages();
        d_size = 150;
        magic = (int) Math.sqrt(images.size());
        setFocusable(true);
    }

    private void fillImages() {
        images = new LinkedHashMap<Integer, Drawable>();
        for (int i = 0; i < positions.length; i++) {
            Drawable temp;
            switch (i) {
                case 0:
                    temp = context.getResources().getDrawable(R.drawable.img1);
                    break;
                case 1:
                    temp = context.getResources().getDrawable(R.drawable.img2);
                    break;
                case 2:
                    temp = context.getResources().getDrawable(R.drawable.img3);
                    break;
                case 3:
                    temp = context.getResources().getDrawable(R.drawable.img4);
                    break;
                case 4:
                    temp = context.getResources().getDrawable(R.drawable.img5);
                    break;
                case 5:
                    temp = context.getResources().getDrawable(R.drawable.img6);
                    break;
                case 6:
                    temp = context.getResources().getDrawable(R.drawable.img7);
                    break;
                case 7:
                    temp = context.getResources().getDrawable(R.drawable.img8);
                    break;
                default:
                    temp = context.getResources().getDrawable(R.drawable.img_none);
                    temp.setVisible(false, false);
                    index_none = positions[i];
            }
            images.put(positions[i], temp);
        }
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

        for (int i = 0; i < images.size(); i++) {
            if (i != index || !selecionou) {
                images.get(i).setBounds(coords.get(i));
                images.get(i).draw(canvas);
            }
        }
        if (selecionou && images.get(index).isVisible() && canChange(index)) {
            images.get(index).setBounds(x, y, x + d_size, y + d_size);
            images.get(index).draw(canvas);
        }
    }

    public boolean canChange(int i) {
        int magic_mod = index_none % magic;
        return ((i == (index_none - magic)) || // posição superior ao espaço vazio
                (i == (index_none + magic)) || // posição inferior ao espaço vazio
                ((magic_mod == 2) && (i == index_none - 1)) || // posição esquerda ao espaço vazio caso ele esteja na borda direita
                ((magic_mod == 0) && (i == index_none + 1)) || // posição direita ao espaço vazio caso ele esteja na borda esquerda
                ((magic_mod == 1) && ((i == index_none + 1) || (i == index_none - 1)))); // posição direita e esquerda ao espaço vazio caso ele esteja no centro
    }

    public void checkWin() {
        boolean resultado = true;
        for (int i = 0; i < images.size(); i++) {
            if (i != positions[i]) {
                resultado = false;
            }
        }
        if (resultado) {
            Log.i(CATEGORIA, "Você ganhou!!!");
            Toast.makeText(context, "Você ganhou!!!", Toast.LENGTH_SHORT).show();
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
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        selecionou = true;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        this.x = (int) x - (d_size / 2);
                        this.y = (int) y - (d_size / 2);
                        break;
                    case MotionEvent.ACTION_UP:
                        if (images.get(index).isVisible() && !images.get(i).isVisible() && canChange(index)) {
                            Drawable temp = images.get(i);
                            images.put(i, images.get(index));
                            images.put(index, temp);
                            index_none = index;
                            for (int j = 0; j < positions.length; j++) {
                                if (positions[j] == i) {
                                    positions[j] = index;
                                } else if (positions[j] == index) {
                                    positions[j] = i;
                                }
                            }
                            checkWin();
                        }
                        selecionou = false;
                        break;
                }
                invalidate();
                return true;
            }
        }
        return false;
    }
}
