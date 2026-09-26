<#import "/spring.ftl" as spring>

<#macro layout>

<!DOCTYPE html>

<html>

<head>

    <meta charset="UTF-8">

    <meta
            name="viewport"
            content="width=device-width, initial-scale=1.0"
    >

    <title>
        <@spring.message "app.name"/>
    </title>

    <link
            rel="stylesheet"
            href="/css/main.css"
    >

</head>

<body>

<header>

    <nav>

        <a href="/">
            <@spring.message "nav.home"/>
        </a>

        <a href="/profile">
            <@spring.message "nav.profile"/>
        </a>

        <a href="/transactions">
            <@spring.message "nav.transactions"/>
        </a>

        <a href="/transfer">
            <@spring.message "nav.transfer"/>
        </a>

        <a href="/services">
            <@spring.message "nav.services"/>
        </a>

        <a href="?lang=ru">
            <@spring.message "language.ru"/>
        </a>

        <a href="?lang=en">
            <@spring.message "language.en"/>
        </a>

    </nav>

</header>


<main>

    <#nested>

</main>

</body>

</html>

</#macro>