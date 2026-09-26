<#import "layout.ftlh" as main>
<#import "/spring.ftl" as spring>

<@main.layout>

    <div class="form-card">

        <h1>
            <@spring.message "transfer.title"/>
        </h1>

        <p>
            <@spring.message "transfer.description"/>
        </p>


        <#if success??>

            <p>
                <@spring.message "transfer.success"/>
            </p>

        </#if>


        <#if pending??>

            <p>
                <@spring.message "transfer.pending"/>
            </p>

        </#if>


        <#if error??>

            <p class="error">
                ${error}
            </p>

        </#if>


        <#if accounts?has_content>

            <form
                    action="/transfer"
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

                    <label for="senderAccountId">
                        <@spring.message "transfer.sender"/>
                    </label>

                    <select
                            id="senderAccountId"
                            name="senderAccountId"
                    >

                        <option value="">
                            <@spring.message "transfer.selectAccount"/>
                        </option>

                        <#list accounts as account>

                            <option
                                    value="${account.id}"
                                    <#if transfer.senderAccountId?? && transfer.senderAccountId == account.id>
                                        selected
                                    </#if>
                            >

                                ${account.accountNumber}
                                -
                                ${account.currency}
                                -
                                ${account.balance?string["0.00"]}

                            </option>

                        </#list>

                    </select>

                    <@spring.bind "transfer.senderAccountId"/>
                    <@spring.showErrors "<br>" "error"/>

                </div>


                <div class="form-group">

                    <label for="receiverAccountNumber">
                        <@spring.message "transfer.receiver"/>
                    </label>

                    <input
                            id="receiverAccountNumber"
                            type="text"
                            name="receiverAccountNumber"
                            maxlength="6"
                            value="${(transfer.receiverAccountNumber)!''}"
                    >

                    <@spring.bind "transfer.receiverAccountNumber"/>
                    <@spring.showErrors "<br>" "error"/>

                </div>


                <div class="form-group">

                    <label for="amount">
                        <@spring.message "transfer.amount"/>
                    </label>

                    <input
                            id="amount"
                            type="number"
                            name="amount"
                            min="0.01"
                            step="0.01"
                            value="${(transfer.amount)!''}"
                    >

                    <@spring.bind "transfer.amount"/>
                    <@spring.showErrors "<br>" "error"/>

                </div>


                <button type="submit">
                    <@spring.message "transfer.button"/>
                </button>

            </form>

        <#else>

            <p>
                <@spring.message "transfer.noAccounts"/>
            </p>

        </#if>

    </div>

</@main.layout>