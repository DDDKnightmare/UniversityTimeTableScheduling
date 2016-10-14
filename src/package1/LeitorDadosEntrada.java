package package1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class LeitorDadosEntrada {
    /**
     * Exibe mensagens de erro
     * @param mensagem 
     */
    public static void Erro(String mensagem){
        System.out.println(mensagem);
    }
    
    public static void main(String[] args) throws IOException {
       
      LeitorDados leitor = new LeitorDados();  
       
       leitor.Executa("ag-informacoes.csv");
    }
    
}
