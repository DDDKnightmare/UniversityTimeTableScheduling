package package1;

import java.util.List;

public class Professor {
    
    public int codigo;
    public String nome;
    public List<Disciplina> disciplinasAMinistrar;
    public List<TimeSlot> TimeSlotsImpossiveis;
    public List<TimeSlot> TimeSlotsAlocados;

    public Professor(int codigo, String nome, List<Disciplina> disciplinasAMinistrar) {
        this.codigo = codigo;
        this.nome = nome;
        this.disciplinasAMinistrar = disciplinasAMinistrar;
    }
}
