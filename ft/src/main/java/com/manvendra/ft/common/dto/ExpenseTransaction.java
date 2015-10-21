package com.manvendra.ft.common.dto;

public class ExpenseTransaction {

	public long	participantId;

	public long	expenseAmont;

	public long getExpenseAmont() {
		return expenseAmont;
	}

	public void setExpenseAmont(long expenseAmont) {
		this.expenseAmont = expenseAmont;
	}

	public long getParticipantId() {
		return this.participantId;
	}

	public void setParticipantId(long participantId) {
		this.participantId = participantId;
	}

}
