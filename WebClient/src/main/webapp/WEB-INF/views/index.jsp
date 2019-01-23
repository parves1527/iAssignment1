<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <title>File List</title>
        <meta charset="utf-8">				
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="resources/index.css">

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
        <script src='resources/index.js' type="text/javascript"></script>
    </head>

    <body>

        <div class="container">
            <h1 style="text-align: center;">Downloaded Files</h1>
            <div class="table-responsive">
                <table class="table table-hover table-striped table-condensed">
                    <thead>
                        <tr>
                            <th>File</th>
                            <th>URI</th>
                            <th>Size</th>
                            <th>Type</th>
                            <th>Status</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>

            <button name="download" class="btn btn-primary pull-right" id="download">Download</button>

            <div>
                <div id="file_viewer" class="modal fade">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                <h4 class="modal-title">Confirmation</h4>
                            </div>
                            <div class="modal-body">
                                <img id="container_image" class="img-responsive" alt="loading ..."  >
                                <iframe id="container_html" class="embed-responsive-item" style="width:100%;" ></iframe> 
                                <pre id="container_text" style="white-space: pre-wrap;"> </pre>
                            </div>
                            <div class="modal-footer">							
                                <button name="approve" class="btn btn-success" id="approve_button" >Approve</button>
                                <button name="reject" class="btn btn-danger" id="reject_button" >Reject</button>
                                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                            </div>
                        </div>
                    </div>
                </div>		

                <div id="request_box" class="modal fade">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                <h4 class="modal-title">Download Request</h4>
                            </div>
                            <div class="modal-body">
                                <div class="form-group">
                                    <label for="example-url-input">URL</label>
                                    <input type="url" placeholder="<protocol://><host-name>/<file-name>" id="request_input"  class="form-control" />
                                    <label for="usr">User Name (Optional)</label>
                                    <input type="text" placeholder="Username" id="username" class="form-control" />
                                    <label for="pwd">Password (Optional)</label>
                                    <input type="password" placeholder="Password" id="password" class="form-control" />
                                </div>
                            </div>
                            <div class="modal-footer">							
                                <button name="approve" class="btn btn-success" id="request_button">Download</button>
                                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>        
    </body>
</html>