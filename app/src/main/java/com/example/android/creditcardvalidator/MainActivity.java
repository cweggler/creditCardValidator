package com.example.android.creditcardvalidator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /**
     * Checks whether the credit card is MasterCard, Visa or American Express
     * @param cardNumber The card number the user enters
     * @return true if the card number is American Express, Visa or Mastercard, false if not
     * @throws NumberFormatException the UI is restricting the user to only enter numbers but just in case.
     */

    public boolean checkCreditCardType(String cardNumber) throws NumberFormatException {


        int firstDigit = Integer.parseInt(cardNumber.substring(0, 1));
        boolean isValid = false;



        switch(firstDigit) {

            // If the first digit is a 4, VISA Card, check if 16 digits long then isValid=true
            case 4 :

                if (cardNumber.length() == 16) {
                    isValid = true;
                }
                break;

            /* If first two digits are 34 or 37 then check the length is 15 digits long
               both true the card is an American Express, isValid=true */
            case 3 :
                // Means this may be an American Express card check second digit
                int secondDigit = Integer.parseInt(cardNumber.substring(1,2));

                if(secondDigit == 4 || secondDigit == 7){

                    //Then check the length is 15 digits long
                    if(cardNumber.length() == 15){
                        isValid = true;
                    }
                }
                break;

            /* If the first 2 digits are in the range 51 or greater or less than or equal to 55
               then check the length is 16 digits long. Both true, the card is MasterCard, isValid=true */
            case 5 :
                secondDigit = Integer.parseInt(cardNumber.substring(1,2));

                if (secondDigit >= 1 && secondDigit <= 5) {
                    // check the length is 16 digits long
                    if (cardNumber.length() == 16) {
                        isValid = true;
                    }
                }
                break;

            /*If first 4 digits are in the range: 2221 to 2720 with those numbers included then check the
              length is 16 digits long. Both true, the card is MasterCard, isValid=true */
            case 2 :
                // Means this may be a MasterCard check the next three digits
                int next3Digits = Integer.parseInt(cardNumber.substring(1,4));

                if (next3Digits >= 221 && next3Digits <= 720) {
                    // check the length is 16 digits long
                    if (cardNumber.length() == 16) {
                        isValid = true;
                    }
                }
                break;
            default: break;


        }

        return isValid;


    }

    /**
     * Validates the credit card number using the Luhn algorithm.
     * Luhn Algorithm is an industry standard for validating credit card numbers
     * @param cardNumber
     * @return
     */

    public boolean validateCreditCard(String cardNumber) throws NumberFormatException {
        int sum = 0;
        int digit = 0;
        int addend = 0;

        boolean isDoubled = false;
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            digit = Integer.parseInt(cardNumber.substring(i, i + 1));
            if (isDoubled) {
                addend = digit * 2;
                if (addend > 9) {
                    addend -= 9;
                }
            } else {
                addend = digit;
            }
            sum += addend;
            isDoubled = !isDoubled;
        }
        return (sum % 10) == 0;

    }

    /**
     * This validates the credit card number once the user selects the "Validate" button
     * @param v The view
     */

    public void onClickValidate(View v){
        boolean cardValid = false;
        String cardEntered = getNumberString();

        if (checkCreditCardType(cardEntered)){

            if(validateCreditCard(cardEntered)) {
                cardValid = true;
                Toast.makeText(this, "Success! Credit Card number is valid.", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(this, "Please try again. Invalid credit card number", Toast.LENGTH_LONG).show();
        }


    }

    /**
     * This gets the credit card number the user entered and formats it into a String for processing
     * @return numberString number formatted as a String
     */

    public String getNumberString(){
        //get EditText
        EditText numberInput = (EditText) findViewById(R.id.enterNumber);

        //Make this a String for better manipulation
        String numberString = numberInput.getText().toString();
        return numberString;
    }
}
