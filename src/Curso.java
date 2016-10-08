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
    
    public Curso(short codCurso, short codTurno, short numPeriodos, String nomeCurso){
        this.codCurso = codCurso;
        this.codTurno = codTurno;
        this.numPeriodos = numPeriodos;
        this.nomeCurso = nomeCurso;
    }
    public short getCodCurso(){
        return this.codCurso;
    }
    public short getTurno(){
        return this.codTurno;
    }
    public short getNumPeriodos(){
        return this.numPeriodos;
    }
    public String getNomeCurso(){
        return this.nomeCurso;
    }
    
    
    private final short codCurso;
    private final short codTurno;
    private final short numPeriodos;
    private final String nomeCurso;
    
    
    
    
    
}
