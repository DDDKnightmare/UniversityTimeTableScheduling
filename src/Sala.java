/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author GMTH
 */import java.util.ArrayList;
public class Sala {
    
    private static ArrayList<Sala> salas = new ArrayList();
    
    public static void addSala(Sala sala){
        salas.add(sala);
    }
    
    public static short getNumSalas(){
        return (short)salas.size();
    }
    
    public static Sala getSala(short indice){
        return salas.get(indice);
    }
    
    public Sala(short codSala){
        this.codSala = codSala;
        this.ocupacao = 0;
    }
    private short codSala;
//    private short tipoSala;
//    private String nomeSala;
    private short ocupacao;
//    private short capacidade;
   
    
    public boolean addEstudante(){
        if(ocupacao < StaticData.salas.get(this.codSala).getCapacidade()){
            ocupacao ++;
            return true;
        }
        return false;
    }
    
    public void removeEstudante(){
        ocupacao--;
    }
    
    public short getTipoSala(){
        return StaticData.salas.get(this.codSala).getTipoSala();
    }
    
    public short getVagas(){
        return (short)(StaticData.salas.get(this.codSala).getTipoSala() - ocupacao);
    }
    
    
    
    
}
