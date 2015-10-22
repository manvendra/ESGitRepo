package com.manvendra.es.common.dto;

import java.util.Date;
import java.util.List;

public class ExpenseItem {

	private long						id;

	private Date						expenseDate;

	private String						description;

	private long						groupId;

	private List<ExpenseTransaction>	paidBy;

	private List<ExpenseTransaction>	paidFor;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getExpenseDate() {
		return expenseDate;
	}

	public void setExpenseDate(Date expenseDate) {
		this.expenseDate = expenseDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<ExpenseTransaction> getPaidBy() {
		// Collections.sort(this.paidByExpenses, new
		// ExpenseTransactionSorter<ExpenseTransaction>());
		return paidBy;
	}

	public void setPaidBy(List<ExpenseTransaction> paidBy) {
		this.paidBy = paidBy;
	}

	public List<ExpenseTransaction> getPaidFor() {
		return paidFor;
	}

	public void setPaidFor(List<ExpenseTransaction> paidFor) {
		this.paidFor = paidFor;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	/*
	 * private class ExpenseTransactionSorter<ExpenseTransaction> implements
	 * Comparator<ExpenseTransaction> {
	 * 
	 * @Override public int compare(ExpenseTransaction o1, ExpenseTransaction
	 * o2) { ((ExpenseTransaction)o1). } }
	 */

}
