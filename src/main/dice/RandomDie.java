package main.dice;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class RandomDie extends Die {
	private static final long serialVersionUID = -7562593438169587021L;
	public RandomDie(int[] values) {
		super(values);
	}
	public void roll() {
		int roll = ThreadLocalRandom.current().nextInt(this.values.length>1 ? 1 : 0,IntStream.of(this.values).sum()+1);
		int sum = 0;
		for (int i = 0; i < this.values.length; i++) {
			if (roll <= (sum+=this.values[i])) {
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
