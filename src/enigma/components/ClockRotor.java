package enigma.components;

import java.util.Arrays;
import java.util.Random;

public class ClockRotor implements Rotor
{

   ////////////////////////////////////////////////////////////////////////////
   // Constructors
   ////////////////////////////////////////////////////////////////////////////

   /**
    * Initializing constructor.
    * 
    * @param inCircuitMap
    * @param inTickRate
    * @param inInitialPosition
    */
   public ClockRotor ( final int[] inCircuitMap, final int inTickRate, final int inInitialPosition )
   {
      initialize( inCircuitMap.clone(), inTickRate, inInitialPosition );
      return;
   }

   /**
    * Copy constructor. Deep clones the internals of the given Rotor.
    * 
    * @param inRotor The {@link ClockRotor} to be copied.
    */
   public ClockRotor ( final ClockRotor inRotor )
   {

      // Call the same initializing function as the initializing constructor.
      initialize( inRotor.theCircuitMap.clone(), inRotor.theTickRate, inRotor.theInitialPosition );

      // Copy and clone everything not in the initializing constructor. The
      // mirror rotor is not needed as that is generated under the hood in the
      // constructor.
      this.theCurrentPosition = inRotor.theCurrentPosition;
      this.theMirrorFlag = inRotor.theMirrorFlag;

      // Deep clone the next chained rotor, if it exists.
      if ( inRotor.theNextRotor != null )
      {
         this.theNextRotor = inRotor.theNextRotor.clone();
      }

      return;
   }

   ////////////////////////////////////////////////////////////////////////////
   // Public Member Methods
   ////////////////////////////////////////////////////////////////////////////

   /**
    * Ciphers a value through this rotor. If the value given was ciphered in this
    * position then it returns the original value.
    * 
    * @param inValue    The value to be ciphered.
    * @param isMirrored
    * @return
    */
   public int cipher ( final int inValue )
   {
      int output = -1;

      // IF: The given value is within the bounds of zero and the size of the
      // rotor then get the value.
      // ELSE: Throw an exception.
      if ( isValidPos( inValue ) )
      {
         int turnOffset;

         if ( theMirrorFlag == false )
         {
            turnOffset = ( ( inValue + theCurrentPosition ) % theCircuitMap.length );
            output = theCircuitMap[ turnOffset ];
         } else
         {
            output = theMirrorCircuitMap[ inValue ];

            // Reverse modulus in case the rotor position evaluates negative
            // values. (e.g. mirrored offset value of 2 minus a position of
            // 5 will give the length of the alphabet - 3)
            output = ( theCircuitMap.length + output - theCurrentPosition ) % theCircuitMap.length;
         }
      } else
      {
         throw new IllegalArgumentException( "Value is outside the range of the rotor alphabet." );
      }

      // IF: There is a next rotor in the circut, pass the value to it.
      if ( theNextRotor != null )
      {
         theNextRotor.cipher( output );
      }

      return output;
   }

   /**
    * Gets the current position of the rotor.
    * 
    * @return The value representing the position of the rotor.
    */
   public int getPosition ()
   {
      return theCurrentPosition;
   }

   /**
    * Sets the operating state of the rotor to mirrored mode, where the values and
    * their ciphers invert. (as opposed to
    * {@link enigma.components.ClockRotor#setStandardMode()})
    */
   public void setMirroredMode ()
   {
      theMirrorFlag = true;
   }

   /**
    * Sets the operating state of the rotor to standard mode. (as opposed to
    * {@link enigma.components.ClockRotor#setMirroredMode()} where the values and
    * their ciphers invert)
    */
   public void setStandardMode ()
   {
      theMirrorFlag = false;
   }

   /**
    * Gets the current state of the mirror flag.
    * 
    * @return True if in mirrored mode, false otherwise.
    */
   public boolean getMirroredFlag ()
   {
      return theMirrorFlag;
   }

   /**
    * Sets a new position for the rotor.
    * 
    * @param inPosition The new position value to set.
    */
   public void setPosition ( final int inPosition )
   {
      if ( isValidPos( inPosition ) )
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
   public int getTickRate ()
   {
      return theTickRate;
   }

   /**
    * Sets a new tickrate for this rotor.
    * 
    * @param inTickRate The new tickrate to be set.
    */
   public void setRotorTickRate ( final int inTickRate )
   {
      theTickRate = inTickRate;
   }

   /**
    * Increments the rotor position by one.
    */
   public void turnRotor ()
   {
      // Increment the current position of the rotor then set the rotor
      // position to the mod of the rotor size to zero it out if it's gone
      // over. (e.g. If the rotor size is 26 and the position goes from 25 to
      // 26 on the increment, set it to zero.)
      theCurrentPosition++;
      theCurrentPosition = theCurrentPosition % theCircuitMap.length;

      // IF: The tick rate has been achieved, and there is a chained rotor
      // attached, turn it.
      if ( turnNext() && ( theNextRotor != null ) )
      {
         theNextRotor.turnRotor();
      }

      return;
   }

   /**
    * Checks to see if this rotor has turned enough to turn the next rotor.
    * @return
    */
   public boolean turnNext ()
   {
      return ( theCurrentPosition % theTickRate ) == 0;
   }

   /**
    * Resets the current position of the rotor to its initial position.
    */
   public void reset ()
   {
      theCurrentPosition = theInitialPosition;
      return;
   }

   /**
    * Adds the given rotor to the chain of rotors, allowing it to turn with other
    * connected rotors.
    * 
    * @param inRotor The rotor to be added to the chain of rotors.
    */
   public void connectRotor ( ClockRotor inRotor )
   {
      // IF: The next rotor points to a ClockRotor instance then pass the
      // parameter on to that rotor, and so on until it reaches the last rotor's
      // terminating null reference.
      // ELSE: Clone the rotor contents into the next rotor.
      if ( theNextRotor != null )
      {
         theNextRotor.connectRotor( inRotor );
      } else
      {
         theNextRotor = inRotor.clone();
      }
      return;
   }

   /**
    * Provides a string representation of the rotor.
    * 
    * @return The string representation of the rotor.
    */
   public String toString ()
   {
      final String toStringFormat = "%d%n%d%n%s%n";

      String output = String.format( toStringFormat, theTickRate, theCurrentPosition,
            Arrays.toString( theCircuitMap ) );
      return output;
   }

   /**
    * Checks to see if the given value is a valid position in the rotor.
    * Essentially saying that the value is between 0 and the size of the alphabet
    * minus one.
    * 
    * @param inValue The value being checked for validity as a rotor position.
    * @return True if this position is valid, false otherwise.
    */
   public boolean isValidPos ( final int inValue )
   {
      // Return if the value is within the range of zero and the size of the
      // rotor;
      return ( inValue < theCircuitMap.length ) && ( inValue >= 0 );
   }

   /**
    * Gets the size of the alphabet (i.e. the size of the rotor).
    * 
    * @return The size of the alphabet.
    */
   public int getAlphabetSize ()
   {
      return theCircuitMap.length;
   }

   /**
    * Returns a deep clone of the {@link ClockRotor} instance.
    */
   public ClockRotor clone ()
   {
      // Call the copy constructor.
      return new ClockRotor( this );
   }

   ////////////////////////////////////////////////////////////////////////////
   // Public Static Methods
   ////////////////////////////////////////////////////////////////////////////

   /**
    * Checks that the rotor map is functionally valid.
    * 
    * @param inRotorMap The rotor map to be checked.
    * @return True if the input values can act as a valid map, false otherwise.
    */
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
    * @param inCircuitMap
    * @param inLockedFlag
    * @param inTickRate
    * @param inInitialPosition
    */
   private void initialize ( final int[] inCircuitMap, final int inTickRate, final int inInitialPosition )
   {
      // IF: The rotor is functionally correct in its configuration then assign the
      // given map.
      // ELSE: Throw an exception.
      if ( isValidRotor( inCircuitMap ) )
      {
         theCircuitMap = inCircuitMap;
      } else
      {
         throw new IllegalArgumentException( "Improper rotor map configuration." );
      }

      // IF: The tick rate is greater than zero, assign it.
      // ELSE: Throw an exception.
      if ( inTickRate >= 0 )
      {
         theTickRate = inTickRate;
      } else
      {
         throw new IllegalArgumentException( "Tick rate must be greater than zero." );
      }

      // IF: The rotor position falls within the bounds of the size of the rotor,
      // assign it.
      // ELSE: Throw an exception.
      if ( isValidPos( inInitialPosition ) )
      {
         theInitialPosition = inInitialPosition;
      } else
      {
         throw new IllegalArgumentException( "Bad starting position." );
      }

      theNextRotor = null;

      // Generate the mirror rotor. The value of the rotor acts as the offset of
      // the mirror, and the offset it was stored at in the rotor acts as the
      // value.
      theMirrorCircuitMap = new int[ inCircuitMap.length ];

      // TODO: Move this logic to the cipher.
      for ( int i = 0; i < theCircuitMap.length; i++ )
      {
         theMirrorCircuitMap[ theCircuitMap[ i ] ] = i;
      }

      return;
   }

   ////////////////////////////////////////////////////////////////////////////
   // Private Member Data
   ////////////////////////////////////////////////////////////////////////////

   /**
    * Current turn position of the rotor.
    */
   private int theCurrentPosition;

   /**
    * The rotor cipher array. Simulates a mapping of values for the enigma rotor.
    */
   private int[] theCircuitMap;

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
    * The counter that is checked against the tick rate to control the turning of
    * the rotor.
    */
   private int theTickCounter;

   /**
    * Flag denoting if the the rotor should pass ciphers in a mirrored mode. (i.e.
    * switching values with their offsets, and visa versa)
    */
   private boolean theMirrorFlag;

   /**
    * A mirror of the rotor map. Simulates sending a value into the output side of
    * the rotor. Maps the value of the rotors as offsets, and the offsets as
    * values.
    */
   private int[] theMirrorCircuitMap;

   /**
    * The next rotor in the rotor chain.
    */
   private ClockRotor theNextRotor;

}
