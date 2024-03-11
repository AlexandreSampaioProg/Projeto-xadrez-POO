package tabuleiro;

public class Tabuleiro {

    private int linhas;
    private int colunas;
    private Peca[][] pecas;

    public Tabuleiro(int linhas, int colunas) {
        
        if (colunas < 1 || linhas < 1) {
            throw new ExecaoDoTabuleiro("Erro ao criar o tabuleiro || O tabuleiro deve ter ao menos 1 linha e coluna");
        }
        
        this.linhas = linhas;
        this.colunas = colunas;
        pecas = new Peca[linhas][colunas];
    }

    public int getLinhas() {
        return linhas;
    }

    

    public int getColunas() {
        return colunas;
    }

    

    public Peca peca(int linha, int coluna){
        if (!posicaoExiste(linha, coluna)) {
            throw new ExecaoDoTabuleiro("Erro ao colocar uma peca || Essa posicão não existe");
        }
        return pecas[linha][coluna];
    }
    
    public Peca peca(Posicao posicao){
         if (!posicaoExiste(posicao)) {
            throw new ExecaoDoTabuleiro("Erro ao colocar uma peca || Essa posicão não existe");
        }
        return pecas[posicao.getLinha()][posicao.getColuna()];      
    }
    
   public void colocarPeca(Peca peca, Posicao posicao){
       if (temUmaPeca(posicao)) {
            throw new ExecaoDoTabuleiro("Já existe uma peça nessa posição: " +posicao);
        }
      pecas[posicao.getLinha()][posicao.getColuna()] = peca;
      peca.posicao = posicao;
   }
    
   private boolean posicaoExiste(int linha, int coluna){
       return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;  
   }
   
   public boolean posicaoExiste(Posicao posicao){
       return posicaoExiste(posicao.getLinha(), posicao.getColuna());
   }
   
   public boolean temUmaPeca(Posicao posicao){
          if (!posicaoExiste(posicao)) {
            throw new ExecaoDoTabuleiro("Erro ao colocar uma peca || Essa posicão não existe");
        }
       return peca(posicao) != null;
   }
}
