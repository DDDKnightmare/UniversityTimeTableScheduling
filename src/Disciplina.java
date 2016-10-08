/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author GMTH
 */
public class Disciplina {
    
    private int professor;
    private int cargaTeorica;
    private int cargaPratica;
    private int salaTeorica;
    private int salaPratica;
    
    private int[] timeSlots;
    
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
    
    public int[] getTimeSlots(){
        return timeSlots;
    }
    
    public void setTimeSlots(int indice,int timeSlot){
        timeSlots[indice] = timeSlot;
    }
    
    
    
    
}
