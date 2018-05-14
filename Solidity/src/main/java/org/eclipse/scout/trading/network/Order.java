package org.eclipse.scout.trading.network;

/**
 * Simple order class
 */
public class Order {

	/**
	 * Order types BUY and SELL
	 */
	public enum Type {
		BUY, SELL
	}

	private int id;
	private Type type;
	private int amount;
	private double price;
	private String owner;

	public Order(Type type, int amount, double price) {
		this.type = type;
		this.setAmount(amount);
		this.setPrice(price);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		if (id < 0) {
			throw new RuntimeException("order id must not be negative");
		}

		this.id = id;
	}

	public Type getType() {
		return type;
	}

	protected void setType(Type type) {
		if (type == null) {
			throw new RuntimeException("order type must not be null");
		}

		this.type = type;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		if (amount <= 0) {
			throw new RuntimeException("order amount must be positive");
		}

		this.amount = amount;
	}

	public double getPrice() {
		return price;
	}

	protected void setPrice(double price) {
		if (price <= 0.0) {
			throw new RuntimeException("order price must be positive");
		}
		this.price = price;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		if (owner == null || owner.isEmpty()) {
			throw new RuntimeException("order owner must not be null or empty");
		}

		this.owner = owner;
	}
}
