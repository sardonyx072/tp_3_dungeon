package main.dice;

import java.util.Collections;

public class FairRandomDie extends RandomDie {
	public FairRandomDie(int sides) {
		super(Collections.nCopies(sides+1, 1).stream().mapToInt(i -> i.intValue()).toArray());
		this.values[0] = 0; //can't figure out a better way
	}
}