package utils;

/**
 *
 * @author Nghia
 */
public class CONSTANT {
    // Path to save uploaded food images
    public static String savedFoodImagePath = "C:\\Users\\Nghia\\Documents\\NetBeansProjects\\foodwebapp\\web\\images\\food";

    // VietQR Payment Receiver Info
    public static final String RECEIVER_BANK_CODE = "vietcombank";            
    public static final String RECEIVER_CARD_NUMBER = "9366966321";            
    public static final String RECEIVER_ACCOUNT_NAME = "NGUYEN MINH NHAN"; 

    // Optional: Add a default payment note format
    public static final String PAYMENT_NOTE_FORMAT = "Payment for Order ID %d";  // Use String.format(...)
}
