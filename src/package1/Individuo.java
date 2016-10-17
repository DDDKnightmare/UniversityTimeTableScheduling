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
    
    private final LeitorDados ld;// Dados lidos dos arquivos
    private short nota;
    private List<Gene> GenesNaoAlocados;
// RNG
    public static final Random rng = new Random(); // gerador de n�meros aleat�rios
    
//  Fim RNG    
    private static List<Disciplina> disciplinasSemRestricaoPSortear; // Disciplinas SEM restri��o a ser sorteadas
    
    private static List<Disciplina> disciplinasComRestricaoPSortear; // Disciplinas COM restri��o a ser sorteadas
      
    public Gene[][] horario;  // Matriz [timeSlot][mapeamento(Curso,Periodo)]
    
    
    public Individuo(LeitorDados ld){        
        this.ld = ld;
        this.nota = 20000;
        this.GenesNaoAlocados = new ArrayList<>();    
        disciplinasSemRestricaoPSortear = new ArrayList<>(ld.DisciplinasSemRestricao);
        disciplinasComRestricaoPSortear = new ArrayList<>(ld.DisciplinasComRestricao);
        horario = new Gene[ld.qtdTimeSlots][ld.qtdPeriodos];
        geraIndividuo(ld);  
    }
    
    /**
     * Gera um novo individuo utilizando os dados lidos dos arquivos como base
     * @param ld 
     */
    private void geraIndividuo(LeitorDados ld){
        
        int qtd = 0;
        
        do{// Definindo professores e timeSlots para as disciplinas com restri��o.
            Disciplina disciplina = disciplinasComRestricaoPSortear.get(rng.nextInt(disciplinasComRestricaoPSortear.size()));
            disciplinasComRestricaoPSortear.remove(disciplina);
            qtd = disciplinasComRestricaoPSortear.size();
            
            CriaGeneDisciplina(disciplina,true);//Definindo e alocando os genes
            
        }while(qtd >0); 
        qtd = 0;
       do{// Definindo professores e timeSlots para as disciplinas sem restri��o.
            Disciplina disciplina = disciplinasSemRestricaoPSortear.get(rng.nextInt(disciplinasSemRestricaoPSortear.size()));
            disciplinasSemRestricaoPSortear.remove(disciplina);
            qtd = disciplinasSemRestricaoPSortear.size();
            
            CriaGeneDisciplina(disciplina,false);//Definindo e alocando os genes
            
        }while(qtd >0);       
       
    }
    
    
    /**
     * Retorna a coluna da matriz, dado o curso e periodo
     * @param codigoCurso
     * @param periodo
     * @return 
     */
    public int mapaCursoPeriodo(final Disciplina disciplina){
        Curso curso = disciplina.curso;
        int indicePeriodo = 0;
        for(final Curso aux : ld.Cursos){
            if(aux == curso){
                break;
            }
            indicePeriodo += aux.numeroPeriodos;
        }
        indicePeriodo += disciplina.codigoPeriodo - 1;
        return indicePeriodo;
    }
    
    
    
    /**
     * implementar
     */
    public void mutar(){
        
    }
    
    
    /**
     * Verifica se determinado timeSlot j� est� ocupado com outra disciplina do mesmo per�odo.
     * @param cursoPeriodo
     * @param timeSlot
     * @return 
     */
    public boolean verificaDisponibilidade(Disciplina disciplina, TimeSlot timeSlot){
        if(Objects.isNull(horario[timeSlot.codigo-1][this.mapaCursoPeriodo(disciplina)])
            ||
          horario[timeSlot.codigo-1][this.mapaCursoPeriodo(disciplina)].getDisciplina() != disciplina)
            return true;
        return false;        
    }
    
    
    
    /**
     * Verifica se o professor est� dispon�vel no timeSlot
     * @param timeSlot
     * @param professor
     * @return true == dispon�vel ; false == indispon�vel
     */
    public boolean verificaDispProf(TimeSlot timeSlot,Professor professor){
        if(professor.timeSlotsImpossiveis.contains(timeSlot)){
            return false;
        }
        for(short i = 0; i<ld.qtdPeriodos; i++){
            Gene gene = horario[timeSlot.codigo-1][i];
            if(Objects.isNull(gene))
                continue;
            if(gene.getProfessor() == professor){
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
     * M�todo que recebe uma disciplina e um professor e 
     * retorna uma lista de timeSlots que seja poss�vel para ambos E que N�O estejam sendo utilizados.
     * @param disciplina
     * @param professor
     * @return 
     */
    public List<TimeSlot> retornaTimeSlot(Disciplina disciplina, Professor professor){             
        
        int qtdAulas = disciplina.cargaHorariaPratica + disciplina.cargaHorariaTeoria;
        /**
         * Disciplina Com restri��o
         */
        if(!Objects.isNull(disciplina.timesSlotsPossiveis) && !disciplina.timesSlotsPossiveis.isEmpty()){
            
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
         * Disciplina Sem restri��o
         */
        else{
            List<TimeSlot> timeSlots = new ArrayList<TimeSlot>(TimeSlotsTurno(disciplina.codigoTurno));
            
            for(int i =0;i<timeSlots.size();i++){
                if(!this.verificaDispProf(timeSlots.get(i), professor) || !this.verificaDisponibilidade(disciplina, timeSlots.get(i))){
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
    
    
    /**
     * Fun��o que retorna os timeSlots pertencentes ao turno informado
     * @param codigoTurno
     * @return List<TimeSlot> || null
     */
    public List<TimeSlot> TimeSlotsTurno(int codigoTurno){
        List<TimeSlot> timeSlots = new ArrayList<>();
        
        switch(codigoTurno){
            case 1:
                for(int i = 0; i<= ld.qtdTimeSlots; i++){
                    if(((int)(i/24)   > 0 //N�o seja domingo
                    &&  (int)((i)%24) > 6 //A partir das 07:00
                    &&  (int)((i)%24) < 13) // At� as 12:00
                                                                            || (int)(i/24)   == 6 // S�bado
                                                                            && (int)((i)%24) > 6// A partir das 7 da manh�
                                                                            && (int)((i)%24) < 13){ // At� as 12:00
                        timeSlots.add(ld.TimeSlots.get(i));
                    }
                }
                break;
                    
            case 2:
                for(int i = 0; i<= ld.qtdTimeSlots; i++){
                    if(((int)(i/24)   > 0 //N�o seja domingo
                    &&  (int)((i)%24) > 13 //A partir das 13:00
                    &&  (int)((i)%24) < 19) // At� as 18:00
                                                                            || (int)(i/24)   == 6 // S�bado
                                                                            && (int)((i)%24) > 6// A partir das 7 da manh�
                                                                            && (int)((i)%24) < 13){ // At� as 12:00
                        timeSlots.add(ld.TimeSlots.get(i));
                    }
                }
                break;
                
                
            case 3:
                for(int i = 0; i<= ld.qtdTimeSlots; i++){
                    if(((int)(i/24)   > 0 //N�o seja domingo
                    &&  (int)((i)%24) > 18 //A partir das 18:00
                    &&  (int)((i)%24) < 23) // At� as 22:00
                                                                            || (int)(i/24)   == 6 // S�bado
                                                                            && (int)((i)%24) > 6// A partir das 7 da manh�
                                                                            && (int)((i)%24) < 13){ // At� as 12:00
                        timeSlots.add(ld.TimeSlots.get(i));
                    }
                }
                break;
                
            case 4:
                for(int i = 0; i<= ld.qtdTimeSlots; i++){
                    if(((int)(i/24)   > 0 //N�o seja domingo
                    &&  ((int)((i)%24) > 7 //A partir das 7:00
                    &&   (int)((i)%24) < 13)) // At� as 12:00
                            
                    ||
                            
                    ((int)((i)%24) > 13 //A partir das 13:00
                    &&   (int)((i)%24) < 19) // At� as 18:00
                                                                            || (int)(i/24)   == 6 // S�bado
                                                                            && (int)((i)%24) > 6// A partir das 7 da manh�
                                                                            && (int)((i)%24) < 13){ // At� as 12:00
                        timeSlots.add(ld.TimeSlots.get(i));
                    }
                }
                break;
                
            case 5:
                for(int i = 0; i<= ld.qtdTimeSlots; i++){
                    if(((int)(i/24)   > 0 //N�o seja domingo
                    &&  ((int)((i)%24) > 7 //A partir das 7:00
                    &&   (int)((i)%24) < 13)) // At� as 12:00
                            
                    ||
                            
                    ((int)((i)%24) > 18 //A partir das 18:00
                    &&   (int)((i)%24) < 23) // At� as 22:00
                                                                            || (int)(i/24)   == 6 // S�bado
                                                                            && (int)((i)%24) > 6// A partir das 7 da manh�
                                                                            && (int)((i)%24) < 13){ // At� as 12:00
                        timeSlots.add(ld.TimeSlots.get(i));
                    }
                }
                break;
                
            case 6:
                for(int i = 0; i<= ld.qtdTimeSlots; i++){
                    if(((int)(i/24)   > 0 //N�o seja domingo
                    &&  ((int)((i)%24) > 18 //A partir das 18:00
                    &&   (int)((i)%24) < 23)) // At� as 22:00
                            
                    ||
                            
                    ((int)((i)%24) > 13 //A partir das 13:00
                    &&   (int)((i)%24) < 19) // At� as 18:00
                                                                            || (int)(i/24)   == 6 // S�bado
                                                                            && (int)((i)%24) > 6// A partir das 7 da manh�
                                                                            && (int)((i)%24) < 13){ // At� as 12:00
                        timeSlots.add(ld.TimeSlots.get(i));
                    }
                }
                break;
                
            case 7:
                for(int i = 0; i<= ld.qtdTimeSlots; i++){
                    if(((int)(i/24)   > 0 // N�o seja domingo
                       && ((int)((i)%24) != 13)//N�o h� aulas das 12:00 �s 13:00
                    &&   (int)((i)/24) < 6) // Verifica��o n�o vale p s�bado
                                                                            || (int)(i/24)   == 6 // S�bado
                                                                            && (int)((i)%24) > 6// A partir das 7 da manh�
                                                                            && (int)((i)%24) < 13){ // At� as 12:00
                        timeSlots.add(ld.TimeSlots.get(i));
                    }
                }
        }
        
        return timeSlots;
    }
    
    
    /**
     * Dada uma disciplina e uma lista de estudantes, retorna uma lista de estudantes que devem ser matriculados na disciplina
     * @param disciplina
     * @param estudantes
     * @return List<Estudante>
     */
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
    
    
    /**
     * Matricula alunos na na disciplina presente no gene fornecido
     * @param gene
     * @return 
     */
    private boolean MatriculaAlunos(Gene gene){
        List<Estudante> estudantes = new ArrayList<>(EstudantesParaMatricular(gene.getDisciplina(),ld.Estudantes));
        
        
        
        for(int i = 0; i < estudantes.size(); i++){
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
     * Define a sala para aulas te�ricas e a sala para aulas pr�ticas ######   PERMITE QUE HAJA MAIS ESTUDANTES EM UM DOS TIPOS DE AULA, CASO UMA DAS SALAS SUPORTE A QUANTIDADE
     *                                                                         DE ALUNOS E A OUTRA N�O
     * @param gene
     * @return Gene da aula pratica || null(se houver apenas aulas pr�ticas ou apenas aulas te�ricas)
     */
    private Gene DefinirSala(Gene gene){
        List<TimeSlot> timeSlotsTeorica = gene.getTimeSlots();
        List<TimeSlot> timeSlotsPratica = new ArrayList<>();
        TimeSlot timeSlot;
        
        for(int i = 0; i < gene.getDisciplina().cargaHorariaPratica;i++){// sorteando timeSlots para a aula pr�tica
            timeSlot = timeSlotsTeorica.get(rng.nextInt(timeSlotsTeorica.size()));
            timeSlotsPratica.add(timeSlot);
            timeSlotsTeorica.remove(timeSlot);           
        }
        
        List<Sala> salas = SalasPossiveis(timeSlotsTeorica,gene.getDisciplina().tipoSalaTeoria);//procurando salas dispon�veis para colocar as aulas teoricas
        
        if(!timeSlotsTeorica.isEmpty()){// Tem aulas TE�RICAS
            gene.setTimeSlots(timeSlotsTeorica);// setando os timeSlots das aulas teoricas
            
            if(salas.size() > 0){ 
                gene.setSala(salas.get(rng.nextInt(salas.size())));
            }else{
                this.GenesNaoAlocados.add(gene);

                LeitorDadosEntrada.Erro(gene.getDisciplina().descricao+" sem sala dispon�vel para aulas teoricas.");

            }
            
            MatriculaAlunos(gene);
            CorrigeNumeroDeAlunos(gene);   
        
            if(!timeSlotsPratica.isEmpty()){ // verificando se h� aulas pr�ticas
                
                Gene pratica = new Gene(gene); // novo gene para a as aulas pr�ticas
                
                pratica.setTimeSlots(timeSlotsPratica);

                salas = SalasPossiveis(timeSlotsPratica,gene.getDisciplina().tipoSalaPratica); // pegando as salas que podem ter essa disciplina
                if(salas.size() > 0){
                    pratica.setSala(salas.get(rng.nextInt(salas.size())));
                }else{
                    this.GenesNaoAlocados.add(gene);

                    LeitorDadosEntrada.Erro(pratica.getDisciplina().descricao+" sem sala dispon�vel para aulas praticas");
                }
                
                
                MatriculaAlunos(pratica);       
                                                // Removendo excesso de alunos
                CorrigeNumeroDeAlunos(pratica);

            return  pratica;
            
            }else{
                return null; // apenas aula te�rica
            }
            
        }else{ // N�O tem aulas TE�RICAS
            gene.setTimeSlots(timeSlotsPratica);// setando os timeSlots das aulas pr�ticas 
            
            if(salas.size() > 0){ 
                gene.setSala(salas.get(rng.nextInt(salas.size())));
            }else{
                this.GenesNaoAlocados.add(gene);

                LeitorDadosEntrada.Erro(gene.getDisciplina().descricao+" sem sala dispon�vel para aulas pr�ticas.");

            }
            
         
                
                gene.setTimeSlots(timeSlotsPratica);

                salas = SalasPossiveis(timeSlotsPratica,gene.getDisciplina().tipoSalaPratica);
                if(salas.size() > 0){
                    gene.setSala(salas.get(rng.nextInt(salas.size())));
                }else{
                    this.GenesNaoAlocados.add(gene);

                    LeitorDadosEntrada.Erro(gene.getDisciplina().descricao+" sem sala dispon�vel para aulas praticas");
                }
                    MatriculaAlunos(gene);       
                                                // Removendo excesso de alunos
                CorrigeNumeroDeAlunos(gene);                

            return  null; // APENAS AULAS PR�TICAS   
            
        }
            
        
        
        
            
        
        
        
        
        
        
        
    }
    
    /**
     * Corrige o n�mero de estudantes matriculados em uma disciplina ap�s definir uma sala.
     * @param gene 
     */
    private void CorrigeNumeroDeAlunos(Gene gene){
        
        if(Objects.isNull(gene.getSala())){
            gene.getEstudantes().clear(); // remove todos os alunos se a disciplina n�o tiver sala
            gene.setQtdEstudantes(0);
        }else{
            int capacidade = gene.getSala().capacidade;
            int numEstudantes = Objects.isNull(gene.getEstudantes())? 0 :gene.getEstudantes().size();
            while(numEstudantes > capacidade){
                
                gene.getEstudantes().remove(rng.nextInt(numEstudantes));
                numEstudantes--;
            }
            gene.setQtdEstudantes(numEstudantes);
        }
        
    }
    
    
    
    
    /**
     * Dados os timeSlots e o tipo de sala desejado, retorna ma lista com as salas que podem ser utilizadas
     * @param timeSlots
     * @param tipoSala
     * @return List<Sala>
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
                    for(int k = 0; k < ld.qtdPeriodos; k++){
                        if(!Objects.isNull(horario[timeSlots.get(j).codigo -1][k]) 
                            && !Objects.isNull(horario[timeSlots.get(j).codigo -1][k].getSala()) 
                            && horario[timeSlots.get(j).codigo-1][k].getSala()== aux){
                            
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
    
    
    /**
     * Verifica se o estudante pode se matricular em alguma disciplina nos timeSlots fornecidos
     * @param estudante
     * @param timeSlots
     * @return boolean (true == pode se matricular  ; false == N�O pode se matricular)
     */
    private boolean VerificaDisponibilidadeEstudante(final Estudante estudante, final List<TimeSlot> timeSlots){
        
        for(int i = 0; i < timeSlots.size(); i++){
            for(int j =0; j < ld.qtdPeriodos; j++){
                if(!Objects.isNull(horario[i][j]) && horario[i][j].getEstudantes().contains(estudante)){
                    return false;
                }
            }
        }
        
        return true;
    }
    
    /**
     * Cria um gene para a disciplina
     * @param disciplina
     * @param restricao : if(true){com restri��o}else{sem restri��o}
     * @return 
     */
    private void CriaGeneDisciplina(Disciplina disciplina, boolean restricao) {
        
        List<Professor> professoresPossiveis = new ArrayList<>(disciplina.ProfessoresPodem);// lista com os professores que podem ministrar a disciplina
        Professor professor;        
        if(professoresPossiveis.size() == 0) 
            return;
        if(restricao){
             List<TimeSlot> timeSlots = new ArrayList<TimeSlot>(disciplina.timesSlotsPossiveis); // lista com os timeSlots nos quais a disciplina DEVE ser ministrada
             
             do{
                 professor = professoresPossiveis.get(rng.nextInt(professoresPossiveis.size())); // selecionando professor aleat�rio
                 professoresPossiveis.remove(professor);
                 timeSlots = retornaTimeSlot(disciplina, professor); // List<TimeSlot> se tiver sucesso, null se n�o for poss�vel para o professor
                 
                 if(!Objects.isNull(timeSlots)){ // Professor que pode dar aula encontrado
                     break;
                 }
                 if(professoresPossiveis.isEmpty()){ // Nenhum professor pode ministrar a disciplina
                     GenesNaoAlocados.add(new Gene(disciplina));  
                     return;                
                 }
                 
             }while(true);
             
             Gene teorica = new Gene(professor, timeSlots, disciplina);
             
             Gene pratica = DefinirSala(teorica);
             
             alocar(teorica,teorica.getTimeSlots());
             System.out.println(teorica.toString());
             
             if(!Objects.isNull(pratica)){
                alocar(pratica,pratica.getTimeSlots());
                
                System.out.println(pratica.toString());
             }
        }else{
        
            List<TimeSlot> timeSlots = new ArrayList<TimeSlot>();
        
        
            

            do{
                professor = professoresPossiveis.get(rng.nextInt(professoresPossiveis.size()));
                professoresPossiveis.remove(professor);            
                timeSlots = retornaTimeSlot(disciplina, professor);

                if(!Objects.isNull(timeSlots))
                    break;
                if(professoresPossiveis.isEmpty())
                    return;

            }while(true);

            Gene teorica = new Gene(professor, timeSlots, disciplina);
            
            Gene pratica = this.DefinirSala(teorica);
            
            alocar(teorica,teorica.getTimeSlots());
            
            if(!Objects.isNull(pratica))
                alocar(pratica,pratica.getTimeSlots());
            
            return;
        }
        
        
        return;
    }
    
//    private void CriaGeneDisciplinaSemRestricao(Disciplina disciplina) {
//
//    }
    
    /**
     * Aloca a disciplina DADOS OS TIMESLOTS EM QUE HAVER�O AULAS
     * @param gene
     * @param timeSlots 
     */
    public void alocar(Gene gene,List<TimeSlot> timeSlots){
        int l = timeSlots.size();
        
        for(int i =0;i<l;i++){
            horario[timeSlots.get(i).codigo -1][this.mapaCursoPeriodo(gene.getDisciplina())] = gene;
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
