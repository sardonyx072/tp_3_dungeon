package main.dice;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

public class Constant extends RandomDie {
	private static final long serialVersionUID = -1837741849316147749L;
	public Constant (int value) {
		super(Stream.of(Collections.nCopies(value, 0).stream().mapToInt(i -> i.intValue()).toArray(), new int[] {1}).flatMapToInt(Arrays::stream).toArray());
	}
	@Override
	public Constant clone() {
		return new Constant(this.getValue());
	}
}
