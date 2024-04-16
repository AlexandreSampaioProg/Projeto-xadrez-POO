package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.XadrezPosicao;


public abstract class XadrezPeca extends Peca {

    private Cor cor;
    private int contadorDeMovimento;

    public XadrezPeca(Cor cor, Tabuleiro tabuleiro) {
        super(tabuleiro);
        this.cor = cor;
    }

    public Cor getCor() {
        return cor;
    }
    
    public int getContadorDeMovimento(){
        return contadorDeMovimento;
    }
    
    public void aumentarContadorDeMovimento(){
        contadorDeMovimento++;
    }
    
    public void diminuirContadorDeMovimento(){
        contadorDeMovimento--;
    }
    
    public XadrezPosicao getXadrezPosicao(){ 
        return XadrezPosicao.daPosicao(posicao);
    }
  
    protected boolean temUmaPecaInimiga(Posicao posicao){
          XadrezPeca p = (XadrezPeca)getTabuleiro().peca(posicao);
          return p != null && p.getCor() != cor;         
          
      }
    
}
