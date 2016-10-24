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
import java.util.List;
import java.util.Objects;
public class Gene {
    
    private Professor professor;
    private Sala sala;
    private Disciplina disciplina;
    private List<Estudante> estudantes;
    private TimeSlot timeSlot; 
    private int qtdEstudantes = 0;
    private boolean teorica;
    private List<Gene> genes;
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
    public Gene(Professor professor, TimeSlot timeSlot, Disciplina disciplina){
        this.professor = professor;
        this.disciplina = disciplina;
        this.timeSlot = timeSlot;
        this.estudantes = new ArrayList<>();
    }
    
    public Gene(Professor professor, TimeSlot timeSlot, Disciplina disciplina,Sala sala, boolean teorica){
        this.professor = professor;
        this.disciplina = disciplina;
        this.timeSlot = timeSlot;
        this.sala = sala;
        this.estudantes = new ArrayList<>();
        this.teorica = teorica;
    }
    
    
    public Gene(Professor professor, TimeSlot timeSlot, Disciplina disciplina,Sala sala){
        this.professor = professor;
        this.disciplina = disciplina;
        this.timeSlot = timeSlot;
        this.sala = sala;
        this.estudantes = new ArrayList<>();
    }
    
    public Gene(Disciplina disciplina){
        this.disciplina = disciplina;
        this.estudantes = new ArrayList<>();
        this.timeSlot = null;
    }
    
    public boolean getTeorica(){
        return teorica;
    }
    
    
    
    
    public void setTeorica(boolean teorica){
        this.teorica = teorica;
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
        this.timeSlot = gene.timeSlot;
        this.estudantes = gene.estudantes;
        this.genes = gene.genes;
        this.qtdEstudantes = gene.qtdEstudantes;
        this.sala = gene.sala;
        this.teorica = gene.teorica;
    }
    
    public Gene(Gene gene,boolean qualquerCoisa){ // Sem copiar a lista de genes
        this.professor = gene.professor;
        this.disciplina = gene.disciplina;
        this.timeSlot = gene.timeSlot;
        this.estudantes = new ArrayList<>(gene.estudantes);
        this.qtdEstudantes = gene.qtdEstudantes;
        this.sala = gene.sala;
        this.teorica = gene.teorica;
    }
    
    
    
    public void setGenes(List<Gene> genes){
        this.genes = genes;
    }
    
    public void addGene(Gene gene){
        this.genes.add(gene);
    }
    
    public void removeGene(Gene gene){
        this.genes.remove(gene);
    }
    
    public void removeGene(int indice){
        this.genes.remove(indice);
    }
    
    public void addGenes(List<Gene> genes){
        this.genes.addAll(genes);
    }
    
    public void removeGenes(List<Gene> genes){
        this.genes.removeAll(genes);
    }
    
    public List<Gene> getGenes(){
        return this.genes;
    }
    
    public TimeSlot getTimeSlot(){
        return this.timeSlot;
    }
    
    public void setTimeSlot(TimeSlot timeSlot){
        this.timeSlot = timeSlot;
    }
            
    public List<Estudante> getEstudantes(){
        return estudantes;
    } 
    
    public void setEstudantes(List<Estudante> estudantes){
        this.estudantes = estudantes;
    }
    
    public void addEstudante(Estudante estudante){
        if(Objects.isNull(estudantes)){
            estudantes = new ArrayList<>();
        }
        estudantes.add(estudante);
        this.qtdEstudantes++;
    }
    
    public void removeEstudante(short estudante){
        estudantes.remove(estudante);
        this.qtdEstudantes--;
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
    
    public String toString(){
        String retorno =    "Disciplina: "+(Objects.isNull(this.disciplina)? "null" : this.getDisciplina().codigo)+
                            "\nTimeSlot: "+(Objects.isNull(this.getTimeSlot())? "null" : timeSlot.toString())+
                            "\nPeriodo: "+(Objects.isNull(this.disciplina)?"null" : this.getDisciplina().codigoPeriodo)+
                            "\nEstudantes: ";
        if(Objects.isNull(this.estudantes)){
            retorno+="null";
        }else{
            if(!estudantes.isEmpty()){
                for(Estudante e: estudantes){
                    if(!Objects.isNull(e)){
                        retorno += e.nome +(estudantes.get(estudantes.size() - 1) == e? ("\nNúmero de alunos: " +  estudantes.size()+"\n"): ", ");
                    }
                }
            }else{
                retorno += "NENHUM ALUNO MATRICULADO\n";
            }
        }
        retorno += "Professor: "+(Objects.isNull(this.professor)? "null" : this.professor.nome) + "\n";
        retorno +=          "Sala: "+(Objects.isNull(this.sala)? "null" : this.sala.descricao +" - Tipo: " +this.sala.tipoDeSala + " - Capacidade: "+sala.capacidade);
        return retorno;

    }
    
}
