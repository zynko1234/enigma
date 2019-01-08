package tools;

import java.util.Random;

import enigma.components.Rotor;

public class RotorGenerator
{
//   public Rotor[] GenerateEnigmaConfiguration( int inRotorCount, 
//                                               int inAlphabetSize, 
//                                               int inTickRange,
//                                               int plugConnections, 
//                                               String path)
//   {
//
//      
//      
//      return outputRotors;
//   }

   private static Rotor[] GenerateRotorConfigurations ( int inRotorCount, int inAlphabetSize, int inTickRange )
   {
      Rotor[] outputRotors = new Rotor[ inRotorCount ];

      for ( int i = 0; i < outputRotors.length; i++ )
      {
         outputRotors[ i ] = new Rotor( Rotor.GenerateRotorMap( inAlphabetSize ), false,
               new Random( System.nanoTime() ).nextInt( inTickRange + 1 ),
               new Random( System.nanoTime() ).nextInt( inAlphabetSize ) );
      }
      
      return outputRotors;
   }

   private static void Write ( String outpath )
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
