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
*According to literature the bearish kicking pattern is a two line pattern composed of a a white marubozu candle
*followed by a black marubozu candle, where there is a gap in the downward direction at the opening in the black marubozu candle. Both
*the candles are long candles.
*/
public class BearishKicking{
private List<Double> Open=new ArrayList<Double>();
private List<Double> High=new ArrayList<Double>();
private List<Double> Low=new ArrayList<Double>();
private List<Double> Close=new ArrayList<Double>();
private int RefLength;
private int RefTrend;;
private double Percentage;
private double []trendPrior={0,0};
private boolean isBearishKicking;
private boolean inUpTrend;
private boolean inDownTrend;
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
public BearishKicking(List<Double> open,List<Double> high,List<Double> low,List<Double> close,int numLen,int numTrend,double percentage)throws DataException{
  Open.addAll(open);
  High.addAll(high);
  Low.addAll(low);
  Close.addAll(close);
  RefLength=numLen;
  RefTrend=numTrend;
  Percentage=percentage;
  isBearishKicking=false;
  inUpTrend=false;
  inDownTrend=false;
  if(Open.size()<((RefTrend+2>RefLength) ? RefTrend+2 : RefLength)
      || (Open.size()!=Close.size() || Open.size()!=High.size() || Open.size()!=Low.size() )){
    throw new DataException();
  }
  else{
	BearishKicking();
  }
}
/*
*
*/
private void BearishKicking()throws DataException{  
    if((new BlackMarubozu(Open,High,Low,Close)).isLongBlackMarubozu(RefLength,Percentage) 
	  && (new WhiteMarubozu(Open.subList(1,Open.size()),High.subList(1,High.size()),Low.subList(1,Low.size()),Close.subList(1,Close.size()))).isLongWhiteMarubozu(RefLength,Percentage)){
	 if(Open.get(0)<Open.get(1)){
	   trendPrior=Mathfns.ComputeTrendLine(Close.subList(2,RefTrend+2),Close.subList(2,RefTrend+2).size());
	   isBearishKicking=true;
	   if(trendPrior[0]<0)inUpTrend=true;
	   if(trendPrior[0]>0)inDownTrend=true;
	 }
  }
}

/**
*@return Returns true if the bearish kicking pattern is generated. 
*/
public boolean isBearishKicking(){
  return isBearishKicking;
}
/**
*@return Returns true if the bearish kicking pattern is generated in up trend. 
*/
public boolean inUpTrend(){
  if(isBearishKicking)return inUpTrend;
  return false;
}
/**
*@return Returns true if the bearish kicking pattern is generated in down trend. 
*/
public boolean inDownTrend(){
  if(isBearishKicking)return inDownTrend;
  return false;
}
}