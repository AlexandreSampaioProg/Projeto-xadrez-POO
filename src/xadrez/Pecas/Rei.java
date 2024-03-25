package xadrez.Pecas;

import tabuleiro.Tabuleiro;
import xadrez.Cor;

public class Rei extends xadrez.XadrezPeca{
    
    public Rei(Cor cor, Tabuleiro tabuleiro) {
        super(cor, tabuleiro);
    }
    
    @Override
    public String toString(){
        return "R";
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] mat = new boolean[getTabuleiro().getColunas()][getTabuleiro().getColunas()];
        return mat;
    }
    
}
