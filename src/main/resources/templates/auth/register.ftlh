<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>

<@main.layout>

    <div class="form-card">

        <h1>
            <@spring.message "register.title"/>
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
                    <@spring.message "register.username"/>
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
                    <@spring.message "register.password"/>
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
                <@spring.message "register.button"/>
            </button>

        </form>


        <p>

            <@spring.message "register.haveAccount"/>

            <a href="/login">
                <@spring.message "register.login"/>
            </a>

        </p>

    </div>

</@main.layout>