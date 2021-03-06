/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jevis.jecommons.dataprocessing;

import java.text.ParseException;
import java.util.List;
import org.jevis.api.JEVisAttribute;
import org.jevis.api.JEVisException;
import org.jevis.api.JEVisSample;
import org.joda.time.DateTime;

/**
 *
 * @author gf
 */
public interface DataCalculation {
    
    /*
     * @param samples one sampled data row of typ JEVisAttribute to be calculated
     * @param value one value of typ double to be added
     * @return
     */
    List<JEVisSample> addition(List<JEVisSample> samples, double value) throws JEVisException;

    /*
     * @param attributes the infinite many sampled data rows of typ JEVisAttribute to be calculated
     * @return
     */
    public List<JEVisSample> addition(List<List<JEVisSample>> attributes) throws JEVisException;
                     
    /*
    the combination of high pass filter und low pass filter
    The input parameter „delete“ decides, whether the improper values 
    will be deleted or replaced by  the upper limit and  the lower limit.
    * @param samples one sampled data row of typ JEVisAttribute to be calculated
    * @param boundary_up the upper limit
    * @param boundary_low the lower limit
    * @param delete decides, whether the improper values will be deleted or replaced by  the upper limit and  the lower limit.(delete=true, delete; delete=false, replace)
    * @return
    */
    public List<JEVisSample> boundaryFilter(List<JEVisSample> samples, double boundary_up, double boundary_low, boolean delete) throws JEVisException;

    /*
    * @param samples one sampled data row of typ JEVisAttribute to be calculated
    * @return
    */
    public List<JEVisSample> cumulativeDifferentialConverter(List<JEVisSample> samples) throws JEVisException;

    /*
     * 1. the over boundary value will not be stored, and become a gap.  no
     * 2. the over boundary value will replaced by boundary.
     * 3. the over boundary value will replaced by 0 or any other value.
     * here choose 2
     * @param samples one sampled data row of typ JEVisAttribute to be calculated
     * @param boundary the upper limit
     * @return
     */
    public List<JEVisSample> highPassFilter(List<JEVisSample> samples, double boundary) throws JEVisException;

    /*
     * 1. the over boundary value will not be stored, and become a gap.  no
     * 2. the over boundary value will replaced by boundary.
     * 3. the over boundary value will replaced by 0 or any other value.
     * here choose 3
     * @param samples one sampled data row of typ JEVisAttribute to be calculated
     * @param boundary the upper limit
     * @param fill_value the value,that replaces the inproper values 
     * @return
     */
    public List<JEVisSample> highPassFilter(List<JEVisSample> samples, double boundary, double fill_value) throws JEVisException;

    /*
     * calculate the area between every two time points,and here the period
     * between every two time points must not be same.
     * And there could be sevrial methods to calculate the integration:vorwaerts,nachwaerts,trapezoid, 
     * here trapezoid is used.
     * @param samples one sampled data row of typ JEVisAttribute to be calculated
     * @return
     */
    public double integration(List<JEVisSample> samples) throws JEVisException;
    
    /*
     * eliminate the deviation of the timestamp(delay). 
     * the smallest unit of time,period_s and deviation_s is second
     * @param samples one sampled data row of typ JEVisAttribute to be calculated
     * @param period_s period of the sampling and it's units is second. 
     * @param deviation_s allowable deviation time and it's units second. 
     * @return
     */
    public List<JEVisSample> intervalAlignment(List<JEVisSample> samples, DateTime begin_time, int period_s, int deviation_s) throws JEVisException;

    /*
     * interpolation the whole data row, from begin to end
     * @param samples one sampled data row of typ JEVisAttribute to be calculated
     * @param insert_num decides,how many points will be added into every two time points in original data row.
     * @return
     */
    public List<JEVisSample> linearInterpolation(List<JEVisSample> samples, int insert_num) throws JEVisException;

    /*
     * calculate every value according to y=a*x+b
     * the gap is not taken into considered until now
     * @param samples one sampled data row of typ JEVisAttribute to be calculated
     * @param proportion 
     * @param b
     * @return
     */
    public List<JEVisSample> linearScaling(List<JEVisSample> samples, double proportion, double b) throws JEVisException;
    
    /*
    look for the median of one data row(JEVis variable). 
    * @param samples one sampled data row of typ JEVisAttribute to be calculated
    * @return
    */
    public double median(List<JEVisSample> samples) throws JEVisException;

    /*
     * merge the values at some timestamps to one timestamp, the values will be added. 
     * The input parameter „begin_time“ is the theoretic begin time(the first sampled time) of the data row. 
     * The input parameter „period_s“ should be time(period) and it's unit is  second. 
     * The last input parameter „meg_num“ means, how many sampled value will be merged.
     * (millinsecond for year is too lang,already beyound the int,so the smallest unit of time is here second.)
     * @param samples one sampled data row of typ JEVisAttribute to be calculated
     * @param begin_time the begintime
     * @param period_s period of the sampling and it's units is second. 
     * @param meg_num means, how many sampled value will be merged.
     * @return
     */
    public List<JEVisSample> mergeValues(List<JEVisSample> samples, DateTime begin_time, int period_s, int meg_num) throws JEVisException;

    /*
    delete the value,that is not bigger or smaller than it's previous value in one percentage value. 
    The inputparameter „percent“ is the percentage value, which is decided by enduser.
    * @param samples one sampled data row of typ JEVisAttribute to be calculated
    * @param percent the percentage value
    * @return
    */
    public List<JEVisSample> precisionFilter(List<JEVisSample> samples, double percent) throws JEVisException;

    /*
    sort the data row according to Time. The import parameter "order" decides form begin/end to end/begin.
    order= 1,begin to end
    order=-1,end to begin
    * @param samples one sampled data row of typ JEVisAttribute to be calculated
    * @param order decides,whether is from begen to end(1) or from end to begin(-1). The value of „order“ should be 1 or -1.
    * @return
    */
    public List<JEVisSample> sortByTime(List<JEVisSample> samples, int order);
    
    /*
    sort the data row according to value. The import parameter "order" decides form big/small to small/big.
    order= 1,small to big
    order=-1,big to small
    * @param samples one sampled data row of typ JEVisAttribute to be calculated
    * @param order decides,whether is from big to small(1) or from samll to big(-1). 
    * @return
    */
    public List<JEVisSample> sortByValue(List<JEVisSample> samples, int order);
    
    /*
     * only split the value as average, it's not komplete
     * @param samples one sampled data row of typ JEVisAttribute to be calculated
     * @param period_s period of the sampling and it's units is second.
     * @param seg_num means, how many time points will one sampled time point will be splitted into.(seg_num=1,one point becomes two points; seg_num=2,one point becomes three points; ......)
     * @return
     */
    public List<JEVisSample> splitValues(List<JEVisSample> samples, int period_s, int seg_num, Boolean forwardbackward) throws JEVisException;
    
    /*
    every value of the data row minus one value
    * @param samples one sampled data row of typ JEVisAttribute to be calculated
    * @param value one value of typ double to be subtracted
    * @return
    */
    public List<JEVisSample> subtraction(List<JEVisSample> samples, double value) throws JEVisException;

    /*
    the value in first data row minus the value in second data row with same time.
    the time punkts,that only the first data row has, it's value will be directly putted into the result.
    the time punkts,that only the second data row has, it's value will become negative and be putted into the result.
    * @param samples1 one sampled data row of typ JEVisAttribute to subtract
    * @param samples2 one sampled data row of typ JEVisAttribute to be subtracted
    * @return
    */
    public List<JEVisSample> subtraction(List<JEVisSample> samples1, List<JEVisSample> samples2) throws JEVisException;

    /*
     * output all minimum values with their time in the data row
     * @param samples one sampled data row of typ JEVisAttribute to be processed
     * @return
     */
    public List<JEVisSample> valueAllMinimum(List<JEVisSample> samples) throws JEVisException;

    /*
     * output only the minimum value in one data row
     * @param samples one sampled data row of typ JEVisAttribute to be processed
     * @return
     */
    public double valueMinimum(List<JEVisSample> samples) throws JEVisException;

    
    /*
    find the minimum value of multiple data rows and a value. 
    the multiple data rows must first be putted into a List, so that this
    function can compare endless more data rows.
    * @param attributes infinite many sampled data rows of typ JEVisAttribute to be processed
    * @return
    */
    public double valueMinimumMore(List<List<JEVisSample>> attributes) throws JEVisException;

    /*
    find the minimum value of multiple data rows and a value. 
    It firstly use the function:valueMinimum(List<JEVisAttribute> attributes) to find 
    the minimum value of multiple data rows, then compare it to the value.
    * @param attributes infinite many sampled data rows of typ JEVisAttribute to be processed
    * @param value one value of typ double to be compared
    * @return
    */
    public double valueMinimumMore(List<List<JEVisSample>> attributes,double value) throws JEVisException;
    
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public List<JEVisSample> multiplication(List<JEVisSample> samples1, List<JEVisSample> samples2) throws ParseException, JEVisException;
    
    public List<JEVisSample> division(List<JEVisSample> samples1, List<JEVisSample> samples2) throws ParseException, JEVisException;
    

    public double getAverageValue(List<JEVisSample> samples) throws JEVisException;
    

    public double getMaxValue(List<JEVisSample> samples) throws JEVisException;
    

//calculate the Mean Deviation    
    public double meanDeviation(List<JEVisSample> samples) throws JEVisException;
    
//add shifttime to original timeaxis
    public List<JEVisSample> addShiftTime(List<JEVisSample> samples, int shiftTime) throws ParseException, JEVisException;
     
//only the value which smaller than setNumber can be stored  
    public List<JEVisSample> lowPassFilter(List<JEVisSample> samples, double setNumber) throws ParseException, JEVisException;

//this function will considers the period as no matter how long you give    
    public List<JEVisSample> derivation(List<JEVisSample> samples, int period) throws ParseException, JEVisException;

    public List<JEVisSample> differentialCumulativeConverter(List<JEVisSample> samples) throws JEVisException;
}
