package application;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;


import enigma.components.ClockRotor;

public class Main
{

   public static void main ( String[] args )
   {

      
      return;
   }

   public static void testUTF8()
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
      return;
   }
   

}
