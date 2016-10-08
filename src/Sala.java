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
    
    public Sala(int tipoSala,int[] horariosDisponiveis){
        this.tipoSala = tipoSala;
        this.horariosDisponiveis = horariosDisponiveis;
    }
    
    private int tipoSala;
    
    private int[] horariosDisponiveis;
   
    
    public int getTipoSala(){
        return tipoSala;
    }
    
    public int[] getHorariosDisponiveis(){
        return horariosDisponiveis;
    }
    
    
    
    
}
