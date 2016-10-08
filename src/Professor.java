/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Aluno
 */
    import java.util.ArrayList;

public class Professor {
    
    private static ArrayList<Professor> professores = new ArrayList<Professor>();
    
    public static void addProfessor(Professor professor){
        professores.add(professor);
    }
    
    public static int getNumProfessores(){
        return professores.size();
    }
    
    public static Professor getProfessor(int indice){
        return professores.get(indice);
    }
    
    public Professor(int[] timeSlotsIndisponiveis, int[]disciplinasMinistraveis){
        this.timeSlotsIndisponiveis = timeSlotsIndisponiveis;
        this.disciplinasMinistraveis = disciplinasMinistraveis;
    }
    
    private int[] timeSlotsIndisponiveis;
    private int[] disciplinasMinistraveis;
    private int[] disciplinasMinistradas = new int[5];
    
    public int[] getTimeSlotsIndisponiveis(){
        return timeSlotsIndisponiveis;
    }    
    
    public int[] getDisciplinasMinistraveis(){
        return disciplinasMinistraveis;
    }
    
    public int[] getDisciplinasMinistradas(){
        return disciplinasMinistradas;
    }
    
    public void setDisciplinasMinistradas(int indice, int disciplina){
        disciplinasMinistradas[indice] = disciplina;
    }
    
    
    
    
    
}
