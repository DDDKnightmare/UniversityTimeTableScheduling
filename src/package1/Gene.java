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
import java.util.List;
public class Gene {
    
    private Professor professor;
    private Sala sala;
    private Disciplina disciplina;
    private List<Estudante> estudantes;
    private List<TimeSlot> timeSlots; 
    private boolean aulaTeorica;
    private int qtdEstudantes;
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
    public Gene(Professor professor, List<TimeSlot> timeSlot, Disciplina disciplina){
        this.professor = professor;
        this.disciplina = disciplina;
        this.timeSlots = timeSlots;
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
        this.timeSlots = gene.timeSlots;
        this.estudantes = gene.estudantes;
    }
    
    public boolean getAulaTeorica(){
        return this.aulaTeorica;
    }
    
    public void setAulaTeorica(boolean teorica){
        this.aulaTeorica = teorica;
    }
    
    public List<TimeSlot> getTimeSlots(){
        return this.timeSlots;
    }
    
    public void setTimeSlots(List<TimeSlot> timeSlots){
        this.timeSlots = timeSlots;
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
    
}
