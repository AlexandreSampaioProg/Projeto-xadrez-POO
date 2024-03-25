package xadrez;

import tabuleiro.Peca;
import tabuleiro.Tabuleiro;

public class XadrezPeca extends Peca {

    private Cor cor;

    public XadrezPeca(Cor cor, Tabuleiro tabuleiro) {
        super(tabuleiro);
        this.cor = cor;
    }

    public Cor getCor() {
        return cor;
    }

}
