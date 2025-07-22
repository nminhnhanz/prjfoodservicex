/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package utils;

import java.math.BigDecimal;

/**
 *
 * @author Nghia
 */
public class PaymentAPI {
    public static String getVQR(String BankName, String cardNumber, String accountName, BigDecimal amount, String info){
        /*
            Return a VietQR Image link by using VietQR Quick Link API.
        */
        return "https://img.vietqr.io/image/" + BankName + 
                "-"+cardNumber+
                "-compact2.jpg?amount="
                +amount.toPlainString()
                +"&addInfo="+info
                +"&accountName="+accountName;
    }
    
}
