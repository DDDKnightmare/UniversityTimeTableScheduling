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
import java.util.List;
import java.util.Objects;
public class Gene {
    
    private Professor professor;
    private Sala sala;
    private Disciplina disciplina;
    private List<Estudante> estudantes;
    private TimeSlot timeSlot; 
    private boolean aulaTeorica;
    private int qtdEstudantes;
    private boolean teorica;
//    public Gene(Professor professor, Sala sala, Disciplina disciplina, List<Short> estudantes){
//        this.professor = professor;
//        this.sala = sala;
//        this.disciplina = disciplina;
//        this.estudantes = estudantes;
//    }
//    
//    public Gene(Professor professor, Disciplina disciplina, List<Short> estudantes){
//        this.professor = professor;
//        this.disciplina = disciplina;
//        this.estudantes = estudantes;
//    }
//    
//    public Gene(Professor professor, Disciplina disciplina){
//        this.professor = professor;
//        this.disciplina = disciplina;
//    }
    /**
     * Construtor com timeSlots
     * @param professor
     * @param timeSlot
     * @param disciplina 
     */
    public Gene(Professor professor, TimeSlot timeSlot, Disciplina disciplina){
        this.professor = professor;
        this.disciplina = disciplina;
        this.timeSlot = timeSlot;
        this.estudantes = new ArrayList<>();
        
    }
    
    public Gene(Professor professor, TimeSlot timeSlot, Disciplina disciplina,Sala sala, boolean teorica){
        this.professor = professor;
        this.disciplina = disciplina;
        this.timeSlot = timeSlot;
        this.sala = sala;
        this.estudantes = new ArrayList<>();
        this.teorica = teorica;
    }
    
    
    public Gene(Professor professor, TimeSlot timeSlot, Disciplina disciplina,Sala sala){
        this.professor = professor;
        this.disciplina = disciplina;
        this.timeSlot = timeSlot;
        this.sala = sala;
        this.estudantes = new ArrayList<>();
    }
    
    public Gene(Disciplina disciplina){
        this.disciplina = disciplina;
        this.estudantes = new ArrayList<>();
        this.timeSlot = null;
    }
    
    public boolean getTeorica(){
        return teorica;
    }
    
    public void setTeorica(boolean teorica){
        this.teorica = teorica;
    }
    
    public int getQtdEstudantes(){
        return this.qtdEstudantes;
    }
    
    public void setQtdEstudantes(int numEstudantes){
        this.qtdEstudantes = numEstudantes;
    }
    
    public Gene(Gene gene){
        this.professor = gene.professor;
        this.disciplina = gene.disciplina;
        this.timeSlot = gene.timeSlot;
        this.estudantes = gene.estudantes;
    }
    
    public boolean getAulaTeorica(){
        return this.aulaTeorica;
    }
    
    public void setAulaTeorica(boolean teorica){
        this.aulaTeorica = teorica;
    }
    
    public TimeSlot getTimeSlot(){
        return this.timeSlot;
    }
    
    public void setTimeSlot(TimeSlot timeSlot){
        this.timeSlot = timeSlot;
    }
            
    public List<Estudante> getEstudantes(){
        return estudantes;
    } 
    
    public void setEstudantes(List<Estudante> estudantes){
        this.estudantes = estudantes;
    }
    
    public void addEstudante(Estudante estudante){
        estudantes.add(estudante);
    }
    
    public void removeEstudante(short estudante){
        estudantes.remove(estudante);
    }
    
    public Professor getProfessor(){
        return professor;
    }
    
    public Sala getSala(){
        return sala;
    }
    
    public Disciplina getDisciplina(){
        return disciplina;
    }
    
    public void setProfessor(Professor professor){
        this.professor = professor;
    }
    
    public void setSala(Sala sala){
        this.sala = sala;
    }
    
    public void setDisciplina(Disciplina disciplina){
        this.disciplina = disciplina;
    }
    
    public String toString(){
        return "disciplina: "+(Objects.isNull(this.disciplina)? "null" : this.getDisciplina().codigo)+
                "\ntimeSlot: "+(Objects.isNull(this.getTimeSlot())? "null" : timeSlot.toString())+
                "\nperiodo: "+(Objects.isNull(this.disciplina)?"null" : this.getDisciplina().codigoPeriodo);
    }
    
}
