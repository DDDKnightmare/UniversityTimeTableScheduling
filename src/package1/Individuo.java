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
      
    public Gene[][] horario;  // Matriz [mapeamento(Curso,Periodo)][timeSlot]
    
    private List<List<Gene>> listaGenesComRestricao;
    private List<List<Gene>> listaGenesSemRestricao;
    
    public Individuo(Individuo i){
        this.GenesNaoAlocados = new ArrayList<>();
//        List<Gene> genes;
//        for(Gene g : GenesNaoAlocados){
//            genes = g.getGenes();
//            for(Gene k : genes){
//                k = new Gene(k,true);
//                if(g.getTimeSlot() == k.getTimeSlot()
//                        &&
//                   g.getDisciplina() == k.getDisciplina()){
//                    k.setGenes(genes);
//                }
//            }
//            
//            
//        }
        this.ld = i.ld;
        this.listaGenesComRestricao = new ArrayList<>(i.listaGenesComRestricao);
        this.listaGenesSemRestricao = new ArrayList<>(i.listaGenesSemRestricao);
        this.nota = i.nota;
        this.horario = new Gene[ld.qtdPeriodos][ld.qtdTimeSlots];
        for(int j = 0; j< ld.qtdPeriodos; j++){
            for(int k = 0; k < ld.qtdTimeSlots; k++){
                if(!Objects.isNull(i.horario[j][k])){
                    this.horario[j][k] = new Gene(i.horario[j][k]);
                }
            }
        }
        
        for(int j = 0; j < listaGenesComRestricao.size(); j++){
            
            listaGenesComRestricao.set(j, new ArrayList<>(listaGenesComRestricao.get(j)));
            
            for(int k = 0; k < listaGenesComRestricao.get(j).size(); k++){
                if(i.GenesNaoAlocados.contains(listaGenesComRestricao.get(j).get(k))){
                    
                    listaGenesComRestricao.get(j).set(k,new Gene(listaGenesComRestricao.get(j).get(k)));
                    listaGenesComRestricao.get(j).get(k).setGenes(listaGenesComRestricao.get(j));
                    this.GenesNaoAlocados.add(listaGenesComRestricao.get(j).get(k));
                }else{
                    
                
                    listaGenesComRestricao.get(j).set(k,new Gene(listaGenesComRestricao.get(j).get(k)));
                    listaGenesComRestricao.get(j).get(k).setGenes(listaGenesComRestricao.get(j));
                
                }
                
            }
            
        }
        
        for(int j = 0; j < listaGenesSemRestricao.size(); j++){
            
            listaGenesSemRestricao.set(j, new ArrayList<>(listaGenesSemRestricao.get(j)));
            
            for(int k = 0; k < listaGenesSemRestricao.get(j).size(); k++){
                if(i.GenesNaoAlocados.contains(listaGenesSemRestricao.get(j).get(k))){
                    
                    listaGenesSemRestricao.get(j).set(k,new Gene(listaGenesSemRestricao.get(j).get(k)));
                    listaGenesSemRestricao.get(j).get(k).setGenes(listaGenesSemRestricao.get(j));
                    this.GenesNaoAlocados.add(listaGenesSemRestricao.get(j).get(k));
                }else{
                    
                
                    listaGenesSemRestricao.get(j).set(k,new Gene(listaGenesSemRestricao.get(j).get(k)));
                    listaGenesSemRestricao.get(j).get(k).setGenes(listaGenesSemRestricao.get(j));
                
                }
            }
            
        }
        
        
    }
    
    public List<List<Gene>> getGenesComRestricao(){
        return this.listaGenesComRestricao;
    }
    
    public List<List<Gene>> getGenesSemRestricao(){
        return this.listaGenesSemRestricao;
    }
    
    public void setGenesComRestricao(List<List<Gene>> listaGenesComRestricao){
        this.listaGenesComRestricao = listaGenesComRestricao;
    }
    
    public void setGenesSemRestricao(List<List<Gene>> listaGenesSemRestricao){
        this.listaGenesSemRestricao = listaGenesSemRestricao;
    }
    
    public List<Gene> getGenesNaoAlocados(){
        return this.GenesNaoAlocados;
    }
    
    public void setGenesNaoAlocados(List<Gene> GenesNaoAlocados){
        this.GenesNaoAlocados = GenesNaoAlocados;
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
        List<List<Gene>> listaAux = new ArrayList<>(listaGenesComRestricao);
        List<Gene> gaux;
        while(listaAux.size() > 0){
            
            gaux = listaAux.get(listaAux.size() > 1? rng.nextInt(listaAux.size()) : 0);
            
            this.SetGeneDisciplina(gaux);
            listaAux.remove(gaux);
        }
        listaAux = new ArrayList<>(listaGenesSemRestricao);
        while(listaAux.size() > 0){
            
            gaux = listaAux.get(listaAux.size() > 1? rng.nextInt(listaAux.size()) : 0);
            
            this.SetGeneDisciplina(gaux);
            listaAux.remove(gaux);
        }
        
       
        Gene aux;
        
       
       for(int i = 0; i< this.GenesNaoAlocados.size(); i ++){
           aux = this.GenesNaoAlocados.get(i);
           this.AlocaGeneImcompletos(aux);
           if(i >= this.GenesNaoAlocados.size() || aux != this.GenesNaoAlocados.get(i)){
                i--;
           }
       }
       
       for(Estudante e : ld.Estudantes){
           this.MatricularAluno(e);
       }
       
       this.funcaoFitness();
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
    
    
    public void removerConcorrentes(Gene gene){
        
        int t = gene.getTimeSlot().codigo -1;
        Gene aux;
        for(int i = 0; i < ld.qtdPeriodos; i ++){
            aux = horario[i][t];
            if(!Objects.isNull(aux)){
                if(aux.getProfessor() == gene.getProfessor()
                   ||
                   aux.getSala() == gene.getSala()){
                        GenesNaoAlocados.add(aux);
                }else{
                    if(!Objects.isNull(gene.getEstudantes()) && !Objects.isNull(aux.getEstudantes())){
                        for(Estudante e : gene.getEstudantes()){
                            if(aux.getEstudantes().contains(e)){
                                aux.getEstudantes().remove(e);
                            }
                        }
                    }
                }
            }
        }
        Gene g;
        int i = GenesNaoAlocados.size()-1;
        while(i >= 0){
            g = GenesNaoAlocados.get(i);
            this.AlocaGeneImcompletos(g);
            i--;
        }
    }
    
    
    
     public void mutacao (){// trocar o horário de uma disciplina aleatória.
         Gene gene;
         List<Gene> genesAux;
         int indice;
         List<TimeSlot> slotsPossiveis;
         TimeSlot t;
         List<Professor> professoresPossiveis;
         List<Sala> salasPossiveis;
         Sala sala;
         Professor p;
         List<Professor> profAuxiliar;
         // pegar um gene aleatorio
//         while(gene == null){
//             gene = this.horario[rng.nextInt(ld.qtdPeriodos)][rng.nextInt(ld.qtdTimeSlots)];
//         }
outroGene:
        do{
        boolean comRestricao = rng.nextBoolean();
        indice = comRestricao ? rng.nextInt(listaGenesComRestricao.size()) : rng.nextInt(listaGenesSemRestricao.size());
        gene = comRestricao
                ? (listaGenesComRestricao.get(indice).get(rng.nextInt(listaGenesComRestricao.get(indice).size())))
                :(listaGenesSemRestricao.get(indice).get(rng.nextInt(listaGenesSemRestricao.get(indice).size())));

         professoresPossiveis = new ArrayList<>(gene.getDisciplina().ProfessoresPodem);
         slotsPossiveis = new ArrayList<>((Objects.isNull(gene.getDisciplina().timeSlotsPossiveis) 
                 || gene.getDisciplina().timeSlotsPossiveis.isEmpty())
                 ? TimeSlotsTurno(gene.getDisciplina().codigoPeriodo) 
                 : gene.getDisciplina().timeSlotsPossiveis);
         

         //procurar um lugar aleratorio
        }while(slotsPossiveis.isEmpty());
         
         
         t = slotsPossiveis.get(rng.nextInt(slotsPossiveis.size()));
         
         loop:
         while(horario[mapaCursoPeriodo(gene.getDisciplina())][t.codigo -1] != gene && !slotsPossiveis.isEmpty()){
             profAuxiliar = new ArrayList<>(professoresPossiveis);
            while(horario[mapaCursoPeriodo(gene.getDisciplina())][t.codigo -1] != gene && !profAuxiliar.isEmpty()){
                p = profAuxiliar.get(rng.nextInt(profAuxiliar.size()));
                profAuxiliar.remove(p);
                    if(p.timeSlotsImpossiveis.contains(t)){
                        continue;
                    }

                    salasPossiveis = SalasPossiveis(t, gene.getTeorica()
                            ? gene.getDisciplina().tipoSalaTeoria 
                            : gene.getDisciplina().tipoSalaPratica);

                    if(Objects.isNull(salasPossiveis) || salasPossiveis.isEmpty()){
                       salasPossiveis = new ArrayList<>();
                       for(Sala s : ld.Salas){
                           if(s.tipoDeSala == (gene.getTeorica()
                               ? gene.getDisciplina().tipoSalaTeoria 
                               : gene.getDisciplina().tipoSalaPratica)){

                                   salasPossiveis.add(s);

                           }
                       }
                    }
            
                    while(horario[mapaCursoPeriodo(gene.getDisciplina())][t.codigo -1] != gene 
                            &&
                          !salasPossiveis.isEmpty()){
                        
                        sala = salasPossiveis.get(rng.nextInt(salasPossiveis.size()));
                        salasPossiveis.remove(sala);
                        
                        for(int i = 0; i < ld.qtdPeriodos; i++){
                            if(!Objects.isNull(horario[i][t.codigo-1]) 
                                    && (horario[i][t.codigo-1].getSala() == sala 
                                        || horario[i][t.codigo-1].getProfessor() == p
                                        )
                                    ){
                                GenesNaoAlocados.add(horario[i][t.codigo-1]);
                                horario[i][t.codigo-1] = null;
                            }
                        }
                        
                        horario[mapaCursoPeriodo(gene.getDisciplina())][t.codigo-1] = gene;
                        gene.setTimeSlot(t);
                        gene.setProfessor(p);
                        gene.setSala(sala);
                        MatriculaAlunos(gene);
                        
                        genesAux = new ArrayList<>(GenesNaoAlocados);
                        for(Gene g: genesAux){
                            AlocaGeneImcompletos(g);
                            MatriculaAlunos(g);
                            CorrigeNumeroDeAlunos(g);
                        }
                        if(horario[mapaCursoPeriodo(gene.getDisciplina())][t.codigo -1] == gene){
                                break loop;
                          }
                    }
                    
                    MatriculaAlunos(gene);
                    CorrigeNumeroDeAlunos(gene);

            }
             
             if(horario[mapaCursoPeriodo(gene.getDisciplina())][t.codigo -1] != gene){
                 t = slotsPossiveis.get(rng.nextInt(slotsPossiveis.size()));
             }else{
                 break loop;
             }
             slotsPossiveis.remove(t);
         }
         this.horario[mapaCursoPeriodo(gene.getDisciplina())][t.codigo -1] = gene;
         
         this.funcaoFitness();
        }
    
    
   
    
    
    /**
     * Método que aloca horários para disciplinas incompletas, reduzindo o número de lacunas, 
     * estudantes e professores ociosos --> consequência da redução de lacunas.
     * @param gene 
     * @return 
     */
    private void AlocaGeneImcompletos(Gene gene){
        if(Objects.isNull(gene)){
            return;
        }
        
        Disciplina disciplina = gene.getDisciplina();
        
        
        List<TimeSlot> timeSlots = (Objects.isNull(disciplina.timeSlotsPossiveis) 
                                     || disciplina.timeSlotsPossiveis.isEmpty())
                                    ?this.TimeSlotsTurno(gene.getDisciplina().codigoTurno) 
                                    : new ArrayList<>(disciplina.timeSlotsPossiveis);
        TimeSlot t;
        List<Sala> s;
        timeSlots:
        while(!timeSlots.isEmpty()){
            t = timeSlots.get(rng.nextInt(timeSlots.size()));
            if(this.verificaDisponibilidade(disciplina, t)){
                for(Professor p : disciplina.ProfessoresPodem){
                    if(this.verificaDispProf(t, p)){
                        s = SalasPossiveis(t, gene.getTeorica()?disciplina.tipoSalaTeoria : disciplina.tipoSalaPratica);
                        if(!Objects.isNull(s)
                        &&
                        !s.isEmpty()){
                                
                                gene.setSala(s.get(rng.nextInt(s.size())));
                                gene.setProfessor(p);
                                gene.setTimeSlot(t);
                                if(this.alocar(gene)){
                                    break timeSlots;
                                }
                                
                                
                        }
                    }
                }
            }
            timeSlots.remove(t);
        }
        
        
    }
    
    private int[] corrigeConflitoTimeSlot(List<Gene> genes){
        int count = 0;
        int[] retorno = {0,0};// {teórica,prática}
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
    * Método que recebe como parametro uma lista, e varre a lista, removendo elementos nulos.
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
//     * Método que seleciona horários picados para adaptar professores às disciplinas para que possam ser 
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
//        if(!Objects.isNull(gene.getTimeSlot())){ // se já tiver sido separado em um gene para aula teórica e um para aula prática
//            numTimeSlots = gene.getTimeSlot();                              // e não foi alocado por não caber todos os timeSlots
//        }else{
//            numTimeSlots = teorica? gene.getDisciplina().cargaHorariaTeoria : gene.getDisciplina().cargaHorariaPratica; // 
//        }
//        
//        List<TimeSlot> timeSlots = (Objects.isNull(gene.getDisciplina().timeSlotsPossiveis) // Se a disciplina não possui restrição de horário:
//                                   || gene.getDisciplina().timeSlotsPossiveis.isEmpty())    // timeSlots para verificar = 
//                                    ? this.TimeSlotsTurno(gene.getDisciplina().codigoTurno)  // timeSlots correspondentes ao turno do curso.(sem restrição)
//                                    : gene.getDisciplina().timeSlotsPossiveis;              // ou timeSlots nos quais a disciplina DEVE ser ministrada(com restrição)
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
     * Verifica se determinado timeSlot já está ocupado com outra disciplina do mesmo período.
     * @param disciplina
     * @param timeSlot
     * @return 
     */
    public boolean verificaDisponibilidade(Disciplina disciplina, TimeSlot timeSlot){
        if(Objects.isNull(horario[this.mapaCursoPeriodo(disciplina)][timeSlot.codigo-1])){
            return true;
        }
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
    public List<TimeSlot> retornaTimeSlots(Disciplina disciplina, Professor professor){             
        
        int qtdAulas = disciplina.cargaHorariaPratica + disciplina.cargaHorariaTeoria;
        /**
         * Disciplina Com restrição
         */
        if(!Objects.isNull(disciplina.timeSlotsPossiveis) && !disciplina.timeSlotsPossiveis.isEmpty()){
            
                List<TimeSlot> timeSlots = new ArrayList<>(disciplina.timeSlotsPossiveis);


            for(int i = 0; i< timeSlots.size(); i++){
                if(!this.verificaDispProf(timeSlots.get(i), professor)|| !this.verificaDisponibilidade(disciplina, timeSlots.get(i))){
                    timeSlots.remove(i);
                    i--;
                }
            }

            
            
            return timeSlots.isEmpty()? null : timeSlots;
        }
        /**
         * Disciplina Sem restrição
         */
        else{
            List<TimeSlot> timeSlots = new ArrayList<>(TimeSlotsTurno(disciplina.codigoTurno));
            
            for(int i =0;i<timeSlots.size();i++){
                if(!this.verificaDispProf(timeSlots.get(i), professor)){
                    timeSlots.remove(i);
                    i--;
                }
            }
            
            
            return timeSlots.isEmpty()? null : timeSlots;
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
     * Método que matricula um aluno em aulas picadas de uma única disciplina INDEPENDENTE se a 
     * disciplina possui aulas suficientes para cumprir a carga horária
     * @param estudante
     * @param disciplina
     * @param timeSlot
     * @return true == matriculado || false == não matriculado
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
    public boolean MatriculaAlunos(Gene gene){
        
        if(Objects.isNull(gene.getTimeSlot())){
            return false;
        }
        
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
     * Método que recebe como parâmetros um timeSlot e uma sala, e 
     * retorna verdadeiro se a sala não estiver sendo utilizada naquele timeSlot
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
//     * Define a sala para aulas teóricas e a sala para aulas práticas ######   PERMITE QUE HAJA MAIS ESTUDANTES EM UM DOS TIPOS DE AULA, CASO UMA DAS SALAS SUPORTE A QUANTIDADE
//     *                                                                         DE ALUNOS E A OUTRA NÃO
//     * @param gene
//     * @return Gene da aula pratica || null(se houver apenas aulas práticas ou apenas aulas teóricas)
//     */
//    private Gene DefinirSala(Gene gene){
//        
//        List<TimeSlot> timeSlotsTeorica = gene.getTimeSlots();
//        
//        List<TimeSlot> timeSlotsPratica = new ArrayList<>();
//        TimeSlot timeSlot;
//        
//        for(int i = 0; i < gene.getDisciplina().cargaHorariaPratica;i++){// sorteando timeSlots para a aula prática
//            timeSlot = timeSlotsTeorica.get(rng.nextInt(timeSlotsTeorica.size()));
//            timeSlotsPratica.add(timeSlot);
//            timeSlotsTeorica.remove(timeSlot);           
//        }
//        
//        List<Sala> salas = SalasPossiveis(timeSlotsTeorica,gene.getDisciplina().tipoSalaTeoria);//procurando salas disponíveis para colocar as aulas teoricas
//        
//        if(!timeSlotsTeorica.isEmpty()){// Tem aulas TEÓRICAS
//            gene.setTimeSlots(timeSlotsTeorica);// setando os timeSlots das aulas teoricas
//            
//            if(salas.size() > 0){ 
//                gene.setSala(salas.get(rng.nextInt(salas.size())));
//            }else{
//                this.GenesNaoAlocados.add(gene);
//                
//                LeitorDadosEntrada.Erro(gene.getDisciplina().descricao+" sem sala disponível para aulas teoricas.");
//
//            }
//            
//            MatriculaAlunos(gene);
//            CorrigeNumeroDeAlunos(gene);  
//            
//            gene.setTeorica(true);
//        
//            if(!timeSlotsPratica.isEmpty()){ // verificando se há aulas práticas
//                
//                Gene pratica = new Gene(gene); // novo gene para a as aulas práticas
//                
//                pratica.setTimeSlots(timeSlotsPratica);
//
//                salas = SalasPossiveis(timeSlotsPratica,gene.getDisciplina().tipoSalaPratica); // pegando as salas que podem ter essa disciplina
//                if(Objects.isNull(salas) || salas.size() > 0){
//                    pratica.setSala(salas.get(rng.nextInt(salas.size())));
//                }else{
//                    this.GenesNaoAlocados.add(gene);
//
//                    LeitorDadosEntrada.Erro(pratica.getDisciplina().descricao+" sem sala disponível para aulas praticas");
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
//                return null; // apenas aula teórica
//            }
//            
//        }else{ // NÃO tem aulas TEÓRICAS
//            gene.setTimeSlots(timeSlotsPratica);// setando os timeSlots das aulas práticas 
//            
//            if(!Objects.isNull(salas) && salas.size() > 0){ 
//                gene.setSala(salas.get(rng.nextInt(salas.size())));
//            }else{
//                this.GenesNaoAlocados.add(gene);
//
//                LeitorDadosEntrada.Erro(gene.getDisciplina().descricao+" sem sala disponível para aulas práticas.");
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
//                    LeitorDadosEntrada.Erro(gene.getDisciplina().descricao+" sem sala disponível para aulas praticas");
//                }
//                    MatriculaAlunos(gene);       
//                                                // Removendo excesso de alunos
//                CorrigeNumeroDeAlunos(gene);
//
//                gene.setTeorica(false);
//
//            return  null; // APENAS AULAS PRÁTICAS   
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
     * Corrige o número de estudantes matriculados em uma disciplina após definir uma sala.
     * @param gene 
     */
    private void CorrigeNumeroDeAlunos(Gene gene){
        List<TimeSlot> timeSlots = new ArrayList<>();
        timeSlots.add(gene.getTimeSlot());
        if(Objects.isNull(gene.getSala())){
            gene.getEstudantes().clear(); // remove todos os alunos se a disciplina não tiver sala
            gene.setQtdEstudantes(0);
        }else{
            int capacidade = gene.getSala().capacidade;
            for(int i = 0; i < ld.qtdTimeSlots; i++){
                
                if(!Objects.isNull(horario[this.mapaCursoPeriodo(gene.getDisciplina())][i])){
                    if(horario[this.mapaCursoPeriodo(gene.getDisciplina())][i].getDisciplina() == gene.getDisciplina()){
                        if(horario[this.mapaCursoPeriodo(gene.getDisciplina())][i].getSala().capacidade < capacidade){
                            capacidade = horario[this.mapaCursoPeriodo(gene.getDisciplina())][i].getSala().capacidade;
                        }
                        if(!timeSlots.contains(horario[this.mapaCursoPeriodo(gene.getDisciplina())][i].getTimeSlot())){
                            timeSlots.add(horario[this.mapaCursoPeriodo(gene.getDisciplina())][i].getTimeSlot());
                        }
                    }
                
                }
            }
            
            int numEstudantes = Objects.isNull(gene.getEstudantes())? 0 :gene.getEstudantes().size();
            while(numEstudantes > capacidade){
                
                gene.getEstudantes().remove(rng.nextInt(numEstudantes));
                numEstudantes--;
            }
            
            for(TimeSlot t : timeSlots){
                if(!Objects.isNull(horario[this.mapaCursoPeriodo(gene.getDisciplina())][t.codigo - 1])){
                    horario[this.mapaCursoPeriodo(gene.getDisciplina())][t.codigo - 1].setEstudantes(gene.getEstudantes());
                }
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
     * Método que recebe UM timeSlot e um tipo de sala, e retorna uma lista com as salas 
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
                Sala sala;
                for(int k = 0; k < ld.qtdPeriodos; k++){
                    
                    if(!Objects.isNull(horario[k][timeSlot.codigo -1])
                        && !Objects.isNull(horario[k][timeSlot.codigo -1].getSala())
                        && horario[k][timeSlot.codigo -1].getSala() == aux){

                        salas.remove(horario[k][timeSlot.codigo -1].getSala());
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
     * @return boolean (true == pode se matricular  ; false == NÃO pode se matricular)
     */
    private boolean VerificaDisponibilidadeEstudante(final Disciplina d, final Estudante estudante, final TimeSlot timeSlot){
        
        
            for(int j =0; j < ld.qtdPeriodos; j++){
                if(!Objects.isNull(horario[j][timeSlot.codigo - 1]) && !Objects.isNull(horario[j][timeSlot.codigo - 1].getEstudantes()) && horario[j][timeSlot.codigo - 1].getEstudantes().contains(estudante)){
                    
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
     * @param restricao : if(true){com restrição}else{sem restrição}
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
            professor = professoresPossiveis.get(rng.nextInt(professoresPossiveis.size())); // selecionando professor aleatório
            professoresPossiveis.remove(professor);
            timeSlots = retornaTimeSlots(disciplina, professor); // List<TimeSlot> se tiver sucesso, null se não for possível para o professor
            if(!Objects.isNull(timeSlots)){ // Professor que pode dar aula encontrado

                List<Gene> aux = new ArrayList<>(genes);
                TimeSlot t;
                Gene j;
                timeSlots:
                while(!timeSlots.isEmpty()){
                    t = timeSlots.isEmpty()? null : timeSlots.get(timeSlots.size() > 0 ? rng.nextInt(timeSlots.size()) : 0);
                    while(!aux.isEmpty()){
                        j = aux.isEmpty()? null : aux.get(aux.size() > 1 ? rng.nextInt(aux.size()) : 0);    
                        if(Objects.isNull(j)){
                            timeSlots.remove(t);
                            continue timeSlots;
                            
                        }
                        if(Objects.isNull(t)){
                            aux.remove(j);
                            continue timeSlots;

                        }
                        salas = this.SalasPossiveis(t, j.getTeorica()? disciplina.tipoSalaTeoria : disciplina.tipoSalaPratica);
                        if(Objects.isNull(salas) || salas.isEmpty()){
                            aux.remove(j);
                        }else{
                            j.setProfessor(professor);
                            j.setTimeSlot(t);
                            j.setSala(salas.get(salas.size()>1?rng.nextInt(salas.size()): 0));
                            
                            this.alocar(j);
                            
                            
                            aux.remove(j);
                            break;
                        }
                    }
                    if(aux.isEmpty()){
                        break professores;
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
//     * Aloca a disciplina DADOS OS TIMESLOTS EM QUE HAVERÃO AULAS
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
     */
    public boolean alocar(Gene gene){
        
        if(Objects.isNull(horario[this.mapaCursoPeriodo(gene.getDisciplina())][gene.getTimeSlot().codigo - 1])){
            horario[this.mapaCursoPeriodo(gene.getDisciplina())][gene.getTimeSlot().codigo - 1] = gene;
            if(GenesNaoAlocados.contains(gene)){
                GenesNaoAlocados.remove(gene);
                return true;
            }
        }else{
            if(horario[this.mapaCursoPeriodo(gene.getDisciplina())][gene.getTimeSlot().codigo - 1] != gene){
                if(!this.GenesNaoAlocados.contains(gene)){
                    this.GenesNaoAlocados.add(gene);
                    
                }
            }
        }
        return false;
    }
    
    public void funcaoFitness () {
       int nota = ld.notaInicial;
       int pesoLacunasVazias  = ld.peso1;
       int pesoAlunosNaoMatriculados = 10*ld.peso2;
       int pesoAlunosParcialmenteMatriculados = ld.peso2;
       int pesoDisciplinasParcialmenteAlocadas = ld.peso3;
       int pesoProfessorOscioso = ld.peso4;
       int pesoMateriaNaoAlocada = ld.peso5; 
       int lacunasVazias = 0;
       for(int i = 0; i<ld.qtdPeriodos;i++){
           lacunasVazias += verificaLacunasVazias(this.horario[i]);
       }
       int[] alunosNaoMatriculados = verificaAlunosNaoMatriculados(ld.Estudantes);
       int professoresOsciosos = verificaProfessoresOciosos(ld.Professores);
       int[] materiasNaoAlocadas = verificaMateriasNaoAlocadas();//{total,parcial(número de aulas faltando)}
       
       nota = nota 
               - pesoLacunasVazias*lacunasVazias 
               - pesoAlunosNaoMatriculados*alunosNaoMatriculados[0]
               - pesoAlunosParcialmenteMatriculados*alunosNaoMatriculados[1] 
               - pesoDisciplinasParcialmenteAlocadas*materiasNaoAlocadas[1]
               - pesoProfessorOscioso*professoresOsciosos 
               - pesoMateriaNaoAlocada*materiasNaoAlocadas[0]; 
 
       
       this.nota = nota;
   
   }
    
    
    
    
    public int verificaLacunasVazias(Gene[] coluna ) {
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
        int aulas; // número de aulas da disciplina encontrados no horário
        int horasAula = 0; // número de aulas que a disciplina deve ter
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
            }
                aux = 0;
            
        }
        
        return retorno;
       }
    
    public void MatricularAluno(Estudante e){
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
        List<List<Gene>> aux = new ArrayList<>();
        for(List<Gene> l : this.listaGenesComRestricao){
            if(e.disciplinasACursar.contains(l.get(0).getDisciplina())){
                for(Gene g : l){
                    if(g.getEstudantes().contains(e)){
                        aux.add(l);
                        break;
                    }
                }
            }
        }
        for(List<Gene> l : this.listaGenesSemRestricao){
            if(e.disciplinasACursar.contains(l.get(0).getDisciplina())){
                for(Gene g : l){
                    if(g.getEstudantes().contains(e)){
                        aux.add(l);
                        break;
                    }
                }
            }
        }
        int numDisciplinasMatriculadas = aux.size();
        i:
        for(int i = 0; i < numDisciplinasMatriculadas - 2;i++){
            j:
            for(int j = 1; j < numDisciplinasMatriculadas - 1;j++){
                
                for(Gene g : aux.get(i)){
                    
                    for(Gene h : aux.get(j)){
                        
                        if(h.getTimeSlot() == g.getTimeSlot()){
                            
                            if(rng.nextBoolean()){
                                
                                g.getEstudantes().remove(e);
                                aux.remove(i);
                                i--;
                                
                            }else{
                                h.getEstudantes().remove(e);
                                aux.remove(j);
                                
                            }
                            
                            j--;
                            numDisciplinasMatriculadas--;
                        }
                        
                    }
                    
                }
                
            }
            
            
        }
        
    }
    
     public String toString(){
        return ""+this.nota;
    }
     
    public void horarioPrint(){
        
        
        System.out.print("         ");
        for(int i = 0; i<ld.qtdPeriodos; i++){
            if(i<9)
                System.out.print("|p"+(i+1)+"  ");
            
            else
                System.out.print("|p"+(i+1)+" ");
        }
        System.out.print("\n");
        for(int i = 23; i< 168; i++){
            if(i<100)
                System.out.print("slot  "+ i +":");
            else
                System.out.print("slot "+ i +":");
            for(int j = 0; j< 32; j++){
                if(horario[j][i] == null){
                    
                    System.out.print("|    ");
                }
                else{
                    if(horario[j][i].getDisciplina().codigo <1000)
                        System.out.print("|"+horario[j][i].getDisciplina().codigo + " ");
                    else
                        System.out.print("|"+horario[j][i].getDisciplina().codigo);
                }
                
            }
            
            System.out.println("");
        }
    }
}
