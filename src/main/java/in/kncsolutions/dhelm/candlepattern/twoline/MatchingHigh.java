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
package in.kncsolutions.dhelm.candlepattern.twoline;
import java.util.*; 
import in.kncsolutions.dhelm.candlebasic.*;
import in.kncsolutions.dhelm.exceptions.DataException;
import in.kncsolutions.dhelm.mathcal.Mathfns;
/**
*According to literature the matching high is a two line pattern composed of  a long White candle with no upper shadow
*followed by a White  candle with no upper shadow, where the closing prices of both the candles are equal.
*The opening price of the latter candle should be higher than the opening price of the previous candle.Prior trend should be uptrend.
*/
public class MatchingHigh{
private List<Double> Open=new ArrayList<Double>();
private List<Double> High=new ArrayList<Double>();
private List<Double> Low=new ArrayList<Double>();
private List<Double> Close=new ArrayList<Double>();
private int RefLength;
private int RefTrend;;
private double Percentage;
private double []trendPrior={0,0};
private boolean isMatchingHigh;
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
public MatchingHigh(List<Double> open,List<Double> high,List<Double> low,List<Double> close,int numLen,int numTrend,double percentage)throws DataException{
  Open.addAll(open);
  High.addAll(high);
  Low.addAll(low);
  Close.addAll(close);
  RefLength=numLen;
  RefTrend=numTrend;
  Percentage=percentage;
  isMatchingHigh=false;
  if(Open.size()<((RefTrend+2>RefLength) ? RefTrend+2 : RefLength)
      || (Open.size()!=Close.size() || Open.size()!=High.size() || Open.size()!=Low.size() )){
    throw new DataException();
  }
  else{
	MatchingHigh();
  }
}
/*
*
*/
private void MatchingHigh()throws DataException{  
    if((new WhiteMarubozu(Open,High,Low,Close)).isWhiteMarubozu() 
	  || (new ClosingWhiteMarubozu(Open,High,Low,Close)).isClosingWhiteMarubozu()){	  
	  if((new WhiteMarubozu(Open.subList(1,Open.size()),High.subList(1,High.size()),Low.subList(1,Low.size()),Close.subList(1,Close.size()))).isLongWhiteMarubozu(RefLength,Percentage)
	  || (new ClosingWhiteMarubozu(Open.subList(1,Open.size()),High.subList(1,High.size()),Low.subList(1,Low.size()),Close.subList(1,Close.size()))).isLongClosingWhiteMarubozu(RefLength,Percentage)){
	    if(Close.get(0)==Close.get(1) && Open.get(0)>Open.get(1)){
		  if(trendPrior[0]<0)
		   isMatchingHigh=true;
		}
	  
	  }
	  
  }
}

/**
*@return Returns true if the matching low pattern is generated. 
*/
public boolean isMatchingHigh(){
  return isMatchingHigh;
}
}