package com.manvendra.ft.common.dto;

import java.util.List;

public class ExpenseGroup {

	private long				id;

	private String				description;

	private String				name;

	private List<Participant>	participants;

	private List<ExpenseItem>	expenseItem;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String groupDescription) {
		this.description = groupDescription;
	}

	public List<Participant> getParticipants() {
		return participants;
	}

	public void setParticipants(List<Participant> participants) {
		this.participants = participants;
	}

	public List<ExpenseItem> getExpenseItem() {
		return expenseItem;
	}

	public void setExpenseItem(List<ExpenseItem> expenseItem) {
		this.expenseItem = expenseItem;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
