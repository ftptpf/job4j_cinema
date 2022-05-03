<%@ page contentType="text/html; charset=UTF-8" %>

<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>

    <title>Кинотеатр</title>
</head>
<body>
<!-- JavaScript -->
<script>
    function validate() {
        if ($(`#username`).val() === "") {
            alert("Необходимо заполнить поле ФИО");
            return false;
        } else if ($(`#phone`).val() === "") {
            alert("Необходимо заполнить поле Номер телефона");
            return false;
        } else if ($(`#email`).val() === "") {
            alert("Необходимо заполнить поле Email");
            return false;
        }
        return true;
    }
</script>
<!-- Java -->
<%
    String placeValue = request.getParameter("placeValue");
    char row = placeValue.charAt(0);
    char cell = placeValue.charAt(1);
%>

<div class="container">
    <div class="row pt-3">
        <h3>
            Вы выбрали ряд <%=row%> место <%=cell%>, Сумма: 500 рублей.
        </h3>
    </div>
    <div class="row">
        <form action="<%=request.getContextPath()%>/payment.do?placeValue=<%=placeValue%>" method="post">
            <div class="form-group">
                <label>ФИО</label>
                <input type="text" class="form-control" name="username" id="username" placeholder="ФИО">
            </div>
            <div class="form-group">
                <label>Номер телефона</label>
                <input type="text" class="form-control" name="phone" id="phone" placeholder="Номер телефона">
            </div>
            <div class="form-group">
                <label>Email</label>
                <input type="text" class="form-control" name="email" id="email" placeholder="Email">
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-success" onclick="return validate();">Оплатить</button>
            </div>
        </form>
    </div>
</div>
<div class="container">
    <a class="nav-link" href="<%=request.getContextPath()%>/index.html">Вернуться на страницу выбора билетов.</a>
</div>

</body>
</html>
