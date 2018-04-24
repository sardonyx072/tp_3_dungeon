package test;

import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Test;

public class ScratchTest {

	@Test
	public void test() {
		Integer test = 9;
		System.out.println(Optional.ofNullable(test).orElse(0));
		assertTrue(true);
	}

}
