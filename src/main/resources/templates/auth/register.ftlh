<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>

<@main.layout>

    <div class="form-card">

        <h1>
            Регистрация
        </h1>

        <form
                action="/register"
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
                        value="${(user.username)!''}"
                >

                <@spring.bind "user.username"/>
                <@spring.showErrors "<br>" "error"/>

            </div>


            <div class="form-group">

                <label for="password">
                    Пароль
                </label>

                <input
                        id="password"
                        type="password"
                        name="password"
                >

                <@spring.bind "user.password"/>
                <@spring.showErrors "<br>" "error"/>

            </div>


            <button type="submit">
                Зарегистрироваться
            </button>

        </form>


        <p>

            Уже есть аккаунт?

            <a href="/login">
                Войти
            </a>

        </p>

    </div>

</@main.layout>