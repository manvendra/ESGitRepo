package com.manvendra.ft.app.persistance;

import com.manvendra.ft.app.persistance.model.ExpenseGroupVO;
import com.manvendra.ft.app.persistance.model.ExpenseItemVO;

public interface FinanceDao {

	public ExpenseGroupVO getGroupWithAllExpenses(long groupId);

	public ExpenseGroupVO getGroupWithExpenseForGivenPeriod(long groupId, String fromDate, String toDate);

	public long saveExpenseItem(ExpenseItemVO expenseItem);
}
