package main.dice;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

public class FairRandomDie extends RandomDie {
	private static final long serialVersionUID = -8854518259500313101L;
	public FairRandomDie(int sides) {
//		super(Collections.nCopies(sides+1, 1).stream().mapToInt(i -> i.intValue()).toArray());
//		this.values[0] = 0; //can't figure out a better way
		super(Stream.of(new int[] {0},Collections.nCopies(sides, 1).stream().mapToInt(i -> i.intValue()).toArray()).flatMapToInt(Arrays::stream).toArray());
	}
	@Override
	public FairRandomDie clone() {
		FairRandomDie dup = new FairRandomDie(this.values.length-1);
		dup.value = this.value;
		return dup;
	}
}
