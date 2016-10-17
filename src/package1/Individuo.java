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
    private int nota;
    private List<Gene> GenesNaoAlocados;
// RNG
    public static final Random rng = new Random(); // gerador de números aleatórios
    
//  Fim RNG    
    private static List<Disciplina> disciplinasSemRestricaoPSortear; // Disciplinas SEM restrição a ser sorteadas
    
    private static List<Disciplina> disciplinasComRestricaoPSortear; // Disciplinas COM restrição a ser sorteadas
      
    public Gene[][] horario;  // Matriz [timeSlot][mapeamento(Curso,Periodo)]
    
    
    public Individuo(LeitorDados ld){        
        this.ld = ld;
        
        this.GenesNaoAlocados = new ArrayList<>();    
        disciplinasSemRestricaoPSortear = new ArrayList<>(ld.DisciplinasSemRestricao);
        disciplinasComRestricaoPSortear = new ArrayList<>(ld.DisciplinasComRestricao);
        horario = new Gene[ld.qtdPeriodos][ld.qtdTimeSlots];
        geraIndividuo(ld);  
    }
    
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
            Disciplina disciplina = disciplinasSemRestricaoPSortear.get(rng.nextInt(disciplinasSemRestricaoPSortear.size()));
            disciplinasSemRestricaoPSortear.remove(disciplina);
            qtd = disciplinasSemRestricaoPSortear.size();
            
            CriaGeneDisciplina(disciplina,false);//Definindo e alocando os genes
            
        }while(qtd >0);       
       
       this.funcaoFitness(this);
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
     * Verifica se determinado timeSlot já está ocupado com outra disciplina do mesmo período.
     * @param cursoPeriodo
     * @param timeSlot
     * @return 
     */
    public boolean verificaDisponibilidade(Disciplina disciplina, TimeSlot timeSlot){
        if(Objects.isNull(horario[this.mapaCursoPeriodo(disciplina)][timeSlot.codigo-1])
            ||
          horario[this.mapaCursoPeriodo(disciplina)][timeSlot.codigo-1].getDisciplina() != disciplina)
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
        if(professor.timeSlotsImpossiveis.contains(timeSlot)){
            return false;
        }
        for(short i = 0; i<ld.qtdPeriodos; i++){
            Gene gene = horario[i][timeSlot.codigo-1];
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
    
    
    
    public int getNota(){
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
                for(int i = 0; i<= ld.qtdTimeSlots; i++){
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
                for(int i = 0; i<= ld.qtdTimeSlots; i++){
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
                for(int i = 0; i<= ld.qtdTimeSlots; i++){
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
                for(int i = 0; i<= ld.qtdTimeSlots; i++){
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
                for(int i = 0; i<= ld.qtdTimeSlots; i++){
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
                for(int i = 0; i<= ld.qtdTimeSlots; i++){
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
                for(int i = 0; i<= ld.qtdTimeSlots; i++){
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
     * Define a sala para aulas teóricas e a sala para aulas práticas ######   PERMITE QUE HAJA MAIS ESTUDANTES EM UM DOS TIPOS DE AULA, CASO UMA DAS SALAS SUPORTE A QUANTIDADE
     *                                                                         DE ALUNOS E A OUTRA NÃO
     * @param gene
     * @return Gene da aula pratica || null(se houver apenas aulas práticas ou apenas aulas teóricas)
     */
    private Gene DefinirSala(Gene gene){
        List<TimeSlot> timeSlotsTeorica = gene.getTimeSlots();
        List<TimeSlot> timeSlotsPratica = new ArrayList<>();
        TimeSlot timeSlot;
        
        for(int i = 0; i < gene.getDisciplina().cargaHorariaPratica;i++){// sorteando timeSlots para a aula prática
            timeSlot = timeSlotsTeorica.get(rng.nextInt(timeSlotsTeorica.size()));
            timeSlotsPratica.add(timeSlot);
            timeSlotsTeorica.remove(timeSlot);           
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
            
            MatriculaAlunos(gene);
            CorrigeNumeroDeAlunos(gene);   
        
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
                
                
                MatriculaAlunos(pratica);       
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
            
         
                
                gene.setTimeSlots(timeSlotsPratica);

                salas = SalasPossiveis(timeSlotsPratica,gene.getDisciplina().tipoSalaPratica);
                if(salas.size() > 0){
                    gene.setSala(salas.get(rng.nextInt(salas.size())));
                }else{
                    this.GenesNaoAlocados.add(gene);

                    LeitorDadosEntrada.Erro(gene.getDisciplina().descricao+" sem sala disponível para aulas praticas");
                }
                    MatriculaAlunos(gene);       
                                                // Removendo excesso de alunos
                CorrigeNumeroDeAlunos(gene);                

            return  null; // APENAS AULAS PRÁTICAS   
            
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
                        if(!Objects.isNull(horario[k][timeSlots.get(j).codigo -1]) 
                            && !Objects.isNull(horario[k][timeSlots.get(j).codigo -1].getSala()) 
                            && horario[k][timeSlots.get(j).codigo-1].getSala()== aux){
                            
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
            for(int j =0; j < ld.qtdPeriodos; j++){
                if(!Objects.isNull(horario[j][i]) && horario[j][i].getEstudantes().contains(estudante)){
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
    private void CriaGeneDisciplina(Disciplina disciplina, boolean restricao) {
        
        List<Professor> professoresPossiveis = new ArrayList<>(disciplina.ProfessoresPodem);// lista com os professores que podem ministrar a disciplina
        Professor professor;        
        if(professoresPossiveis.size() == 0) 
            return;
        if(restricao){
             List<TimeSlot> timeSlots = new ArrayList<TimeSlot>(disciplina.timesSlotsPossiveis); // lista com os timeSlots nos quais a disciplina DEVE ser ministrada
             
             do{
                 professor = professoresPossiveis.get(rng.nextInt(professoresPossiveis.size())); // selecionando professor aleatório
                 professoresPossiveis.remove(professor);
                 timeSlots = retornaTimeSlot(disciplina, professor); // List<TimeSlot> se tiver sucesso, null se não for possível para o professor
                 
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
     * Aloca a disciplina DADOS OS TIMESLOTS EM QUE HAVERÃO AULAS
     * @param gene
     * @param timeSlots 
     */
    public void alocar(Gene gene,List<TimeSlot> timeSlots){
        int l = timeSlots.size();
        
        for(int i =0;i<l;i++){
            horario[this.mapaCursoPeriodo(gene.getDisciplina())][timeSlots.get(i).codigo -1] = gene;
        }
    }
    /**
     * Coloca o gene na matriz.
     * @param gene
     * @param timeSlot
     * @param codigoPeriodo
     */
    public void alocar(Gene gene, int timeSlot, int codigoPeriodo){
        horario[codigoPeriodo][timeSlot] = gene;
    }
    
    public int funcaoFitness (Individuo individuo) {
       int nota = 9500;
       
       int pesoLacunasVazias  = 1;
       int pesoAlunosNaoMatriculados = 1;
       int pesoAlunosParcialmenteMatriculados = 1;
       int pesoDisciplinasParcialmenteAlocadas = 1;
       int pesoProfessorOscioso = 1;
       int pesoMateriaNaoAlocada = 1; 
       int lacunasVazias = 0;
        for(int i = 0; i<ld.qtdPeriodos;i++){
           lacunasVazias += verificaLacunasVazias(individuo.horario[i]);
        }
       int[] alunosNaoMatriculados = verificaAlunosNaoMatriculados(ld.Estudantes);
       int professoresOsciosos = verificaProfessoresOciosos(ld.Professores);
       int materiasNaoAlocadas = verificaMateriasNaoAlocadas();
       
       nota = nota - pesoLacunasVazias*lacunasVazias - pesoAlunosNaoMatriculados*alunosNaoMatriculados[0]
               - pesoAlunosParcialmenteMatriculados*alunosNaoMatriculados[1] - pesoDisciplinasParcialmenteAlocadas* alunosNaoMatriculados[2]
               - pesoProfessorOscioso*professoresOsciosos - pesoMateriaNaoAlocada*materiasNaoAlocadas; 
 
       
       return nota;
   
   }

    private int verificaLacunasVazias(Gene[] coluna ) {
        if(Objects.isNull(coluna)){
            return ld.qtdTimeSlots;
        }
        int lacunas = 0;
                
            for(int j = 0; j < ld.qtdTimeSlots; j++){
                if(Objects.isNull(coluna[j])){
                    lacunas++;
                }
            }
        
        
        return lacunas;
        }

    private int[] verificaAlunosNaoMatriculados(List<Estudante> estudantes) {
        int alunos = 0; // não matriculado em nenhuma disciplina
        int disciplinas = 0; // não matriculado em algumas disciplinas
        int parcial = 0; //  disciplinas com carga horaria incompleta
        int aulas = 0;
        int horasAula = 0;
        for(Estudante p : estudantes){
            for(Disciplina d : p.disciplinasACursar ){
                horasAula = d.cargaHorariaPratica + d.cargaHorariaTeoria;
                for(int i = 0; i< ld.qtdTimeSlots;i++){
                Gene aux = horario[this.mapaCursoPeriodo(d)][i];
                    if(!Objects.isNull(aux) && aux.getDisciplina() == d){
                        aulas++;
                        if(!aux.getEstudantes().contains(p)){
                            disciplinas++;
                        }
                        
                        if(aulas == horasAula){
                            break;
                        }
                    }
                    
                }
                if(aulas < horasAula){
                    if(aulas == 0){
                        parcial++;
                    }
                }
                
            }
            if(disciplinas == p.disciplinasACursar.size()){
                alunos++;
                disciplinas = 0;
            }
        }
        
        int[] retorno = {alunos,disciplinas,parcial};
        return retorno;
       }

    private int verificaProfessoresOciosos(List<Professor> professores) {
        int qtdProfessores = 0;
        int count = 0;
        for(Professor p : professores){
            int aulas = p.disciplinasAMinistrar.size();
            outroProfessor:
                for(Disciplina d : p.disciplinasAMinistrar){
                    for(int i = 0; i< ld.qtdTimeSlots;i++){
                        if(!Objects.isNull(horario[this.mapaCursoPeriodo(d)][i]) && horario[this.mapaCursoPeriodo(d)][i].getProfessor() == p){
                            break outroProfessor;
                        }
                    }
                    count++;
                }
            if(count == p.disciplinasAMinistrar.size()){
                qtdProfessores++;
            }
            count = 0;
            
            
        }
        return qtdProfessores;
       }

    private int verificaMateriasNaoAlocadas(){
        return this.GenesNaoAlocados.size();
       }
    
    
}
