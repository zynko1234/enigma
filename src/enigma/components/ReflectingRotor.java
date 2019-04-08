package enigma.components;

public class ReflectingRotor implements Rotor
{
   
   ////////////////////////////////////////////////////////////////////////////
   // Constructors
   ////////////////////////////////////////////////////////////////////////////
   
   /**
    * 
    * @param inMap
    */
   public ReflectingRotor( int[] inMap )
   {
      // IF: The map passes validation then assign it.
      // ELSE: Throw an exception.
      if( validateRotor( inMap ) )
      {
         theCipherMap = inMap.clone();
      }
      else
      {
         throw new IllegalArgumentException( "The given map does not qualify as a circuit for a reflecting rotor." );
      }
      return;
   }
   
   public ReflectingRotor( ReflectingRotor inRotor )
   {
      this.theCipherMap = inRotor.theCipherMap.clone();
      return;
   }

   ////////////////////////////////////////////////////////////////////////////
   // Public Member Methods
   ////////////////////////////////////////////////////////////////////////////
   
   @Override
   /**
    * Converts a given value to a mapped value. In a refelecting rotor, all
    * values are co-mapped. (i.e. the input x returns y, and the input y returns
    * x)
    */
   public int cipher ( int inValue )
   {
      return theCipherMap[inValue];
   }

   @Override
   /**
    * Gets the size of the alphabet used by this cipher.
    */
   public int getAlphabetSize ()
   {
      return theCipherMap.length;
   }

   @Override
   public ReflectingRotor clone()
   {
      return new ReflectingRotor( this );
   }

   ////////////////////////////////////////////////////////////////////////////
   // Public Static Methods
   ////////////////////////////////////////////////////////////////////////////
   
   public boolean validateRotor( int[] inMap )
   {
      // Assume true until proven otherwise.
      boolean validity = true;
      
      // Test to see that the following holds: All values X at offsets Y, are
      // equal to values Y at offsets X. No value is mapped to an offset equal
      // to itself.
      for( int i = 0; i < inMap.length; i++)
      {
         if( ( i != inMap[inMap[i]] ) || ( i == inMap[i] ) )
         {
            validity = false;
            break;
         }
      }    

      return validity;
   }
   
   ////////////////////////////////////////////////////////////////////////////
   // Private Member Data
   ////////////////////////////////////////////////////////////////////////////
   
   /**
    * The map of the reflected cipher values.
    */
   int[] theCipherMap;
   
   
}
