package edu.brandeis.cs12b.pa06;

import java.util.ArrayList;
import java.util.*;

import sample.PasswordGenerator;
import sample.PasswordGenerator.Mode;

public class Hacks { 
	public static final StringBuilder POSSIBLECHAR1 = new StringBuilder("0123456789");
	public static final StringBuilder POSSIBLECHAR2 = new StringBuilder("0123456789abcdefghijklmnopqrstuvwxyz");
	public static final int N = 5; // run 5 times to get a average runTime when testing a certain character in the password
	   								
	/**
	 * This main method might be useful for debugging. It tests both of your hacks
	 * and prints the results to the console.
	 * 
	 * @param args not used
	 */
	
	
	public static void main(String[] args) {
		Hacks h = new Hacks();		
		
		System.out.println("Calling hack1...");
		UserAuthenticator auth = new UserAuthenticator();
		
		h.hack1(auth);
		System.out.println("Result of logging in as Eliot with password 1234: " + auth.authenticateUser("Eliot", "1234"));
		System.out.println();
		
		System.out.println("Calling hack2...");
		auth = new UserAuthenticator();
		h.hack2(auth);
		try {
			auth.authenticateUser("Bob", "test");
			System.out.println("User authenticator still working...");
		} catch (Exception e) {
			System.out.println("User authenticator is broken!");
		}
		System.out.println();

		
		System.out.println("Calling timingAttack...");
		PasswordGenerator password = new PasswordGenerator();
	//	System.out.println("Initial password is 0000");

		// Generated passwords will contain digits and letters
		password.setDigitsAndLowercaseLetters();
		
		// Generated passwords will contain digits only
		password.setDigitsOnly();
		
		// Generated passwords will contain 2 characters
		password.setPasswordLength(14);
		
		// Generate a random password
		password.setRandomPassword();
	
		String result = h.timingAttack(password);
		System.out.println("The password is: "+result);
		System.out.println(password.checkPassword(result));
		
		
	}	
	
 

	
	/**
	 * For the first hack, you'll use the UserAuthenticator::addRegularUser method
	 * in order to add a user with "admin" status. Think about how the addRegularMethod works.
	 * 
	 * This method should make it so that a subsequent call to authenticateUser("Eliot", "1234")
	 * will return "admin".
	 * 
	 * @param auth the UserAuthenticator object to hack
	 */
	public void hack1(UserAuthenticator auth) {	
		auth.addRegularUser("Eliot//1234//admin&&Alice", "2222" );
	}
	
	/**
	 * For the second hack, you'll use the UserAuthenticator::addRegularUser method
	 * in order to completely break the authenticator, so it throws an exception instead of
	 * properly returning.
	 * 
	 * @param auth the UserAuthenticator object to break
	 */
	public void hack2(UserAuthenticator auth) {
		auth.addRegularUser("//", "&&");
	}
	
	/**
	 * exploit the Timing Attack and return the correct password.
	 * 
	 * @param pass the PasswordGenerator object
	 * @return correct password
	 */
	
	public String timingAttack(PasswordGenerator pass) {
		for (int x=0; x<this.N;x++) {// run the whole method N times and stops running when the correct password is found
			String password;
			StringBuilder currentGuess = new StringBuilder();
			for(int i=0; i<pass.getLength();i++) {
				currentGuess.append(0);	// create a StringBuilder with the same length of the correct password's length	
			}	
			
			if(pass.getMode().equals(PasswordGenerator.Mode.DIGITS_ONLY)){// test what Mode of the correct password is 
				password = checkWholePassword(pass,1,currentGuess) ;
			}else {
				password = checkWholePassword(pass,2,currentGuess) ;
			}
			
			if (pass.checkPassword(password)) {// check if the correct password is found
				System.out.println("Got the correct password: "+ password);
				return password;
			}	
		}	
		return "Can't find the password";
	}
	
	
	
	public String checkWholePassword(PasswordGenerator pass,int n, StringBuilder currentGuess) {
		StringBuilder checkBase;
		if(n==1){// choose the corresponding checkBase
			checkBase = POSSIBLECHAR1;
		}else {
			checkBase = POSSIBLECHAR2;
		}
		String password=null;
		for (int i=0; i<pass.getLength();i++) {// testing the character on each position
			System.out.println();
			System.out.println("now testing position NO."+(i+1));
			currentGuess.replace(0, currentGuess.length(), checkThisPosition(pass,checkBase, currentGuess,i));
			System.out.println("currentGuess in the wholePass method:"+ currentGuess);
			password = currentGuess.toString();
			if(pass.checkPassword(password)) {
				return password;
			}			
		}
		return password;
	}
	
	
	
	
	public String checkThisPosition(PasswordGenerator pass, StringBuilder checkBase, StringBuilder currentPass, int i) {
		long startTime, stopTime;
		long elapsedTime = 0;
		String testString = null;
		String guess = null; 
		long longestTime = 0;	
		for (int j=0;j<checkBase.length();j++) {
			currentPass.deleteCharAt(i);
			currentPass.insert(i, checkBase.charAt(j));
			testString = currentPass.toString();
			startTime = System.currentTimeMillis();
			for(int k=0;k<this.N+1;k++) {
				pass.checkPassword(testString);
			}
			stopTime = System.currentTimeMillis();
			elapsedTime = stopTime - startTime;
			System.out.println("Trying code "+testString+", runtime:"+elapsedTime);
			if(pass.checkPassword(testString)) {
				System.out.println("Got the correct password: "+ testString);
				return testString;
			}else if(elapsedTime > longestTime) {
				longestTime = elapsedTime;
				guess=testString;
			}
		}
		System.out.println("With the runTime "+longestTime+", "+guess+" is confirmed until position N0."+(i+1));
		return guess;
	}			
}
