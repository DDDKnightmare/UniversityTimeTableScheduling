package package1;

import java.io.IOException;
import java.util.Scanner;
public class LeitorDadosEntrada {
    
    static String enderecoArqInfo;
    static String enderecoArqRest;
    
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
      leitor.Executa(enderecoArqInfo, enderecoArqRest);
      
      
      while(numIndividuos <=0){
          System.out.println("Digite o n�mero de indiv�duos desejados na popula��o:(n�mero natural maior que 0)");
          numIndividuos = entrada.nextInt();
      }
        
      
      for(int i = 0; i< numIndividuos;i++){
          
        Arvore.addNo(new Individuo(leitor));
        System.out.println("Fim do indiv�duo "+(i+1)+System.getProperty("line.separator"));
      }
      
      Arvore.imprimeNotas();
      
    }
    
    
    //metodo para receber o endere�o dos arquivos
    
    public static void RecebeTexto(String endInfo, String endRest){
        enderecoArqInfo = endInfo;
        enderecoArqRest = endRest;
    }
    
}
