package package1;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author GMTH
 */
import java.util.Scanner;

public class Main {
    private static int geracao = 1;
    
    private static Scanner entrada = new Scanner(System.in);
    
    private static int numIndividuos;
    private static int numGeracoes;
    private static int N = 0;
    
    public static void Main(String args[]){
        System.out.println("Número de indivíduos:");
        numIndividuos = entrada.nextInt();
        System.out.println(System.getProperty("line.separator")+"Número de gerações:");
        numGeracoes = entrada.nextInt();
        
        
        while (geracao < numGeracoes){
            
            
            
            
            geracao++;
        }
        
    }
}
