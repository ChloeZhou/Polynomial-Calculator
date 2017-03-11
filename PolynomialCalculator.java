// Name: Keying Zhou
// USC loginid: 1935-0418-72
// CS 455 PA2
// Spring 2017


import java.util.Scanner;

public class PolynomialCalculator {


    public static void main(String[] args){
        /*
         *  Print the Intro Message
         */
        System.out.println("Intro Message");
        System.out.println("There are total five commands that you can use. They are create, print, add, eval and quit.");
        System.out.println("1. create <index of the polynomial>--Create a new polynomial.");
        System.out.println("2. print <index of the polynomial>--Print the specific polynomial.");
        System.out.println("3. add <index of the result polynomial> <index of operand 1> <index of operand 2>--Calculate the sum of two polynomial.");
        System.out.println("4. eval <index of the polynomial>--Evaluate a polynomial for a specific floating point value of x.");
        System.out.println("5. quit --Exit the program.");
        System.out.print("cmd>");
        
        Polynomial[] polys = new Polynomial[10];   //create an array of 10 polynomials
        /*
         *  Initialize the array of polynomials
         */
        for (int i = 0; i < polys.length; i++){
        	polys[i] = new Polynomial();
        }
        
        Scanner in = new Scanner(System.in);

        while(true) {
            
        	String operation = in.next();  // read user's command
            /*
             *  create a polynomial
             */
            if(operation.equalsIgnoreCase("create")){
                int index = in.nextInt();        // read the index of the polynomial 
                if( index < polys.length && index >= 0) {  // check the index is valid or not
                    polys[index] = create();     // create the polynomial
                    System.out.print("cmd>");
                }else{
                    System.out.println("ERROR: illegal index for a poly.  must be between 0 and 9, inclusive"); // if the index is invalid, print the error information
                    System.out.print("cmd>");
                }

            }
            /*
             *  Print the specific polynomial
             */
            else if(operation.equalsIgnoreCase("print")){
                int index = in.nextInt();
                if(index < polys.length && index >= 0) {
                    System.out.println(polys[index].toFormattedString()); // print the polynomial
                    System.out.print("cmd>");
                }else{
                    System.out.println("ERROR: illegal index for a poly.  must be between 0 and 9, inclusive");
                    System.out.print("cmd>");
                }
            }
            /*
             *  Calculate the sum of two polynomial
             */
            else if(operation.equalsIgnoreCase("add")){
            	//read all the index of the polynomials
                int index = in.nextInt();
                int operand1 = in.nextInt();
                int operand2 = in.nextInt();
                
                if(index < polys.length &&  index >= 0 && operand1 < polys.length && operand1 >= 0 && operand2 < polys.length && operand2 >= 0) { // check the index
                    polys[index] = (polys[operand1].add(polys[operand2]));   // add two polynomial
                    System.out.print("cmd>");
                }else{
                    System.out.println("ERROR: illegal index for a poly.  must be between 0 and 9, inclusive");
                    System.out.print("cmd>");
                }
            }
            /*
             *  Evaluate a polynomial for a specific floating point value of x
             */
            else if(operation.equalsIgnoreCase("eval")){
                int index = in.nextInt();
                if (index < 10 && index >= 0) {
                    System.out.print("Enter a floating point value for x:");
                    System.out.println(polys[index].eval(in.nextDouble()));
                    System.out.print("cmd>");
                }else{
                    System.out.println("ERROR: illegal index for a poly.  must be between 0 and 9, inclusive");
                    System.out.print("cmd>");
                }
            }
            /*
             *  exit the code
             */
            else if(operation.equalsIgnoreCase("quit")){
                return;
            }
            /*
             *  print Intro Message
             */
            else if(operation.equalsIgnoreCase("help")){
                System.out.println("Intro Message");
                System.out.println("There are total five commands that you can use. They are create, print, add, eval and quit.");
                System.out.println("1. create <index of the polynomial>--Create a new polynomial.");
                System.out.println("2. print <index of the polynomial>--Print the specific polynomial.");
                System.out.println("3. add <index of the result polynomial> <operand 1> <operand 2>--Calculate the sum of operator 1 and operator 2");
                System.out.println("4. eval <index of the polynomial>--Evaluate a polynomial for a specific floating point value of x.");
                System.out.println("5. quit --Exit the program.");
                System.out.print("cmd>");
            }else{
                System.out.println("ERROR: Illegal command. Type 'help' for command options.");   // if the command is not valid, print the error message.
                System.out.print("cmd>");
            }
        }
    }

    // **************************************************************
    //  PRIVATE METHOD(S)

    /**
     Create a polynomial in the array of polynomials.
     */

    private static Polynomial create() {
        System.out.println("Enter a space-separated sequence of coeff-power pairs terminated by <nl>");
        
        int counter = 1;      // use a counter to decide the type (integer of double) of the input digit
        double coeff = 0.0;
        int expon;
        
        Polynomial polyReturn = new Polynomial();
        
        Scanner in = new Scanner(System.in);
        Scanner lineScanner = new Scanner(in.nextLine());
        
        while (lineScanner.hasNext()){
        	
		    if(counter % 2 != 0){ // judge the type of the digit. if the counter is odd, it's a double type
		    	coeff = lineScanner.nextDouble();    // read in the double type digit
			    counter++;
			}else if (counter % 2 == 0){// judge the type of the digit. if the counter is even, it's an integer
			    expon = lineScanner.nextInt();     // read in the integer
			    if(expon < 0){                                         // check the exponent
			    	System.out.println("WARNING: Negative exponent."); // print out warning message if the exponent is invalid
			        expon = -1*expon;                                  // fix the exponent by using absolute value
			    }
			        polyReturn = polyReturn.add(new Polynomial(new Term(coeff, expon)));
			        counter++;
			}
        }
        if(counter % 2 == 0){                                          // check the input is complete or not
            System.out.println("WARNING: Missing the last exponent."); // if the last exponent is missing, print a warning message
        }
    	return polyReturn;
    }
}