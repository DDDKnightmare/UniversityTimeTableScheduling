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
        this.ld = ld;
    }
    private final short qtdTimeSlots;
    private final short qtdPeriodos;
    private final LeitorDados ld;
    private short nota = 20000;
    private List<Gene> GenesNaoAlocados = new ArrayList<>();
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
            
            CriaGeneDisciplina(disciplina,true);
            
        }while(qtd >0); 
        
       do{
            Disciplina disciplina = disciplinasPSortear.get(rng.nextInt(disciplinasPSortear.size()));
            disciplinasPSortear.remove(disciplina);
            qtd = disciplinasPSortear.size();
            /**
             * IMPLEMENTAR
             */
            CriaGeneDisciplina(disciplina,true);
            
        }while(qtd >0);       
       
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
    
    
    public boolean verificaDisponibilidade(int cursoPeriodo,TimeSlot timeSlot){
        if(Objects.isNull(horario[timeSlot.codigo][cursoPeriodo]))
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
        /**
         * Disciplina Com restrição
         */
        if(!Objects.isNull(disciplina.timesSlotsPossiveis) || disciplina.timesSlotsPossiveis.isEmpty()){
            
                List<TimeSlot> timeSlots = new ArrayList<TimeSlot>(disciplina.timesSlotsPossiveis);


            for(int i = 0; i< timeSlots.size(); i++){
                if(!this.verificaDispProf(timeSlots.get(i), professor)){
                    timeSlots.remove(i);
                    i--;

                }
            }

            if(timeSlots.size() >= qtdAulas){

                while(timeSlots.size() > qtdAulas){
                    timeSlots.remove(rng.nextInt(timeSlots.size()));
                }

                return timeSlots;
            }
            return null;
        }
        /**
         * Disciplina Sem restrição
         */
        else{
            List<TimeSlot> timeSlots = new ArrayList<TimeSlot>(TimeSlotsTurno(disciplina.codigoTurno));
            
            for(int i =0;i<timeSlots.size();i++){
                if(!this.verificaDispProf(timeSlots.get(i), professor) || !this.verificaDisponibilidade(disciplina.codigoPeriodo, timeSlots.get(i))){
                    timeSlots.remove(i);
                    i--;
                }
            }
            
            if(timeSlots.size() >= qtdAulas){
                return timeSlots;
            }
            return null;
        }
        
    }
    
    public List<TimeSlot> TimeSlotsTurno(int codigoTurno){
        List<TimeSlot> timeSlots = new ArrayList<>();
        
        switch(codigoTurno){
            case 1:
                for(int i = 0; i<= this.qtdTimeSlots; i++){
                    if(((int)(i/24)   > 0 //Não seja domingo
                    &&  (int)((i)%24) > 6 //A partir das 07:00
                    &&  (int)((i)%24) < 13) // Até as 12:00
                                                                            || (int)(i/24)   == 6 // Sábado
                                                                            && (int)((i)%24) > 6// A partir das 7 da manhã
                                                                            && (int)((i)%24) < 13){ // Até as 12:00
                        timeSlots.add(ld.TimeSlots.get(i));
                    }
                }
                break;
                    
            case 2:
                for(int i = 0; i<= this.qtdTimeSlots; i++){
                    if(((int)(i/24)   > 0 //Não seja domingo
                    &&  (int)((i)%24) > 13 //A partir das 13:00
                    &&  (int)((i)%24) < 19) // Até as 18:00
                                                                            || (int)(i/24)   == 6 // Sábado
                                                                            && (int)((i)%24) > 6// A partir das 7 da manhã
                                                                            && (int)((i)%24) < 13){ // Até as 12:00
                        timeSlots.add(ld.TimeSlots.get(i));
                    }
                }
                break;
                
                
            case 3:
                for(int i = 0; i<= this.qtdTimeSlots; i++){
                    if(((int)(i/24)   > 0 //Não seja domingo
                    &&  (int)((i)%24) > 18 //A partir das 18:00
                    &&  (int)((i)%24) < 23) // Até as 22:00
                                                                            || (int)(i/24)   == 6 // Sábado
                                                                            && (int)((i)%24) > 6// A partir das 7 da manhã
                                                                            && (int)((i)%24) < 13){ // Até as 12:00
                        timeSlots.add(ld.TimeSlots.get(i));
                    }
                }
                break;
                
            case 4:
                for(int i = 0; i<= this.qtdTimeSlots; i++){
                    if(((int)(i/24)   > 0 //Não seja domingo
                    &&  ((int)((i)%24) > 7 //A partir das 7:00
                    &&   (int)((i)%24) < 13)) // Até as 12:00
                            
                    ||
                            
                    ((int)((i)%24) > 13 //A partir das 13:00
                    &&   (int)((i)%24) < 19) // Até as 18:00
                                                                            || (int)(i/24)   == 6 // Sábado
                                                                            && (int)((i)%24) > 6// A partir das 7 da manhã
                                                                            && (int)((i)%24) < 13){ // Até as 12:00
                        timeSlots.add(ld.TimeSlots.get(i));
                    }
                }
                break;
                
            case 5:
                for(int i = 0; i<= this.qtdTimeSlots; i++){
                    if(((int)(i/24)   > 0 //Não seja domingo
                    &&  ((int)((i)%24) > 7 //A partir das 7:00
                    &&   (int)((i)%24) < 13)) // Até as 12:00
                            
                    ||
                            
                    ((int)((i)%24) > 18 //A partir das 18:00
                    &&   (int)((i)%24) < 23) // Até as 22:00
                                                                            || (int)(i/24)   == 6 // Sábado
                                                                            && (int)((i)%24) > 6// A partir das 7 da manhã
                                                                            && (int)((i)%24) < 13){ // Até as 12:00
                        timeSlots.add(ld.TimeSlots.get(i));
                    }
                }
                break;
                
            case 6:
                for(int i = 0; i<= this.qtdTimeSlots; i++){
                    if(((int)(i/24)   > 0 //Não seja domingo
                    &&  ((int)((i)%24) > 18 //A partir das 18:00
                    &&   (int)((i)%24) < 23)) // Até as 22:00
                            
                    ||
                            
                    ((int)((i)%24) > 13 //A partir das 13:00
                    &&   (int)((i)%24) < 19) // Até as 18:00
                                                                            || (int)(i/24)   == 6 // Sábado
                                                                            && (int)((i)%24) > 6// A partir das 7 da manhã
                                                                            && (int)((i)%24) < 13){ // Até as 12:00
                        timeSlots.add(ld.TimeSlots.get(i));
                    }
                }
                break;
                
            case 7:
                for(int i = 0; i<= this.qtdTimeSlots; i++){
                    if(((int)(i/24)   > 0 // Não seja domingo
                       && ((int)((i)%24) != 13)//Não há aulas das 12:00 às 13:00
                    &&   (int)((i)/24) < 6) // Verificação não vale p sábado
                                                                            || (int)(i/24)   == 6 // Sábado
                                                                            && (int)((i)%24) > 6// A partir das 7 da manhã
                                                                            && (int)((i)%24) < 13){ // Até as 12:00
                        timeSlots.add(ld.TimeSlots.get(i));
                    }
                }
        }
        
        return timeSlots;
    }
    
    private List<Estudante> EstudantesParaMatricular(final Disciplina disciplina, final List<Estudante> estudantes){
        List<Estudante> alunos = new ArrayList<>(estudantes);
        
        for(int i = 0; i< alunos.size(); i++){
            if(!alunos.get(i).disciplinasACursar.contains(disciplina)){
                    alunos.remove(i);
                    i--;
            }
        }
        
        return alunos;
    }
    
    private boolean MatriculaAlunos(Gene gene){
        List<Estudante> estudantes = new ArrayList<>(EstudantesParaMatricular(gene.getDisciplina(),ld.Estudantes));
        
        
        
        for(int i =0; i < estudantes.size(); i++){
            if(!VerificaDisponibilidadeEstudante(estudantes.get(i),gene.getTimeSlots())){
                estudantes.remove(i);
                i--;
            }
        }
        if(estudantes.size() > 0){
            gene.setEstudantes(estudantes);
            return true;
        }
        return false;
    }
    /**
     * 
     * @param gene
     * @return boolean {salaTeoricaAlocada, salaPraticaAlocada}
     */
    private boolean[] DefinirSala(Gene gene){
        List<TimeSlot> timeSlotsTeorica = gene.getTimeSlots();
        List<TimeSlot> timeSlotsPratica = new ArrayList<>();
        int j = 0;
        boolean[] retorno = {true,true}; // {teorica,pratica}
        for(int i = 0; i < gene.getDisciplina().cargaHorariaPratica;i++){
            timeSlotsPratica.add(timeSlotsTeorica.get(rng.nextInt(timeSlotsTeorica.size())));
            timeSlotsTeorica.remove(i - j);
            j++;
        }
        
        gene.setTimeSlots(timeSlotsTeorica);
        
        List<Sala> salas = SalasPossiveis(timeSlotsTeorica,gene.getDisciplina().tipoSalaTeoria);
        if(salas.size() > 0){
            gene.setSala(salas.get(rng.nextInt(salas.size())));
        }else{
            this.GenesNaoAlocados.add(gene);
            retorno[0] = false;
            LeitorDadosEntrada.Erro(gene.getDisciplina().descricao+" sem sala disponível para aulas teoricas.");
            
        }
        
        
        Gene pratica = new Gene(gene);
        pratica.setTimeSlots(timeSlotsPratica);
        
        salas = SalasPossiveis(timeSlotsPratica,gene.getDisciplina().tipoSalaPratica);
        if(salas.size() > 0){
            pratica.setSala(salas.get(rng.nextInt(salas.size())));
        }else{
            this.GenesNaoAlocados.add(gene);
            retorno[1] = false;
            LeitorDadosEntrada.Erro(pratica.getDisciplina().descricao+" sem sala disponível para aulas praticas");
        }
        
        CorrigeNumeroDeAlunos(gene);
        
        CorrigeNumeroDeAlunos(pratica);
        
        
        return retorno;
        
    }
    
    /**
     * Corrige o número de estudantes matriculados em uma disciplina após definir uma sala.
     * @param gene 
     */
    private void CorrigeNumeroDeAlunos(Gene gene){
        
        if(Objects.isNull(gene.getSala())){
            gene.getEstudantes().clear();
        }else{
            int capacidade = gene.getSala().capacidade;
            int numEstudantes = gene.getEstudantes().size();
            while(numEstudantes > capacidade){
                
                gene.getEstudantes().remove(rng.nextInt(numEstudantes));
                numEstudantes--;
            }
        }
        
    }
    
    
    
    
    /**
     * 
     * @param timeSlots
     * @param tipoSala
     * @return 
     */
    private List<Sala> SalasPossiveis(final List<TimeSlot> timeSlots, final int tipoSala){
        List<Sala> salas = new ArrayList<>(ld.Salas);
        Sala aux;
        /**
         * Separando as salas do tipo desejado
         */
        for(int i = 0; i< salas.size(); i++){
            
            if(salas.get(i).tipoDeSala != tipoSala){
                salas.remove(i);
                i--;
            }else{
                /**
                * verificando disponibilidade
                */
                aux = salas.get(i);
                
                outraSala: // Label for the timeSlots loop
                for(int j = 0; j< timeSlots.size();j++){
                    for(int k = 0; k < this.qtdPeriodos; k++){
                        if(!Objects.isNull(horario[timeSlots.get(j).codigo][k].getSala()) && horario[timeSlots.get(j).codigo][k].getSala()!= aux){
                            
                            salas.remove(i);
                            i--;
                            break outraSala;
                        }
                    }
                }
            }
        }
        
        return salas;
        
        
    }
    
    
    
    private boolean VerificaDisponibilidadeEstudante(final Estudante estudante, final List<TimeSlot> timeSlots){
        
        for(int i = 0; i < timeSlots.size(); i++){
            for(int j =0; j < this.qtdPeriodos; j++){
                if(horario[i][j].getEstudantes().contains(estudante)){
                    return false;
                }
            }
        }
        
        return true;
    }
    
    
    private boolean CriaGeneDisciplina(Disciplina disciplina, boolean restricao) {
        
        List<Professor> professoresPossiveis = new ArrayList<>(disciplina.ProfessoresPodem);
        Professor professor;
        
        if(restricao){
             List<TimeSlot> timeSlots = new ArrayList<TimeSlot>(disciplina.timesSlotsPossiveis);
             
             do{
                 professor = professoresPossiveis.get(rng.nextInt(professoresPossiveis.size()));
                 professoresPossiveis.remove(professor);
                 timeSlots = retornaTimeSlot(disciplina,professor);
                 
                 if(!Objects.isNull(timeSlots)){
                     break;
                 }
                 if(professoresPossiveis.isEmpty()){
                     return false;
                 }
                 
             }while(true);
             
             Gene gene = new Gene(professor, timeSlots, disciplina);
             alocar(gene,timeSlots);
             
             
        }else{
        
            List<TimeSlot> timeSlots = new ArrayList<TimeSlot>();
        
        
            

            do{
                professor = professoresPossiveis.get(rng.nextInt(professoresPossiveis.size()));
                professoresPossiveis.remove(professor);            
                timeSlots = retornaTimeSlot(disciplina, professor);

                if(!Objects.isNull(timeSlots))
                    break;
                if(professoresPossiveis.isEmpty())
                    return false;

            }while(true);

            Gene gene = new Gene(professor, timeSlots, disciplina);
            alocar(gene,timeSlots);
            return true;
        }
        
        
        return false;
    }
    
//    private void CriaGeneDisciplinaSemRestricao(Disciplina disciplina) {
//
//    }
    
    /**
     * 
     * @param gene
     * @param timeSlots 
     */
    public void alocar(Gene gene,List<TimeSlot> timeSlots){
        int l = timeSlots.size();
        for(int i =0;i<l;i++){
            horario[i][gene.getDisciplina().codigoPeriodo] = gene;
        }
    }
    /**
     * Coloca o gene na matriz.
     * @param gene
     * @param timeSlot
     * @param codigoPeriodo
     */
    public void alocar(Gene gene, int timeSlot, int codigoPeriodo){
        horario[timeSlot][codigoPeriodo] = gene;
    }
    
    
    
    
}
