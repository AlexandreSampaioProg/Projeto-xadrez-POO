/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package xadrez.Pecas;

import tabuleiro.Tabuleiro;
import xadrez.Cor;

/**
 *
 * @author alexa
 */
public class Rei extends xadrez.XadrezPeca{
    
    public Rei(Cor cor, Tabuleiro tabuleiro) {
        super(cor, tabuleiro);
    }
    
    @Override
    public String toString(){
        return "R";
    }
    
}
