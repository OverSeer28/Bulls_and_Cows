package bullscows;
import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {


        System.out.println("Please, enter the secret code's length:");
        System.out.print(">");
        Scanner scanner = new Scanner(System.in);
        String leng = scanner.nextLine();
        boolean status = true;


        try {

            int length = Integer.parseInt(leng);


            System.out.println(" Input the number of possible symbols in the code:");
            System.out.print(">");
            int letter =  scanner.nextInt();


            if(letter > 36){
                System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
                return;
            }


            int letters = letter;

            char[] asteriks = new char[length];
            Arrays.fill(asteriks, '*');

            if(letters < length || length<1){
                System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.", letters, length);
                return;
            }



            System.out.print("The secret is prepared: ");
            for(int i = 0; i < length ; i++){
                System.out.print("*");
            }

            int numberSize = letters > 10 ? 10 : letters;
            int lettersize = letters-numberSize;


            int [] numberLength = new int[numberSize];
            char [] alphabetLength = new char[lettersize];

            //System.out.println(" (0-9, a-f).");

            for(int i = 0; i < numberSize; i++){
                numberLength[i] = i;
            }





            if(lettersize <= 1 ){
                System.out.printf(" (%d-%d, a-f).",numberLength[0], numberLength[numberLength.length - 1] );
            }else{
                for(int i = 0, j = 97; i+j< 97+lettersize; i++){
                    alphabetLength[i] = (char)(i+j);
                }
                System.out.printf(" (%d-%d, %c-%c).",numberLength[0], numberLength[numberLength.length - 1], alphabetLength[0], alphabetLength[alphabetLength.length-1] );
            }



            String secretCode = randomNumber(length, letters);
            //System.out.println(secretCode);


            System.out.println("Okay, let's start a game!");

            int turn = 0;


            while(status) {
                System.out.println("Turn "+ turn + ".");
                System.out.print(">");
                String guess = scanner.next();


                if(guess.length() == secretCode.length()){


                    int [] result =  secret(secretCode, guess);

                    int number_of_bulls = result[0];
                    int number_of_cows = result[1];


                    if (number_of_bulls == 0 && number_of_cows == 0) {
                        System.out.println("Grade: None.");
                    } else {
                        if (number_of_bulls > 0 && number_of_cows > 0) {
                            System.out.println("Grade: " + number_of_bulls + " bull(s) and " + number_of_cows + " cow(s).");
                        }else if(number_of_bulls == 0 && number_of_cows > 0) {
                            System.out.println("Grade: " + number_of_cows + " cow(s).");
                        }else if (number_of_bulls == secretCode.length()) {
                            System.out.println("Grade: " + number_of_bulls + " bull(s).");
                            System.out.println("Congratulations! You guessed the secret code.");
                            status = false;
                        }else if(number_of_bulls > 0 && number_of_cows == 0) {
                            System.out.println("Grade: " + number_of_bulls + " bull(s).");
                        }
                    }
                    turn++;
                }else{
                    System.out.println("Invalid Guess");
                }

            }

            scanner.close();

        } catch (Exception e) {
            // TODO: handle exception
            System.out.printf("Error: %s isn't a valid number.", leng);
            return;
        }

    }






    public static String randomNumber(int length, int charlength){

        String uniqueNumber;
        StringBuilder uniqueNumberBuilder = new StringBuilder();

        if (length > 36) {
            System.out.printf("Error: can't generate a secret number with a length of %d because there aren't enough unique digits.%n", length);
        } else {


            //Generate String of Characters
            String alphabets = "abcdefghijklmnopqrstuvwxyz";


            String[] Chars = alphabets.split("");





            // Generate an array of digits 0 to 9
            String [] digits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

            int excessNum = charlength > digits.length ? charlength - digits.length: 0;
            //System.out.println(excessNum);

            String[] selectedAlphabets  = new String[excessNum];

            if (excessNum > 0){
                for(int i = 0; i < excessNum; i++){
                    selectedAlphabets[i] = Chars[i];
                }
            }
            //System.out.println(digits);

            // Shuffle the array to randomize the digits

            int totalLength = digits.length + selectedAlphabets.length;
            //System.out.println(totalLength);
            String[] newArray = new String[totalLength];

            for(int i = 0; i < digits.length; i++){
                newArray[i] = digits[i];
            }

            for(int i = 0; i < selectedAlphabets.length; i++){
                newArray[digits.length + i] = selectedAlphabets[i];
            }

            shuffleArray(newArray);
            //System.out.println();

            // for(String x : selectedAlphabets){
            //      System.out.println(x);
            //  }
            //  System.out.println();
            //  for(String x : digits){
            //     System.out.println(x);
            // }
            // System.out.println();

            // for(String x : newArray){
            //     System.out.println(x);
            // }



            // Build the unique number using the shuffled digits
            //StringBuilder uniqueNumberBuilder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                uniqueNumberBuilder.append(newArray[i]);
            }

//            uniqueNumber = uniqueNumberBuilder.toString();
//            System.out.println("The random secret number is " + uniqueNumber + ".");
//
//            return uniqueNumber;

        }
        // scanner.close();
        uniqueNumber = uniqueNumberBuilder.toString();
        //System.out.println(uniqueNumber);
        return uniqueNumber;
    }

    // Method to shuffle an array
    private static void shuffleArray(String[] array) {
        Random rnd = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Swap elements at i and index
            String temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }




    public static int[] secret(String SecretCode, String guess){
//        String SecretCode = "9305";
//
//        StringBuilder SecretCode = new StringBuilder();
//        for (int i = 0; i < 4; i++){
//            SecretCode.append((int) (Math.random() *10));
//        }

//        Scanner scanner = new Scanner(System.in);
//        System.out.print(">");
//        String guess = scanner.nextLine();


        int number_of_bulls = 0;
        int number_of_cows = 0 ;

        // String[] SecretArray = SecretCode.split("");
        // String[] guessArray = guess.split("");



        //compare the Numbers for similarity

        for (int i = 0 ; i < SecretCode.length(); i++){
            char secretDigit = SecretCode.charAt(i);
            char guessDigit = guess.charAt(i);


            if(secretDigit == guessDigit){
                number_of_bulls++;
            }else if(SecretCode.contains(String.valueOf(guessDigit))){
                number_of_cows++;
            }
        }

        int[] array = new int[2];

        array[0] = number_of_bulls;
        array[1] = number_of_cows;

        return array;

    }




}
