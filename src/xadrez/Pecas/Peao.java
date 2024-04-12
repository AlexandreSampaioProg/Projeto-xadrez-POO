package xadrez.Pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidadeXadrez;
import xadrez.XadrezPeca;

public class Peao extends XadrezPeca {

    private PartidadeXadrez partidadeXadrez; //associação de objetos

    public Peao(Cor cor, Tabuleiro tabuleiro, PartidadeXadrez partidadeXadrez) {
        super(cor, tabuleiro);
        this.partidadeXadrez = partidadeXadrez; //associação de objetos
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
        Posicao p = new Posicao(0, 0);

        if (getCor() == Cor.BRANCA) {
            p.setValores(posicao.getLinha() - 1, posicao.getColuna());
            if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }
            p.setValores(posicao.getLinha() - 2, posicao.getColuna());
            Posicao p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
            if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p) && getTabuleiro().posicaoExiste(p2) && !getTabuleiro().temUmaPeca(p2) && getContadorDeMovimento() == 0) {
                mat[p.getLinha()][p.getColuna()] = true;
            }
            p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
            if (getTabuleiro().posicaoExiste(p) && temUmaPecaInimiga(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }
            p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
            if (getTabuleiro().posicaoExiste(p) && temUmaPecaInimiga(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }
            
            // movimento especial en passant branco
            if (posicao.getLinha() == 3) {
                
                Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
                
                if (getTabuleiro().posicaoExiste(esquerda) && temUmaPecaInimiga(esquerda) && getTabuleiro().peca(esquerda) == partidadeXadrez.getEnPassantVulneravel()) {
                    mat[esquerda.getLinha() - 1][esquerda.getColuna()] = true;
                }
                
                //direita
                Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
                
                if (getTabuleiro().posicaoExiste(direita) && temUmaPecaInimiga(direita) && getTabuleiro().peca(direita) == partidadeXadrez.getEnPassantVulneravel()) {
                    mat[direita.getLinha() - 1][direita.getColuna()] = true;
                }
            }
        }
        else {
            p.setValores(posicao.getLinha() + 1, posicao.getColuna());
            if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }
            p.setValores(posicao.getLinha() + 2, posicao.getColuna());
            Posicao p2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
            if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p) && getTabuleiro().posicaoExiste(p2) && !getTabuleiro().temUmaPeca(p2) && getContadorDeMovimento() == 0) {
                mat[p.getLinha()][p.getColuna()] = true;
            }
            p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
            if (getTabuleiro().posicaoExiste(p) && temUmaPecaInimiga(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }
            p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
            if (getTabuleiro().posicaoExiste(p) && temUmaPecaInimiga(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }
            
            //movimento especial en passant preto
            if (posicao.getLinha() == 4) {
                Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
                if (getTabuleiro().posicaoExiste(esquerda) && temUmaPecaInimiga(esquerda) && getTabuleiro().peca(esquerda) == partidadeXadrez.getEnPassantVulneravel()) {
                    mat[esquerda.getLinha() + 1][esquerda.getColuna()] = true;
                }
                //direita
                Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
                if (getTabuleiro().posicaoExiste(direita) && temUmaPecaInimiga(direita) && getTabuleiro().peca(direita) == partidadeXadrez.getEnPassantVulneravel()) {
                    mat[direita.getLinha() + 1][direita.getColuna()] = true;
                }
            }
        }
        return mat;
    } 
    @Override
    public String toString() {
        return "P";
    }
}
