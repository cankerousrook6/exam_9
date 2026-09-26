<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>

<@main.layout>

    <div class="form-card">

        <h1>
            <@spring.message "login.title"/>
        </h1>


        <#if RequestParameters.error??>

            <p class="error">
                <@spring.message "login.error"/>
            </p>

        </#if>


        <#if RequestParameters.registered??>

            <p>
                <@spring.message "login.registered"/>
            </p>

        </#if>


        <#if RequestParameters.logout??>

            <p>
                <@spring.message "login.logout"/>
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
                    <@spring.message "login.username"/>
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
                    <@spring.message "login.password"/>
                </label>

                <input
                        id="password"
                        type="password"
                        name="password"
                        required
                >

            </div>


            <button type="submit">
                <@spring.message "login.button"/>
            </button>

        </form>


        <p>

            <@spring.message "login.noAccount"/>

            <a href="/register">
                <@spring.message "login.register"/>
            </a>

        </p>

    </div>

</@main.layout>