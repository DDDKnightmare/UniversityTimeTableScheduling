package package1;

import java.io.IOException;

public class LeitorDadosEntrada {
    /**
     * Exibe mensagens de erro
     * @param mensagem 
     */
    public static void Erro(String mensagem){
        System.out.println(mensagem);
    }
    
    public static void main(String[] args) throws IOException {
       
      LeitorDados leitor = new LeitorDados();        
      leitor.Executa("ag-informacoes.csv", "ag-restricoes.csv");
      
      Individuo individuo = new Individuo(leitor);
    }
    
}
