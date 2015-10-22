require([ "mftController" ], function(MftController) {

	console.log("mftBootstrap is loaded");

	initializeWidgets();
	MftController.loadPage();

	function initializeWidgets() {

		window.MftController = MftController;
		$('#tabs').tabs({
			hide : {
				effect : "scale",
				duration : 500,
				collapsible : true
			}
		});

		$(".addExpenseLabel").click(function() {
			$("#saveExpenseButton").prop('disabled', true);

			$("#addExpenseDialog").dialog("open");
		});
		$(".addExpenseLabel").button();

		$(".addParticipantLabel").click(function() {
			$("#addParticipantDialog").dialog("open");
		});
		$(".addParticipantLabel").button();

		$(".finalTallyLabel").click(function() {
			MftController.setFinalTally();

			$("#finalTallyDialog").dialog("open");
		});

		$(".finalTallyLabel").button();

		$("#getExpenseButton").button();

		$('#email').on('blur', function() {

			$(this).mailcheck({
				suggested : function(element, suggestion) {

				},
				empty : function(element) {

				}
			});
		});
		$("#fromDate").datepicker({
			changeMonth : true,
			numberOfMonths : 1,
			maxDate : 0,
			dateFormat : 'yy-mm-dd',
			showOn : "both",
			buttonImage : "images/calendar.gif",
			buttonImageOnly : true,
			buttonText : "Select from date",

			onClose : function(selectedDate) {

				$("#toDate").datepicker("option", "minDate", selectedDate);
			}
		});
		$("#toDate").datepicker({
			changeMonth : true,
			numberOfMonths : 1,
			maxDate : 0,
			dateFormat : 'yy-mm-dd',
			showOn : "both",
			buttonImage : "images/calendar.gif",
			buttonImageOnly : true,
			buttonText : "Select to date",

			onClose : function(selectedDate) {

				$("#fromDate").datepicker("option", "maxDate", selectedDate);
			}
		});

		$("#expenseDate").datepicker({
			changeMonth : true,
			numberOfMonths : 1,
			maxDate : 0,
			dateFormat : 'yy-mm-dd',
			showOn : "both",
			buttonImage : "images/calendar.gif",
			buttonImageOnly : true,
			buttonText : "Select to date",
		});

		$("#ExpenseDate").datepicker({
			showOn : "button",
			buttonImage : "images/calendar.gif",
			buttonImageOnly : true,
			dateFormat : 'yy-mm-dd',
			buttonText : "Select date"
		});

		$('#expenseDetails').maxlength({
			events : [], // Array of events to be triggerd
			maxCharacters : 50, // Characters limit
			status : true, // True to show status indicator below the element
			statusClass : "status", // The class on the status div
			statusText : "character left", // The status text
			notificationClass : "notification", // Will be added when maxlength
			// is reached
			showAlert : false, // True to show a regular alert message
			alertText : "You have typed too many characters.", // Text in alert
			// message
			slider : false
		// True Use counter slider
		});

		$("#addExpenseDialog").dialog({
			autoOpen : false,
			height : 350,
			width : 500,
			modal : true,
			show : {
				effect : "blind",
				duration : 700
			},
			hide : {
				effect : "blind",
				duration : 500
			}
		});

		$("#finalTallyDialog").dialog({
			autoOpen : false,

			modal : true,
			show : {
				effect : "blind",
				duration : 900

			}

		});
		$("#addParticipantDialog").dialog({
			autoOpen : false,
			height : 200,
			width : 300,
			modal : true,
			show : {
				effect : "blind",
				duration : 700
			},
			buttons : {
				OK : function() {

					dialog.dialog("close");
				}
			},
		});
		$('#expenseForm').find('table')._built = false;

	}
});