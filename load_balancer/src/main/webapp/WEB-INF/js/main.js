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
        $('.ui.accordion')
          .accordion()
        ;
      /* $('.menu .item')
         .tab()
       ;*/
        $('.menu .item')
                .tab({
                    cache: false,
                    // faking api request
                    apiSettings: {
                      loadingDuration : 300,
                      mockResponse    : function(settings) {


                        var response = {
                          first  : function(){
                           $.ajax({
                            url: '/stlb/nodeInfoFragment-' + $("#nodeId").val(),
                             success: function(data) {
                             $('#first-tab').html(data);
                             }
                            });
                          },
                          second : function(){
                            $.ajax({
                                   url: '/stlb/nodeLoading-' + $("#nodeId").val(),
                                    success: function(data) {
                                    $('#second-tab').html(data);
                                    }
                                   });
                                 },

                          third : function(){
                           $.ajax({
                                  url: '/stlb/cpuGraph-' + $("#nodeId").val(),
                                   success: function(data) {
                                   $('#third-tab').html(data);
                                   }
                                  });},
                          fifth : function(){
                             $.ajax({
                                    url: '/stlb/cpuProcessGraph-' + $("#nodeId").val(),
                                     success: function(data) {
                                     $('#fifth-tab').html(data);
                                     }
                                    });},
                            forth : function(){
                                 $.ajax({
                                        url: '/stlb/memoryGraph-' + $("#nodeId").val(),
                                         success: function(data) {
                                         $('#forth-tab').html(data);
                                         }
                                        });}


                        };
                        return response[settings.urlData.tab];
                      }
                    },
                    context : 'parent',
                    auto    : true,
                    path    : '/'
                  })
                ;
})();