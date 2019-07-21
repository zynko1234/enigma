package enigma.components;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;

import org.junit.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JUnitClockRotor
{
   
   ////////////////////////////////////////////////////////////////////////////
   // Common Test Data
   ////////////////////////////////////////////////////////////////////////////
   
   private static final String VAL_CHECK_FMT = "[Input: %d]"
                                             + "[Cipher: %d]"
                                             + "[Expected Cipher: %d]\n";
   
   private static final int LARGE_MAP_SIZE = 100000;
   
   private static final int LARGE_MAP_COUNT = 100;
   
   private static final int LARGE_INPUT_SIZE = 50000;
   
   private static ClockRotor rotAlpha;
   
   private static final int[] MAP_ALPHA = {5, 3, 0, 2, 1, 4};
   
   private static final int[] MAP_BETA  = {4, 0, 3, 1, 5, 2};
   
   private static final int[] MAP_GAMMA = {2, 4, 0, 5, 1, 3};   
   
   ////////////////////////////////////////////////////////////////////////////
   // Pre-Condition Functions
   ////////////////////////////////////////////////////////////////////////////
   
   // Setup preconditions.
   @BeforeEach
   public void beforeEach()
   {      
      manuallyResetRotors();
   }
   
   @AfterEach
   public void afterEach()
   {
      //rotAlpha = null;
      return;
   }
   
   ////////////////////////////////////////////////////////////////////////////
   // Unit Tests
   ////////////////////////////////////////////////////////////////////////////
   
   @Test
   public void testBasicCipher()
   {
      System.out.println( Thread.currentThread().getStackTrace()[1].getMethodName() );
      int[] testMapIn  = {0, 1, 2, 3, 4, 5};
      int[] testMapOut = {0, 4, 1, 5, 2, 3};
      
      for( int i = 0, val; i < testMapIn.length; i++ )
      {
         val = rotAlpha.cipher( testMapIn[i] );
         System.out.printf(VAL_CHECK_FMT, i, val, testMapOut[i]);
         assertTrue( val == testMapOut[i] );
      }
      
      return;
   }
   
   @Test
   public void testFullCipher()
   {
      System.out.println( Thread.currentThread().getStackTrace()[1].getMethodName() );
      
      int[] reflMap = {1, 0, 5, 4, 3, 2};
      ClockRotor reflRotor = new ClockRotor(reflMap, 0);
      
      int[] testMapIn = {0, 1, 2, 3, 4, 5};
      int[] testMapOut = new int[testMapIn.length];
      
      // Generate an array of output ciphered values from this rotor
      // configuration.
      for( int i = 0, ciphVal; i < testMapIn.length; i++ )
      {
         rotAlpha.turn();
         ciphVal = rotAlpha.cipher(testMapIn[i]);
         ciphVal = reflRotor.cipher(ciphVal);
         ciphVal = rotAlpha.mirrorCipher(ciphVal);
         testMapOut[i] = ciphVal;
         System.out.printf("Creating output map ciphering %d -> %d\n", testMapIn[i], testMapOut[i]);
      }
      
      // Reset the rotors and plug the output back in through the rotors. The
      // result should be the beginning input.
      manuallyResetRotors();
      
      for( int i = 0, ciphVal; i < testMapIn.length; i++ )
      {
         rotAlpha.turn();
         ciphVal = rotAlpha.cipher(testMapOut[i]);
         ciphVal = reflRotor.cipher(ciphVal);
         ciphVal = rotAlpha.mirrorCipher(ciphVal);
         System.out.printf(VAL_CHECK_FMT, testMapOut[i], ciphVal, testMapIn[i]);
         
         // Check that the cipher value matches the original input.
         assertTrue( ciphVal == testMapIn[i] );
      }
      return;
   }  
   
   @Test
   public void testLargeMap()
   {
      final int alphabetSize = Rotor.UNICODE_SIZE;
      ClockRotor rootRotor = null;
      Random randomzier = new Random(System.currentTimeMillis());
      
      int[] inputValues = new int[LARGE_INPUT_SIZE];
      int[] ciphValues = new int[LARGE_INPUT_SIZE];
      
      // Generate input values
      for( int i = 0; i < inputValues.length; i++ )
      {
         inputValues[i] = randomzier.nextInt( alphabetSize );
      }
      
      // Generate the random large maps.
      for( int i = 0; i < LARGE_MAP_COUNT; i++ )
      {
         if( rootRotor != null )
         {
            rootRotor.insert(ClockRotor.generateRotor(alphabetSize, System.currentTimeMillis()));
         }
         else
         {
            rootRotor = ClockRotor.generateRotor(alphabetSize, System.currentTimeMillis() );
         }
      }
      
      // Cipher the random input.
      for( int i = 0; i < ciphValues.length; i++ )
      {
         
      }
      
      // Reverse the ciphering and check.
      for( int i = 0; i < ciphValues.length; i++ )
      {
         
      }
   }
   
   @Test
   public void testTurningCipher()
   {
      System.out.println( Thread.currentThread().getStackTrace()[1].getMethodName() );
      int[] testMapIn  = {0, 1, 2, 3, 4, 5};
      int[] testMapOut = {4, 4, 0, 0, 3, 4};
      
      for( int i = 0, val; i < testMapIn.length; i++ )
      {
         rotAlpha.turn();
         val = rotAlpha.cipher( testMapIn[i] );
         System.out.printf(VAL_CHECK_FMT, i, val, testMapOut[i]);
         assertTrue( val == testMapOut[i] );
      }
      
      return;
   }
   
   @Ignore
   public void manuallyResetRotors()
   {
      // Create rotors and chain them into their clock configuration.
      rotAlpha = new ClockRotor(MAP_ALPHA, 2);
      rotAlpha.insert( new ClockRotor(MAP_BETA, 3) );
      rotAlpha.insert( new ClockRotor(MAP_GAMMA, 4) );  
   }
   
}
