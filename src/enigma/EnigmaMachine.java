package enigma;

import enigma.components.PlugBoard;
import enigma.components.Rotor;

public class EnigmaMachine
{

   ////////////////////////////////////////////////////////////////////////////
   // Constructors
   ////////////////////////////////////////////////////////////////////////////
   
   public EnigmaMachine( Rotor inRotA, Rotor inRotB, Rotor inRotC, Rotor inRotS, Rotor inRotR )
   {
      
      return;
   }
   
   ////////////////////////////////////////////////////////////////////////////
   // Public Member Methods
   ////////////////////////////////////////////////////////////////////////////
   
   
   ////////////////////////////////////////////////////////////////////////////
   // Private Member Methods
   ////////////////////////////////////////////////////////////////////////////
   
   private void Rotate()
   {
      // Rotate the first wheel.
      theFirstRotorSlot.TurnRotor();
      
      // IF: The first rotor returns to the 0th position, turn the next rotor.
      if ( theFirstRotorSlot.GetRotorPosition() == 0 )
      {
         theSecondRotorSlot.TurnRotor();

         // IF: The first rotor returns to the 0th position, turn the next rotor.
         if ( theSecondRotorSlot.GetRotorPosition() == 0 )
         {
            theThirdRoterSlot.TurnRotor();
         }
       }
         return;
   }
   
   private int CipherValue( final int inVal )
   {
      int output = inVal;
      
      Rotate();
      
      boolean isReflected = false;
      
      thePlugBoard.GetMap( output );
      
      output = theStationaryRotor.PassValue( output, isReflected );
      output = theFirstRotorSlot.PassValue( output, isReflected );
      output = theSecondRotorSlot.PassValue( output, isReflected );
      output = theThirdRoterSlot.PassValue( output, isReflected );
      
      output = theReflectingRotor.PassValue( output, isReflected );
      isReflected = true;
      
      output = theThirdRoterSlot.PassValue( output, isReflected );
      output = theSecondRotorSlot.PassValue( output, isReflected );
      output = theFirstRotorSlot.PassValue( output, isReflected );
      output = theStationaryRotor.PassValue( output, isReflected );
      
      thePlugBoard.GetMap( output );
      
      return output;
   }
   
   ////////////////////////////////////////////////////////////////////////////
   // Private Member Data
   ////////////////////////////////////////////////////////////////////////////
   
   /**
    * 
    */
   PlugBoard thePlugBoard;
   
   /**
    * 
    */
   Rotor theFirstRotorSlot;
   
   /**
    * 
    */
   Rotor theSecondRotorSlot;
   
   /**
    * 
    */
   Rotor theThirdRoterSlot;
   
   /**
    * 
    */
   Rotor theReflectingRotor;
   
   /**
    * 
    */
   Rotor theStationaryRotor;
   
   private static final int[][] ROT_ONE = { { 0, 15 },  { 1, 4 },   { 2, 25 },  
         { 3, 20 },  { 4, 14 },  { 5, 7 }, 
         { 6, 23 },  { 7, 18 },  { 8, 2 },
         { 9, 21 },  { 10, 5 },  { 11, 12 },  
         { 12, 19 }, { 13, 1 },  { 14, 6 },  
         { 15, 11 }, { 16, 17 }, { 17, 8 }, 
         { 18, 13 }, { 19, 16 }, { 20, 9 },
         { 21, 22 }, { 22, 0 },  { 23, 24 }, 
         { 24, 3 },  { 25, 10 } };

/**
* Rotor two of the Swiss-K model of the enigma machine.
*/
private static final int[][] ROT_TWO = { { 0, 25 },  { 1, 14 },  { 2, 20 }, 
         { 3, 4 },   { 4, 18 },  { 5, 24 }, 
         { 6, 3 },   { 7, 10 },  { 8, 5 }, 
         { 9, 22 },  { 10, 15 }, { 11, 2 }, 
         { 12, 8 },  { 13, 16 }, { 14, 23 }, 
         { 15, 7 },  { 16, 12 }, { 17, 21 }, 
         { 18, 1 },  { 19, 11 }, { 20, 6 }, 
         { 21, 13 }, { 22, 9 },  { 23, 17 }, 
         { 24, 0 },  { 25, 19 } };  

/**
* Rotor three of the Swiss-K model of the enigma machine.
*/
private static final int[][] ROT_THREE = { { 0, 4 },   { 1, 7 },   {2, 17 },
           { 3, 21 },  { 4, 23 },  {5, 6 }, 
           { 6, 0 },   { 7, 14 },  {8, 1 }, 
           { 9, 16 },  { 10, 20 }, {11, 18 }, 
           { 12, 8 },  { 13, 12 }, {14, 25 }, 
           { 15, 5 },  { 16, 11 }, {17, 24 }, 
           { 18, 13 }, { 19, 22 }, {20, 10 }, 
           { 21, 19 }, { 22, 15 }, {23, 3 }, 
           { 24, 9 },  { 25, 2 } };

/**
*  Reflector rotor. Does not turn, and all mapped values are mirrored (e.g. 
*  0 is mapped to 8, and 8 to zero).
*/
private static final int[][] ROT_REF = { { 0, 8 },   { 1, 12 },  { 2, 4 }, 
         { 3, 19 },  { 4, 2 },   { 5, 6 }, 
         { 6, 5 },   { 7, 17 },  { 8, 0 }, 
         { 9, 24 },  { 10, 18 }, { 11, 16 }, 
         { 12, 1 },  { 13, 25 }, { 14, 23 }, 
         { 15, 22 }, { 16, 11 }, { 17, 7 }, 
         { 18, 10 }, { 19, 3 },  { 20, 21 }, 
         { 21, 20 }, { 22, 15 }, { 23, 14 }, 
         { 24, 9 },  { 25, 13 } };

/**
* Stationary rotar at the beginning of the rotor array.
*/
private static final int[][] ROT_STAT = { { 0, 16 },  { 1, 22 },  { 2, 4 }, 
          { 3, 17 },  { 4, 19 },  { 5, 25 }, 
          { 6, 20 },  { 7, 8 },   { 8, 14 }, 
          { 9, 0 },   { 10, 18 }, { 11, 3 }, 
          { 12, 5 },  { 13, 6 },  { 14, 7 }, 
          { 15, 9 },  { 16, 10 }, { 17, 15 }, 
          { 18, 24 }, { 19, 23 }, { 20, 2 }, 
          { 21, 21 }, { 22, 1 },  { 23, 13 }, 
          { 24, 12 }, { 25, 11 } };
   
}
