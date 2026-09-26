<#import "../layout.ftlh" as main>

<@main.layout>

    <div class="form-card">

        <h1>
            Вход
        </h1>


        <#if RequestParameters.error??>

            <p class="error">
                Неверный логин или пароль
            </p>

        </#if>


        <#if RequestParameters.registered??>

            <p>
                Регистрация прошла успешно
            </p>

        </#if>


        <#if RequestParameters.logout??>

            <p>
                Вы вышли из аккаунта
            </p>

        </#if>


        <form
                action="/login"
                method="post"
        >

            <#if _csrf??>

                <input
                        type="hidden"
                        name="${_csrf.parameterName}"
                        value="${_csrf.token}"
                >

            </#if>


            <div class="form-group">

                <label for="username">
                    Логин
                </label>

                <input
                        id="username"
                        type="text"
                        name="username"
                        required
                >

            </div>


            <div class="form-group">

                <label for="password">
                    Пароль
                </label>

                <input
                        id="password"
                        type="password"
                        name="password"
                        required
                >

            </div>


            <button type="submit">
                Войти
            </button>

        </form>


        <p>

            Нет аккаунта?

            <a href="/register">
                Зарегистрироваться
            </a>

        </p>

    </div>

</@main.layout>