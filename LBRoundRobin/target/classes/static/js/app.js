var app = (function () {
var entrada =0;
    function addMessage(){
        var url=window.location.href+'insert';
        var m =document.getElementById("message").value;
        axios.post(url,m)
            .then(res => {
                $("#Table > tbody").empty();
                getMessages();
            })
    }
    function getMessages(){
        $("#Table > tbody").empty();
        var url=window.location.href+'results';
        axios.get(url).then(res=>{
            res.data.map(message=>{
                console.log(message)
                $("#Table > tbody").append(
                    "<tr>" +
                    "<td>" + message.content + "</td>" +
                    "<td>" + message.date + "</td> " +
                    "</tr>"
                );
            })
        })
    }
    return {
        addMessage:addMessage,
        getMessages:getMessages
    };
})();