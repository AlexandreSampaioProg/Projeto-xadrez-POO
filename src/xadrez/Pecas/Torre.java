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
public class Torre extends xadrez.XadrezPeca{

    public Torre(Cor cor, Tabuleiro tabuleiro) {
        super(cor, tabuleiro);
    }
    
    @Override
    public String toString(){
        return "T";
    }
    
    
}
