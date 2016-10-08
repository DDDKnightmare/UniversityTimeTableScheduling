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
public class Curso {
    
    private static ArrayList<Curso> cursos = new ArrayList<>();
    
    public Curso(int codCurso, int codTurno, int numPeriodos, String nomeCurso){
        this.codCurso = codCurso;
        this.codTurno = codTurno;
        this.numPeriodos = numPeriodos;
        this.nomeCurso = nomeCurso;
    }
    
    
    
    private final int codCurso;
    private final int codTurno;
    private final int numPeriodos;
    private final String nomeCurso;
    
    
    
    
    
}
