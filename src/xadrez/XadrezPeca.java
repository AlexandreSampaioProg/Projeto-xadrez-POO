/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package xadrez;

import tabuleiro.Peca;
import tabuleiro.Tabuleiro;

/**
 *
 * @author alexa
 */
public class XadrezPeca extends Peca{
    
    private Cor cor;

    public XadrezPeca(Cor cor, Tabuleiro tabuleiro) {
        super(tabuleiro);
        this.cor = cor;
    }

    public Cor getCor() {
        return cor;
    }
    
    
    
    
}
