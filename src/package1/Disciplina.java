package package1;
import java.util.ArrayList;
import java.util.List;
public class Disciplina {
    
    public int codigo;
    public Curso curso;
    public int codigoPeriodo;
    public String descricao;
    public int cargaHorariaTeoria;
    public int tipoSalaTeoria;
    public int cargaHorariaPratica;
    public int tipoSalaPratica;    
    public int codigoTurno;
    public List<Estudante> estudantesAMatricular;
    
    List<TimeSlot> timeSlotsPossiveis;
    List<Professor> ProfessoresPodem;
    
    public Disciplina(int codigo, Curso curso, int codigoPeriodo, String descricao, int cargaHorariaTeoria, int tipoSalaTeoria, int cargaHorariaPratica, int tipoSalaPratica) {
        this.codigo = codigo;
        this.curso = curso;
        this.codigoPeriodo = codigoPeriodo;
        this.descricao = descricao;
        this.cargaHorariaTeoria = cargaHorariaTeoria;
        this.tipoSalaTeoria = tipoSalaTeoria;
        this.cargaHorariaPratica = cargaHorariaPratica;
        this.tipoSalaPratica = tipoSalaPratica;
        
        this.timeSlotsPossiveis = new ArrayList<>();        
        this.ProfessoresPodem = new ArrayList<>();
        this.estudantesAMatricular = new ArrayList<>();
    }
}
