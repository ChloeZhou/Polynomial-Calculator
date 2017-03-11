// Name: Keying Zhou
// USC loginid: 1935-0418-72
// CS 455 PA2
// Spring 2017


import java.util.ArrayList;

/**
 A polynomial. Polynomials can be added together, evaluated, and
 converted to a string form for printing.
 */
public class Polynomial {
	
    private ArrayList<Term> terms;  //store the terms of the polynomial

    /**
    	Representation invariants
    	-- for non-zero poly. all the terms must be in decreasing order by exponents.
    	-- for non-zero poly. all the exponents must be different.
    	-- for non-zero poly. all the coefficients are not zero.
    	-- for zero poly. the ArrayList contains empty term element.
    	-- exponents cannot be negative.
    */
    /**
     Creates the 0 polynomial
     */
    public Polynomial() {
        terms = new ArrayList<Term>();
        assert isValidPolynomial();    //check the polynomial
    }


    /**
     Creates polynomial with single term given
     */
    public Polynomial(Term term) {
       
        terms = new ArrayList<Term>();
        if (term.getCoeff() != 0) {     //add terms to the polynomial. If the coefficient is zero, leave the list empty.
            terms.add(term);
        }
        assert isValidPolynomial();   // check the polynomial
    }


    /**
     Returns the Polynomial that is the sum of this polynomial and b
     (neither poly is modified)
     */
    public Polynomial add(Polynomial b) {
        Polynomial sum = new Polynomial();          //create a new polynomial to store the sum
        /*
         Check the polynomials
        */
        
        int thisPointer = 0;   //the index of this polynomial
        int bPointer = 0;      //the index of the b polynomial
        /*
         Add the two polynomial to the sum polynomial, order the terms from highest power to lowest and combine the terms with the same power.
       */
        while (thisPointer < this.terms.size() || bPointer < b.terms.size()){
            if (thisPointer != this.terms.size() && bPointer != b.terms.size() ) { 
                if (this.terms.get(thisPointer).getExpon() < b.terms.get(bPointer).getExpon()) { // order the terms in decreasing power
                    sum.terms.add(b.terms.get(bPointer));         // add polynomial b to the result 
                    bPointer++;
                } else if (this.terms.get(thisPointer).getExpon() > b.terms.get(bPointer).getExpon()) {
                    sum.terms.add(terms.get(thisPointer));       // add this polynomial to the result
                    thisPointer++;
                } else {                                        // combine the terms with same exponent
                    Term termEqualExpon = new Term(this.terms.get(thisPointer).getCoeff() + b.terms.get(bPointer).getCoeff(), b.terms.get(bPointer).getExpon());
                    if (termEqualExpon.getCoeff() != 0) {       // after combination, if the coefficient is not zero, add the term to sum polynomial
                        sum.terms.add(termEqualExpon);
                    }
                    bPointer++;
                    thisPointer++;
                }
            }
            if (thisPointer == this.terms.size() && bPointer != b.terms.size()){   //when there is no more terms in this polynomial, just add the rest terms of b polynomial, no need to compare
                sum.terms.add(b.terms.get(bPointer));
                bPointer++;
            }
            if (bPointer == b.terms.size() && thisPointer != this.terms.size()){  //when there is no more terms in b polynomial, just add the rest terms of this polynomial, no need to compare
                sum.terms.add(this.terms.get(thisPointer));
                thisPointer++;
            }
        }
        
        assert isValidPolynomial();
        assert b.isValidPolynomial();
        assert sum.isValidPolynomial();
        
        return sum; 
    }


    /**
     Returns the value of the poly at a given value of x.
     */
    public double eval(double x) {
        assert isValidPolynomial();
        double value = 0;
        for(int i = 0; i < terms.size(); i++) {
            value += terms.get(i).getCoeff() * Math.pow(x, terms.get(i).getExpon()); // add up the values of every terms
        }
        return value;         
    }


    /**
     Return a String version of the polynomial with the
     following format, shown by example:
     zero poly:   "0.0"
     1-term poly: "3.2x^2"
     4-term poly: "3.0x^5 + -x^2 + x + -7.9"

     Polynomial is in a simplified form (only one term for any exponent),
     with no zero-coefficient terms, and terms are shown in
     decreasing order by exponent.
     */
    public String toFormattedString() {
        assert isValidPolynomial();   // check the polynomial
        String result = "";         //the result of transferring a polynomial into string format
        if (terms.size() == 0){     // if the polynomial is empty, return "0.0"
            result = "0.0";
            return result;
        }
        for (int i = 0; i < terms.size() - 1; i++) {
            /*
             We need to output different items when the coefficients and exponents are different.
             There are several special cases: 1. coefficient equal to 1 and -1
             								  2. exponent equal to 1 
             								  3. exponent equal to 0 (which means the term is a constant)
             								  4. no "+ " after the last term of the polynomial
           */
            if (terms.get(i).getCoeff() == 1 && terms.get(i).getExpon() == 1 ){   
            	result += "x" + " + ";
            }
            else if(terms.get(i).getCoeff() == 1 && terms.get(i).getExpon() != 1){
            	result += "x^" + terms.get(i).getExpon() + " + ";
            }else if(terms.get(i).getCoeff() == -1 && terms.get(i).getExpon() == 1){
            	result += "-x" + " + ";
            }else if(terms.get(i).getCoeff() == -1 && terms.get(i).getExpon() != 1){
            	result += "-x^" + terms.get(i).getExpon() + " + ";
            }else{
            	
            	if(terms.get(i).getExpon() == 1){
            		result += terms.get(i).getCoeff() + "x" + " + ";
            	}else{
            		result += terms.get(i).getCoeff() + "x^" + terms.get(i).getExpon() + " + ";
            	}
            }
        }
        
        if (terms.get(terms.size() - 1).getExpon() != 0) {
            if (terms.get(terms.size() - 1).getCoeff() == 1 && terms.get(terms.size() - 1).getExpon() == 1 ){
            	result += "x";
            }
            else if(terms.get(terms.size() - 1).getCoeff() == 1 && terms.get(terms.size() - 1).getExpon() != 1){
            	result += "x^" + terms.get(terms.size() - 1).getExpon() ;
            }else if(terms.get(terms.size() - 1).getCoeff() == -1 && terms.get(terms.size() - 1).getExpon() == 1){
            	result += "-x";
            }else if(terms.get(terms.size() - 1).getCoeff() == -1 && terms.get(terms.size() - 1).getExpon() != 1){
            	result += "-x^" + terms.get(terms.size() - 1).getExpon() ;
            }
            else{
            	if(terms.get(terms.size() - 1).getExpon() == 1){
            		result += terms.get(terms.size() - 1).getCoeff() + "x" ;
            	}else{
            		result += terms.get(terms.size() - 1).getCoeff() + "x^" + terms.get(terms.size() - 1).getExpon();
            	}
            }
        }else{
            result += terms.get(terms.size() - 1).getCoeff();
        }

        return result;        
    }

    // **************************************************************
    //  PRIVATE METHOD(S)

    /**
     Returns true iff the poly data is in a valid state.
     */

    private boolean isValidPolynomial() {
            for (int i = 0; i < terms.size() - 1; i++ ){
                if(terms.get(i).getExpon() <= terms.get(i+1).getExpon()){  // check the order and combination
                    return false;
                }
            }
            for (int i = 0; i < terms.size(); i++){
                if(terms.get(i).getExpon() < 0){     // check the exponent >=0
                    return false;
                }
                if(terms.get(i).getCoeff() == 0){   // zero terms are not contained in the polynomial
                    return false;
                }
            }
        return true;     
    }
}

