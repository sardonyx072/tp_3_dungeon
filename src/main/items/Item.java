package main.items;

import main.Effect;

public abstract class Item {
	protected String name;
	protected int weight, cost;
	protected Effect[] effects;
	public Item (String name, int weight, int cost, Effect[] effects) {
		this.name = name;
		this.weight = weight;
		this.cost = cost;
		this.effects = effects != null ? effects : new Effect[] {};
	}
	public String getName() {return this.name;}
	public int getWeight() {return this.weight;}
	public int getCost() {return this.cost;}
	public Effect[] getEffects() {return this.effects;}
}
