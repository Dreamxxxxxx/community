function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();

    if(!content){
        alert("不能回复空内容~~~~");
        return;
    }

    $.ajax({
        type:"POST",
        url:"/comment",
        contentType:'application/json',
        data:JSON.stringify({
            "parentId":questionId,
            "content":content,
            "type":1
        }),
        success: function (response) {
            if(response.code == 200){
                window.location.reload();
            }else {
                if(response.code == 2003){
                    var isAccept = confirm(response.message);
                    if(isAccept){
                        window.open("https://github.com/login/oauth/authorize?client_id=3c5ba20e41268201ec04&redirect_uri=http://localhost:8080/callback&scope=user&state=1")
                        window.localStorage.setItem("closable",true);
                    }
                }else{
                    alert(response.message)
                }
            }
            console.log(response)
        },
        fail: function(response){
            console.log("失败了？？？"+response.code)
        },
        dataType:"json"
    });
    console.log(questionId);
    console.log(content);
}