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
        if(Objects.isNull(aux)){
            return null;
        }
        while(individuos.size() < numIndividuos && individuos.size() < Arvore.numIndividuos){
            
            if(Objects.isNull(aux.getRight())){
                if(Objects.isNull(aux.getLeft())){ 
                    if(Individuo.rng.nextBoolean()){
                        if(!individuos.contains(aux.getIndividuo())){
                            individuos.add(aux.getIndividuo());
                        }
                    }
                    aux = raiz;
                }
                if(Individuo.rng.nextBoolean()){
                    aux = aux.getLeft();
                }else{
                    if(!individuos.contains(aux.getIndividuo())){
                        individuos.add(aux.getIndividuo());
                        aux = aux.getLeft();
                    }
                }
            }else{
                if(Objects.isNull(aux.getLeft())){
                    if(Individuo.rng.nextBoolean()){
                        if(!individuos.contains(aux.getIndividuo())){
                            individuos.add(aux.getIndividuo());
                        }
                    }
                    aux = aux.getRight();
                }else{
                    if(Individuo.rng.nextBoolean()){
                        if(!individuos.contains(aux.getIndividuo())){
                            individuos.add(aux.getIndividuo());
                        }  
                    }
                    if(Individuo.rng.nextBoolean()){
                        aux = aux.getLeft();
                    }else{
                        aux = aux.getRight();
                    }
                    
                }
            }
        }
        
        return individuos;
        
    }
    
    
    
    
    public static void recombinar(float porcentagemDaPopulacao, boolean elitismo){
        int recombinar = (int)porcentagemDaPopulacao*numIndividuos;
        if(recombinar < 2 && numIndividuos >= 2){
            recombinar = 2;
        }
        if(recombinar%2 > 0){
            recombinar++;
        }
        
        List<Individuo> individuos = selecao(recombinar);
        Individuo elite = null;
        if(elitismo){
            elite = new Individuo(getMelhor());
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
