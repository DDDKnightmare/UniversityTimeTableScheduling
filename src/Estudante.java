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

public class Estudante {
    private static ArrayList<Estudante> estudantes = new ArrayList<Estudante>();
    
    public static void addEstudante(Estudante estudante){
        estudantes.add(estudante);
    }
    
    public static Estudante getEstudante(int indice){
        return estudantes.get(indice);
    }
    
    public Estudante(int codEstudante, String nomeEstudante, int[] disciplinasACursar){
        this.codEstudante = codEstudante;
        this.nomeEstudante = nomeEstudante;
        this.disciplinasACursar = disciplinasACursar;
    }
    
    private final int codEstudante;
    private final String nomeEstudante;
    private final int[] disciplinasACursar;
    boolean[] matriculado;
    
    
    
    
    
    
}
