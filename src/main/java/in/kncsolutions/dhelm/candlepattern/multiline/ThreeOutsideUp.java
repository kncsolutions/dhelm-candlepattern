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
import in.kncsolutions.dhelm.candlepattern.twoline.BullishEngulfing;
/**
*According to literature the three outside up  pattern is a three line pattern composed of a
*bullish engulfing pattern followed by a white candle , where the closing of the  white candle is above the closing of the
*second candle(which is a white candle) of the previous bullish engulfing pattern.
*/
public class ThreeOutsideUp{
private List<Double> Open=new ArrayList<Double>();
private List<Double> High=new ArrayList<Double>();
private List<Double> Low=new ArrayList<Double>();
private List<Double> Close=new ArrayList<Double>();
private int RefBody;
private int RefLength;
private int RefTrend;;
private double Percentage;
private double []trendPrior={0,0};
private boolean isThreeOutsideUp;
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
public ThreeOutsideUp(List<Double> open,List<Double> high,List<Double> low,List<Double> close,int numBody,int numLen,int numTrend,double percentage)throws DataException{
  Open.addAll(open);
  High.addAll(high);
  Low.addAll(low);
  Close.addAll(close);
  RefBody=numBody;
  RefLength=numLen;
  RefTrend=numTrend;
  Percentage=percentage;
  isThreeOutsideUp=false;
  if(Open.size()<((RefTrend+3>RefLength) ? RefTrend+3 : RefLength)
      || (Open.size()!=Close.size() || Open.size()!=High.size() || Open.size()!=Low.size() )){
    throw new DataException();
  }
  else{
	ThreeOutsideUp();
  }
}
/*
*
*/
private void ThreeOutsideUp()throws DataException{
  if((Close.get(0)>Open.get(0))){
    if(validCandlePattern()){
	    if(Close.get(0)>Close.get(1)){
	      trendPrior=Mathfns.ComputeTrendLine(Close.subList(3,RefTrend+3),Close.subList(3,RefTrend+3).size());
	      if(trendPrior[0]>0)
		    isThreeOutsideUp=true;
	    }	  
	}
  }
}
/*
*
*/
private boolean validCandlePattern()throws DataException{
if((new BullishEngulfing(Open.subList(1,Open.size()),High.subList(1,High.size()),Low.subList(1,Low.size()),Close.subList(1,Close.size()),RefBody,RefLength,RefTrend,Percentage)).isBullishEngulfing()){
  return true;
}
  return false;
}
/**
*@return Returns true if the three outside up pattern is generated. 
*/
public boolean isThreeOutsideUp(){
  return isThreeOutsideUp;
}
}