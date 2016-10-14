package package1;
import java.util.List;
public class Disciplina {
    
    public int codigo;
    public int codigoCurso;
    public int codigoPeriodo;
    public String descricao;
    public int cargaHorariaTeoria;
    public int tipoSalaTeoria;
    public int cargaHorariaPratica;
    public int tipoSalaPratica;    
    public int codigoTurno;
    
    List<TimeSlot>timesSlotsPossiveis;
    List<Professor> ProfessoresPodem;
    public Disciplina(int codigo, int codigoCurso, int codigoPeriodo, String descricao, int cargaHorariaTeoria, int tipoSalaTeoria, int cargaHorariaPratica, int tipoSalaPratica) {
        this.codigo = codigo;
        this.codigoCurso = codigoCurso;
        this.codigoPeriodo = codigoPeriodo;
        this.descricao = descricao;
        this.cargaHorariaTeoria = cargaHorariaTeoria;
        this.tipoSalaTeoria = tipoSalaTeoria;
        this.cargaHorariaPratica = cargaHorariaPratica;
        this.tipoSalaPratica = tipoSalaPratica;
    }
}
