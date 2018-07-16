package in.kncsolutions.dhelm.candlepattern.oneline;

import org.junit.Assert;
import org.junit.Test;
import java.util.*;
import in.kncsolutions.dhelm.exceptions.DataException; 
/**
 * Unit test for BullishBeltHold.class.
 */
public class BullishBeltHoldTest 
{
    public List<Double> open=new <Double>ArrayList();
	public List<Double> high=new <Double>ArrayList();
	public List<Double> low=new <Double>ArrayList();
	public List<Double> close=new <Double>ArrayList();
	final int DATASIZE=200;
	/**
	*
	*/
	private  void generateData(int ll,int ul,int SIZE){
	    double rangeMin=ll;
	    double rangeMax=ul;
		Random rand=new Random();
	    for(int i=0;i<SIZE;i++){		   
		    open.add(rangeMin + (rangeMax - rangeMin) * rand.nextDouble());
			high.add(rangeMin + (rangeMax - rangeMin) * rand.nextDouble());
			low.add(rangeMin + (rangeMax - rangeMin) * rand.nextDouble());
			close.add(rangeMin + (rangeMax - rangeMin) * rand.nextDouble());
		}
	}
	@Test
	  public void test() {	     
	      for(int i=0;i<10;i++){
		    open.clear();
			high.clear();
			low.clear();
			close.clear();
			generateData(250*(i+1),260*(i+1),(int)DATASIZE/(i+1));
			try{
			   BullishBeltHold  l=new BullishBeltHold(open,high,low,close,25,25,70+i,25);
			   boolean isL=l.isBullishBeltHold();
			   System.out.println("The pattern is Bullish Belthold : "+ isL);
			}
			catch(DataException e){
			  e.printStackTrace();
			}
			
		  }
	   }
}
