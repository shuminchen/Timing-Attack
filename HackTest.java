package edu.brandeis.cs12b.pa06;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.brandeis.cs12b.pa06.Hacks;
import edu.brandeis.cs12b.pa06.UserAuthenticator;
import sample.PasswordGenerator;

public class HackTest {

	private UserAuthenticator auth;
	private Hacks h;
	
	@Before
	public void setUp() throws Exception {
		auth = new UserAuthenticator();
		h = new Hacks();

	}
	
	@Test
	public void hack1Test() {
		h.hack1(auth);
		assertEquals(auth.authenticateUser("Eliot", "1234"), "admin");
		
	}
	
	@Test
	public void hack2Test() {
		h.hack2(auth);
		try {
			auth.authenticateUser("Bob", "test");
			fail("User authenticator still working...");
		} catch (Exception e) {
			// pass!
			return;
		}
		
	}
	
/*	
	@Test
	public void timingAttackTest1() {
		PasswordGenerator password = new PasswordGenerator();
		password.setDigitsOnly();
		password.setPasswordLength(3);
		password.setRandomPassword();
		
		assertTrue(password.checkPassword(h.timingAttack(password)));
	}
	
	
	@Test
	public void timingAttackTest2() {
		PasswordGenerator password = new PasswordGenerator();
		password.setDigitsAndLowercaseLetters();
		password.setPasswordLength(3);
		password.setRandomPassword();
		
		assertTrue(password.checkPassword(h.timingAttack(password)));
	}
	
	@Test
	public void timingAttackTest3() {
		PasswordGenerator password = new PasswordGenerator();
		password.setDigitsOnly();
		password.setPasswordLength(5);
		password.setRandomPassword();
		
		assertTrue(password.checkPassword(h.timingAttack(password)));
	}
	
	
	@Test
	public void timingAttackTest4() {
		PasswordGenerator password = new PasswordGenerator();
		password.setDigitsAndLowercaseLetters();
		password.setPasswordLength(5);
		password.setRandomPassword();
		
		assertTrue(password.checkPassword(h.timingAttack(password)));
	}
	
	
	
	@Test
	public void timingAttackTest5() {
		PasswordGenerator password = new PasswordGenerator();
		password.setDigitsAndLowercaseLetters();
		password.setPasswordLength(10);
		password.setRandomPassword();
		
		assertTrue(password.checkPassword(h.timingAttack(password)));
	}
	

	@Test
	public void timingAttackTest6() {
		PasswordGenerator password = new PasswordGenerator();
		password.setDigitsOnly();
		password.setPasswordLength(10);
		password.setRandomPassword();
		
		assertTrue(password.checkPassword(h.timingAttack(password)));
	}
	

	
	@Test
	public void timingAttackTest7() {
		PasswordGenerator password = new PasswordGenerator();
		password.setDigitsOnly();
		password.setPasswordLength(15);
		password.setRandomPassword();
		
		assertTrue(password.checkPassword(h.timingAttack(password)));
	}
	
	
	*/
}
