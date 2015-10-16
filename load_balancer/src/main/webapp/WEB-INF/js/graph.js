(function(){
              $(document).ready(function () {
                     Highcharts.setOptions({
                         global: {
                             useUTC: false
                         }
                     });

                     $('#container').highcharts({
                         chart: {
                             type: 'spline',
                             animation: Highcharts.svg, // don't animate in old IE
                             marginRight: 10,
                             events: {
                                 load: function () {

                                     // set up the updating of the chart each second
                                     var series = this.series[0];
                                     setInterval(function () {

                                      $.ajax({
                                         url: '/stlb/nodeLoading-' + $("#nodeId").val(),
                                          async:false,
                                         success: function(data) {
                                              $(data).find('#first-report').each(function(index, el){
                                                  var date = $(el).find('.date').data('time'),
                                                      cpu = parseFloat($(el).find('.scpu').text());
                                                       series.addPoint([new Date(date).getTime(), cpu], true, true);
                                              });
                                          }
                                      });


                                        /* var x = (new Date()).getTime(), // current time
                                             y = Math.random();
                                         series.addPoint([x, y], true, true);*/


                                     }, 1000);
                                 }
                             }
                         },
                         title: {
                             text: 'Cpu loading'
                         },
                         xAxis: {
                             type: 'datetime',
                             tickPixelInterval: 150
                         },
                         yAxis: {
                             title: {
                                 text: 'Value'
                             },
                             plotLines: [{
                                 value: 0,
                                 width: 1,
                                 color: '#808080'
                             }]
                         },
                         tooltip: {
                             formatter: function () {
                                 return '<b>' + this.series.name + '</b><br/>' +
                                     Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
                                     Highcharts.numberFormat(this.y, 2);
                             }
                         },
                         legend: {
                             enabled: false
                         },
                         exporting: {
                             enabled: false
                         },
                         series: [{
                             name: 'Random data',
                             data: (function () {

                                 // generate an array of random data
                                 var dataContainer = [],  i;
                                    $.ajax({
                                       url: '/stlb/nodeLoading-' + $("#nodeId").val(),
                                       async:false,
                                       success: function(data) {
                                        var count = parseInt($(data).find("#reports-count").val())
                                         for (i = count; i > 0; i--) {
                                             var date = $($(data).find(".report-number-"+i)[0]).find('.date').data('time'),
                                             cpu = parseFloat($($(data).find(".report-number-"+i)[0]).find('.scpu').text());
                                             dataContainer.push({
                                                 x: new Date(date).getTime(),
                                                 y: cpu
                                             });
                                         }
                                           /* $(data).find('.report').each(function(index, el){
                                                var date = $(el).find('.date').data('time'),
                                                    cpu = parseFloat($(el).find('.scpu').text());
                                                dataContainer.push({
                                                     x: new Date(date).getTime(),
                                                     y: cpu
                                                 });
                                            });*/
                                        }
                                    });
                                 return dataContainer;
                             }())
                         }]
                     });
                 });
})();