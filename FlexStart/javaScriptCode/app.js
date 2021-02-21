var document = document;
var window = window;
var d3 = d3;

function heroColor(d, index) {
    'use strict';
    if (index === 0) { // ironman
        return '#DA0716';
    } else if (index === 1) {
        return '#007315'; // hulk!
    } else {
        return '#006eff';
    }
}

function addTen(heroBar) {
    'use strict';
    var oldVal = heroBar.getAttribute('width');
    heroBar.setAttribute('width', parseInt(oldVal, 10) + 10);
}

function removeTen(heroBar) {
    'use strict';
    var oldVal = heroBar.getAttribute('width');
    heroBar.setAttribute('width', parseInt(oldVal, 10) - 10);
}

function init() {
    'use strict';
    var data = [50, 75, 12];
    var svg = d3.select('#chart')
              .append('svg')
              .attr('width', 500)
              .attr('height', 200);
    var heroBars = svg.selectAll('rect')
                .data(data)
                .enter()
                .append('rect')
                .attr('height', 50)
                .attr('width', function (d) { return d; })
                .attr('y', function (d, index) { return index * 55; })
                .attr('fill', heroColor);
    document.getElementById('ironman').addEventListener('click', function () { addTen(heroBars[0][0]); });
    document.getElementById('hulk').addEventListener('click', function () { addTen(heroBars[0][1]); });
    document.getElementById('thor').addEventListener('click', function () { addTen(heroBars[0][2]); });
    document.getElementById('noironman').addEventListener('click', function () { removeTen(heroBars[0][0]); });
    document.getElementById('nohulk').addEventListener('click', function () { removeTen(heroBars[0][1]); });
    document.getElementById('nothor').addEventListener('click', function () { removeTen(heroBars[0][2]); });
}

window.onload = init;