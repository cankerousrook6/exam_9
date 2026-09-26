<#import "layout.ftlh" as main>
<#import "/spring.ftl" as spring>

<@main.layout>

    <div class="card">

        <h1>
            <@spring.message "transactions.title"/>
        </h1>

    </div>


    <div class="form-card">

        <form
                action="/transactions"
                method="get"
        >

            <div class="form-group">

                <label for="dateFrom">
                    <@spring.message "transactions.dateFrom"/>
                </label>

                <input
                        id="dateFrom"
                        type="date"
                        name="dateFrom"
                        value="${dateFromValue!''}"
                >

            </div>


            <div class="form-group">

                <label for="dateTo">
                    <@spring.message "transactions.dateTo"/>
                </label>

                <input
                        id="dateTo"
                        type="date"
                        name="dateTo"
                        value="${dateToValue!''}"
                >

            </div>


            <div class="form-group">

                <label for="sort">
                    <@spring.message "transactions.sort"/>
                </label>

                <select
                        id="sort"
                        name="sort"
                >

                    <option
                            value="date"
                            <#if sort == "date">
                                selected
                            </#if>
                    >
                        <@spring.message "transactions.sortDate"/>
                    </option>

                    <option
                            value="currency"
                            <#if sort == "currency">
                                selected
                            </#if>
                    >
                        <@spring.message "transactions.sortCurrency"/>
                    </option>

                </select>

            </div>


            <button type="submit">
                <@spring.message "transactions.filter"/>
            </button>

            <a
                    href="/transactions"
                    class="btn"
            >
                <@spring.message "transactions.reset"/>
            </a>

        </form>

    </div>


    <div class="card">

        <#if transactions?has_content>

            <table>

                <thead>

                <tr>

                    <th>
                        <@spring.message "transactions.date"/>
                    </th>

                    <th>
                        <@spring.message "transactions.type"/>
                    </th>

                    <th>
                        <@spring.message "transactions.amount"/>
                    </th>

                    <th>
                        <@spring.message "transactions.currency"/>
                    </th>

                    <th>
                        <@spring.message "transactions.counterparty"/>
                    </th>

                </tr>

                </thead>


                <tbody>

                <#list transactions as transaction>

                    <#assign
                            typeKey =
                            "transaction.type."
                            + transaction.type?string?lower_case
                    >

                    <tr>

                        <td>
                            ${transaction.createdAt}
                        </td>


                        <td>
                            <@spring.message typeKey/>
                        </td>


                        <#if transaction.senderAccount??
                        && transaction.senderAccount.user.id == userId>

                            <td>
                                ${transaction.amount?string["0.00"]}
                            </td>

                            <td>
                                ${transaction.currency}
                            </td>

                        <#else>

                            <td>
                                ${transaction.receivedAmount?string["0.00"]}
                            </td>

                            <td>

                                <#if transaction.receiverAccount??>
                                    ${transaction.receiverAccount.currency}
                                <#else>
                                    ${transaction.currency}
                                </#if>

                            </td>

                        </#if>


                        <td>

                            <#if transaction.type?string == "SERVICE_PAYMENT">

                                ${transaction.description}

                            <#elseif transaction.senderAccount??
                            && transaction.senderAccount.user.id == userId>

                                <#if transaction.receiverAccount??>

                                    ${transaction.receiverAccount.user.username}
                                    (${transaction.receiverAccount.accountNumber})

                                <#else>

                                    <@spring.message "transactions.system"/>

                                </#if>

                            <#elseif transaction.senderAccount??>

                                ${transaction.senderAccount.user.username}
                                (${transaction.senderAccount.accountNumber})

                            <#else>

                                <@spring.message "transactions.topUp"/>

                            </#if>

                        </td>

                    </tr>

                </#list>

                </tbody>

            </table>

        <#else>

            <p>
                <@spring.message "transactions.empty"/>
            </p>

        </#if>

    </div>

</@main.layout>