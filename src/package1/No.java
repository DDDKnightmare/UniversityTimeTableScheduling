package package1;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author GMTH
 */
public class No {
    private int nota;
    private Individuo individuo;
    private No pai = null;
    private No left = null;
    private No right = null;
    public boolean lSon;
    
    public Individuo getIndividuo(){
        return individuo;
    }
    
    public No getPai(){
        return pai;
    }
    
    public void setPai(No pai){
        this.pai = pai;
    }
    
    public No getLeft(){
        return left;
    }
    
    public No getRight(){
        return right;
    }
    
    public void setLeft(No left){
        this.left = left;
    }
    
    public void setRight(No right){
        this.right = right;
    }
    
    
    public No(Individuo individuo){
        this.individuo = individuo;
        nota = individuo.getNota();
    }
    
    public int getNota(){
        return individuo.getNota();
    }
    
    public void addNota(short nota){
        individuo.addNota(nota);
    }
    
    
}
