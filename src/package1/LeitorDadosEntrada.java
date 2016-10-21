package package1;

import java.io.IOException;
import java.util.Objects;
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
      leitor.Executa("ag-informacoes.csv", "ag-restricoes.csv");
//    leitor.Executa(enderecoArqInfo, enderecoArqRest);
      
      
      while(numIndividuos <=0){
          System.out.println("Digite o número de indivíduos desejados na população:(número natural maior que 0)");
          numIndividuos = entrada.nextInt();
      }
        
      
      for(int i = 0; i< numIndividuos;i++){
          
        Arvore.addNo(new Individuo(leitor));
        System.out.println("Fim do indivíduo "+(i+1)+System.getProperty("line.separator"));
      }
      
      Arvore.imprimeNotas();
      Individuo melhor = Arvore.getMelhor();
      for(int i = 0; i < leitor.qtdPeriodos; i++){
          for(int j = 0; j < leitor.qtdTimeSlots; j++){
              
              System.out.println("TimeSlot: "+ (j+1) + ";   Período: " + (i+1) + ";  " + (Objects.isNull(melhor.horario[i][j])? "null" : melhor.horario[i][j].toString()));
              
          }
      }
    }
    
    
    //metodo para receber o endereço dos arquivos
    
    public static void RecebeTexto(String endInfo, String endRest){
        enderecoArqInfo = endInfo;
        enderecoArqRest = endRest;
    }
    
}
