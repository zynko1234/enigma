package application;

import enigma.components.Rotor;

import java.io.IOException;
import java.io.UTFDataFormatException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import enigma.components.ClockRotor;
import enigma.components.ReflectingRotor;

public class Main
{

   public static void main ( String[] args )
   {
      char[] x = new char[ClockRotor.UNICODE_SIZE];
      
      for( int i = 0; i < x.length; i++ ) 
      {
         x[i] = (char)i;
      }
      
      String y = String.valueOf( x );
      StringBuilder z = new StringBuilder();
      final String outputFmt = "\rPercent Complete: %.2f%%   ";
      
      for( int i = 0; i < y.length(); i++ )
      {
         z.append( i + ": " + y.charAt( i ) + "\n" ) ;
         double percCalculation = ( ( 100* (double)( i + 1 ) ) / y.length() );
         System.out.printf( outputFmt, percCalculation);
      }
      System.out.println( );
      
      byte[] theBytes = z.toString().getBytes(StandardCharsets.UTF_8);
      
      try 
      {
         Files.write(Paths.get("./codepoints.txt"), theBytes);
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
      
//      int[] x = {1, 3, 0, 2};
//      ClockRotor testRotor = new ClockRotor( x, 2, 0 ); 
//      
//      ClockRotor cloneRotor = testRotor.clone();
//      
//      System.out.println( "Original" );
//      System.out.println( testRotor.toString() );
//      System.out.println( "CLONE" );
//      System.out.println( cloneRotor.toString() );
//      
//      int[] testInput = {0, 1, 2, 3};
//      int[] testOutput = new int[testInput.length];
//      
//      for( int i = 0; i < testInput.length; i++ )
//      {  
//         testOutput[i] = testRotor.cipher( testInput[i]);
//         System.out.print( testOutput[i] );
//      }
//      
//      System.out.println( "" );
//      
//      testRotor.reset();
//      
//      for( int i = 0; i < testOutput.length; i++ )
//      {
//         System.out.print( testRotor.cipher( testOutput[i] ) );
//      }      
      
      return;
   }

   public static int[] convertAlphabetString ( String inVals )
   {
      int[] output = new int[ inVals.length() ];

      for ( int i = 0; i < inVals.length(); i++ )
      {
         output[ i ] = convertAlphabetToValue( inVals.charAt( i ) );
      }
      return output;
   }

   public static String convertValueString ( int[] inVals )
   {
      String output = "";

      for ( int i = 0; i < inVals.length; i++ )
      {
         output = output + convertValueToAlphabet( inVals[ i ] );
      }
      return output;
   }

   public static int convertAlphabetToValue ( char inValue )
   {
      int outValue = -1;

      switch ( Character.toUpperCase( inValue ) )
      {
         case 'A':
            outValue = 0;
            break;
         case 'B':
            outValue = 1;
            break;
         case 'C':
            outValue = 2;
            break;
         case 'D':
            outValue = 3;
            break;
         case 'E':
            outValue = 4;
            break;
         case 'F':
            outValue = 5;
            break;
         case 'G':
            outValue = 6;
            break;
         case 'H':
            outValue = 7;
            break;
         case 'I':
            outValue = 8;
            break;
         case 'J':
            outValue = 9;
            break;
         case 'K':
            outValue = 10;
            break;
         case 'L':
            outValue = 11;
            break;
         case 'M':
            outValue = 12;
            break;
         case 'N':
            outValue = 13;
            break;
         case 'O':
            outValue = 14;
            break;
         case 'P':
            outValue = 15;
            break;
         case 'Q':
            outValue = 16;
            break;
         case 'R':
            outValue = 17;
            break;
         case 'S':
            outValue = 18;
            break;
         case 'T':
            outValue = 19;
            break;
         case 'U':
            outValue = 20;
            break;
         case 'V':
            outValue = 21;
            break;
         case 'W':
            outValue = 22;
            break;
         case 'X':
            outValue = 23;
            break;
         case 'Y':
            outValue = 24;
            break;
         case 'Z':
            outValue = 25;
            break;
         default:
            throw new IllegalArgumentException( "Vlaue not mapped in this alphabet" );
      }
      return outValue;
   }

   public static char convertValueToAlphabet ( int inValue )
   {
      char outValue = ' ';

      switch ( inValue )
      {
         case 0:
            outValue = 'A';
            break;
         case 1:
            outValue = 'B';
            break;
         case 2:
            outValue = 'C';
            break;
         case 3:
            outValue = 'D';
            break;
         case 4:
            outValue = 'E';
            break;
         case 5:
            outValue = 'F';
            break;
         case 6:
            outValue = 'G';
            break;
         case 7:
            outValue = 'H';
            break;
         case 8:
            outValue = 'I';
            break;
         case 9:
            outValue = 'J';
            break;
         case 10:
            outValue = 'K';
            break;
         case 11:
            outValue = 'L';
            break;
         case 12:
            outValue = 'M';
            break;
         case 13:
            outValue = 'N';
            break;
         case 14:
            outValue = 'O';
            break;
         case 15:
            outValue = 'P';
            break;
         case 16:
            outValue = 'Q';
            break;
         case 17:
            outValue = 'R';
            break;
         case 18:
            outValue = 'S';
            break;
         case 19:
            outValue = 'T';
            break;
         case 20:
            outValue = 'U';
            break;
         case 21:
            outValue = 'V';
            break;
         case 22:
            outValue = 'W';
            break;
         case 23:
            outValue = 'X';
            break;
         case 24:
            outValue = 'Y';
            break;
         case 25:
            outValue = 'Z';
            break;
         default:
            throw new IllegalArgumentException( "Vlaue not mapped in this alphabet" );
      }
      return outValue;
   }

}
