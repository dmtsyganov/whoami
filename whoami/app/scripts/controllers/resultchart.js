'use strict';

myApp
    .controller('ResultChartCtrl', ['$scope', 'alltraits',
        function ($scope, alltraits) {

            var resultChart = {};
            resultChart.type = "AreaChart"; // "LineChart"
            resultChart.displayed = false;
            resultChart.cssStyle = "height:600px; width:100%;";
            resultChart.data = {"cols": [
                {id: "trait", label: "Характеристика", type: "string"},
                {id: "direct", label: "Прямая Оценка", type: "number"},
                {id: "inderect", label: "Косвенная Оценка", type: "number"}
            ], "rows": [

            ]};

            function getScore(trait, traitScores) {
                var count = traitScores.length;
                for(var i = 0; i < count; i++) {
                    if (trait.localeCompare(traitScores[i].trait) === 0) {
                       return traitScores[i].score;
                    }
                }

                return 0;
            }

            function initDataRows() {
                var rows = [];

                var count = alltraits.length;

                for (var i = 0; i < count; i++) {

                    var data = {c: [
                        {v: alltraits[i].description},
                        {v: getScore(alltraits[i].name, $scope.results.directScores)},
                        {v: getScore(alltraits[i].name, $scope.results.indirectScores)}
                    ]};

                    rows.push(data);
                }

                return rows;
            }

            resultChart.data.rows = initDataRows();

            resultChart.options = {
                "title": "Результат тестирования",
                "isStacked": "false",
                "fill": 20,
                "displayExactValues": true,
                "vAxis": {
                    "title": "Уровень выраженности", "gridlines": {"count": 8}
                },
                "hAxis": {
                    "title": "Характеристики"
                }
            };

            resultChart.formatters = {};

            $scope.chart = resultChart;

            $scope.chartReady = function () {
                fixGoogleChartsBarsBootstrap();
            }

            function fixGoogleChartsBarsBootstrap() {
                // Google charts uses <img height="12px">, which is incompatible with Twitter
                // * bootstrap in responsive mode, which inserts a css rule for: img { height: auto; }.
                // *
                // * The fix is to use inline style width attributes, ie <img style="height: 12px;">.
                // * BUT we can't change the way Google Charts renders its bars. Nor can we change
                // * the Twitter bootstrap CSS and remain future proof.
                // *
                // * Instead, this function can be called after a Google charts render to "fix" the
                // * issue by setting the style attributes dynamically.

                $(".google-visualization-table-table img[width]").each(function (index, img) {
                    $(img).css("width", $(img).attr("width")).css("height", $(img).attr("height"));
                });
            };
        }]);
