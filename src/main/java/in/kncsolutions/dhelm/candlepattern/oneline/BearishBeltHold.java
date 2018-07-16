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
 import in.kncsolutions.dhelm.mathcal.Mathfns;
 /**
 *According to literature the Bearish belthold pattern is a single line pattern composed of
 *a opening black marubozu basic candle. It has a long body. Here the lower shadow should be significantly smaller than
 *the body. Important parameter of this pattern is the fact that the lower shadow should be how much smaller
 *than the body.
 */
 
 public class BearishBeltHold{
   private List<Double> Open=new ArrayList<Double>();
   private List<Double> High=new ArrayList<Double>();
   private List<Double> Low=new ArrayList<Double>();
   private List<Double> Close=new ArrayList<Double>();
   private int RefLength;
   private int RefTrend;
   private double Percentage1;
   private double Percentage2;
   private double []trendPrior={0,0};
   private boolean isBearishBeltHold;
  /**
  *@param open : List of opening prices where first element is the latest data.
  *@param high : List of high prices where first element is the latest data.
  *@param low : List of low prices where first element is the latest data.
  *@param close : List of closing prices where first element is the latest data.
  *@param numLen :  Number of previous periods w.r.t which it is to be found that if the latest candle is long or short.
  *@param numTrend : Number of previous periods w.r.t which the previous trend have to be approximated.
  *@param p1 : The percentage by which if the latest candle is longer than the previous candles, it will be treated as a long candle, otherwise short.
  *@param p2 : The percentage by which the lower shadow should be smaller than the body in case the latest candle is a opening black marubozu candle.
  *@throws DataException if sufficient data not available.
  */
   public BearishBeltHold(List<Double> open,List<Double> high,List<Double> low,List<Double> close, int numLen,int numTrend, double p1,double p2)throws DataException{
    Open.addAll(open);
	 High.addAll(high);
	 Low.addAll(low);
	 Close.addAll(close);
	 RefLength=numLen;
	 RefTrend=numTrend;
	 Percentage1=p1;
	 Percentage2=p2;
	 isBearishBeltHold=false;
	 if(Open.size()<RefLength
        || (Open.size()!=Close.size() || Open.size()!=High.size() || Open.size()!=Low.size() )){
	   throw new DataException();
	 }
	 else{
	   BearishBeltHold();
	 }
   }
   /*
   *
   */
   private void BearishBeltHold()throws DataException{
     if((new OpeningBlackMarubozu(Open,High,Low,Close)).isLongOpeningBlackMarubozu(RefLength,Percentage1)){
	   if((Close.get(0)-Low.get(0))/(Open.get(0)-Close.get(0))<=(Percentage2/100.00)){
	     trendPrior=Mathfns.ComputeTrendLine(Close.subList(1,RefTrend+1),Close.subList(1,RefTrend+1).size());
	     if(trendPrior[0]<0)isBearishBeltHold=true;
	   }
	 }
   }
   /**
   *@return Returns true if the Bearish belthold pattern is generated. 
   */
   public boolean isBearishBeltHold(){
     return isBearishBeltHold;
   }
 }