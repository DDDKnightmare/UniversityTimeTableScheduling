/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Aluno
 */
//    import java.util.ArrayList;

public class Professor {
    
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
