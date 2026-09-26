<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>

<@main.layout>

    <div class="card">

        <h1>
            <@spring.message "admin.details.title"/>
            #${transaction.id}
        </h1>


        <#if RequestParameters.approved??>

            <p>
                <@spring.message "admin.details.approved"/>
            </p>

        </#if>


        <#if error??>

            <p class="error">
                ${error}
            </p>

        </#if>


        <p>
            <strong>
                <@spring.message "admin.transactions.type"/>:
            </strong>

            ${transaction.type}
        </p>


        <p>
            <strong>
                <@spring.message "admin.transactions.status"/>:
            </strong>

            ${transaction.status}
        </p>


        <p>
            <strong>
                <@spring.message "admin.transactions.amount"/>:
            </strong>

            ${transaction.amount?string["0.00"]}
            ${transaction.currency}
        </p>


        <#if transaction.senderAccount??>

            <p>
                <strong>
                    <@spring.message "admin.details.sender"/>:
                </strong>

                ${transaction.senderAccount.accountNumber}
            </p>

        </#if>


        <#if transaction.receiverAccount??>

            <p>
                <strong>
                    <@spring.message "admin.details.receiver"/>:
                </strong>

                ${transaction.receiverAccount.accountNumber}
            </p>

        </#if>


        <#if transaction.receivedAmount??>

            <p>
                <strong>
                    <@spring.message "admin.details.received"/>:
                </strong>

                ${transaction.receivedAmount?string["0.00"]}

                <#if transaction.receiverAccount??>
                    ${transaction.receiverAccount.currency}
                </#if>

            </p>

        </#if>


        <#if transaction.exchangeRate??>

            <p>
                <strong>
                    <@spring.message "admin.details.rate"/>:
                </strong>

                ${transaction.exchangeRate}
            </p>

        </#if>


        <p>
            <strong>
                <@spring.message "admin.transactions.date"/>:
            </strong>

            ${transaction.createdAt}
        </p>


        <#if transaction.status?string == "PENDING">

            <form
                    action="/admin/transactions/${transaction.id}/approve"
                    method="post"
            >

                <#if _csrf??>

                    <input
                            type="hidden"
                            name="${_csrf.parameterName}"
                            value="${_csrf.token}"
                    >

                </#if>

                <button type="submit">
                    <@spring.message "admin.details.approve"/>
                </button>

            </form>

        </#if>

    </div>

</@main.layout>