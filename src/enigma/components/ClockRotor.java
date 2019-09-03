package enigma.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.function.IntUnaryOperator;

import org.omg.CORBA.portable.IndirectionException;

/**
 * This class models the gears of an enigma machine. It can be configured to act
 * as the turning rotors, a stationary rotor, or a stationary reflecting rotor.
 * <p>
 * Normal turning rotors can be generated with a call to
 * {@link #generateRotor(int, long)} and passing true to
 * {@link #setTurning(boolean)}.
 * <p>
 * Stationary rotors can be generated with a call to
 * {@link #generateRotor(int, long)}, and passing false to
 * {@link #setTurning(boolean)}.
 * <p>
 * Reflecting rotors can be generated with a call to
 * {@link #generateReflRotor(int, long)} and passing false to
 * {@link #setTurning(boolean)}.
 */
public class ClockRotor implements Rotor
{      
   
   //////////////////////////////////////////////////////////////////////////////
   // Exception Strings
   //////////////////////////////////////////////////////////////////////////////
   
   /**
    * Exception string used to denote an improperly generated map for a
    * {@link ClockRotor} object.
    */
   protected final String EXC_INVAL_MAP = "Invalid rotor map: Map cannot "
                                        + "contain duplicate or self connected "
                                        + "values";
   
   protected final String EXC_INSERT_SIZE = "Invalid insertion: Cannot insert "
                                          + "inner rotor of a different size.";

   //////////////////////////////////////////////////////////////////////////////
   // Protected Member Data
   //////////////////////////////////////////////////////////////////////////////
   
   /**
    * The value map of the RotorNodes. The map is the size of the rotors alphabet,
    * and each node contains a reference to the value it ciphers to, and the index
    * of the nodes that ciphers to it.
    * 
    * Where X ciphers to Y with a call to {@link ClockRotor#cipher(int)}, and Y
    * mirror-ciphers to X with the call to {@link ClockRotor#mirrorCipher(int)}.
    */
   protected RotorNode[] theNodeMap;
   
   /**
    * How many times this rotor must turn before it turns the next inner rotor.
    */
   protected int theTurnRate;
   
   /**
    * The current position of the turned rotor.
    */
   protected int theCurrPos;
   
   /**
    * Acts as the first position setting, and is what {@link ClockRotor#theCurrPos}
    * will reset to if {@link ClockRotor#reset()} is called.
    */
   protected int theInitPos;
   
   /*
    *  Reference to the next rotor in the rotor chain.
    */
   protected ClockRotor theNextRotor;
   
   /**
    * Denotes if this is a turning rotor or not.
    */
   protected boolean theTurnFlag;
   
   /////////////////////////////////////////////////////////////////////////////
   // Constructors
   /////////////////////////////////////////////////////////////////////////////
   
   /**
    * 
    * @param inRotorMap
    * @param inTurnRate
    */
   public ClockRotor( final int[] inRotorMap, final int inTurnRate, final int inInitPos )
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
   
   /////////////////////////////////////////////////////////////////////////////
   // Public Member Methods
   /////////////////////////////////////////////////////////////////////////////
   
   /**
    * 
    * @param inRotor
    */
   public void insert( final ClockRotor inRotor )
   {
      // Only insert rotors of the same size.
      if(inRotor.getSize() == this.getSize())
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
      }
      else
      {
         throw new IllegalArgumentException(EXC_INSERT_SIZE);
      }
      return;
   }
   
   /**
    * 
    * @param inTurnFlag
    */
   public void setTurning( final boolean inTurnFlag )
   {
      theTurnFlag = inTurnFlag;
      return;
   }
   
   /**
    * Sets the position of the rotor back to it's initial position.
    */
   public void reset()
   {
      theCurrPos = theInitPos;
      if(theNextRotor != null)
      {
         theNextRotor.reset();
      }
   }
   
   /** 
    * Moves the current position of the rotor up one, and checks if the next rotor should be turned.
    */
   public void turn()
   {
      // Only turn this rotor if the turning has been enabled.
      if(theTurnFlag)
      {
         // Increment the position and rectify any overflow.
         theCurrPos++;
         theCurrPos = theCurrPos % theNodeMap.length;
         
         // If there's a next rotor to turn and the new current position is
         // divisible by the turn rate the turn the next rotor.
         if( isDiv( theCurrPos, theTurnRate ) && ( theNextRotor != null ) )
         {
            theNextRotor.turn();
         }
      }
      return;
   }
   
   /**
    * 
    */
   public int getSize()
   {
      return theNodeMap.length;
   }
   
   /**
    * 
    */
   public int cipher(final int inValue)
   {
      // Calculate the effective offset of the value that's being ciphered to in
      // this rotor, and get the value that's pointed to at that offset as the
      // initial value of the cipher.
      int effOffset = (theCurrPos + inValue) % theNodeMap.length;
      int outValue = theNodeMap[effOffset].getWiredTo();

      // If this is not the last rotor in the chain, pass it through the chain
      // for continued ciphering.
      if (theNextRotor != null)
      {
         outValue = theNextRotor.cipher(outValue);
      }

      return outValue;
   }
   
   /**
    * 
    * @param inValue
    * @return
    */
   public int mirrorCipher(final int inValue)
   {
      int outValue = inValue;

      if (theNextRotor != null)
      {
         outValue = theNextRotor.mirrorCipher(inValue);
      }

      outValue = theNodeMap[outValue].getWiredFrom();
      outValue = ((theNodeMap.length + outValue) - theCurrPos);
      outValue = (outValue % theNodeMap.length);
      return outValue;
   }
   
   /**
    * 
    */
   public void setPosition(final int inPos)
   {
      theCurrPos = inPos;
      return;
   }
   
   /////////////////////////////////////////////////////////////////////////////
   // Public Static Methods
   /////////////////////////////////////////////////////////////////////////////
   
   public static boolean isValidRotor(final int[] inRotorMap)
   {
      boolean validity = false;
      boolean[] valueCheckList = null;

      // First validity check is if
      if (isDiv(inRotorMap.length, 2))
      {
         validity = true;
         // Used to map all possible values from 0 to N of the candidate rotor
         // map.
         valueCheckList = new boolean[inRotorMap.length];
         Arrays.fill(valueCheckList, Boolean.FALSE);
      }

      // Check off every mapped value. Set the index equal rotor map value to
      // true when found. Check if any values are mapped to themselves.
      for (int i = 0; (i < inRotorMap.length) && validity; i++)
      {
         valueCheckList[inRotorMap[i]] = true;
         validity &= (inRotorMap[i] != i);
      }

      // Bitwise AND against the checklist. If there are unmapped or
      // duplicate values then validity will be set to false and the loop
      // will break.
      for (int i = 0; i < valueCheckList.length && validity; i++)
      {
         // Check off every mapped value.
         validity = validity & valueCheckList[i];
      }
      return validity;
   }
   
   /**
    * 
    * @param inAlphaSize
    * @param inMapSeed
    * @return
    */
   public static ClockRotor generateRotor(final int inAlphaSize,
                                          final long inMapSeed)
   {
      int[] outMap = new int[inAlphaSize];

      // Flag denoting that the generated map has been fully tested for
      // validity.
      boolean validMap = false;

      // Seed a random number generator.
      Random tempRand = new Random(inMapSeed);

      // Make sure the tick rate is grater than 1, and less than the alphabet size
      // inclusively.
      int outTickRate = tempRand.nextInt(inAlphaSize - 1) + 1;

      int[] swapMap = generateOrderedArray(0, (inAlphaSize - 1));

      // Keep generating until a valid map is created.
      while (validMap == false)
      {
         for (int i = 0, swapIndex = 0, swapTail = 0; i < outMap.length; i++)
         {
            // Pick a random index of the remaining values.
            swapIndex = tempRand.nextInt(swapMap.length - i);

            // Swap it with the last accessible element of the list.
            swapTail = (swapMap.length - 1) - i;

            // Copy the random value to be swapped to the output array.
            outMap[i] = swapMap[swapIndex];

            // Swap the copied value to the accessible end of the array.
            swapMap[swapIndex] = swapMap[swapTail];
            swapMap[swapTail] = outMap[i];

         }
         // Check the final validity of the map.
         validMap = ClockRotor.isValidRotor(outMap);
      }
      ClockRotor outRotor = new ClockRotor(outMap, tempRand.nextInt(generateTickBound(inAlphaSize)), tempRand.nextInt(inAlphaSize));
      outRotor.setTurning(true);
      return outRotor;
   }
   
   public static ClockRotor generateReflRotor(final int inAlphaSize,
                                              final long inMapSeed)
   {
      // Map to be returned.
      int[] outMap = new int[inAlphaSize];
      
      // Seed a random number generator for choosing indexes.
      Random tempRand = new Random(inMapSeed);
      
      // Create an array list and initialize it with all the values of the list.
      ArrayList<Integer> swapList = new ArrayList<Integer>(inAlphaSize);
      for( int i = 0; i < inAlphaSize; i++ ) {swapList.add(i);}
      
      // Flag denoting that the generated map has been fully tested for
      // validity.
      boolean validMap = false;
      int swapIndex = 0;
      int swapAlpha = 0;
      int swapBeta;
      
      // Get the next two numbers to have mapped to each other in the rotor map.
      // Remove that element after each assignment to avoid duplication.
      while(swapList.size() > 0)
      {
         // Pull value the first swap value, then delete it from the swap list.
         swapIndex = tempRand.nextInt(swapList.size());
         swapAlpha = swapList.get(swapIndex);
         swapList.remove(swapIndex);
         
         // Pull the second swap value, then delete it from the swap list.
         swapIndex = tempRand.nextInt(swapList.size());
         swapBeta = swapList.get(swapIndex);
         swapList.remove(swapIndex);
         
         // Perform the swap.
         outMap[swapAlpha] = swapBeta;
         outMap[swapBeta] = swapAlpha;
      }
      
      ClockRotor outRotor = new ClockRotor(outMap, tempRand.nextInt(generateTickBound(inAlphaSize)), tempRand.nextInt(inAlphaSize));
      outRotor.setTurning(false);
      return outRotor;
   }
   
   /////////////////////////////////////////////////////////////////////////////
   // Protected Static Methods
   /////////////////////////////////////////////////////////////////////////////
   
   /**
    * Macro function for checking factorability.
    * 
    * @param inBase The number acting as the base for checking factorability.
    * @param inFact The divisor that is being checked as a perfect factor.
    * @return True if the base is factorable by the devisor, false otherwise.
    */
   protected static boolean isDiv(final int inBase, final int inFact)
   {
      return ((inBase % inFact) == 0);
   }
   
   //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   // TODO: Add logic so that when start is less than end, the array is
   // generated in descending order.
   //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   
   protected static int[] generateOrderedArray(final int start, final int end)
   {
      int length = (end - start) + 1;
      
      // Anything with size less than zero should be treated as zero.
      if (length < 0) { length = 0; }
            
      int[] outArray = new int[length];
      
      for(int i = start; i < (start + outArray.length); i++)
      {
         outArray[i - start] = i;
      }
      return outArray;
   }
   
   protected static int[] generateArray(final int inValue, final int inLength)
   {
      int[] outArray = new int[inLength];
      
      for(int i = 0; i < outArray.length; i++)
      {
         outArray[i] = inValue;
      }
      
      return outArray;
   }
   
   protected static int generateTickBound(final int inAlphaSize)
   {
      return (int)Math.sqrt((double)(inAlphaSize));
   }
   
}

