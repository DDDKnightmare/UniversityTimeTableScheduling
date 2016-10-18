package package1;

import java.util.ArrayList;
import java.util.List;

public class Estudante {
    
    public int codigo;
    public String nome;
    public List<Disciplina> disciplinasACursar;
    

    public Estudante(int codigo, String nome, List<Disciplina> disciplinasACursar) {
        this.codigo = codigo;
        this.nome = nome;
        this.disciplinasACursar = disciplinasACursar;
        
    }
}
