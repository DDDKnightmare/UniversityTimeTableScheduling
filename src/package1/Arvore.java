package package1;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author GMTH
 */
public class Arvore {
    private static int numIndividuos = 0;
    private static No raiz = null;
    
    public static No getRaiz(){
        return raiz;
    }
    
    public static void imprimeNotas(){
        imprimeNotas(raiz);
    }
    
    private static List<Individuo> selecao(int numIndividuos){
        List<Individuo> individuos = new ArrayList<>();
        No aux = raiz;
        
        
        if(numIndividuos > Arvore.numIndividuos){
            numIndividuos = Arvore.numIndividuos % 2 > 0? Arvore.numIndividuos-1 : Arvore.numIndividuos;
        }
        
        while(!Objects.isNull(aux.getRight())){
            aux = aux.getRight();
        }
        
        while(numIndividuos > 0){
            if(!Objects.isNull(aux.getRight())){
                if(!individuos.contains(aux.getRight().getIndividuo())){
                    aux = aux.getRight();
                    individuos.add(aux.getIndividuo());
                    numIndividuos--;
                    continue;
                }
            }
            if(!individuos.contains(aux.getIndividuo())){
                individuos.add(aux.getIndividuo());
                numIndividuos--;
                continue;
            }
            if(!Objects.isNull(aux.getLeft())){
                if(!individuos.contains(aux.getLeft().getIndividuo())){
                    aux = aux.getLeft();
                    individuos.add(aux.getIndividuo());
                    numIndividuos--;
                    continue;
                }
            }
            if(!Objects.isNull(aux.getPai())){
                aux = aux.getPai();
            }
            
        }
        
        return individuos;
        
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
        
        List<Individuo> individuos = null; 
        while(Objects.isNull(individuos) || individuos.size() % 2 > 0 ){
            individuos = selecao(recombinar);
        }
        Individuo elite = null;
        if(elitismo){
            elite = new Individuo(getMelhor());
        }
        
        recombinar = individuos.size();
        Individuo individuo1;
        Individuo individuo2;
        List<Integer> indices = new ArrayList<>();
        int count;
        int indice = 0;
        int interacoes;
        for(int i = 1; i < recombinar; i++){
            
            if(individuos.get(i-1).getNota() >= individuos.get(i).getNota()){
                individuo1 = individuos.get(i-1);
                individuo2 = individuos.get(i);
            }else{
                individuo1 = individuos.get(i);
                individuo2 = individuos.get(i - 1);
            }
            
            i++;
            
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
            indices.clear();
            
        }
        
        elitismo(elite);
        
        System.out.println("Recombinação concluída.");
        
    }
    
    
    private static void elitismo(Individuo elite){
        if(!Objects.isNull(elite)){
            No aux = raiz;
            while(!Objects.isNull(aux.getLeft())){
                aux = aux.getLeft();
            }
            if(aux != raiz){
                aux = aux.getPai();
                
                if(Objects.isNull(aux.getRight())){
                    if(Individuo.rng.nextBoolean()){
                        removeIndividuo(aux.getIndividuo());
                    }else{
                        removeIndividuo(aux.getLeft().getIndividuo());
                    }
                }else{
                    switch(Individuo.rng.nextInt(5)){ // mais chances de remover o individuo de menor nota.
                        case 0:
                            removeIndividuo(aux.getRight().getIndividuo());
                            break;
                            
                        case 1:
                            removeIndividuo(aux.getIndividuo());
                            break;
                            
                        default:
                            removeIndividuo(aux.getLeft().getIndividuo());
                            break;
                    }
                }
            }else{ // aux == raiz
                
                if(Individuo.rng.nextBoolean()){
                    removeIndividuo(aux.getIndividuo());
                }else{
                    removeIndividuo(aux.getRight().getIndividuo());
                }
            }
            addNo(elite);
        }
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
    /**
     * 
     * @param individuo1  Origem
     * @param individuo2  Destino
     * @param genes       Lista de Listas de genes
     */
    private static void substituiListas(Individuo individuo1, Individuo individuo2, List<List<Gene>> genes1, List<List<Gene>> genes2){
        
        for(List<Gene> l : genes1){
                Disciplina d = l.get(0).getDisciplina();
                if(Objects.isNull(l.get(0).getDisciplina().timeSlotsPossiveis)
                        || 
                   l.get(0).getDisciplina().timeSlotsPossiveis.isEmpty()){
                    for(List<Gene> k : individuo2.getGenesSemRestricao()){
                        if(k.get(0).getDisciplina() == d){
                            individuo2.getGenesSemRestricao().remove(k);
                            individuo2.getGenesSemRestricao().add(l);
                            break;
                        }
                    }
                }else{
                    for(List<Gene> k : individuo2.getGenesComRestricao()){
                        if(k.get(0).getDisciplina() == d){
                            individuo2.getGenesComRestricao().remove(k);
                            individuo2.getGenesComRestricao().add(l);
                            break;
                        }
                    }
                }
                
                for(Gene g : l){
                    if(!Objects.isNull(g.getTimeSlot())){
                        individuo2.horario[individuo2.mapaCursoPeriodo(d)][g.getTimeSlot().codigo - 1] = g;
                        individuo2.removerConcorrentes(g);
                    }
                }
                
            }
        
    }
    
    
    public static void imprimeNotas(No aux){
        if(Objects.isNull(aux)) return;
        
        imprimeNotas(aux.getLeft());
        
        System.out.println(aux.getNota());
        
        imprimeNotas(aux.getRight());
    }
    
    public static void addNo(Individuo individuo){
        No aux = raiz;
        if(Objects.isNull(aux)){
            raiz = new No(individuo);
            numIndividuos++;
            return ;
        }
        While:
        while(true){
            
            if(individuo.getNota() < aux.getNota()){
                if(Objects.isNull(aux.getLeft())){
                    aux.setLeft(new No(individuo));
                    aux.getLeft().setPai(aux);
                    aux.getLeft().lSon = true;
                    numIndividuos++;
                    break;
                }
                aux = aux.getLeft();
                        
            }else if(individuo.getNota() > aux.getNota()){
                if(Objects.isNull(aux.getRight())){
                    aux.setRight(new No(individuo));
                    aux.getRight().setPai(aux);
                    aux.getRight().lSon = false;
                    numIndividuos++;
                    break;
                }
                aux = aux.getRight();
            }else{
                switch(Individuo.rng.nextInt(2)){
                    case 0: // left
                        if(Objects.isNull(aux.getLeft())){
                            aux.setLeft(new No(individuo));
                            aux.getLeft().setPai(aux);
                            numIndividuos++;
                            break While;
                        }
                        
                        if(aux.getLeft().getNota() == aux.getNota()){
                            aux = aux.getLeft();
                            break;
                        }
                        
                        aux.getLeft().setPai(new No(individuo));
                        aux.getLeft().getPai().setPai(aux);
                        aux.setLeft(aux.getLeft().getPai());
                        aux.getLeft().lSon = true;
                        break;
                    
                        
                    case 1: // right
                        if(Objects.isNull(aux.getRight())){
                            aux.setRight(new No(individuo));
                            aux.getRight().setPai(aux);
                            numIndividuos++;
                            break While;
                        }
                        if(aux.getNota() == aux.getRight().getNota()){
                            aux = aux.getRight();
                            break;
                        }
                        aux.getRight().setPai(new No(individuo));
                        aux.getRight().getPai().setPai(aux);
                        aux.setRight(aux.getRight().getPai());
                        aux.getRight().lSon = false;
                        
                        break;
                }
                
                
            }
        
        }
        
    }
    
    public static Individuo getMelhor(){
        No aux = raiz;
        while (!Objects.isNull(aux.getRight())){
            aux = aux.getRight();
        }
        return aux.getIndividuo();
    }
    
    public static void removeIndividuo(Individuo individuo){
        int aux = individuo.getNota();
        No guide = raiz;
        while(true){
            
            if(aux < guide.getNota()){
                guide = guide.getLeft();
            }else if(aux > guide.getNota()){
                guide = guide.getRight();
            }else{
                if(guide.getIndividuo() == individuo){
                    if(guide.lSon){
                        if(Objects.isNull(guide.getRight())){
                            guide.getPai().setLeft(guide.getLeft());
                            guide.getLeft().setPai(guide.getPai());
                            break;
                        }
                        if(Objects.isNull(guide.getLeft())){
                            guide.getPai().setLeft(guide.getRight());
                            guide.getRight().setPai(guide.getPai());
                            break;
                        }
                        
                        guide.getPai().setLeft(guide.getRight());
                        guide.getRight().setPai(guide.getPai());
                        No x = guide.getRight();
                        while(!Objects.isNull(x.getLeft())){
                            x = x.getLeft();
                        }
                        guide.getLeft().setPai(x);
                        x.setLeft(guide.getLeft());
                        break;
                        
                        
                    }
                        if(Objects.isNull(guide.getRight())){
                            guide.getPai().setRight(guide.getLeft());
                            guide.getLeft().setPai(guide.getPai());
                            break;
                        }
                        if(Objects.isNull(guide.getLeft())){
                            guide.getPai().setRight(guide.getRight());
                            guide.getRight().setPai(guide.getPai());
                            break;
                        }
                        
                        guide.getPai().setRight(guide.getLeft());
                        guide.getLeft().setPai(guide.getPai());
                        No x = guide.getLeft();
                        while(!Objects.isNull(x.getRight())){
                            x = x.getRight();
                        }
                        guide.getRight().setPai(x);
                        x.setRight(guide.getRight());
                        break;
                }
            }
            
        }
        
        
    }
    
}
