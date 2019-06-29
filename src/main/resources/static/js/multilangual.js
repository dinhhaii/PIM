$(document).ready(function(){
    $('#en').hide();
    $('#fr').hide();

    var lang = new URLSearchParams(window.location.search).get('lang');
    if(lang=='en'){
        $('#en-label').addClass('active');
        $('#fr-label').removeClass('active');
    }

    if(lang=='fr'){
        $('#fr-label').addClass('active');
        $('#en-label').removeClass('active');
    }
});

$('#en').click(function () {
    if ($(this).is(':checked')) {
        window.location.href = '?lang=en';
    }
});
$('#fr').click(function () {
    if ($(this).is(':checked')) {
        window.location.href = '?lang=fr';
    }
});