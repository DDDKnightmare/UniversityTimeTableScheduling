package package1;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Objects;
import java.util.Random;
import java.util.ArrayList;
/**
 *
 * @author guilhermeferreira
 */
public class Individuo {
    
    
    
    
    
    private short nota = 9500;
    
// RNG
    public static Random rng = new Random();
    
    public static <T> T randomElement(T[] array){
        return array[rng.nextInt(array.length)];
    }
    
// Fim RNG
        

    


    
    
    public static short[] mapaTimeSlot(short timeSlot){
        short[] retorno = new short[2];
        retorno[0] = (short)((timeSlot - 1)/24 + 1);
        retorno[1]= (short)((timeSlot-1)%24 + 1);
        
        return retorno;
    }
    
    
    public Gene[][][] horario = new Gene[7][24][32];
    
    
    public Disciplina sorteiaDisciplinas(Disciplina[] disciplinas){
        Disciplina aux = randomElement(disciplinas);
        return aux;
    }
    
    
    public boolean verificaDisponibilidade(short cursoPeriodo,short timeSlot){
        short diaHora[] = mapaTimeSlot(timeSlot);
        
        if(Objects.isNull(horario[diaHora[0]][diaHora[1]][cursoPeriodo]))
            return true;
        return false;        
    }
    
    
    
    
    public boolean verificaDispProf(short timeSlot,Professor professor){
        short[] aux = mapaTimeSlot(timeSlot);
        for(short i = 0; i< 32; i++){
            if((horario[aux[0]][aux[1]][i]).getProfessor() == professor){
                return false;
            }
        }
        return true;
        
    }
    
    public void addNota(short nota){
        this.nota += nota;
    }
    
    public short getNota(){
        return this.nota;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
