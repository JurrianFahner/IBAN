package nl.ensignprojects.iban;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jurrian
 */
public class IbanTest {
    
    /**
     * Test of validate method, of class Iban.
     */
    @Test
    public void testValidate() throws Exception {
        System.out.println("validate");
        String internationalBankAccountNumber = "NL91ABNA0417164300";
        boolean result = Iban.validate(internationalBankAccountNumber);
        assertTrue(result);
    }
    
    @Test(expected = Exception.class)  
    public void testIBANLengthException() throws Exception {
        String result = Iban.generateValidCheckDigits("NL00ABNA041716430"); //Invalid length of accountnumber
    }

    /**
     * Test of generateValidCheckDigits method, of class Iban.
     */
    @Test
    public void testGenerateValidCheckDigits() throws Exception {
        System.out.println("generateValidCheckDigits");
        String expResult = "NL91ABNA0417164300";
        String result = Iban.generateValidCheckDigits("NL00ABNA0417164300");
        assertEquals(expResult, result);
    }
    
}
