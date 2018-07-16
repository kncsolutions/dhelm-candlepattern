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
 import in.kncsolutions.dhelm.mathcal.CandleFacts;
 /**
 *According to literature the one candle shooting star is a single line pattern composed of
 *a  white or black candle with small body. There should be no or very small lower shadow. The upper shadow should be at least two times
 *longer than the body. The candle appears as a long candle. The prior trend should be up trend.
 */
 public class OneCandleShootingStar{
   private List<Double> Open=new ArrayList<Double>();
   private List<Double> High=new ArrayList<Double>();
   private List<Double> Low=new ArrayList<Double>();
   private List<Double> Close=new ArrayList<Double>();
   private int RefLength;
   private int RefTrend;;
   private double Percentage;
   private double []trendPrior={0,0};
   private boolean isOneCandleShootingStar;
   /**
  *@param open : List of opening prices where first element is the latest data.
  *@param high : List of high prices where first element is the latest data.
  *@param low : List of low prices where first element is the latest data.
  *@param close : List of closing prices where first element is the latest data.
  *@param numLen : Number of previous periods w.r.t which it is to be found that if the latest candle is long or short.
  *@param numTrend : Number of previous periods w.r.t which the previous trend have to be approximated.
  *@param percentage : The percentage by which if the latest candle is longer than the previous candles, it will be treated as a long candle, otherwise short. 
  *@throws DataException if sufficient data not available.
  */
   public OneCandleShootingStar(List<Double> open,List<Double> high,List<Double> low,List<Double> close,int numLen,int numTrend,double percentage)throws DataException{
    Open.addAll(open);
	 High.addAll(high);
	 Low.addAll(low);
	 Close.addAll(close);
	 RefLength=numLen;
	 RefTrend=numTrend;
	 Percentage=percentage;
	 isOneCandleShootingStar=false;
	 if(Open.size()<((RefTrend+1>RefLength) ? RefTrend+1 : RefLength)
        || (Open.size()!=Close.size() || Open.size()!=High.size() || Open.size()!=Low.size() )){
	   throw new DataException();
	 }
	 else{
	   OneCandleShootingStar();
	 }
   }
   /*
   *
   */
   private void OneCandleShootingStar()throws DataException{
     double lShadow=Math.abs(Math.min(Open.get(0),Close.get(0))-Low.get(0));
     double uShadow=Math.abs(High.get(0)-Math.max(Open.get(0),Close.get(0)));
     double body=Math.abs(Open.get(0)-Close.get(0));
	 double avgCandleLength=CandleFacts.getAverageLength(High,Low,RefLength); 
	 if(lShadow<body && uShadow/body>=2){
	   if((High.get(0)-Low.get(0))>avgCandleLength*Percentage/100){
	     trendPrior=Mathfns.ComputeTrendLine(Close.subList(1,RefTrend+1),Close.subList(1,RefTrend+1).size());
		 if(trendPrior[0]<0)isOneCandleShootingStar=true;
	   }
	 }
   }
   /**
   *@return Returns true if the OneCandleShootingStar pattern is generated. 
   */
   public boolean isOneCandleShootingStar(){
     return isOneCandleShootingStar;
   }
   /**
   *@return Returns true if the OneCandleShootingStar pattern is generated. 
   */
   public boolean isLong(){
     if(isOneCandleShootingStar)return true;
	 return false;
   }
   /**
   *@return Always returns false
   */
   public boolean isShort(){
	 return false;
   }
 }