package org.mf.model;

public class Account extends Category{

	private long id;
	private Double amount;
	private boolean active;
	
	public Account(long id,String name){
		super(name);
		this.id=id;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}
