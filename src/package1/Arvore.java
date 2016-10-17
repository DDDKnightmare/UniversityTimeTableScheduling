package package1;


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
    
    private static No raiz = null;
    
    public static No getRaiz(){
        return raiz;
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
            return ;
        }
        While:
        while(true){
            
            if(individuo.getNota() < aux.getNota()){
                if(Objects.isNull(aux.getLeft())){
                    aux.setLeft(new No(individuo));
                    aux.getLeft().setPai(aux);
                    aux.getLeft().lSon = true;
                    break;
                }
                aux = aux.getLeft();
                        
            }else if(individuo.getNota() > aux.getNota()){
                if(Objects.isNull(aux.getRight())){
                    aux.setRight(new No(individuo));
                    aux.getRight().setPai(aux);
                    aux.getRight().lSon = false;
                    break;
                }
                aux = aux.getRight();
            }else{
                switch(Individuo.rng.nextInt(2)){
                    case 0: // left
                        if(Objects.isNull(aux.getLeft())){
                            aux.setLeft(new No(individuo));
                            aux.getLeft().setPai(aux);
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
        short aux = individuo.getNota();
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
