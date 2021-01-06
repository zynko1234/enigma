package enigma.components;

public interface Rotor
{
   /**
    * Denotes what character set is being referenced in rotor generation.
    */
   public enum CharEncoding
   {
      ASCII, UNICODE, ALPHABET
   }

   /**
    * Size of the English-Latin alphabet.
    */
   public static final int ALPHABET_SIZE = 26;
   
   /**
    * Size of the complete ASCII character set.
    */
   public static final int ASCII_BYTE = 256;

   /**
    * Size of the complete Unicode character set.
    */
   public static final int UNICODE_SIZE = 1112064;
   
   /**
    * Converts the value based on the rotor architecture.
    * 
    * @param inValue The value to be converted.
    * @return The converted value.
    */
   public abstract int cipher ( final int inValue );

   /**
    * Returns the size of the rotor.
    * 
    * @return The size of the rotor.
    */
   public int getSize ();

   /**
    * 
    * @param inPos
    */
   public void setPosition ( final int inPos );

}
