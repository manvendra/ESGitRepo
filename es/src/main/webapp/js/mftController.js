define([ "mftServerApi", "mftUtil" ], function(MftServerAPI, MftUtil) {

	console.log("MftController is loaded");
	this.expenseData = {};
	return {
		loadPage : function() {
			window.MftController = this;
			console.log("mft controller loadPage called");
			var groupId = location.search.split('groupId=')[1];
			var currentDate = new Date();
			var fromDate = MftUtil.getFirstDateForGivenMonth(currentDate);
			MftServerAPI.getExpenses(groupId, MftUtil.getISOFormatDate(fromDate), MftUtil.getISOFormatDate(currentDate));
			// UIPainter.createTabs(this.issues);
		},

		saveExpenseItem : function() {
			MftUtil.stopPropagation();

			$("#expenseForm :input").prop('readonly', true);
			$("#expenseForm :input").css('background-color', 'lightgray');
			$("#expenseForm :input").css('border', '1px solid gray');

			MftServerAPI.saveExpenseItem(MftUtil.getExpenseItemJsonFromForm("expenseForm"));
		},

		getExpenseForPeriod : function() {
			MftUtil.stopPropagation();

			var fromDate = $("#fromDate").val();
			var toDate = $("#toDate").val();

			if(fromDate == '' || toDate=='' || fromDate ==undefined || toDate == undefined){
				MftUtil.showDialogBox('What did you do !!','Dont be in such a hurry, Select the date range first !! ','Ok','');
			}else{
				//fromDate = MftUtil.getISOFormatDate();
				//toDate = MftUtil.getISOFormatDate();				
				MftServerAPI.getExpenses(ExpenseData.id, fromDate, toDate);
			}
			
		},
		setFinalTally : function() {
			MftUtil.stopPropagation();

			var api = $("#expenseTable").dataTable().api();
			

			// bad code correct it by one call neither redundant
			// line nor any loop
			for (var columnNumber = 3; columnNumber < ((ExpenseData.participants.length) + 3); columnNumber++) {
				var participant = ExpenseData.participants[columnNumber-3];
				
				var paidByTotal = api.column(columnNumber, {
					page : 'current'
				}).data().reduce(function(a, b) {
					return MftUtil.intVal(a) + MftUtil.intVal(b);
				}, 0);
				
				var paidForTotal = api.column(columnNumber + ExpenseData.participants.length, {
					page : 'current'
				}).data().reduce(function(a, b) {
					return MftUtil.intVal(a) + MftUtil.intVal(b);
				}, 0);
				var finalAmount=paidByTotal-paidForTotal;
				var text = (finalAmount <0)?" - need to pay ": " - will receive ";
				
				$('#finalTallyDiv').find("#_finalPaidBy"+participant.id).html('<span> ' +text + Math.abs(finalAmount) +'</span>');
			}
			
		}

	};
});