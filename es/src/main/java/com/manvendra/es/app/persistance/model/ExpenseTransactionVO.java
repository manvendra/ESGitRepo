package com.manvendra.es.app.persistance.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "EXPENSE_TRANSACTION")
public class ExpenseTransactionVO {

	private long			id;
	private ParticipantVO	participant;
	private String			transactionType;
	private long			expenseAmont;
	private ExpenseItemVO	expenseItemVO;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "EXPENSE_TRANSACTION_ID")
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "PARTICIPANT_ID")
	public ParticipantVO getParticipant() {
		return participant;
	}
	public void setParticipant(ParticipantVO participant) {
		this.participant = participant;
	}

	@Column(name = "TRANSACTION_TYPE", columnDefinition = "PAID_BY OR PAID FOR")
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	@Column(name = "AMOUNT")
	public long getExpenseAmont() {
		return expenseAmont;
	}
	public void setExpenseAmont(long expenseAmont) {
		this.expenseAmont = expenseAmont;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "EXPENSE_ITEM_ID")
	public ExpenseItemVO getExpenseItemVO() {
		return expenseItemVO;
	}
	public void setExpenseItemVO(ExpenseItemVO expenseItemVO) {
		this.expenseItemVO = expenseItemVO;
	}

}
