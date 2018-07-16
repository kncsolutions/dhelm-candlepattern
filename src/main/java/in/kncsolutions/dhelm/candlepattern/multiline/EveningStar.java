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
package in.kncsolutions.dhelm.candlepattern.multiline;
import java.util.*; 
import in.kncsolutions.dhelm.candlebasic.*;
import in.kncsolutions.dhelm.exceptions.DataException;
import in.kncsolutions.dhelm.mathcal.Mathfns;
import in.kncsolutions.dhelm.mathcal.CandleFacts;
/**
*According to literature the evening  star  pattern is a three line pattern composed of a
*long candle with white body followed by a white or black short candle, followed by a long black candle, where the body of the  middle candle is above the closing of the
*previous white candle and the following black candle's opening is lower than the body of the middle candle. The closing of the black candle should be below the mid point of the body of the 
*first white candle . The prior trend should be up trend.
*/
public class EveningStar{
private List<Double> Open=new ArrayList<Double>();
private List<Double> High=new ArrayList<Double>();
private List<Double> Low=new ArrayList<Double>();
private List<Double> Close=new ArrayList<Double>();
private int RefBody;
private int RefLength;
private int RefTrend;;
private double Percentage;
private double []trendPrior={0,0};
private boolean isEveningStar;
/**
*@param open : List of opening prices where first element is the latest data.
*@param high : List of high prices where first element is the latest data.
*@param low : List of low prices where first element is the latest data.
*@param close : List of closing prices where first element is the latest data.
*@param numBody : Number of previous periods w.r.t which it is to be found that if the latest candle's body  is greater than the average body length of those past consecutive periods or not. 
*@param numLen : Number of previous periods w.r.t which it is to be found that if the latest candle is long or short.
*@param numTrend : Number of previous periods w.r.t which the previous trend have to be approximated.
*@param percentage : The percentage by which if the latest candle is longer than the previous candles, it will be treated as a long candle, otherwise short. 
*@throws DataException if sufficient data not available.
*/
public EveningStar(List<Double> open,List<Double> high,List<Double> low,List<Double> close,int numBody,int numLen,int numTrend,double percentage)throws DataException{
  Open.addAll(open);
  High.addAll(high);
  Low.addAll(low);
  Close.addAll(close);
  RefBody=numBody;
  RefLength=numLen;
  RefTrend=numTrend;
  Percentage=percentage;
  isEveningStar=false;
  if(Open.size()<((RefTrend+3>RefLength) ? RefTrend+3 : RefLength)
      || (Open.size()!=Close.size() || Open.size()!=High.size() || Open.size()!=Low.size() )){
    throw new DataException();
  }
  else{
	EveningStar();
  }
}
/*
*
*/
private void EveningStar()throws DataException{
  if((Close.get(0)<Open.get(0)) && validBasicCandle(1)){
    if((Close.get(1)!=Open.get(1)) && isShort()){
	  if((Close.get(2)>Open.get(2)) && validBasicCandle(3)){
	    if(Open.get(0)<Math.min(Close.get(1),Open.get(1)) && Math.min(Close.get(1),Open.get(1))>Close.get(2) && Close.get(0)<(Open.get(2)+Close.get(2))/2){
	      trendPrior=Mathfns.ComputeTrendLine(Close.subList(3,RefTrend+3),Close.subList(3,RefTrend+3).size());
	      if(trendPrior[0]<0)
		    isEveningStar=true;
	    }
	  }
	}
  }
}
/*
*
*/
private boolean validBasicCandle(int i)throws DataException{
if(i==3 && (new WhiteCandle(Open,High,Low,Close,RefBody,RefLength,Percentage)).isWhiteCandle()
    || (new LongWhiteCandle(Open,High,Low,Close,RefBody,RefLength,Percentage)).isLongWhiteCandle() 
	 || (new WhiteMarubozu(Open,High,Low,Close)).isLongWhiteMarubozu(RefLength,Percentage)
	  || (new ClosingWhiteMarubozu(Open,High,Low,Close)).isLongClosingWhiteMarubozu(RefLength,Percentage)
	   || (new OpeningWhiteMarubozu(Open,High,Low,Close)).isLongOpeningWhiteMarubozu(RefLength,Percentage)){
	   return true;
  }
  else if(i==1 && (new BlackCandle(Open.subList(2,Open.size()),High.subList(2,High.size()),Low.subList(2,Low.size()),Close.subList(2,Close.size()),RefBody,RefLength,Percentage)).isBlackCandle()
    || (new LongBlackCandle(Open.subList(2,Open.size()),High.subList(2,High.size()),Low.subList(2,Low.size()),Close.subList(2,Close.size()),RefBody,RefLength,Percentage)).isLongBlackCandle() 
	 || (new BlackMarubozu(Open.subList(2,Open.size()),High.subList(2,High.size()),Low.subList(2,Low.size()),Close.subList(2,Close.size()))).isLongBlackMarubozu(RefLength,Percentage)
	  || (new ClosingBlackMarubozu(Open.subList(2,Open.size()),High.subList(2,High.size()),Low.subList(2,Low.size()),Close.subList(2,Close.size()))).isLongClosingBlackMarubozu(RefLength,Percentage)
	   || (new OpeningBlackMarubozu(Open.subList(2,Open.size()),High.subList(2,High.size()),Low.subList(2,Low.size()),Close.subList(2,Close.size()))).isLongOpeningBlackMarubozu(RefLength,Percentage)){
	   return true;
  }
  
  return false;
}
/*
*
*/
private boolean isShort()throws DataException{
 double avgCandleLength=CandleFacts.getAverageLength(High.subList(1,High.size()),Low.subList(1,Low.size()),RefLength); 
  if((High.get(1)-Low.get(1))<=avgCandleLength*Percentage/100)return true;
  return false;
}
/**
*@return Returns true if the evening  star pattern is generated. 
*/
public boolean isEveningStar(){
  return isEveningStar;
}
}