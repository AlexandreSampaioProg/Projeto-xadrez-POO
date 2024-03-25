package application;

import java.util.InputMismatchException;
import java.util.Scanner;
import xadrez.ExecaoDoXadrez;
import xadrez.PartidadeXadrez;
import xadrez.XadrezPeca;
import xadrez.XadrezPosicao;

public class Program {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        PartidadeXadrez partidaprincipal = new PartidadeXadrez();

        while (true) {
            try {
                IU.clearScreen();
                IU.imprimirTabuleiro(partidaprincipal.getPecas());
                System.out.println();
                System.out.println("Posicao inicial: ");
                XadrezPosicao atual = IU.readXadrezPosicao(sc);

                System.out.println();
                System.out.println("Posicao final: ");
                XadrezPosicao destino = IU.readXadrezPosicao(sc);

                XadrezPeca pecaCapturada = partidaprincipal.movimentacao(atual, destino);
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
