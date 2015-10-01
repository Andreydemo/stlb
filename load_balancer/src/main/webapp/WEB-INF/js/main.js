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
                            type: 'regExp[/(http+s?):\/\/(localhost:\d*|\w*.\w{2,3})\/?(\w*\/)*/]',
                            prompt : 'Please enter a valid url'
                        }
                    ]
                }
            }
        });
})();