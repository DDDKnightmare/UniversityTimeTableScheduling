/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author GMTH
 */
import java.util.ArrayList;

public class Disciplina {
    
    private static ArrayList<Disciplina> disciplinas = new ArrayList<Disciplina>();
    
    public static Disciplina getDisciplina(int indice){
        return disciplinas.get(indice);
    }
    
    public static void addDisciplina(Disciplina disciplina){
        disciplinas.add(disciplina);
    }
    
    public static int getNumDisciplinas(){
        return disciplinas.size();
    }
    
    public Disciplina(int cargaTeorica, int cargaPratica){
        
    }
    
    
    private int professor;
    private int cargaTeorica;
    private int cargaPratica;
    private int salaTeorica;
    private int salaPratica;
    
    private int[] timeSlotsTeorica;
    private int[] timeSlotsPratica;
    
    public int getProfessor(){
        return professor;
    }
    
    public int[] getCargaHoraria(){
        int[] aux = {cargaTeorica,cargaPratica};
        return aux;
    }
    
    public int[] getSalas(){
        int[] aux = {salaTeorica,salaPratica};
        return aux;
    }
    
    public int[][] getTimeSlots(){
        int[][] aux = {timeSlotsTeorica,timeSlotsPratica};
        return aux;
    }
    
    public void setTimeSlotsTeorica(int indice,int timeSlot){
        timeSlotsTeorica[indice] = timeSlot;
    }
    
    public void setTimeSlotsPratica(int indice,int timeSlot){
        timeSlotsPratica[indice] = timeSlot;
    }
    
    
    
    
}
