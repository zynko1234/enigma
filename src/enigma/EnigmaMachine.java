package enigma;

import enigma.components.PlugBoard;
import enigma.components.Rotor;

public class EnigmaMachine
{

   ////////////////////////////////////////////////////////////////////////////
   // Constructors
   ////////////////////////////////////////////////////////////////////////////
   
   public EnigmaMachine( Rotor inRotOne, 
                         Rotor inRotTwo,
                         Rotor inRotThree, 
                         Rotor inRotStat, 
                         Rotor inRotRefl,
                         PlugBoard inBoard)
   {
      theFirstRotSlot = inRotOne;
      theSecondRotSlot = inRotTwo;
      theThirdRotSlot = inRotThree;
      thePlugBoard = inBoard;
      return;
   }
   
   ////////////////////////////////////////////////////////////////////////////
   // Public Member Methods
   ////////////////////////////////////////////////////////////////////////////
   
   public int CipherValue( final int inVal )
   {
      int output = inVal;
      
      Rotate();
      
      boolean isReflected = false;
      
      thePlugBoard.GetMap( output );
      
      // Pass the value through the simulated circut.
      output = theStationaryRotor.PassValue( output, isReflected );
      output = theFirstRotSlot.PassValue( output, isReflected );
      output = theSecondRotSlot.PassValue( output, isReflected );
      output = theThirdRotSlot.PassValue( output, isReflected );
      
      output = theReflectingRotor.PassValue( output, isReflected );
      isReflected = true;
      
      // From the reflector rotor, send the value back through the output side
      // of the rotors back into the plug board.
      output = theThirdRotSlot.PassValue( output, isReflected );
      output = theSecondRotSlot.PassValue( output, isReflected );
      output = theFirstRotSlot.PassValue( output, isReflected );
      output = theStationaryRotor.PassValue( output, isReflected );
      
      thePlugBoard.GetMap( output );
      
      return output;
   }
   
   ////////////////////////////////////////////////////////////////////////////
   // Private Member Methods
   ////////////////////////////////////////////////////////////////////////////
   
   private void Rotate()
   {
      // Rotate the first wheel.
      theFirstRotSlot.TurnRotor();
      
      // IF: The first rotor returns to the 0th position, turn the next rotor.
      if ( theFirstRotSlot.GetRotorPosition() == 0 )
      {
         theSecondRotSlot.TurnRotor();

         // IF: The first rotor returns to the 0th position, turn the next rotor.
         if ( theSecondRotSlot.GetRotorPosition() == 0 )
         {
            theThirdRotSlot.TurnRotor();
         }
       }
         return;
   }
   
   ////////////////////////////////////////////////////////////////////////////
   // Private Member Data
   ////////////////////////////////////////////////////////////////////////////
   
   /**
    * 
    */
   PlugBoard thePlugBoard;
   
   /**
    * 
    */
   Rotor theFirstRotSlot;
   
   /**
    * 
    */
   Rotor theSecondRotSlot;
   
   /**
    * 
    */
   Rotor theThirdRotSlot;
   
   /**
    * 
    */
   Rotor theReflectingRotor;
   
   /**
    * 
    */
   Rotor theStationaryRotor;
   
   /**
   * Value map of rotor one of the Swiss-K model of the enigma machine.
   */
   public static final int[] SWISS_K_I = { 15, 4, 25, 20, 14, 7, 23, 18, 2, 21, 5, 12, 19, 1, 6, 11, 17, 8, 13, 16, 9, 22, 0, 24, 3, 10 };
   
   /**
   * Value map of rotor two of the Swiss-K model of the enigma machine.
   */
   public static final int[] SWISS_K_II = { 25, 14, 20, 4, 18, 24, 3, 10, 5, 22, 15, 2, 8, 16, 23, 7, 12, 21, 1, 11, 6, 13, 9, 17, 0, 19 };  

   /**
   * Value map of rotor three of the Swiss-K model of the enigma machine.
   */
   public static final int[] SWISS_K_III = { 4, 7, 17, 21, 23, 6, 0, 14, 1, 16, 20, 18, 8, 12, 25, 5, 11, 24, 13, 22, 10, 19, 15, 3, 9, 2 };

   /**
   * Value map of the stationary rotor of the Swiss-K model of the enigma machine.
   */
private static final int[][] ROT_REF = { { 0, 8 },  { 1, 12 }, { 2, 4 }, 
         { 3, 19 }, { 4, 2 },  { 5, 6 }, 
         { 6, 5 },  { 7, 17 }, { 8, 0 }, 
         { 9, 24 }, { 10, 18 }, { 11, 16 }, 
         { 12, 1 }, { 13, 25 }, { 14, 23 }, 
         { 15, 22 }, { 16, 11 }, { 17, 7 }, 
         { 18, 10 }, { 19, 3 }, { 20, 21 }, 
         { 21, 20 }, { 22, 15 }, { 23, 14 }, 
         { 24, 9 }, { 25, 13 } };

/**
* Value map of the stationary reflecting rotor of the Swiss-K model of the enigma machine.
*/
private static final int[][] ROT_STAT = { { 0, 16 }, { 1, 22 }, { 2, 4 }, 
          { 3, 17 }, { 4, 19 }, { 5, 25 }, 
          { 6, 20 }, { 7, 8 },  { 8, 14 }, 
          { 9, 0 },  { 10, 18 }, { 11, 3 }, 
          { 12, 5 }, { 13, 6 }, { 14, 7 }, 
          { 15, 9 }, { 16, 10 }, { 17, 15 }, 
          { 18, 24 }, { 19, 23 }, { 20, 2 }, 
          { 21, 21 }, { 22, 1 }, { 23, 13 }, 
          { 24, 12 }, { 25, 11 } };
   
}
