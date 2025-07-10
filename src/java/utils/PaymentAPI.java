/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author Nghia
 */
public class PaymentAPI {
    public static String getVQR(String BankName, int cardNumber, String accountName, int amount, String info){
        /*
            Return a VietQR Image link by using VietQR Quick Link API.
        */
        return "https://img.vietqr.io/image/" + BankName + 
                "-"+cardNumber+
                "-compact2.jpg?amount="
                +String.valueOf(amount)
                +"&addInfo="+info
                +"&accountName="+accountName;
    }
    
}
