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
    public List<Estudante> Estudantes; 
    public List<Professor> Professores; 
    public List<Professor> restricaoProfessor;
    public List<Disciplina> restricaoDisciplina;
    
    
    public LeitorDados(){
        
        TimeSlots = new ArrayList<>();
        Cursos = new ArrayList<>();
        TiposDeSala = new ArrayList<>();
        Salas = new ArrayList<>();
        Disciplinas = new ArrayList<>();
        Estudantes = new ArrayList<>();
        Professores = new ArrayList<>();
    }
    
    public void Executa(String path) throws FileNotFoundException, IOException { 
        
        List<String> lines = new ArrayList<>();
        
        InputStream is = new FileInputStream(path);
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
            
            if(isSeparator(line)){
                separator  = line;
                continue;
            }            
            AddObject(line, separator);                
        }
        
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

    private void AddObject(String line, String separator) {
        
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
                AddDisciplina(line);
                break;
            case "ESTUDANTE":
                AddEstudante(line);
                break;
            case "PROFESSOR":
                AddProfessor(line);
                break;
        }
    }

    private void AddTimeSlot(String line) {
        
        String[] timeSloteLine = line.split(",");     
        
        int codigo = Integer.parseInt(timeSloteLine[0].replaceAll(" ", ""));
        int codigoDiaSemana = Integer.parseInt(timeSloteLine[1].replaceAll(" ", ""));
        String horarioInicial = timeSloteLine[2];
        String horarioTermino = timeSloteLine[3];        
        
        TimeSlot timeSlot = new TimeSlot(codigo, codigoDiaSemana, horarioInicial, horarioTermino);
        TimeSlots.add(timeSlot);
    }

    private void AddCurso(String line) {
        
        String[] timeSloteLine = line.split(",");   
        
        int codigo = Integer.parseInt(timeSloteLine[0].replaceAll(" ", ""));
        String nome = timeSloteLine[1];
        int numeroPeriodos = Integer.parseInt(timeSloteLine[2].replaceAll(" ", ""));
        int turno = Integer.parseInt(timeSloteLine[3].replaceAll(" ", ""));
        
        Curso curso = new Curso(codigo, nome, numeroPeriodos, turno);
        Cursos.add(curso);
    }

    private void AddTipoSala(String line) {
        
        String[] timeSloteLine = line.split(",");   
        
        int codigo = Integer.parseInt(timeSloteLine[0].replaceAll(" ", ""));
        String descricao = timeSloteLine[1];
        
        TipoDeSala tipoDeSala = new TipoDeSala(codigo, descricao);
        TiposDeSala.add(tipoDeSala);
    }

    private void AddSala(String line) {
        
        String[] timeSloteLine = line.split(",");   
        
        int codigo = Integer.parseInt(timeSloteLine[0].replaceAll(" ", ""));
        String descricao = timeSloteLine[1];
        int tipoDeSala = Integer.parseInt(timeSloteLine[2].replaceAll(" ", ""));
        int capacidade = Integer.parseInt(timeSloteLine[3].replaceAll(" ", ""));
        
        Sala sala = new Sala(codigo, descricao, tipoDeSala, capacidade);
        Salas.add(sala);
    }

    private void AddDisciplina(String line) {
        
        String[] timeSloteLine = line.split(",");   
        
        int codigo = Integer.parseInt(timeSloteLine[0].replaceAll(" ", ""));
        int codigoCurso = Integer.parseInt(timeSloteLine[1].replaceAll(" ", ""));
        int codigoPeriodo = Integer.parseInt(timeSloteLine[2].replaceAll(" ", ""));
        String descricao = timeSloteLine[3];
        int cargaHorariaTeoria = Integer.parseInt(timeSloteLine[4].replaceAll(" ", ""));
        int tipoSalaTeoria = Integer.parseInt(timeSloteLine[5].replaceAll(" ", ""));
        int cargaHorariaPratica = Integer.parseInt(timeSloteLine[6].replaceAll(" ", ""));
        int tipoSalaPratica = Integer.parseInt(timeSloteLine[7].replaceAll(" ", ""));    
        
        Disciplina disciplina = new Disciplina(codigo, codigoCurso, codigoPeriodo, descricao, 
                                               cargaHorariaTeoria, tipoSalaTeoria, 
                                               cargaHorariaPratica, tipoSalaPratica);
        
        Disciplinas.add(disciplina);
    }
    
    private void AddEstudante(String line) {
        try{
        String[] timeSloteLine = line.split(",");   
        
        int codigo = Integer.parseInt(timeSloteLine[0].replaceAll(" ", ""));        
        String nome = timeSloteLine[1];        
        List<Disciplina> disciplinasACursar = new ArrayList<>();
        
        for(int i = 2; i < timeSloteLine.length; i++){
            if(timeSloteLine[i].replaceAll(" ", "").equals(""))
                continue;            
            int codigoDisciplina = Integer.parseInt(timeSloteLine[i].replaceAll(" ", ""));
            
            for(Disciplina d : Disciplinas)
                if(d.codigo == codigoDisciplina)    
                    disciplinasACursar.add(d);
        }   
        
        Estudante estudante = new Estudante(codigo, nome, disciplinasACursar);
        Estudantes.add(estudante);
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }

    private void AddProfessor(String line) {
        
        String[] timeSloteLine = line.split(",");   
        
        int codigo = Integer.parseInt(timeSloteLine[0].replaceAll(" ", ""));
        String nome = timeSloteLine[1];
        List<Disciplina> disciplinasAMinistrar = new ArrayList<>();
        
        for(int i = 2; i < timeSloteLine.length; i++){
            if(timeSloteLine[i].replaceAll(" ", "").equals(""))
                continue;
             int codigoDisciplina = Integer.parseInt(timeSloteLine[i].replaceAll(" ", ""));
            
            for(Disciplina d : Disciplinas)
                if(d.codigo == codigoDisciplina)    
                    disciplinasAMinistrar.add(d);
        }   
        
        Professor professor = new Professor(codigo, nome, disciplinasAMinistrar);
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
}

