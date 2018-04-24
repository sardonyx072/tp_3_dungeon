package main.items;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Optional;

public class Coin {
	public static enum Type {
		IRON(1),
		TIN(10),
		COPPER(100),
		SILVER(1000),
		ELECTRUM(10000),
		ALUMINUM(100000),
		GOLD(1000000),
		PLATINUM(10000000);
		private int worth;
		private Type(int worth) {
			this.worth = worth;
		}
		public int getWorth() {return this.worth;}
		public Coin create(int num) {return new Coin();}
	}
	private HashMap<Type,Integer> coins;
	public Coin() {
		this.coins = new HashMap<Type,Integer>();
	}
	public Coin(Type type, int amount) {
		this();
		this.add(type,amount);
	}
	public int get(Type type) {return this.coins.get(type);}
	public void add(Type type, int amount) {this.coins.put(type, Optional.ofNullable(this.coins.get(type)).orElse(0) + amount);}
	public void add(Coin coin) {
		for (Type type : coin.coins.keySet())
			this.add(type, coin.coins.get(type));
	}
	public Coin remove(Type type, int amount) {
		int removed = Math.min(amount, Optional.ofNullable(this.coins.get(type)).orElse(0));
		if (removed > 0) {
			this.coins.put(type, Optional.ofNullable(this.coins.get(type)).orElse(0)-removed);
			if (this.coins.get(type) == 0)
				this.coins.remove(type);
			return new Coin(type,removed);
		}
		return new Coin();
	}
	public Coin remove(Coin coin) {
		Coin removed = new Coin();
		for (Type type : coin.coins.keySet())
			removed.add(this.remove(type, coin.get(type)));
		return removed;
	}
	public long getValue() {return this.coins.keySet().stream().mapToLong(type -> this.coins.get(type)*(long)type.getWorth()).sum();}
	public void crunch() {
		HashMap<Type,Integer> replace = new HashMap<Type,Integer>();
		long value = this.getValue();
		Type[] types = Type.values();
		Arrays.sort(types, new Comparator<Type>() {
			@Override
			public int compare(Type type1, Type type2) {return type2.getWorth() - type1.getWorth();}
		});
		for (Type type : types) {
			int amount = (int)(value/type.getWorth());
			value = value%type.getWorth();
			if (amount > 0)
				replace.put(type, amount);
		}
		this.coins = replace;
	}
}
