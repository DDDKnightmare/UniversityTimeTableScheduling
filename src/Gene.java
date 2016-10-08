/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author GMTH
 */
public class Gene {
    
    private Professor professor;
    private Sala sala;
    private Disciplina disciplina;
    
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
