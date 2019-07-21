package enigma.components;

import java.util.Arrays;
import java.util.Random;

public class ClockRotor implements Rotor
{   
   ////////////////////////////////////////////////////////////////////////////
   // Public Constants
   ////////////////////////////////////////////////////////////////////////////
   
   /**
    * Value map of the reflecting rotor of the Swiss-K model of the enigma
    * machine.
    */
    public static final int[] SWISS_K_REF = { 8, 12, 4, 19, 2, 6, 5, 17, 0, 24, 
                                              18, 16, 1, 25, 23, 22, 11, 7, 10, 
                                              3, 21, 20, 15, 14, 9, 13 };
    
   /**
    * Value map of the stationary front rotor of the Swiss-K model of the enigma
    * machine.
    */
   public static final int[] SWISS_K_STAT  = { 16, 22, 4, 17, 19, 25, 20, 8, 14,
                                               0, 18, 3, 5, 6, 7, 9, 10, 15, 24, 
                                               23, 2, 21, 1, 13, 12, 11 };
   
   /**
   * Value map of rotor one of the Swiss-K model of the enigma machine.
   */
   public static final int[] SWISS_K_I = { 15, 4, 25, 20, 14, 7, 23, 18, 2, 21,
                                           5, 12, 19, 1, 6, 11, 17, 8, 13, 16, 
                                           9, 22, 0, 24, 3, 10 };
   
   /**
   * Value map of rotor two of the Swiss-K model of the enigma machine.
   */
   public static final int[] SWISS_K_II = { 25, 14, 20, 4, 18, 24, 3, 10, 5, 22,
                                            15, 2, 8, 16, 23, 7, 12, 21, 1, 11, 
                                            6, 13, 9, 17, 0, 19 };  

   /**
   * Value map of rotor three of the Swiss-K model of the enigma machine.
   */
   public static final int[] SWISS_K_III = { 4, 7, 17, 21, 23, 6, 0, 14, 1, 16, 
                                             20, 18, 8, 12, 25, 5, 11, 24, 13, 
                                             22, 10, 19, 15, 3, 9, 2 }; 
   
   
   
   ////////////////////////////////////////////////////////////////////////////
   // Exception Strings
   ////////////////////////////////////////////////////////////////////////////
   
   /**
    * 
    */
   protected final String EXC_INVAL_MAP = "Invalid rotor map. Map cannot "
                                        + "contain duplicate or self connected "
                                        + "values.";

   ////////////////////////////////////////////////////////////////////////////
   // Protected Member Data
   ////////////////////////////////////////////////////////////////////////////
   
   /**
    * 
    */
   protected RotorNode[] theNodeMap;
   
   /**
    * 
    */
   private int theSize;
   
   /**
    * 
    */
   private int theTurnRate;
   
   /**
    * 
    */
   private int theCurrPosition;
   
   /*
    *  
    */
   private ClockRotor theNextRotor;
   
   ////////////////////////////////////////////////////////////////////////////
   // Constructors
   ////////////////////////////////////////////////////////////////////////////
   
   public ClockRotor( final int[] inRotorMap, final int inTurnRate )
   {
      if(isValidRotor(inRotorMap)) 
      {
         theNodeMap = new RotorNode[inRotorMap.length];
         
         // Initialize the map. Each node should wire to a value, the index
         // represented by that value should map back to the index that maps to
         // it.
         for( int i = 0; i < theNodeMap.length; i++ )
         {
            // Create the nodes to be wired from and to if either of them
            // haven't been created in a previous wiring.
            if(theNodeMap[i] == null)
            {
               theNodeMap[i] = new RotorNode();
            }
            if(theNodeMap[inRotorMap[i]] == null)
            {
               theNodeMap[inRotorMap[i]] = new RotorNode();
            }
         
            // Map to an index of value this node is wiring to, as well as that
            // node wiring back to the index it is wired from.
            theNodeMap[i].setWiredTo(inRotorMap[i]);
            theNodeMap[inRotorMap[i]].setWiredFrom(i);
         }
         // Initialize the turning mechanism.
         theTurnRate = inTurnRate;
      }
      else
      {
         throw new IllegalArgumentException(EXC_INVAL_MAP);
      }
      return;
   }
   
   ////////////////////////////////////////////////////////////////////////////
   // Public Member Methods
   ////////////////////////////////////////////////////////////////////////////
   
   public void insert( final ClockRotor inRotor )
   {
      // Set the next rotor in the chain if there isn't one, otherwise pass it
      // down the chain until it hits the end.
      if( theNextRotor == null )
      {
         theNextRotor = inRotor;
      }
      else
      {
         theNextRotor.insert( inRotor );
      }
      
      return;
   }
   
   /** 
    * Moves the current position of the rotor up one, and checks if the next rotor should be turned.
    */
   public void turn()
   {
      // Increment the position and rectify any overflow.
      theCurrPosition++;
      theCurrPosition = theCurrPosition % theNodeMap.length;
      
      // If there's a next rotor to turn and the new current position is
      // divisible by the turn rate the turn the next rotor.
      if( isDiv( theCurrPosition, theTurnRate ) && ( theNextRotor != null ) )
      {
         theNextRotor.turn();
      }
      return;
   }
   
   public int getSize()
   {
      return theNodeMap.length;
   }
   
   public int cipher(final int inValue)
   {  
      int effOffset = (theCurrPosition + inValue) % theNodeMap.length;
      int outValue = theNodeMap[effOffset].getWiredTo();
      
      if(theNextRotor != null)
      {
         outValue = theNextRotor.cipher( outValue );
      }
      
      return outValue;
   }
   
   public int mirrorCipher( final int inValue )
   {
      int outValue = inValue;
      int effOffset;
      
      if(theNextRotor != null)
      {
         outValue = theNextRotor.mirrorCipher(inValue);
      }
      
      outValue = theNodeMap[outValue].getWiredFrom();
      outValue = ((theNodeMap.length + outValue) - theCurrPosition ) % theNodeMap.length;
      return outValue;
   }
   
   public void setPosition(final int inPosition)
   {
      theCurrPosition = inPosition;
      return;
   }
   
   ////////////////////////////////////////////////////////////////////////////
   // Protected Member Methods
   ////////////////////////////////////////////////////////////////////////////
   
   /**
    * Macro function for checking for divisibility.
    * @param inNum The number that is being divided.
    * @param inDiv
    * @return
    */
   protected boolean isDiv(final int inNum, final int inDiv)
   {
      return ((inNum % inDiv) == 0);
   }
   
   ////////////////////////////////////////////////////////////////////////////
   // Public Static Methods
   ////////////////////////////////////////////////////////////////////////////
   
   public static boolean isValidRotor ( final int[] inRotorMap )
   {
      boolean validity = true;

      // Used to map all possible values from 0 to N of the candidate rotor map.
      boolean[] valueCheckList = new boolean[ inRotorMap.length ];
      Arrays.fill( valueCheckList, Boolean.FALSE );

      // Check off every mapped value. Set the index equal rotor map value to
      // true when found.
      for ( int i = 0; i < inRotorMap.length; i++ )
      {
         valueCheckList[ inRotorMap[ i ] ] = true;
         
         // Check that the current value does not map to itself.
         validity = validity & ( inRotorMap[i] != i);
         
         // If a failure is found then break out of the loop.
         if ( !validity )
         {
            break;
         }
      }

      // If the previous test passed, run the next test for validity.
      if ( validity )
      {
         // Bitwise AND against the checklist. If there are unmapped or
         // duplicate values then validity will be set to false.
         for ( int i = 0; i < valueCheckList.length; i++ )
         {
            // Check off every mapped value.
            validity = validity & valueCheckList[ i ];
            
            // If failure is found then break out of the loop.
            if ( !validity )
            {
               break;
            }
         }
      }

      return validity;
   }
   
   public static ClockRotor generateRotor(final int inAlphabetSize, final long inMapSeed)
   {      
      int[] outMap = new int[ inAlphabetSize ];
      
      // Flag denoting that the generated map has been fully tested for
      // validity.
      boolean validMap = false;

      // Seed a random number generator.
      Random tempRand = new Random( inMapSeed );
      
      // Make sure the tick rate grater than 1, and less than the alphabet size inclusively.
      int outTickRate = tempRand.nextInt(inAlphabetSize + 1);
      
      int[] swapMap = new int[ inAlphabetSize ];

      // Populate this map from 0 to N
      for ( int i = 0; i < swapMap.length; i++ )
      {
         swapMap[ i ] = i;
      }

      // Keep generating until a valid map is created.
      while ( !validMap )
      {
         for ( int i = 0, swapIndex = 0, swapTail = 0; i < outMap.length; i++ )
         {
            // Pick a random index of the remaining values.
            swapIndex = tempRand.nextInt( swapMap.length - i );

            // Swap it with the last accessible element of the list.
            swapTail = ( swapMap.length - 1 ) - i;

            // Copy the random value to be swapped to the output array.
            outMap[ i ] = swapMap[ swapIndex ];

            // Swap the copied value to the accessible end of the array.
            swapMap[ swapIndex ] = swapMap[ swapTail ];
            swapMap[ swapTail ] = outMap[ i ];

         }
         // Check the final validity of the map.
         validMap = ClockRotor.isValidRotor( outMap );
      } 
      return new ClockRotor( outMap, tempRand.nextInt( outTickRate ) );
   }
}
