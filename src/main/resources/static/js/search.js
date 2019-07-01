$('#btnSearch').click(function(){
    var keyword = $('#inputSearch').val();
    var status = $('#selectStatus').val();
    var searchKeyword = "?keyword=" + keyword;
    var searchStatus = "?status=" + status;
    var searchKeywordAndStatus = "?keyword=" + keyword + "&status=" + status;
    if(keyword != "" && status != ""){
        window.location.href = searchKeywordAndStatus;
    }
    if(keyword != "" && status == ""){
        window.location.href = searchKeyword;
    }
    if(keyword == "" && status != ""){
        window.location.href = searchStatus;
    }
    if(keyword == "" && status ==""){
        window.location.href = "/";
    }
})