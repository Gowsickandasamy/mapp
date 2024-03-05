<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Email Content</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      color: #333;
      background-color: #f4f4f4;
      margin: 0;
      padding: 20px;
    }

    div {
      margin-bottom: 15px;
    }

    h4 {
      color: #007BFF;
      margin-top: 0;
    }

    table {
      border-collapse: collapse;
      width: 100%;
      margin-top: 20px;
    }

    th, td {
      border: 1px solid #ddd;
      padding: 8px;
      text-align: left;
    }

    b {
      color: #555;
    }
    
    .remainder {
      color: red;
    }

    /* Fancy Styles */
    .container {
      background-color: #fff;
      border-radius: 10px;
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
      padding: 20px;
    }

    .header {
      background-color: #007BFF;
      color: #fff;
      padding: 10px;
      border-radius: 10px 10px 0 0;
    }

    .content {
      padding: 20px;
    }

    .example {
      background-color: #f9f9f9;
      padding: 10px;
      border-radius: 5px;
      margin-top: 10px;
    }

    .footer {
      text-align: center;
      margin-top: 20px;
    }
  </style>
</head>
<body>
  <div class="container">
    <div class="header">
      <h4>Hello, <span style="text-transform: capitalize;">${officer.officerName}</span></h4>
    </div>

    <div class="content">
      <p>You have been successfully <b>created</b> and <b>assigned</b> to the below jurisdiction!</p>

      <p>Your temporary password for login is <b>first 4 digits of your first name</b> (first letter should be capital) and <b>last 4 digits of your phone number</b> along with the special character (@) in between them.</p>

      <div class="example">
        <b>Example:</b><br>
        First Name: username<br>
        Phone No.: 1234567890<br>
        Password: User@7890
      </div>

      <table>
        <tr>
          <th>Area</th>
          <th>Ward</th>
          <th>Layout</th>
        </tr>
        <tr>
          <td>${jury.area}</td>
          <td>${jury.ward}</td>
          <td>${jury.layout}</td>
        </tr>
      </table>
    </div>

    <div class="content remainder">
      <p>Please reset your password as soon as you login...</p>
    </div>

    <div class="footer">
      <h4>Best regards,</h4>
      <h4>UrbanVoice</h4>
    </div>
  </div>
</body>
</html>
