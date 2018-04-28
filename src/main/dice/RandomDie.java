package main.dice;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class RandomDie extends Die {
	public RandomDie(int[] values) {
		super(values);
	}
	public void roll() {
		int roll = IntStream.of(this.values).sum() > 0 ? ThreadLocalRandom.current().nextInt(1,IntStream.of(this.values).sum()+1) : 0;
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
	@Override
	public RandomDie clone() {
		RandomDie dup = new RandomDie(this.values.clone());
		dup.value = this.value;
		return dup;
	}
}
