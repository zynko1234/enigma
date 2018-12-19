package utility;

public class Pair <K, V>
{
   private K first;
   
   private V second;
   
   public Pair()
   {
      first = null;
      second = null;
      return;
   }
   
   public Pair( final K inValA, final V inValB )
   {
      first = inValA;
      second = inValB;
      return;
   }
   
   public K GetFirst()
   {
      return first;
   }
   
   public V GetSecond()
   {
      return second;
   }
   
   public void SetFirst( final K inVal )
   {
      first = inVal;
      return;
   }
   
   public void SetSecond( final V inVal )
   {
      second = inVal;
      return;
   }
   
   
}
