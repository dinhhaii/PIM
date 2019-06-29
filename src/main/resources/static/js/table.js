//Count selected items
$('#tblfoot').hide();
var numberselecteditem = 0;

$("#tblProject input[type='checkbox']").change(function() {
    if(this.checked){
        numberselecteditem += 1;
        $('#tblfoot').show();
        $('#numberselecteditem').text(numberselecteditem + ' items selected');
    }
    else{
        numberselecteditem -= 1;
        if(numberselecteditem == 0){
            $('#tblfoot').hide();
        }
        $('#numberselecteditem').text(numberselecteditem + ' items selected');
    }
});

//Confirm when click Delete button
$('.delete').click(function(event) {
    if(!confirm("Are you sure you want to delete?")){
        event.preventDefault();
    }
});

//Delete selected items
$('#deleteselected').click(function() {
    $('.checkitem:checked').map(function(){
        $.post( "deleteproject",
            { id: $(this).val() },
            function(data){});
    });
    $('.checkitem:checked').prop('checked',false);
    numberselecteditem = 0;
    window.location.reload();
});

//Click Row to go to Edit Page
$('.clickable-row').children('td:not(.unclickable)').click(function(){
    window.location = $('.clickable-row').data("href");
});
