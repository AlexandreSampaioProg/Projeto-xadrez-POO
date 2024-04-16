package xadrez;

import com.sun.source.tree.IfTree;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Pecas.Bispo;
import xadrez.Pecas.Cavalo;
import xadrez.Pecas.Peao;
import xadrez.Pecas.Rainha;
import xadrez.Pecas.Torre;
import xadrez.Pecas.Rei;

public class PartidadeXadrez {

    private int turno;
    private Cor jogadorAtual;
    private Tabuleiro tabuleiro;
    private boolean check;
    private boolean checkMate;
    private XadrezPeca enPassantVulneravel;
    private XadrezPeca promocao;

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

    public boolean getCheckMate() {
        return checkMate;
    }

    public XadrezPeca getEnPassantVulneravel() {
        return enPassantVulneravel;
    }

    public XadrezPeca getpromocao() {
        return promocao;
    }

    public XadrezPeca[][] getPecas() {

        XadrezPeca[][] mat = new XadrezPeca[tabuleiro.getLinhas()][tabuleiro.getColunas()];

        for (int i = 0; i < tabuleiro.getColunas(); i++) {
            for (int j = 0; j < tabuleiro.getLinhas(); j++) {
                mat[i][j] = (XadrezPeca) tabuleiro.peca(i, j);
            }
        }
        return mat;
    }

    public boolean[][] movimentoPossiveis(XadrezPosicao posicaoAtual) {
        Posicao posicao = posicaoAtual.toPosicao();
        validarPosicaoAtual(posicao);
        return tabuleiro.peca(posicao).movimentosPossiveis();
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

        XadrezPeca pecaMovida = (XadrezPeca) tabuleiro.peca(destino);

        /// movimento especial promoção
        promocao = null;
        if (pecaMovida instanceof Peao) {
            if (pecaMovida.getCor() == Cor.BRANCA && destino.getLinha() == 0 || pecaMovida.getCor() == Cor.PRETA && destino.getLinha() == 7) {
                promocao = (XadrezPeca) tabuleiro.peca(destino);
                promocao = recolocarPecaPromovida("Q");
            }
        }

        check = (testarCheck(inimigo(jogadorAtual))) ? true : false;

        if (testarCheck(inimigo(jogadorAtual))) {
            checkMate = true;
        } else {
            proximoTurno();
        }

        // movimento especial en Passant
        if (pecaMovida instanceof Peao && (destino.getLinha() == atual.getLinha() - 2 || destino.getLinha() == atual.getLinha() + 2)) {
            enPassantVulneravel = pecaMovida;
        } else {
            enPassantVulneravel = null;
        }
        return (XadrezPeca) pecaCapturada;
    }

    public XadrezPeca recolocarPecaPromovida(String tipo) {
        if (promocao == null) {
            throw new ExecaoDoXadrez("não ha peca para ser promovida");
        }
        if (!tipo.equals("Q") && !tipo.equals("T") && !tipo.equals("B") && !tipo.equals("C")) {
            throw new ExecaoDoXadrez("Essa peca nao participa da promocao");
        }
        Posicao pos = promocao.getXadrezPosicao().toPosicao();
        Peca p = tabuleiro.removerPeca(pos);
        pecasNoTabuleiro.remove(p);
        
        XadrezPeca novaPeca = novaPeca(tipo, promocao.getCor());
        
        tabuleiro.colocarPeca(novaPeca, pos);
        pecasNoTabuleiro.add(novaPeca);
        
        return novaPeca;
    }
    
    private XadrezPeca novaPeca(String tipo,Cor cor){
        if (tipo.equals("B")) return new Bispo(cor, tabuleiro);
        if (tipo.equals("C")) return new Cavalo(cor, tabuleiro);
        if (tipo.equals("T")) return new Torre(cor, tabuleiro);
        return new Rainha(cor, tabuleiro);        
    }

    private Peca fazerMovimento(Posicao atual, Posicao destino) {
        XadrezPeca p = (XadrezPeca) tabuleiro.removerPeca(atual); //downcasting
        p.aumentarContadorDeMovimento();
        Peca pecaCapturada = tabuleiro.removerPeca(destino);
        tabuleiro.colocarPeca(p, destino); //upcasting
        if (pecaCapturada != null) {
            pecasNoTabuleiro.remove(pecaCapturada);
            pecasCapturadas.add(pecaCapturada);
        }
        //roque pequeno movendo manualmente
        if (p instanceof Rei && destino.getColuna() == atual.getColuna() + 2) {
            Posicao atualTorre = new Posicao(atual.getLinha(), atual.getColuna() + 3);
            Posicao destinoTorre = new Posicao(atual.getLinha(), atual.getColuna() + 1);
            XadrezPeca torre = (XadrezPeca) tabuleiro.removerPeca(atualTorre);
            tabuleiro.colocarPeca(torre, destinoTorre);
            torre.aumentarContadorDeMovimento();
        }
        //roque grande lado da rainha
        if (p instanceof Rei && destino.getColuna() == atual.getColuna() - 2) {
            Posicao atualTorre = new Posicao(atual.getLinha(), atual.getColuna() - 4);
            Posicao destinoTorre = new Posicao(atual.getLinha(), atual.getColuna() - 1);
            XadrezPeca torre = (XadrezPeca) tabuleiro.removerPeca(atualTorre);
            tabuleiro.colocarPeca(torre, destinoTorre);
            torre.aumentarContadorDeMovimento();
        }

        // movimento especial en passant captura manual
        if (p instanceof Peao) {
            if (atual.getColuna() != destino.getColuna() && pecaCapturada == null) { //andou na diagonal? capturou?
                Posicao posicaoPeao;
                if (p.getCor() == Cor.BRANCA) {
                    posicaoPeao = new Posicao(destino.getLinha() + 1, destino.getColuna());
                } else {
                    posicaoPeao = new Posicao(destino.getLinha() - 1, destino.getColuna());
                }
                pecaCapturada = tabuleiro.removerPeca(posicaoPeao);
                pecasCapturadas.add(pecaCapturada);
                pecasNoTabuleiro.remove(pecaCapturada);
            }
        }

        return pecaCapturada;
    }

    private void desfazerMovimento(Posicao atual, Posicao destino, Peca pecaCapturada) {
        XadrezPeca p = (XadrezPeca) tabuleiro.removerPeca(destino);
        p.diminuirContadorDeMovimento();
        tabuleiro.colocarPeca(p, atual);
        if (pecaCapturada != null) {
            tabuleiro.colocarPeca(pecaCapturada, destino);
            pecasCapturadas.remove(pecaCapturada);
            pecasNoTabuleiro.add(pecaCapturada);
        }
        //desfazendo roque pequeno movendo manualmente
        if (p instanceof Rei && destino.getColuna() == atual.getColuna() + 2) {
            Posicao atualTorre = new Posicao(atual.getLinha(), atual.getColuna() + 3);
            Posicao destinoTorre = new Posicao(atual.getLinha(), atual.getColuna() + 1);
            XadrezPeca torre = (XadrezPeca) tabuleiro.removerPeca(destinoTorre); //mudando o tirar e colocar da origem e destino
            tabuleiro.colocarPeca(torre, atualTorre);
            torre.diminuirContadorDeMovimento();
        }
        //desfazendo roque grande lado da rainha
        if (p instanceof Rei && destino.getColuna() == atual.getColuna() - 2) {
            Posicao atualTorre = new Posicao(atual.getLinha(), atual.getColuna() - 4);
            Posicao destinoTorre = new Posicao(atual.getLinha(), atual.getColuna() - 1);
            XadrezPeca torre = (XadrezPeca) tabuleiro.removerPeca(destinoTorre);
            tabuleiro.colocarPeca(torre, atualTorre); //mudando o tirar e colocar da origem e destino
            torre.diminuirContadorDeMovimento();
        }
        // desfazendo movimento especial en passant captura manual
        if (p instanceof Peao) {
            if (atual.getColuna() != destino.getColuna() && pecaCapturada == enPassantVulneravel) { //andou na diagonal? capturou?
                XadrezPeca peao = (XadrezPeca) tabuleiro.removerPeca(destino);
                Posicao posicaoPeao;
                if (p.getCor() == Cor.BRANCA) {
                    posicaoPeao = new Posicao(3, destino.getColuna());
                } else {
                    posicaoPeao = new Posicao(4, destino.getColuna());
                }
                tabuleiro.colocarPeca(peao, posicaoPeao);
            }
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

    private boolean testarCheckMate(Cor cor) {
        if (!testarCheck(cor)) {
            return false;
        }
        List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((XadrezPeca) x).getCor() == cor).collect(Collectors.toList());
        for (Peca p : list) {
            boolean[][] mat = p.movimentosPossiveis();
            for (int i = 0; i < tabuleiro.getLinhas(); i++) {
                for (int j = 0; j < tabuleiro.getColunas(); j++) {
                    if (mat[i][j]) {
                        Posicao atual = ((XadrezPeca) p).getXadrezPosicao().toPosicao();
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

    private void posicaoDefalt() {
        colocarUmaNovaPeca('a', 2, new Peao(Cor.BRANCA, tabuleiro, this));
        colocarUmaNovaPeca('b', 2, new Peao(Cor.BRANCA, tabuleiro, this));
        colocarUmaNovaPeca('c', 2, new Peao(Cor.BRANCA, tabuleiro, this));
        colocarUmaNovaPeca('d', 2, new Peao(Cor.BRANCA, tabuleiro, this));
        colocarUmaNovaPeca('e', 2, new Peao(Cor.BRANCA, tabuleiro, this));
        colocarUmaNovaPeca('f', 2, new Peao(Cor.BRANCA, tabuleiro, this));
        colocarUmaNovaPeca('g', 2, new Peao(Cor.BRANCA, tabuleiro, this));
        colocarUmaNovaPeca('h', 2, new Peao(Cor.BRANCA, tabuleiro, this));

        colocarUmaNovaPeca('a', 7, new Peao(Cor.PRETA, tabuleiro, this));
        colocarUmaNovaPeca('b', 7, new Peao(Cor.PRETA, tabuleiro, this));
        colocarUmaNovaPeca('c', 7, new Peao(Cor.PRETA, tabuleiro, this));
        colocarUmaNovaPeca('d', 7, new Peao(Cor.PRETA, tabuleiro, this));
        colocarUmaNovaPeca('e', 7, new Peao(Cor.PRETA, tabuleiro, this));
        colocarUmaNovaPeca('f', 7, new Peao(Cor.PRETA, tabuleiro, this));
        colocarUmaNovaPeca('g', 7, new Peao(Cor.PRETA, tabuleiro, this));
        colocarUmaNovaPeca('h', 7, new Peao(Cor.PRETA, tabuleiro, this));
        
        colocarUmaNovaPeca('g', 1, new Cavalo(Cor.BRANCA, tabuleiro));
        colocarUmaNovaPeca('b', 1, new Cavalo(Cor.BRANCA, tabuleiro));

        colocarUmaNovaPeca('g', 8, new Cavalo(Cor.PRETA, tabuleiro));
        colocarUmaNovaPeca('b', 8, new Cavalo(Cor.PRETA, tabuleiro));

        colocarUmaNovaPeca('h', 1, new Torre(Cor.BRANCA, tabuleiro));
        colocarUmaNovaPeca('a', 1, new Torre(Cor.BRANCA, tabuleiro));

        colocarUmaNovaPeca('a', 8, new Torre(Cor.PRETA, tabuleiro));
        colocarUmaNovaPeca('h', 8, new Torre(Cor.PRETA, tabuleiro));

        colocarUmaNovaPeca('c', 8, new Bispo(Cor.PRETA, tabuleiro));
        colocarUmaNovaPeca('f', 8, new Bispo(Cor.PRETA, tabuleiro));

        colocarUmaNovaPeca('c', 1, new Bispo(Cor.BRANCA, tabuleiro));
        colocarUmaNovaPeca('f', 1, new Bispo(Cor.BRANCA, tabuleiro));

        colocarUmaNovaPeca('e', 8, new Rei(Cor.PRETA, tabuleiro, this)); //this faz ele entender que é essa partida dele

        colocarUmaNovaPeca('e', 1, new Rei(Cor.BRANCA, tabuleiro, this)); //this faz ele entender que é essa partida dele

        colocarUmaNovaPeca('d', 8, new Rainha(Cor.PRETA, tabuleiro));

        colocarUmaNovaPeca('d', 1, new Rainha(Cor.BRANCA, tabuleiro));
    }
}
