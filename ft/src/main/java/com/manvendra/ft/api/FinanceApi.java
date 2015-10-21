package com.manvendra.ft.api;

import com.manvendra.ft.common.dto.ExpenseGroup;
import com.manvendra.ft.common.dto.ExpenseItem;

public interface FinanceApi {

	public ExpenseGroup getGroupWithAllExpenses(long groupId);
	public ExpenseGroup getGroupExpensesForPeriod(long groupId, String fromDate, String toDate);

	public long saveExpenseItem(ExpenseItem expenseItem);
}
