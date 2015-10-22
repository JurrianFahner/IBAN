

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ensignprojects.iban;

import java.math.BigInteger;

/**
 *
 * @author jurrian
 */
public class Iban {
    private Iban() {} // Disable class initialisation, no private members, only static methods.
    
    private static int getValidLength(String countryCode) {
        int lengthIban = 0;
        
        switch(countryCode) {
            case "NO": lengthIban = 15; break;
            case "BE": lengthIban = 16; break;

            case "DK": case "FO": case "FI": case "GL": 
            case "NL": lengthIban = 18; break;
                
            case "MK": 
            case "SI": lengthIban = 19; break;
                
            case "AT": case "BA": case "KZ": case "XK": case "EE": case "LT": 
            case "LU": lengthIban = 20; break;
                
            case "HR": case "LV": case "CR": case "LI": 
            case "CH": lengthIban = 21; break;
                
            case "BH": case "BG": case "GE": case "DE": case "GB": 
            case "IE": case "IM": case "JE": case "ME": 
            case "RS": lengthIban = 22; break;
                
            case "GI": case "IL":
            case "AE": lengthIban = 23; break;
                
            case "AD": case "CZ": case "MD": case "PK": case "RO": 
            case "SA": case "SK": case "ES": case "SE": case "VG":
            case "TN": lengthIban = 24; break;
                
            case "ST":
            case "PT": lengthIban = 25; break;
                
            case "IS": 
            case "TR": lengthIban = 26; break;
                
            case "FR": case "GR": case "IT": case "MC": case "MR":
            case "SM": lengthIban = 27; break;
                
            case "AL": case "AZ": case "DO": case "GT": 
            case "CY": case "HU": case "LB": 
            case "PL": lengthIban = 28; break;
                
            case "BR":
            case "PS": lengthIban = 29; break;
                
            case "QA": lengthIban = 29; break;
                
            case "KW": case "MU":    
            case "JO": lengthIban = 30; break;
                
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
    
    public static void main(String[] args) {
        try {
            System.out.println(validate("NL91ABNA0417164300"));
            System.out.println(validate("QA58DOHB00001234567890ABCDEFG"));
//            System.out.println(validate("QA58DOHB0000d1234567890ABCDEF"));
            
            System.out.println("NL91ABNA0417164300:: " + generateValidCheckDigits("NL91ABNA0417164300"));

        } catch (IbanLengthException ible) {
            System.out.println("Error occurred in length");
            ible.printStackTrace();
        }
        
        System.out.println("charcode a: " + ((int) 'a'));
    }
}
