package enigma.machine;

import enigma.components.ClockRotor;
import enigma.components.ReflectingRotor;
import enigma.components.Rotor;

public class EnigmaMachine
{

   
   /**
   * Value map of the stationary rotor of the Swiss-K model of the enigma machine.
   */
   public static final int[] SWISS_K_REF = { 8, 12, 4, 19, 2, 6, 5, 17, 0, 24, 18, 16, 1, 25, 23, 22, 11, 7, 10, 3, 21, 20, 15, 14, 9, 13 };
                                        // { 8, 12, 4, 19, 2, 6, 5, 17, 0, 24, 18, 16, 1, 25, 23, 22, 11, 7, 10, 3, 21, 20, 15, 14, 9, 13 }
   
	/**
	* Value map of the stationary reflecting rotor of the Swiss-K model of the enigma machine.
	*/
   public static final int[] SWISS_K_STAT = { 16, 22, 4, 17, 19, 25, 20, 8, 14, 0, 18, 3, 5, 6, 7, 9, 10, 15, 24, 23, 2, 21, 1, 13, 12, 11 };
   
   
}
