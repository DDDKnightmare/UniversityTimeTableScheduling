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
    
    public Disciplina(String nomeDisciplina,int cargaTeorica, int cargaPratica, int codCurso, int codDisciplina, int periodo,int tipoSalaTeorica, int tipoSalaPratica){
        this.cargaTeorica = cargaTeorica;
        this.cargaPratica = cargaPratica;
        this.tipoSalaTeorica = tipoSalaTeorica;
        this.codCurso = codCurso;
        this.codDisciplina = codDisciplina;
        this.periodo = periodo;
        this.tipoSalaPratica = tipoSalaPratica;
        this.nomeDisciplina = nomeDisciplina;
    }
    
    private final int codDisciplina;
    private final int periodo;
    private final String nomeDisciplina;
    private int professor;
    private final int cargaTeorica;
    private final int cargaPratica;
    private final int tipoSalaTeorica;
    private final int tipoSalaPratica;
    private final int codCurso;
    
    private int[] timeSlotsMust;
    
    public void setTimeSlotsMust(int[] timeSlots){
        timeSlotsMust = timeSlots;
    }
    
    
    private int[] timeSlotsTeorica;
    private int[] timeSlotsPratica;
    
    public int getProfessor(){
        return professor;
    }
    
    public int[] getCargaHoraria(){
        int[] aux = {cargaTeorica,cargaPratica};
        return aux;
    }
    
    public int[] getTipoSalas(){
        int[] aux = {tipoSalaTeorica,tipoSalaPratica};
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
