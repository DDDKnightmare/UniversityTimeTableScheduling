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
import java.util.Objects;

public class Estudante {
    private static ArrayList<Estudante> estudantes = new ArrayList<Estudante>();
    
    public static void addEstudante(Estudante estudante){
        estudantes.add(estudante);
    }
    
    public static Estudante getEstudante(short indice){
        return estudantes.get(indice);
    }
    
    public Estudante(short codEstudante){
        this.codEstudante = codEstudante;
//        this.nomeEstudante = nomeEstudante;
//        this.disciplinasACursar = disciplinasACursar;
    }
    
    private final short codEstudante;
//    private final String nomeEstudante;
//    private final short[] disciplinasACursar;
    private short[] matriculado = new short[10];
    
    public void desmatricular(short codDisciplina){
        
        for(short i = 0; i<10;i++){
            if(Objects.isNull(matriculado[i])){
                
            }else{
                if(matriculado[i] == codDisciplina){
                    matriculado[i] = -1;
                }
            }
        }
        
    }
    
    public void matricular(short indice,short codDisciplina){
        matriculado[indice] = codDisciplina;
    }
    
    
    
    
    
}
