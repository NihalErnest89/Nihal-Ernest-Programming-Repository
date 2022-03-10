import json
import random
import urllib.request
import requests
import datetime
import discord



def getApexStats(name):
    key = 'wxPOs7q4qLcEerzZciTa';
    if (name == "replicator"):
        url = "https://api.mozambiquehe.re/crafting?&auth=" + key
    else:
        url = "https://api.mozambiquehe.re/bridge?version=5&platform=PC&player=" + name + "&auth=" + key
    response = requests.get(url)
    j = response.json()
    if "Error" in j:
        embed = discord.Embed(title="Error", description=j["Error"],
                              colour=discord.Colour.red())
        return embed
    else:
        if (name == "replicator"):
            daily = ""
            weekly = ""
            embed = discord.Embed(title="Replicator Rotation", description="",
                                  colour=discord.Colour.orange())
            for i in j[0]["bundleContent"]:
                daily += i["itemType"]["name"].replace("_", " ").title() + "\n"
            embed.add_field(name="Daily", value=daily, inline=False)
            for i in j[1]["bundleContent"]:
                weekly += i["itemType"]["name"].replace("_", " ").title() + "\n"
            embed.add_field(name="Weekly", value=weekly, inline=False)
        else :
            embed = discord.Embed(title=str(j["global"]["name"]) + '\'s Apex Stats', description="",
                                  colour=discord.Colour.red())
            embed.add_field(name="Level", value=j["global"]["level"], inline=False)
            online = "Offline"
            if (j["realtime"]["isOnline"] != 0):
                online = "Online"
            embed.add_field(name="Selected Legend", value=j["realtime"]["selectedLegend"], inline=False)
            kills = "Whoever made the API is bald and thinks u have no kills as this legend"
            if (len(j["legends"]["selected"]["data"]) > 0):
                kills = str(j["legends"]["selected"]["data"][0]["value"])
            embed.add_field(name=j["realtime"]["selectedLegend"] + " Kills", value=kills, inline=False)
            embed.add_field(name="Status", value=online, inline=False)
            embed.add_field(name="Rank", value=j["global"]["rank"]["rankName"] + " " + str(j["global"]["rank"]["rankDiv"]) + ", " + str(j["global"]["rank"]["rankScore"]) + " RP", inline=False)
        return embed
    return

def getsubs(name):
    yt_id = name
    proper = False

    key = "AIzaSyB0KD4P9vSkAb_LLeTyQzaZTaX5TSs76WM"
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
    w_key = "04b20e255eedbda39754fcd4e244a91f"
    if isinstance(city, int) or True in [char.isdigit() for char in city]:
        url = "http://api.openweathermap.org/data/2.5/weather?" + "zip=" + str(city) + "&appid=" + w_key
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
        embed = discord.Embed(title=str(j["name"]) + ', ' + str(j["sys"]["country"]), description="Weather Report",
                              colour=discord.Colour.blue())
        embed.add_field(name="Temperature", value=str(int(temp + 0.5)) + ' °C (' +
                str(int(f_temp + 0.5)) + '°F)', inline=False)
        embed.add_field(name="Humidity", value=str(m["humidity"]) + '%', inline=False)
        embed.add_field(name="Pressure", value=str(m["pressure"]) + ' hPa', inline=False)
        embed.add_field(name="Wind Speed", value=str(round((wind * 3.6), 1)) + ' km/h (' + str(round((wind * 2.236936), 1)) + 'mph)', inline=False)
        embed.add_field(name="Wind Direction", value=str(j["wind"]["deg"]) + ' degrees', inline=False)
        embed.add_field(name="Description", value=str(j["weather"][0]["description"]).title(), inline=False)
        embed.add_field(name="Sunrise", value=str(sunrise_time)[11:], inline=False)
        embed.add_field(name="Sunset", value=str(sunset_time)[11:], inline=False)
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
            return(str(rng) + ' + ' + str(sum) + ' = ' + str(rng + sum))
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
            return(out + ' + ' + str(sum) + ' = ' + str(total + sum))
        else:
            return(out + ' = ' + str(total))

def math(arg):
    out = 0
    operations = ['+', '-', '*', '/']
    expr = arg.replace(" ", "")
    if arg.isdigit() == True:
        if '.0' in arg:
            return float(arg)
        else:
            return int(arg)
    for i in operations:
        left, operation, right = expr.rpartition(i)
        if operation in operations:
            if operation == '*':
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
    for i in range(0, len(input)):
        rng = random.randint(0, 1)
        if (rng == 1):
            output += input[i].upper()
        else:
            output += input[i]
    return output


