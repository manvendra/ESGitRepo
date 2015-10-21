package com.manvendra.ft.app.business;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.manvendra.ft.api.CommonConstants;
import com.manvendra.ft.api.FinanceApi;
import com.manvendra.ft.app.persistance.FinanceDao;
import com.manvendra.ft.app.persistance.model.ExpenseGroupVO;
import com.manvendra.ft.app.persistance.model.ExpenseItemVO;
import com.manvendra.ft.app.persistance.model.ExpenseTransactionVO;
import com.manvendra.ft.app.persistance.model.ParticipantVO;
import com.manvendra.ft.common.dto.ExpenseGroup;
import com.manvendra.ft.common.dto.ExpenseItem;
import com.manvendra.ft.common.dto.ExpenseTransaction;
import com.manvendra.ft.common.dto.Participant;

@Component("financeApi")
public class FinanceApiImpl implements FinanceApi {

	@Autowired
	FinanceDao	financeDao;

	@Override
	public ExpenseGroup getGroupExpensesForPeriod(long groupId, String fromDate, String toDate) {

		ExpenseGroupVO expenseGroupVO = financeDao.getGroupWithAllExpenses(groupId);
		removeOutOfDateRangeExpenses(expenseGroupVO, fromDate, toDate);
		ExpenseGroup expenseGroup = convertFromVOtoDto(expenseGroupVO);
		return expenseGroup;
	}

	public ExpenseGroup getGroupWithAllExpenses(long groupId) {
		ExpenseGroupVO expenseGroupVO = financeDao.getGroupWithAllExpenses(groupId);
		ExpenseGroup expenseGroup = convertFromVOtoDto(expenseGroupVO);
		return expenseGroup;
	}

	@Override
	public long saveExpenseItem(ExpenseItem expenseItem) {
		// ExpenseGroupVO expenseGroupVO =
		// financeDao.getGroupWithAllExpenses()expenseItem.getGroupId());
		ExpenseItemVO expenseItemVO = convertFromDtoToVO(expenseItem);
		return financeDao.saveExpenseItem(expenseItemVO);
	}

	private ExpenseItemVO convertFromDtoToVO(ExpenseItem expenseItem) {
		ExpenseItemVO expenseItemVO = new ExpenseItemVO();
		expenseItemVO.setDescription(expenseItem.getDescription());
		expenseItemVO.setExpenseDate(expenseItem.getExpenseDate());

		ExpenseGroupVO expenseGroupVO = new ExpenseGroupVO();
		expenseGroupVO.setId(expenseItem.getGroupId());
		expenseItemVO.setExpenseGroupVO(expenseGroupVO);

		List<ExpenseTransactionVO> expenseTransactionVOs = new ArrayList<ExpenseTransactionVO>();
		List<ExpenseTransaction> expenseTransactions = expenseItem.getPaidBy();

		for (ExpenseTransaction expenseTransaction : expenseTransactions) {
			if (expenseTransaction.getExpenseAmont() != 0) {
				ExpenseTransactionVO expenseTransactionVO = new ExpenseTransactionVO();
				expenseTransactionVO.setExpenseAmont(expenseTransaction.getExpenseAmont());
				expenseTransactionVO.setTransactionType(CommonConstants.PAID_BY);
				expenseTransactionVO.setParticipant(new ParticipantVO(expenseTransaction.getParticipantId()));
				expenseTransactionVOs.add(expenseTransactionVO);
			}
		}

		expenseTransactions = expenseItem.getPaidFor();
		for (ExpenseTransaction expenseTransaction : expenseTransactions) {

			if (expenseTransaction.getExpenseAmont() != 0) {
				ExpenseTransactionVO expenseTransactionVO = new ExpenseTransactionVO();
				expenseTransactionVO.setExpenseAmont(expenseTransaction.getExpenseAmont());
				expenseTransactionVO.setTransactionType(CommonConstants.PAID_FOR);
				expenseTransactionVO.setParticipant(new ParticipantVO(expenseTransaction.getParticipantId()));
				expenseTransactionVO.setExpenseItemVO(expenseItemVO);
				expenseTransactionVOs.add(expenseTransactionVO);
			}
		}

		expenseItemVO.setExpenseTransactions(expenseTransactionVOs);
		return expenseItemVO;
	}

	private ExpenseGroup convertFromVOtoDto(ExpenseGroupVO expenseGroupVO) {

		ExpenseGroup expenseGroup = new ExpenseGroup();
		expenseGroup.setDescription(expenseGroupVO.getDescription());
		expenseGroup.setId(expenseGroupVO.getId());

		// ------------------Participants-------
		List<ParticipantVO> pariParticipantVOs = expenseGroupVO.getParticipants();
		List<Participant> participants = new ArrayList<Participant>();
		for (ParticipantVO participantVO : pariParticipantVOs) {
			Participant participant = new Participant();
			participant.id = participantVO.id;
			participant.name = participantVO.name;
			participant.email = participantVO.email;
			participant.phoneNumber = participantVO.phoneNumber;
			participants.add(participant);
		}
		Collections.sort(participants, perticipantComparator);
		expenseGroup.setParticipants(participants);
		// --------- Participants--------

		List<ExpenseItem> expenseItems = new ArrayList<ExpenseItem>();
		List<ExpenseItemVO> expenseItemVOs = expenseGroupVO.getExpenseItemVOs();
		for (ExpenseItemVO expenseItemVO : expenseItemVOs) {
			ExpenseItem expenseItem = new ExpenseItem();
			expenseItem.setId(expenseItemVO.getId());
			expenseItem.setDescription(expenseItemVO.getDescription());
			expenseItem.setExpenseDate(expenseItemVO.getExpenseDate());

			List<ExpenseTransaction> paidByTransactions = new ArrayList<ExpenseTransaction>();
			List<ExpenseTransaction> paidForTransactions = new ArrayList<ExpenseTransaction>();

			List<ExpenseTransactionVO> expenseTransactionVOs = expenseItemVO.getExpenseTransactions();
			for (ExpenseTransactionVO expenseTransactionVO : expenseTransactionVOs) {
				ExpenseTransaction expenseTransaction = new ExpenseTransaction();

				expenseTransaction.setExpenseAmont(expenseTransactionVO.getExpenseAmont());
				expenseTransaction.setParticipantId(expenseTransactionVO.getParticipant().getId());

				if (CommonConstants.PAID_BY.equalsIgnoreCase(expenseTransactionVO.getTransactionType())) {
					paidByTransactions.add(expenseTransaction);
				} else if (CommonConstants.PAID_FOR.equalsIgnoreCase(expenseTransactionVO.getTransactionType())) {
					paidForTransactions.add(expenseTransaction);
				} else {
					// Ignore the transaction. this should never occur
				}
			}

			// Not a nice design, It increases the json size.
			// need to enhance the code in javascript to paint the table for
			// missing transactions rather then sending all from server
			addZeroTransactionsFormissingParticipant(paidByTransactions, participants);
			addZeroTransactionsFormissingParticipant(paidForTransactions, participants);

			expenseItem.setPaidBy(paidByTransactions);
			expenseItem.setPaidFor(paidForTransactions);

			expenseItems.add(expenseItem);
		}
		expenseGroup.setExpenseItem(expenseItems);
		return expenseGroup;
	}

	// bad code high
	private void addZeroTransactionsFormissingParticipant(List<ExpenseTransaction> expenseTransactions, List<Participant> participants) {

		for (Participant participant : participants) {
			boolean participantFound = false;
			for (ExpenseTransaction expenseTransaction : expenseTransactions) {
				if (expenseTransaction.getParticipantId() == participant.getId()) {
					participantFound = true;
					break;
				}
			}
			if (!participantFound) {
				ExpenseTransaction expenseTransaction = new ExpenseTransaction();
				expenseTransaction.setParticipantId(participant.getId());
				expenseTransaction.setExpenseAmont(0);
				expenseTransactions.add(expenseTransaction);
			}
		}
		Collections.sort(expenseTransactions, expenseTransactionComparator);
	}

	private ExpenseGroup removeOutOfDateRangeExpenses(ExpenseGroupVO expenseGroupVO, String fromDateStr, String toDateStr) {
		Date fromDate;
		Date toDate;
		try {
			fromDate = CommonConstants.DATE_FORMAT.parse(fromDateStr);
			toDate = CommonConstants.DATE_FORMAT.parse(toDateStr);
		} catch (ParseException e) {
			// If dates are in wrong format. return all expenses
			return convertFromVOtoDto(expenseGroupVO);
		}
		List<ExpenseItemVO> unwantedExpenses = new ArrayList<ExpenseItemVO>();
		List<ExpenseItemVO> expenseItemVOs = expenseGroupVO.getExpenseItemVOs();
		for (ExpenseItemVO expenseItemVO : expenseItemVOs) {
			if (expenseItemVO.getExpenseDate().before(fromDate) || expenseItemVO.getExpenseDate().after(toDate)) {
				unwantedExpenses.add(expenseItemVO);
			}
		}
		expenseItemVOs.removeAll(unwantedExpenses);
		return convertFromVOtoDto(expenseGroupVO);
	}

	private static Comparator<Participant>			perticipantComparator			= new Comparator<Participant>() {

																						public int compare(Participant o1, Participant o2) {
																							return (int) (o1.id - o2.id);
																						}
																					};
	private static Comparator<ExpenseTransaction>	expenseTransactionComparator	= new Comparator<ExpenseTransaction>() {

																						public int compare(ExpenseTransaction o1,
																								ExpenseTransaction o2) {
																							return (int) (o1.getParticipantId() - o2
																									.getParticipantId());
																						}
																					};

}
