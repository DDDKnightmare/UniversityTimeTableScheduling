package package1;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LeitorDados {
    
    public List<TimeSlot> TimeSlots; 
    public List<Curso> Cursos; 
    public List<TipoDeSala> TiposDeSala;
    public List<Sala> Salas;   
    public List<Disciplina> Disciplinas;
    public List<Disciplina> DisciplinasComRestricao; 
    public List<Disciplina> DisciplinasSemRestricao; 
    public List<Estudante> Estudantes; 
    public List<Professor> Professores; 
    public int peso1;
    public int peso2;
    public int peso3;
    public int peso4;
    public int peso5;
    public int notaInicial;
    public short qtdTimeSlots;
    public short qtdPeriodos;    
    
    public LeitorDados(){
        
        TimeSlots = new ArrayList<>();
        Cursos = new ArrayList<>();
        TiposDeSala = new ArrayList<>();
        Salas = new ArrayList<>();       
        Disciplinas = new ArrayList<>();
        DisciplinasComRestricao = new ArrayList<>();
        DisciplinasSemRestricao = new ArrayList<>();
        Estudantes = new ArrayList<>();
        Professores = new ArrayList<>();
    }
    
    public void Executa(String info, String restr) throws FileNotFoundException, IOException {        

        LeInformacoes(info);
        LeRestricoes(restr);   
        
        for(Disciplina d: Disciplinas){
            if(d.timeSlotsPossiveis.size() > 0)
                DisciplinasComRestricao.add(d);
            else
                DisciplinasSemRestricao.add(d);               
        }
        
        qtdTimeSlots = (short)TimeSlots.size();
        qtdPeriodos = somaPeriodos();
        
        System.out.println("concluído!");
    }         
    private boolean isSeparator(String line) {
        
        return  
            line.equals("TIMESLOT") ||
            line.equals("CURSO")||
            line.equals("TIPO DE SALA")||
            line.equals("SALA")||
            line.equals("DISCIPLINA")||
            line.equals("ESTUDANTE")||
            line.equals("PROFESSOR");
    }

    private void AddObject(String line, String separator, boolean restr) {
        
        switch (separator) {
            case "TIMESLOT":
                AddTimeSlot(line);
                break;
            case "CURSO":
                AddCurso(line);
                break;
            case "TIPO DE SALA":
                AddTipoSala(line);
                break;
            case "SALA":
                AddSala(line);
                break;
            case "DISCIPLINA": 
                if(restr){
                    AddRestricao(line, separator);
                    break;
                }                
                AddDisciplina(line);
                break;
            case "ESTUDANTE":
                AddEstudante(line);
                break;
            case "PROFESSOR":
                if(restr){
                    AddRestricao(line, separator);
                     break;
                }    
                AddProfessor(line);
                break;
        }
    }

    private void AddTimeSlot(String line) {
        
        String[] timeSlotLine = line.split(",");     
        
        int codigo = Integer.parseInt(timeSlotLine[0].replaceAll(" ", ""));
        int codigoDiaSemana = Integer.parseInt(timeSlotLine[1].replaceAll(" ", ""));
        String horarioInicial = timeSlotLine[2];
        String horarioTermino = timeSlotLine[3];        
        
        TimeSlot timeSlot = new TimeSlot(codigo, codigoDiaSemana, horarioInicial, horarioTermino);
        TimeSlots.add(timeSlot);
    }

    private void AddCurso(String line) {
        
        String[] timeSlotLine = line.split(",");   
        
        int codigo = Integer.parseInt(timeSlotLine[0].replaceAll(" ", ""));
        String nome = timeSlotLine[1];
        int numeroPeriodos = Integer.parseInt(timeSlotLine[2].replaceAll(" ", ""));
        int turno = Integer.parseInt(timeSlotLine[3].replaceAll(" ", ""));
        
        Curso curso = new Curso(codigo, nome, numeroPeriodos, turno);
        Cursos.add(curso);
    }

    private void AddTipoSala(String line) {
        
        String[] timeSlotLine = line.split(",");   
        
        int codigo = Integer.parseInt(timeSlotLine[0].replaceAll(" ", ""));
        String descricao = timeSlotLine[1];
        
        TipoDeSala tipoDeSala = new TipoDeSala(codigo, descricao);
        TiposDeSala.add(tipoDeSala);
    }

    private void AddSala(String line) {
        
        String[] timeSlotLine = line.split(",");   
        
        int codigo = Integer.parseInt(timeSlotLine[0].replaceAll(" ", ""));
        String descricao = timeSlotLine[1];
        int tipoDeSala = Integer.parseInt(timeSlotLine[2].replaceAll(" ", ""));
        int capacidade = Integer.parseInt(timeSlotLine[3].replaceAll(" ", ""));
        
        Sala sala = new Sala(codigo, descricao, tipoDeSala, capacidade);
        Salas.add(sala);
    }

    private void AddDisciplina(String line) {
        
        String[] timeSlotLine = line.split(",");   
        
        int codigo = Integer.parseInt(timeSlotLine[0].replaceAll(" ", ""));
        int codigoCurso = Integer.parseInt(timeSlotLine[1].replaceAll(" ", ""));
        int codigoPeriodo = Integer.parseInt(timeSlotLine[2].replaceAll(" ", ""));
        String descricao = timeSlotLine[3];
        int cargaHorariaTeoria = Integer.parseInt(timeSlotLine[4].replaceAll(" ", ""));
        int tipoSalaTeoria = Integer.parseInt(timeSlotLine[5].replaceAll(" ", ""));
        int cargaHorariaPratica = Integer.parseInt(timeSlotLine[6].replaceAll(" ", ""));
        int tipoSalaPratica = Integer.parseInt(timeSlotLine[7].replaceAll(" ", ""));  
        Curso curso = null;
        
        for(Curso c: Cursos){
            if(c.codigo == codigoCurso){
                curso = c;        
                break;                
            }                        
        }
        
        Disciplina disciplina = new Disciplina(codigo, curso, codigoPeriodo, descricao, 
                                               cargaHorariaTeoria, tipoSalaTeoria, 
                                               cargaHorariaPratica, tipoSalaPratica);
        
        Disciplinas.add(disciplina);
    }
    
    private void AddEstudante(String line) {
        try{
            String[] timeSlotLine = line.split(",");   

            int codigo = Integer.parseInt(timeSlotLine[0].replaceAll(" ", ""));        
            String nome = timeSlotLine[1];        
            List<Disciplina> disciplinasACursar = new ArrayList<>();

            for(int i = 2; i < timeSlotLine.length; i++){
                if(timeSlotLine[i].replaceAll(" ", "").equals(""))
                    continue;            
                int codigoDisciplina = Integer.parseInt(timeSlotLine[i].replaceAll(" ", ""));

                for(Disciplina d : Disciplinas)
                    if(d.codigo == codigoDisciplina)    
                        disciplinasACursar.add(d);
            }   

            Estudante estudante = new Estudante(codigo, nome, disciplinasACursar);
            Estudantes.add(estudante);
            for(Disciplina d : estudante.disciplinasACursar){
                d.estudantesAMatricular.add(estudante);
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }

    private void AddProfessor(String line) {
        
        String[] timeSlotLine = line.split(",");   
        
        int codigo = Integer.parseInt(timeSlotLine[0].replaceAll(" ", ""));
        String nome = timeSlotLine[1];
        List<Disciplina> disciplinasAMinistrar = new ArrayList<>();
        Professor professor = new Professor();
        
        for(int i = 2; i < timeSlotLine.length; i++){
            if(timeSlotLine[i].replaceAll(" ", "").equals(""))
                continue;
             int codigoDisciplina = Integer.parseInt(timeSlotLine[i].replaceAll(" ", ""));
            
            for(Disciplina d : Disciplinas)
                if(d.codigo == codigoDisciplina){ 
                    disciplinasAMinistrar.add(d);
                    d.ProfessoresPodem.add(professor);
                }
        }   
        
        professor.SetDados(codigo, nome, disciplinasAMinistrar);
        Professores.add(professor);
    }
    
    public short somaPeriodos(){
        short quantidade = 0;
        
        int l = Cursos.size();
        
        for(int i =0; i < l; i++){
            quantidade += Cursos.get(i).numeroPeriodos;
        }  
        
        return quantidade;
    }    
    
    private void LeInformacoes(String info) throws FileNotFoundException, IOException {
        List<String> lines = new ArrayList<>();
        
        InputStream is = new FileInputStream(info);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line = br.readLine();
        
         while (line != null) {
            
            if(line.startsWith("//")){
                
                line = br.readLine();
                continue;
            }                     
            
            lines.add(line.replace(".", ",")); 
            
            line = br.readLine();
        }
        
        br.close();    
        
        String separator = "";
        
        for(int i = 0; i < lines.size(); i++){  
            
            line = lines.get(i); 
            
            if(line == "")
                continue;
            
            if(isSeparator(line)){
                separator  = line;
                continue;
            }            
            AddObject(line, separator, false);                
        }         
    }

    private void LeRestricoes(String restr) throws FileNotFoundException, IOException {
         List<String> lines = new ArrayList<>();
        
        InputStream is = new FileInputStream(restr);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line = br.readLine();
        
         while (line != null) {
             
            if(line.startsWith("//")){
                
                line = br.readLine();
                continue;
            }                     
            
            lines.add(line.replace(".", ",")); 
            
            line = br.readLine();
        }
        
        br.close();    
        
        String separator = "";
        
        for(int i = 0; i < lines.size(); i++){  
            
            line = lines.get(i);   
            
            if(line.length() < 1)
                continue;
            
            if(isSeparator(line)){
                separator  = line;
                continue;
            }   
            AddObject(line, separator, true);
        }         
    }   

    private void AddRestricao(String line, String separator) {        
        switch(separator){        
            case "DISCIPLINA":
                AddRestricaoDisciplina(line);
                break;
                        
            case "PROFESSOR":    
                AddRestricaoProfessor(line);
                break;               
        }
    }

    private void AddRestricaoDisciplina(String line) {
        String[] restricaoDisciplina = line.split(",");
        int codigo = Integer.parseInt(restricaoDisciplina[0].replaceAll(" ", ""));
        Disciplina disciplina = null;
        int timeSlot = 0;
        
        for(Disciplina d : Disciplinas){
           if(d.codigo == codigo) {
               disciplina = d; 
               break;
           }         
        }
        
        if(disciplina == null)
            return;        
        
        for(int i = 1; i < restricaoDisciplina.length; i++){
            timeSlot = Integer.parseInt(restricaoDisciplina[i].replaceAll(" ", ""));
            for(int j = 0; j < TimeSlots.size(); j++){
                if(TimeSlots.get(j).codigo == timeSlot){
                    disciplina.timeSlotsPossiveis.add(TimeSlots.get(j));                    
                }                    
            }
        }            
    }

    private void AddRestricaoProfessor(String line) {
        String[] restricaoProfessor = line.split(",");
        int codigo = Integer.parseInt(restricaoProfessor[0].replaceAll(" ", ""));
        Professor professor = null;
        int timeSlot = 0;
        
        for(Professor p : Professores){
           if(p.codigo == codigo) {
               professor = p; 
               break;
           }         
        }
        
        if(professor == null)
            return;     
        
        
        for(int i = 1; i < restricaoProfessor.length; i++){
            timeSlot = Integer.parseInt(restricaoProfessor[i].replaceAll(" ", ""));
            for(int j = 0; j < TimeSlots.size(); j++){
                if(TimeSlots.get(j).codigo == timeSlot){
                    professor.timeSlotsImpossiveis.add(TimeSlots.get(j));  
                    break;
                }                    
            }
        }
    }
}

