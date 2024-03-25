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
        validarPosicaoAtual(atual);
        validarPosicaoDeDestino(atual,destino);
        Peca pecaCapturada = fazerMovimento(atual, destino);
        return (XadrezPeca) pecaCapturada;
    }
    
    public boolean[][] movimentoPossiveis(XadrezPosicao posicaoAtual){
        Posicao posicao = posicaoAtual.toPosicao();
        validarPosicaoAtual(posicao);
        return tabuleiro.peca(posicao).movimentosPossiveis();
    } 
    
    private Peca fazerMovimento(Posicao atual, Posicao destino) {
        Peca p = tabuleiro.removerPeca(atual);
        Peca pecaCapturada = tabuleiro.removerPeca(destino);
        tabuleiro.colocarPeca(p, destino);
        return pecaCapturada;
    }

    private void validarPosicaoAtual(Posicao posicao) {
        if (!tabuleiro.temUmaPeca(posicao)) {
            throw new ExecaoDoXadrez("não há peça nessa posicao");
        }
        if (!tabuleiro.peca(posicao).temPossibilidadeDeMovimentacao()) {
            throw new  ExecaoDoXadrez("Não há movimentações possiveis");
        }
    }

    private void validarPosicaoDeDestino(Posicao atual, Posicao destino){
        if (!tabuleiro.peca(atual).movimentoPossivel(destino)) {
            throw new ExecaoDoXadrez("A peca não pode ir para esse destino");
        }
    }
    
    
    private void colocarUmaNovaPeca(char coluna, int linha, XadrezPeca peca) {
        tabuleiro.colocarPeca(peca, new XadrezPosicao(coluna, linha).toPosicao());
    }
}
