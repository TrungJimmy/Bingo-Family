package com.arun.rajora.chat.chit.bingo.family.bingofamily.Model;


public class Transaction {

	private String drawn;
	private String amount;
	private String source;
	private String category;
	private String time;
	private String notes;

	public Transaction() {
	}

	public Transaction(String drawn, String amount, String source, String category, String time, String notes) {
		this.drawn = drawn;
		this.amount = amount;
		this.source = source;
		this.category = category;
		this.time = time;
		this.notes = notes;
	}

	public String getDrawn() {
		return drawn;
	}

	public String getAmount() {
		return amount;
	}

	public String getSource() {
		return source;
	}

	public String getCategory() {
		return category;
	}

	public String getTime() {
		return time;
	}

	public String getNotes() {
		return notes;
	}

	public void setDrawn(String drawn) {
		this.drawn = drawn;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
}
