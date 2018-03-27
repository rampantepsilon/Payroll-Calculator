package payroll2_1;

/**
 *
 * @author Thomas Ware
 */
public class FedWithholding {
    
    private double fedWithholding;
    private double gross;
    private double dependants;
    private double dependantsCost;
    private double taxableGross;
    private double paySchedule;
    private int maritalStatus;
    
    public FedWithholding(double inGross, double inDependants, int inPaySchedule, int inMaritalStatus){
        gross = inGross;
        dependants = inDependants;
        paySchedule = inPaySchedule;
        maritalStatus = inMaritalStatus;
        
        //Weekly Withholding
        if (paySchedule == 1){
            dependantsCost = 77.9;
            taxableGross = gross - (dependantsCost * dependants);;
            
            //Single Brackets
            if (maritalStatus == 1){
                if (taxableGross > 8090){
                    fedWithholding = 2336.48 + (.396*(taxableGross - 8090));}
                else if (taxableGross >= 8058){
                    fedWithholding = 2325.28 + (.35*(taxableGross - 8058));}
                else if (taxableGross >= 3730){
                    fedWithholding = 897.04 + (.33*(taxableGross - 3730));}
                else if (taxableGross >= 1812){
                    fedWithholding = 360 + (.28*(taxableGross - 1812));}
                else if (taxableGross >= 774){
                    fedWithholding = 100.5 + (.25*(taxableGross - 744));}
                else if (taxableGross >= 224){
                    fedWithholding = 18 + (.15*(taxableGross - 224));}
                else if (taxableGross >= 44){
                    fedWithholding = .1*(taxableGross - 44);}}
            
            //Married Brackets
            else if (maritalStatus == 2){
                if (taxableGross > 9218){
                    fedWithholding = 2531.22 + (.396*(taxableGross - 9218));}
                else if (taxableGross >= 8180){
                    fedWithholding = 2167.92 + (.35*(taxableGross - 8180));}
                else if (taxableGross >= 4654){
                    fedWithholding = 1004.34 + (.33*(taxableGross - 4654));}
                else if (taxableGross >= 3111){
                    fedWithholding = 572.3 + (.28*(taxableGross - 3111));}
                else if (taxableGross >= 1626){
                    fedWithholding = 201.05 + (.25*(taxableGross - 1626));}
                else if (taxableGross >= 525){
                    fedWithholding = 35.9 + (.15*(taxableGross - 525));}
                else if (taxableGross >= 166){
                    fedWithholding = .1*(taxableGross - 166);}}}
        
        //Bi-Weekly Withholdings
        else if (paySchedule == 2){
            dependantsCost = 155.8;
            taxableGross = gross - (dependantsCost * dependants);
            
            //Single Brackets
            if (maritalStatus == 1){
                if (taxableGross > 16181){
                    fedWithholding = 4673.41 + (.396*(taxableGross - 16181));}
                else if (taxableGross >= 16115){
                    fedWithholding = 4650.31 + (.35*(taxableGross - 16115));}
                else if (taxableGross >= 7460){
                    fedWithholding = 1794.16 + (.33*(taxableGross - 7460));}
                else if (taxableGross >= 3623){
                    fedWithholding = 719.80 + (.28*(taxableGross - 3623));}
                else if (taxableGross >= 1548){
                    fedWithholding = 201.05 + (.25*(taxableGross - 1548));}
                else if (taxableGross >= 447){
                    fedWithholding = 35.9 + (.15*(taxableGross - 447));}
                else if (taxableGross >= 88){
                    fedWithholding = .1*(taxableGross - 88);}}
            
            //Married Brackets
            else if (maritalStatus == 2){
                if (taxableGross > 18437){
                    fedWithholding = 5062.72 + (.396*(taxableGross - 18437));}
                else if (taxableGross >= 16360){
                    fedWithholding = 4335.77 + (.35*(taxableGross - 16360));}
                else if (taxableGross >= 9308){
                    fedWithholding = 2008.61 + (.33*(taxableGross - 9308));}
                else if (taxableGross >= 6221){
                    fedWithholding = 1144.25 + (.28*(taxableGross - 6221));}
                else if (taxableGross >= 3252){
                    fedWithholding = 402 + (.25*(taxableGross - 3252));}
                else if (taxableGross >= 1050){
                    fedWithholding = 71.7 + (.15*(taxableGross - 1050));}
                else if (taxableGross >= 333){
                    fedWithholding = .1*(taxableGross - 333);}}}
        
        //Round Withholdings for money
        fedWithholding = fedWithholding * 100;
        fedWithholding = Math.round(fedWithholding);
        fedWithholding = fedWithholding/100;
    }
    
    //Return Withholding Values
    public double getFedWithholding(){
        return fedWithholding;
    }
}
