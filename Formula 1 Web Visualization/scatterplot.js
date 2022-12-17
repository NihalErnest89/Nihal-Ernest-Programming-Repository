var main_body_width = parseInt(d3.select("body").style("width"));


    console.log(main_body_width);
    //Define Margin
    var margin = { left: 80, right: 0, top: 50, bottom: 50 },
      width = ((2 * main_body_width) / 3) - margin.left - margin.right - 100,
      height = 700 - margin.top - margin.bottom;

    //Define Color
    // Schemecategory20 got removed in v4
    var colors = d3.scaleOrdinal(d3.schemeCategory10);

    var isDark = false;

    //Define SVG
    var svg1 = d3
      .select(".svg1")
      .append("svg")
      //.attr("width", width + margin.left + margin.right)
      .attr("width", "63%")
      .attr("height", height + margin.top + margin.bottom)
      .append("g")
      .attr("transform", "translate(" + margin.left + "," + margin.top + ")");


    var startDate = "1990-01-01";
    var endDate = "2010-01-01";
    var yearSelected = 2022;


    //Define Tooltip here
    var tooltip = d3.select("body").append("div").attr("class", "tooltip");



    function driverConverter(data) {
      return {
        driverId: +data.driverId,
        driverSurname: data.surname,
        driverFirstname: data.forename,
      };
    }

    function raceConverter(data) {
      return {
        raceId: +data.raceId,
        year: +data.year,
      };
    }

    function resultsConverter(data) {
      return {
        resRaceId: +data.raceId,
        resultId: +data.resultId,
        resDriverId: +data.driverId,
        resConstructorId: +data.constructorId,
      };
    }

    function teamConverter(data) {
      return {
        constructorId: +data.constructorId,
        constructorName: data.name,
      };
    }

    var drivers = [];
    d3.csv("data/drivers.csv", driverConverter).then(function (data) {
      for (var i = 0; i < data.length; i++) {
        drivers.push({
          driverId: data[i].driverId,
          driverName: data[i].driverFirstname + " " + data[i].driverSurname,
        });
      }
    });

    var races = [];
    d3.csv("data/races.csv", raceConverter).then(function (data) {
      for (var i = 0; i < data.length; i++) {
        races.push({ raceId: data[i].raceId, year: data[i].year });
      }
    });

    var constructors = [];
    d3.csv("data/constructors.csv", teamConverter).then(function (data) {
      for (var i = 0; i < data.length; i++) {
        constructors.push({
          teamId: data[i].constructorId,
          teamName: data[i].constructorName,
        });
      }
    });

    d3.csv("data/results.csv", resultsConverter).then(function (data) {
      for (var j = 0; j < data.length; j++) {
        for (var i = 0; i < races.length; i++) {
          if (
            data[j].resRaceId == races[i]["raceId"] &&
            races[i]["year"] == yearSelected
          ) {
            drivers[data[j].resDriverId - 1].teamId = data[j].resConstructorId;
          }
        }
      }
    });

    console.log(drivers);
    console.log(races);
    console.log(constructors);
    
function drawPlot() {
  //Define Scales
  var xScale = d3.scaleLinear().range([0, width]);

  var yScale = d3.scaleLinear().range([height, 0]);

  //Define Axis
  var xAxis = d3.axisBottom(xScale).tickPadding(2);
  var yAxis = d3.axisLeft(yScale).tickPadding(2);

  function rowConverter(data) {
    var s = +data.driverStanding2022;
    var p = +data.driverPoints2022;
    var t = +data.constructorId2022;
    var l = +data.avgLap2022;

    if (yearSelected == 2021) {
      s = +data.driverStanding2021;
      p = +data.driverPoints2021;
      t = +data.constructorId2021;
      l = +data.avgLap2021;
    } else if (yearSelected == 2020) {
      s = +data.driverStanding2020;
      p = +data.driverPoints2020;
      t = +data.constructorId2020;
      l = +data.avgLap2020;
    } else if (yearSelected == 2019) {
      s = +data.driverStanding2019;
      p = +data.driverPoints2019;
      t = +data.constructorId2019;
      l = +data.avgLap2019;
    } else if (yearSelected == 2018) {
      s = +data.driverStanding2018;
      p = +data.driverPoints2018;
      t = +data.constructorId2018;
      l = +data.avgLap2018;
    } else if (yearSelected == 2017) {
      s = +data.driverStanding2017;
      p = +data.driverPoints2017;
      t = +data.constructorId2017;
      l = +data.avgLap2017;
    } else if (yearSelected == 2016) {
      s = +data.driverStanding2016;
      p = +data.driverPoints2016;
      t = +data.constructorId2016;
      l = +data.avgLap2016;
    } else if (yearSelected == 2015) {
      s = +data.driverStanding2015;
      p = +data.driverPoints2015;
      t = +data.constructorId2015;
      l = +data.avgLap2015;
    } else if (yearSelected == 2014) {
      s = +data.driverStanding2014;
      p = +data.driverPoints2014;
      t = +data.constructorId2014;
      l = +data.avgLap2014;
    } else if (yearSelected == 2013) {
      s = +data.driverStanding2013;
      p = +data.driverPoints2013;
      t = +data.constructorId2013;
      l = +data.avgLap2013;
    } else if (yearSelected == 2012) {
      s = +data.driverStanding2012;
      p = +data.driverPoints2012;
      t = +data.constructorId2012;
      l = +data.avgLap2012;
    } else if (yearSelected == 2011) {
      s = +data.driverStanding2011;
      p = +data.driverPoints2011;
      t = +data.constructorId2011;
      l = +data.avgLap2011;
    } else if (yearSelected == 2010) {
      s = +data.driverStanding2010;
      p = +data.driverPoints2010;
      t = +data.constructorId2010;
      l = +data.avgLap2010;
    } else if (yearSelected == 2009) {
      s = +data.driverStanding2009;
      p = +data.driverPoints2009;
      t = +data.constructorId2009;
      l = +data.avgLap2009;
    } else if (yearSelected == 2008) {
      s = +data.driverStanding2008;
      p = +data.driverPoints2008;
      t = +data.constructorId2008;
      l = +data.avgLap2008;
    } else if (yearSelected == 2007) {
      s = +data.driverStanding2007;
      p = +data.driverPoints2007;
      t = +data.constructorId2007;
      l = +data.avgLap2007;
    } else if (yearSelected == 2006) {
      s = +data.driverStanding2006;
      p = +data.driverPoints2006;
      t = +data.constructorId2006;
      l = +data.avgLap2006;
    } else if (yearSelected == 2005) {
      s = +data.driverStanding2005;
      p = +data.driverPoints2005;
      t = +data.constructorId2005;
      l = +data.avgLap2005;
    } else if (yearSelected == 2004) {
      s = +data.driverStanding2004;
      p = +data.driverPoints2004;
      t = +data.constructorId2004;
      l = +data.avgLap2004;
    }

    return {
      driverid: +data.driverId,
      drivername: data.driverName,
      bestlaptime: +data.bestLapTime,
      avglaptime: l,
      yearbestlaptime: +data.yearbestlaptime,
      driverstanding: s,
      driverpoints: p,
      laptime2022: +data.laptime2022,
      laptime2021: +data.laptime2021,
      teamid: t,
      teamstanding: +data.teamstanding,
      teamname: data.constructorName,
    };
  }
  d3.csv("data/F1data.csv", rowConverter).then(function (data) {
    var color_domain = [];
    for (var i = 0; i < data.length; i++) {
      if (!color_domain.includes(data[i]["teamid"])) {
        color_domain.push(data[i]["teamid"]);
      }
    }
    console.log(color_domain);
    colors.domain(color_domain);
    //console.log(color_domain);

    // 0 to max gdp of data
    xScale.domain([
      d3.min(
        data.map(function (d) {
          if (
            d.avglaptime > 0 &&
            d.driverstanding <= 20 &&
            d.driverstanding > 0 &&
            d.driverpoints > 0
          ) {
            return d.avglaptime;
          }
        })
      ) - 0.5,
      d3.max(
        data.map(function (d) {
          if (d.driverstanding <= 20 && d.driverstanding > 0)
            return d.avglaptime;
        })
      ),
    ]);
    // 0 to max ecc of data
    yScale.domain([1, 20]);

    yAxis.ticks(15);

    //x axis

    //Draw Scatterplot
    //Scale Changes as we Zoom
    // Call the function d3.behavior.zoom to Add zoom
    var zoom = d3
      .zoom()
      .scaleExtent([1, 40])
      .extent([
        [0, 0],
        [width, height],
      ])
      .on("zoom", zoomFunction);

    // view rectangle
    svg1
      .append("rect")
      .attr("width", width)
      .attr("height", height)
      .style("fill", "none")
      .style("pointer-events", "all");

    // Var to select shifting cirles on the scatterplot
    var shifting = svg1.append("g").classed("circles", true);

    // edited version of the js file given to us
    var newShift = shifting.selectAll("circle").data(data);

    // Adds all data to scatterplot, along with tooltip mouseover, mousemove and mouseout
    // Also added in the html and css portions to make the table on mouseover
    // Works with double click or ctrl click for zoom in or out
    // Sliding orks with mouse click and drag
    // Zoom also works with trackpad/mouse scroller

    newShift = newShift
      .enter()
      .append("circle")
      .attr("class", "dot")
      .attr("r", function (d) {
        return 10 * Math.sqrt(d.driverpoints / 5 / Math.PI);
      })
      .attr("cx", function (d) {
        return xScale(d.avglaptime);
      })
      .attr("cy", function (d) {
        return yScale(d.driverstanding);
      })
      .style("opacity", "0.75")
      .style("fill", function (d) {
        return colors(d.teamid);
      })
      .on("mouseover", function (d) {
        var getTeamId = 0;
        var getTeamName;

        getTeamId = d.teamid;

        for (var i = 0; i < constructors.length; i++) {
          if (constructors[i]["teamId"] == getTeamId) {
            getTeamName = new String(constructors[i]["teamName"]);
          }
        }
        tooltip
          .style("left", d3.event.pageX + "px")
          .style("top", d3.event.pageY - 55 + "px")
          .style("display", "inline-block")
          .html(
            "Driver: " +
              d.drivername +
              "<br/>" +
              "Team: " +
              getTeamName +
              "<br/>" +
              "Points: " +
              d.driverpoints
          );
      })

      .on("click", function (d) {
        clearLines();
        if(!lineData.includes(d.drivername)){
            lineData.push(d.drivername);
        }
        else{
            lineData.splice(lineData.indexOf(d.drivername), 1);
        }
        drawLineChart();
        console.log(lineData);
      })
      
      // Makes the tooltip follow the mouse when it is moved
      .on("mousemove", function (d) {
        tooltip
          .style("top", d3.event.pageY - 55 + "px")
          .style("left", d3.event.pageX + "px");
      })

      // Event when mouse is not longer hovering over a county
      .on("mouseout", function (d) {
        tooltip.style("display", "none");
      });

    var cName = svg1
      .selectAll(".text")
      .data(data)
      .enter()
      .append("text")
      .attr("class", "label")
      .style("text-anchor", "start")
      .attr("x", function (d) {
        return xScale(d.avglaptime);
      })
      .attr("y", function (d) {
        return yScale(d.driverstanding - 0.05);
      })
      .attr("fill", "black")
      .text(function (d) {
        if (d.driverstanding > 0) {
          return d.drivername;
        }
      });

    // Adds legend as seperate shapes with respective sized cirles and different text
    svg1
      .append("rect")
      .attr("class", "rectLegend")
      .attr("x", width - 100)
      .attr("y", height - 260)
      .attr("width", 120)
      .attr("height", 255)
      .attr("fill", "lightgrey")
      .style("opacity", 0.9)
      .style("stroke-size", "1px");

    svg1
      .append("text")
      .attr("class", "legendTitle")
      .attr("x", width - 90)
      .attr("y", height - 15)
      .style("fill", "green")
      .attr("font-size", "16px")
      .text("Annual Points");

    svg1
      .append("circle")
      .attr("class", "legendCircle")
      .attr("cx", width - 40)
      .attr("cy", height - 230)
      .attr("r", 10 * Math.sqrt(10 / 5 / Math.PI))
      .style("fill", "white");

    svg1
      .append("text")
      .attr("class", "legend")
      .attr("x", width - 70)
      .attr("y", height - 240)
      .text(" 10 points");

    svg1
      .append("circle")
      .attr("class", "legendCircle")
      .attr("cx", width - 40)
      .attr("cy", height - 190)
      .attr("r", 10 * Math.sqrt(100 / 5 / Math.PI))
      .style("fill", "white");

    svg1
      .append("text")
      .attr("class", "legend")
      .attr("x", width - 73)
      .attr("y", height - 185)
      .text(" 100 points");

    svg1
      .append("circle")
      .attr("class", "legendCircle")
      .attr("cx", width - 40)
      .attr("cy", height - 105)
      .attr("r", 10 * Math.sqrt(400 / 5 / Math.PI))
      .style("fill", "white");

    svg1
      .append("text")
      .attr("class", "legend")
      .attr("x", width - 70)
      .attr("y", height - 100)
      .text(" 400 points");

    //x axis
    var gX = svg1
      .append("g")
      .attr("class", "x_axis")
      .attr("transform", "translate(0," + height + ")")
      .call(xAxis);

    //y axis
    var gY = svg1.append("g").attr("class", "y_axis").call(yAxis);

    function zoomFunction() {
      gX.call(xAxis.scale(d3.event.transform.rescaleX(xScale)));
      gY.call(yAxis.scale(d3.event.transform.rescaleY(yScale)));
      cName.attr("transform", d3.event.transform);
      shifting.selectAll("circle").attr("transform", d3.event.transform);
    }

    //    timelinesvg.call(timelinezoom);

    // call zoom on svg members of shifting
    shifting.call(zoom);

    // Add labels on both x and y axis
    svg1
      .append("g")
      .append("text")
      .attr("class", "label")
      .attr("transform", "translate(0," + height + ")")
      .attr("y", 30)
      .attr("x", width / 2)
      .style("text-anchor", "middle")
      .attr("font-size", "12px")
      .text("Average Qualifying Lap Time (Seconds)");

    svg1
      .append("g")
      .append("text")
      .attr("class", "label")
      .attr("transform", "rotate(-90)")
      .attr("y", -50)
      .attr("x", -130)
      .attr("dy", ".71em")
      .style("text-anchor", "end")
      .attr("font-size", "12px")
      .text("Driver Standings");
  });
}
drawPlot();

var delayInMilliseconds = 10;

function upYear() {
  if (yearSelected + 1 < 2023) {
    yearSelected = yearSelected + 1;
  }
  document.getElementById("yearSelected").innerText = yearSelected;
  svg1.selectAll("*").remove();
  drawPlot();
    
  if (isDark) {
    setTimeout(function() {
        temp();
    }, delayInMilliseconds);
  }
}
function downYear() {
  if (yearSelected - 1 > 2003) {
    yearSelected = yearSelected - 1;
  }
  document.getElementById("yearSelected").innerText = yearSelected;
  svg1.selectAll("*").remove();
  drawPlot();
    
  if (isDark) {
    setTimeout(function() {
        temp();
    }, delayInMilliseconds);
  }
}
function up5Year() {
  if (yearSelected + 5 < 2023) {
    yearSelected = yearSelected + 5;
  }
  document.getElementById("yearSelected").innerText = yearSelected;
  svg1.selectAll("*").remove();
  drawPlot();
    
  if (isDark) {
    setTimeout(function() {
        temp();
    }, delayInMilliseconds);
  }
}
function down5Year() {
  if (yearSelected - 5 > 2003) {
    yearSelected = yearSelected - 5;
  }
  document.getElementById("yearSelected").innerText = yearSelected;
  svg1.selectAll("*").remove();
  drawPlot();
    
  if (isDark) {
    setTimeout(function() {
        temp();
    }, delayInMilliseconds);
  }
}

function temp() {
    document.body.style.backgroundColor = "#0D1430";
    document.getElementById("endinfo").style.backgroundColor = "#5A5A5A";
    console.log("im mad");
    svg1.selectAll("g").attr("color", "white");
    svg1.selectAll(".label").attr("fill", "white");
    svg1.selectAll(".legend").attr("fill", "white");
    svg1.selectAll(".legendTitle").style("fill", "lime");
    svg1.selectAll(".legendCircle").style("fill", "#0D1430");

    svg1.selectAll(".rectLegend").attr("fill", "#5A5A5A");
}
function toggleDarkMode() {
  var background = document.body.style.backgroundColor;
  if (!isDark) {

    document.body.style.backgroundColor = "#0D1430";
    document.getElementById("endinfo").style.backgroundColor = "#5A5A5A";

    svg1.selectAll("g").attr("color", "white");
    svg1.selectAll(".label").attr("fill", "white");
    svg1.selectAll(".legend").attr("fill", "white");
    svg1.selectAll(".legendTitle").style("fill", "lime");
    svg1.selectAll(".legendCircle").style("fill", "#0D1430");

    svg1.selectAll(".rectLegend").attr("fill", "#5A5A5A");
    isDark = true;

    //    timelinesvg.selectAll(".timeaxis").attr("color", "white");
  } else {

    document.body.style.backgroundColor = "white";
    document.getElementById("endinfo").style.backgroundColor = "lightgrey";

    svg1.selectAll("g").attr("color", "black");
    svg1.selectAll(".label").attr("fill", "black");
    svg1.selectAll(".legend").attr("fill", "black");
    svg1.selectAll(".legendTitle").style("fill", "green");
    svg1.selectAll(".legendCircle").style("fill", "white");

    svg1.selectAll(".rectLegend").attr("fill", "lightgrey");
    isDark = false;
    //    timelinesvg.selectAll(".timeaxis").attr("color", "black");
  }
}
drawLineChart();