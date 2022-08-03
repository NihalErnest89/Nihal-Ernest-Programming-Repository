from ctypes import alignment
import json
import random
import urllib.request
import requests
import re
import datetime
import nextcord



def getApexStats(name, legend="", rank=False, map=False):
    key = ''
    if name == "replicator":
        url = f"https://api.mozambiquehe.re/crafting?&auth={key}"
    elif name == "map":
        url = f"https://api.mozambiquehe.re/maprotation?auth={key}"
    else:
        url = f"https://api.mozambiquehe.re/bridge?version=5&platform=PC&player={name}&auth={key}"
    
    try:
        response = requests.get(url)
        j = response.json()
    except:
        return nextcord.Embed(title="Error", description="The API is not working", color=nextcord.Colour.red())

    # Check to see if the caller only wants the player rank

    if rank == True:
        return j["global"]["rank"]["rankName"]
        
    # Prints the error if there is one
    if "Error" in j:
        embed = nextcord.Embed(title="Error", description=j["Error"],
                              colour=nextcord.Colour.red())
        return embed

    else:
        
        # Replicator rotation
        if (name == "replicator"):
            daily = ""
            weekly = ""
            embed = nextcord.Embed(title="Replicator Rotation", description="",
                                  colour=nextcord.Colour.teal())
            for i in j[0]["bundleContent"]:
                daily += i["itemType"]["name"].replace("_", " ").title() + "\n"
            embed.add_field(name="Daily", value=daily, inline=False)
            for i in j[1]["bundleContent"]:
                weekly += i["itemType"]["name"].replace("_", " ").title() + "\n"
            embed.add_field(name="Weekly", value=weekly, inline=False)
        
        # Map rotation
        elif (name == "map"):
            maps = {"world's edge": "https://cdnb.artstation.com/p/assets/images/images/022/590/471/large/kristen-c-wong-altamirano-kaltamirano-al-16.jpg?1575998502&dl=1", "storm point": "https://cdn1.dotesports.com/wp-content/uploads/2021/10/25132108/storm-point-barometer-apex-legends-gaea.png", "olympus": "https://assets2.rockpapershotgun.com/olympus-apex-legends.jpg/BROK/resize/1920x1920%3E/format/jpg/quality/80/olympus-apex-legends.jpg", "king's canyon": "https://static1.thegamerimages.com/wordpress/wp-content/uploads/2021/01/Kings-Canyon.jpg"}
            embed = nextcord.Embed(title="Map Rotation", description="",
                                  colour=nextcord.Colour.red())
            if not j:
                embed.add_field(name="Error", value="API is down")
                return embed
            embed.add_field(name="Current Map", value = j["current"]["map"])
            embed.add_field(name="Remaining Time", value=j["current"]["remainingTimer"])
            embed.add_field(name="Next Map", value=j["next"]["map"], inline=False)
            embed.set_image(url=maps[j["current"]["map"].lower()])
            embed.set_thumbnail(url="https://content.uiowa.edu/sites/content.uiowa.edu/files/apex-legends-logo-ftr_175s2k8gp3yw7106ov4jxxiadi.png")

        
        else :
            embed = nextcord.Embed(title=str(j["global"]["name"]) + '\'s Apex Stats', description="",
                                  colour=nextcord.Colour.red())
            embed.add_field(name="Level", value=j["global"]["level"], inline=False)
            online = j["realtime"]["currentStateAsText"]
            selectedLegend = "selectedLegend"
            # kills = "Equip a kill tracker to access kills here"
            # wins = "Equip a win tracker to access wins here"

            if (len(legend) > 1): # if the legend is specified by user
                selectedLegend = legend.title()
                embed.add_field(name="Selected Legend", value=selectedLegend, inline=False)
                if (len(j["legends"]["all"][selectedLegend]) > 1):
                    for i in j["legends"]["all"][selectedLegend]["data"]:
                        embed.add_field(name=i["name"], value=str(i["value"]))
                        # if "kills" in i["name"].lower():
                        #     kills = str(i["value"])
                        #     embed.add_field(name=i["name"], value=kills, inline=False)
                        # if "wins" in i["name"].lower():
                        #     wins = str(i["value"])
                        #     embed.add_field(name=i["name"], value=wins, inline=False)
                embed.set_thumbnail(url=j["legends"]["all"][selectedLegend]["ImgAssets"]["icon"])
            else:
                embed.add_field(name="Selected Legend", value=j["realtime"][selectedLegend], inline=False)
                if (len(j["legends"]["selected"]) > 2):
                    for i in j["legends"]["selected"]["data"]:
                        embed.add_field(name=i["name"], value=str(i["value"]))
                        # if "kills" in i["name"].lower():
                        #     kills = str(i["value"])
                        #     embed.add_field(name=i["name"], value=kills, inline=False)
                        # if "wins" in i["name"]:
                        #     wins = str(i["value"])
                        #     embed.add_field(name=i["name"], value=wins, inline=False)
                embed.set_thumbnail(url=j["legends"]["selected"]["ImgAssets"]["icon"])
            embed.set_image(url=j["global"]["rank"]["rankImg"])
            embed.add_field(name="Status", value=online, inline=False)
            embed.add_field(name="Rank", value=j["global"]["rank"]["rankName"] + " " + str(j["global"]["rank"]["rankDiv"]) + ", " + str(j["global"]["rank"]["rankScore"]) + " RP", inline=False)
        return embed
    return

def getsubs(name):
    yt_id = name
    proper = False

    key = ""
    search = urllib.request.urlopen(
        "https://www.googleapis.com/youtube/v3/search?part=snippet&type=channel&maxResults=1&q=" + name + "&key=" + key).read()
    id = json.loads(search)["items"][0]["snippet"]["channelId"]
    title = json.loads(search)["items"][0]["snippet"]["title"]
    pfp = json.loads(search)["items"][0]["snippet"]["thumbnails"]["default"]["url"]
    data = urllib.request.urlopen(
        "https://www.googleapis.com/youtube/v3/channels?part=snippet,statistics&id=" + id + "&key=" + key).read()
    subs = json.loads(data)["items"][0]["statistics"]["subscriberCount"]
    views = json.loads(data)["items"][0]["statistics"]["viewCount"]
    videos = json.loads(data)["items"][0]["statistics"]["videoCount"]
    return [subs, views, videos, proper, title, pfp]

def getweather(city):
    w_key = ""

    # If input is a zip code
    if isinstance(city, int) or True in [char.isdigit() for char in city]:
        url = "http://api.openweathermap.org/data/2.5/weather?" + "zip=" + str(city) + "&appid=" + w_key
    # If input is a city name
    else:
        url = "http://api.openweathermap.org/data/2.5/weather?" + "appid=" + w_key + "&q=" + city
    response = requests.get(url)
    j = response.json()
    wind = j["wind"]["speed"]
    if j["cod"] != "404":
        m = j["main"]
        sunrise_time = datetime.datetime.fromtimestamp(j["sys"]["sunrise"])
        sunset_time = datetime.datetime.fromtimestamp(j["sys"]["sunset"])
        temp = m["temp"] - 273.15
        f_temp = temp * (9 / 5) + 32
        embed = nextcord.Embed(title=str(j["name"]) + ', ' + str(j["sys"]["country"]), description="Weather Report",
                              colour=nextcord.Colour.blue())
        embed.add_field(name="Temperature", value=str(int(temp + 0.5)) + ' °C (' +
                str(int(f_temp + 0.5)) + '°F)', inline=False)
        embed.add_field(name="Humidity", value=str(m["humidity"]) + '%', inline=False)
        embed.add_field(name="Pressure", value=str(m["pressure"]) + ' hPa', inline=False)
        embed.add_field(name="Wind Speed", value=str(round((wind * 3.6), 1)) + ' km/h (' + str(round((wind * 2.236936), 1)) + 'mph)')
        embed.add_field(name="Wind Direction", value=str(j["wind"]["deg"]) + ' degrees')
        embed.add_field(name="Description", value=str(j["weather"][0]["description"]).title(), inline=False)
        embed.add_field(name="Sunrise", value=str(sunrise_time)[11:])
        embed.add_field(name="Sunset", value=str(sunset_time)[11:])
        return embed
    else:
        return 1

def pyr(arg, arg2=4):
    content = arg
    out = ''
    for i in range(0, int(arg2)):
        out += content + '\n'
        content += ' ' + arg
    for j in range(int(arg2), 0, -1):
        blank = ''
        for k in range(0, j - 1):
            blank += arg + ' '
        out += blank + '\n'
    return out

def rolly(eq):
    arg = eq
    sum = 0
    if '+' in arg:
        a = int(arg.index('+'))
        sum = int(arg[a + 1:])
        arg = arg[:a]
        if sum > 999:
            return('Invalid modifier')
    if arg[0] == 'd' or (arg[:2] == '1d'):
        if arg[:2] == '1d':
            count = int(arg[2:])
        else:
            count = int(arg[1:])
        if count > 200 or count <= 0:
            return('Invalid number')
        rng = random.randint(1, count)
        if sum != 0:
            return(f"{str(rng)} + {str(sum)} = {str(rng + sum)}")
        else:
            return(str(rng))
    elif arg[0] != 'd' and 'd' in arg:
        out = ''
        d = int(arg.index('d'))
        mult = int(arg[:d])
        count = int(arg[d + 1:])
        if count > 200 or count <= 0 or mult > 40:
            return('Invalid input')
        total = 0
        for i in range(0, mult):
            rng = random.randint(1, count)
            out += str(rng)
            total += rng
            if i < (mult - 1):
                out += '+'
        if sum != 0:
            return(f"{out} + {str(sum)} = {str(total + sum)}")
        else:
            return(f"{out} = {str(total)}")

def math(arg):
    out = 0
    left = ""
    right = ""
    operations = ['+', '-', '\*', '/', '*']
    expr = arg.replace(" ", "")
    isDigy = True
    for x in operations:
        if x in arg:
            isDigy = False
            break

    if isDigy == True:
        if arg[-2:] == '.0':
            return int(arg)
        elif '.' in arg:
            return float(arg)
        else:
            return int(arg)
    for i in operations:
        left, operation, right = expr.rpartition(i)
        if operation in operations:
            if '*' in operation:
                out = math(left) * math(right)
            if operation == '/':
                out = math(left) / math(right)
            if operation == '+':
                out = math(left) + math(right)
            if operation == '-':
                out = math(left) - math(right)
            return(out)

def getSarcasm(input):
    output = ""
    for i in input:
        rng = random.randint(0, 1)
        if (rng == 1):
            output += i.upper()
        else:
            output += i
    return output


