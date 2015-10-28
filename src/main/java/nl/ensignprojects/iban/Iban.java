package nl.ensignprojects.iban;

import java.math.BigInteger;

/**
 * This class can validate International Bank Account Numbers (hereafter: IBAN). 
 * It is also able to calculate the checkdigits of an IBAN
 * @author Jurrian Fahner
 */
public class Iban {
    private Iban() {} // Disable class initialisation, no private members, only static methods.
    
    // The IBAN length information is derived from 
    // https://www.dnb.no/en/business/transaction-banking/international-payments/example-iban.html
    private static int getValidLength(String countryCode) {
        int lengthIban = 0;
        
        switch(countryCode) {
            
            case "NO": lengthIban = 15; break;
                
            case "BE": lengthIban = 16; break;

            case "DK": case "FI": case "FO": 
            case "GL": case "NL": 
                lengthIban = 18; break;
                
            case "MK": 
            case "SI": lengthIban = 19; break;
                
            case "AT": case "BA": case "EE": 
            case "KZ": case "LT": 
            case "LU": case "XK": 
                lengthIban = 20; break;
                
            case "CH": case "CR": case "HR": 
            case "LI": case "LV": 
                lengthIban = 21; break;
                
            case "BH": case "BG": case "DE": case "GB": case "GE": 
            case "IE": case "IM": case "JE": case "ME": case "RS": 
                lengthIban = 22; break;
                
            case "AE": case "GI": case "IL":            
                lengthIban = 23; break;
                
            case "AD": case "CZ": case "ES": case "MD": case "PK": 
            case "RO": case "SA": case "SK": case "SE": case "TN": 
            case "VG":
                lengthIban = 24; break;
                
            case "ST": case "PT": 
                lengthIban = 25; break;
                
            case "IS": case "TR": 
                lengthIban = 26; break;
                
            case "FR": case "GR": case "IT": 
            case "MC": case "MR": case "SM": 
                lengthIban = 27; break;
                
            case "AL": case "AZ": case "CY": case "DO": 
            case "GT": case "HU": case "LB": case "PL": 
                lengthIban = 28; break;
                
            case "BR": case "PS": case "QA": 
                lengthIban = 29; break;
                
            case "KW": case "MU": case "JO": 
                lengthIban = 30; break;
                
            case "MT": lengthIban = 31; break;        
                
            case "LC": lengthIban = 32; break;
        }
        
        return lengthIban;
    }
    
    private static BigInteger replaceEachLetterWithTwoDigits(String ibanRearranged) {
       
        String result = "";
        for (char letter : ibanRearranged.toCharArray()) {
            int charCode = (int) letter;
            int charCodeA = (int) 'A';
            
            if (charCode >= charCodeA) {
                result += (((int) letter) - charCodeA + 10); //  A = 10, B = 11, ..., Z = 35            
            } else {
                result += letter;
            }
                
        }
        //Parse to BigInteger, because number is to big for basic datatypes. 
        return new BigInteger(result);
    }
    
    private static boolean isLengthValid(String internationalBankAccountNumber) {
        String country = internationalBankAccountNumber.substring(0,2);
        return internationalBankAccountNumber.length() == getValidLength(country);
    }
    
    
    /**
     * Validates internationalBankAccountNumber according to description on <a href="https://nl.wikipedia.org/wiki/International_Bank_Account_Number">Wikipedia</a>
     * @param internationalBankAccountNumber
     * @return true, when following criteria are met: correct length (for specific country) and remainder of division by 97 equals to 1
     * @throws nl.ensignprojects.iban.IbanLengthException
     */
    public final static boolean validate(String internationalBankAccountNumber) throws IbanLengthException {
        boolean result = false;
        
        if (isLengthValid(internationalBankAccountNumber)) {
            String ibanRearranged = internationalBankAccountNumber.substring(4) 
                    + internationalBankAccountNumber.substring(0, 4);

            result = replaceEachLetterWithTwoDigits(ibanRearranged)
                    .mod(new BigInteger("97"))
                    .compareTo(new BigInteger("1")) 
                    == 0;
        } else {
            throw new IbanLengthException(internationalBankAccountNumber);
        }
        
        return result;
    }
    
    /**
     * Replaces the check digits in an international bank account number with
     * correct ones, for example in case of generating new numbers.
     * @param internationalBankAccountNumber
     * @return internationalBankAccountNumber replaced with correct check digits
     * @throws nl.ensignprojects.iban.IbanLengthException 
     */
    public final static String generateValidCheckDigits(String internationalBankAccountNumber) throws IbanLengthException {
        String result = "";
        if (isLengthValid(internationalBankAccountNumber)) {

            String ibanRearranged = internationalBankAccountNumber.substring(4) 
                    + internationalBankAccountNumber.substring(0, 2) + "00";
            
            String checkDigits = "00" + (new BigInteger("98"))
                    .subtract(replaceEachLetterWithTwoDigits(ibanRearranged)
                    .mod(new BigInteger("97")));
                        
            result = internationalBankAccountNumber.substring(0, 2) 
                    + checkDigits.substring(checkDigits.length()-2)
                    + internationalBankAccountNumber.substring(4);
            
        } else {            
            throw new IbanLengthException(internationalBankAccountNumber);
        }
        return result;
    }
    
}
