package package1;

import java.io.IOException;
import java.util.Scanner;
public class LeitorDadosEntrada {
    /**
     * Exibe mensagens de erro
     * @param mensagem 
     */
    public static void Erro(String mensagem){
        System.out.println(mensagem);
    }
    
    private static int numGeracoes;
    private static int numIndividuos = 0;
    public static Scanner entrada = new Scanner(System.in);
    
    public static void main(String[] args) throws IOException {
      
        
        
      LeitorDados leitor = new LeitorDados();        
      leitor.Executa("ag-informacoes.csv", "ag-restricoes.csv");
      
      
      while(numIndividuos <=0){
          System.out.println("Digite o número de indivíduos desejados na população:(número natural maior que 0)");
          numIndividuos = entrada.nextInt();
      }
        
      
      for(int i = 0; i< numIndividuos;i++){
          
        Arvore.addNo(new Individuo(leitor));
        System.out.println("Fim do indivíduo "+(i+1)+System.getProperty("line.separator"));
      }
      
      Arvore.imprimeNotas();
      
    }
    
}
