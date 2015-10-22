package com.manvendra.es.api;

import com.manvendra.es.common.dto.ExpenseGroup;
import com.manvendra.es.common.dto.ExpenseItem;

public interface FinanceApi {

	public ExpenseGroup getGroupWithAllExpenses(long groupId);
	
	
	public ExpenseGroup getGroupExpensesForPeriod(long groupId, String fromDate, String toDate);

	public long saveExpenseItem(ExpenseItem expenseItem);
}
