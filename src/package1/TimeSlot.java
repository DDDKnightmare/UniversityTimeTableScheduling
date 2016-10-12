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
    /***
     * 
     * @param codigoTurno
     * @param diaSemana
     * @return 
     */
    public static int[] timeSlotsNoTurno(int codigoTurno, int diaSemana){
        switch(codigoTurno){
            
        }
        
        
        
    }
}
