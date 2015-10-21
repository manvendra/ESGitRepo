package com.manvendra.ft.app.persistance.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "EXPENSE_ITEM")
public class ExpenseItemVO {

	private long						id;
	private Date						expenseDate;
	private String						description;
	private ExpenseGroupVO				expenseGroupVO;
	private List<ExpenseTransactionVO>	expenseTransactions	= new ArrayList<ExpenseTransactionVO>();

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "EXPENSE_ITEM_ID")
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "EXPENSE_DATE")
	public Date getExpenseDate() {
		return expenseDate;
	}
	public void setExpenseDate(Date expenseDate) {
		this.expenseDate = expenseDate;
	}

	@Column(name = "DESCRIPTION", length = 50)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "EXPENSE_ITEM_ID", referencedColumnName = "EXPENSE_ITEM_ID")
	@LazyCollection(LazyCollectionOption.FALSE)
	public List<ExpenseTransactionVO> getExpenseTransactions() {
		return expenseTransactions;
	}
	public void setExpenseTransactions(List<ExpenseTransactionVO> expenseTransactions) {
		this.expenseTransactions = expenseTransactions;
	}

	@ManyToOne
	@JoinColumn(name = "EXPENSE_GROUP_ID")
	@LazyCollection(LazyCollectionOption.FALSE)
	public ExpenseGroupVO getExpenseGroupVO() {
		return expenseGroupVO;
	}
	public void setExpenseGroupVO(ExpenseGroupVO expenseGroupVO) {
		this.expenseGroupVO = expenseGroupVO;
	}

}
