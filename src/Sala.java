/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author GMTH
 */import java.util.ArrayList;
public class Sala {
    
    private static ArrayList<Sala> salas = new ArrayList();
    
    public static void addSala(Sala sala){
        salas.add(sala);
    }
    
    public static int getNumSalas(){
        return salas.size();
    }
    
    public static Sala getSala(int indice){
        return salas.get(indice);
    }
    
    public Sala(int tipoSala,int capacidade, String nomeSala){
        this.tipoSala = tipoSala;
        this.capacidade = capacidade;
        this.nomeSala = nomeSala;
        this.ocupacao = 0;
    }
    
    private int tipoSala;
    private String nomeSala;
    private int ocupacao;
    private int capacidade;
   
    
    public boolean addEstudante(){
        if(ocupacao < capacidade){
            ocupacao ++;
            return true;
        }
        return false;
    }
    
    public void removeEstudante(){
        ocupacao--;
    }
    
    public int getTipoSala(){
        return tipoSala;
    }
    
    public int getVagas(){
        return (capacidade - ocupacao);
    }
    
    
    
    
}
