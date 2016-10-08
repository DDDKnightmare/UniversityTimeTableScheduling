/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Objects;
import java.util.Random;
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
        

//Dias da Semana
    public static final int seg = 0;
    public static final int ter = 1;
    public static final int qua = 2;
    public static final int qui = 3;
    public static final int sex = 4;
    public static final int sab = 5;
    
//Fim Dias da Semana

    

//Cursos - C = Computação, E = Elétrica, M = Mecânica
    public static final int c1 = 0;
    public static final int c2 = 1;
    public static final int c3 = 2;
    public static final int c4 = 3;
    public static final int c5 = 4;
    public static final int c6 = 5;
    public static final int c7 = 6;
    public static final int c8 = 7;
    public static final int c9 = 8;
    public static final int c10 = 9;
    public static final int c11 = 10;
    public static final int c12 = 11;
    public static final int e1 = 12;
    public static final int e2 = 13;
    public static final int e3 = 14;
    public static final int e4 = 15;
    public static final int e5 = 16;
    public static final int e6 = 17;
    public static final int e7 = 18;
    public static final int e8 = 19;
    public static final int e9 = 20;
    public static final int e10 = 21;
    public static final int m1 = 22;
    public static final int m2 = 23;
    public static final int m3 = 24;
    public static final int m4 = 25;
    public static final int m5 = 26;
    public static final int m6 = 27;
    public static final int m7 = 28;
    public static final int m8 = 29;
    public static final int m9 = 30;
    public static final int m10 = 31;
//Fim Cursos
    
    
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
