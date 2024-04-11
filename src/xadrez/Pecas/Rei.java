package xadrez.Pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidadeXadrez;
import xadrez.XadrezPeca;

public class Rei extends xadrez.XadrezPeca {

    private PartidadeXadrez partidadeXadrez;

    public Rei(Cor cor, Tabuleiro tabuleiro, PartidadeXadrez partidadeXadrez) {
        super(cor, tabuleiro);
        this.partidadeXadrez = partidadeXadrez;
    }

    @Override
    public String toString() {
        return "R";
    }

    private boolean podeMover(Posicao posicao) {
        XadrezPeca p = (XadrezPeca) getTabuleiro().peca(posicao);
        return p == null || p.getCor() != getCor();
    }

    //testar sé é possivel roque
    private boolean testarTorreMovRoque(Posicao posicao) {
        XadrezPeca p = (XadrezPeca) getTabuleiro().peca(posicao);
        return p != null && p instanceof Torre && p.getCor() == getCor() && p.getContadorDeMovimento() == 0;
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
        Posicao p = new Posicao(0, 0);

        //acima
        p.setValores(posicao.getLinha() - 1, posicao.getColuna());
        if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        //esquerda
        p.setValores(posicao.getLinha(), posicao.getColuna() - 1);
        if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        //direita
        p.setValores(posicao.getLinha(), posicao.getColuna() + 1);
        if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        //abaixo
        p.setValores(posicao.getLinha() + 1, posicao.getColuna());
        if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        //diagonal acima esquerda
        p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
        if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        //diagonal acima direita
        p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
        if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        //diagonal acima esquerda
        p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
        if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        //diagonal abaixo direita
        p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
        if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        //movimento especial do roque
        if (getContadorDeMovimento() == 0 && !partidadeXadrez.getCheck()) {
            //movimento especial roque pequeno lado sem rainha
            Posicao posicaoTorre1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 3);
            if (testarTorreMovRoque(posicaoTorre1)) {
                Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
                Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() + 2);

                if (getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null) {
                    mat[posicao.getLinha()][posicao.getColuna() + 2] = true;
                }
            }
        }

        if (getContadorDeMovimento() == 0 && !partidadeXadrez.getCheck()) {
            Posicao posicaoTorre2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 4);
            if (testarTorreMovRoque(posicaoTorre2)) {
                Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna()-1);
                Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna()-2); 
                Posicao p3 = new Posicao(posicao.getLinha(), posicao.getColuna()-3);       
                if (getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null && getTabuleiro().peca(p3) == null) {
                    mat[posicao.getLinha()][posicao.getColuna() + 3] = true;
                }
            }
        }
        return mat;
    }
}
