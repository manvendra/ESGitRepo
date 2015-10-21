define(
		[ "mftUtil" ],
		function(MftUtil) {

			console.log("mtfUPainter is loaded");

			return {

				paintScreen : function(ExpenseDetails) {
					debugger;
					this.fillParticipantDetails(ExpenseDetails);
					this.fillExpenses(ExpenseDetails);
					this.createAddExpenseModule(ExpenseDetails);
					this.createExpenseCharts(ExpenseDetails);
					this.createFinalTallyDiv(ExpenseDetails.participants);

					expenseTableObject = $("#expenseTable").dataTable({
						"paging" : false,
						"jQueryUI" : true,
						"footerCallback" : function(row, data, start, end, display) {
							var api = this.api(), data;

							// bad code correct it by one call neither redundant
							// line nor any loop
							for (var columnNumber = 3; columnNumber < (2 * (ExpenseDetails.participants.length) + 3); columnNumber++) {
								var pageTotal = api.column(columnNumber, {
									page : 'current'
								}).data().reduce(function(a, b) {
									return MftUtil.intVal(a) + MftUtil.intVal(b);
								}, 0);
								$(api.column(columnNumber).footer()).html(pageTotal);
							}

						}

					});
					var table = $('#example').DataTable();

				},

				fillParticipantDetails : function(expensee) {

				},

				fillExpenses : function(expenses) {

					this.createExpenseTableHeader(expenses);
					this.createTableFooter(expenses);

					var expenseRows = expenses.expenseItem.length;
					for (var i = 0; i < expenseRows; i++) {
						var expense = expenses.expenseItem[i];
						this.addExpenseTableRow(expense);
					}

				},
				createExpenseCharts : function(expenses) {

				},

				addExpenseTableRow : function(expense) {

					var rowContent = '<tr><td>' + expense.id + '</td><td style="text-align: left;">'
							+ MftUtil.getISOFormatDate(new Date(expense.expenseDate)) + '</td><td>' + expense.description + '</td>';

					for (var i = 0; i < expense.paidBy.length; i++) {
						rowContent = rowContent + '<td>' + expense.paidBy[i].expenseAmont + '</td>';
					}
					for (var i = 0; i < expense.paidFor.length; i++) {
						rowContent = rowContent + '<td>' + expense.paidFor[i].expenseAmont + '</td>';
					}
					rowContent = rowContent + '</tr>';

					$("#expenseTable").find('tbody').append(rowContent);

				},

				createExpenseTableHeader : function(expenses) {

					// /debugger;
					var headerColSpan = expenses.participants.length;

					var tableHeader = '<br><table style="margin-bottom: 10px;" class="display compact cell-border" id="expenseTable"><thead>';
					var headerRow1 = '<tr>' + '<th rowspan="2">Expense id</th>' + '<th rowspan="2">Date</th>' + '<th rowspan="2">Description</th>'
							+ '<th colspan="' + headerColSpan + '">Paid By</th>' + '<th colspan="' + headerColSpan + '">PaidFor</th>' + '</tr>';

					var headerRow2 = '<tr>';

					for (var i = 0; i < headerColSpan; i++) {
						var participant = expenses.participants[i];
						headerRow2 = headerRow2 + '<th id="paidBy_' + participant.id + '">' + participant.name + '</th>';
					}
					for (var i = 0; i < headerColSpan; i++) {
						var participant = expenses.participants[i];
						headerRow2 = headerRow2 + '<th id="paidfor_' + participant.id + '">' + participant.name + '</th>';
					}
					headerRow2 = headerRow2 + '</tr>'

					var tableHTML = tableHeader + headerRow1 + headerRow2 + '</thead><tbody></tbody>';

					$("#expenseDetailsDiv").html(tableHTML);

				},

				createTableFooter : function(expenses) {

					var headerColSpan = expenses.participants.length;

					var footerRow = '<tfoot><tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>';

					for (var i = 0; i < headerColSpan; i++) {
						var participant = expenses.participants[i];
						footerRow = footerRow + '<td id="paidBytotal_' + participant.id + '"></td>';
					}
					for (var i = 0; i < headerColSpan; i++) {
						var participant = expenses.participants[i];
						footerRow = footerRow + '<td id="paidfortotal_' + participant.id + '"></td>';
					}
					footerRow = footerRow + '</tr></tfoot></table>';
					$("#expenseDetailsDiv").find('#expenseTable').append(footerRow);
				},

				createAddExpenseModule : function(expenses) {
					var expenseForm = $('#expenseForm');

					if (expenseForm.find('table').attr("_isCreated") != "true") {

						var space = '<tr><td>&nbsp;</td><td><span class="errMsgText" id="digitOnlyErrMsg"></span></td><td>&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>';
						var htmlText = space
								+ "<tr><td><b>Paid By<b></td><td>&nbsp;</td><td>&nbsp;</td><td><b>Paid For<b></td><td>&nbsp;&nbsp;&nbsp;</td></tr>";

						for (var i = 0; i < ExpenseData.participants.length; i++) {
							var participant = expenses.participants[i];
							htmlText = htmlText + '<tr><td><label width="30px">' + participant.name
									+ '</label></td><td> <input class="numeric" type="text" _pByid="' + participant.id + '" name="expensePaidBy_'
									+ participant.id + '"><td>&nbsp;&nbsp;&nbsp;</td><td><label>' + participant.name
									+ '</label> </td><td><input class="numeric" type="text" _pForid="' + participant.id + '" name="expensePaidfor_'
									+ participant.id + '"></td> </tr>';
						}
						htmlText = htmlText + space;
						expenseForm.find('table').attr("_isCreated", "true");

						expenseForm.find('tbody').append(htmlText);
						$('#validateExpenseButton').click(function(e) {
							var expensePaidBySum = 0;
							$('#expenseForm input[name^=expensePaidBy]').each(function() {
								expensePaidBySum += Number($(this).val());
							});
							var expensePaidforSum = 0;
							$('#expenseForm input[name^=expensePaidfor]').each(function() {
								expensePaidforSum += Number($(this).val());
							});
							if (expensePaidBySum == expensePaidforSum) {
								$("#saveExpenseButton").prop('disabled', false);
							}else{
								
								$("#sumWrongErrMsg").html("Sum of paid-By values should be equal to sum of paid-for").show().fadeOut(6000);
							}

						});

						$(".numeric").keypress(function(e) {
							$("#saveExpenseButton").prop('disabled', true);

							// if the letter is not digit then display error and
							// don't type anything
							if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
								// display error message
								$("#digitOnlyErrMsg").html("Digits Only").show().fadeOut(2500);
								return false;
							}
						});
					}
				},

				addRowToDataTable : function(data) {

					var t = $("#expenseTable").DataTable();
					var item = JSON.parse(data);

					var rowParams = data.id + "," + item.expenseDate + "," + item.description;
					for (var i = 0; i < item.paidBy.length; i++) {
						rowParams = rowParams + "," + item.paidBy[i].expenseAmont;
					}
					for (var i = 0; i < item.paidFor.length; i++) {
						rowParams = rowParams + "," + item.paidFor[i].expenseAmont;
					}

					t.row.add(rowParams.split(',')).draw(false);

					ExpenseData.expenseItem.push(data);
				},

				createFinalTallyDiv : function(participants) {
					var finalTallyDiv = $('#finalTallyDiv');
					if (finalTallyDiv.attr("_isCreated") != "true") {
						tableHtml = '<br><table style="margin-bottom: 10px;" class="display compact cell-border" id="FinalTallyTable"><tr>';

						for (var i = 0; i < participants.length; i++) {
							var participant = participants[i];
							tableHtml = tableHtml + '<tr><td><label width="30px">' + participant.name
									+ '</label></td><td> <label class="numeric" type="text" id="_finalPaidBy' + participant.id
									+ '"></label></td> </tr>';
						}

						tableHTML = tableHtml + '</tbody></table>';
						finalTallyDiv.html(tableHTML);
						finalTallyDiv.attr("_isCreated", "true");
					}
				}

			};
		});
