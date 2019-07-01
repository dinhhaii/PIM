$('#btnSearch').click(function(){
    var keyword = $('#inputSearch').val();
    var status = $('#selectStatus').val();
    var searchKeyword = "?keyword=" + keyword;
    var searchStatus = "?status=" + status;
    var searchKeywordAndStatus = "?keyword=" + keyword + "&status=" + status;
    if(keyword != "" && status != "" && keyword!= null && status != null){
        window.location.href = searchKeywordAndStatus;
    }
    if(keyword != "" && status == "" && keyword!= null){
        window.location.href = searchKeyword;
    }
    if(keyword == "" && status != "" && status!= null){
        window.location.href = searchStatus;
    }
    if(keyword == "" && status ==""){
        window.location.href = "/";
    }
})