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
      theStationaryRotor = inRotStat;
      theReflectingRotor = inRotRefl;
      thePlugBoard = inBoard;
      return;
   }
   
   public EnigmaMachine( int alphabetSize )
   {
      
   }
   
   ////////////////////////////////////////////////////////////////////////////
   // Public Member Methods
   ////////////////////////////////////////////////////////////////////////////
   
   /**
    * 
    * @param inVal
    * @return
    */
   public int CipherValue( final int inVal )
   {
      int output = inVal;
      
      Rotate();
      
      boolean isReflected = false;
      
      output = thePlugBoard.GetMap( output );
      
      // Pass the value through the simulated circut.
      output = theStationaryRotor.PassValue( output, isReflected );
      output = theFirstRotSlot.PassValue( output, isReflected );
      output = theSecondRotSlot.PassValue( output, isReflected );
      output = theThirdRotSlot.PassValue( output, isReflected );

      output = theReflectingRotor.PassValue( output, isReflected );
      isReflected = true;

      // From the reflector rotor,send the value back through the output side
      // of the rotors back into the plug board.
      output = theThirdRotSlot.PassValue( output, isReflected );
      output = theSecondRotSlot.PassValue( output, isReflected );
      output = theFirstRotSlot.PassValue( output, isReflected );
      output = theStationaryRotor.PassValue( output, isReflected );

      output = thePlugBoard.GetMap( output );

      
      // Go through the rotor list.
      for( int i = 0; i < theTurningRotors.length; i++ )
      {
         output = theTurningRotors[i].PassValue( output, isReflected );
      }
      
      // Go through the reflecting rotor, and set the mirrored flag to represent
      // the signal going back through the turning rotors in reverse.
      output = theReflectingRotor.PassValue( output, isReflected );
      isReflected = true;
      
      // Go back through the rotor list in reverse.
      for( int i = theTurningRotors.length - 1; i >= 0; i++ )
      {
         output = theTurningRotors[i].PassValue( output, isReflected );
      }
      
      return output;
   }
   
   /**
    * 
    * @param inVals
    * @return
    */
   public int[] CipherValues( int[] inVals )
   {
	   int[] output = new int[inVals.length];
	   
	   // Cipher each individual value.
	   for(int i = 0; i < inVals.length; i++)
	   {
		   output[i] = CipherValue( inVals[i] );
	   }
	   
	   return output;
   }
   
   ////////////////////////////////////////////////////////////////////////////
   // Private Member Methods
   ////////////////////////////////////////////////////////////////////////////
   
   private void Rotate()
   {
      // Rotate the first wheel.
      theFirstRotSlot.TurnRotor();
      
      // IF: The first rotor returns to the 0th position,turn the next rotor.
      if ( theFirstRotSlot.GetPosition() == 0 )
      {
         theSecondRotSlot.TurnRotor();

         // IF: The first rotor returns to the 0th position,turn the next rotor.
         if ( theSecondRotSlot.GetPosition() == 0 )
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
    * Simulated plug board that directly crosses connected inputs.
    */
   PlugBoard thePlugBoard;
   
   /**
    * The first non-stationary rotor to get initial input, and last to get reflected input.
    */
   Rotor theFirstRotSlot;
   
   /**
    * The second non-stationary rotor to get initial input, and second to get reflected input.
    */
   Rotor theSecondRotSlot;
   
   /**
    * The third non-stationay rotor to get initial input, and last to get reflected output.
    */
   Rotor theThirdRotSlot;
   
   /**
    * The stationary reflecting rotor.
    */
   Rotor theReflectingRotor;
   
   /**
    * 
    */
   Rotor[] theTurningRotors;
   
   /**
    * The stationary input rotor. It's the first rotor to take input after the
    * plugboard.
    */
   Rotor theStationaryRotor;
   
   /**
   * Value map of rotor one of the Swiss-K model of the enigma machine.
   */
   public static final int[] SWISS_K_I = { 15, 4, 25, 20, 14, 7, 23, 18, 2, 21, 5, 12, 19, 1, 6, 11, 17, 8, 13, 16, 9, 22, 0, 24, 3, 10 };
                                      // { 15, 4, 25, 20, 14, 7, 23, 18, 2, 21, 5, 12, 19, 1, 6, 11, 17, 8, 13, 16, 9, 22, 0, 24, 3, 10 }  

   
   /**
   * Value map of rotor two of the Swiss-K model of the enigma machine.
   */
   public static final int[] SWISS_K_II = { 25, 14, 20, 4, 18, 24, 3, 10, 5, 22, 15, 2, 8, 16, 23, 7, 12, 21, 1, 11, 6, 13, 9, 17, 0, 19 };  
                                       // { 25, 14, 20, 4, 18, 24, 3, 10, 5, 22, 15, 2, 8, 16, 23, 7, 12, 21, 1, 11, 6, 13, 9, 17, 0, 19 }  

   /**
   * Value map of rotor three of the Swiss-K model of the enigma machine.
   */
   public static final int[] SWISS_K_III = { 4, 7, 17, 21, 23, 6, 0, 14, 1, 16, 20, 18, 8, 12, 25, 5, 11, 24, 13, 22, 10, 19, 15, 3, 9, 2 };
                                        // { 4, 7, 17, 21, 23, 6, 0, 14, 1, 16, 20, 18, 8, 12, 25, 5, 11, 24, 13, 22, 10, 19, 15, 3, 9, 2 }  
   
   /**
   * Value map of the stationary rotor of the Swiss-K model of the enigma machine.
   */
   public static final int[] SWISS_K_REF = { 8, 12, 4, 19, 2, 6, 5, 17, 0, 24, 18, 16, 1, 25, 23, 22, 11, 7, 10, 3, 21, 20, 15, 14, 9, 13 };
                                        // { 8, 12, 4, 19, 2, 6, 5, 17, 0, 24, 18, 16, 1, 25, 23, 22, 11, 7, 10, 3, 21, 20, 15, 14, 9, 13 }
   
	/**
	* Value map of the stationary reflecting rotor of the Swiss-K model of the enigma machine.
	*/
   public static final int[] SWISS_K_STAT = { 16, 22, 4, 17, 19, 25, 20, 8, 14, 0, 18, 3, 5, 6, 7, 9, 10, 15, 24, 23, 2, 21, 1, 13, 12, 11 };
                                         // { 16, 22, 4, 17, 19, 25, 20, 8, 14, 0, 18, 3, 5, 6, 7, 9, 10, 15, 24, 23, 2, 21, 1, 13, 12, 11 }  
   
   
}
