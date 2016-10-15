package package1;

public class TimeSlot {
    
    public int codigo;
    public int codigoDiaSemana;
    public String horarioInicial;
    public String horarioTermino;      

    public TimeSlot(int codigo, int codigoDiaSemana, String horarioInicial, String horarioTermino) {
        this.codigo = codigo;
        this.codigoDiaSemana = codigoDiaSemana;
        this.horarioInicial = horarioInicial;
        this.horarioTermino = horarioTermino;
    }
    
    public String toString(){
        return ""+codigo;
    }
}
