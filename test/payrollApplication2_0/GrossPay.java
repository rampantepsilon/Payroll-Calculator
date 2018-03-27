package payrollApplication2_0;

/**
 *
 * @author Thomas Ware
 */

public class GrossPay {
    private double payRate;
    private double hours;
    private double hoursReg;
    private double overtimePay;
    private double grossPay;
    private double overtimeHours;
    
    public GrossPay(double inPayRate, double inHours, double inHoursReg){        
        payRate = inPayRate;
        hours = inHours;
        hoursReg = inHoursReg;
    
        if (hours > hoursReg){
            overtimeHours = hours - hoursReg;
        }
        else {
            overtimeHours = 0;
        }
        overtimePay = (1.5 * payRate)*overtimeHours;
        if (overtimePay > 0){
            grossPay = (payRate * hoursReg);
        }
        else {
            grossPay = (payRate * hours);
        }
        grossPay = grossPay + overtimePay;
        grossPay = grossPay * 100;
        grossPay = Math.round(grossPay);
        grossPay = grossPay/100;
        
        overtimePay = overtimePay * 100;
        overtimePay = Math.round(overtimePay);
        overtimePay = overtimePay/100;
    }
    public double getGrossPay(){
        return grossPay;
    }
    public double getOvertimePay(){
        return overtimePay;
    }
}
