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
import java.util.ArrayList;

public class Disciplina {
    
    private static ArrayList<Disciplina> disciplinas = new ArrayList<Disciplina>();
    
    public static Disciplina getDisciplina(short indice){
        return disciplinas.get(indice);
    }
    
    public static void addDisciplina(Disciplina disciplina){
        disciplinas.add(disciplina);
    }
    
    public static short getNumDisciplinas(){
        return (short)disciplinas.size();
    }
    
    public Disciplina(short codCurso, short codDisciplina){
//        this.cargaTeorica = cargaTeorica;
//        this.cargaPratica = cargaPratica;
//        this.tipoSalaTeorica = tipoSalaTeorica;
        this.codCurso = codCurso;
        this.codDisciplina = codDisciplina;
//        this.periodo = periodo;
//        this.tipoSalaPratica = tipoSalaPratica;
//        this.nomeDisciplina = nomeDisciplina;
    }
    
    private final short codDisciplina;
//    private final short periodo;
//    private final String nomeDisciplina;
    private short professor;
//    private final short cargaTeorica;
//    private final short cargaPratica;
//    private final short tipoSalaTeorica;
//    private final short tipoSalaPratica;
    private final short codCurso;
    
    private short[] timeSlotsMust = null;
    
    public void setTimeSlotsMust(short[] timeSlots){
        timeSlotsMust = timeSlots;
    }
    
    
    private short[] timeSlotsTeorica;
    private short[] timeSlotsPratica;
    
    public short getProfessor(){
        return professor;
    }
    
    public short[] getCargaHoraria(){
        if(StaticData.disciplinas.containsKey(this.codCurso)){
            short[] aux = {StaticData.disciplinas.get(this.codCurso).getCargaTeorica(),
                         StaticData.disciplinas.get(this.codCurso).getCargaPratica()};
            return aux;
        }else{
            //Erro: Disciplina não foi cadastrada na hashtable;
            return null;
        }
        
        
    }
    
    public short[] getTipoSalas(){
        if(StaticData.disciplinas.containsKey(this.codCurso)){
            short[] aux = {StaticData.disciplinas.get(this.codCurso).getTipoSalaTeorica(),
                         StaticData.disciplinas.get(this.codCurso).getTipoSalaPratica()};
            return aux;
        }
        else{
            return null;
        }
        
    }
    
    public short[][] getTimeSlots(){
        short[][] aux = {timeSlotsTeorica,timeSlotsPratica};
        return aux;
    }
    
    public void setTimeSlotsTeorica(short indice,short timeSlot){
        timeSlotsTeorica[indice] = timeSlot;
    }
    
    public void setTimeSlotsPratica(short indice,short timeSlot){
        timeSlotsPratica[indice] = timeSlot;
    }
    
    
    
    
}
