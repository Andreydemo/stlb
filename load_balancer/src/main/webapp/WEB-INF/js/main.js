(function(){
    $('.ui.form')
        .form({
            fields: {
                name: {
                    rules: [
                        {
                            type: 'empty',
                            prompt : 'Please enter a name'
                        }
                    ]
                },
                url: {
                    rules: [
                        {
                            type: 'empty',
                            prompt : 'Please enter a url'
                        },
                        {
                            type: 'regExp[]',
                            prompt : 'Please enter a valid url'
                        }
                    ]
                }
            }
        });
})();