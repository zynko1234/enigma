package enigma.components;

/**
 * Represents the plug board of the enigma machine. The internals are meant to
 * be agnostic to any alphabet but the default constructor will set the size to
 * 26 to represent the english alphabet.
 * 
 * @author Nicholas J. Zynko (December 19th, 2018)
 */
public class PlugBoard
{

   ////////////////////////////////////////////////////////////////////////////
   // Constructors
   ////////////////////////////////////////////////////////////////////////////

   /**
    * Default constructor for PlugBoard class. Default alphabet size of the
    * plugboard is 26 to represent the english alphabet.
    */
   public PlugBoard ()
   {
      theAlphabetSize = DEF_ALPHABET_SIZE;
      Initialize();
      return;
   }

   /**
    * Initializing constructor. Allows the setting of an alphabet size.
    * 
    * @param inAlphabetSize A custom alphabet size. This intended to map to an
    *                       alphabet that is external and agnostic to this object.
    */
   public PlugBoard ( int inAlphabetSize )
   {
      theAlphabetSize = inAlphabetSize;
      Initialize();
      return;
   }

   ////////////////////////////////////////////////////////////////////////////
   // Public Member Methods
   ////////////////////////////////////////////////////////////////////////////

   /**
    * Fetches the value mapped to given value. If nothing is currently mapped to it
    * then this function returns the given value itself.
    * 
    * @param inVal The value to check for an associated value.
    * @return The associated value (if one exists), the original given value
    *         otherwise.
    */
   public int GetMap ( final int inVal )
   {
      // If the parameter value is outside of possible range throw an exception.
      if ( inVal > DEF_ALPHABET_SIZE || inVal < 0 )
      {
         throw new IllegalArgumentException( EXC_GETMAP_ARGS );
      }

      // Return the mapped value if there is a mapping, otherwise return
      // the given value. (e.g. If A is mapped to null, then just return
      // the value A)
      Integer output = thePlugMap[ inVal ];
      output = ( output == null ) ? inVal : output;

      return output.intValue();
   }

   /**
    * Sets the plug values. Simulates a wire being connected from value A to value
    * B. (e.g. Connecting the letter B to the letter X, and X to B). If this fails
    * it returns false.
    * 
    * @param inValA The value to be connected to B.
    * @param inValB The value to be connected to A.
    * @param force  Forces an unset of the values passed to any preset values
    *               before setting them to eachother. If this is false, and one or
    *               both of the values are set then this function will do nothing.
    * @return True on successful plug connection, false otherwise.
    */
   public boolean SetPlug ( final int inValA, final int inValB, final boolean forceSet )
   {
      boolean success = true;

      // If the force set flag is flipped, remove any set plugs from A and B,
      // as well as their associated set plugs.
      if ( forceSet )
      {
         UnsetPlug( inValA );
         UnsetPlug( inValB );
      }

      // IF: Force set flipped or the plugs are naturally unset then set the
      // plugs. Force set should lazy evaluate and skip checking if the plugs
      // are already set.
      // ELSE: The values were not successfully mapped. Set return flag to
      // false.
      if ( forceSet || ( !isPlugSet( inValA ) && !isPlugSet( inValB ) ) )
      {
         // Set the plugs to mirror eachother to mimic a wire connecting A to B,
         // and B to A.
         thePlugMap[ inValA ] = inValB;
         thePlugMap[ inValB ] = inValA;
      }
      else
      {
         success = false;
      }

      return success;
   }

   /**
    * Unconnects a plug given as well as its associated plug. Simulates pulling a
    * wire from two values.
    * 
    * @param inVal The value to be unset. The associated value will be unset if it
    *              is set. (e.g. Given A, and A is mapped to B then A and B will be
    *              unmapped as they are mirrored).
    */
   public void UnsetPlug ( final int inVal )
   {
      // Skip unsetting if the given plug is not set.
      if ( isPlugSet( inVal ) )
      {
         // Get the value stored in the plug map that mirrors the index of the
         // associated value. (e.g. The offset equal to the value A will store
         // B. The offset equal to the value B will store A).
         int associatedValue = thePlugMap[ inVal ];

         // Unset these values.
         thePlugMap[ associatedValue ] = null;
         thePlugMap[ inVal ] = null;
      }

      return;
   }

   /**
    * Checks to see if there is an associated value with the given value.
    * 
    * @param inVal The value to be checked for an associated value.
    * @return True if a mapping/connection is found.
    */
   public boolean isPlugSet ( final int inVal )
   {
      return ( thePlugMap[ inVal ] != null );
   }

   ////////////////////////////////////////////////////////////////////////////
   // Private Member Methods
   ////////////////////////////////////////////////////////////////////////////

   /**
    * Macro initializing function for the common elements of all constructors.
    */
   private void Initialize ()
   {
      thePlugMap = new Integer[ theAlphabetSize ];

      // Populate the plug map with nulls to represent unpaired values.
      for ( int i = 0; i < DEF_ALPHABET_SIZE; i++ )
      {
         thePlugMap[ i ] = null;
      }
      return;
   }

   ////////////////////////////////////////////////////////////////////////////
   // Private Static Data
   ////////////////////////////////////////////////////////////////////////////

   /**
    * Default alphabet size. Corresponds to the english alphabet size.
    */
   private static int DEF_ALPHABET_SIZE = 26;

   /**
    * Exception string for the {@link #GetMap(int)} function.
    */
   private static String EXC_GETMAP_ARGS = "Attempted to access an unmappable value";

   ////////////////////////////////////////////////////////////////////////////
   // Private Member Data
   ////////////////////////////////////////////////////////////////////////////

   /**
    * Array representing the associated values. The offsets 0 to N represent the
    * glyphs of the alphabet, and the values stored there represent the other
    * glyphs values (also 0 to N).  
    */
   private Integer[] thePlugMap;

   /**
    * The size of the alphabet to use for this plugboard.
    */
   private int theAlphabetSize;

}
