package tools;

import java.util.Random;

import enigma.components.ClockRotor;
import enigma.components.Rotor;

public class RotorGenerator
{
   /**
    * Size of the complete ASCII character set.
    */
   private static int ASCII_SIZE = 256;

   /**
    * Size of the complete Unicode character set.
    */
   private static int UNICODE_SIZE = 1112064;

   /**
    * Denotes what character set is being referenced in rotor generation.
    */
   enum CHARENC
   {
      ASCII, UNICODE
   }

   private static ClockRotor[] generateRotorConfigurations ( int inRotorCount, CHARENC inEncoding, int inTickRange )
   {
      ClockRotor[] outputRotors = new ClockRotor[ inRotorCount ];

      int alphabetSize;

      switch ( inEncoding )
      {
         case ASCII:
            alphabetSize = ASCII_SIZE;
            break;
         case UNICODE:
            alphabetSize = UNICODE_SIZE;
            break;
         default:
            break;
      }

      for ( int i = 0; i < outputRotors.length; i++ )
      {
//         outputRotors[ i ] = new ClockRotor( ClockRotor.generateRotorMap( inAlphabetSize ), false,
//               new Random( System.nanoTime() ).nextInt( inTickRange + 1 ),
//               new Random( System.nanoTime() ).nextInt( inAlphabetSize ) );
      }

      return outputRotors;
   }

   /**
    * Generates a random rotor map o
    * 
    * @param alphabetSize
    * @return
    */
   public static int[] generateRotorMap ( final int alphabetSize )
   {
      int[] outmap = new int[ alphabetSize ];

      // Flag denoting that the generated map has been fully tested for
      // validity.
      boolean validMap = false;

      // Seed a random number generator.
      Random tempRand = new Random( System.nanoTime() );

      int[] swapMap = new int[ alphabetSize ];

      // Populate this map from 0 to N
      for ( int i = 0; i < swapMap.length; i++ )
      {
         swapMap[ i ] = i;
      }

      // Keep generating until a valid map is created.
      while ( !validMap )
      {
         for ( int i = 0, swapIndex = 0, swapTail = 0; i < outmap.length; i++ )
         {
            // Pick a random index of the remaining values.
            swapIndex = tempRand.nextInt( swapMap.length - i );

            // Swap it with the last accessible element of the list.
            swapTail = ( swapMap.length - 1 ) - i;

            // Copy the random value to be swapped to the output array.
            outmap[ i ] = swapMap[ swapIndex ];

            // Swap the copied value to the accessible end of the array.
            swapMap[ swapIndex ] = swapMap[ swapTail ];
            swapMap[ swapTail ] = outmap[ i ];

         }
         // Check the final validity of the map.
         validMap = ClockRotor.isValidRotor( outmap );
      }

      return outmap;
   }

   public static char[] generateUnicodeAlphabet ( int alphabetSize )
   {
      char[] output = new char[ alphabetSize ];

      for ( int i = 0; i < output.length; i++ )
      {
         output[ i ] = ( char ) i;
      }
      return output;
   }

   private static void write ( String outpath )
   {
//      BufferedWriter outputWriter = null;
//      outputWriter = new BufferedWriter(new FileWriter(filename));
//      for (int i = 0; i < x.length; i++) {
//        // Maybe:
//        outputWriter.write(x[i]+"");
//        // Or:
//        outputWriter.write(Integer.toString(x[i]);
//        outputWriter.newLine();
//      }
//      outputWriter.flush();  
//      outputWriter.close();  
   }
}
