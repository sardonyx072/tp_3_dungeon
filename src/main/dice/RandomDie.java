package main.dice;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class RandomDie extends Die {
	public RandomDie(int[] values) {
		super(values);
	}
	public void roll() {
		int roll = ThreadLocalRandom.current().nextInt(1,IntStream.of(this.values).sum()+1);
		int sum = 0;
		for (int i = 0; i < this.values.length; i++) {
			sum+=this.values[i];
			if (roll <= sum) {
				this.value = i;
				break;
			}
		}
	}
	public int getValue() {return this.value;}
}
