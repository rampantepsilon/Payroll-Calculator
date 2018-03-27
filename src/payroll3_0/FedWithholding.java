package payroll3_0;

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
            dependantsCost = 79.8;
            taxableGross = gross - (dependantsCost * dependants);;
            
            //Single Brackets
            if (maritalStatus == 1){
                if (taxableGross > 9687){
                    fedWithholding = 2898.1 + (.37*(taxableGross - 9687));}
                else if (taxableGross >= 3917){
                    fedWithholding = 878.6 + (.35*(taxableGross - 3917));}
                else if (taxableGross >= 3100){
                    fedWithholding = 617.16 + (.32*(taxableGross - 3100));}
                else if (taxableGross >= 1658){
                    fedWithholding = 271.08 + (.24*(taxableGross - 1658));}
                else if (taxableGross >= 815){
                    fedWithholding = 85.62 + (.22*(taxableGross - 815));}
                else if (taxableGross >= 254){
                    fedWithholding = 18.3 + (.12*(taxableGross - 224));}
                else if (taxableGross >= 71){
                    fedWithholding = .1*(taxableGross - 71);}}
            
            //Married Brackets
            else if (maritalStatus == 2){
                if (taxableGross > 11761){
                    fedWithholding = 3103.57 + (.396*(taxableGross - 11761));}
                else if (taxableGross >= 7914){
                    fedWithholding = 1757.12 + (.35*(taxableGross - 7914));}
                else if (taxableGross >= 6280){
                    fedWithholding = 1234.24 + (.33*(taxableGross - 6280));}
                else if (taxableGross >= 3395){
                    fedWithholding = 541.84 + (.28*(taxableGross - 3395));}
                else if (taxableGross >= 1711){
                    fedWithholding = 171.36 + (.25*(taxableGross - 1711));}
                else if (taxableGross >= 688){
                    fedWithholding = 36.6 + (.15*(taxableGross - 688));}
                else if (taxableGross >= 222){
                    fedWithholding = .1*(taxableGross - 222);}}}
        
        //Bi-Weekly Withholdings
        else if (paySchedule == 2){
            dependantsCost = 159.6;
            taxableGross = gross - (dependantsCost * dependants);
            
            //Single Brackets
            if (maritalStatus == 1){
                if (taxableGross > 19373){
                    fedWithholding = 5795.72 + (.37*(taxableGross - 19373));}
                else if (taxableGross >= 7835){
                    fedWithholding = 1757.42 + (.35*(taxableGross - 7835));}
                else if (taxableGross >= 6200){
                    fedWithholding = 1234.22 + (.31*(taxableGross - 6200));}
                else if (taxableGross >= 3315){
                    fedWithholding = 541.82 + (.22*(taxableGross - 3315));}
                else if (taxableGross >= 1631){
                    fedWithholding = 171.34 + (.22*(taxableGross - 1631));}
                else if (taxableGross >= 509){
                    fedWithholding = 36.7 + (.12*(taxableGross - 509));}
                else if (taxableGross >= 142){
                    fedWithholding = .1*(taxableGross - 142);}}
            
            //Married Brackets
            else if (maritalStatus == 2){
                if (taxableGross > 23521){
                    fedWithholding = 5062.72 + (.37*(taxableGross - 23521));}
                else if (taxableGross >= 15829){
                    fedWithholding = 4335.77 + (.35*(taxableGross - 15829));}
                else if (taxableGross >= 12560){
                    fedWithholding = 2008.61 + (.32*(taxableGross - 12560));}
                else if (taxableGross >= 6790){
                    fedWithholding = 1144.25 + (.48*(taxableGross - 6790));}
                else if (taxableGross >= 3421){
                    fedWithholding = 402 + (.22*(taxableGross - 3421));}
                else if (taxableGross >= 1177){
                    fedWithholding = 71.7 + (.12*(taxableGross - 1177));}
                else if (taxableGross >= 444){
                    fedWithholding = .1*(taxableGross - 444);}}}
        
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
