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

        <#if user??>

            <p>
                <@spring.message "profile.user"/>:

                <strong>
                    ${user.username}
                </strong>
            </p>

        </#if>

    </div>


    <#if user??>

        <div class="card">

            <h2>
                <@spring.message "profile.accounts"/>
            </h2>

            <#if accounts?has_content>

                <table>

                    <thead>

                    <tr>

                        <th>
                            <@spring.message "profile.accountNumber"/>
                        </th>

                        <th>
                            <@spring.message "profile.currency"/>
                        </th>

                        <th>
                            <@spring.message "profile.balance"/>
                        </th>

                    </tr>

                    </thead>

                    <tbody>

                    <#list accounts as account>

                        <tr>

                            <td>
                                ${account.accountNumber}
                            </td>

                            <td>
                                ${account.currency}
                            </td>

                            <td>
                                ${account.balance?string["0.00"]}
                            </td>

                        </tr>

                    </#list>

                    </tbody>

                </table>

            <#else>

                <p>
                    <@spring.message "profile.noAccounts"/>
                </p>

            </#if>

        </div>

    </#if>


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
                        maxlength="6"
                        value="${(topUp.accountNumber)!''}"
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