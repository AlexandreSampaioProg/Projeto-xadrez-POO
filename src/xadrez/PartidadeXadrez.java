package xadrez;

import java.util.ArrayList;
import java.util.List;
import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Pecas.Torre;
import xadrez.Pecas.Rei;

public class PartidadeXadrez {

    private int turno;
    private Cor jogadorAtual;
    private Tabuleiro tabuleiro;
    
    private List<Peca> pecasNoTabuleiro = new ArrayList<>();
    private List<Peca> pecasCapturadas = new ArrayList<>();

    public PartidadeXadrez() {
        tabuleiro = new Tabuleiro(8, 8);
        turno = 1;
        jogadorAtual = Cor.BRANCA;
        posicaoDefalt();
    }

    public int getTurno() {
        return turno;
    }
    
    public Cor getJogadorAtual() {
        return jogadorAtual;
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
        colocarUmaNovaPeca('f', 1, new Torre(Cor.PRETA, tabuleiro));
        colocarUmaNovaPeca('g', 1, new Torre(Cor.BRANCA, tabuleiro));
        colocarUmaNovaPeca('h', 1, new Rei(Cor.PRETA, tabuleiro));
    }

    public XadrezPeca movimentacao(XadrezPosicao posicaoAtual, XadrezPosicao posicaoDeDestino) {
        Posicao atual = posicaoAtual.toPosicao();
        Posicao destino = posicaoDeDestino.toPosicao();
        validarPosicaoAtual(atual);
        validarPosicaoDeDestino(atual, destino);
        Peca pecaCapturada = fazerMovimento(atual, destino);
        proximoTurno();
        return (XadrezPeca) pecaCapturada;
    }

    public boolean[][] movimentoPossiveis(XadrezPosicao posicaoAtual) {
        Posicao posicao = posicaoAtual.toPosicao();
        validarPosicaoAtual(posicao);
        return tabuleiro.peca(posicao).movimentosPossiveis();
    }

    private Peca fazerMovimento(Posicao atual, Posicao destino) {
        Peca p = tabuleiro.removerPeca(atual);
        Peca pecaCapturada = tabuleiro.removerPeca(destino);
        tabuleiro.colocarPeca(p, destino);
        if (pecaCapturada != null) {
            pecasNoTabuleiro.remove(pecaCapturada);
            pecasCapturadas.add(pecaCapturada);
        }
        return pecaCapturada;
    }

    private void validarPosicaoAtual(Posicao posicao) {
        if (!tabuleiro.temUmaPeca(posicao)) {
            throw new ExecaoDoXadrez("não há peça nessa posicao");
        }
        if (jogadorAtual != ((XadrezPeca) tabuleiro.peca(posicao)).getCor()) {
             throw new ExecaoDoXadrez("A peca escolhida não é sua");
        }
        if (!tabuleiro.peca(posicao).temPossibilidadeDeMovimentacao()) {
            throw new ExecaoDoXadrez("Não há movimentações possiveis");
        }
    }

    private void validarPosicaoDeDestino(Posicao atual, Posicao destino) {
        if (!tabuleiro.peca(atual).movimentoPossivel(destino)) {
            throw new ExecaoDoXadrez("A peca não pode ir para esse destino");
        }
    }

    private void proximoTurno() {
        turno++;
        jogadorAtual = (jogadorAtual == Cor.BRANCA) ? Cor.PRETA : Cor.BRANCA; //seo jogador atual for branco troca para o preto caso contrario ele será branco
    }

    private void colocarUmaNovaPeca(char coluna, int linha, XadrezPeca peca) {
        tabuleiro.colocarPeca(peca, new XadrezPosicao(coluna, linha).toPosicao());
        pecasNoTabuleiro.add(peca);
    }
}
