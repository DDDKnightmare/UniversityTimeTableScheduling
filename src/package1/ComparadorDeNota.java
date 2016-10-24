package package1;

import java.util.Comparator;


public class ComparadorDeNota implements Comparator<Individuo> {

    @Override
    public int compare(Individuo o1, Individuo o2) {
        if(o1.getNota() < o2.getNota())
            return -1;
        else if(o1.getNota() > o2.getNota())
            return 1;
        else 
            return 0;
    }    
}
