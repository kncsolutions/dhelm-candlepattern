/**
 *Copyright 2018 Knc Solutions Private Limited

  *Licensed under the Apache License, Version 2.0 (the "License");
  *you may not use this file except in compliance with the License.
  *You may obtain a copy of the License at

  * http://www.apache.org/licenses/LICENSE-2.0

  *Unless required by applicable law or agreed to in writing, software
  *distributed under the License is distributed on an "AS IS" BASIS,
  *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  *See the License for the specific language governing permissions and
  *limitations under the License.
 */
 package in.kncsolutions.dhelm.candlepattern.oneline;
 import java.util.*; 
 import in.kncsolutions.dhelm.candlebasic.*;
 import in.kncsolutions.dhelm.exceptions.DataException;
 import in.kncsolutions.dhelm.mathcal.CandleFacts;
 /**
 *According to literature the Bearish strong line pattern is a single line pattern composed of
 *a  black candle. It has a long body. The shadows are not required. However if the shadows exist, none of the shadows can be
 *greater than the body. The candle body must be atleast three times greater than average body length of certain previous candles.  
 */
 
 public class BearishStrongLine{
   private List<Double> Open=new ArrayList<Double>();
   private List<Double> High=new ArrayList<Double>();
   private List<Double> Low=new ArrayList<Double>();
   private List<Double> Close=new ArrayList<Double>();
   private int RefBody;
   private int RefLength;
   private double Percentage;
   private boolean isBearishStrongLine;
  /**
  *@param open : List of opening prices where first element is the latest data.
  *@param high : List of high prices where first element is the latest data.
  *@param low : List of low prices where first element is the latest data.
  *@param close : List of closing prices where first element is the latest data.
  *@param numBody : Number of previous periods w.r.t which it is to be found that if the latest candle's body  is greater than the average body length of those past consecutive periods or not. 
  *@param numLen :  Number of previous periods w.r.t which it is to be found that if the latest candle is long or short.
  *@param percentage : The percentage by which if the latest candle is longer than the previous candles, it will be treated as a long candle, otherwise short.
  *@throws DataException if sufficient data not available.
  */
   public BearishStrongLine(List<Double> open,List<Double> high,List<Double> low,List<Double> close, int numBody, int numLen, double percentage)throws DataException{
     Open.addAll(open);
	 High.addAll(high);
	 Low.addAll(low);
	 Close.addAll(close);
	 RefLength=numBody;
	 RefLength=numLen;
	 Percentage=percentage;
	 isBearishStrongLine=false;
	 if(Open.size()<((RefBody>RefLength) ? RefBody : RefLength)
        || (Open.size()!=Close.size() || Open.size()!=High.size() || Open.size()!=Low.size() )){
	   throw new DataException();
	 }
	 else{
	   BearishStrongLine();
	 }
   }
   /*
   *
   */
   private void BearishStrongLine()throws DataException{
     double avgBodyLength=CandleFacts.getAverageLength(Open,Close,RefBody); 
     if((new OpeningBlackMarubozu(Open,High,Low,Close)).isLongOpeningBlackMarubozu(RefLength,Percentage)
	      || (new ClosingBlackMarubozu(Open,High,Low,Close)).isLongClosingBlackMarubozu(RefLength,Percentage)
		   || (new BlackMarubozu(Open,High,Low,Close)).isLongBlackMarubozu(RefLength,Percentage)
		    || (new LongBlackCandle(Open,High,Low,Close,RefBody,RefLength,Percentage)).isLongBlackCandle()){
	   if(Open.get(0)-Close.get(0)>=3*avgBodyLength){
	     isBearishStrongLine=true;
	   }
	 }
   }
   /**
   *@return Returns true if the Bearish strong line pattern is generated. 
   */
   public boolean isBearishStrongLine(){
     return isBearishStrongLine;
   }
 }