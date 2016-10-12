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
    
    
    public static void addNo(Individuo individuo){
        No aux = raiz;
        if(Objects.isNull(aux)){
            raiz = new No(individuo);
            return ;
        }
        boolean Switch = false;
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
                switch(Individuo.rng.nextInt(3)){
                    case 0: // left
                        if(Objects.isNull(aux.getLeft())){
                            aux.setLeft(new No(individuo));
                            aux.getLeft().setPai(aux);
                            Switch = true;
                            break;
                        }
                        aux = aux.getLeft();
                        break;
                        
                    case 1: // middle
                        No pai = aux.getPai();
                        if(aux.lSon){
                            aux.lSon = false;
                            aux.setPai(new No(individuo));
                            aux.getPai().setPai(pai);
                            pai.setLeft(aux.getPai());
                            aux.getPai().setRight(aux);
                            Switch = true;
                            break;
                        }
                        aux.lSon = true;
                        aux.setPai(new No(individuo));
                        aux.getPai().setPai(pai);
                        pai.setRight(aux.getPai());
                        aux.getPai().setLeft(aux);
                        Switch = true;
                        break;
                        
                }
                
                if(Switch){
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
