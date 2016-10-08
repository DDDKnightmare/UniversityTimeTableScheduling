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
    
    
    
    
    
    private int nota = 9500;
    
// RNG
    Random rng = new Random();
    
    public <T> T randomElement(T[] array){
        return array[rng.nextInt(array.length)];
    }
    
// Fim RNG
        

    


    
    
    public int[] mapaTimeSlot(int timeSlot){
        int[] retorno = new int[2];
        retorno[0] = (timeSlot - 1)/24 + 1;
        retorno[1]= (timeSlot-1)%24 + 1;
        
        return retorno;
    }
    
    
    public Gene[][][] horario = new Gene[7][24][32];
    
    
    public Disciplina sorteiaDisciplinas(Disciplina[] disciplinas){
        Disciplina aux = randomElement(disciplinas);
        return aux;
    }
    
    
    public boolean verificaDisponibilidade(int cursoPeriodo,int timeSlot){
        int diaHora[] = mapaTimeSlot(timeSlot);
        
        if(Objects.isNull(horario[diaHora[0]][diaHora[1]][cursoPeriodo]))
            return true;
        return false;        
    }
    
    
    
    
    public boolean verificaDispProf(int timeSlot,Professor professor){
        int[] aux = mapaTimeSlot(timeSlot);
        for(int i = 0; i< 32; i++){
            if((horario[aux[0]][aux[1]][i]).getProfessor() == professor){
                return false;
            }
        }
        return true;
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
