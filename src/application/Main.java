package application;

import enigma.components.Rotor;
import application.test.EnigmaTester;
import enigma.EnigmaMachine;
import enigma.components.PlugBoard;

public class Main
{

   public static void main ( String[] args )
   {
      
      return;
   }
   
   public static int[] ConvertAlphabetString( String inVals )
   {
      int[] output = new int[inVals.length()];

      for( int i = 0; i < inVals.length(); i++ )
      {
         output[i] = ConvertAlphabetToValue( inVals.charAt( i ) ); 
      }
      return output;	   
   }
   
   public static String ConvertValueString( int[] inVals )
   {
      String output = "";
	   
      for( int i = 0; i < inVals.length; i++ )
      {
         output = output + ConvertValueToAlphabet(inVals[i]); 
      }
      return output;
   }
   
   public static int ConvertAlphabetToValue( char inValue )
   {
      int outValue = -1;

      switch ( Character.toUpperCase( inValue ) )
      {
         case 'A': outValue = 0; break;
         case 'B': outValue = 1; break;
         case 'C': outValue = 2; break;
         case 'D': outValue = 3; break;
         case 'E': outValue = 4; break;
         case 'F': outValue = 5; break;
         case 'G': outValue = 6; break;
         case 'H': outValue = 7; break;
         case 'I': outValue = 8; break;
         case 'J': outValue = 9; break;
         case 'K': outValue = 10; break;
         case 'L': outValue = 11; break;
         case 'M': outValue = 12; break;
         case 'N': outValue = 13; break;
         case 'O': outValue = 14; break;
         case 'P': outValue = 15; break;
         case 'Q': outValue = 16; break;
         case 'R': outValue = 17; break;
         case 'S': outValue = 18; break;
         case 'T': outValue = 19; break;
         case 'U': outValue = 20; break;
         case 'V': outValue = 21; break;
         case 'W': outValue = 22; break;
         case 'X': outValue = 23; break;
         case 'Y': outValue = 24; break;
         case 'Z': outValue = 25; break;
         default: throw new IllegalArgumentException("Vlaue not mapped in this alphabet");
      }
      return outValue;
   }

   public static char ConvertValueToAlphabet( int inValue )
   {
      char outValue = ' ';

      switch ( inValue ) 
      {
         case 0: outValue = 'A'; break;
         case 1: outValue = 'B'; break;
         case 2: outValue = 'C'; break;
         case 3: outValue = 'D'; break;
         case 4: outValue = 'E'; break;
         case 5: outValue = 'F'; break;
         case 6: outValue = 'G'; break;
         case 7: outValue = 'H'; break;
         case 8: outValue = 'I'; break;
         case 9: outValue = 'J'; break;
         case 10: outValue = 'K'; break;
         case 11: outValue = 'L'; break;
         case 12: outValue = 'M'; break;
         case 13: outValue = 'N'; break;
         case 14: outValue = 'O'; break;
         case 15: outValue = 'P'; break;
         case 16: outValue = 'Q'; break;
         case 17: outValue = 'R'; break;
         case 18: outValue = 'S'; break;
         case 19: outValue = 'T'; break;
         case 20: outValue = 'U'; break;
         case 21: outValue = 'V'; break;
         case 22: outValue = 'W'; break;
         case 23: outValue = 'X'; break;
         case 24: outValue = 'Y'; break;
         case 25: outValue = 'Z'; break;
         default: throw new IllegalArgumentException("Vlaue not mapped in this alphabet");
      }
      return outValue;
   }
   
   public static void RunTests()
   {
      EnigmaTester.TestPlugBoard();
      EnigmaTester.TestRotors();
      return;
   }
   
   
   
   

}
