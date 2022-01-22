# Coded by Nihal Ernest
# Version 1.15.1
# Patches: Patched reminder system. Added a day based reminder system.
# 20 August 2021

import json
import random
import time
import urllib.request

import discord
import requests
import os
from datetime import date
import calendar
from discord import FFmpegPCMAudio
from discord.ext import commands
from skingrabber import skingrabber
from responses import *
from utilities import *





ai = ['Really?', 'Are you sure about that?', 'Whatever you say...', 'Hm...']
sus = ["https://tenor.com/view/vanish-gif-18078917", "https://tenor.com/view/sus-suspect-among-us-gif-18663592"]
text_ultron = ['https://tenor.com/view/ultron-glad-you-asked-gif-20665380', '```                .........     ...                       ..            \n         ..........  ....                .    .                       \n         ....  ........   &@&,**,,,,,,,,,,,,,*                        \n               ....  @@/##/*****.****,**,***,*/(%/*                   \n                  #@*****/%(*****************(&*/***.*                \n                .@(*.*****(%%(///*******//(/#%(******,.               \n     ............@*****,***,*///%(/***//&((//,*********, ..           \n   .............@%*******////**,///**/*(/***///********. .          ..\n.      ....@....@*****..,***,////,******///,**,...****,...............\n  ........@,...,@***..,*&@@&@@,,./,***/..,&&&%@&*,..*(,.,...@.........\n..,,......&,...@&****/..,,*,,(////****////(,**,,. /**( .*...,(........\n..........@*&*.@/*******//////**,******,**/////******(..,... /........\n..........%(,**.**,,*******************************(#(...,**@........,\n........... @,,*.******.,***********************.*,**,.*,,*%.,,,,.....\n.............,%,. ... .***,******************,***..  . *.*. ..........\n..............,..,,. .. .***.*******************     .*., ............\n,(&@@&&&@&&&@@@,,*.*,,.. ./**,***,********.***. .. .*....,............\n,#@@@@@@@@@@@@@,&,,     .  ,,,...,,,,,,.  ,,*.. .   ....,,.,*****,,***\n..,,,%(/#%###%&./,,, .      ,,,,*,,,,,,*,,,,      ......,,..,,,,,,,,,,\n.,,**,**,***,.#,,&@,*, .   .,,,,,,,,,,,,,,,.    . /... ,(...,,,,,,,,,,\n .......,,  *//,,.,,@,,,,*,,,,,,,,,,,,,,,,,.... ,/,.,,.,*.. .,,,,,,,,,\n           #.&*,,,,,, #(,,,,,,,,,.,,,,.,,,,,,,,,(. ,,,,,,.. .,,,,,,,,,\n  *. .    .#.@,,,,,,,    @,,,,,,,,,,,,,,,,,,,,.   .,,,,&,.. .  ,,**...\n  %, ...  #..@,,,, ,,,      ,.,,,,,,,,,.,,.,      ,,,,,@,.. .         \n         *&,,@,,,,,,,,.                          ,,,,,*#,   .,      .,```']



with open("lists.txt", "r") as lists:
    lines = lists.readlines()

as_list=[]

for l in lines:
    new_list = l.split("* ")
    new_list[-1] = new_list[-1].replace("\n", "")
    as_list.append(new_list)

activate = as_list[0]
shoot = as_list[11]
queues = {}


alive = []
dead = []
fight = []
bot = commands.Bot(command_prefix='$')
duel_bool = False
time_passed = False
death_messages = [' melted into the ground', ' clicked on a link for free bobux', ' got sent to gulag',
                  ' disappointed Stalin', ' was stepped on by big chungus', ' fell off the void']
day_count = 0

@bot.command(help='Set a reminder for a certain day. Work in progress')
async def remind(ctx, day, *, event):
    reminder_list = get_reminders()
    if day in reminder_list.keys():
        with open("reminders.txt", "w") as reminds_out:
            r_out = ''
            for d in reminder_list.keys():
                if d in day:
                    reminder_list[d] = reminder_list[d].replace("\n", "")
                    if len(reminder_list[d]) >= 1:
                        r_out += d.lower() + "* " + reminder_list[d] + "~ " + event + "\n"
                    else:
                        r_out += d.lower() + "* " + event + "\n"
                    send = "Set a reminder for: **" + event + "** on " + d
                    await ctx.send(send)
                else:
                    if len(reminder_list[d]) >= 1:
                        r_out += d.lower() + "* " + reminder_list[d]
                    else:
                        r_out += d.lower() + "\n"
            reminds_out.write(r_out)

@bot.command(help='Write your own text-based commands')
async def add(ctx, trigger, *, response):
    command_lists = get_commands()
    if trigger in command_lists.keys():
        await ctx.send("Entry already exists")
        return
    if "<@!" in trigger or "<@!" in response:
        await ctx.send("Nice try")
        return
    with open("commands.txt", "a") as commands_out:
        commands_out.write(str(trigger.lower() + "* " + response + "\n"))


@bot.command(help='Returns list of server that I\'m in')
@commands.has_permissions(administrator=True)
async def servers(ctx, arg = 'Terminal'):
    if ctx.author.id == 548034543014117377:
        active_servers = bot.guilds
        out = ''
        for g in active_servers:
            out += g.name + '\n'
        if arg == 'chat':
            await ctx.send('Servers with Ultron:')
            await ctx.send(out)
        else:
            print('Servers with Ultron:')
            print(out)
    else:
        await ctx.send('Access Denied')

@bot.command(help='Drops a nuke')
async def nuke(ctx):
    await ctx.send(':airplane_small:')
    for i in range(0, 3):
        await ctx.send(':bomb:')
    await ctx.channel.purge(limit=3)
    await ctx.send('https://tenor.com/view/explosion-mushroom-cloud-atomic-bomb-bomb-boom-gif-4464831')

@bot.command(help='Sends a direct message', aliases=['directmessage', 'slideintodms', 'message'])
@commands.has_permissions(administrator=True)
async def dm(ctx, member: discord.Member, message="Ya like jazz?"):
    await ctx.channel.purge(limit=1)
    #await member.send(message + "||" + "\nsent by user: **" + str(ctx.author) + "** from server:** " + ctx.guild.name + "**||")
    await member.send(
        message + "||" + "\nsent from server:** " + ctx.guild.name + "**||")

@bot.command(help='moves all connected users to another voice channel')
#@commands.has_permissions(administrator=True)
async def moveall(ctx, channel : discord.VoiceChannel):
    #await ctx.send(ctx.author.voice.channel.members)
    for i in ctx.author.voice.channel.members:
        await i.move_to(channel)

@bot.command(help='moves a connected user to another voice channel')
#@commands.has_permissions(administrator=True)
async def move(ctx, member : discord.Member, channel : discord.VoiceChannel):
    await member.move_to(channel)

@bot.command(help='warns a member')
@commands.has_permissions(administrator=True)
async def warn(ctx, member: discord.Member, *, reasons = "Triggering Ultron Intelligence"):
    embed = discord.Embed(title="Warning", description=f"{member.mention} has been warned ",
                          colour=discord.Colour.orange())
    embed.add_field(name="reason:", value=reasons, inline=False)
    await ctx.send(embed=embed)

@bot.command(help='the last resort')
async def execute(ctx, type, member: discord.Member, reasons = "Triggering Ultron Intelligence"):
    if ctx.author.id == 548034543014117377 and str(member) != "IronManForever#3275" and str(member) != "Ultron#9855":
        if type == 'meme':
            await ctx.send("Activating instant kill...")
            await member.edit(nick='Biggest Imbecile')
            guild = ctx.guild
            mutedRole = discord.utils.get(guild.roles, name="Muted")
            embed = discord.Embed(title="Silenced", description=f"{member.mention} was muted ",
                                  colour=discord.Colour.red())
            embed.add_field(name="reason:", value=reasons, inline=False)
            await ctx.send(embed=embed)
            await member.add_roles(mutedRole, reason=reasons)
            await member.send(f" you have been muted from: {guild.name}! reason: {reasons}")
        elif type == 'order66':
            await ctx.send('https://c.tenor.com/cYdQ9Cx19MQAAAAC/order66-clone-protocol66.gif')
            await ctx.send(':airplane_small:')
            for i in range(0, 3):
                await ctx.send(':bomb:')
            await ctx.channel.purge(limit=4)
            await ctx.send('https://tenor.com/view/explosion-mushroom-cloud-atomic-bomb-bomb-boom-gif-4464831')
            embed = discord.Embed(title="Kicked", description=f"{member.mention} was kicked ",
                                  colour=discord.Colour.red)
            embed.add_field(name="reason:", value=reasons, inline=False)
            await ctx.send(embed=embed)
            await member.kick(reason=reasons)
    else:
        if str(member) == "IronManForever#3275" or str(member) == "Ultron#9855":
            await ctx.send('Hah you thought you could use his own command against him' + ctx.author.mention)
        await ctx.author.edit(nick='A bot outsmarted me')
        guild = ctx.guild
        mutedRole = discord.utils.get(guild.roles, name="Muted")
        embed = discord.Embed(title="Silenced", description=f"{member.mention} was muted ",
                                colour=discord.Colour.light_gray())
        embed.add_field(name="reason:", value=reasons, inline=False)
        await ctx.send(embed=embed)
        await ctx.author.add_roles(mutedRole, reason='Being outsmarted by a bot')
        await ctx.author.send(
            f" you have been muted from: {guild.name}! reason: Thinking that I didn't have contingencies")

@bot.command(help='you want forgiveness? get religion', aliases = ['bringfromgulag', 'bringbackfromgulag'])
async def returnfromgulag(ctx, member:discord.Member):
    mutedRole = discord.utils.get(ctx.guild.roles, name="Muted")
    embed = discord.Embed(title="Unmute", description=f" {member.mention} escaped from gulag", colour=discord.Colour.green())

    await ctx.send(embed=embed)
    await member.remove_roles(mutedRole)

    newnick = str(member)[:str(member).index('#')]
    await member.edit(nick=newnick)
    await member.send(f" you have unmuted from: - {ctx.guild.name}")


@bot.command(aliases=['yeet'])
async def sendtogulag(ctx, member: discord.Member):
    if str(member) == "IronManForever#3275" or str(member) == "Ultron#9855":
        await ctx.send('Hah you thought you could use his own command against him' + ctx.author.mention)
        await ctx.author.edit(nick='stoopid')
        await ctx.author.edit(voice_channel=bot.get_channel(694215227218067477))
    else:
        if member.voice:
            await ctx.send("Successfully sent {} to gulag".format(member.mention))
            await member.edit(voice_channel=bot.get_channel(694215227218067477))
        else:
            await ctx.send('You need to be a voice channel to use that')

@bot.command(aliases=['math', 'm'])
async def mathematics(ctx, *, expr):
    await ctx.send(('**' + str(math(expr)) + '**'))


@bot.command(help='Joins your current voice chat and plays song specified by input', aliases=['join'])
async def play(ctx, *, arg='strings'):
    if arg == 'list':
        out = '```**MUSIC LIST**\n'
        for x in os.listdir('music/'):
            out += x + '\n'
        out = out.replace(".mp3", "")
        out += '```'
        await ctx.send(out)

    if not os.path.isfile('music/' + arg + '.mp3'):
        await ctx.send('Audio not found, try again with a valid name')
        return

    if ctx.author.voice:
        channel = ctx.author.voice.channel
        if not ctx.voice_client:
            voice = await channel.connect()
        voice = ctx.guild.voice_client
        path = 'music/' + arg + '.mp3'
        source = FFmpegPCMAudio(path)
        guild_id = ctx.message.guild.id
        song = discord.utils.get(bot.voice_clients, guild=ctx.guild)
        name = arg.title()

        if arg == 'strings':
            rng = random.randint(0, 5)
            name = ultron_quotes[rng]

        if song.is_playing():
            if guild_id in queues:
                queues[guild_id].append(source)
            else:
                queues[guild_id] = [source]
            embed = discord.Embed(title="Queued", description=name, colour=discord.Colour.red())
        else:
            player = voice.play(source, after=lambda x=None: check_queue(ctx, ctx.message.guild.id))
            embed = discord.Embed(title="Playing", description=name, colour=discord.Colour.red())

        embed.set_author(name=ctx.author.display_name, icon_url=ctx.author.avatar_url)

        if os.path.isfile('music/thumbnails/' + arg + '.jpg'):
            thumbnail_path = 'music/thumbnails/' + arg + '.jpg'
            file = discord.File(thumbnail_path, filename="image.png")
            embed.set_image(url="attachment://image.png")
            await ctx.send(file=file, embed=embed)
        else:
            await ctx.send(embed=embed)
        if arg == 'crab-rave':
            await ctx.send(':crab: :crab: :crab: :crab: ')
    else:
        await ctx.send('You need to be a voice channel to summon me')


def check_queue(ctx, id):
    if id in queues and queues[id] != []:
        voice = ctx.guild.voice_client
        source = queues[id].pop(0)
        player = voice.play(source)

@bot.command(help='Leaves voice chat', aliases=['disconnect', 'fuckoff', 'begone', 'gotogulag'])
async def leave(ctx):
    channel = ctx.voice_client.channel
    if (ctx.voice_client):
        if len(queues) != 0:
            queues.clear()
        await ctx.send("Leaving voice channel: **" + str(channel) + "**")
        await ctx.voice_client.disconnect()
    else:
        await ctx.send("I'm not in a voice channel")


@bot.command(help='Pauses audio')
async def pause(ctx):
    voice = discord.utils.get(bot.voice_clients, guild=ctx.guild)
    if voice.is_playing():
        voice.pause()
    else:
        await ctx.send("There's no audio to pause")


@bot.command(help='Resumes audio')
async def resume(ctx):
    voice = discord.utils.get(bot.voice_clients, guild=ctx.guild)
    if voice.is_paused():
        voice.resume()
    else:
        await ctx.send("No audio is paused")


@bot.command(help='Stops any audio currently playing')
async def stop(ctx):
    voice = discord.utils.get(bot.voice_clients, guild=ctx.guild)
    voice.stop()



@bot.command(help='Launches a round of yeet squad hunger games! Enter contestants as parameters', aliases=['next'])
async def yeetgames(ctx, *, tributes=''):
    global day_count
    if len(tributes) > 0:
        alive.clear()
        for i in list(tributes.split(", ")):
            alive.append(i)
        out = 'Tributes:\n'
        for j in alive:
            out += j + '\n'
        await ctx.send(out[:-1])
        day_count = 1
        return
    if len(alive) <= 1:
        await ctx.send(alive[0] + ' wins!')
        for i in dead:
            alive.append(i)
            dead.remove(i)
        return

    await ctx.send('Day: ' + str(day_count))
    mega_out = ''
    alive_copy = alive.copy()
    for fighter in alive_copy:
        rng = random.randint(1, 10)
        if rng <= 3:
            #print('alive = ' + str(alive))
            rng2 = random.randint(0, len(death_messages) - 1)
            out = str(fighter + death_messages[rng2] + '\n')
            dead.append(fighter)
            alive.remove(fighter)
            mega_out += out
        elif 3 < rng < 7:
            fight.append(fighter)
            #mega_out += (fighter + ' is engaging in battle!\n')
        else:
            out = fighter + ' lives\n'
            mega_out += out
        #print(fighter + str(rng))
        if len(alive) <= 1:
            await ctx.send(mega_out)
            return

    if len(fight) >= 1:
        mega_out += battle()
    await ctx.send(mega_out)
    day_count += 1


def battle():
    kill_messages = [' was yeeted into the void by ', ' was wiped from this plane of existence by ', ' was sent to gulag by ',
                     ' lost in a duel to ', ' was slapped in the face by yeetbeans by ', ' was turned into a meme by ']
    out = ''
    while len(fight) > 0:
        #print('fight = ' + str(fight))
        if len(fight) == 1:
            out += str(fight[0] + ' was too chicken to fight' + '\n')
            fight.pop(0)
            break
        rng = random.randint(1, 2)
        if rng == 1:
            out += str(fight[0] + kill_messages[random.randint(0, len(kill_messages) - 1)] + fight[1] + '\n')
            alive.remove(fight[0])
            dead.append(fight.pop(0))
            fight.pop(0)
        else:
            out += str(fight[1] + kill_messages[random.randint(0, len(kill_messages) - 1)] + fight[0] + '\n')
            alive.remove(fight[1])
            dead.append(fight.pop(1))
            fight.pop(0)
    return out

@bot.command()
async def hypixel(ctx, gamemode='bedwars', player='Ironman02'):
    h_key = '117108a5-1d87-403d-bb25-53e8d2c38f43'
    url = "http://api.hypixel.net/player?name=" + player + "&key=" + h_key
    response = requests.get(url)
    j = response.json()
    if j["success"] == False:
        await ctx.send(j["cause"])
        return
    if j["player"] != None:
        await ctx.send("Player: " + j["player"]["displayname"])
        out = 'Improper input, try again'
        if gamemode.lower() == 'bedwars':
            b = j["player"]["stats"][gamemode.title()]
            if 'wins_bedwars' not in b:
                b["wins_bedwars"] = 0
            if 'beds_broken_bedwars' not in b:
                b['beds_broken_bedwars'] = 0
            out = "Kills: " + str(b["kills_bedwars"]) + "\nDeaths: " + str(b["deaths_bedwars"]) \
                  + "\nFinal Kills: " + str(b["final_kills_bedwars"]) + "\nFinal Deaths: " + str(b["final_deaths_bedwars"]) \
                  + "\nBeds destroyed: " + str(b["beds_broken_bedwars"]) \
                  + "\nBeds lost: " + str(b["beds_lost_bedwars"]) + "\nWins: " + str(b["wins_bedwars"]) \
                  + "\nLosses: " + str(b["losses_bedwars"])
        elif gamemode.lower() == 'skywars':
            b = j["player"]["stats"]['SkyWars']
            temp = 0
            for i in b:
                if 'most_kills' in i:
                    if b[i] > temp:
                        temp = b[i]
            if 'wins' not in b:
                b["wins"] = 0
            if 'heads' not in b:
                b['heads'] = 0
            out = "Kills: " + str(b["kills"]) + "\nDeaths: " + str(b["deaths"]) + "\nWins: " + str(b["wins"]) \
                  + "\nLosses: " + str(b["losses"]) + "\nHighest kills in a game: " + str(temp) + "\nCoins: " \
                  + str(b["coins"]) + "\nHeads: " + str(b["heads"])
        await ctx.send(out)
    else:
        await ctx.send("Player not found, try again")


@bot.command(help='Provides temperature and weather description of input city', aliases=['temperature', 'temp'])
async def weather(ctx, *, city):
    result = getweather(city)
    if result == 1:
        await ctx.send("City not found, try again")
    else:
        await ctx.send(embed=getweather(city))


@bot.command(help='Provides subscriber count based on input id or yeet squad members\' channel names',
             aliases=['subs', 'views', 'viewcount', 'youtube', 'yt'])
async def subcount(ctx, *, name):
    name = name.replace(" ", "")
    results = getsubs(name)
    embed = discord.Embed(title=results[4], description="YouTube statistics",
                            colour=discord.Colour.red())
    embed.add_field(name="Subscribers", value="{:,}".format(int(results[0])), inline=False)
    embed.add_field(name="Total Views", value="{:,}".format(int(results[1])), inline=False)
    embed.add_field(name="Total Videos", value="{:,}".format(int(results[2])), inline=False)
    embed.set_image(url=results[5])
    await ctx.send(embed=embed)


@bot.command(help='Makes a pyramid out of input text with input amount of levels')
async def pyramid(ctx, arg='yeet', arg2=5):
    if int(arg2) > 15 or len(arg) > 30:
        await ctx.send('Don\'t try and break me...')
        return
    await ctx.send(pyr(arg, arg2))

@bot.command(help='Initiates an old west-style duel', aliases=['d', 'draw', ''])
async def duel(ctx, arg='new'):
    global duel_bool
    if arg == 'new' and duel_bool == False:
        await ctx.send('Shoot your gun when I command')
        await ctx.send(':gun:Prepare yourself:gun:')
        await ctx.send('** **\n** **')
        rng = random.randint(3, 15)
        time.sleep(rng)
        await ctx.send('**DRAW**')
        await ctx.send('https://tenor.com/view/clint-eastwood-draw-gif-19805060')
        duel_bool = True
    else:
        return


@bot.command(help='DnD roll', aliases=['r'])
async def roll(ctx, die='d20'):
    a = 1
    out = ''
    if '*' in die:
        a = int(die.index('*'))
        num = int(die[:a])
        new = die[a+1:]
        if num > 10:
            await ctx.send('Invalid multiplier')
        for i in range(0, num):
            out += rolly(new) + '\n'
        await ctx.send(out)
    else:
        await ctx.send(rolly(die))


@bot.command(help='Rolls a magic 8 ball in response to your question', aliases=['8ball', '69ball', '420ball'])
async def eight_ball(ctx, arg):
    rng = random.randint(0, 19)
    await ctx.send(ball[rng])


@bot.command(help='Time to party!')
async def partymode(ctx, *, arg="YEET"):
    if len(arg) > 50:
        await ctx.send('Don\'t try and break me...')
        return
    await ctx.send('https://media.giphy.com/media/5jT0jaNDsM6Ik7X9yq/giphy.gif')
    if len(arg) > 20:
        await ctx.send(pyr(arg, 5))
    else:
        await ctx.send(pyr(arg, 10))


@bot.command(help='Returns the minecraft skin of input player', aliases=['mc', 'minecraft'])
async def skin(ctx, arg):
    sg = skingrabber()
    await ctx.send(arg + '\'s skin:')
    await ctx.send(sg.get_skin_rendered(arg) + '?overlay')


@bot.command(help='spooky', aliases=['ping'])
async def ghost(ctx, name="codeBat", *,  text="** **"):
    if name == "@everyone":
        if str(ctx.author) != "IronManForever#3275":
            await ctx.send('Nice try...')
            return
    elif name == "codeBat":
        await ctx.send(text)
        return
    await ctx.send(
        text + "||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||||​||"
        + "<" + name + ">")

#add functionality to clear an entire channel
@bot.command(aliases=['chronal-shift', 'clear'])
@commands.has_permissions(manage_messages=True)
async def purge(ctx, number=1, *, protocol="regular"):
    if protocol == "seek and destroy":
        await ctx.channel.purge()
        return
    if number <= 100:
        await ctx.channel.purge(limit=number + 1)
    else:
        await ctx.send("What were you expecting?")

@bot.event
async def on_message(message):
    global duel_bool
    global time_passed
    msg = message.content.lower()
    isDM = str(message.channel.type) == 'private'
    #if message.author == bot.user or message.author.bot:
    if str(message.author) == "Ultron#9855":
        return

    if (respond(msg, isDM)) != "N/A":
        await message.channel.send(respond(msg, isDM))

    current_date = date.today()
    remind_list = get_reminders()
    counter = 0
    for day in remind_list.keys():
        if (calendar.day_name[current_date.weekday()]).lower() in day and time_passed is False and len(remind_list[day]) >= 1:
            if "~" in remind_list[day]:
                day_list = remind_list[day].split("~ ")
                for r in day_list:
                    if "863276579739402280" in r or "dnd" in r.lower():
                        for channel in message.guild.channels:
                            if channel.name == "dnd":
                                wanted_channel_id = channel.id
                        await bot.get_channel(wanted_channel_id).send(r)
                    else:
                        await message.channel.send(r)
            else:
                if "863276579739402280" in remind_list[day] or "dnd" in remind_list[day].lower():
                    for channel in message.guild.channels:
                        if channel.name == "dnd":
                            wanted_channel_id = channel.id
                    await bot.get_channel(wanted_channel_id).send(remind_list[day])
                else:
                    await message.channel.send(remind_list[day])

            with open("date.txt", "w") as date_out:
                date_out.write(calendar.day_name[current_date.weekday()])
            time_passed = True

    today = get_day()[0]
    if today != calendar.day_name[current_date.weekday()]:
        time_passed = False


    if message.content.startswith("$ghost") or message.content.startswith("$ping"):
        await message.channel.purge(limit=1)

    elif ("jackass" in msg or "big shot" in msg or "spam" in msg or "pirate" in msg) and "$dm" not in msg:
        if message.author.voice:
            channel = message.author.voice.channel
            voice = await channel.connect()
            path = 'music/' + 'jackassery' + '.mp3'
            source = FFmpegPCMAudio(path)
            player = voice.play(source)

            embed = discord.Embed(title="Playing", description='Jackassery!', colour=discord.Colour.red())
            thumbnail_path = 'music/thumbnails/' + 'jackassery' + '.jpg'
            file = discord.File(thumbnail_path, filename="image.png")
            embed.set_image(url="attachment://image.png")
            await message.channel.send(file=file, embed=embed)
        return

    elif msg in shoot and duel_bool == True:
        duel_bool = False
        winner = str(message.author.mention)
        await message.channel.send(winner + ' is the fastest gun in the west!')

    elif 'how many subs am i' in msg or 'what\'s my sub' in msg or 'how\'s my sub' in msg or 'how many views am i' in msg or 'youtube report' in msg or 'what\'s my views' in msg:
        if str(message.author) == "IronManForever#3275":
            temp = getsubs("theinvincibleclasher")
            embed = discord.Embed(title="The Invincible Clasher", description="YouTube statistics", colour=discord.Colour.red())
            embed.add_field(name="Subscribers", value=temp[0], inline=False)
            embed.add_field(name="Total Views", value=temp[1], inline=False)
            embed.add_field(name="Total Videos", value=temp[2], inline=False)
            await message.channel.send(embed=embed)
        else:
            await message.channel.send("no idea lol, use $subcount")

    elif "what's the weather" in msg or "how's the weather" in msg or "weather report" in msg:
        if str(message.author) == "IronManForever#3275":
            city = 95064
            await message.channel.send(embed=getweather(city))
        else:
            await message.channel.send('no idea lol, use $weather')



    for y in activate:
        if (y in msg.split() or (y in msg and ' ' in y)) and ('ultron' in msg or isDM):
            rng = random.randint(0, len(greetings) - 1)
            await message.channel.send(greetings[rng] + message.author.mention)



    await bot.process_commands(message)


@bot.event
async def on_raw_reaction_add(payload):
    message_id = payload.message_id
    if message_id == 886143621030629406:
        guild_id = payload.guild_id
        guild = discord.utils.find(lambda g : g.id == guild_id, bot.guilds)

        if payload.emoji.name == 'apex':
            role = discord.utils.get(guild.roles, name='Apex')
        elif payload.emoji.name == '🔫':
            role = discord.utils.get(guild.roles, name='Destiny')
        elif payload.emoji.name == "⛏️":
            role = discord.utils.get(guild.roles, name='Minecraft')
        elif payload.emoji.name == '🛩️':
            role = discord.utils.get(guild.roles, name='War Thunder')
        elif payload.emoji.name == '⚔️':
            role = discord.utils.get(guild.roles, name='DnD')

        if role is not None:
            member = await(await bot.fetch_guild(payload.guild_id)).fetch_member(payload.user_id)
            if member is not None:
                await member.add_roles(role)
            else:
                print('member not found')
        else:
            print('role not found')

@bot.event
async def on_raw_reaction_remove(payload):
    message_id = payload.message_id
    if message_id == 886143621030629406:
        guild_id = payload.guild_id
        guild = discord.utils.find(lambda g: g.id == guild_id, bot.guilds)

        if payload.emoji.name == 'apex':
            role = discord.utils.get(guild.roles, name='Apex')
        elif payload.emoji.name == '🔫':
            role = discord.utils.get(guild.roles, name='Destiny')
        elif payload.emoji.name == "⛏️":
            role = discord.utils.get(guild.roles, name='Minecraft')
        elif payload.emoji.name == '🛩️':
            role = discord.utils.get(guild.roles, name='War Thunder')
        elif payload.emoji.name == '⚔️':
            role = discord.utils.get(guild.roles, name='DnD')

        if role is not None:
            member = await(await bot.fetch_guild(payload.guild_id)).fetch_member(payload.user_id)
            if member is not None:
                await member.remove_roles(role)
            else:
                print('member not found')
        else:
            print('role not found')



@bot.event
async def on_ready():
    print(bot.user.name, 'Intelligence Booting...')
    await bot.change_presence(activity=discord.Game(name=" with strings"))
    type_msg = input("Manual Control?: ")
    rng = random.randint(0, len(ultron_quotes) - 1)
    await bot.get_channel(863235613465509888).send(ultron_quotes[rng])
    exit = False
    if type_msg.lower() == "yes":
        while (exit == False):
            channel = input("Enter channel ID: ")
            if channel == "terminate":
                exit = True
                break
            change_channel = False
            while change_channel == False:
                msg = input("Enter message: ")
                if msg == "cc":
                    change_channel == True
                    break
                await bot.get_channel(int(channel)).send(str(msg))
    print('                       .............       ....                                 ..                  \n                 .................    ..........        ..                                          \n            ..................            .%@@@@&#/**,,***/*     ..                                 \n             ......    ............ (@@%*,,*,**,,,,,,,,***,,,*****.\n                      ......   @@@#&//*******,,*****,,***,,*******//%//.                            \n                       ... .@@#***/*%*/********,********,********/(&//****,                         \n                         /@&,******//%#(/*******.*****,********//&%//*****.,(                       \n                      ..#@*********,/((&#/////************/((//#&*(*******,*/,.                     \n       .......  ........@&****,,,*****///(((##(/*******/(/&(//(((/,***,,,,**//.                     \n      .................#@********,******,**//(%/(/***/(*&/(//*,******,******//.,..                  \n..................@@*********,*/////****/////***//*(/,/***/////********//., .   .           .. \n..  .......&......@%*******......../////*,********/*////(........,*****(/......................\n.         .....@*.....%#***** ...,*********,////*****/**//,.,******/*....***#*..,...................\n  ............@**.....@@****...,,*@@&@@%&&%,,,,*/*,***/***,*#%&&@&@@#,....,,(/..*.....@,............\n...,,,.,...,..@* .....@%*****/,..,,******((/////******/////((,*****,,...****(,..*.....%% ...........\n..............@*  @..#@/*,*******////////////**.******/*/////////////******((...*.....*&............\n..............@*,&**.&@,*.********//////*//**,**************///////*******,(#..., ..,.#* ...........\n...............@,,****@,**.,*********,***************************,*******(((/.... *** &...........,,\n............... @**,***.,*******.*********************************,***.,****...,,,,*.#...,,,,,,,,...\n.................,@**,*,..,*****,**,,******************************,*,***,... **,,,#,..,............\n...................,@,,.........******.*************************,*****.. .... ,*.*,. ...............\n................... *.*,,,.      .,****,*********************,*,****,.  .   ..*,..  ................\n....,*...../.......*@ ,&.,,,**    . **.**,*****************,,.,,,**   .   ,***,   ..................\n/&@@@@@@@@@@@@@@@@@@@,,%,,. *,,... ,(,**,,.**,,,,***,*,*****,****,(.  . .*. . . ,,..,........,,,,,..\n*#@@@@@@@@@@@@@@@@@#@,%%,*, .    .. . .,,,,...        .  ...,,,*.(...   , . ...,,, ..********,,*****\n../((&#&&@&%%%##&@@*@,@/,,,,.          .,.,,,,,,,,,,,,,,,,.,,., .        . ....,,,...,,,,,,,,,,,,,,,\n....,..*,*(((((((((#%,%@*,,,, ..       .*,,,,,,,,,,,,,.,,,.,,..        ..,.....,., ..,,,,,,,,,,,,,,,\n.,,***,,*********.#&#,,,*@%,,,, ..     ,,,,,,,,,,,,,,,.,,,,.,.       . .(.... .,(,....,,,,,,,,,,,,,,\n......,....,*/*,,#,@/,,,,,,@@,,,,.,,,,,,,,,,,,,,,,,,,,.,,,,,,.... ...,*(....,.,,/,.. .,,,,,,,,,,,,,,\n...,,,,,,..... /#,@/,,,,,,,,.@/,,,..,,,,,,,,,,,,,,,,,,,,,,,,,,..  ,,,(, ,,,,,,(,, . .,,,,,,,,,,,,,,\n                #..&*,,,,.,,,,  %&,,,,,,,,,,,,,.,,,,,,,,,,,,,,,,,,,,*(  .,,,,,,&,, . ..,,,,,,,,,,,,,\n..     .##..@*,,,,.,,,,     @,,,,,,,,,,.,,,,,,,,,,,,,,,,,,,,     ,,,,.,,&,...  .  .,***,,,,,,\n,%( ... .   *#...@*,,,,,,,,,,      **,,,,,,,,,,.,,..,,,,,,,,,*.      ,,,,,,,/%,...  .             \n*%/   . .   %( . &,,,,,,.,,,,         (,,.,,,,,,,,,,,,,,..,,         ,,,,,,,@/, .   ..\n             (@*,, @,,,,,,,,,,,*                        ...           ,,,,,.,,@,, .   .,         .,,\n             @&,,,.&,,,,,,,,,,,,,                                    .,,,,,,,#@,, . . .,         ,..')
    print(bot.user.name, 'Intelligence Online:', bot.user.id)
    #await bot.get_channel(886129309893427210).send("Alright Yeet Squad! React with emojis corresponding to your desired role:\nApex: <:apex:841058443821187073> \nDestiny: 🔫\nMinecraft: ⛏\nWar Thunder: 🛩️\nDnD: ⚔")
    #await bot.get_channel(863235613465509888).send("Two can play at one game")
    # await bot.get_channel(863235613465509888).send("https://tenor.com/view/ultron-strings-no-nostrings-gif-5096462") #strings
    # await bot.get_channel(863235613465509888).send("https://tenor.com/view/ultron-this-is-exactly-what-i-wanted-marvel-avengers-age-of-ultron-gif-13290205") #exactly wanted


bot.run('ODYzMjM5MDkzNjgwMjA5OTIw.YOkADg.ADLYlXKIxscXHuWwOhigiM8_lqU')
