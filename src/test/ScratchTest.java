package test;

import static org.junit.Assert.*;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Optional;

import org.junit.Test;

public class ScratchTest {

	@Test
	public void test() {
		Integer test = 9;
		System.out.println(Optional.ofNullable(test).orElse(0));
		System.out.println(Optional.ofNullable(null).orElse(0));
		assertTrue(true);
		System.out.println("-------------------------------------------------------------");
		Rectangle rect = new Rectangle(-1,-1,2,2);
		Point point = new Point(-2,-2);
		System.out.println(String.format("rect: x=%d, y=%d, w=%d, h=%d", rect.x, rect.y, rect.width, rect.height));
		rect.add(point);
		System.out.println(String.format("rect: x=%d, y=%d, w=%d, h=%d", rect.x, rect.y, rect.width, rect.height));
	}

}
