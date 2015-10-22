package com.manvendra.es.app.persistance;

import com.manvendra.es.app.persistance.model.ExpenseGroupVO;
import com.manvendra.es.app.persistance.model.ExpenseItemVO;

public interface FinanceDao {

	public ExpenseGroupVO getGroupWithAllExpenses(long groupId);

	public ExpenseGroupVO getGroupWithExpenseForGivenPeriod(long groupId, String fromDate, String toDate);

	public long saveExpenseItem(ExpenseItemVO expenseItem);
}
