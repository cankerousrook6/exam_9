<#import "layout.ftlh" as main>
<#import "/spring.ftl" as spring>

<@main.layout>

    <div class="card">

        <h1>
            <@spring.message "error.accessDenied.title"/>
        </h1>

        <p>
            <@spring.message "error.accessDenied.message"/>
        </p>

        <a
                href="/"
                class="btn"
        >
            <@spring.message "error.home"/>
        </a>

    </div>

</@main.layout>