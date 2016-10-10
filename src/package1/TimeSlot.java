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

public class TimeSlot {
    
    public TimeSlot(short timeSlot, short diaSemana, String horaInicio,String horaFim){
        
        this.timeSlot = timeSlot;
        this.diaSemana = diaSemana;
        this.horaFim = horaFim;
        this.horaInicio = horaInicio;
        
    }
    
    public TimeSlot(short timeSlot, String horaInicio, String horaFim){
        this.timeSlot = (short)((timeSlot-1)%24 + 1);
        this.diaSemana = (short)((timeSlot - 1)/24 + 1);
        this.horaFim = horaFim;
        this.horaInicio = horaInicio;
    }
    
    public final short timeSlot;
    public final short diaSemana;
    public final String horaInicio;
    public final String horaFim;
    
    
}
