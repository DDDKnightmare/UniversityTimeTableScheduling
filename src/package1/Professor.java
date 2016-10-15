package package1;

import java.util.ArrayList;
import java.util.List;

public class Professor {
    
    public int codigo;
    public String nome;
    public List<Disciplina> disciplinasAMinistrar;
    public List<TimeSlot> timeSlotsImpossiveis;
    public List<TimeSlot> timeSlotsAlocados;
   
    
    
    public void SetDados(int codigo, String nome, List<Disciplina> disciplinasAMinistrar){
        this.codigo = codigo;
        this.nome = nome;
        this.disciplinasAMinistrar = disciplinasAMinistrar;
        this.timeSlotsImpossiveis = new ArrayList<>();
        this.timeSlotsAlocados = new ArrayList<>();
    }
}
