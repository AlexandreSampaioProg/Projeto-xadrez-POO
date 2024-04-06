package application;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import xadrez.Cor;
import xadrez.PartidadeXadrez;
import xadrez.XadrezPeca;
import xadrez.XadrezPosicao;

public class IU {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static XadrezPosicao readXadrezPosicao(Scanner sc) {
        try {
            String s = sc.nextLine();
            char coluna = s.charAt(0);
            int linha = Integer.parseInt(s.substring(1));
            return new XadrezPosicao(coluna, linha);
        } catch (RuntimeException e) {
            throw new InputMismatchException("Erro ao ler a posição, selecione uma posição valida");
        }
    }

    public static void imprimirPartida(PartidadeXadrez partidadeXadrez, List<XadrezPeca> capturadas) {
        imprimirTabuleiro(partidadeXadrez.getPecas());
        System.out.println();
        mostrarPecasCapturadas(capturadas);
        System.out.println();
        System.out.println("Turno: " + partidadeXadrez.getTurno());
        if (!partidadeXadrez.getCheckMate()) {
            System.out.println("Esperando jogador: " + partidadeXadrez.getJogadorAtual());
            if (partidadeXadrez.getCheck()) {
                System.out.println("CHECK!");
            }
        } else {
            System.out.println("CHECKMATE!");
            System.out.println("GANHADOR: " + partidadeXadrez.getJogadorAtual());
        }

    }

    public static void imprimirTabuleiro(XadrezPeca[][] pecas) {
        for (int i = 0; i < pecas.length; i++) {
            System.out.print((8 - i) + " ");

            for (int j = 0; j < pecas.length; j++) {
                imprimirPeca(pecas[i][j], false);
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
    }

    private static void imprimirPeca(XadrezPeca peca, boolean background) {
        if (background == true) {
            System.out.print(ANSI_BLUE_BACKGROUND);
        }
        if (peca == null) {
            System.out.print("-" + ANSI_RESET);
        } else {
            if (peca.getCor() == Cor.BRANCA) {
                System.out.print(ANSI_WHITE + peca + ANSI_RESET);
            } else {
                System.out.print(ANSI_YELLOW + peca + ANSI_RESET);
            }

        }
        System.out.print(" ");
    }

    public static void imprimirTabuleiro(XadrezPeca[][] pecas, boolean[][] movimentoPossiveis) {
        for (int i = 0; i < pecas.length; i++) {
            System.out.print((8 - i) + " ");
            for (int j = 0; j < pecas.length; j++) {
                imprimirPeca(pecas[i][j], movimentoPossiveis[i][j]);
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
    }

    private static void mostrarPecasCapturadas(List<XadrezPeca> capturadas) {
        List<XadrezPeca> branca = capturadas.stream().filter(x -> x.getCor() == Cor.BRANCA).collect(Collectors.toList());
        List<XadrezPeca> preta = capturadas.stream().filter(x -> x.getCor() == Cor.PRETA).collect(Collectors.toList());
        System.out.println();
        System.out.println("Pecas capturadas");
        System.out.println("Brancas:");
        System.out.print(ANSI_WHITE);
        System.out.println(branca.toString());
        System.out.print(ANSI_RESET);
        System.out.println("Pretas:");
        System.out.print(ANSI_YELLOW);
        System.out.println(preta.toString());
    }
}
