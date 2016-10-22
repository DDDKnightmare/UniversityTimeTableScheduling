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
    public static final Random rng = new Random(); // gerador de n�meros aleat�rios
    
//  Fim RNG    
    private static List<Disciplina> disciplinasSemRestricaoPSortear; // Disciplinas SEM restri��o a ser sorteadas
    
    private static List<Disciplina> disciplinasComRestricaoPSortear; // Disciplinas COM restri��o a ser sorteadas
      
    public Gene[][] horario;  // Matriz [timeSlot][mapeamento(Curso,Periodo)]
    
    private List<List<Gene>> listaGenesComRestricao;
    private List<List<Gene>> listaGenesSemRestricao;
    
    public Individuo(Individuo i){
        this.GenesNaoAlocados = new ArrayList<>(i.GenesNaoAlocados);
        this.ld = i.ld;
        this.listaGenesComRestricao = new ArrayList<>(i.listaGenesComRestricao);
        this.listaGenesSemRestricao = new ArrayList<>(i.listaGenesSemRestricao);
        this.nota = i.nota;
        
        for(int j = 0; j< ld.qtdPeriodos; j++){
            for(int k = 0; k < ld.qtdTimeSlots; k++){
                
                this.horario[j][k] = new Gene(i.horario[j][k]);
                                
            }
        }
        
        for(int j = 0; j < listaGenesComRestricao.size(); j++){
            
            listaGenesComRestricao.set(j, new ArrayList<>(listaGenesComRestricao.get(j)));
            
            for(int k = 0; k < listaGenesComRestricao.size(); k++){
                
                listaGenesComRestricao.get(j).set(k,new Gene(listaGenesComRestricao.get(j).get(k)));
                listaGenesComRestricao.get(j).get(k).setGenes(listaGenesComRestricao.get(j));
                
                
            }
            
        }
        
        for(int j = 0; j < listaGenesSemRestricao.size(); j++){
            
            listaGenesSemRestricao.set(j, new ArrayList<>(listaGenesSemRestricao.get(j)));
            
            for(int k = 0; k < listaGenesSemRestricao.size(); k++){
                
                listaGenesSemRestricao.get(j).set(k,new Gene(listaGenesSemRestricao.get(j).get(k)));
                listaGenesSemRestricao.get(j).get(k).setGenes(listaGenesSemRestricao.get(j));
                
                
            }
            
        }
        
        
    }
    
    public Individuo(LeitorDados ld){
        this.ld = ld;
        int t;
        int p;
        Gene aux;
        boolean teorica;
        List<Gene> g = new ArrayList<>();
        listaGenesComRestricao = new ArrayList<>();
        for(Disciplina d : ld.DisciplinasComRestricao){
            p = d.cargaHorariaPratica;
            t = d.cargaHorariaTeoria;
            for(int i = 0; i < t+p; i ++){
                teorica = (t > 0 ? (p > 0? rng.nextBoolean() : true) : false);
                aux = new Gene(d);
                aux.setTeorica(teorica);
                if(teorica){
                    t--;
                }else{
                    p--;
                }
                i--;
                g.add(aux);
                aux.setGenes(g);
                if(!listaGenesComRestricao.contains(aux.getGenes())){
                    listaGenesComRestricao.add(aux.getGenes());
                }
            }
            g = new ArrayList<>();
        }
        listaGenesSemRestricao = new ArrayList<>();
        for(Disciplina d : ld.DisciplinasSemRestricao){
            p = d.cargaHorariaPratica;
            t = d.cargaHorariaTeoria;
            for(int i = 0; i < t+p; i ++){
                teorica = (t > 0 ? (p > 0? rng.nextBoolean() : true) : false);
                aux = new Gene(d);
                aux.setTeorica(teorica);
                if(teorica){
                    t--;
                }else{
                    p--;
                }
                i--;
                g.add(aux);
                aux.setGenes(g);
                if(!listaGenesSemRestricao.contains(aux.getGenes())){
                    listaGenesSemRestricao.add(aux.getGenes());
                }
            }
            g = new ArrayList<>();
        }
        this.GenesNaoAlocados = new ArrayList<>();
        horario = new Gene[ld.qtdPeriodos][ld.qtdTimeSlots];
        geraIndividuo(ld);  
    }
    
    /**
     * Gera um novo individuo utilizando os dados lidos dos arquivos como base
     * @param ld 
     */
    private void geraIndividuo(LeitorDados ld){
        
        List<Gene> gaux;
        while(listaGenesComRestricao.size() > 0){
            
            gaux = listaGenesComRestricao.get(listaGenesComRestricao.size() > 1? rng.nextInt(listaGenesComRestricao.size()) : 0);
            
            this.SetGeneDisciplina(gaux);
            listaGenesComRestricao.remove(gaux);
        }
        
        while(listaGenesSemRestricao.size() > 0){
            
            gaux = listaGenesSemRestricao.get(listaGenesSemRestricao.size() > 1? rng.nextInt(listaGenesSemRestricao.size()) : 0);
            
            this.SetGeneDisciplina(gaux);
            listaGenesSemRestricao.remove(gaux);
        }
        
       
        Gene aux;
        
       
       for(int i = 0; i< this.GenesNaoAlocados.size(); i ++){
           aux = this.GenesNaoAlocados.get(i);
           this.AlocaGeneImcompletos(aux);
           if(aux != this.GenesNaoAlocados.get(i)){
                i--;
           }
       }
       
       for(Estudante e : ld.Estudantes){
           this.MatricularAluno(e);
       }
       
       this.nota = this.funcaoFitness(this);
    }
    
    
    /**
     * Retorna a coluna da matriz, dado o curso e periodo
     * @param disciplina
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
     * M�todo que aloca hor�rios para disciplinas incompletas, reduzindo o n�mero de lacunas, 
     * estudantes e professores ociosos --> consequ�ncia da redu��o de lacunas.
     * @param gene 
     * @return 
     */
    private void AlocaGeneImcompletos(Gene gene){
        Disciplina disciplina = gene.getDisciplina();
        if(Objects.isNull(gene)){
            return;
        }
        Gene aux;
        List<TimeSlot> timeSlots = (Objects.isNull(disciplina.timeSlotsPossiveis) 
                                    || disciplina.timeSlotsPossiveis.isEmpty())
                                    ?this.TimeSlotsTurno(gene.getDisciplina().codigoTurno) 
                                    : new ArrayList<>(disciplina.timeSlotsPossiveis);
        TimeSlot t;
        while(!timeSlots.isEmpty()){
            t = timeSlots.isEmpty()? null : (timeSlots.get(timeSlots.size() > 1? rng.nextInt(timeSlots.size()) : 0));
            aux = horario[this.mapaCursoPeriodo(gene.getDisciplina())][t.codigo - 1];
            if(!Objects.isNull(aux)){
                
                if(aux.getDisciplina() == gene.getDisciplina()){
                    
                }
                
                timeSlots.remove(t);
            }
        }
        
        if(!timeSlots.isEmpty()){
            List<Professor> professoresPodem = new ArrayList<>(gene.getDisciplina().ProfessoresPodem);
            Professor professor;
            List<Sala> salas;
            
            timeSlots:
            while(!timeSlots.isEmpty()){
                t = timeSlots.isEmpty()? null : (timeSlots.get(timeSlots.size() > 1? rng.nextInt(timeSlots.size()) : 0));
                if(Objects.isNull(t)){
                    break;
                }
                professores:
                for(int j = 0; j < professoresPodem.size(); j++){
                    professor = professoresPodem.get(rng.nextInt(professoresPodem.size()));
                    professoresPodem.remove(professor);
                    j--;
                    
                    if(this.verificaDispProf(t, professor)
                     &&
                     this.verificaDisponibilidade(gene.getDisciplina(), t)){

                           salas = this.SalasPossiveis(t, gene.getTeorica()? gene.getDisciplina().tipoSalaTeoria : gene.getDisciplina().tipoSalaPratica);

                           if(!Objects.isNull(salas) && !salas.isEmpty()){
                               
                               gene.setSala(salas.get(rng.nextInt(salas.size())));
                               
                               this.alocar(gene, t);

                               break professores;
                           }
                    }
                    
                }
                timeSlots.remove(t);
            }
        }
    }
    
    private int[] corrigeConflitoTimeSlot(List<Gene> genes){
        int count = 0;
        int[] retorno = {0,0};// {te�rica,pr�tica}
        for(Gene g : genes){
            count = 0;
            for(Gene h : genes){
                if(g.getTimeSlot() == h.getTimeSlot() 
                   &&
                   g.getDisciplina().codigoPeriodo == h.getDisciplina().codigoPeriodo
                   &&
                   g.getDisciplina() != h.getDisciplina()){
                    count ++;
                }

                if(count > 1){
                    if(rng.nextBoolean()){
                        retorno[g.getTeorica()?0:1] ++;
                        this.GenesNaoAlocados.add(g);
                        genes.remove(g);
                        g.setTimeSlot(null);
                    }else{
                        retorno[h.getTeorica()?0:1] ++;
                        this.GenesNaoAlocados.add(h);
                        genes.remove(h);
                        h.setTimeSlot(null);
                    }
                    count --;
                }
            }
        }
        
        return retorno;
    }
    
    
    
    
    
    
    
    
   /**
    * M�todo que recebe como parametro uma lista, e varre a lista, removendo elementos nulos.
    * 
    * @param <T>
    * @param lista
    * @return true == sucesso  || false == (lista vazia || lista nula)
    */
    private  <T> boolean VerificaElementosDaLista(List<T> lista){
        if(Objects.isNull(lista)){
            return false;
        }
        if(lista.isEmpty()){
            return false;
        }
        for(T t : lista){
            if(Objects.isNull(t)){
                lista.remove(t);
            }
        }
        
        
        return true;
        
    }
    
//    /**
//     * M�todo que seleciona hor�rios picados para adaptar professores �s disciplinas para que possam ser 
//     * ministradas mais disciplinas
//     * @param gene
//     * @param professor
//     * @return lista com os genes gerados
//     */
//    private List<Gene> RetornaNovosGenesParaCompletarCargaHoraria(Gene gene){
//        
//        if(Objects.isNull(gene)){
//            return null;
//        }
//        
//        int numTimeSlots;
//        
//        
//        
//            boolean teorica = gene.getTeorica();
//        
//        
//        if(!Objects.isNull(gene.getTimeSlot())){ // se j� tiver sido separado em um gene para aula te�rica e um para aula pr�tica
//            numTimeSlots = gene.getTimeSlot();                              // e n�o foi alocado por n�o caber todos os timeSlots
//        }else{
//            numTimeSlots = teorica? gene.getDisciplina().cargaHorariaTeoria : gene.getDisciplina().cargaHorariaPratica; // 
//        }
//        
//        List<TimeSlot> timeSlots = (Objects.isNull(gene.getDisciplina().timeSlotsPossiveis) // Se a disciplina n�o possui restri��o de hor�rio:
//                                   || gene.getDisciplina().timeSlotsPossiveis.isEmpty())    // timeSlots para verificar = 
//                                    ? this.TimeSlotsTurno(gene.getDisciplina().codigoTurno)  // timeSlots correspondentes ao turno do curso.(sem restri��o)
//                                    : gene.getDisciplina().timeSlotsPossiveis;              // ou timeSlots nos quais a disciplina DEVE ser ministrada(com restri��o)
//        
//        
//        List<Gene> genes = new ArrayList<>(); // lista com genes a serem retornados.
//        
//        List<TimeSlot> aux = new ArrayList<>();
//        List<TimeSlot> temp = new ArrayList<>();
//        int tipoSala = teorica? gene.getDisciplina().tipoSalaTeoria : gene.getDisciplina().tipoSalaPratica; 
//        
//        List<Professor> professores = gene.getDisciplina().ProfessoresPodem;
//        List<Sala> salas = new ArrayList<>(); 
//        Sala sala = null;
//        professores:
//        for(Professor p: professores){
//            
//            
//                
//
//                if(!timeSlots.isEmpty()){
//                    for(int i = 0; i< timeSlots.size() - 1;i++){
//
//                        TimeSlot t = timeSlots.get(i);
//                        aux.add(t);
//                        salas.addAll(this.SalasPossiveis(aux, tipoSala));
//                        if(!salas.isEmpty()){
//                            for(Sala s : salas){
//
//                                if(this.verificaDispProf(t, p)
//                                   &&
//                                   this.verificaDisponibilidade(gene.getDisciplina(), t)
//                                   &&
//                                   this.VerificaDisponibilidadeSala(t, s)){
//
//                                        timeSlots.remove(t);
//                                        temp.add(t);
//                                        aux.remove(t);
//
//                                }
//
//                                if(temp.size() == numTimeSlots){
//                                    break professores;
//                                }
//                            }
//                        }
//                        genes.add(new Gene(p,aux,gene.getDisciplina(),sala));
//                        if(numTimeSlots == aux.size()){
//                            break professores;
//                        }
//
//                        numTimeSlots -= aux.size();
//                        aux.clear();
//                }
//            }
//            if(numTimeSlots == 0){
//                break;
//            }
//        }
//               
//        return genes;
//                    
//                
//    }
//        
//        
//        
        
    
    
    
    
    /**
     * implementar
     */
    private void mutar(){
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * Verifica se determinado timeSlot j� est� ocupado com outra disciplina do mesmo per�odo.
     * @param disciplina
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
     * M�todo que recebe uma disciplina e um professor e 
     * retorna uma lista de timeSlots que seja poss�vel para ambos E que N�O estejam sendo utilizados.
     * @param disciplina
     * @param professor
     * @return 
     */
    public List<TimeSlot> retornaTimeSlots(Disciplina disciplina, Professor professor){             
        
        int qtdAulas = disciplina.cargaHorariaPratica + disciplina.cargaHorariaTeoria;
        /**
         * Disciplina Com restri��o
         */
        if(!Objects.isNull(disciplina.timeSlotsPossiveis) && !disciplina.timeSlotsPossiveis.isEmpty()){
            
                List<TimeSlot> timeSlots = new ArrayList<>(disciplina.timeSlotsPossiveis);


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
            
            return timeSlots.isEmpty()? null : timeSlots;
        }
        /**
         * Disciplina Sem restri��o
         */
        else{
            List<TimeSlot> timeSlots = new ArrayList<>(TimeSlotsTurno(disciplina.codigoTurno));
            
            for(int i =0;i<timeSlots.size();i++){
                if(!this.verificaDispProf(timeSlots.get(i), professor) || !this.verificaDisponibilidade(disciplina, timeSlots.get(i))){
                    timeSlots.remove(i);
                    i--;
                }
            }
            
            if(timeSlots.size() >= qtdAulas){
                return timeSlots;
            }
            return timeSlots.isEmpty()? null : timeSlots;
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
     * M�todo que matricula um aluno em aulas picadas de uma �nica disciplina INDEPENDENTE se a 
     * disciplina possui aulas suficientes para cumprir a carga hor�ria
     * @param estudante
     * @param disciplina
     * @param timeSlot
     * @return true == matriculado || false == n�o matriculado
     */
    private boolean MatriculaAluno(Estudante estudante, Disciplina disciplina, List<TimeSlot> timeSlots){
        int numAulas = disciplina.cargaHorariaPratica + disciplina.cargaHorariaTeoria;
        timeSlots:
        for(TimeSlot t : timeSlots){
            for(int i = 0; i< ld.qtdPeriodos; i++){
                if(!Objects.isNull(horario[i][t.codigo])){
                    if(estudante.disciplinasACursar.contains(horario[i][t.codigo - 1].getDisciplina())
                       &&
                       disciplina != horario[i][t.codigo - 1].getDisciplina()){

                        if(horario[i][t.codigo - 1].getEstudantes().contains(estudante)){
                            return false;

                    }


                    }

                    if(horario[i][t.codigo - 1].getDisciplina() == disciplina){
                        if(horario[i][t.codigo - 1].getEstudantes().contains(estudante)){
                            numAulas -= 1;
                            timeSlots.remove(t);
                        }
                        
                        else{
                            numAulas -= 1;
                        }

                    }
                    
                    
                }
            }
            if(numAulas == 0){
                break;
            }
        }
        for(TimeSlot t : timeSlots){
            horario[this.mapaCursoPeriodo(disciplina)][t.codigo - 1].addEstudante(estudante);
        }
        return true;
        
    }
    
    
    
    
    
    
    /**
     * Matricula alunos na na disciplina presente no gene fornecido
     * @param gene
     * @return 
     */
    private boolean MatriculaAlunos(Gene gene){
        List<Estudante> estudantes = new ArrayList<>(EstudantesParaMatricular(gene.getDisciplina(),ld.Estudantes));
        
        
        
        for(int i = 0; i < estudantes.size(); i++){
            if(!VerificaDisponibilidadeEstudante(gene.getDisciplina(), estudantes.get(i),gene.getTimeSlot())){
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
     * M�todo que recebe como par�metros um timeSlot e uma sala, e 
     * retorna verdadeiro se a sala n�o estiver sendo utilizada naquele timeSlot
     * ou falso se ela estiver sendo utilizada.
     * @param timeSlot
     * @param sala
     * @return 
     */
    private boolean VerificaDisponibilidadeSala(TimeSlot timeSlot, Sala sala){
        for(int i = 0; i< ld.qtdPeriodos; i++){
            if(!Objects.isNull(horario[i][timeSlot.codigo - 1])
               &&
               horario[i][timeSlot.codigo - 1].getSala() == sala){
                return false;
            }
        }
        
        return true;
    }
    
    
    
    
//    /**
//     * Define a sala para aulas te�ricas e a sala para aulas pr�ticas ######   PERMITE QUE HAJA MAIS ESTUDANTES EM UM DOS TIPOS DE AULA, CASO UMA DAS SALAS SUPORTE A QUANTIDADE
//     *                                                                         DE ALUNOS E A OUTRA N�O
//     * @param gene
//     * @return Gene da aula pratica || null(se houver apenas aulas pr�ticas ou apenas aulas te�ricas)
//     */
//    private Gene DefinirSala(Gene gene){
//        
//        List<TimeSlot> timeSlotsTeorica = gene.getTimeSlots();
//        
//        List<TimeSlot> timeSlotsPratica = new ArrayList<>();
//        TimeSlot timeSlot;
//        
//        for(int i = 0; i < gene.getDisciplina().cargaHorariaPratica;i++){// sorteando timeSlots para a aula pr�tica
//            timeSlot = timeSlotsTeorica.get(rng.nextInt(timeSlotsTeorica.size()));
//            timeSlotsPratica.add(timeSlot);
//            timeSlotsTeorica.remove(timeSlot);           
//        }
//        
//        List<Sala> salas = SalasPossiveis(timeSlotsTeorica,gene.getDisciplina().tipoSalaTeoria);//procurando salas dispon�veis para colocar as aulas teoricas
//        
//        if(!timeSlotsTeorica.isEmpty()){// Tem aulas TE�RICAS
//            gene.setTimeSlots(timeSlotsTeorica);// setando os timeSlots das aulas teoricas
//            
//            if(salas.size() > 0){ 
//                gene.setSala(salas.get(rng.nextInt(salas.size())));
//            }else{
//                this.GenesNaoAlocados.add(gene);
//                
//                LeitorDadosEntrada.Erro(gene.getDisciplina().descricao+" sem sala dispon�vel para aulas teoricas.");
//
//            }
//            
//            MatriculaAlunos(gene);
//            CorrigeNumeroDeAlunos(gene);  
//            
//            gene.setTeorica(true);
//        
//            if(!timeSlotsPratica.isEmpty()){ // verificando se h� aulas pr�ticas
//                
//                Gene pratica = new Gene(gene); // novo gene para a as aulas pr�ticas
//                
//                pratica.setTimeSlots(timeSlotsPratica);
//
//                salas = SalasPossiveis(timeSlotsPratica,gene.getDisciplina().tipoSalaPratica); // pegando as salas que podem ter essa disciplina
//                if(Objects.isNull(salas) || salas.size() > 0){
//                    pratica.setSala(salas.get(rng.nextInt(salas.size())));
//                }else{
//                    this.GenesNaoAlocados.add(gene);
//
//                    LeitorDadosEntrada.Erro(pratica.getDisciplina().descricao+" sem sala dispon�vel para aulas praticas");
//                }
//                
//                
//                MatriculaAlunos(pratica);       
//                                                // Removendo excesso de alunos
//                CorrigeNumeroDeAlunos(pratica);
//                
//                pratica.setTeorica(false);
//
//            return  pratica;
//            
//            }else{
//                return null; // apenas aula te�rica
//            }
//            
//        }else{ // N�O tem aulas TE�RICAS
//            gene.setTimeSlots(timeSlotsPratica);// setando os timeSlots das aulas pr�ticas 
//            
//            if(!Objects.isNull(salas) && salas.size() > 0){ 
//                gene.setSala(salas.get(rng.nextInt(salas.size())));
//            }else{
//                this.GenesNaoAlocados.add(gene);
//
//                LeitorDadosEntrada.Erro(gene.getDisciplina().descricao+" sem sala dispon�vel para aulas pr�ticas.");
//
//            }
//            
//         
//                
//                gene.setTimeSlots(timeSlotsPratica);
//
//                salas = SalasPossiveis(timeSlotsPratica,gene.getDisciplina().tipoSalaPratica);
//                if(salas.size() > 0){
//                    gene.setSala(salas.get(rng.nextInt(salas.size())));
//                }else{
//                    this.GenesNaoAlocados.add(gene);
//
//                    LeitorDadosEntrada.Erro(gene.getDisciplina().descricao+" sem sala dispon�vel para aulas praticas");
//                }
//                    MatriculaAlunos(gene);       
//                                                // Removendo excesso de alunos
//                CorrigeNumeroDeAlunos(gene);
//
//                gene.setTeorica(false);
//
//            return  null; // APENAS AULAS PR�TICAS   
//            
//        }
//            
//        
//        
//        
//            
//        
//        
//        
//        
//        
//        
//        
//    }
//    
    
    
    /**
     * Corrige o n�mero de estudantes matriculados em uma disciplina ap�s definir uma sala.
     * @param gene 
     */
    private void CorrigeNumeroDeAlunos(Gene gene){
        List<TimeSlot> timeSlots = new ArrayList<>();
        timeSlots.add(gene.getTimeSlot());
        if(Objects.isNull(gene.getSala())){
            gene.getEstudantes().clear(); // remove todos os alunos se a disciplina n�o tiver sala
            gene.setQtdEstudantes(0);
        }else{
            int capacidade = gene.getSala().capacidade;
            for(int i = 0; i < ld.qtdTimeSlots; i++){
                if(horario[this.mapaCursoPeriodo(gene.getDisciplina())][i].getDisciplina() == gene.getDisciplina()){
                    if(horario[this.mapaCursoPeriodo(gene.getDisciplina())][i].getSala().capacidade < capacidade){
                        capacidade = horario[this.mapaCursoPeriodo(gene.getDisciplina())][i].getSala().capacidade;
                    }
                    timeSlots.add(horario[this.mapaCursoPeriodo(gene.getDisciplina())][i].getTimeSlot());
                }
            }
            
            int numEstudantes = Objects.isNull(gene.getEstudantes())? 0 :gene.getEstudantes().size();
            while(numEstudantes > capacidade){
                
                gene.getEstudantes().remove(rng.nextInt(numEstudantes));
                numEstudantes--;
            }
            
            for(TimeSlot t : timeSlots){
                horario[this.mapaCursoPeriodo(gene.getDisciplina())][t.codigo - 1].setEstudantes(gene.getEstudantes());
            }
            
            
        }
        
    }
    
    
    
    
    /**
     * Dados os timeSlots e o tipo de sala desejado, retorna ma lista com as salas que podem ser utilizadas
     * @param timeSlots
     * @param tipoSala
     * @return List<Sala>
     */
    private List<Sala> SalasPossiveis(final List<TimeSlot> timeSlots, final int tipoSala){
        if(tipoSala == 0){
            return null;
        }
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
     * M�todo que recebe UM timeSlot e um tipo de sala, e retorna uma lista com as salas 
     * do tipo informado que podem ser utilizadas no timeSlot informado.
     * @param timeSlot
     * @param tipoSala
     * @return 
     */
    private List<Sala> SalasPossiveis(final TimeSlot timeSlot, final int tipoSala){
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
                for(int k = 0; k < ld.qtdPeriodos; k++){
                    if(!Objects.isNull(horario[k][timeSlot.codigo -1])
                        && !Objects.isNull(horario[k][timeSlot.codigo -1].getSala())){

                        salas.remove(i);
                        i--;
                        break;
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
    private boolean VerificaDisponibilidadeEstudante(final Disciplina d, final Estudante estudante, final TimeSlot timeSlot){
        
        
            for(int j =0; j < ld.qtdPeriodos; j++){
                if(!Objects.isNull(horario[j][timeSlot.codigo - 1]) && horario[j][timeSlot.codigo - 1].getEstudantes().contains(estudante)){
                    
                    if(horario[j][timeSlot.codigo - 1].getDisciplina() != d){
                        return false;
                    }
                    
                }
            }
        
        
        return true;
    }
    
    /**
     * Seta dados dos genes de uma disciplina
     * @param gene
     * @param restricao : if(true){com restri��o}else{sem restri��o}
     * @return 
     */
    private void SetGeneDisciplina(List<Gene> genes) {
        Disciplina disciplina = genes.get(0).getDisciplina();
        List<Professor> professoresPossiveis = new ArrayList<>(disciplina.ProfessoresPodem);// lista com os professores que podem ministrar a disciplina
        Professor professor;
        List<Sala> salas;
        if(professoresPossiveis.isEmpty()){
            return;
        }
        List<TimeSlot> timeSlots; // lista com os timeSlots nos quais a disciplina DEVE ser ministrada
        professores:
        do{
            professor = professoresPossiveis.get(rng.nextInt(professoresPossiveis.size())); // selecionando professor aleat�rio
            professoresPossiveis.remove(professor);
            timeSlots = retornaTimeSlots(disciplina, professor); // List<TimeSlot> se tiver sucesso, null se n�o for poss�vel para o professor
            if(!Objects.isNull(timeSlots)){ // Professor que pode dar aula encontrado

                List<Gene> aux = new ArrayList<>(genes);
                TimeSlot t;
                Gene j;
                timeSlots:
                while(!timeSlots.isEmpty()){
                    t = timeSlots.isEmpty()? null : timeSlots.get(timeSlots.size() > 0 ? rng.nextInt(timeSlots.size()) : 0);
                    while(!aux.isEmpty()){
                        j = aux.isEmpty()? null : aux.get(aux.size() > 1 ? rng.nextInt(aux.size()) : 0);    
                        if(Objects.isNull(j) || Objects.isNull(t)){
                            break timeSlots;
                        }
                        salas = this.SalasPossiveis(t, j.getTeorica()? disciplina.tipoSalaTeoria : disciplina.tipoSalaPratica);
                        if(Objects.isNull(salas) || salas.isEmpty()){
                            aux.remove(j);
                        }else{
                            j.setProfessor(professor);
                            j.setTimeSlot(t);
                            j.setSala(salas.get(salas.size()>1?rng.nextInt(salas.size()): 0));
                            
                            this.alocar(j, j.getTimeSlot());
                            
                            timeSlots.remove(t);
                            aux.remove(j);
                            break;
                        }
                    }
                    timeSlots.remove(t);
                }
            }
        }while(!professoresPossiveis.isEmpty());
    }
    
//    private void SetGeneDisciplinaSemRestricao(Disciplina disciplina) {
//
//    }
    
//    /**
//     * Aloca a disciplina DADOS OS TIMESLOTS EM QUE HAVER�O AULAS
//     * @param gene
//     * @param timeSlots 
//     */
//    public void alocar(Gene gene,List<TimeSlot> timeSlots){
//        int l = timeSlots.size();
//        
//        for(int i =0;i<l;i++){
//            horario[this.mapaCursoPeriodo(gene.getDisciplina())][timeSlots.get(i).codigo -1] = gene;
//        }
//    }
    
    
    /**
     * Coloca o gene na matriz.
     * @param gene
     * @param timeSlot
     */
    public void alocar(Gene gene, TimeSlot timeSlot){
        
        if(Objects.isNull(horario[this.mapaCursoPeriodo(gene.getDisciplina())][timeSlot.codigo - 1])){
            horario[this.mapaCursoPeriodo(gene.getDisciplina())][timeSlot.codigo - 1] = gene;
        }else{
            if(horario[this.mapaCursoPeriodo(gene.getDisciplina())][timeSlot.codigo - 1] != gene){
                if(this.GenesNaoAlocados.contains(gene)){
                    this.GenesNaoAlocados.add(gene);
                }
                gene.setNull();
            }
        }
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
       int[] materiasNaoAlocadas = verificaMateriasNaoAlocadas();//{total,parcial(n�mero de aulas faltando)}
       
       nota = nota 
               - pesoLacunasVazias*lacunasVazias 
               - pesoAlunosNaoMatriculados*alunosNaoMatriculados[0]
               - pesoAlunosParcialmenteMatriculados*alunosNaoMatriculados[1] 
               - pesoDisciplinasParcialmenteAlocadas*materiasNaoAlocadas[1]
               - pesoProfessorOscioso*professoresOsciosos 
               - pesoMateriaNaoAlocada*materiasNaoAlocadas[0]; 
 
       
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
        int alunos = 0; // n�o matriculado em nenhuma disciplina
        int disciplinas = 0; // n�o matriculado em algumas disciplinas
        int aulas; // n�mero de aulas da disciplina encontrados no hor�rio
        int horasAula = 0; // n�mero de aulas que a disciplina deve ter
        for(Estudante p : estudantes){
            for(Disciplina d : p.disciplinasACursar ){
                aulas = 0;
                horasAula = d.cargaHorariaPratica + d.cargaHorariaTeoria;
                for(int i = 0; i< ld.qtdTimeSlots;i++){
                Gene aux = horario[this.mapaCursoPeriodo(d)][i];
                    if(!Objects.isNull(aux) && aux.getDisciplina() == d){
                        aulas++;
                        if(Objects.isNull(aux.getEstudantes()) || !aux.getEstudantes().contains(p)){
                            disciplinas++;
                        }
                        
                        if(aulas == horasAula){
                            break;
                        }
                    }
                    
                }

                
            }
            if(disciplinas == p.disciplinasACursar.size()){
                alunos++;
                disciplinas = 0;
            }
        }
        
        int[] retorno = {alunos,disciplinas};
        return retorno;
       }

    private int verificaProfessoresOciosos(List<Professor> professores){
        int qtdProfessores = 0;
        int count = 0;
        for(Professor p : professores){
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

    private int[] verificaMateriasNaoAlocadas(){
        int numAulas;
        int[] retorno = new int[2];
        retorno[1] = 0;
        retorno[0] = 0;
        int aux = 0;
        disciplina:
        for(Disciplina d : ld.Disciplinas){
            numAulas = d.cargaHorariaPratica + d.cargaHorariaTeoria;
            for(int i = 0; i < ld.qtdTimeSlots; i ++){
                if(!Objects.isNull(horario[this.mapaCursoPeriodo(d)][i]) && horario[this.mapaCursoPeriodo(d)][i].getDisciplina() == d){
                    aux ++;
                }
                if(aux == numAulas){
                    aux = 0;
                    continue disciplina;
                }
                
            }
            if(aux == 0){
                retorno[0] ++;
            }else{
                retorno[1]+= aux;
                retorno[1] = 0;
            }
        }
        
        return retorno;
       }
    
    private void MatricularAluno(Estudante e){
       int disciplinasCursadas;
       int capacidadeSala = -1;
       List<TimeSlot> timeSlots = new ArrayList<>(ld.TimeSlots);
           disciplinasCursadas = 0;
           Gene gene;
           disciplinas:
           for(Disciplina d: e.disciplinasACursar){
                for(int i = 0; i < timeSlots.size(); i++){
                   if(!Objects.isNull(horario[this.mapaCursoPeriodo(d)][i])){
                       gene = null;
                       if(horario[this.mapaCursoPeriodo(d)][i].getDisciplina() == d){
                            gene = horario[this.mapaCursoPeriodo(d)][i];
                            for(Gene g : gene.getGenes()){
                                if(!Objects.isNull(g.getTimeSlot())){
                                    if(g.getSala().capacidade < capacidadeSala || capacidadeSala == -1){
                                        capacidadeSala = g.getSala().capacidade;
                                    }
                                    timeSlots.remove(g.getTimeSlot());
                                    if(g.getTimeSlot().codigo <= i){
                                        i--;
                                    }
                                }
                            }
                            for(Gene g : gene.getGenes()){
                                if(!Objects.isNull(g.getTimeSlot())){
                                    if(g.getEstudantes() != gene.getEstudantes()){
                                        g.setEstudantes(gene.getEstudantes());
                                    }
                                    if(capacidadeSala > -1 && g.getQtdEstudantes() < capacidadeSala){
                                        if(!g.getEstudantes().contains(e)){
                                            g.addEstudante(e);
                                        }
                                        
                                    }
                                }
                            }
                            for(Gene g : gene.getGenes()){
                                if(g.getEstudantes().contains(e)){
                                    disciplinasCursadas++;
                                    break;
                                }
                            }
                       }
                       if(!Objects.isNull(gene)){
                            break;
                       }
                   }
                }
           }
           
           if(disciplinasCursadas > 10){
               DesmatriculaAluno(e);
           }
           
       
    }
    
    private void DesmatriculaAluno(Estudante e){
        
        
        
    }
    
    public Individuo mutacao (Individuo individuo){
        Gene gene = null;
        int linha = 0;
        int coluna = 0;
        int linha2 =0;
        int coluna2 =0;
        
        
        // pegar um gene aleatorio
        while(gene == null){ 
            linha = rng.nextInt(ld.qtdPeriodos);
            coluna = rng.nextInt(ld.qtdTimeSlots);
            gene = individuo.horario[linha][coluna];
        }
        //procurar um lugar aleratorio
       
        while(horario[linha2][coluna2] != null){
            linha2 = rng.nextInt(ld.qtdPeriodos);
            coluna2 = rng.nextInt(ld.qtdTimeSlots);
        }
        individuo.horario[linha][coluna] = null;
        individuo.horario[linha2][coluna2] = gene;
        return individuo;
    }
    
    
}
