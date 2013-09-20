package br.com.jgames.squaregame.enums;

public enum Dificuldade {

    FACIL(9, 150, 87, 55),
    MEDIO(16, 125, 65, 40),
    DIFICIL(25, 100, 65, 40);

    private int tamanhoVetor;
    private int d_size;
    private int textoPaddingTop;
    private int textoPaddingLeft;

    private Dificuldade(int tamanhoVetor, int d_size, int textoPaddingTop, int textoPaddingLeft) {
        this.tamanhoVetor = tamanhoVetor;
        this.d_size = d_size;
        this.textoPaddingTop = textoPaddingTop;
        this.textoPaddingLeft = textoPaddingLeft;
    }

    public int getTamanhoVetor() {
        return tamanhoVetor;
    }

    public int getSize() {
        return d_size;
    }

    public int getTextoPaddingTop() {
        return textoPaddingTop;
    }

    public int getTextoPaddingLeft(int valor) {
        if (valor < 10) {
            return textoPaddingLeft;
        } else {
            return textoPaddingLeft - 22;
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
}
