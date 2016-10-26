package package1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
public class LeitorDadosEntrada {
    
    static String enderecoArqInfo;
    static String enderecoArqRest;
    
    public static List<Individuo> Individuos = new ArrayList<>();
            
    /**
     * Exibe mensagens de erro
     * @param mensagem 
     */
    
    
    public static void Erro(String mensagem){
        System.out.println(mensagem);
    }
    
    public LeitorDadosEntrada(Object[] entrada){
        
    }
    
    
    
    private static int numGeracoes = 0;pegar da Interface
    private static int contadorGeracoes;
    private static int numIndividuos = 0;pegar da Interface
    private static float taxaRecombinacao = 0;pegar da Interface
    private static boolean elitismo;pegar da Interface
    private static boolean accept = false;
//    private static String elite;
//    public static Scanner entrada = new Scanner(System.in);
    private static float taxaMutacao = 0;pegar da Interface
    private static int melhorNotaInicial;
    private static int[] pesos = {2,3,1,5,10};pegar da Interface 
    /**{
     * pesoLacunasVazias,
     * pesoAlunosParcialmenteMatriculados,
     * pesoDisciplinasParcialmenteAlocadas,
     * pesoProfessorOscioso
     * pesoMateriaNaoAlocada
     * }
     */
    private static int notaInicial = 9500;pegar da Interface
    
    
    public static int getNotaInicial(){
        return notaInicial;
    }
    
    public static void setNotaInicial(int nota){
        notaInicial = nota;
    }
    
    public static int[] getPesos(){
        return pesos;
    }
    public static void setPesos(int[] pesos){
        LeitorDadosEntrada.pesos = pesos;
    }
    public static LeitorDados leitor = new LeitorDados();
    
    public static void main(String[] args) throws IOException {
      
        
        
              
//      leitor.Executa("ag-informacoes.csv", "ag-restricoes.csv");
    leitor.Executa(enderecoArqInfo, enderecoArqRest);
      
      
//      while(numIndividuos <= 1){
//          System.out.println("Digite o número de indivíduos desejados na população:(número inteiro maior que 1)");
//          numIndividuos = entrada.nextInt();
//      }
//      while(numGeracoes <= 0){
//          System.out.println("Digite o número de gerações desejados:(número inteiro maior que 0)");
//          numGeracoes = entrada.nextInt();
//      }
//      
//      while(taxaRecombinacao <= 0.0){
//          System.out.println("Digite a taxa de recombinação desejada(%)  OBS: no mínimo 2 indivíduos serão recombinados");
//          elite = entrada.next();
//          elite = elite.replace(" ", "").replace(",", ".").replaceAll("[\\D && [^\\.]]", "");
//          taxaRecombinacao = Float.parseFloat(elite) / 100;
//      }
//      
//      while(taxaMutacao <= 0.0){
//          System.out.println("Digite a taxa de mutação desejada(%)");
//          elite = entrada.next();
//          elite = elite.replace(" ", "").replace(",", ".").replaceAll("[\\D && [^\\.]]", "");
//          taxaMutacao = Float.parseFloat(elite) / 100;
//      }
//      
//      
//      elite = "";
//      while(!accept){
//          
//          System.out.println("Deseja que seja feito o uso de elitismo? Responda sim ou não");
//          elite = entrada.next();
//          elite = elite.replace(" ", "").toLowerCase();
//          elite = elite.length() > 1? (elite.length() > 2 ? elite.substring(0, 3) : elite) : elite.substring(0, 1);
//          switch(elite){
//              case "s": case "sim": case "y": case "yes":
//                  elitismo = true;
//                  accept = true;
//                  break;
//                  
//              case "n": case "nao": case "não": case "not": case "no":
//                  elitismo = false;
//                  accept = true;
//                break;
//              
//              default:
//                  System.out.println("Responda à pergunta com SIM ou NÃO");
//                  break;
//              
//          }
//          
//      }
      
      
      while(Individuos.size() < numIndividuos){
          Individuo i = new Individuo(leitor);
          i.horarioPrint();
          Individuos.add(i);
          System.out.println("Fim do indivíduo "+(Individuos.size())+System.getProperty("line.separator"));
      }
//      while(Arvore.getNumIndividuos() < numIndividuos){
//          
//        Arvore.addNo(new Individuo(leitor));
//        System.out.println("Fim do indivíduo "+(Arvore.getNumIndividuos())+System.getProperty("line.separator"));
//      }
      
            //Arvore.imprimeNotas();
       
        Collections.sort(Individuos, new ComparadorDeNota());
                    
      Individuo melhor = Individuos.get(Individuos.size() -1);
      melhorNotaInicial = melhor.getNota();
      for(int i = 0; i < leitor.qtdPeriodos; i++){
          for(int j = 0; j < leitor.qtdTimeSlots; j++){
              
          }
      }
      System.out.println("Iníncio algoritmo   -   População : "+numIndividuos + "   -   Número de gerações : "+numGeracoes);
      
      for(contadorGeracoes = 0; contadorGeracoes < numGeracoes; contadorGeracoes ++){
          
          recombinar(taxaRecombinacao, elitismo);
          System.out.println("Recombinação concluída - Geração atual: "+(contadorGeracoes+1));
          if(Individuo.rng.nextFloat() < taxaMutacao){
              int mutar = (int)(Individuo.rng.nextFloat()*numIndividuos*taxaMutacao);
              mutar = mutar > 0? mutar : 1;
              List<Individuo> aux = new ArrayList<>(Individuos);
              int indice;
              while(mutar > 0){
                  indice = Individuo.rng.nextInt(aux.size());
                  aux.get(indice).mutacao();
                  aux.remove(indice);
                  mutar--;
                  System.out.println("Mutação concluída - Restam "+mutar);
                  Individuos.sort(new ComparadorDeNota());
                  
              }
          }
          
          
      }
      melhor = Individuos.get(Individuos.size()-1);
      System.out.println("--------------------------------------------------------------------");
      
      
      melhor.horarioPrint();
      System.out.println("\n"+melhorNotaInicial);
      System.out.println("\n"+melhor.getNota());
      
    }
    //metodo para receber o endereço dos arquivos
    
    public static void RecebeTexto(String endInfo, String endRest){
        enderecoArqInfo = endInfo;
        enderecoArqRest = endRest;
    }  
    
    
     public static void recombinar(float porcentagemDaPopulacao, boolean elitismo){
        int recombinar = (int)(porcentagemDaPopulacao*numIndividuos);
        if(recombinar < 2 && numIndividuos >= 2){
            recombinar = 2;
        }
        if(recombinar%2 > 0){
            if(recombinar < numIndividuos){
                recombinar++;
            }else{
                recombinar--;
            }
        }
        
        Individuo elite = null;
        if(elitismo){
            elite = new Individuo(Individuos.get(Individuos.size() - 1));
        }
        
        Individuo individuo1;
        Individuo individuo2;
        List<Integer> indices = new ArrayList<>();
        int count;
        int indice = 0;
        int interacoes;
        for(int i = LeitorDadosEntrada.Individuos.size()-1; i >0; i-=2){
          
            individuo1 = Individuos.get(i);
            individuo2 = Individuos.get(i-1);
            //vai trocar pelo menos uma coluna, e deixar pelo menos uma coluna do mesmo jeito
            count = Individuo.rng.nextInt(LeitorDadosEntrada.leitor.qtdPeriodos - 2) + 1;  
            interacoes = count * 3;
            outer:
            while(count > 0 && interacoes > 0){
                
                indice = Individuo.rng.nextInt(LeitorDadosEntrada.leitor.qtdPeriodos);
                
                if(!indices.contains(indice)){
                    
                    if(individuo1.verificaLacunasVazias(individuo1.horario[indice]) 
                       < 
                       individuo2.verificaLacunasVazias(individuo2.horario[indice])){
                            
                            indices.add(indice);
                            
                    
                    }
                    if(!indices.isEmpty()){
                        count--;
                    }
                    
                    
                }
                interacoes--;
            }
            
            if(interacoes <= 0 && count > 0){
                if(interacoes >= LeitorDadosEntrada.leitor.qtdPeriodos && count > 0){
                    while(indices.contains(indice)){
                        indice = Individuo.rng.nextInt(LeitorDadosEntrada.leitor.qtdPeriodos);
                    }
                    indices.add(indice);
                    count--;
                }
            }
            trocaColunas(individuo1, individuo2, indices);
                        
            individuo1.funcaoFitness();
            individuo2.funcaoFitness();
            indices.clear();
            
        }
        if(elitismo){
            elitismo(elite);
        }
        
        
        
        
    }

    private static List<Individuo> selecao(int recombinar) {
         List<Individuo> IndividuosARecombinar = Individuos.subList(Individuos.size() - recombinar, Individuos.size());
         
         return IndividuosARecombinar;
    }   

    private static void elitismo(Individuo elite){
        Individuos.sort(new ComparadorDeNota());
        Individuos.remove(0);
        Individuos.add(elite);
        Individuos.sort(new ComparadorDeNota());
    }
    
    private static void trocaColunas(Individuo individuo1, Individuo individuo2, List<Integer> colunas){
        
        List<List<Gene>> aux1 = new ArrayList<>();
        List<List<Gene>> aux2 = new ArrayList<>();
        for(Integer i : colunas){
            
            for(int j = 0; j < LeitorDadosEntrada.leitor.qtdTimeSlots;j++){
                
                if(!Objects.isNull(individuo1.horario[i][j])){
                    if(!Objects.isNull(individuo1.horario[i][j].getGenes())
                            &&
                       !individuo1.horario[i][j].getGenes().isEmpty()
                            &&
                       !aux1.contains(individuo1.horario[i][j].getGenes())){
                        
                            aux1.add(individuo1.horario[i][j].getGenes());
                            
                            
                    }
                    individuo1.horario[i][j] = null;
                }
                
                if(!Objects.isNull(individuo2.horario[i][j])){
                    if(!Objects.isNull(individuo2.horario[i][j].getGenes())
                            &&
                       !individuo2.horario[i][j].getGenes().isEmpty()
                            &&
                       !aux2.contains(individuo2.horario[i][j].getGenes())){
                        
                            aux2.add(individuo2.horario[i][j].getGenes());
                            
                            
                    }
                    individuo2.horario[i][j] = null;
                }
                
            }
            
            substituiListas(individuo1,individuo2,aux1,aux2);
            
        }
        
        
    }
    
    private static void substituiListas(Individuo individuo1, Individuo individuo2, List<List<Gene>> genes1, List<List<Gene>> genes2){
        List<List<Gene>> aux = new ArrayList<>();
        for(List<Gene> l : genes1){
                Disciplina d = l.get(0).getDisciplina();
                if(Objects.isNull(l.get(0).getDisciplina().timeSlotsPossiveis)
                        || 
                   l.get(0).getDisciplina().timeSlotsPossiveis.isEmpty()){
                    for(List<Gene> k : individuo2.getGenesSemRestricao()){
                        if(k.get(0).getDisciplina() == d){
                            
                            individuo2.getGenesSemRestricao().set(individuo2.getGenesSemRestricao().indexOf(k), l);
                            break;
                        }
                    }
                }else{
                    for(List<Gene> k : individuo2.getGenesComRestricao()){
                        if(k.get(0).getDisciplina() == d){
                            
                            individuo2.getGenesComRestricao().set(individuo2.getGenesComRestricao().indexOf(k), l);
                            break;
                        }
                    }
                }
                
                for(Gene g : l){
                    if(!Objects.isNull(d.timeSlotsPossiveis) && !d.timeSlotsPossiveis.isEmpty()){
                        for(List<Gene> o : individuo2.getGenesComRestricao()){
                            if(o == g.getGenes()){
                                break;
                            }
                            if(o.get(0).getDisciplina() == d){
                                individuo2.getGenesComRestricao().set(individuo2.getGenesComRestricao().indexOf(o), g.getGenes());
                                break;
                            }
                        }
                    }else{
                        for(List<Gene> o : individuo2.getGenesSemRestricao()){
                            if(o == g.getGenes()){
                                break;
                            }
                            if(o.get(0).getDisciplina() == d){
                                individuo2.getGenesSemRestricao().set(individuo2.getGenesSemRestricao().indexOf(o), g.getGenes());
                                break;
                            }
                        }
                    }
                    if(!Objects.isNull(g.getTimeSlot())){
                        individuo2.horario[individuo2.mapaCursoPeriodo(d)][g.getTimeSlot().codigo - 1] = g;
                        individuo2.removerConcorrentes(g);
                    }
                }
                
            }
        for(List<Gene> l : genes2){
                Disciplina d = l.get(0).getDisciplina();
                if(Objects.isNull(l.get(0).getDisciplina().timeSlotsPossiveis)
                        || 
                   l.get(0).getDisciplina().timeSlotsPossiveis.isEmpty()){
                    for(List<Gene> k : individuo1.getGenesSemRestricao()){
                        if(k.get(0).getDisciplina() == d){
                            
                            individuo1.getGenesSemRestricao().set(individuo1.getGenesSemRestricao().indexOf(k), l);
                            break;
                        }
                    }
                }else{
                    for(List<Gene> k : individuo1.getGenesComRestricao()){
                        if(k.get(0).getDisciplina() == d){
                            
                            individuo1.getGenesComRestricao().set(individuo1.getGenesComRestricao().indexOf(k), l);
                            break;
                        }
                    }
                }
                
                for(Gene g : l){
                    d = g.getDisciplina();
                    if(!Objects.isNull(d.timeSlotsPossiveis) && !d.timeSlotsPossiveis.isEmpty()){
                        for(List<Gene> o : individuo1.getGenesComRestricao()){
                            if(o == g.getGenes()){
                                break;
                            }
                            if(o.get(0).getDisciplina() == d){
                                individuo1.getGenesComRestricao().set(individuo1.getGenesComRestricao().indexOf(o), g.getGenes());
                                break;
                            }
                        }
                    }else{
                        for(List<Gene> o : individuo1.getGenesSemRestricao()){
                            if(o == g.getGenes()){
                                break;
                            }
                            if(o.get(0).getDisciplina() == d){
                                individuo1.getGenesSemRestricao().set(individuo1.getGenesSemRestricao().indexOf(o), g.getGenes());
                                break;
                            }
                        }
                    }
                    if(!Objects.isNull(g.getTimeSlot())){
                        individuo1.horario[individuo1.mapaCursoPeriodo(d)][g.getTimeSlot().codigo - 1] = g;
                        individuo1.removerConcorrentes(g);
                    }
                }
                
            }
        }    
}

