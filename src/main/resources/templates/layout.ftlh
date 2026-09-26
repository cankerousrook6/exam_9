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

<header class="header">

    <div class="nav-container">

        <div class="nav-left">

            <a
                    href="/"
                    class="nav-logo"
            >
                MoneyTransferApp
            </a>


            <#if SPRING_SECURITY_CONTEXT??>

                <#if isAdmin?? && isAdmin>

                    <a href="/admin/transactions">
                        <@spring.message "nav.adminTransactions"/>
                    </a>

                    <a href="/admin/transactions/pending">
                        <@spring.message "nav.pendingTransactions"/>
                    </a>

                <#else>

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

                </#if>

            </#if>

        </div>


        <div class="nav-right">

            <a href="?lang=ru">
                RU
            </a>

            <a href="?lang=en">
                EN
            </a>


            <#if SPRING_SECURITY_CONTEXT??>

                <form
                        action="/logout"
                        method="post"
                        class="logout-form"
                >

                    <#if _csrf??>

                        <input
                                type="hidden"
                                name="${_csrf.parameterName}"
                                value="${_csrf.token}"
                        >

                    </#if>

                    <button
                            type="submit"
                            class="nav-button"
                    >
                        <@spring.message "nav.logout"/>
                    </button>

                </form>

            <#else>

                <a
                        href="/login"
                        class="nav-button"
                >
                    <@spring.message "nav.login"/>
                </a>

                <a
                        href="/register"
                        class="nav-button"
                >
                    <@spring.message "nav.register"/>
                </a>

            </#if>

        </div>

    </div>

</header>


<main>

    <#nested>

</main>


<script src="/js/main.js"></script>

</body>

</html>

</#macro>