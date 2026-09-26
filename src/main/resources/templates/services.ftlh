<#import "layout.ftlh" as main>
<#import "/spring.ftl" as spring>

<@main.layout>

    <div class="form-card">

        <h1>
            <@spring.message "service.title"/>
        </h1>

        <p>
            <@spring.message "service.description"/>
        </p>


        <#if success??>

            <p>
                <@spring.message "service.success"/>
            </p>

        </#if>


        <#if error??>

            <p class="error">
                ${error}
            </p>

        </#if>


        <#if accounts?has_content>

            <form
                    action="/services"
                    method="post"
                    class="confirm-form"
                    data-confirm-message="<@spring.message "service.confirm"/>"
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
                        <@spring.message "service.sender"/>
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
                                    <#if payment.senderAccountId??
                                    && payment.senderAccountId == account.id>
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

                    <@spring.bind "payment.senderAccountId"/>
                    <@spring.showErrors "<br>" "error"/>

                </div>


                <div class="form-group">

                    <label for="providerId">
                        <@spring.message "service.provider"/>
                    </label>

                    <select
                            id="providerId"
                            name="providerId"
                    >

                        <option value="">
                            <@spring.message "service.selectProvider"/>
                        </option>

                        <#list providers as provider>

                            <option
                                    value="${provider.id}"
                                    <#if payment.providerId??
                                    && payment.providerId == provider.id>
                                        selected
                                    </#if>
                            >
                                ${provider.name}
                            </option>

                        </#list>

                    </select>

                    <@spring.bind "payment.providerId"/>
                    <@spring.showErrors "<br>" "error"/>

                </div>


                <div class="form-group">

                    <label for="requisite">
                        <@spring.message "service.requisite"/>
                    </label>

                    <input
                            id="requisite"
                            type="text"
                            name="requisite"
                            value="${(payment.requisite)!''}"
                    >

                    <@spring.bind "payment.requisite"/>
                    <@spring.showErrors "<br>" "error"/>

                </div>


                <div class="form-group">

                    <label for="amount">
                        <@spring.message "service.amount"/>
                    </label>

                    <input
                            id="amount"
                            type="number"
                            name="amount"
                            min="0.01"
                            step="0.01"
                            value="${(payment.amount)!''}"
                    >

                    <@spring.bind "payment.amount"/>
                    <@spring.showErrors "<br>" "error"/>

                </div>


                <button type="submit">
                    <@spring.message "service.button"/>
                </button>

            </form>

        <#else>

            <p>
                <@spring.message "service.noAccounts"/>
            </p>

        </#if>

    </div>

</@main.layout>