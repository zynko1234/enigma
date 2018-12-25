package application;

import enigma.components.Rotor;
import enigma.EnigmaMachine;
import enigma.components.PlugBoard;

public class Main
{

   public static void main ( String[] args )
   {
     Rotor[] rotors = SetupRotors();
     PlugBoard plugBoard = SetupPlugboard();
     
      EnigmaMachine theEnigmaMachine = new EnigmaMachine( rotors[0], 
                                               			  rotors[1], 
                                               			  rotors[2], 
                                               			  rotors[3], 
                                               			  rotors[4], 
                                               			  plugBoard );
      
      String testInput = "AAA";
      String testOutput = "";
      
      System.out.println( "Test Input: " + testInput );
      int[] testInputValues = ConvertAlphabetString( testInput );
      int[] outValues = theEnigmaMachine.CipherValues( testInputValues );
      
      System.out.println( "Test Output: " + ConvertValueString( outValues ) );
      
      rotors = SetupRotors();
      plugBoard = SetupPlugboard();
      
      //Reset the enigma machine.
      theEnigmaMachine = new EnigmaMachine( rotors[0], 
                            				rotors[1], 
                            				rotors[2], 
                            				rotors[3], 
                            				rotors[4],
                            				plugBoard );
      
      outValues = theEnigmaMachine.CipherValues( outValues );
      testOutput = ConvertValueString( outValues );
      
      System.out.println( "Test Output Re-Entered: " + testOutput );
      
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
         output = output + ConvertValueAlphabet(inVals[i]); 
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

   public static char ConvertValueAlphabet( int inValue )
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
      TestPlugBoard();
      TestRotors();
      return;
   }
   
   
   public static PlugBoard SetupPlugboard()
   {
      PlugBoard outBoard = new PlugBoard(26);
      return outBoard;
   }
   
   public static Rotor[] SetupRotors()
   {
      Rotor[] outputRotors = new Rotor[5];
      
      // Setup non-stationary rotors.
      outputRotors[0] = new Rotor( EnigmaMachine.SWISS_K_I, false );
      outputRotors[1] = new Rotor( EnigmaMachine.SWISS_K_II, false);
      outputRotors[2] = new Rotor( EnigmaMachine.SWISS_K_III, false);
      
      // Setup stationary rotors.
      outputRotors[3] = new Rotor( EnigmaMachine.SWISS_K_STAT, true);
      outputRotors[4] = new Rotor( EnigmaMachine.SWISS_K_REF, true);
      
      return outputRotors;
   }
   
   public static void TestPlugBoard()
   {
      PlugBoard testBoard = new PlugBoard();
      
      System.out.println( "---RUN PLUG BOARD TEST---" );
      
      // Should alternate every consecutive pair of values.
      testBoard.SetPlug( 0, 1, false );
      testBoard.SetPlug( 2, 3, false );
      testBoard.SetPlug( 4, 5, false );
      testBoard.SetPlug( 6, 7, false );
      testBoard.SetPlug( 8, 9, false );
      testBoard.SetPlug( 10, 11, false );
      testBoard.SetPlug( 12, 13, false );
      testBoard.SetPlug( 14, 15, false );
      testBoard.SetPlug( 16, 17, false );
      testBoard.SetPlug( 18, 19, false );
      testBoard.SetPlug( 20, 21, false );
      testBoard.SetPlug( 22, 23, false );
      testBoard.SetPlug( 24, 25, false );
      
      // Should do nothing.
      testBoard.SetPlug( 0, 25, false );
      
      // Should force 0 and 1 to unmap, 24 and 25 to unmap, and 0 and 25 to map.
      testBoard.SetPlug( 0, 25, true );
      
      // Should print each other.
      System.out.println( "Value mapped to 0: " + testBoard.GetMap( 0 ) );
      System.out.println( "Value mapped to 25: " + testBoard.GetMap( 25 ) );
      
      // Should print themselves.
      System.out.println( "Value mapped to 1: " + testBoard.GetMap( 1 ) );
      System.out.println( "Value mapped to 24: " + testBoard.GetMap( 24 ) );
      
      System.out.println( "---END PLUG BOARD TEST---" );
      
      return;
   }
   
   public static void TestRotors()
   {
      System.out.println( "---RUN ROTOR TEST---" );
      Rotor testRotor = new Rotor( rotorMap, false);
      
      // 0 representing A will be pushed through the rotor over and over again.
      int repeatValue = 0;
      
      for(int i = 0; i < testRotor.GetRotorSize() + 50; i++ )
      {
         System.out.println( "Value converted: " + 0 + " --> " + testRotor.PassValue( repeatValue, false ));

         testRotor.TurnRotor();
      }
      
      System.out.println( "---END ROTOR TEST---" );
      return;
   }
   
   private static int[] rotorMap = { 15, 4,  25, 20, 14, 7,  23, 18, 2,  21,
                                     5,  12, 19, 1,  6,  11, 17, 8,  13, 16, 
                                     9,  22, 0,  24, 3,  10 };
}
