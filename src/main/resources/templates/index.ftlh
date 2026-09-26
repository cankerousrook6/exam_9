<#import "layout.ftlh" as main>
<#import "/spring.ftl" as spring>

<@main.layout>

    <div class="card">

        <h1>
            <@spring.message "home.title"/>
        </h1>

        <p>
            <@spring.message "home.description"/>
        </p>

        <a
                href="/login"
                class="btn"
        >
            <@spring.message "home.login"/>
        </a>

        <a
                href="/register"
                class="btn"
        >
            <@spring.message "home.register"/>
        </a>

    </div>


    <div class="form-card">

        <h2>
            <@spring.message "topup.title"/>
        </h2>

        <p>
            <@spring.message "topup.description"/>
        </p>


        <#if success??>

            <p>
                <@spring.message "topup.success"/>
            </p>

        </#if>


        <#if error??>

            <p class="error">
                ${error}
            </p>

        </#if>


        <form
                action="/top-up"
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

                <label for="accountNumber">
                    <@spring.message "topup.accountNumber"/>
                </label>

                <input
                        id="accountNumber"
                        type="text"
                        name="accountNumber"
                        value="${(topUp.accountNumber)!''}"
                        maxlength="6"
                >

                <@spring.bind "topUp.accountNumber"/>
                <@spring.showErrors "<br>" "error"/>

            </div>


            <div class="form-group">

                <label for="amount">
                    <@spring.message "topup.amount"/>
                </label>

                <input
                        id="amount"
                        type="number"
                        name="amount"
                        min="0.01"
                        step="0.01"
                        value="${(topUp.amount)!''}"
                >

                <@spring.bind "topUp.amount"/>
                <@spring.showErrors "<br>" "error"/>

            </div>


            <button type="submit">
                <@spring.message "topup.button"/>
            </button>

        </form>

    </div>

</@main.layout>