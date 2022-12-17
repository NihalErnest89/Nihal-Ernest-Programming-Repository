var lineData = [];


var driversLine = {'Lewis Hamilton': 1, 'Nick Heidfeld': 2, 'Nico Rosberg': 3, 'Fernando Alonso': 4, 'Heikki Kovalainen': 5, 'Kazuki Nakajima': 6, 'Sébastien Bourdais': 7, 'Kimi Räikkönen': 8, 'Robert Kubica': 9, 'Timo Glock': 10, 'Takuma Sato': 11, 'Nelson Piquet Jr.': 12, 'Felipe Massa': 13, 'David Coulthard': 14, 'Jarno Trulli': 15, 'Adrian Sutil': 16, 'Mark Webber': 17, 'Jenson Button': 18, 'Anthony Davidson': 19, 'Sebastian Vettel': 20, 'Giancarlo Fisichella': 21, 'Rubens Barrichello': 22, 'Ralf Schumacher': 23, 'Vitantonio Liuzzi': 24, 'Alexander Wurz': 25, 'Scott Speed': 26, 'Christijan Albers': 27, 'Sakon Yamamoto': 28, 'Michael Schumacher': 29, 'Juan Pablo Montoya': 30, 'Christian Klien': 31, 'Tiago Monteiro': 32, 'Yuji Ide': 33, 'Jacques Villeneuve': 34, 'Franck Montagny': 35, 'Pedro de la Rosa': 36, 'Robert Doornbos': 37, 'Narain Karthikeyan': 38, 'Patrick Friesacher': 39, 'Ricardo Zonta': 40, 'Antônio Pizzonia': 41, 'Cristiano da Matta': 42, 'Olivier Panis': 43, 'Giorgio Pantano': 44, 'Gianmaria Bruni': 45, 'Zsolt Baumgartner': 46, 'Marc Gené': 47, 'Heinz-Harald Frentzen': 48, 'Jos Verstappen': 49, 'Justin Wilson': 50, 'Ralph Firman': 51, 'Nicolas Kiesa': 52, 'Jean Alesi': 53, 'Eddie Irvine': 54, 'Mika Häkkinen': 55, 'Tarso Marques': 56, 'Enrique Bernoldi': 57, 'Gastón Mazzacane': 58, 'Tomáš Enge': 59, 'Alex Yoong': 60, 'Mika Salo': 61, 'Pedro Diniz': 62, 'Johnny Herbert': 63, 'Allan McNish': 64, 'Sébastien Buemi': 65, 'Toranosuke Takagi': 66, 'Luca Badoer': 67, 'Alessandro Zanardi': 68, 'Damon Hill': 69, 'Ricardo Rosset': 70, 'Esteban Tuero': 71, 'Shinji Nakano': 72, 'Jan Magnussen': 73, 'Gerhard Berger': 74, 'Nicola Larini': 75, 'Ukyo Katayama': 76, 'Gianni Morbidelli': 77, 'Norberto Fontana': 78, 'Pedro Lamy': 79, 'Martin Brundle': 80, 'Andrea Montermini': 81, 'Giovanni Lavaggi': 82, 'Jaime Alguersuari': 83, 'Romain Grosjean': 84, 'Kamui Kobayashi': 85, 'Nico Hülkenberg': 86, 'Vitaly Petrov': 87, 'Lucas di Grassi': 88, 'Bruno Senna': 89, 'Karun Chandhok': 90, 'Pastor Maldonado': 91, 'Paul di Resta': 92, 'Sergio Pérez': 93, "Jérôme d'Ambrosio": 94, 'Daniel Ricciardo': 95, 'Jean-Éric Vergne': 96, 'Charles Pic': 97, 'Max Chilton': 98, 'Esteban Gutiérrez': 99, 'Valtteri Bottas': 100, 'Giedo van der Garde': 101, 'Jules Bianchi': 102, 'Kevin Magnussen': 103, 'Daniil Kvyat': 104, 'André Lotterer': 105, 'Marcus Ericsson': 106, 'Will Stevens': 107, 'Max Verstappen': 108, 'Felipe Nasr': 109, 'Carlos Sainz': 110, 'Roberto Merhi': 111, 'Alexander Rossi': 112, 'Jolyon Palmer': 113, 'Pascal Wehrlein': 114, 'Rio Haryanto': 115, 'Stoffel Vandoorne': 116, 'Esteban Ocon': 117, 'Lance Stroll': 118, 'Antonio Giovinazzi': 119, 'Pierre Gasly': 120, 'Brendon Hartley': 121, 'Charles Leclerc': 122, 'Sergey Sirotkin': 123, 'Lando Norris': 124, 'George Russell': 125, 'Alexander Albon': 126, 'Nicholas Latifi': 127, 'Pietro Fittipaldi': 128, 'Jack Aitken': 129, 'Yuki Tsunoda': 130, 'Stefano Modena': 131, 'Mick Schumacher': 132, 'Guanyu Zhou': 133, 'Nikita Mazepin': 134};

function drawLineChart(){
var margin = { top: 10, right: 90, bottom: 150, left: 0 },
  chart_width = ((3 * main_body_width) / 10) - margin.left - margin.right,
  chart_height = 475 - margin.top - margin.bottom;

var svg2 = d3
  .select(".svg2")
  .append("svg")
  //.attr("width", width + margin.left + margin.right)
  .attr("width", "" + ((40 * main_body_width) / 100) + "px")
  .attr("height", chart_height + margin.top + margin.bottom)
  .append("g")
  .attr("transform", "translate(" + ((5 * main_body_width) / 100) + margin.left + "," + margin.top + ")");

var xScale = d3.scaleLinear().range([0, chart_width]);
var yScale = d3.scaleLinear().range([chart_height, 0]);
var colorScale = d3.scaleOrdinal(d3.schemeCategory10);

var isDark2 = false;

var xAxis = d3.axisBottom(xScale).tickFormat(d3.format("d"));
var yAxis = d3.axisLeft(yScale);

// hard coded driver id's


    
var driverList = [];

for (var nnr = 0; nnr < lineData.length; nnr++) {
    driverList.push(driversLine[lineData[nnr]]);
}

console.log("asssssssssssssssssss");
console.log(driverList);    
    
function rowConverter(d) {
    console.log(d.driverId);
    return {
        dName: d.driverName,
        dId: +d.driverId,
        dPointYears: [
        [2004,+d.driverPoints2004],
        [2005,+d.driverPoints2005],
        [2006,+d.driverPoints2006],
        [2007,+d.driverPoints2007],
        [2008,+d.driverPoints2008],
        [2009,+d.driverPoints2009],
        [2010,+d.driverPoints2010],
        [2011,+d.driverPoints2011],
        [2012,+d.driverPoints2012],
        [2013,+d.driverPoints2013],
        [2014,+d.driverPoints2014],
        [2015,+d.driverPoints2015],
        [2016,+d.driverPoints2016],
        [2017,+d.driverPoints2017],
        [2018,+d.driverPoints2018],
        [2019,+d.driverPoints2019],
        [2020,+d.driverPoints2020],
        [2021,+d.driverPoints2021],
        [2022,+d.driverPoints2022]
        ]
    }   
}

var startYear = 2004;
var endYear = 2022;

var years = [/*2004, 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012, 2013, 2014, 2015, 2016,*/ 2017, 2018, 2019, 2020, 2021, 2022]


// gridlines in x axis function
function make_x_gridlines() {
  return d3.axisBottom(xScale).ticks(0).tickSizeOuter(0);
}

// gridlines in y axis function
function make_y_gridlines() {
  return d3.axisLeft(yScale).ticks(0).tickSizeOuter(0);
}

// This function parses the data using rowConverter and makes the bar chart based on that data
d3.csv("data/F1data.csv", rowConverter).then(function (data) {


  var idx = 0;

  // Setting the ranges for the x domain, y domain, and color domain
  xScale.domain([2004, 2022]);
  yScale.domain([0, 460]);

  // Setting the color domain from 0 to 5 because there are 6 countries that need 6 different colors
  colorScale.domain([0, 1]);
  //    x.domain(data.map(function(d){ return d.years[idx++][0]; }));

  // add the X gridlines
  for (let x = 50; x < endYear; x += 50) {
    svg2
      .append("g")
      .attr("class", "grid")
      .attr("transform", "translate(0," + yScale(x) + ")")
      .call(make_x_gridlines().tickSize(0).tickFormat(""));
  }
  // add the Y gridlines
  for (let x = startYear; x < endYear; x += 1) {
    svg2
      .append("g")
      .attr("class", "grid")
      .attr("transform", "translate(" + xScale(x) + ",0)")
      .call(make_y_gridlines().tickSize(0).tickFormat(""));
  }

  // the d3.line() function returns a line so we save it to lineGen
  var lineGen = d3
    .line()
    .x(function (d) { //year
      return xScale(d[0]);
    })
    .y(function (d) { //points
      return yScale(d[1]);
    });

  // Uses the curve basis on the lines
  lineGen.curve(d3.curveBasis);

  var line;
  group = svg2.append("g").attr("class", "countryLines");

  // Goes through each individual line
  for (let x = 0; x < data.length; x++) {
      console.log(data);
      if(driverList.includes(data[x]['dId'])){
    // Creates the path with the country
    line = group
      .append("path")
      .attr("class", data[x][0] + " line")
      .attr("d", lineGen(data[x]["dPointYears"]))
      .attr("stroke", colorScale(x))
      .attr("stroke-width", "2")
      .attr("fill", "none");

    // Sets the state before animations
    line
      .attr("stroke-dashoffset", line.node().getTotalLength())
      .attr("stroke-dasharray", line.node().getTotalLength());

    // Animation
    line
      .transition()
      .delay(x + 1000)
      .duration(1000)
      .attr("stroke-dashoffset", "0");

    // Adds the country labels to the side of the lines
    text = group
      .append("text")
      .attr("class", "countryLabel")
      .text(data[x]["dName"])
      .attr("y", yScale(data[x]["dPointYears"][2022-2004][1]))
      .attr("x", chart_width + 10)
      .style("fill", colorScale(x))
      .attr("font-size", "14px");
    // .style("opacity")
    }
  }

  // Draw xAxis and position the label
  svg2
    .append("g")
    .attr("class", "axis--x")
    .attr("transform", "translate(0," + chart_height + ")")
    .call(xAxis);

  // Draw yAxis and position the label
  svg2
    .append("g")
    .attr("class", "axis--y")
    .attr("transform", "translate(" + 0 + ",0)")
    .call(yAxis);

  // This function creates the y-axis label, and positions it near the y-axis with respect to the margins,
  // and based on the pre-defined height
  svg2
    .append("text")
    .attr("transform", "rotate(-90)")
    .attr("y", 0 - margin.leftx)
    .attr("x", 0 - chart_height / 2)
    .attr("dy", "-2.8em")
    .style("text-anchor", "middle")
    .text("Points")
    .attr("font-size", "14px");

  // Adding the Year label on x axis
  svg2
    .append("text")
    .attr("y", chart_height+75)
    .attr("x", chart_width/2)
    .attr("dy", "-2.8em")
    .text("Year")
    .attr("font-size", "14px");
    
  svg2.selectAll("g").attr("color", "crimson");
  svg2.selectAll("text").attr("fill", "crimson");
});

function temp2() {
    var background = document.body.style.backgroundColor;
    if (isDark2 == false) {    
        svg2.selectAll("g").attr("color", "white");
        svg2.selectAll("text").attr("fill", "white");
        isDark2 = true;
    }
    
    else {
        svg2.selectAll("g").attr("color", "black");
        svg2.selectAll("text").attr("fill", "black");
        isDark2 = false;
    }
}
}

function clearLines(){
    console.log("remove");
    d3.select(".svg2 svg").remove();
}