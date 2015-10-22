package com.manvendra.es.api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.manvendra.es.app.persistance.model.ParticipantVO;
import com.manvendra.es.common.dto.ExpenseGroup;
import com.manvendra.es.common.dto.ExpenseItem;
@Component
@Path("/groupfinance")
public class GroupFinanceService {

	@Autowired
	private FinanceApi	financeApi;

	@GET
	@Produces("text/plain")
	public String getIt() {
		return "Hi there!";
	}

	@GET
	@Path("/expense")
	@Produces("application/json")
	public ExpenseGroup getGroupExpenseForPeriod(@QueryParam("groupId") long groupId, @QueryParam("fromDate") String toDate,
			@QueryParam("todate") String fromDate) {
		return financeApi.getGroupExpensesForPeriod(groupId, toDate, fromDate);
	}

	@POST
	@Path("/expenseItem")
	@Consumes("application/json")
	public long saveExpenseItem(ExpenseItem expenseItem) {
		return financeApi.saveExpenseItem(expenseItem);
	}

	public List<ParticipantVO> getAllExpenseForParticipant(long participantId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ParticipantVO> getExpenseGroupDetails(long groupId) {
		// TODO Auto-generated method stub
		return null;
	}

}
