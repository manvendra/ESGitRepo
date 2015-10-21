package com.manvendra.ft.app.persistance.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "EXPENSE_GROUP")
public class ExpenseGroupVO {

	private long				id;
	private String				name;
	private String				description;
	private List<ParticipantVO>	participants	= new ArrayList<ParticipantVO>();
	private List<ExpenseItemVO>	expenseItemVOs	= new ArrayList<ExpenseItemVO>();

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "EXPENSE_GROUP_ID")
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

	@Column(name = "DESCRIPTION", length = 50, unique = true)
	public String getDescription() {
		return description;
	}
	public void setDescription(String groupDescription) {
		this.description = groupDescription;
	}

	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "EXPENSE_GROUP_PARTICIPANT",
			joinColumns = @JoinColumn(name = "EXPENSE_GROUP_ID"),
			inverseJoinColumns = @JoinColumn(name = "PARTICIPANT_ID"))
	public List<ParticipantVO> getParticipants() {
		return participants;
	}
	public void setParticipants(List<ParticipantVO> participants) {
		this.participants = participants;
	}

	@OneToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "EXPENSE_GROUP_ID")
	public List<ExpenseItemVO> getExpenseItemVOs() {
		return expenseItemVOs;
	}
	public void setExpenseItemVOs(List<ExpenseItemVO> expenseItemVOs) {
		this.expenseItemVOs = expenseItemVOs;
	}

}
