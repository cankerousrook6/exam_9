<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>

<@main.layout>

    <div class="card">

        <h1>
            <@spring.message "admin.transactions.title"/>
        </h1>

    </div>


    <div class="card">

        <#if transactions?has_content>

            <table>

                <thead>

                <tr>

                    <th>ID</th>

                    <th>
                        <@spring.message "admin.transactions.type"/>
                    </th>

                    <th>
                        <@spring.message "admin.transactions.amount"/>
                    </th>

                    <th>
                        <@spring.message "admin.transactions.currency"/>
                    </th>

                    <th>
                        <@spring.message "admin.transactions.status"/>
                    </th>

                    <th>
                        <@spring.message "admin.transactions.date"/>
                    </th>

                    <th></th>

                </tr>

                </thead>


                <tbody>

                <#list transactions as transaction>

                    <#assign
                            typeKey =
                            "transaction.type."
                            + transaction.type?string?lower_case
                    >

                    <#assign
                            statusKey =
                            "transaction.status."
                            + transaction.status?string?lower_case
                    >

                    <tr>

                        <td>
                            ${transaction.id}
                        </td>

                        <td>
                            <@spring.message typeKey/>
                        </td>

                        <td>
                            ${transaction.amount?string["0.00"]}
                        </td>

                        <td>
                            ${transaction.currency}
                        </td>

                        <td>
                            <@spring.message statusKey/>
                        </td>

                        <td>
                            ${transaction.createdAt}
                        </td>

                        <td>

                            <a
                                    href="/admin/transactions/${transaction.id}"
                                    class="btn"
                            >
                                <@spring.message "admin.transactions.details"/>
                            </a>

                        </td>

                    </tr>

                </#list>

                </tbody>

            </table>

        <#else>

            <p>
                <@spring.message "admin.transactions.empty"/>
            </p>

        </#if>

    </div>

</@main.layout>