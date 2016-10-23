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
    
    private static int numGeracoes = 0;
    private static int contadorGeracoes;
    private static int numIndividuos = 0;
    private static float taxaRecombinacao = 0;
    private static boolean elitismo;
    private static boolean accept = false;
    private static String elite;
    public static Scanner entrada = new Scanner(System.in);
    
    public static LeitorDados leitor = new LeitorDados();
    
    public static void main(String[] args) throws IOException {
      
        
        
              
      leitor.Executa("ag-informacoes.csv", "ag-restricoes.csv");
//    leitor.Executa(enderecoArqInfo, enderecoArqRest);
      
      
      while(numIndividuos <= 1){
          System.out.println("Digite o n�mero de indiv�duos desejados na popula��o:(n�mero inteiro maior que 1)");
          numIndividuos = entrada.nextInt();
      }
      while(numGeracoes <= 0){
          System.out.println("Digite o n�mero de gera��es desejados:(n�mero inteiro maior que 0)");
          numGeracoes = entrada.nextInt();
      }
      
      while(taxaRecombinacao <= 0.0){
          System.out.println("Digite a taxa de recombina��o desejada(%)  OBS: no m�nimo 2 indiv�duos ser�o recombinados");
          elite = entrada.next();
          elite = elite.replace(" ", "").replace(",", ".").replaceAll("[\\D && [^\\.]]", "");
          taxaRecombinacao = Float.parseFloat(elite) / 100;
      }
      elite = "";
      while(!accept){
          
          System.out.println("Deseja que seja feito o uso de elitismo? Responda sim ou n�o");
          elite = entrada.next();
          elite = elite.replace(" ", "").toLowerCase();
          elite = elite.length() > 1? (elite.length() > 2 ? elite.substring(0, 3) : elite) : elite.substring(0, 1);
          switch(elite){
              case "s": case "sim": case "y": case "yes":
                  elitismo = true;
                  accept = true;
                  break;
                  
              case "n": case "nao": case "n�o": case "not": case "no":
                  elitismo = false;
                  accept = true;
                break;
              
              default:
                  System.out.println("Responda � pergunta com SIM ou N�O");
                  break;
              
          }
          
      }
      
      
      for(int i = 0; i< numIndividuos;i++){
          
        Arvore.addNo(new Individuo(leitor));
        System.out.println("Fim do indiv�duo "+(i+1)+System.getProperty("line.separator"));
      }
      
      Arvore.imprimeNotas();
      Individuo melhor = Arvore.getMelhor();
      for(int i = 0; i < leitor.qtdPeriodos; i++){
          for(int j = 0; j < leitor.qtdTimeSlots; j++){
              
              System.out.println("TimeSlot: "+ (j+1) + ";   Per�odo: " + (i+1) + ";  " + (Objects.isNull(melhor.horario[i][j])? "null" : melhor.horario[i][j].toString()));
              
          }
      }
      System.out.println("In�ncio algoritmo   -   Popula��o : "+numIndividuos + "   -   N�mero de gera��es : "+numGeracoes);
      
      for(contadorGeracoes = 0; contadorGeracoes < numGeracoes; contadorGeracoes ++){
          
          Arvore.recombinar(taxaRecombinacao, elitismo);
          
          
      }
      
      Arvore.imprimeNotas();
      System.out.println("--------------------------------------------------------------------");
      for(int i = 0; i < leitor.qtdPeriodos; i++){
          for(int j = 0; j < leitor.qtdTimeSlots; j++){
              
              System.out.println("TimeSlot: "+ (j+1) + ";   Per�odo: " + (i+1) + ";  " + (Objects.isNull(melhor.horario[i][j])? "null" : melhor.horario[i][j].toString()));
              
          }
      }
      
    }
    
    
    //metodo para receber o endere�o dos arquivos
    
    public static void RecebeTexto(String endInfo, String endRest){
        enderecoArqInfo = endInfo;
        enderecoArqRest = endRest;
    }
    
}
