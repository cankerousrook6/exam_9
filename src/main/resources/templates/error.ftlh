<#import "layout.ftlh" as main>
<#import "/spring.ftl" as spring>

<@main.layout>

    <div class="card">

        <h1>
            <@spring.message "error.title"/>
        </h1>

        <p class="error">
            <#if errorMessage??>
                ${errorMessage}
            <#else>
                <@spring.message "error.general"/>
            </#if>
        </p>

        <a
                href="/"
                class="btn"
        >
            <@spring.message "error.home"/>
        </a>

    </div>

</@main.layout>