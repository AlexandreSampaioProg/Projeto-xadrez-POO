package xadrez.Pecas;

import tabuleiro.Posicao;
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

    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        Posicao p = new Posicao(0, 0);

        //Acima
        p.setValores(posicao.getLinha() - 1, posicao.getColuna());
        while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
            p.setLinha(p.getLinha() - 1);
        }
        if (getTabuleiro().posicaoExiste(p) && temUmaPecaInimiga(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        //esquerda
        p.setValores(posicao.getLinha(), posicao.getColuna() - 1);
        while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
            p.setColuna(p.getColuna() - 1);
        }

        if (getTabuleiro().posicaoExiste(p) && temUmaPecaInimiga(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        //direita
        p.setValores(posicao.getLinha(), posicao.getColuna() + 1);
        while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
            p.setColuna(p.getColuna() + 1);
        }

        if (getTabuleiro().posicaoExiste(p) && temUmaPecaInimiga(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        //baixo
        p.setValores(posicao.getLinha() + 1, posicao.getColuna());
        while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
            p.setLinha(p.getLinha() + 1);
        }

        if (getTabuleiro().posicaoExiste(p) && temUmaPecaInimiga(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
        return mat;
    }
}
