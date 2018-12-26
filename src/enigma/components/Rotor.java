package enigma.components;

public class Rotor
{

   /**
    * 
    * @param rotorMap
    * @param isStationary
    * @param inStartPos
    */
   public Rotor ( final int[] rotorMap, final boolean isStationary )
   {

      if ( IsValidRotor( rotorMap ) == false )
      {
         throw new IllegalArgumentException( "Rotor missing values for a complete alphabet." );
      }

      theRotor = rotorMap;
      theMirrorRotor = new int[ rotorMap.length ];

      // Generate the mirror rotor. The value of the rotor acts as the offset of
      // the mirror, and the offset it was stored at in rotor acts as the value.
      for ( int i = 0; i < theRotor.length; i++ )
      {
         theMirrorRotor[ theRotor[ i ] ] = i;
      }

      theStationaryFlag = isStationary;
      theRotorPos = DEF_INIT_START_POS;

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
      if ( IsValidValue( inValue ) )
      {
         int turnOffset;

         // IF: This is a stationary rotor then the turn offset is just the
         // initial value.
         // ELSE: Set the turn offset to the value added to the rotations moded
         // against the rotor size for overflow. (e.g. If the value is 20 of 25
         // and the rotation position is 9 then the offset
         // is (20 + 9) mod 26 = 3 )

            if( isMirrored == false )
            {
               turnOffset = ( ( inValue + theRotorPos ) % theRotor.length );
               output =  theRotor[ turnOffset ];
            }
            else
            {
               output = theMirrorRotor[inValue];
               
               // Reverse modulus in case the rotor position evaluates negative 
               // values. (e.g. mirrored offset value of 2 minus a position of 
               // 5 will give the length of the alphabet - 3)
               output = ( theRotor.length + output - theRotorPos ) % theRotor.length;
               
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
    * Sets a new position value.
    * 
    * @param inPosition The new position value to set.
    */
   public void SetRotorPos ( int inPosition )
   {
      if ( IsValidValue( inPosition ) )
      {
         theRotorPos = inPosition;
      } else
      {
         throw new IllegalArgumentException( "Value is outside the range of the rotor alphabet." );
      }
   }

   /**
    * 
    */
   public int GetRotorPos ()
   {
      return theRotorPos;
   }

   /**
    * 
    */
   public void TurnRotor ()
   {
      if ( !theStationaryFlag )
      {
         // Increment the current position of the rotor.
         theRotorPos++;

         // Set the rotor position to the mod of the rotor size to zero it out if
         // it's gone over. (e.g. If the rotor size is 26 and the position goes
         // from 25 to 26 on the increment, set it to zero.)
         theRotorPos = theRotorPos % theRotor.length;
      } else
      {
         throw new IllegalStateException( "Attempted to turn a stationary rotor." );
      }
   }

   public boolean IsValidValue ( final int inValue )
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
    * @param inRotorMap
    * @return
    */
   public static boolean IsValidRotor ( int[] inRotorMap )
   {
      boolean validity = true;

      // Used to check that all values are mapped on the rotor.
      boolean[] valueCheckList = new boolean[ inRotorMap.length ];

      // Check off every mapped value. Set the index representing the value to
      // true when found.
      for ( int i = 0; i < inRotorMap.length; i++ )
      {

         valueCheckList[ inRotorMap[ i ] ] = true;
      }

      // Bitwise AND against the checklist. If there are unmapped or duplicate
      // values then validity will be set to false.
      for ( int i = 0; i < valueCheckList.length; i++ )
      {
         // Check off every mapped value.
         validity = validity | valueCheckList[ i ];

         // If a failure is found then break out of the loop.
         if ( !validity )
         {
            break;
         }
      }

      return validity;
   }

   ////////////////////////////////////////////////////////////////////////////
   // Private Static Data
   ////////////////////////////////////////////////////////////////////////////

   /**
    * 
    */
   private static int DEF_INIT_START_POS = 0;

   ////////////////////////////////////////////////////////////////////////////
   // Private Member Data
   ////////////////////////////////////////////////////////////////////////////

   /**
    * 
    */
   private boolean theStationaryFlag;

   /**
    * Current turn position of the rotor.
    */
   private int theRotorPos;

   /**
    * The rotor cipher array. Simulates a mapping of values for the enigma rotor.
    */
   private int[] theRotor;

   /**
    * A mirror of the rotor map. Simulates sending a value into the output side of
    * the rotor. Maps the value of the rotors as offsets, and the offsets as
    * values.
    */
   private int[] theMirrorRotor;
}
