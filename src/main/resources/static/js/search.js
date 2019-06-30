$(document).ready(function(){
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