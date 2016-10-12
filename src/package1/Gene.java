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
    private List<Short> estudantes;
    private List<TimeSlot> timeSlots; 
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
    
    public List<TimeSlot> getTimeSlots(){
        return this.timeSlots;
    }
    
    public void setTime(List<TimeSlot> timeSlots){
        this.timeSlots = timeSlots;
    }
            
    public List<Short> getEstudante(){
        return estudantes;
    } 
    
    public void setEstudante(short estudante){
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
