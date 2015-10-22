package com.manvendra.ft.app.persistance.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "PARTICIPANT")
public class ParticipantVO {

	public long							id;
	public String						name;
	public String						email;
	public String						phoneNumber;
	private List<ExpenseTransactionVO>	expenseTransactions	= new ArrayList<ExpenseTransactionVO>();

	public ParticipantVO() {
		// TODO Auto-generated constructor stub
	}
	public ParticipantVO(long id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PARTICIPANT_ID")
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "NAME", length = 50)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "PHONE", length = 14)
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@OneToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "PARTICIPANT_ID")
	public List<ExpenseTransactionVO> getExpenseTransactions() {
		return expenseTransactions;
	}
	public void setExpenseTransactions(List<ExpenseTransactionVO> expenseTransactions) {
		this.expenseTransactions = expenseTransactions;
	}
}
