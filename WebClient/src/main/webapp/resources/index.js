function add_row(item) {
    var inputTag = "<input type='button' value='open' class='btn' onclick='show_file_viewer(\"" + item.id + "\")'>";
    
    var classType = "warning";
    if(item.status === 'Approved') {
        classType="success";
    }
    else if(item.status === 'Rejected') {
        classType="danger";
    }    
    var classTag = "class=\"" + classType + "\"";
    
    var markup = "<tr id=\"tr" + item.id + "\" " + classTag + " \">";
    markup += "<td>" + item.file + "</td>";
    markup += "<td>" + item.uri + "</td>";
    markup += "<td>" + item.size + "</td>";
    markup += "<td>" + item.type + "</td>";
    markup += "<td>" + item.status + "</td>";
    markup += "<td>" + inputTag + "</td>";
    markup += "</tr>";
    $("table tbody").append(markup);
}

function retrieve_files() {
    console.log("retrieve_files in");
    $('table tbody').empty();
    
    $.ajax({
        type : "get",
        url : "show",
        async : true,
        data : {
            "start" : 0,
            "end" : 10000
        },
        success: function(data){
            console.log('get response from /show');
            
            data.forEach(function(item) {
                add_row(item);
            });                        
        },
        error: function(req, status, err) {
            console.log(req + ' ' + status + ' ' + err);
        }
    });
}

function update_file_state(event) {
    
    $("#file_viewer").modal('hide');
    console.log('updating file state');
    $.ajax({
        type : "get",
        url : "update",
        async : true,
        data : {            
            "state" : event.data.state,
            "id": window.currentId
        },
        success: function(data){
            console.log('response from update request: ' + data);
            retrieve_files();
            
        },
        error: function(req, status, err) {
            console.log(req + ' ' + status + ' ' + err);
        }
    });        
}

function show_file_viewer(id) {
    var file_viewer = $('#file_viewer');

    $("#container_image").hide();
    $("#container_html").hide();
    $("#container_text").hide();
    
    window.currentId = id;

    $.ajax({
        type : "get",
        url : "get",
        async : true,
        data : {
            "id" : id
        },        
        success: function(data) {
            console.log("download file content");
            if(data.type.startsWith('image/')) {
                $("#container_image").attr('src', data.content);
                $("#container_image").show();
            }
            else if(data.type === 'text/html') {
                $("#container_html").contents().find('body').html(data.content);
                $("#container_html").show();
            }
            else if(data.type === 'text/plain') {
                $("#container_text").text(data.content);
                $("#container_text").show();
            }
            else 
            {
                console.log('unexpected data format: ' + data.type);
            }
        },
        error: function(req, status, err) {
            console.log(req + ' ' + status + ' ' + err);
        }
    });        

    file_viewer.modal('show');
}

function request_download() {
    $.ajax({
    type : "post",
        url : "download",
        async : true,
        data : {                
            "address" : $("#request_input").val(),
            "username" : $("#username").val(),
            "password" : $("#password").val()
        },
        success: function(data){
            console.log('response from download request: ' + data);
            retrieve_files();
        },
        error: function(req, status, err) {
            console.log(req + ' ' + status + ' ' + err);
        }
    });     
}

$(document).ready(function(){
    
    $("#download").click(function() {
        $("#request_box").modal('show');
    });
    
    $("#request_button").click(function() {
        
        $("#request_box").modal('hide');
        
        request_download();   
    });        

    $("#approve_button").click({state: true}, update_file_state);
    $("#reject_button").click({state: false}, update_file_state);
    
    retrieve_files();

    //setInterval(add_row, 10000);
});