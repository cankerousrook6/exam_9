<#import "layout.ftlh" as main>
<#import "/spring.ftl" as spring>

<@main.layout>

    <div class="card">

        <h1>
            <@spring.message "profile.title"/>
        </h1>

        <p>

            <@spring.message "profile.user"/>:

            <strong>
                ${user.username}
            </strong>

        </p>

    </div>


    <#if error??>

        <div class="card">

            <p class="error">
                ${error}
            </p>

        </div>

    </#if>


    <div class="form-card">

        <h2>
            <@spring.message "profile.createAccount"/>
        </h2>

        <form
                action="/accounts/create"
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

                <label for="currency">
                    <@spring.message "profile.currency"/>
                </label>

                <select
                        id="currency"
                        name="currency"
                        required
                >

                    <#list currencies as currency>

                        <option value="${currency}">
                            ${currency}
                        </option>

                    </#list>

                </select>

            </div>


            <button type="submit">
                <@spring.message "profile.create"/>
            </button>

        </form>

    </div>


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

</@main.layout>