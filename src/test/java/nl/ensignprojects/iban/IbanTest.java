/*
 * The MIT License
 *
 * Copyright 2015 jurrian.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package nl.ensignprojects.iban;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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
