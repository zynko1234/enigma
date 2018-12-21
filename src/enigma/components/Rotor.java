package enigma.components;

public class Rotor
{
   
   /**
    * 
    * @param rotorMap
    * @param isStationary
    * @param inStartPos
    */
   public Rotor( final int[] rotorMap, final boolean isStationary, final int inStartPos )
   {
      
      if ( IsValidRotor( rotorMap ) == false )
      {
         throw new IllegalArgumentException("Rotor missing values for a complete alphabet.");
      }
      
      theRotor = rotorMap;

      // Generate the mirror rotor. The value of the rotor acts as the offset of
      // the mirror, and the offset it was stored at in rotor acts as the value.
      for( int i = 0; i < theRotor.length; i++ )
      {
         theMirrorRotor[theRotor[i]] = i;
      }
      
      theStationaryFlag = isStationary;
      theRotorPos = inStartPos;

      return;
   }
   
   /**
    * 
    * @param inValue
    * @param isMirrored
    * @return
    */
   public int PassValue( final int inValue, final boolean isMirrored )
   {
      int output = -1;
      
      // IF: The given value is within the bounds of zero and the size of the
      // rotor then get the value.
      // ELSE: Thow an exception.
      if( ( inValue < theRotor.length ) && ( inValue >= 0 ) )
      {
         int turnOffset;
         
         // IF: This is a stationary rotor then the turn offset is just the
         // initial value.
         // ELSE: Set the turn offset to the value added to the rotations moded
         // against the rotor size for overflow. (e.g. If the value is 20 of 25
         // and the rotation position is 9 then the offset
         // is (20 + 9) mod 26 = 3 )
         if(theStationaryFlag)
         {
            turnOffset = inValue;
         }
         else
         {
            turnOffset = ( (inValue + theRotorPos) % theRotor.length );
         }
            
         // If this is coming from the simulated output side of the rotor, use
         // mirror of the rotor. Otherwise use the rotor as normal.
         output = isMirrored ? theMirrorRotor[turnOffset]: theRotor[turnOffset];
      }
      else
      {
         throw new IllegalArgumentException( "Value is outside the range of the rotor alphabet." );
      }
      return output;
   }
   
   /**
    * 
    * @param inPosition
    */
   public void SetRotorPosition(final int inPosition)
   {
      if( IsValidValue( inPosition ) )
      {
         theRotorPos = inPosition;
      }
      else
      {
         throw new IllegalArgumentException( "Value is outside the range of the rotor alphabet." );
      }
   }
   
   /**
    * 
    * @return
    */
   public int GetRotorPosition()
   {
      return theRotorPos;
   }
   
   /**
    * 
    * @return
    */
   public int GetRotorSize()
   {
      return theRotor.length;
   }
   
   /**
    * 
    */
   public void TurnRotor()
   {
      if(!theStationaryFlag)
      {
         // Increment the current position of the rotor.
         theRotorPos++;
         
         // Set the rotor position to the mod of the rotor size to zero it out if
         // it's gone over. (e.g. If the rotor size is 26 and the position goes
         // from 25 to 26 on the increment, set it to zero.)
         theRotorPos = theRotorPos % theRotor.length;
      }
      else
      {
         throw new IllegalStateException( "Attempted to turn a stationary rotor." );
      }
   }
   
   public boolean IsValidValue( final int inValue )
   {
      // Return if the value is within the range of zero and the size of the
      // rotor;
      return ( inValue < theRotor.length ) && ( inValue >= 0 );
   }
   
   ////////////////////////////////////////////////////////////////////////////
   // Private Static Methods
   ////////////////////////////////////////////////////////////////////////////
   
   /**
    * 
    * @param inRotorMap
    * @return
    */
   public static boolean IsValidRotor( int[] inRotorMap )
   {
      boolean validity = true;

      // Used to check that all values are mapped on the rotor.
      boolean[] valueCheckList = new boolean[inRotorMap.length];
      
      // Check off every mapped value. Set the index representing the value to
      // true when found.
      for( int i = 0; i < inRotorMap.length; i++ )
      {
         
         valueCheckList[ inRotorMap[i] ] = true;
      }
      
      // Bitwise AND against the checklist. If there are unmapped or duplicate
      // values then validity will be set to false.
      for( int i = 0; i < valueCheckList.length; i++ )
      {
         // Check off every mapped value.
         validity = validity | valueCheckList[ i ];
         
         // If a failure is found then break out of the loop.
         if( !validity )
         {
            break;
         }
      }
      
      return validity;
   }
   
   ////////////////////////////////////////////////////////////////////////////
   // Private Member Data
   ////////////////////////////////////////////////////////////////////////////
   
   /**
    * 
    */
   private boolean theStationaryFlag;
   
   /**
    *  Current turn position of the rotor.
    */
   private int theRotorPos;
   
   /**
    * The rotor cipher array. Simulates a mapping of values for the enigma
    * rotor.
    */
   private int[] theRotor;
   
   /**
    * A mirror of the rotor map. Simulates sending a value into the output side
    * of the rotor. Maps the value of the rotors as offsets, and the offsets as
    * values.
    */
   private int[] theMirrorRotor;
}
