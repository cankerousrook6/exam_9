<#macro layout>

<!DOCTYPE html>
<html lang="ru">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>MoneyTransferApp</title>

    <link
            rel="stylesheet"
            href="/css/main.css"
    >

</head>

<body>

<header>

    <nav>

        <a href="/">
            MoneyTransferApp
        </a>

        <a href="/profile">
            Профиль
        </a>

        <a href="/transactions">
            Транзакции
        </a>

        <a href="/transfer">
            Перевод
        </a>

        <a href="/services">
            Оплата услуг
        </a>

    </nav>

</header>


<main>

    <#nested>

</main>


</body>

</html>

</#macro>