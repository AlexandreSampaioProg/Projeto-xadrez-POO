package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Pecas.Rei;
import xadrez.Pecas.Torre;

public class PartidadeXadrez {

    private Tabuleiro tabuleiro;

    public PartidadeXadrez() {
        tabuleiro = new Tabuleiro(8, 8);
        posicaoDefalt();
    }

    public XadrezPeca[][] getPecas() {

        XadrezPeca[][] partida = new XadrezPeca[tabuleiro.getLinhas()][tabuleiro.getColunas()];

        for (int i = 0; i < tabuleiro.getColunas(); i++) {
            for (int j = 0; j < tabuleiro.getLinhas(); j++) {
                partida[i][j] = (XadrezPeca) tabuleiro.peca(i, j);
            }
        }
        return partida;
    }

    private void posicaoDefalt() {
        colocarUmaNovaPeca('a', 1, new Rei(Cor.PRETA, tabuleiro));
        colocarUmaNovaPeca('b', 2, new Torre(Cor.BRANCA, tabuleiro));
        colocarUmaNovaPeca('c', 1, new Rei(Cor.PRETA, tabuleiro));
        colocarUmaNovaPeca('d', 2, new Torre(Cor.BRANCA, tabuleiro));
        colocarUmaNovaPeca('f', 1, new Rei(Cor.PRETA, tabuleiro));
        colocarUmaNovaPeca('g', 2, new Torre(Cor.BRANCA, tabuleiro));
        colocarUmaNovaPeca('h', 1, new Rei(Cor.PRETA, tabuleiro));
    }

    public XadrezPeca movimentacao(XadrezPosicao posicaoAtual, XadrezPosicao posicaoDeDestino) {
        Posicao atual = posicaoAtual.toPosicao();
        Posicao destino = posicaoDeDestino.toPosicao();
        validarPosicao(atual);
        Peca pecaCapturada = fazerMovimento(atual, destino);
        return (XadrezPeca) pecaCapturada;
    }

    private Peca fazerMovimento(Posicao atual, Posicao destino) {
        Peca p = tabuleiro.removerPeca(atual);
        Peca pecaCapturada = tabuleiro.removerPeca(destino);
        tabuleiro.colocarPeca(p, destino);
        return pecaCapturada;
    }

    private void validarPosicao(Posicao posicao) {
        if (!tabuleiro.temUmaPeca(posicao)) {
            throw new ExecaoDoXadrez("não há peça nessa posicao");
        }
    }

    private void colocarUmaNovaPeca(char coluna, int linha, XadrezPeca peca) {
        tabuleiro.colocarPeca(peca, new XadrezPosicao(coluna, linha).toPosicao());
    }
}
