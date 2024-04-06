package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Pecas.Torre;
import xadrez.Pecas.Rei;

public class PartidadeXadrez {

    private int turno;
    private Cor jogadorAtual;
    private Tabuleiro tabuleiro;
    private boolean check;
    private boolean checkMate;

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

    public boolean getCheck() {
        return check;
    }
    
    public boolean getCheckMate(){
        return checkMate;
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
        colocarUmaNovaPeca('g', 6, new Torre(Cor.BRANCA, tabuleiro));
        colocarUmaNovaPeca('g', 7, new Torre(Cor.BRANCA, tabuleiro));
        colocarUmaNovaPeca('h', 1, new Rei(Cor.PRETA, tabuleiro));
        colocarUmaNovaPeca('h', 8, new Rei(Cor.BRANCA, tabuleiro));
        
    }

    public XadrezPeca movimentacao(XadrezPosicao posicaoAtual, XadrezPosicao posicaoDeDestino) {
        Posicao atual = posicaoAtual.toPosicao();
        Posicao destino = posicaoDeDestino.toPosicao();
        validarPosicaoAtual(atual);
        validarPosicaoDeDestino(atual, destino);
        Peca pecaCapturada = fazerMovimento(atual, destino);       
        if (testarCheck(jogadorAtual)) {
            desfazerMovimento(atual, destino, pecaCapturada);
            throw new ExecaoDoXadrez("O rei está em cheque mova outra peca");
        }
        
        check = (testarCheck(inimigo(jogadorAtual))) ? true : false;
        
        if (testarCheckMate(inimigo(jogadorAtual))) {
            checkMate = true;
        }
        
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

    private void desfazerMovimento(Posicao atual, Posicao destino, Peca pecaCapturada) {
        Peca p = tabuleiro.removerPeca(destino);
        tabuleiro.colocarPeca(p, atual);
        if (pecaCapturada != null) {
            tabuleiro.colocarPeca(pecaCapturada, destino);
            pecasCapturadas.remove(pecaCapturada);
            pecasNoTabuleiro.add(pecaCapturada);
        }

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

    private Cor inimigo(Cor cor) {
        return (cor == Cor.BRANCA) ? Cor.PRETA : Cor.BRANCA;
    }

    private XadrezPeca rei(Cor cor) {
        List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((XadrezPeca) x).getCor() == cor).collect(Collectors.toList());
        for (Peca p : list) {
            if (p instanceof Rei) {
                return (XadrezPeca) p;
            }
        }
        throw new IllegalStateException("O sistema de Xadrez tem um problema, o rei " + cor + "nao esta no tabuleiro");
    }

    private boolean testarCheck(Cor cor) {
        Posicao posicaoDoRei = rei(cor).getXadrezPosicao().toPosicao();
        List<Peca> pecasInimigas = pecasNoTabuleiro.stream().filter(x -> ((XadrezPeca) x).getCor() == inimigo(cor)).collect(Collectors.toList());
        for (Peca p : pecasInimigas) {
            boolean[][] mat = p.movimentosPossiveis();
            if (mat[posicaoDoRei.getLinha()][posicaoDoRei.getColuna()]) {
                return true;
            }
        }
        return false;
    }
    
    private boolean testarCheckMate(Cor cor){
        if (!testarCheck(cor)) {
            return false;
        }
        List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((XadrezPeca) x).getCor() == cor).collect(Collectors.toList());
        for (Peca p : list) {
            boolean[][] mat = p.movimentosPossiveis();
            for (int i = 0; i < tabuleiro.getLinhas(); i++) {
                for (int j = 0; j < tabuleiro.getColunas(); j++) {
                    if (mat[i][j]){
                        Posicao atual = ((XadrezPeca)p).getXadrezPosicao().toPosicao();
                        Posicao destino = new Posicao(i, j);
                        Peca pecaCapturada = fazerMovimento(atual, destino);
                        boolean testarCheck = testarCheck(cor);
                        desfazerMovimento(atual, destino, pecaCapturada);
                        if (!testarCheck) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private void colocarUmaNovaPeca(char coluna, int linha, XadrezPeca peca) {
        tabuleiro.colocarPeca(peca, new XadrezPosicao(coluna, linha).toPosicao());
        pecasNoTabuleiro.add(peca);
    }
}
