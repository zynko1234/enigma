package application;

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
      
      System.out.println( "---END ROTOR TEST---" );
      return;
   }
}
