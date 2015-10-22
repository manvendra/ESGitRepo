define([ "mftUiPainter", "mftController" ], function(MftUiPainter, MftController) {

	console.log("mtfServerApi is loaded");
	return {

		callAjax : function(url, successCallback, errorCallback, method, datatype, headers, data) {
			$.ajax({
				url : url,
				type : method,
				data : data,
				dataType : datatype,
				success : successCallback,
				error : errorCallback,
				headers : headers
			});
		},
		
		getExpenses : function(groupid, fromDate, toDate) {

			var url = "api/groupfinance/expense?groupId=" + groupid + "&fromDate=" + fromDate + "&todate=" + toDate;
			$("body").addClass("loading");
			
			this.callAjax(url, function(expenseData) {
				ExpenseData = expenseData;
				MftUiPainter.paintScreen(expenseData);
				$("body").removeClass("loading");
			}, function() {
				alert("error conneting to server!! Please contact Manvendra.singh@gmail.com");
				$("body").removeClass("loading");
			}, "GET", "json");
		},
		saveExpenseItem : function(requestData) {
			
			this.callAjax("api/groupfinance/expenseItem", function(response, textStatus, xhr) {
				if (xhr.status == 200) {
					requestData.id = response;
					MftUiPainter.addRowToDataTable(requestData);
				}

				$("#expenseForm :input").prop('readonly', false);
				$("#expenseForm :input").css('background-color', 'white');
				$("#addExpenseDialog").dialog('close');

			}, function() {
				$("#expenseForm :input").prop('readonly', false);
				$("#expenseForm :input").css('background-color', 'white');
				$("#addExpenseDialog").dialog('close');
				
				alert("error conneting to server!! Please contact Manvendra.singh@gmail.com with exact time of error!");

			}, "POST", "json", {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			}, requestData);
		}
	};
});
