<!DOCTYPE html>
<html>
<head>
    <title>Spring JSP Example</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            text-align: center;
            margin-top: 50px;
            background-color: #f4f4f4;
            color: #333;
        }
        .container {
            background-color: #fff;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            display: inline-block;
        }
        h1 {
            color: #0056b3;
        }
        p {
            font-size: 1.1em;
            color: #555;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Hello from a JSP Page!</h1>
        <p>This page was rendered by a Spring MVC Controller.</p>
        <p>Current Time in India (IST): <strong><%= new java.util.Date() %></strong></p>
        <p>Message from Controller: <strong>${messageFromController}</strong></p>
        <p>Your Name (from URL): <strong>${userName}</strong></p>
        <p>Save changes</p>
    </div>
</body>
</html>