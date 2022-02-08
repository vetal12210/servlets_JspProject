<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Login</title>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <style>
        body {
            background-color: cyan;
        }

        h1 {
            text-align: center;
        }

        .form-group {
            padding: 5px;
        }

        .form-group label {
            width: 105px;
            display: inline-block;
        }

        .form-group input {
            width: 165px;
            padding: 5px;
        }
    </style>

</head>
<body>
<div class="row">
    <div class="mx-auto">
        <div class="input-group mb-3">
            <div class="card">
                <article class="card-body">
                    <h4 class="card-title text-center mb-4 mt-1">Authorization</h4>
                    <form method="post" align="center" action="/login">
                        <div class="form-group">
                            <label><b>Login:</b></label>
                            <input name="login" pattern="[a-zA-Z0-9]{3,10}" id="login" type="login"
                                   placeholder="Enter your login"
                                   required/>
                        </div>
                        <div class="form-group">
                            <label><b>Password:</b></label>
                            <input name="password" pattern="[a-zA-Z0-9]{3,7}" id="password" type="password"
                                   placeholder="Enter your password"
                                   required/>
                        </div>
                        <div class="form-group">
                            <a href="#" id="s-h-pass">Show pass</a>
                        </div>
                        <br/>
                        <br/>
                        <button type="submit" align="center" class="btn btn-primary">Log in</button>
                    </form>
                    <br/>
                    <br/>
                    <script type="text/javascript"
                            src="https://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
                    <script>
                        $(document).ready(function () {
                            $('#s-h-pass').click(function () {
                                var type = $('#password').attr('type') == "text" ? "password" : 'text',
                                    c = $(this).text() == "Hide pass" ? "Show pass" : "Hide pass";
                                $(this).text(c);
                                $('#password').prop('type', type);
                            });
                        });
                    </script>
                </article>
            </div>
        </div>
    </div>
</div>
</body>
</html>
