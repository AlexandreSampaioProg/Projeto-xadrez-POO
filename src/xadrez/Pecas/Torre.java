package xadrez.Pecas;

import tabuleiro.Tabuleiro;
import xadrez.Cor;

public class Torre extends xadrez.XadrezPeca {

    public Torre(Cor cor, Tabuleiro tabuleiro) {
        super(cor, tabuleiro);
    }

    @Override
    public String toString() {
        return "T";
    }

}
