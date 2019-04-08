package enigma;

import enigma.components.ClockRotor;
import enigma.components.PlugBoard;
import enigma.components.ReflectingRotor;
import enigma.components.Rotor;

public class EnigmaMachine
{

   ////////////////////////////////////////////////////////////////////////////
   // Constructors
   ////////////////////////////////////////////////////////////////////////////
   
   public EnigmaMachine( Rotor inRotOne,
                         ReflectingRotor inRotRefl, 
                         PlugBoard inBoard)
   {
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
   public int cipherValue( final int inVal )
   {
      int output = inVal;
      
      // Activate the clock rotation of the rotors before ciphering a value.
      Rotate();
      
      boolean isReflected = false;
      
      output = thePlugBoard.GetMap( output );
      
      // Pass the value through the simulated circut.
      output = theStationaryRotor.cipher( output );

      for( int i = 0; i < theTurningRotors.length; i++ )
      {
         output = theTurningRotors[i].cipher( output );
      }
      
      output = theReflectingRotor.cipher( output );
      isReflected = true;

      // From the reflector rotor,send the value back through the output side
      // of the rotors back into the plug board.
      for( int i = ( theTurningRotors.length -1 ); i >= 0; i++ )
      {
         output = theTurningRotors[i].cipher( output);
      }
      
      output = theStationaryRotor.cipher( output );
      
      output = thePlugBoard.GetMap( output );
      
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
		   output[i] = cipherValue( inVals[i] );
	   }
	   
	   return output;
   }
   
   ////////////////////////////////////////////////////////////////////////////
   // Private Member Methods
   ////////////////////////////////////////////////////////////////////////////
   
   private void Rotate()
   {
      // Turn rotors that can be triggered from the first rotor turning.
      for( int i = 0; i < theTurningRotors.length; i++ ) 
      {
         theTurningRotors[i].turnRotor();
         
         // If turning this rotor will not trigger the next one to turn, 
         // then stop iterating. 
         if( theTurningRotors[i].turnNext() == false )
         {
            break;
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
    * The stationary reflecting rotor.
    */
   ReflectingRotor theReflectingRotor;
   
   /**
    * 
    */
   ClockRotor[] theTurningRotors;
   
   /**
    * The stationary input rotor. It's the first rotor to take input after the
    * plugboard.
    */
   ClockRotor theStationaryRotor;
   
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
