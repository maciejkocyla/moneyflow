package org.mf.model;

import java.util.Date;

public class Transaction {
	
	private Category from;
	private Category to;
	private Date date;
	private Double amount;
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	public Category getFrom() {
		return from;
	}
	public void setFrom(Category from) {
		this.from = from;
	}
	public Category getTo() {
		return to;
	}
	public void setTo(Category to) {
		this.to = to;
	}
	
}
