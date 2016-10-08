/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author GMTH
 */
import java.util.Hashtable;

public class StaticData {
    
    public static Hashtable<Short,StaticData> professores = new Hashtable<Short,StaticData>();
    public static Hashtable<Short,StaticData> estudantes = new Hashtable<Short,StaticData>();
    public static Hashtable<Short,StaticData> disciplinas = new Hashtable<Short,StaticData>();
    public static Hashtable<Short,StaticData> salas = new Hashtable<Short,StaticData>();
    
    
// Gets
    public short getCodigo(){
        return this.codigo;
    }
    public String getNome(){
        return this.nome;
    }
    public short getCapacidade(){
        return this.capacidade;
    }
    public short getPeriodo(){
        return this.periodo;
    }
    public short getCargaTeorica(){
        return this.cargaTeorica;
    }
    public short getCargaPratica(){
        return this.cargaPratica;
    }
    public short getTipoSalaTeorica(){
        return this.tipoSalaTeorica;
    }
    public short getTipoSalaPratica(){
        return this.tipoSalaPratica;
    }
    public short getTipoSala(){
        return this.tipoSala;
    }
    public short[] getTimeSlotsIndisponiveis(){
        return this.timeSlotsIndisponiveis;
    }
    public short[] getDisciplinasACursarOuMinistrar(){
        return this.disciplinasACursarOuMinistrar;
    }
    public short[] getTimeSlotsMust(){
        return this.timeSlotsMust;
    }
    
// Variaveis/Constantes
    private final short codigo;
    private final String nome;
    private final short capacidade;
    private final short periodo;
    private final short cargaTeorica;
    private final short cargaPratica;
    private final short tipoSalaTeorica;
    private final short tipoSalaPratica;
    private final short tipoSala;
    private short[] timeSlotsIndisponiveis;
    private final short[] disciplinasACursarOuMinistrar;
    private short[] timeSlotsMust;
    
    
//Sets
    public void setTimeSlotsIndisponiveis(short[] timeSlotsIndisponiveis){
        this.timeSlotsIndisponiveis = timeSlotsIndisponiveis;
    }
    public void setTimeSlotsMust(short[] timeSlotsMust){
        this.timeSlotsMust = timeSlotsMust;
    }
//Construtores
    /*
    Construtor de Sala
    */
    public StaticData(short codigo,String nome,short capacidade, short tipoSala){
        this.codigo = codigo;
        this.nome = nome;
        this.capacidade = capacidade;
        this.tipoSala = tipoSala;
        this.periodo = -1;
        this.cargaPratica = -1;
        this.cargaTeorica = -1;
        this.tipoSalaPratica = -1;
        this.tipoSalaTeorica = -1;
        this.timeSlotsIndisponiveis = null;
        this.disciplinasACursarOuMinistrar = null;
    }
    /*
    Construtor de Disciplina
    */
    public StaticData(short codigo, String nome, short periodo, short cargaPratica, short cargaTeorica, short tipoSalaPratica, short tipoSalaTeorica){
        this.codigo = codigo;
        this.nome = nome;
        this.capacidade = -1;
        this.tipoSala = -1;
        this.periodo = periodo;
        this.cargaPratica = cargaPratica;
        this.cargaTeorica = cargaTeorica;
        this.tipoSalaPratica = tipoSalaPratica;
        this.tipoSalaTeorica = tipoSalaTeorica;
        this.timeSlotsIndisponiveis = null;
        this.disciplinasACursarOuMinistrar = null;
    }
    
    /*
    Construtor de Professores
    */
    public StaticData(short codigo, String nome){
        this.codigo = codigo;
        this.nome = nome;
        this.timeSlotsIndisponiveis = null;
        this.capacidade = -1;
        this.tipoSala = -1;
        this.cargaPratica = -1;
        this.cargaTeorica = -1;
        this.tipoSalaPratica = -1;
        this.tipoSalaTeorica = -1;
        this.periodo = -1;
        this.disciplinasACursarOuMinistrar = null;
    }
    
    /*
    Construtor de Estudantes
    */
    public StaticData(short codigo, String nome, short[] disciplinasACursarOuMinistrar){
        this.codigo = codigo;
        this.nome = nome;
        this.timeSlotsIndisponiveis = null;
        this.capacidade = -1;
        this.tipoSala = -1;
        this.cargaPratica = -1;
        this.cargaTeorica = -1;
        this.tipoSalaPratica = -1;
        this.tipoSalaTeorica = -1;
        this.periodo = -1;
        this.disciplinasACursarOuMinistrar = disciplinasACursarOuMinistrar;
    }
    
}
