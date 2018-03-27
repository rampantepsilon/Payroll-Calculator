package payroll.application;

/**
 *
 * @author Thomas Ware
 */
import javax.swing.JFrame;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

public class PayrollApplication {
    public static void main (String [] args){
        
        //create JFrame element
        JFrame frame = new JFrame("Payroll Application v1.2");
        
        frame.setSize(500,300);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //create int for calculation & paySchedule
        int calculation = 0;
        int paySchedule;
        double hours;
        double payRate;
        String maritalValue = "Single";
        Scanner scan = new Scanner(System.in);
        
        //Set paySchedule value
        paySchedule = 0;
        
        //Determine which pay calculations to follow
        while (paySchedule == 0){
            String paySchedule2 = JOptionPane.showInputDialog(frame,"How often do you work? \n1 = Weekly\n2 = Bi-Weekly\nEnter your response");
            paySchedule = Integer.parseInt(paySchedule2);
            
            //Weekly Calculations
            if (paySchedule == 1){
                    
                //Ask for Hours
                String hours2 = JOptionPane.showInputDialog(frame,"Enter how many hours you've worked this pay period:");
                hours = Double.parseDouble(hours2);
                    
                //Ask for PayRate
                String payRate2 = JOptionPane.showInputDialog(frame,"Enter your hourly pay rate: ");
                payRate = Double.parseDouble(payRate2);
                    
                //Ask Marital Status
                int maritalStatus = 0;
                while (maritalStatus == 0){
                    String maritalStatus2 = JOptionPane.showInputDialog(frame,"Enter your marital status: (1 for Single, 2 for Married)");
                    maritalStatus = Integer.parseInt(maritalStatus2);}
                    if (maritalStatus > 2){
                        JOptionPane.showMessageDialog(frame,"Invalid Value");
                        maritalStatus = 0;
                    }
                    
                //Ask # of Dependents
                String dependants2 = JOptionPane.showInputDialog(frame,"Enter how many dependents you claim: ");
                int dependants = Integer.parseInt(dependants2);
                    
                //Ask for Pre-Tax Deductions
                String deductions2 = JOptionPane.showInputDialog(frame,"Enter any pre-tax deductions: ");
                double deductions = Double.parseDouble(deductions2);
                    
                //Set Hours for non-overtime
                double hoursReg = 40;
                    
                //Calculate Gross Pay for taxes
                GrossPay calc = new GrossPay(payRate, hours, hoursReg);
                double grossPay = calc.getGrossPay();
                double overtimePay = calc.getOvertimePay();
                double grossDeducted = grossPay - deductions;
                    
                //Calculate Federal Withholding
                FedWithholding fedWithholding = new FedWithholding(grossDeducted,dependants,paySchedule,maritalStatus);
                double fedWithholdingTax = fedWithholding.getFedWithholding();
                    
                //Calculate and Round SS Tax
                double ssTax = (grossDeducted*.062);
                ssTax = ssTax * 100;
                ssTax = Math.round(ssTax);
                ssTax = ssTax/100;
                    
                //Calculate and Round Medicare
                double medicareTax = (grossDeducted*.0145);
                medicareTax = medicareTax * 100;
                medicareTax = Math.round(medicareTax);
                medicareTax = medicareTax/100;
                    
                //Calculate and Round Net Pay
                double netPay = grossPay - deductions - fedWithholdingTax - ssTax - medicareTax;
                netPay = netPay*100;
                netPay = Math.round(netPay);
                netPay = netPay/100;
                    
                //Output Results
                if (maritalStatus == 1){
                    maritalValue = "Single";
                }
                else if (maritalStatus == 2){
                    maritalValue = "Married";
                }
                JOptionPane.showMessageDialog(frame,
                        "Pay for "+hours+" at "+payRate+" claiming " +maritalValue+
                        " and "+dependants+" dependents.\nGross Pay: "+grossPay+
                        "\nOvertime Pay: "+overtimePay+
                        "\nPre-tax Deductions: "+deductions+"\nFederal Income Tax Withheld: "
                        +fedWithholdingTax+"\nSocial Security Tax: "+ssTax+"\nMedicare Tax: "
                        +medicareTax+"\nNet Pay: "+netPay
                );
            }
            else if (paySchedule == 2){
                System.out.print("Enter how many hours you've worked this pay period: ");
                hours = scan.nextDouble();
                System.out.print("Enter your hourly pay rate: ");
                payRate = scan.nextDouble();
                double hoursReg = 80;
                GrossPay calc = new GrossPay(payRate, hours, hoursReg);
                System.out.println("Your Gross Pay for the values entered is "+calc.getGrossPay());}
            else {
                JOptionPane.showMessageDialog(frame,"Invalid Value");
                paySchedule = 0;}
            paySchedule = 0;
        }
    }
}
