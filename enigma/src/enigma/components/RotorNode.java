package enigma.components;

public class RotorNode
{
   /**
    * 
    */
   private Integer theWiredToNode;
   
   /**
    * 
    */
   private Integer theWiredFromNode;
   
   /**
    * Default constructor.
    */
   public RotorNode()
   {
      theWiredToNode = null;
      theWiredFromNode = null;
      return;
   }
   
   /**
    * 
    * @param inData
    */
   public RotorNode( final int inWiredTo )
   {
      theWiredToNode = inWiredTo;
      return;
   }
   
   /**
    * 
    * @return
    */
   public Integer getWiredFrom()
   {
      return theWiredFromNode;
   }

   /**
    * 
    * @param inWiredFrom
    */
   public void setWiredFrom( final int inWiredFrom )
   {
      theWiredFromNode = inWiredFrom;
   }

   /**
    * 
    * @return
    */
   public Integer getWiredTo()
   {
      return theWiredToNode;
   }

   /**
    * 
    * @param inWiredTo
    */
   public void setWiredTo( final int inWiredTo )
   {
      theWiredToNode = inWiredTo;
   }
}
