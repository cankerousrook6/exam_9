document.addEventListener(
    "DOMContentLoaded",
    function () {

        const confirmForms =
            document.querySelectorAll(
                ".confirm-form"
            );

        confirmForms.forEach(
            function (form) {

                form.addEventListener(
                    "submit",
                    function (event) {

                        const message =
                            form.dataset.confirmMessage;

                        if (
                            message
                            && !confirm(message)
                        ) {

                            event.preventDefault();
                        }
                    }
                );
            }
        );
    }
);