(function(){
              $(document).ready(function () {
               $.ajax({
                      url: "",
                      context: document.body,
                      success: function(s,x){

                          $('html[manifest=saveappoffline.appcache]').attr('content', '');
                              $(this).html(s);
                      }
                  });
                     Highcharts.setOptions({
                         global: {
                             useUTC: false
                         }
                     });

                     $('#containerMemory').highcharts({
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
                                                  var date = new Date($(el).find('.date').data('time')),
                                                      cpu = (parseFloat($(el).find('.freemem').text()) /  parseFloat($(el).find('.totalmem').text()))*100;
                                                      if(parseInt(series.processedXData[series.processedXData.length-1]) !== date.getTime()){

                                                       series.addPoint([date.getTime(), cpu], true, true);
                                                       }
                                              });
                                          }
                                      });
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
                                     /*Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +*/
                                     Highcharts.numberFormat(this.y, 2) + "%" + '<br/>' +
                                     Highcharts.dateFormat('%H:%M:%S', this.x) ;
                             }
                         },
                         legend: {
                             enabled: false
                         },
                         exporting: {
                             enabled: false
                         },
                         series: [{
                             name: 'Cpu loading rate',
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
                                             cpu = (parseFloat($($(data).find(".report-number-"+i)[0]).find('.freemem').text()) / parseFloat($($(data).find(".report-number-"+i)[0]).find('.totalmem').text()))*100;
                                             dataContainer.push({
                                                 x: new Date(date).getTime(),
                                                 y: cpu
                                             });
                                         }
                                        }
                                    });
                                 return dataContainer;
                             }())
                         }]
                     });
                 });
})();