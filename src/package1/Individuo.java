package package1;


import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.ArrayList;
/**
 *
 * @author guilhermeferreira
 */
public class Individuo {
    
    
    public Gene[][] horario; 
    public Individuo(short qtdTimeSlots, short qtdPeriodos, LeitorDados ld){
        horario = new Gene[qtdTimeSlots][qtdPeriodos];
        geraIndividuo(ld);
        this.qtdTimeSlots = qtdTimeSlots;
        this.qtdPeriodos = qtdPeriodos;
        disciplinasPSortear = new ArrayList<>(ld.Disciplinas);
    }
    private final short qtdTimeSlots;
    private final short qtdPeriodos;
    
    private short nota = 20000;
    
// RNG
    public static Random rng = new Random();
    
    public static <T> T randomElement(T[] array){
        return array[rng.nextInt(array.length)];
    }
//  Fim RNG
    
    private static List<Disciplina> disciplinasPSortear;
    
    private static List<Disciplina> disciplinasComRestricaoPSortear;
    
    private void geraIndividuo(LeitorDados ld){
        
        int qtd = 0;
        
        do{
            Disciplina disciplina = disciplinasComRestricaoPSortear.get(rng.nextInt(disciplinasComRestricaoPSortear.size()));
            disciplinasComRestricaoPSortear.remove(disciplina);
            qtd = disciplinasComRestricaoPSortear.size();
            
            CriaGeneDisciplinaComRestricao(disciplina);
            
        }while(qtd >0); 
        
       do{
            Disciplina disciplina = disciplinasPSortear.get(rng.nextInt(disciplinasPSortear.size()));
            disciplinasPSortear.remove(disciplina);
            qtd = disciplinasPSortear.size();
            /**
             * IMPLEMENTAR
             */
            CriaGeneDisciplinaSemRestricao(disciplina);
            
        }while(qtd >0);       
       
    }
    /**
     * Implementar --> Coloca o gene na matriz.
     * @param gene
     */
    public void alocar(Gene gene){
        int l = gene.;
        for(int i =0;i<l;i++){
            horario[i][]
        }
    }
    
    
    public static short[] mapaTimeSlot(short timeSlot){
        short[] retorno = new short[2];
        retorno[0] = (short)((timeSlot - 1)/24 + 1);
        retorno[1]= (short)((timeSlot-1)%24 + 1);
        
        return retorno;
    }
    
    public void mutar(){
        
    }
    
    
    
    
    
    public Disciplina sorteiaDisciplinas(Disciplina[] disciplinas){
        Disciplina aux = randomElement(disciplinas);
        return aux;
    }
    
    
    public boolean verificaDisponibilidade(short cursoPeriodo,short timeSlot){
        short diaHora[] = mapaTimeSlot(timeSlot);
        
        if(Objects.isNull(horario[timeSlot][cursoPeriodo]))
            return true;
        return false;        
    }
    
    
    
    
    public boolean verificaDispProf(TimeSlot timeSlot,Professor professor){
        for(short i = 0; i<this.qtdPeriodos; i++){
            if((horario[timeSlot.codigo][i]).getProfessor() == professor){
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

    /**
     * retornaTimeSlot --> Implementar o método que recebe uma disciplina e um professor e 
     * retorna uma lista de timeSlots que seja possível para ambos E que NÃO estejam sendo utilizados.
     * @param disciplina
     * @param professor
     * @return 
     */
    public List<TimeSlot> retornaTimeSlot(Disciplina disciplina, Professor professor){
        int qtdAulas = disciplina.cargaHorariaPratica + disciplina.cargaHorariaTeoria;
        return new ArrayList<TimeSlot>();         
    }
    
    
    
    private boolean CriaGeneDisciplinaComRestricao(Disciplina disciplina) {
        
        List<Professor> professoresPossiveis = new ArrayList<>(disciplina.ProfessoresPodem);
        
        List<TimeSlot> timeSlots = new ArrayList<TimeSlot>();
        Professor professor;
                
        do{
            professor = professoresPossiveis.get(rng.nextInt(professoresPossiveis.size()));
            professoresPossiveis.remove(professor);            
            timeSlots = retornaTimeSlot(disciplina, professor);
            
            if(timeSlots.size() > 0)
                break;
            if(professoresPossiveis.size() == 0)
                return false;
            
        }while(true);
        
        
        Gene gene = new Gene(professor, timeSlots, disciplina);
        alocar(gene);
        return true;
    }

//    private void CriaGeneDisciplinaSemRestricao(Disciplina disciplina) {
//
//    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
