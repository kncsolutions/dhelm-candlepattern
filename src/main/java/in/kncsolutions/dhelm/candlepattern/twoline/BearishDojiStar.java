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
*According to literature the bearish doji star pattern is a two line pattern composed of a
*long candle with White body followed by a doji candle with atleast one shadows, where the body of doji candle is above the body of the previous White candle.
*/
public class BearishDojiStar{
private List<Double> Open=new ArrayList<Double>();
private List<Double> High=new ArrayList<Double>();
private List<Double> Low=new ArrayList<Double>();
private List<Double> Close=new ArrayList<Double>();
private int RefBody;
private int RefLength;
private int RefTrend;;
private double Percentage;
private double []trendPrior={0,0};
private boolean isBearishDojiStar;
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
public BearishDojiStar(List<Double> open,List<Double> high,List<Double> low,List<Double> close,int numBody,int numLen,int numTrend,double percentage)throws DataException{
  Open.addAll(open);
  High.addAll(high);
  Low.addAll(low);
  Close.addAll(close);
  RefBody=numBody;
  RefLength=numLen;
  RefTrend=numTrend;
  Percentage=percentage;
  isBearishDojiStar=false;
  if(Open.size()<((RefTrend+2>RefLength) ? RefTrend+2 : RefLength)
      || (Open.size()!=Close.size() || Open.size()!=High.size() || Open.size()!=Low.size() )){
    throw new DataException();
  }
  else{
	BearishDojiStar();
  }
}
/*
*
*/
private void BearishDojiStar()throws DataException{
  if((Close.get(0)==Open.get(0)) && (Low.get(0)!=Open.get(0) || High.get(0)!=Close.get(0))){
    if((Close.get(1)>Open.get(1)) && validBasicCandle()){
	 if( Close.get(0)>Close.get(1)){
	   trendPrior=Mathfns.ComputeTrendLine(Close.subList(2,RefTrend+2),Close.subList(2,RefTrend+2).size());
	     if(trendPrior[0]<0)
		   isBearishDojiStar=true;
	 }
	}
  }
}
/*
*
*/
private boolean validBasicCandle()throws DataException{
  if((new WhiteCandle(Open.subList(1,Open.size()),High.subList(1,High.size()),Low.subList(1,Low.size()),Close.subList(1,Close.size()),RefBody,RefLength,Percentage)).isWhiteCandle()
    || (new LongWhiteCandle(Open.subList(1,Open.size()),High.subList(1,High.size()),Low.subList(1,Low.size()),Close.subList(1,Close.size()),RefBody,RefLength,Percentage)).isLongWhiteCandle() 
	 || (new WhiteMarubozu(Open.subList(1,Open.size()),High.subList(1,High.size()),Low.subList(1,Low.size()),Close.subList(1,Close.size()))).isLongWhiteMarubozu(RefLength,Percentage)
	  || (new ClosingWhiteMarubozu(Open.subList(1,Open.size()),High.subList(1,High.size()),Low.subList(1,Low.size()),Close.subList(1,Close.size()))).isLongClosingWhiteMarubozu(RefLength,Percentage)
	   || (new OpeningWhiteMarubozu(Open.subList(1,Open.size()),High.subList(1,High.size()),Low.subList(1,Low.size()),Close.subList(1,Close.size()))).isLongOpeningWhiteMarubozu(RefLength,Percentage)){
	   return true;
  }
  return false;
}
/**
*@return Returns true if the bullish harami pattern is generated. 
*/
public boolean isBearishDojiStar(){
  return isBearishDojiStar;
}
}