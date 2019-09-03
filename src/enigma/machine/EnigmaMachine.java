package enigma.machine;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import enigma.components.ClockRotor;
import enigma.components.Rotor;
import enigma.components.Rotor.CharEncoding;

public class EnigmaMachine
{

   ////////////////////////////////////////////////////////////////////////////
   // Public Constants
   ////////////////////////////////////////////////////////////////////////////
   
   /**
    * Value map of the reflecting rotor of the Swiss-K model of the enigma
    * machine.
    */
   public static final int[] SWISS_K_REF = {8, 12, 4, 19, 2, 6, 5, 17, 0, 24, 
                                            18, 16, 1, 25, 23, 22, 11, 7, 10, 3,
                                            21, 20, 15, 14, 9, 13};

   /**
    * Value map of the stationary front rotor of the Swiss-K model of the enigma
    * machine.
    */
   public static final int[] SWISS_K_STAT = {16, 22, 4, 17, 19, 25, 20, 8, 14,
                                             0, 18, 3, 5, 6, 7, 9, 10, 15, 24, 
                                             23, 2, 21, 1, 13, 12, 11};

   /**
    * Value map of rotor one of the Swiss-K model of the enigma machine.
    */
   public static final int[] SWISS_K_I = {15, 4, 25, 20, 14, 7, 23, 18, 2, 21,
                                          5, 12, 19, 1, 6, 11, 17, 8, 13, 16, 9,
                                          22, 0, 24, 3, 10};

   /**
    * Value map of rotor two of the Swiss-K model of the enigma machine.
    */
   public static final int[] SWISS_K_II = {25, 14, 20, 4, 18, 24, 3, 10, 5, 22,
                                           15, 2, 8, 16, 23, 7, 12, 21, 1, 11, 
                                           6, 13, 9, 17, 0, 19};

   /**
    * Value map of rotor three of the Swiss-K model of the enigma machine.
    */
   public static final int[] SWISS_K_III = {4, 7, 17, 21, 23, 6, 0, 14, 1, 16,
                                            20, 18, 8, 12, 25, 5, 11, 24, 13, 
                                            22, 10, 19, 15, 3, 9, 2};
   
   ////////////////////////////////////////////////////////////////////////////
   // Protected Constants
   ////////////////////////////////////////////////////////////////////////////

   /**
    * Maximum number of clock rotors to chain to the head rotor.
    */
   protected static int DEF_ROTOR_MAX = 128;
   
   /**
    * Minimum number of clock rotors to chain to the head rotor. 
    */
   protected static int DEF_ROTOR_MIN = 4;
   
   /////////////////////////////////////////////////////////////////////////////
   // Protected Member Data
   /////////////////////////////////////////////////////////////////////////////   
   
   protected ClockRotor theHeadRotor;

   protected ClockRotor theReflectingRotor;
   
   protected CharEncoding theEncoding;

   protected int theRotorCount;
   
   protected boolean theInitializedFlag;
   
   protected int theAlphSize;
   
   /////////////////////////////////////////////////////////////////////////////
   // Constructors
   /////////////////////////////////////////////////////////////////////////////
   
   //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   // TODO: Set the size of the rotor explicitly, given the encoding        
   // parameter. Probably with a switch.                                                           
   //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   
   /**
    * 
    * @param inEncoding
    * @param inSeed
    */
   public EnigmaMachine(final CharEncoding inEncoding, final long inSeed)
   {
      theEncoding = inEncoding;
      theHeadRotor = null;
      theReflectingRotor = null;
      theRotorCount = 0;
      theInitializedFlag = false;
      
      return;
   }
   
   public void reInitialize(final long inSeed)
   {
      // Setup the machine with using the rotor generators, and flip the flag
      // denoting that the machine has been fully initialized.
      initMachine(this, inSeed);
      theInitializedFlag = true;
   }
   
   public void initialize(final Calendar inDate)
   {

      
      // Setup the machine with using the rotor generators, and flip the flag
      // denoting that the machine has been fully initialized.
      initMachine(this, genSeedFromDate(inDate));
      theInitializedFlag = true;
      return;
   }
   
   public void setHeadRotor( ClockRotor inHead )
   {
      theHeadRotor = inHead;
   }
   
   public void setReflRotor( ClockRotor inRefl )
   {
      theReflectingRotor = inRefl;
   }
   
   public int getRotorCount()
   {
      return theRotorCount;
   }
   
   public int getAlphabetSize()
   {
      return theAlphSize;
   }
   
   /////////////////////////////////////////////////////////////////////////////
   // Protected Static Methods
   /////////////////////////////////////////////////////////////////////////////
   
   
   protected long genSeedFromDate( final Calendar inDate )
   {
      long seed = 0;
      Charset charSet = StandardCharsets.UTF_8;
      byte[] seedBytes = inDate.getTime().toString().getBytes(charSet);
      
      // Sum the bytes together.
      for(byte currByte : seedBytes) { seed += currByte; }
      
      return seed;
   }
   
   protected static void initMachine (final EnigmaMachine inMach, 
                                      final long inSeed)
   {
      if( inMach.theHeadRotor != null ) { inMach.theHeadRotor = null; }
      
      Random randGen = new Random(inSeed);
            
      int tempRotorCount = randGen.nextInt(DEF_ROTOR_MAX - (DEF_ROTOR_MIN - 1));
      tempRotorCount += DEF_ROTOR_MIN;
      
      // Build the chain of clock rotors.
      for(int i = 0; i < tempRotorCount; i++ )
      {
         if(inMach.theHeadRotor != null)
         {
            inMach.theHeadRotor.insert(ClockRotor.generateRotor(inMach.theAlphSize, randGen.nextLong()));
         }
         else
         {
            inMach.theHeadRotor = ClockRotor.generateRotor(inMach.theAlphSize, randGen.nextLong());
         }
      }
      
      inMach.theReflectingRotor = ClockRotor.generateReflRotor(inMach.theAlphSize, randGen.nextLong());
      
      // Suggest the garbage collector do a cleanup at the end of this function
      // due to potentially shaking entire rotor collections.
      System.gc();
      return;
   }
   
   
   
}
