package package1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
public class LeitorDadosEntrada {
    
     Object[] dadosUsu;
     TelaProgresso tp = new TelaProgresso();
    
    public  List<Individuo> Individuos = new ArrayList<>();
            
    /**
     * Exibe mensagens de erro
     * @param mensagem 
     */
    static LeitorDadosEntrada lde;
    
    public  void Erro(String mensagem){
        System.out.println(mensagem);
    }
    
    public LeitorDadosEntrada(Object[] entrada) throws IOException{
        
        for(Object o : entrada){
        System.out.println(o.toString());
            }
        dadosUsu = entrada;
        
        numGeracoes = Integer.parseInt(dadosUsu[4].toString());
        
        numIndividuos = Integer.parseInt(dadosUsu[3].toString());
        taxaRecombinacao = Integer.parseInt(dadosUsu[5].toString());
        elitismo = (boolean) dadosUsu[7];
        taxaMutacao = Integer.parseInt(dadosUsu[5].toString());
//        System.out.println(Integer.parseInt(dadosUsu[9].toString()));

        
        leitor.peso1 = new Integer(Integer.parseInt(dadosUsu[9].toString()));
        leitor.peso2 = new Integer(Integer.parseInt(dadosUsu[10].toString()));
        leitor.peso3 = new Integer(Integer.parseInt(dadosUsu[11].toString()));
        leitor.peso4 = new Integer(Integer.parseInt(dadosUsu[12].toString()));
        leitor.peso5 = new Integer(Integer.parseInt(dadosUsu[13].toString()));
        
        System.out.println(peso1);
        System.out.println(peso2);
        System.out.println(peso3);
        System.out.println(peso4);
        System.out.println(peso5);
        
        leitor.notaInicial = new Integer(Integer.parseInt(dadosUsu[14].toString()));
        
          this.main(new String[1]);
        
        
    }
    
    
    int peso1;
    int peso2;
    int peso3;
    int peso4;
    int peso5;
    public  int numGeracoes;
    private  int contadorGeracoes;
    private  int numIndividuos;
    private  float taxaRecombinacao;
    private  boolean elitismo;
    private  boolean accept = false;
//    private  String elite;
//    public  Scanner entrada = new Scanner(System.in);
    private  float taxaMutacao ;
    private  int melhorNotaInicial;
    public int[] pesos= {peso1, peso2, peso3, peso4, peso5};
    /**{
     * pesoLacunasVazias,
     * pesoAlunosParcialmenteMatriculados,
     * pesoDisciplinasParcialmenteAlocadas,
     * pesoProfessorOscioso
     * pesoMateriaNaoAlocada
     * }
     */
    private  int notaInicial ;
    
    
    public  int getNotaInicial(){
        return notaInicial;
    }
    
    public  void setNotaInicial(int nota){
        notaInicial = nota;
    }
    
    public  int[] getPesos(){
        return pesos;
    }
    public  void setPesos(int[] pesos){
        this.pesos = pesos;
    }
    public  LeitorDados leitor = new LeitorDados();
    
    public  void main(String[] args) throws IOException {
      
        

        
//        TelaProgresso tp = new TelaProgresso();
            tp.setVisible(true);
            tp.setLocationRelativeTo(null);
        
        
        
              
//      leitor.Executa("ag-informacoes.csv", "ag-restricoes.csv");
    leitor.Executa(dadosUsu[0].toString(), dadosUsu[1].toString());
      
      
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
          tp.escreve("Fim do indivíduo "+(Individuos.size())+System.getProperty("line.separator"));
          //System.out.println("Fim do indivíduo "+(Individuos.size())+System.getProperty("line.separator"));
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
      //System.out.println("Iníncio algoritmo   -   População : "+numIndividuos + "   -   Número de gerações : "+numGeracoes);
      tp.escreve("Iníncio algoritmo   -   População : "+numIndividuos + "   -   Número de gerações : "+numGeracoes);
      for(contadorGeracoes = 0; contadorGeracoes < numGeracoes; contadorGeracoes ++){
          
          recombinar(taxaRecombinacao, elitismo);
          //System.out.println("Recombinação concluída - Geração atual: "+(contadorGeracoes+1));
          tp.escreve("Recombinação concluída - Geração atual: "+(contadorGeracoes+1));
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
                  //System.out.println("Mutação concluída - Restam "+mutar);
                  tp.escreve("Mutação concluída - Restam "+mutar);
                  Individuos.sort(new ComparadorDeNota());
                  
              }
          }
          
          
      }
      melhor = Individuos.get(Individuos.size()-1);
      //System.out.println("--------------------------------------------------------------------");
      tp.escreve("--------------------------------------------------------------------");
      
      melhor.horarioPrint();
      //System.out.println("\n"+melhorNotaInicial);
      tp.escreve("\n"+melhorNotaInicial);
      //System.out.println("\n"+melhor.getNota());
      tp.escreve("\n"+melhor.getNota());
      
    }
   
    
    
     public  void recombinar(float porcentagemDaPopulacao, boolean elitismo){
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
        for(int i = this.Individuos.size()-1; i >0; i-=2){
          
            individuo1 = Individuos.get(i);
            individuo2 = Individuos.get(i-1);
            //vai trocar pelo menos uma coluna, e deixar pelo menos uma coluna do mesmo jeito
            count = Individuo.rng.nextInt(this.leitor.qtdPeriodos - 2) + 1;  
            interacoes = count * 3;
            outer:
            while(count > 0 && interacoes > 0){
                
                indice = Individuo.rng.nextInt(this.leitor.qtdPeriodos);
                
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
                if(interacoes >= this.leitor.qtdPeriodos && count > 0){
                    while(indices.contains(indice)){
                        indice = Individuo.rng.nextInt(this.leitor.qtdPeriodos);
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

    private  List<Individuo> selecao(int recombinar) {
         List<Individuo> IndividuosARecombinar = Individuos.subList(Individuos.size() - recombinar, Individuos.size());
         
         return IndividuosARecombinar;
    }   

    private  void elitismo(Individuo elite){
        Individuos.sort(new ComparadorDeNota());
        Individuos.remove(0);
        Individuos.add(elite);
        Individuos.sort(new ComparadorDeNota());
    }
    
    private  void trocaColunas(Individuo individuo1, Individuo individuo2, List<Integer> colunas){
        
        List<List<Gene>> aux1 = new ArrayList<>();
        List<List<Gene>> aux2 = new ArrayList<>();
        for(Integer i : colunas){
            
            for(int j = 0; j < this.leitor.qtdTimeSlots;j++){
                
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
    
    private  void substituiListas(Individuo individuo1, Individuo individuo2, List<List<Gene>> genes1, List<List<Gene>> genes2){
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

