package payrollApplication2_0;

/**
 *
 * @author Thomas Ware
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

public class PayrollApplication extends JFrame {
    private Container contents;
    private JTextArea calculations;
    private JTextField inPayRate, inHours, inFiling, inDependents,
            inDeductions;
    private JLabel payRateLabel, hoursLabel, filingLabel, 
            dependentsLabel, deductionsLabel, payLabel;
    private JButton inWeekly, inBiweekly, clear;
    
    public PayrollApplication(){
        super("Payroll Calculator");
        
        contents = getContentPane();
        contents.setLayout(new FlowLayout());
        
        calculations = new JTextArea
        ("Enter your values above to calculate pay.");
        
        inWeekly = new JButton("Weekly");
        inBiweekly = new JButton("Bi-Weekly");
        clear = new JButton("Clear");
        
        inPayRate = new JTextField(6);
        inHours = new JTextField(6);
        inFiling = new JTextField(1);
        inDependents = new JTextField(2);
        inDeductions = new JTextField(7);
        
        payRateLabel = new JLabel("Enter your pay rate:");
        hoursLabel = new JLabel("Enter hours worked:");
        filingLabel = new JLabel("Enter 1 for Single or 2 for Married:");
        dependentsLabel = new JLabel("Enter amount of dependents:");
        deductionsLabel = new JLabel("Enter any pre-tax deductions:");
        payLabel = new JLabel("Select the button for your pay bracket:");
        
        contents.add(payRateLabel);
        contents.add(inPayRate);
        contents.add(hoursLabel);
        contents.add(inHours);
        contents.add(filingLabel);
        contents.add(inFiling);
        contents.add(dependentsLabel);
        contents.add(inDependents);
        contents.add(deductionsLabel);
        contents.add(inDeductions);
        contents.add(payLabel);
        contents.add(inWeekly);
        contents.add(inBiweekly);
        contents.add(clear);
        contents.add(calculations);
        
        ButtonHandler bh = new ButtonHandler();
        
        inWeekly.addActionListener(bh);
        inBiweekly.addActionListener(bh);
        clear.addActionListener(bh);
        
        setSize(300,500);
        setVisible(true);
        setLocationRelativeTo(null);
    }
    private class ButtonHandler implements ActionListener{
        public void actionPerformed(ActionEvent ae){
            DecimalFormat money = new DecimalFormat("$#,##0.00");
            DecimalFormat integer = new DecimalFormat("0");
            
            double payRate = 0;
            double hours = 0;
            int filing = 0;
            double dependents = 0;
            double deductions = 0;
            String maritalValue = "null";
            
            try{
                payRate = Double.parseDouble(inPayRate.getText());
            }
            catch (NumberFormatException e){
                inPayRate.setText("");
            }
            try{
                hours = Double.parseDouble(inHours.getText());
            }
            catch (NumberFormatException e){
                inHours.setText("");
            }
            try{
                filing = Integer.parseInt(inFiling.getText());
                if (filing > 2 || filing < 1){
                        JOptionPane.showMessageDialog(null,"Invalid Value");
                    }
            }
            catch (NumberFormatException e){
                inFiling.setText("");
            }
            try{
                dependents = Double.parseDouble(inDependents.getText());
            }
            catch (NumberFormatException e){
                inDependents.setText("");
            }
            try{
                deductions = Double.parseDouble(inDeductions.getText());
            }
            catch (NumberFormatException e){
                inDeductions.setText("");
            }
            double hoursReg = 0;
            int paySchedule = 0;
            
            if (ae.getSource() == inWeekly){
                hoursReg = 40;
                paySchedule = 1;
            }
            else if (ae.getSource() == inBiweekly){
                hoursReg = 80;
                paySchedule = 2;
            }
            GrossPay calc = new GrossPay(payRate, hours, hoursReg);
            double grossPay = calc.getGrossPay();
            double overtimePay = calc.getOvertimePay();
            double grossDeducted = grossPay - deductions;
            
            FedWithholding fedTax = new FedWithholding(grossDeducted, dependents, paySchedule,filing);
            double fedWithheld = fedTax.getFedWithholding();
            
            double ssTax = (grossDeducted*.062);
            ssTax = ssTax * 100;
            ssTax = ssTax + 0.5;
            ssTax = Math.round(ssTax);
            ssTax = ssTax/100;
            
            double medicareTax = (grossDeducted*.0145);
            medicareTax = medicareTax * 100;
            medicareTax = Math.round(medicareTax);
            medicareTax = medicareTax/100;
            
            double netPay = grossPay-deductions-fedWithheld-ssTax-medicareTax;
            netPay = netPay*100;
            netPay = Math.round(netPay);
            netPay = netPay/100;
            
            if (filing == 1)
                maritalValue = "Single";
            else if (filing == 2)
                maritalValue = "Married";
            if (ae.getSource() != clear){
                calculations.setText("Pay for "+hours+" at "
                    +money.format(payRate)+"\nclaiming "+maritalValue+
                    " and "+integer.format(dependents)+" dependents."
                    +"\n\nGrossPay: "+money.format(grossPay)+
                    "\nOvertime Pay: "+money.format(overtimePay)+
                    "\nPre-tax Deductions: "+money.format(deductions)+
                    "\nFederal Income Tax Withheld: "+money.format(fedWithheld)+
                    "\nSocial Security Tax: "+money.format(ssTax)+
                    "\nMedicare Tax: "+money.format(medicareTax)+
                    "\nNet Pay: "+money.format(netPay));
            }
            else if (ae.getSource() == clear){
                calculations.setText("Enter your values above to calculate pay.");
                inPayRate.setText("");
                inHours.setText("");
                inFiling.setText("");
                inDependents.setText("");
                inDeductions.setText("");
            }
        }
    }
    
    public static void main(String [] args){
        PayrollApplication payGUI = new PayrollApplication();
        payGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
