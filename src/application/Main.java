package application;

import enigma.components.Rotor;
import enigma.components.PlugBoard;

public class Main
{

   public static void main ( String[] args )
   {
      RunTests();
      return;
   }
   
   public static void RunTests()
   {
      TestPlugBoard();
      TestRotors();
      return;
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
      
      // Should print eachother.
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
      Rotor testRotor = new Rotor( rotorMap, false, 0 );
      
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
