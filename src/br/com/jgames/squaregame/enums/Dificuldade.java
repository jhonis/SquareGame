package br.com.jgames.squaregame.enums;

import android.view.Display;

public enum Dificuldade {

    FACIL(9, 150, 87, 53, 0),
    MEDIO(16, 120, 65, 38, 8),
    DIFICIL(25, 100, 65, 38, 10);

    private int tamanhoVetor;
    private int d_size;
    private int textoPaddingTop;
    private int textoPaddingLeft;
    private int overSize;

    private Dificuldade(int tamanhoVetor, int d_size, int textoPaddingTop, int textoPaddingLeft, int overSize) {
        this.tamanhoVetor = tamanhoVetor;
        this.d_size = d_size;
        this.textoPaddingTop = textoPaddingTop;
        this.textoPaddingLeft = textoPaddingLeft;
        this.overSize = overSize;
    }

    public int getTamanhoVetor() {
        return tamanhoVetor;
    }

    public int getOverSize() {
        return overSize;
    }

    public int getSize(int width, int height) {
        if (width > height) {
            return (int) (d_size * (height / 450.0)) - overSize;
        } else {
            return (int) (d_size * (width / 450.0)) - overSize;
        }
    }

    public int getTextoPaddingTop() {
        return textoPaddingTop;
    }

    public int getTextoPaddingLeft(int valor) {
        if (valor < 10) {
            return textoPaddingLeft;
        } else {
            return textoPaddingLeft - 20;
        }
    }

    public int getMagic() {
        return (int) Math.sqrt(tamanhoVetor);
    }

    public int getMagicMod(int index) {
        return index % getMagic();
    }

    public int getMagicDiv(int index) {
        return (int) (index / getMagic());
    }

    public boolean canChange(int i, int index_none) {
        int magic_mod = getMagicMod(index_none);
        if (this.equals(FACIL)) {
            return ((i == (index_none - getMagic())) || // posição superior ao espaço vazio
                    (i == (index_none + getMagic())) || // posição inferior ao espaço vazio
                    ((magic_mod == 2) && (i == index_none - 1)) || // posição esquerda ao espaço vazio caso ele esteja na borda direita
                    ((magic_mod == 0) && (i == index_none + 1)) || // posição direita ao espaço vazio caso ele esteja na borda esquerda
                    ((magic_mod == 1) && ((i == index_none + 1) || (i == index_none - 1)))); // posição direita e esquerda ao espaço vazio caso ele esteja no centro
        } else if (this.equals(MEDIO)) {
            return ((i == (index_none - getMagic())) || // posição superior ao espaço vazio
                    (i == (index_none + getMagic())) || // posição inferior ao espaço vazio
                    ((magic_mod == 3) && (i == index_none - 1)) || // posição esquerda ao espaço vazio caso ele esteja na borda direita
                    ((magic_mod == 0) && (i == index_none + 1)) || // posição direita ao espaço vazio caso ele esteja na borda esquerda
                    ((magic_mod == 1 || magic_mod == 2) && ((i == index_none + 1) || (i == index_none - 1)))); // posição direita e esquerda ao espaço vazio caso ele esteja no centro
        } else if (this.equals(DIFICIL)) {
            return ((i == (index_none - getMagic())) || // posição superior ao espaço vazio
                    (i == (index_none + getMagic())) || // posição inferior ao espaço vazio
                    ((magic_mod == 4) && (i == index_none - 1)) || // posição esquerda ao espaço vazio caso ele esteja na borda direita
                    ((magic_mod == 0) && (i == index_none + 1)) || // posição direita ao espaço vazio caso ele esteja na borda esquerda
                    ((magic_mod == 1 || magic_mod == 2 || magic_mod == 3) && ((i == index_none + 1) || (i == index_none - 1)))); // posição direita e esquerda ao espaço vazio caso ele esteja no centro
        }
        return false;
    }

    public Direcao getDirecaoIndexNone(int i, int index_none) {
        int magic_mod = getMagicMod(index_none);
        if (i == (index_none - getMagic())) {
            return Direcao.ABAIXO;
        } else if (i == (index_none + getMagic())) {
            return Direcao.ACIMA;
        } else {
            if (this.equals(FACIL)) {
                if (((magic_mod == 2) && (i == index_none - 1)) || ((magic_mod == 1) && (i == index_none - 1))) {
                    return Direcao.DIREITA;
                } else if (((magic_mod == 0) && (i == index_none + 1)) || ((magic_mod == 1) && (i == index_none + 1))) {
                    return Direcao.ESQUERDA;
                }
            } else if (this.equals(MEDIO)) {
                if (((magic_mod == 3) && (i == index_none - 1)) || ((magic_mod == 1 || magic_mod == 2) && (i == index_none - 1))) {
                    return Direcao.DIREITA;
                } else if (((magic_mod == 0) && (i == index_none + 1)) || ((magic_mod == 1 || magic_mod == 2) && (i == index_none + 1))) {
                    return Direcao.ESQUERDA;
                }
            } else if (this.equals(DIFICIL)) {
                if (((magic_mod == 4) && (i == index_none - 1)) || ((magic_mod == 1 || magic_mod == 2 || magic_mod == 3) && (i == index_none + 1)))
                    return Direcao.DIREITA;
            } else if (((magic_mod == 0) && (i == index_none + 1)) || ((magic_mod == 1 || magic_mod == 2 || magic_mod == 3) && (i == index_none + 1))) {
                return Direcao.ESQUERDA;
            }
        }
        return null;
    }
}
