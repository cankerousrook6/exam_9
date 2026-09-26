<#import "layout.ftlh" as main>
<@main.layout>
<div class="card">

    <h1>
        Профиль
    </h1>

    <p>
        Пользователь:
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
        Создать счет
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
                Валюта
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
            Создать счет
        </button>

    </form>

</div>


<div class="card">

    <h2>
        Мои счета
    </h2>


    <#if accounts?has_content>

        <table>

            <thead>

            <tr>

                <th>
                    Номер счета
                </th>

                <th>
                    Валюта
                </th>

                <th>
                    Баланс
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
            У вас пока нет счетов.
        </p>

    </#if>

</div>
/@main.layout