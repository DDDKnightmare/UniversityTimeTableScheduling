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
    
    
    public Gene[][] horario;  // Matriz [timeSlot][mapeamento(Curso,Periodo)]
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
    private final LeitorDados ld;// Dados lidos dos arquivos
    private short nota = 20000;
    private List<Gene> GenesNaoAlocados = new ArrayList<>();
// RNG
    public static Random rng = new Random(); // gerador de números aleatórios
    
//  Fim RNG
    
    private static List<Disciplina> disciplinasPSortear; // Disciplinas SEM restrição a ser sorteadas
    
    private static List<Disciplina> disciplinasComRestricaoPSortear; // Disciplinas COM restrição a ser sorteadas
    
    
    /**
     * Gera um novo individuo utilizando os dados lidos dos arquivos como base
     * @param ld 
     */
    private void geraIndividuo(LeitorDados ld){
        
        int qtd = 0;
        
        do{// Definindo professores e timeSlots para as disciplinas com restrição.
            Disciplina disciplina = disciplinasComRestricaoPSortear.get(rng.nextInt(disciplinasComRestricaoPSortear.size()));
            disciplinasComRestricaoPSortear.remove(disciplina);
            qtd = disciplinasComRestricaoPSortear.size();
            
            CriaGeneDisciplina(disciplina,true);//Definindo e alocando os genes
            
        }while(qtd >0); 
        qtd = 0;
       do{// Definindo professores e timeSlots para as disciplinas sem restrição.
            Disciplina disciplina = disciplinasPSortear.get(rng.nextInt(disciplinasPSortear.size()));
            disciplinasPSortear.remove(disciplina);
            qtd = disciplinasPSortear.size();
            
            CriaGeneDisciplina(disciplina,false);//Definindo e alocando os genes
            
        }while(qtd >0);       
       
    }
    
    
    /**
     * Retorna a coluna da matriz, dado o curso e periodo
     * @param codigoCurso
     * @param periodo
     * @return 
     */
    public int mapaCursoPeriodo(final int codigoCurso, final int periodo){
        int aux = periodo - 1;
        for(int i =0; i < codigoCurso; i++){
            aux += ld.Cursos.get(i).numeroPeriodos;
        }
        return aux;
    }
    
    
    /**
     * implementar
     */
    public void mutar(){
        
    }
    
    
    
    /**
     * Verifica se determinado timeSlot já está ocupado com outra disciplina do mesmo período.
     * @param cursoPeriodo
     * @param timeSlot
     * @return 
     */
    public boolean verificaDisponibilidade(Disciplina disciplina, TimeSlot timeSlot){
        if(Objects.isNull(horario[timeSlot.codigo][this.mapaCursoPeriodo(disciplina.codigoCurso, disciplina.codigoPeriodo)])
            ||
          horario[timeSlot.codigo][this.mapaCursoPeriodo(disciplina.codigoCurso, disciplina.codigoPeriodo)].getDisciplina() != disciplina)
            return true;
        return false;        
    }
    
    
    
    /**
     * Verifica se o professor está disponível no timeSlot
     * @param timeSlot
     * @param professor
     * @return true == disponível ; false == indisponível
     */
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
     * Método que recebe uma disciplina e um professor e 
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
        if(!Objects.isNull(disciplina.timesSlotsPossiveis) || !disciplina.timesSlotsPossiveis.isEmpty()){
            
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
     * Função que retorna os timeSlots pertencentes ao turno informado
     * @param codigoTurno
     * @return List<TimeSlot> || null
     */
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
     * Define a sala para aulas teóricas e a sala para aulas práticas ######   PERMITE QUE HAJA MAIS ESTUDANTES EM UM DOS TIPOS DE AULA, CASO UMA DAS SALAS SUPORTE A QUANTIDADE
     *                                                                         DE ALUNOS E A OUTRA NÃO
     * @param gene
     * @return Gene da aula pratica || null(se houver apenas aulas práticas ou apenas aulas teóricas)
     */
    private Gene DefinirSala(Gene gene){
        List<TimeSlot> timeSlotsTeorica = gene.getTimeSlots();
        List<TimeSlot> timeSlotsPratica = new ArrayList<>();
        int j = 0;
        
        for(int i = 0; i < gene.getDisciplina().cargaHorariaPratica;i++){// sorteando timeSlots para a aula prática
            timeSlotsPratica.add(timeSlotsTeorica.get(rng.nextInt(timeSlotsTeorica.size())));
            timeSlotsTeorica.remove(i - j);
            j++;
        }
        
        List<Sala> salas = SalasPossiveis(timeSlotsTeorica,gene.getDisciplina().tipoSalaTeoria);//procurando salas disponíveis para colocar as aulas teoricas
        
        if(!timeSlotsTeorica.isEmpty()){// Tem aulas TEÓRICAS
            gene.setTimeSlots(timeSlotsTeorica);// setando os timeSlots das aulas teoricas
            
            if(salas.size() > 0){ 
                gene.setSala(salas.get(rng.nextInt(salas.size())));
            }else{
                this.GenesNaoAlocados.add(gene);

                LeitorDadosEntrada.Erro(gene.getDisciplina().descricao+" sem sala disponível para aulas teoricas.");

            }
        
            if(!timeSlotsPratica.isEmpty()){ // verificando se há aulas práticas
                
                Gene pratica = new Gene(gene); // novo gene para a as aulas práticas
                
                pratica.setTimeSlots(timeSlotsPratica);

                salas = SalasPossiveis(timeSlotsPratica,gene.getDisciplina().tipoSalaPratica); // pegando as salas que podem ter essa disciplina
                if(salas.size() > 0){
                    pratica.setSala(salas.get(rng.nextInt(salas.size())));
                }else{
                    this.GenesNaoAlocados.add(gene);

                    LeitorDadosEntrada.Erro(pratica.getDisciplina().descricao+" sem sala disponível para aulas praticas");
                }

                CorrigeNumeroDeAlunos(gene); 
                                                // Removendo excesso de alunos
                CorrigeNumeroDeAlunos(pratica);

            return  pratica;
            
            }else{
                return null; // apenas aula teórica
            }
            
        }else{ // NÃO tem aulas TEÓRICAS
            gene.setTimeSlots(timeSlotsPratica);// setando os timeSlots das aulas práticas 
            
            if(salas.size() > 0){ 
                gene.setSala(salas.get(rng.nextInt(salas.size())));
            }else{
                this.GenesNaoAlocados.add(gene);

                LeitorDadosEntrada.Erro(gene.getDisciplina().descricao+" sem sala disponível para aulas práticas.");

            }
            
            if(!timeSlotsPratica.isEmpty()){ // verificando se há aulas práticas
                
                gene.setTimeSlots(timeSlotsPratica);

                salas = SalasPossiveis(timeSlotsPratica,gene.getDisciplina().tipoSalaPratica);
                if(salas.size() > 0){
                    gene.setSala(salas.get(rng.nextInt(salas.size())));
                }else{
                    this.GenesNaoAlocados.add(gene);

                    LeitorDadosEntrada.Erro(gene.getDisciplina().descricao+" sem sala disponível para aulas praticas");
                }

                CorrigeNumeroDeAlunos(gene);

                

            return  null; // APENAS AULAS PRÁTICAS
            
            }else{
                return null; // SEM AULAS!!! 
            }
            
        }
            
        
        
        
            
        
        
        
        
        
        
        
    }
    
    /**
     * Corrige o número de estudantes matriculados em uma disciplina após definir uma sala.
     * @param gene 
     */
    private void CorrigeNumeroDeAlunos(Gene gene){
        
        if(Objects.isNull(gene.getSala())){
            gene.getEstudantes().clear(); // remove todos os alunos se a disciplina não tiver sala
            gene.setQtdEstudantes(0);
        }else{
            int capacidade = gene.getSala().capacidade;
            int numEstudantes = gene.getEstudantes().size();
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
    
    
    /**
     * Verifica se o estudante pode se matricular em alguma disciplina nos timeSlots fornecidos
     * @param estudante
     * @param timeSlots
     * @return boolean (true == pode se matricular  ; false == NÃO pode se matricular)
     */
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
    
    /**
     * Cria um gene para a disciplina
     * @param disciplina
     * @param restricao : if(true){com restrição}else{sem restrição}
     * @return 
     */
    private boolean CriaGeneDisciplina(Disciplina disciplina, boolean restricao) {
        
        List<Professor> professoresPossiveis = new ArrayList<>(disciplina.ProfessoresPodem);// lista com os professores que podem ministrar a disciplina
        Professor professor;
        
        if(restricao){
             List<TimeSlot> timeSlots = new ArrayList<TimeSlot>(disciplina.timesSlotsPossiveis); // lista com os timeSlots nos quais a disciplina DEVE ser ministrada
             
             do{
                 professor = professoresPossiveis.get(rng.nextInt(professoresPossiveis.size())); // selecionando professor aleatório
                 professoresPossiveis.remove(professor);
                 timeSlots = retornaTimeSlot(disciplina,professor); // List<TimeSlot> se tiver sucesso, null se não for possível para o professor
                 
                 if(!Objects.isNull(timeSlots)){ // Professor que pode dar aula encontrado
                     break;
                 }
                 if(professoresPossiveis.isEmpty()){ // Nenhum professor pode ministrar a disciplina
                     return false;
                 }
                 
             }while(true);
             
             Gene teorica = new Gene(professor, timeSlots, disciplina);
             
             Gene pratica = DefinirSala(teorica);
             
             alocar(teorica,teorica.getTimeSlots());
             
             alocar(pratica,pratica.getTimeSlots());
             
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

            Gene teorica = new Gene(professor, timeSlots, disciplina);
            
            Gene pratica = this.DefinirSala(teorica);
            
            alocar(teorica,teorica.getTimeSlots());
            
            alocar(pratica,pratica.getTimeSlots());
            
            return true;
        }
        
        
        return false;
    }
    
//    private void CriaGeneDisciplinaSemRestricao(Disciplina disciplina) {
//
//    }
    
    /**
     * Aloca a disciplina DADOS OS TIMESLOTS EM QUE HAVERÃO AULAS
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
    
    
   public int funcaoFitness (Individuo individuo) {
       int nota = 9500;
       
       int pesoLacunasVazias  = 1;
       int pesoAlunosNaoMatriculados = 1;
       int pesoProfessorOscioso = 1;
       int pesoMateriaNaoAlocada = 1; 
       
       int lacunasVazias = verificaLacunasVazias(individuo);
       int alunosNaoMatriculados = verificaAlunosNaoMatriculados();
       int professoresOsciosos = verificaProfessoresOsciosos(ld.Professores);
       int materiasNaoAlocadas = verificaMateriasNaoAlocadas(ld.Estudantes);
       
       nota = nota - pesoLacunasVazias*lacunasVazias - pesoAlunosNaoMatriculados*alunosNaoMatriculados 
               - pesoProfessorOscioso*professoresOsciosos - pesoMateriaNaoAlocada*materiasNaoAlocadas ; 
 
       
       return nota;
   
   }

    private int verificaLacunasVazias(Individuo individuo ) {
        int lacunas = 0;
        
        for (int i = 0; i < individuo.qtdPeriodos; i++){
            for(int j = 0; j < individuo.qtdTimeSlots; j++){
                if(individuo.horario[j][i] == null){
                    lacunas++;
                
                }
            }
        }
        
        return lacunas;
        }

    private int verificaAlunosNaoMatriculados(List<Estudante> estudantes) {
        int alunos = 0;
        int disciplinas = 0;
        
        for(Estudante p : estudantes){
            for(Disciplina d : p.disciplinasACursar ){
                if(!p.disciplinasCursadas.contains(d))
                  disciplinas++;  
            }
            if(disciplinas == p.disciplinasACursar.size()){
                alunos++;
                disciplinas = 0;
        }
        
        
        return alunos;
       }

    private int verificaProfessoresOsciosos(List<Professor> professores) {
        int qtdProfessores = 0;
            
        for(Professor p : professores){
            if(p.TimeSlotsAlocados.isEmpty()){
                qtdProfessores++;
            }
        }
        return qtdProfessores;
       }

    private int verificaMateriasNaoAlocadas() {
        int materias = 0;
        return materias;
       }
    
}
