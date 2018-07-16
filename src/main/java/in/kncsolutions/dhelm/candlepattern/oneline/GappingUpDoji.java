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
 *According to literature the gapping up pattern is a single line pattern composed of
 *a  doji candle. The candle can be any doji candle except four price doji. The prior trend should be up trend.
 *The low of the doji candle should be higher than the high of the previous candle.
 */
 public class GappingUpDoji{
   private List<Double> Open=new ArrayList<Double>();
   private List<Double> High=new ArrayList<Double>();
   private List<Double> Low=new ArrayList<Double>();
   private List<Double> Close=new ArrayList<Double>();
   private int RefTrend;
   private double []trendPrior={0,0};
   private boolean isGappingUpDoji;   
   /**
  *@param open : List of opening prices where first element is the latest data.
  *@param high : List of high prices where first element is the latest data.
  *@param low : List of low prices where first element is the latest data.
  *@param close : List of closing prices where first element is the latest data.
  *@param numTrend : Number of previous periods w.r.t which the previous trend have to be approximated.
  *@throws DataException if sufficient data not available.
  */
   public GappingUpDoji(List<Double> open,List<Double> high,List<Double> low,List<Double> close,int numTrend)throws DataException{
     Open.addAll(open);
	 High.addAll(high);
	 Low.addAll(low);
	 Close.addAll(close);
	 RefTrend=numTrend;
	 isGappingUpDoji=false;
	 if(Open.size()<(RefTrend+1) || Open.size()<2
        || (Open.size()!=Close.size() || Open.size()!=High.size() || Open.size()!=Low.size() )){
	   throw new DataException();
	 }
	 else{
	   GappingUpDoji();
	 }
   }
   /*
   *
   */
   private void GappingUpDoji(){
     if((Open.get(0)==Close.get(0))
	    && (Low.get(0)!=Open.get(0) || High.get(0)!=Open.get(0))){
		trendPrior=Mathfns.ComputeTrendLine(Close.subList(1,RefTrend+1),Close.subList(1,RefTrend+1).size());
	    if(trendPrior[0]<0 && Low.get(0)>High.get(1)){
		  isGappingUpDoji=true;
		}
	 }
   }
   /**
   *@return Returns true if the Gapping Up doji pattern is generated. 
   */
   public boolean isGappingUpDoji(){
     return isGappingUpDoji;
   }
 }