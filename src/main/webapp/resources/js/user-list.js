
function showAddresses(userid) {

	if ($('#addr_'+userid).length) {
		$('#addr_'+userid).parents('tr').toggle(100);
		console.log($('#addr_'+userid));
		
	} else {
		$.get("User?action=getAddresses&userid="+userid, function(jsonResp) {
			
			if (jsonResp.length!=0) {
				
				// create inside table for addresses
				tableHTML = '<tr style="display: none;"><td colspan="6" style="padding-left: 0px;padding-bottom: 0px;padding-top: 0px;padding-right: 0px;"><div class="d-flex flex-row-reverse"><div class="p-2" style="padding-left: 0px !important;padding-bottom: 0px !important;padding-top: 0px !important;padding-right: 0px !important;">\
					<table id="addr_'+userid+'" class="table-sm pull-right table-borderless">';
				for (var a in jsonResp) {
					tableHTML += '<tr>';
					if (jsonResp[a].type == 'HOME') {
						tableHTML += '<td class="text-right" nowrap>Home address: </td>';
					} else {
						tableHTML += '<td class="text-right" nowrap>Work address: </td>';
					}
					tableHTML +='<td nowrap>'+jsonResp[a].address+'</td>';
					tableHTML += '</tr>';
				}
				tableHTML += '</table></div></div></td></tr>';
				
				//insert table to user table
				$('#userid_' + userid).closest('tr').after(tableHTML);
				$('#addr_'+userid).parents('tr').show('slow');
				
			}
		});
	}
}