package com.manvendra.ft.app.persistance;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.manvendra.ft.app.persistance.model.ExpenseGroupVO;
import com.manvendra.ft.app.persistance.model.ExpenseItemVO;

@Repository("financeDao")
public class FinanceDaoJPAImpl implements FinanceDao {

	@PersistenceContext
	private EntityManager	entityManager;

	@Override
	public ExpenseGroupVO getGroupWithExpenseForGivenPeriod(long groupId, String fromDate, String toDate) {
		// For now load all expense and filter in APIImpl.
		// But later I'll change the code to fetch only required expenses.
		//testRDSConnection();
		ExpenseGroupVO expenseGroup = getGroupWithAllExpenses(groupId); // Need to change this in future
		return expenseGroup;

	}
	public ExpenseGroupVO getGroupWithAllExpenses(long groupId) {
		ExpenseGroupVO expenseGroup = (ExpenseGroupVO) entityManager.find(ExpenseGroupVO.class, groupId);
		return expenseGroup;

	}

	@Override
	@Transactional
	public long saveExpenseItem(ExpenseItemVO expenseItem) {
		entityManager.persist(expenseItem);
		entityManager.flush();
		return expenseItem.getId();
	}

//	private void testRDSConnection() {
//
//		Properties prop = new Properties();
//		try {
//			prop.load(this.getClass().getClassLoader().getResourceAsStream("db.properties"));
//		} catch (Exception e) {
//
//		}
//
//		String url = prop.getProperty("db.url");
//		String userName = prop.getProperty("db.username");
//		String password = prop.getProperty("db.password");
//
//		String jdbcUrl = url + "?user=" + userName + "&password=" + password;
//
//		// Load the JDBC driver
//		try {
//			System.out.println("Loading driver...");
//			Class.forName("com.mysql.jdbc.Driver");
//			System.out.println("Driver loaded!");
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException("Cannot find the driver in the classpath!", e);
//		}
//
//		Connection conn = null;
//
//		Statement readStatement = null;
//		ResultSet resultSet = null;
//		String results = "";
//
//		try {
//			conn = DriverManager.getConnection(jdbcUrl);
//
//			readStatement = conn.createStatement();
//			resultSet = readStatement.executeQuery("SELECT * FROM EXPENSE_GROUP;");
//
//			resultSet.first();
//			results = resultSet.getString("DESCRIPTION");
//			resultSet.next();
//			results += ", " + resultSet.getString("DESCRIPTION");
//
//			resultSet.close();
//			readStatement.close();
//			conn.close();
//			System.out.println(results);
//		} catch (SQLException ex) {
//			// Handle any errors
//			System.out.println("SQLException: " + ex.getMessage());
//			System.out.println("SQLState: " + ex.getSQLState());
//			System.out.println("VendorError: " + ex.getErrorCode());
//		} finally {
//			System.out.println("Closing the connection.");
//			if (conn != null)
//				try {
//					conn.close();
//				} catch (SQLException ignore) {
//				}
//		}
//	}
}
