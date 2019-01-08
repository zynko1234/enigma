package application.test;

import enigma.EnigmaMachine;
import enigma.components.PlugBoard;
import enigma.components.Rotor;

public class EnigmaTester
{
   public static PlugBoard SetupPlugboard ()
   {
      PlugBoard outBoard = new PlugBoard( 26 );
      return outBoard;
   }

//   public static Rotor[] SetupRotors()
//   {
//      Rotor[] outputRotors = new Rotor[5];
//      
//      // Setup non-stationary rotors.
//      outputRotors[0] = new Rotor( EnigmaMachine.SWISS_K_I, false, 25 );
//      outputRotors[1] = new Rotor( EnigmaMachine.SWISS_K_II, false, 25);
//      outputRotors[2] = new Rotor( EnigmaMachine.SWISS_K_III, false, 25);
//      
//      // Setup stationary rotors.
//      outputRotors[3] = new Rotor( EnigmaMachine.SWISS_K_STAT, true, 25);
//      outputRotors[4] = new Rotor( EnigmaMachine.SWISS_K_REF, true, 25);
//      
//      return outputRotors;
//   }

   public static void TestPlugBoard ()
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

//   public static void TestMachine()
//   {
//      Rotor[] rotors = SetupRotors();
//      PlugBoard plugBoard = SetupPlugboard();
//
//      EnigmaMachine theEnigmaMachine = new EnigmaMachine( rotors[ 0 ], rotors[ 1 ], rotors[ 2 ], rotors[ 3 ], rotors[ 4 ], plugBoard );
//
//      String testInput = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
//      String testOutput = "";
//
//      System.out.println( "Test Input: " + testInput );
//      int[] testInputValues = ConvertAlphabetString( testInput );
//      int[] outValues = theEnigmaMachine.CipherValues( testInputValues );
//
//      System.out.println( "Test Output: " + ConvertValueString( outValues ) );
//
//      rotors = SetupRotors();
//      plugBoard = SetupPlugboard();
//
//      // Reset the enigma machine.
//      theEnigmaMachine = new EnigmaMachine( rotors[ 0 ], rotors[ 1 ], rotors[ 2 ], rotors[ 3 ], rotors[ 4 ], plugBoard );
//
//      outValues = theEnigmaMachine.CipherValues( outValues );
//      testOutput = ConvertValueString( outValues );
//
//      System.out.println( "Test Output Re-Entered: " + testOutput );
//   }

   public static void TestRotors ()
   {
//      System.out.println( "---RUN ROTOR TEST---" );
//      Rotor testRotor = new Rotor( rotorMap, false);
//      
//      // 0 representing A will be pushed through the rotor over and over again.
//      int repeatValue = 0;
//      
//      for(int i = 0; i < testRotor.GetRotorSize(); i++ )
//      {
//         System.out.println( "Value converted: " + 0 + " --> " + testRotor.PassValue( repeatValue, false ));
//
//         testRotor.TurnRotor();
//      }
//      System.out.println( "Mirroring Rotor With Repeating Input" );
//      for(int i = 0; i < testRotor.GetRotorSize(); i++ )
//      {
//         System.out.println( "Value converted: " + 0 + " --> " + testRotor.PassValue( repeatValue, true ));
//
//         testRotor.TurnRotor();
//      }
//      
//      System.out.println( "Resetting Position" );
//      testRotor.SetPosition( 0 );
//      int[] testRotorOutput = new int[testRotor.GetRotorSize()];
//      
//      for(int i = 0; i < testRotor.GetRotorSize(); i++ )
//      {
//         testRotorOutput[i] = testRotor.PassValue( repeatValue, false );
//         System.out.println( "Value converted: " + 0 + " --> " + testRotorOutput[i]);
//
//         testRotor.TurnRotor();
//      }
//      
//      System.out.println( "Mirroring Rotor With First Output" );
//      testRotor.SetPosition( 0 );
//      
//      for(int i = 0; i < testRotor.GetRotorSize(); i++ )
//      {
//         System.out.println( "Value converted: " + testRotorOutput[i] + " --> " + testRotor.PassValue( testRotorOutput[i], true ));
//
//         testRotor.TurnRotor();
//      }
//      
//      
//      System.out.println( "---END ROTOR TEST---" );
//      return;
//   }
//   
//   private static int[] rotorMap =
//   { 15, 4, 25, 20, 14, 7, 23, 18, 2, 21, 5, 12, 19, 1, 6, 11, 17, 8, 13, 16, 9, 22, 0, 24, 3, 10 };
   }
}
