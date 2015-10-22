define(function() {

	return {
		getISOFormatDate : function(date) {

			alert(date);
			try {
				return (date.getFullYear() + "-" + (date.getMonth() +1)+ "-" + date.getDate());
			} catch (error) {
				alert("problem in formatting date " + date);
			}
		},

		getFirstDateForGivenMonth : function(currentDate) {
			return new Date(currentDate.getFullYear(), +currentDate.getMonth(), '1');
		},
		stopPropagation : function(evt) {

			evt = evt || window.event; // For IE
			if (typeof evt.stopPropagation != "undefined") {
				evt.stopPropagation();
			} else {
				evt.cancelBubble = true;
			}
		},

		formToJSON : function(formName) {

			var form = {};
			$("#" + formName + " :input").each(function() {

				var self = $(this);
				var name = self.attr('name');
				if (form[name]) {
					form[name] = form[name] + ',' + self.val();
				} else {
					form[name] = self.val();
				}
			});

			return form;
		},

		getExpenseItemJsonFromForm : function(formName) {

			var expenseItemObject = JSON.parse(JSON.stringify(ExpenseData.expenseItem[0]));
			expenseItemObject.expenseDate = $('#expenseForm #expenseDate').val();
			expenseItemObject.description = $('#expenseForm #expenseDetails').val();
			expenseItemObject.id = 0;
			expenseItemObject.groupId = ExpenseData.id;
			for (var i = 0; i < expenseItemObject.paidBy.length; i++) {
				var paidBy = expenseItemObject.paidBy[i];
				paidBy.expenseAmont = $('#expenseForm input[_pByid^=' + paidBy.participantId + ']').val();

			}
			for (var i = 0; i < expenseItemObject.paidFor.length; i++) {
				var paidFor = expenseItemObject.paidFor[i];
				paidFor.expenseAmont = $('#expenseForm input[_pForid^=' + paidFor.participantId + ']').val();

			}
			return JSON.stringify(expenseItemObject);
		},

		
		intVal : function(i) {
			return typeof i === 'string' ? i.replace(/[\$,]/g, '') * 1 : typeof i === 'number' ? i : 0;
		}

	};
});