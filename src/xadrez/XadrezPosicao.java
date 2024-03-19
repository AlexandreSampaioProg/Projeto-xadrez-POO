package xadrez;

import tabuleiro.Posicao;

public class XadrezPosicao {

    private char coluna;
    private int linha;

    public XadrezPosicao(char coluna, int linha) {
        if (coluna < 'a' || coluna > 'h' || linha < 1 || linha > 8) {
            throw new ExecaoDoXadrez("Erro ao instanciar XadrezPosição, Valores validos: A1 A8 até H1 H8");
        }
        this.coluna = coluna;
        this.linha = linha;
    }

    public char getColuna() {
        return coluna;
    }

    public int getLinha() {
        return linha;
    }

    protected Posicao toPosicao() {
        return new Posicao(8 - linha, coluna - 'a');
    }

    protected XadrezPosicao daPosicao(Posicao posicao) {
        return new XadrezPosicao((char)('a' - posicao.getColuna()), 8 - posicao.getLinha());                    
    }
    
    @Override
    public String toString(){
    return "" + coluna + linha; //concatenar as Strings se retirar o "" o compilador não irá aceitar imprimir a linha e a coluna
}
}
