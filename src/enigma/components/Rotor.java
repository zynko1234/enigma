package enigma.components;

import java.beans.XMLEncoder;
import java.util.Arrays;
import java.util.Random;

public class Rotor
{

   /**
    * 
    * @param inRotorMap
    * @param inLockedFlag
    * @param inTickRate
    * @param inInitialPosition
    */
   public Rotor ( final int[] inRotorMap, final boolean inLockedFlag, final int inTickRate,
         final int inInitialPosition )
   {
      Initialize( inRotorMap, inLockedFlag, inTickRate, inInitialPosition );
      return;
   }

   /**
    * 
    * @param inAlphabetSize
    */
   public Rotor ( final int inAlphabetSize )
   {
      Initialize( GenerateRotorMap( inAlphabetSize ), 
                  DEF_LOCK_FLAG, 
                  inAlphabetSize, 
                  DEF_INIT_POS );

      return;
   }

   /**
    * Copy constructor. Clones the internals of the given Rotor.
    * 
    * @param inRotor The {@link Rotor} to be copied.
    */
   public Rotor ( final Rotor inRotor )
   {
      Initialize( inRotor.theRotor.clone(), 
                  inRotor.theLockedFlag, 
                  inRotor.theTickRate,
                  inRotor.theInitialPosition );
      
      return;
   }

   ////////////////////////////////////////////////////////////////////////////
   // Public Member Methods
   ////////////////////////////////////////////////////////////////////////////

   /**
    * 
    * @param inValue
    * @param isMirrored
    * @return
    */
   public int PassValue ( final int inValue, final boolean isMirrored )
   {
      int output = -1;

      // IF: The given value is within the bounds of zero and the size of the
      // rotor then get the value.
      // ELSE: Thow an exception.
      if ( IsValidPos( inValue ) )
      {
         int turnOffset;

         if ( isMirrored == false )
         {
            turnOffset = ( ( inValue + theCurrentPosition ) % theRotor.length );
            output = theRotor[ turnOffset ];
         } else
         {
            output = theMirrorRotor[ inValue ];

            // Reverse modulus in case the rotor position evaluates negative
            // values. (e.g. mirrored offset value of 2 minus a position of
            // 5 will give the length of the alphabet - 3)
            output = ( theRotor.length + output - theCurrentPosition ) % theRotor.length;

         }

         // If this is coming from the simulated output side of the rotor, use
         // mirror of the rotor. Otherwise use the rotor as normal.

      } else
      {
         throw new IllegalArgumentException( "Value is outside the range of the rotor alphabet." );
      }
      return output;
   }

   /**
    * 
    * @return
    */
   public int GetRotorSize ()
   {
      return theRotor.length;
   }

   /**
    * Gets the current position of the rotor.
    * 
    * @return The value representing the position of the rotor.
    */
   public int GetPosition ()
   {
      return theCurrentPosition;
   }

   /**
    * Sets a new position for the rotor.
    * 
    * @param inPosition The new position value to set.
    */
   public void SetPosition ( final int inPosition )
   {
      if ( IsValidPos( inPosition ) )
      {
         theCurrentPosition = inPosition;
      } else
      {
         throw new IllegalArgumentException( "Value is outside the range of the rotor alphabet." );
      }
   }

   /**
    * Gets the tick rate of this rotor.
    * 
    * @return The value representing the tick rate of this rotor.
    */
   public int GetTickRate ()
   {
      return theTickRate;
   }

   /**
    * Sets a new tickrate for this rotor.
    * 
    * @param inTickRate The new tickrate to be set.
    */
   public void SetRotorTickRate ( final int inTickRate )
   {
      theTickRate = inTickRate;
   }

   /**
    * Increments the rotor position by one.
    * 
    * @throws IllegalStateException If the rotor is set as stationary then an
    *                               exception is thrown if this function is called.
    */
   public void TurnRotor ()
   {
      if ( !theLockedFlag )
      {
         // Increment the current position of the rotor.
         theCurrentPosition++;

         // Set the rotor position to the mod of the rotor size to zero it out if
         // it's gone over. (e.g. If the rotor size is 26 and the position goes
         // from 25 to 26 on the increment, set it to zero.)
         theCurrentPosition = theCurrentPosition % theRotor.length;
      } 
      else
      {
         throw new IllegalStateException( "Attempted to turn a stationary rotor." );
      }
   }

   /**
    * 
    * @return
    */
   public boolean TurnNext ()
   {
      return ( theCurrentPosition % theTickRate ) == 0;
   }
   
   /**
    * Resets the current position of the rotor to its initial position.
    */
   public void Reset()
   {
      theCurrentPosition = theInitialPosition;
      return;
   }

   @Override
   public String toString ()
   {
      final String toStringFormat = "%d%n%d%n%s%n";
      String output = String.format( toStringFormat, theTickRate, theCurrentPosition, Arrays.toString( theRotor ) );

      return output;
   }

   /**
    * 
    * @param inValue
    * @return
    */
   public boolean IsValidPos ( final int inValue )
   {
      // Return if the value is within the range of zero and the size of the
      // rotor;
      return ( inValue < theRotor.length ) && ( inValue >= 0 );
   }

   ////////////////////////////////////////////////////////////////////////////
   // Public Static Methods
   ////////////////////////////////////////////////////////////////////////////

   /**
    * 
    * @param alphabetSize
    * @return
    */
   public static int[] GenerateRotorMap ( final int alphabetSize )
   {
      int[] outmap = new int[ alphabetSize ];

      // Flag denoting when the last element value will be equal to it's
      // destination index, and cannot be switched out.
      boolean lastElementEdgeCase = false;

      // Flag denoting that the generated map has been fully tested for
      // validity.
      boolean validMap = false;

      // Seed a random number generator.
      Random tempRand = new Random( System.nanoTime() );

      int[] swapMap = new int[ alphabetSize ];

      // Populate this map from 0 to N
      for ( int i = 0; i < swapMap.length; i++ )
      {
         swapMap[ i ] = i;
      }

      // Keep generating until a valid map is created.
      while ( lastElementEdgeCase || !validMap )
      {
         lastElementEdgeCase = false;

         for ( int i = 0, swapIndex, swapTail; i < outmap.length; i++ )
         {
            swapIndex = tempRand.nextInt( swapMap.length - i );
            swapTail = ( swapMap.length - 1 ) - i;

            // IF: The value to be swapped out of the swap map is not equal to
            // the index of the output map, then assign it and swap it will the
            // tail index of the swap map.
            // ELSE IF: The value and the index will be equal, and this is the
            // final value, set the edge case flag and regenerate the rotor map.
            // ELSE: The value of the index will be equal, and this is not the
            // final value, set the loop iterator back one and try again.
            if ( swapMap[ swapIndex ] != i )
            {
               outmap[ i ] = swapMap[ swapIndex ];
               swapMap[ swapIndex ] = swapMap[ swapTail ];
               swapMap[ swapTail ] = outmap[ i ];
            } else if ( swapTail == 0 )
            {
               lastElementEdgeCase = true;
            } else
            {
               i--;
            }
         }
         // Don't bother checking for a fully valid map if the known edge case
         // was found.
         if ( !lastElementEdgeCase )
         {
            validMap = IsValidRotor( outmap );
         }
      }

      return outmap;
   }

   /**
    * 
    * @param inRotorMap
    * @return
    */
   public static boolean IsValidRotor ( final int[] inRotorMap )
   {
      boolean validity = true;

      // Used to map all possible values from 0 to N of the candidate rotor map.
      boolean[] valueCheckList = new boolean[ inRotorMap.length ];
      Arrays.fill( valueCheckList, Boolean.FALSE );

      // Check off every mapped value. Set the index equal rotor map value to
      // true when found. Also, make sure that no value is mapped to an index
      // equal to itself. (e.g. the value 3 is not stored at index 3)
      for ( int i = 0; i < inRotorMap.length; i++ )
      {
         valueCheckList[ inRotorMap[ i ] ] = true;
         validity = validity & ( inRotorMap[ i ] != i );

         // IF: A failure is found then break out of the loop.
         if ( !validity )
         {
            break;
         }
      }

      // IF: The previous test passed, run the next test for validity.
      if ( validity )
      {
         // Bitwise AND against the checklist. If there are unmapped or
         // duplicate values then validity will be set to false.
         for ( int i = 0; i < valueCheckList.length; i++ )
         {
            // Check off every mapped value.
            validity = validity & valueCheckList[ i ];

            // IF: A failure is found then break out of the loop.
            if ( !validity )
            {
               break;
            }
         }
      }

      return validity;
   }

   ////////////////////////////////////////////////////////////////////////////
   // Private Static Data
   ////////////////////////////////////////////////////////////////////////////

   /**
    * The initial position to use if nothing is given.
    */
   private static int DEF_INIT_POS = 0;

   /**
    * The default lock state of the rotor to set to if nothing is given.
    */
   private static boolean DEF_LOCK_FLAG = false;

   ////////////////////////////////////////////////////////////////////////////
   // Private Member Methods
   ////////////////////////////////////////////////////////////////////////////

   /**
    * 
    * @param inRotorMap
    * @param inLockedFlag
    * @param inTickRate
    * @param inInitialPosition
    */
   private void Initialize ( final int[] inRotorMap, 
                             final boolean inLockedFlag, 
                             final int inTickRate,
                             final int inInitialPosition )
   {
      if ( IsValidRotor( inRotorMap ) == false )
      {
         throw new IllegalArgumentException( "Improper rotor map configuration." );
      }

      theRotor = inRotorMap.clone();

      if ( IsValidPos( inInitialPosition ) == false )
      {
         throw new IllegalArgumentException( "Bad starting position." );
      }

      if ( inTickRate <= 0 )
      {
         throw new IllegalArgumentException( "Tick rate must be greater than zero." );
      }

      theLockedFlag = inLockedFlag;
      theCurrentPosition = inInitialPosition;

      // Generate the mirror rotor. The value of the rotor acts as the offset of
      // the mirror, and the offset it was stored at in the rotor acts as the
      // value.
      theMirrorRotor = new int[ inRotorMap.length ];
      
      for ( int i = 0; i < theRotor.length; i++ )
      {
         theMirrorRotor[ theRotor[ i ] ] = i;
      }

      return;
   }

   ////////////////////////////////////////////////////////////////////////////
   // Private Member Data
   ////////////////////////////////////////////////////////////////////////////

   /**
    * Flag that denotes if this is a preventing from turning or not.
    */
   private boolean theLockedFlag;

   /**
    * Current turn position of the rotor.
    */
   private int theCurrentPosition;

   /**
    * The rotor cipher array. Simulates a mapping of values for the enigma rotor.
    */
   private int[] theRotor;

   /**
    * The position of the rotor to start and reset to.
    */
   private int theInitialPosition;

   /**
    * Represents the number of turns this rotor will turn, before turning its
    * neighbor.
    */
   private int theTickRate;

   /**
    * A mirror of the rotor map. Simulates sending a value into the output side of
    * the rotor. Maps the value of the rotors as offsets, and the offsets as
    * values.
    */
   private int[] theMirrorRotor;
}
