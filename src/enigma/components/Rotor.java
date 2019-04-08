package enigma.components;

public interface Rotor
{  
   public int cipher( int inValue );
   
   public int getAlphabetSize();
   
   public Object clone();
   
   public String toString();
}