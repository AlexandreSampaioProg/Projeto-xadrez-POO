
package xadrez;

import tabuleiro.Tabuleiro;


public class PartidadeXadrez {
    private Tabuleiro tabuleiro;

    public PartidadeXadrez() {
        tabuleiro = new Tabuleiro(8, 8);
    }
    
    public XadrezPeca[][] getPecas(){
       
        XadrezPeca[][] partida = new XadrezPeca [tabuleiro.getLinhas()][tabuleiro.getColunas()];
        
        for (int i = 0; i < tabuleiro.getColunas(); i++) {
            for (int j = 0; j < tabuleiro.getLinhas(); j++) {
                partida[i][j] = (XadrezPeca) tabuleiro.peca(i, j);
            }
        }
        return partida;
    }
    
}
