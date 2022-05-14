$(document).ready(function() 
{  
		$("#alertSuccess").hide();  
	    $("#alertError").hide(); 
}); 
 
 
// SAVE ============================================ 
$(document).on("click", "#btnSave", function(event) 
{  
	// Clear alerts---------------------  
	$("#alertSuccess").text("");  
	$("#alertSuccess").hide();  
	$("#alertError").text("");  
	$("#alertError").hide(); 
 
	// Form validation-------------------  
	var status = validatePaymentForm();  
	if (status != true)  
	{   
		$("#alertError").text(status);   
		$("#alertError").show();   
		return;  
	} 
 
	// If valid------------------------  
	var type = ($("#hidpay_idSave").val() == "") ? "POST" : "PUT"; 

	$.ajax( 
	{  
			url : "PaymentService",  
			type : type,  
			data : $("#formPayment").serialize(),  
			dataType : "text",  
			complete : function(response, status)  
			{   
				onPaymentSaveComplete(response.responseText, status);  
			} 
	}); 
}); 


function onPaymentSaveComplete(response, status) 
{  
	if (status == "success")  
	{   
		var resultSet = JSON.parse(response); 

		if (resultSet.status.trim() == "success")   
		{    
			$("#alertSuccess").text("Successfully saved.");    
			$("#alertSuccess").show(); 

			$("#divPaymentGrid").html(resultSet.data);   
		} else if (resultSet.status.trim() == "error")   
		{    
			$("#alertError").text(resultSet.data);    
			$("#alertError").show();   
		} 

	} else if (status == "error")  
	{   
		$("#alertError").text("Error while saving.");   
		$("#alertError").show();  
	} else  
	{   
		$("#alertError").text("Unknown error while saving..");   
		$("#alertError").show();  
	} 

	$("#hidInnovatorIDSave").val("");  
	$("#formInnovator")[0].reset(); 
} 

 
// UPDATE========================================== 
$(document).on("click", ".btnUpdate", function(event) 
{     
	$("#hidpay_idSave").val($(this).closest("tr").find('#hidpay_idUpdate').val());     
	$("#holder_name").val($(this).closest("tr").find('td:eq(0)').text());     
	$("#ctype").val($(this).closest("tr").find('td:eq(1)').text());     
	$("#card_no").val($(this).closest("tr").find('td:eq(2)').text());  
	$("#cvv").val($(this).closest("tr").find('td:eq(3)').text());  
	$("#exp_month").val($(this).closest("tr").find('td:eq(4)').text());  
	$("#exp_year").val($(this).closest("tr").find('td:eq(5)').text());  
	$("#total").val($(this).closest("tr").find('td:eq(6)').text()); 
	$("#pay_date").val($(this).closest("tr").find('td:eq(7)').text());      
}); 


//REMOVE===========================================
$(document).on("click", ".btnRemove", function(event) 
{  
	$.ajax(  
	{   
		url : "PaymentService",   
		type : "DELETE",   
		data : "pay_id=" + $(this).data("iid"),   
		dataType : "text",   
		complete : function(response, status)   
		{    
			onPaymentDeleteComplete(response.responseText, status);   
		}  
	}); 
}); 

function onPaymentDeleteComplete(response, status) 
{  
	if (status == "success")  
	{   
		var resultSet = JSON.parse(response); 

		if (resultSet.status.trim() == "success")   
		{    
			
			$("#alertSuccess").text("Successfully deleted.");    
			$("#alertSuccess").show(); 
		
			$("#divPaymentGrid").html(resultSet.data); 
			
		} else if (resultSet.status.trim() == "error")   
		{    
			$("#alertError").text(resultSet.data);    
			$("#alertError").show();   
		}
		

	} else if (status == "error")  
	{   
		$("#alertError").text("Error while deleting.");   
		$("#alertError").show();  
	} else  
	{   
		$("#alertError").text("Unknown error while deleting..");   
		$("#alertError").show();  
	}
}
 
// CLIENT-MODEL========================================================================= 
function validatePaymentForm() 
{  
	// NAME  
	if ($("#holder_name").val().trim() == "")  
	{   
		return "Insert holder_name.";  
	} 

	// Card Type------------------------  
	if ($("#ctype").val().trim() == "")  
	{   
		return "Insert ctype.";  
	} 
	
	

	//card_no-------------------------------
	if ($("#card_no").val().trim() == "")  
	{   
		return "Insert card_no.";  
	} 
	
	
	//cvv-------------------------------
	if ($("#cvv").val().trim() == "")  
	{   
		return "Insert cvv.";  
	} 
	
	//exp_month-------------------------------
	if ($("#exp_month").val().trim() == "")  
	{   
		return "Insert exp_month.";  
	} 
	
	//exp_year-------------------------------
	if ($("#exp_year").val().trim() == "")  
	{   
		return "Insert exp_year.";  
	} 
	

		//total-------------------------------
	 var tmpAmount = $("#total").val().trim();
	if (!$.isNumeric(tmpAmount)) 
	 {
	 return "Insert total.";
	 }
	
	//pay_date-------------------------------
	if ($("#pay_date").val().trim() == "")  
	{   
		return "Insert pay_date.";  
	} 
	
	return true; 
}