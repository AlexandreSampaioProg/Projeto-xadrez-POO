
package application;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.PartidadeXadrez;
import xadrez.XadrezPeca;

public class Program {
    public static void main(String[] args) {
        PartidadeXadrez partidaprincipal = new PartidadeXadrez();
        
        IU.imprimirTabuleiro(partidaprincipal.getPecas());
   }
}
