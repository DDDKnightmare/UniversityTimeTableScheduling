package package1;

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
    
    public static short getNumProfessores(){
        return (short)professores.size();
    }
    
    public static Professor getProfessor(short indice){
        return professores.get(indice);
    }
    
    public Professor( short codProfessor){
        
//        this.disciplinasMinistraveis = disciplinasMinistraveis;
        this.codProfessor = codProfessor;
//        this.nomeProfessor = nomeProfessor;
    }
    private final short codProfessor;
//    private final String nomeProfessor;
//    private short[] timeSlotsIndisponiveis;
//    private final short[] disciplinasMinistraveis;
    private short[] disciplinasMinistradas = new short[5];
    
    public short[] getTimeSlotsIndisponiveis(){
        return StaticData.professores.get(this.codProfessor).getTimeSlotsIndisponiveis();
    }    
    
    public void setTimeSlotsIndisponiveis(short[] timeSlotsIndisponiveis){
        StaticData.professores.get(this.codProfessor).setTimeSlotsIndisponiveis(timeSlotsIndisponiveis);
    }
    
    public short[] getDisciplinasMinistraveis(){
        return StaticData.professores.get(this.codProfessor).getDisciplinasACursarOuMinistrar();
    }
    
    public short[] getDisciplinasMinistradas(){
        return disciplinasMinistradas;
    }
    
    public void setDisciplinasMinistradas(short indice, short disciplina){
        disciplinasMinistradas[indice] = disciplina;
    }
    
    
    
    
    
}
