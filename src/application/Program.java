package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import xadrez.ExecaoDoXadrez;
import xadrez.PartidadeXadrez;
import xadrez.XadrezPeca;
import xadrez.XadrezPosicao;

public class Program {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        PartidadeXadrez partidaprincipal = new PartidadeXadrez();
        List<XadrezPeca> capturadas = new ArrayList<>();

        while (true) {
            try {
                IU.clearScreen();
                IU.imprimirPartida(partidaprincipal, capturadas);
                System.out.println();
                System.out.println("Posicao inicial: ");
                XadrezPosicao atual = IU.readXadrezPosicao(sc);
                boolean[][] movimentoPossiveis = partidaprincipal.movimentoPossiveis(atual);
                IU.clearScreen();
                IU.imprimirTabuleiro(partidaprincipal.getPecas(), movimentoPossiveis);
                System.out.println();
                System.out.println("Posicao final: ");
                XadrezPosicao destino = IU.readXadrezPosicao(sc);
                
                XadrezPeca pecaCapturada = partidaprincipal.movimentacao(atual, destino);
                
                if (pecaCapturada != null) {
                    capturadas.add(pecaCapturada);
                }
                
            } catch (ExecaoDoXadrez e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
            catch(InputMismatchException e){
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }

    }
}
