package payroll3_0;

/**
 *
 * @author Thomas Ware
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

public class PayrollApplication extends JFrame {
    private final Container contents;
    private final JPanel data, payButtons, filingCheck, payRate, payRateWrapper;
    private final JTextArea calculations;
    private final JTextField inPayRate, inHours, inDependents, inDeductions, inAdjustments;
    private final JLabel payRateLabel, hoursLabel, filingLabel, 
            dependentsLabel, deductionsLabel, payLabel, adjustmentsLabel;
    private final JButton inWeekly, inBiweekly, /*inCustom,*/ clear;
    private final ButtonGroup filing;
    private final JRadioButton single, married;
    private int filingStatus = 1;
    
    public PayrollApplication(){
        //Title for Window
        super("Payroll Calculator v3.0 (2018 Edition)");
        
        //Set Ground Work for Layout
        contents = getContentPane();
        contents.setLayout(new BoxLayout(contents,BoxLayout.Y_AXIS));
        
        //Text Area for Results
        calculations = new JTextArea
                ("Enter your values above to calculate pay.\n\n\n\n\n\n\n\n\n\n");
        
        //Selection Buttons for Pay Period Length
        inWeekly = new JButton("Weekly");
        inBiweekly = new JButton("Bi-Weekly");
        //inCustom = new JButton("Custom");
        clear = new JButton("Clear");
        
        //Fields for data to be entered
        inPayRate = new JTextField(6);
        inHours = new JTextField(6);
        inDependents = new JTextField(2);
        inDeductions = new JTextField(7);
        inAdjustments = new JTextField(8);
        
        //Marital Status Buttons
        single = new JRadioButton("Single",true);
        married = new JRadioButton("Married");
        
        filing = new ButtonGroup();
        filing.add(single);
        filing.add(married);
        
        //Labels
        payRateLabel = new JLabel("Enter your pay rate:");
        hoursLabel = new JLabel("Enter hours worked:");
        filingLabel = new JLabel("Select your marital status:");
        dependentsLabel = new JLabel("Enter amount of dependents:");
        deductionsLabel = new JLabel("Enter any pre-tax deductions:");
        adjustmentsLabel = new JLabel("Enter any adjustments:");
        payLabel = new JLabel("Select the button for your pay bracket:");
        
        //filingCheck JPanel
        filingCheck = new JPanel(new GridLayout(1,3));
        filingCheck.add(single);
        filingCheck.add(married);
        
        //payButtons JPanel
        payButtons = new JPanel(new GridLayout(1,4));
        payButtons.add(inWeekly);
        payButtons.add(inBiweekly);
        //payButtons.add(inCustom);
        payButtons.add(clear);
        
        //payRate JPanel
        payRate = new JPanel(new FlowLayout());
        payRate.add(payLabel);
        
        //payRateWrapper JPanel
        payRateWrapper = new JPanel(new GridLayout(2,1));
        payRateWrapper.add(payRate);
        payRateWrapper.add(payButtons);
        
        //data JPanel
        data = new JPanel(new GridLayout(6,2));
        data.add(payRateLabel);
        data.add(inPayRate);
        data.add(hoursLabel);
        data.add(inHours);
        data.add(filingLabel);
        data.add(filingCheck);
        data.add(dependentsLabel);
        data.add(inDependents);
        data.add(deductionsLabel);
        data.add(inDeductions);
        data.add(adjustmentsLabel);
        data.add(inAdjustments);
        
        //add JPanels to contents
        contents.add(data);
        contents.add(payRateWrapper);
        contents.add(calculations);
        
        //ButtonHandler
        ButtonHandler bh = new ButtonHandler();
        
        inWeekly.addActionListener(bh);
        inBiweekly.addActionListener(bh);
        //inCustom.addActionListener(bh);
        clear.addActionListener(bh);
        
        //RadioHandler
        RadioButtonHandler rbh = new RadioButtonHandler();
        single.addItemListener(rbh);
        married.addItemListener(rbh);
        
        //set window visibility
        setSize(450,500);
        setVisible(true);
        setLocationRelativeTo(null);
    }
    
    private class RadioButtonHandler implements ItemListener{
        public void itemStateChanged(ItemEvent ie){
            if (ie.getSource() == single)
                filingStatus = 1;
            else if (ie.getSource() == married)
                filingStatus = 2;
        }
    }
    
    private class ButtonHandler implements ActionListener{
        public void actionPerformed(ActionEvent ae){
            DecimalFormat money = new DecimalFormat("$#,##0.00");
            DecimalFormat integer = new DecimalFormat("0");
            
            double payRate = 0;
            double origPay = 0;
            double hours = 0;
            int filing = filingStatus;
            double dependents = 0;
            double deductions = 0;
            double adjustments = 0;
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
            try{
                adjustments = Double.parseDouble(inAdjustments.getText());
            }
            catch (NumberFormatException e){
                inAdjustments.setText("");
            }
            double hoursReg = 0;
            int paySchedule = 0;
            
            if (ae.getSource() == inWeekly){
                hoursReg = 40;
                paySchedule = 1;
                origPay = payRate;
            }
            else if (ae.getSource() == inBiweekly){
                hoursReg = 80;
                paySchedule = 2;
                origPay = payRate;
            }
            /*else if (ae.getSource() == inCustom){
                hoursReg = 80;
                paySchedule = 2;
                origPay = payRate;
            }*/
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
            
            double netPay = 0;
            double pto = 0;
            netPay = grossPay-deductions-fedWithheld-ssTax-medicareTax;
            /*if (ae.getSource() != inCustom){
                netPay = grossPay-deductions-fedWithheld-ssTax-medicareTax;
            }
            if (ae.getSource() == inSupport){
                netPay = grossPay-deductions-fedWithheld-ssTax-medicareTax+adjustments;
                pto = (hours*.0385);
                pto = pto * 100;
                pto = Math.round(pto);
                pto = pto/100;
            }*/
            netPay = netPay*100;
            netPay = Math.round(netPay);
            netPay = netPay/100;
            
            if (filing == 1)
                maritalValue = "Single";
            else if (filing == 2)
                maritalValue = "Married";
            if (ae.getSource() != clear){
                calculations.setText("Pay for "+hours+" at "
                        +money.format(origPay)+"\nclaiming "+maritalValue+
                        " and "+integer.format(dependents)+" dependents."
                        +"\n\nGrossPay: "+money.format(grossPay)+
                        "\nOvertime Pay: "+money.format(overtimePay)+
                        "\nPre-tax Deductions: "+money.format(deductions)+
                        "\nFederal Income Tax Withheld: "+money.format(fedWithheld)+
                        "\nSocial Security Tax: "+money.format(ssTax)+
                        "\nMedicare Tax: "+money.format(medicareTax)+
                        "\nNet Pay: "+money.format(netPay)+"\n");
                /*if (ae.getSource() != inCustom){
                    calculations.setText("Pay for "+hours+" at "
                        +money.format(origPay)+"\nclaiming "+maritalValue+
                        " and "+integer.format(dependents)+" dependents."
                        +"\n\nGrossPay: "+money.format(grossPay)+
                        "\nOvertime Pay: "+money.format(overtimePay)+
                        "\nPre-tax Deductions: "+money.format(deductions)+
                        "\nFederal Income Tax Withheld: "+money.format(fedWithheld)+
                        "\nSocial Security Tax: "+money.format(ssTax)+
                        "\nMedicare Tax: "+money.format(medicareTax)+
                        "\nNet Pay: "+money.format(netPay)+"\n");
                }
                if (ae.getSource() == inCustom){
                    calculations.setText("Pay for "+hours+" at "
                        +money.format(origPay)+"\nclaiming "+maritalValue+
                        " and "+integer.format(dependents)+" dependents."
                        +"\n\nGrossPay: "+money.format(grossPay)+
                        "\nOvertime Pay: "+money.format(overtimePay)+
                        "\nPre-tax Deductions: "+money.format(deductions)+
                        "\nFederal Income Tax Withheld: "+money.format(fedWithheld)+
                        "\nSocial Security Tax: "+money.format(ssTax)+
                        "\nMedicare Tax: "+money.format(medicareTax)+
                        "\nAdjustments: "+money.format(adjustments)+
                        "\nNet Pay: "+money.format(netPay)+"\n\n"+
                        "PTO Earned: "+pto+"\n");
                }*/
            }
            else if (ae.getSource() == clear){
                calculations.setText("Enter your values above to calculate pay.");
                inPayRate.setText("");
                inHours.setText("");
                inDependents.setText("");
                inDeductions.setText("");
                inAdjustments.setText("");
            }
        }
    }
    
    public static void main(String [] args){
        PayrollApplication payGUI = new PayrollApplication();
        payGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
