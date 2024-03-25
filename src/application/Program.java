package application;

import java.util.Scanner;
import xadrez.PartidadeXadrez;
import xadrez.XadrezPeca;
import xadrez.XadrezPosicao;

public class Program {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        PartidadeXadrez partidaprincipal = new PartidadeXadrez();

        while (true) {
            IU.imprimirTabuleiro(partidaprincipal.getPecas());
            System.out.println();
            System.out.println("Posicao inicial: ");
            XadrezPosicao atual = IU.readXadrezPosicao(sc);
            
            System.out.println();
            System.out.println("Posicao final: ");
            XadrezPosicao destino = IU.readXadrezPosicao(sc);

            XadrezPeca pecaCapturada = partidaprincipal.movimentacao(atual, destino);
            
        }

    }
}
