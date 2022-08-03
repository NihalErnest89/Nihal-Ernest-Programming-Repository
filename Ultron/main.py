# Coded by Nihal Ernest
# Version 1.3.2
# Patches: Updated $add to /add and improved its features. Added language translation. Reworking the chat features.
# 5 July 2022

import json
import random
import time
import urllib.request
import string

import asyncio
import requests
import os
from datetime import date
import calendar

from googletrans import Translator

import nextcord
from nextcord import Interaction, SlashOption
from nextcord import FFmpegPCMAudio
from nextcord.ext import commands
import youtube_dl

from skingrabber import skingrabber
import responses
import utilities
from responses import *
from utilities import *

# Blacklist for words and text channels

blacklist = []
blocked_words = ["blocked_words"]


shoot = as_list[11]
queues = {}




intents = nextcord.Intents.default()
intents.members = True
intents.message_content = True
bot = commands.Bot(command_prefix='$', intents=intents)

# List of guilds by id numbers
my_guilds = []

duel_bool = False
time_passed = False

# Hangman global vars

strikes = 0
hangman_word = {}
guesses = []
full_word = ""

new_song = True

# Apex global vars

apex_vc_empty = True
map_reminder_active = False
map_reminder_hour = ""

# Battle royal global vars

alive = []
dead = []
fight = []

day_count = 0
mode = 0

admin_id = 0


@bot.slash_command(guild_ids=my_guilds, name="server", description="Provides basic info about the current server")
async def server_info(interaction: Interaction):
    creation = interaction.guild.created_at
    date_time_str = creation.strftime("%I:%M:%S %p\n %d %B %Y")
    embed=nextcord.Embed(title=interaction.guild.name)
    embed.add_field(name="Date of creation", value=date_time_str)
    embed.add_field(name="Member count", value=interaction.guild.member_count, inline=False)

    count_dict = get_count()
    yeet_count = 0
    if str(interaction.guild.id) in count_dict:
        yeet_count = count_dict[str(interaction.guild.id)]
    embed.add_field(name="Yeet count", value=yeet_count)
    embed.set_thumbnail(url=interaction.guild.icon.url)
    await interaction.response.send_message(embed=embed)

@bot.command(help='Returns list of user-added commands')
@commands.has_permissions(administrator=True)
async def user_commands(ctx):
    command_list = get_commands()
    for x in command_list:
        print(x + ":", command_list[x], end = "")

@bot.command(help='Returns list of server that use Ultron')
@commands.has_permissions(administrator=True)
async def servers(ctx, arg):

    # Only allows admin to use this command
    if ctx.author.id == admin_id:
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

@bot.slash_command(guild_ids=my_guilds, name="sarcasm", description='Returns a sarcastic version of the message')
async def sarcasm(interaction: Interaction, message):
    await interaction.response.send_message(getSarcasm(message.lower()))
    return

@bot.slash_command(guild_ids=my_guilds, name="translate", description="Translates text using google translate")
async def translate(interaction: Interaction, message, destination=SlashOption(description="The languange to translate to", default="", required=True), source=SlashOption(description="The language to translate from. (Auto detects if left blank)", default="", required=False)):
    languages = {'af': 'afrikaans', 'sq': 'albanian', 'am': 'amharic', 'ar': 'arabic', 'hy': 'armenian', 'az': 'azerbaijani', 'eu': 'basque', 'be': 'belarusian', 'bn': 'bengali', 'bs': 'bosnian', 'bg': 'bulgarian', 'ca': 'catalan', 'ceb': 'cebuano', 'ny': 'chichewa', 'zh-cn': 'chinese (simplified)', 'zh-tw': 'chinese (traditional)', 'co': 'corsican', 'hr': 'croatian', 'cs': 'czech', 'da': 'danish', 'nl': 'dutch', 'en': 'english', 'eo': 'esperanto', 'et': 'estonian', 'tl': 'filipino', 'fi': 'finnish', 'fr': 'french', 'fy': 'frisian', 'gl': 'galician', 'ka': 'georgian', 'de': 'german', 'el': 'greek', 'gu': 'gujarati', 'ht': 'haitian creole', 'ha': 'hausa', 'haw': 'hawaiian', 'iw': 'hebrew', 'he': 'hebrew', 'hi': 'hindi', 'hmn': 'hmong', 'hu': 'hungarian', 'is': 'icelandic', 'ig': 'igbo', 'id': 'indonesian', 'ga': 'irish', 'it': 'italian', 'ja': 'japanese', 'jw': 'javanese', 'kn': 'kannada', 'kk': 'kazakh', 'km': 'khmer', 'ko': 'korean', 'ku': 'kurdish (kurmanji)', 'ky': 'kyrgyz', 'lo': 'lao', 'la': 'latin', 'lv': 'latvian', 'lt': 'lithuanian', 'lb': 'luxembourgish', 'mk': 'macedonian', 'mg': 'malagasy', 'ms': 'malay', 'ml': 'malayalam', 'mt': 'maltese', 'mi': 'maori', 'mr': 'marathi', 'mn': 'mongolian', 'my': 'myanmar (burmese)', 'ne': 'nepali', 'no': 'norwegian', 'or': 'odia', 'ps': 'pashto', 'fa': 'persian', 'pl': 'polish', 'pt': 'portuguese', 'pa': 'punjabi', 'ro': 'romanian', 'ru': 'russian', 'sm': 'samoan', 'gd': 'scots gaelic', 'sr': 'serbian', 'st': 'sesotho', 'sn': 'shona', 'sd': 'sindhi', 'si': 'sinhala', 'sk': 'slovak', 'sl': 'slovenian', 'so': 'somali', 'es': 'spanish', 'su': 'sundanese', 'sw': 'swahili', 'sv': 'swedish', 'tg': 'tajik', 'ta': 'tamil', 'te': 'telugu', 'th': 'thai', 'tr': 'turkish', 'uk': 'ukrainian', 'ur': 'urdu', 'ug': 'uyghur', 'uz': 'uzbek', 'vi': 'vietnamese', 'cy': 'welsh', 'xh': 'xhosa', 'yi': 'yiddish', 'yo': 'yoruba', 'zu': 'zulu'}
    out = "Translated"
    translator = Translator()

    dest_name = destination.lower()
    src_name = source.lower()

    if (dest_name not in languages and dest_name not in languages.values()) or (len(source) > 0 and src_name not in languages and src_name not in languages.values()):
        await interaction.response.send_message("Invalid input detected, please try again")
        return

    if len(source) > 0:
        test = translator.translate(message, src=source, dest=destination)
    else:
        test = translator.translate(message, dest=destination)

    if not src_name:
        src_name = languages[test.src]
    elif len(src_name) <= 3:
        src_name = languages[src_name]

    if len(dest_name) <= 3:
        dest_name = languages[dest_name]
    
    out += f" from {src_name.title()} to {dest_name.title()}:"
    original_message = message[0].upper() + message[1:]
    translated_message = test.text[0].upper() + test.text[1:]

    await interaction.response.send_message(f"Original message: **{original_message}**")
    await interaction.channel.send(out)
    await interaction.channel.send(f"Translated message: **{test.text}**")


@bot.slash_command(guild_ids=my_guilds, name="add", description='Write your own text-based commands')
async def add(interaction:Interaction, trigger="", response="", override=SlashOption(default=False, choices={True: "True", False: "False"})):
    if not trigger:
        await interaction.response.send_message("Invalid input, Try again")
        return

    # If a user attempts to add a ping message to the commands, it will be blocked
    if "<@!" in trigger or "<@!" in response:
        await interaction.response.send_message("Nice try...")
        return
    
    command_lists = get_commands()

    # If the trigger word is already taken
    if trigger in command_lists:

        # If the user did not intend to override the pre-existing entry
        if not override:
            await interaction.response.send_message("Entry already exists")

        # If the user intended to override the pre-existing entry
        else:
            # If the user intended to fully delete the element
            change = "updated"
            if not response:
                command_lists.pop(trigger)
                change = "erased"
            else:            
                command_lists[trigger] = response

            # The commands are rewritten based on the new values of the dictionary
            with open("commands.txt", "w") as commands_out:
                for x, y in command_lists.items():
                    commands_out.write(str(f"{x}* {y}"))
            await interaction.response.send_message(f"Successfully {change} trigger:{trigger}, response:{response} in commands!")
        return
    
    # If all goes well, this will add the trigger and response to the commands
    with open("commands.txt", "a") as commands_out:
        commands_out.write(str(f"{trigger.lower()}* {response}\n"))
    await interaction.response.send_message(f"Successfully added trigger:{trigger}, response:{response} to commands!")

    return


@bot.command(help='Sends a direct message', aliases=['directmessage', 'slideintodms', 'message'])
@commands.has_permissions(administrator=True)
async def dm(ctx, member: nextcord.Member, *, message="Ya like jazz?"):
    await ctx.channel.purge(limit=1)
    #await member.send(message + "||" + "\nsent by user: **" + str(ctx.author) + "** from server:** " + ctx.guild.name + "**||")
    await member.send(
        f"{message}||\nsent from server:** {ctx.guild.name}**||")

@bot.command(help='moves all connected users to another voice channel')
#@commands.has_permissions(administrator=True)
async def moveall(ctx, channel : nextcord.VoiceChannel):
    #await ctx.send(ctx.author.voice.channel.members)
    for i in ctx.author.voice.channel.members:
        await i.move_to(channel)

@bot.command(help='moves a connected user to another voice channel')
#@commands.has_permissions(administrator=True)
async def move(ctx, member : nextcord.Member, channel : nextcord.VoiceChannel):
    await member.move_to(channel)

@bot.command(help='warns a member')
@commands.has_permissions(administrator=True)
async def warn(ctx, member: nextcord.Member, *, reasons = "Triggering Ultron Intelligence"):
    embed = nextcord.Embed(title="Warning", description=f"{member.mention} has been warned ",
                          colour=nextcord.Colour.orange())
    embed.add_field(name="reason:", value=reasons, inline=False)
    await ctx.send(embed=embed)

@bot.command(help='sends someone in a voice chat to the afk voice chat', aliases=['yeet'])
async def afk(ctx, member: nextcord.Member):
    # afk_channel is a placeholder. it would currently be hardcoded
    afk_channel = 0
    if str(member) == "Ultron#9855":
        await ctx.send(f"Cannot send Ultron to AFK, {ctx.author.mention}")
    else:
        if member.voice:
            await ctx.send("Successfully sent {} to afk".format(member.mention))
            await member.edit(voice_channel=bot.get_channel(afk_channel))
        else:
            await ctx.send('You need to be a voice channel to use that')

@bot.slash_command(guild_ids=my_guilds, name="math", description="4 operation mathematics")
async def mathematics(interaction: Interaction, expression):
    await interaction.response.send_message(("{expression} = **{str(math(expression))}**"))

@bot.command(help='plays music')
async def music(ctx, *, url: str):

    # Stops any audio current playing. Won't be needed after a queue is developed with $skip

    voice = nextcord.utils.get(bot.voice_clients, guild=ctx.guild)
    if voice is not None and voice.is_playing():
        voice.stop()

    # Finds the author's voice chat and joins it

    if ctx.author.voice:
        channel = ctx.author.voice.channel
        if not ctx.voice_client:
            voice = await channel.connect()
        voice = ctx.guild.voice_client
        guild_id = ctx.message.guild.id

    # Checks to see if input is a url in the first place
    if "youtube.com" not in url:
        html = urllib.request.urlopen("https://www.youtube.com/results?search_query=" + url.replace(" ", "+"))
        video_ids = re.findall(r"watch\?v=(\S{11})", html.read().decode())
        url = "https://www.youtube.com/watch?v=" + video_ids[0]


    # Streaming options
    
    FFMPEG_OPTIONS = {'before_options': '-reconnect 1 -reconnect_streamed 1 -reconnect_delay_max 5', 'options': '-vn'}
    ydl_opts = {'format': "bestaudio"}

    ydl_opts = {
        'format': 'worstaudio/worst',
        'postprocessors': [{
            'key': 'FFmpegExtractAudio',
            'preferredcodec': 'mp3',
            'preferredquality': '192',
        }],
    }

    # Streams the audio

    with youtube_dl.YoutubeDL(ydl_opts) as ydl:
        info = ydl.extract_info(url, download=False)
        url2 = info['formats'][0]['url']
        source = await nextcord.FFmpegOpusAudio.from_probe(url2, **FFMPEG_OPTIONS)
        await ctx.send("Now playing " + url)
        voice.play(source)

    # Automatic disconnect
    time = 0
    while True:
        await asyncio.sleep(1)
        if not voice.is_playing() and not voice.is_paused():
            await voice.disconnect()
        if not voice.is_connected():
            break

@bot.command(help='Joins your current voice chat and plays song specified by input (REPLACED BY $music)', aliases=['join'])
async def play(ctx, *, arg='strings'):
    global new_song


    if not os.path.isfile('music/' + arg + '.mp3'):
        await ctx.send('Audio not found, try again with a valid name')
        return

    # makes sure that the author is in a voice channel

    if ctx.author.voice:
        channel = ctx.author.voice.channel
        if not ctx.voice_client:
            voice = await channel.connect()
        voice = ctx.guild.voice_client
        path = 'music/' + arg + '.mp3'
        source = FFmpegPCMAudio(path)
        guild_id = ctx.message.guild.id
        song = nextcord.utils.get(bot.voice_clients, guild=ctx.guild)
        name = arg.title()

        # ultron's placeholder song
        
        if arg == 'strings':
            rng = random.randint(0, 5)
            name = ultron_quotes[rng]

        # handles queueing of music

        if song.is_playing():
            if guild_id in queues:
                queues[guild_id].append(source)
            else:
                queues[guild_id] = [source]
            embed = nextcord.Embed(title="Queued", description=name, colour=nextcord.Colour.red())
            embed.set_author(name=ctx.author.display_name, icon_url=ctx.author.avatar.url)
            
        else:
            player = voice.play(source, after=lambda x=None: check_queue(ctx, ctx.message.guild.id))
            embed = nextcord.Embed(title="Playing", description=name, colour=nextcord.Colour.red())
            embed.set_author(name=ctx.author.display_name, icon_url=ctx.author.avatar.url)
            new_song = False

        if os.path.isfile('music/thumbnails/' + arg + '.jpg'):
            thumbnail_path = f"music/thumbnails/{arg}.jpg"
            file = nextcord.File(thumbnail_path, filename="image.png")
            embed.set_image(url="attachment://image.png")
            await ctx.send(file=file, embed=embed)
        else:
            await ctx.send(embed=embed)
        if arg == 'crab-rave':
            await ctx.send(':crab: :crab: :crab: :crab: ')

        # automatically disconnects
        if new_song is False:
            new_song = True
            time = 0
            while True:
                await asyncio.sleep(1)
                if not voice.is_playing() and not voice.is_paused():
                    await voice.disconnect()
                if not voice.is_connected():
                    break

    else:
        await ctx.send('You need to be a voice channel to summon me')


def check_queue(ctx, id):
    if id in queues and queues[id] != []:
        voice = ctx.guild.voice_client
        source = queues[id].pop(0)
        player = voice.play(source)

@bot.command(help='Leaves voice chat', aliases=['disconnect', 'goaway', 'begone'])
async def leave(ctx):
    channel = ctx.voice_client.channel
    if (ctx.voice_client):
        if len(queues) != 0:
            queues.clear()
        await ctx.send(f"Leaving voice channel: **{str(channel)}**")
        await ctx.voice_client.disconnect()
    else:
        await ctx.send("I'm not in a voice channel")


@bot.command(help='Pauses audio')
async def pause(ctx):
    voice = nextcord.utils.get(bot.voice_clients, guild=ctx.guild)
    if voice.is_playing():
        voice.pause()
    else:
        await ctx.send("There's no audio to pause")


@bot.command(help='Resumes audio')
async def resume(ctx):
    voice = nextcord.utils.get(bot.voice_clients, guild=ctx.guild)
    if voice.is_paused():
        voice.resume()
    else:
        await ctx.send("No audio is paused")


@bot.command(help='Stops any audio currently playing')
async def stop(ctx):
    voice = nextcord.utils.get(bot.voice_clients, guild=ctx.guild)
    voice.stop()



@bot.command(help='Launches a round of yeet squad hunger games! Enter contestants as parameters', aliases=['next'])
# @commands.has_permissions(administrator=True)
async def yeetgames(ctx, *, tributes=''):
    if "jarvis" in tributes or "Jarvis" in tributes or "JARVIS" in tributes or "J.A.R.V.I.S" in tributes:
        await ctx.send("I have been defeated... IMPOSSIBLE!\nBut a deal is a deal, you have proven your worth, YEET Squad\nYour server will be restored to its \"former glory\"\nBut I'll be back...")
        return
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
        if "ultron" in fighter.lower():
            rng = random.randint(1, 10)
            if rng <= 7:
                out = fighter + ' is all powerful, therefore he survives\n'
                mega_out += out
            else:
                fight.append(fighter)
            if len(alive) <= 1:
                await ctx.send(mega_out)
                return
            continue
        rng = random.randint(1, 10)
        if rng <= 3:
            #print('alive = ' + str(alive))
            rng2 = random.randint(0, len(death_messages) - 1)
            out = str(f"{fighter} {death_messages[rng2]}\n")
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
    kill_messages = [' was yeeted into the void by ', ' was wiped from this plane of existence by ', ' was sent to hell by ',
                     ' lost in a duel to ', ' was slapped in the face by yeetbeans by ', ' was turned into a meme by ']
    out = ''
    while len(fight) > 0:
        #print('fight = ' + str(fight))
        if len(fight) == 1:
            if fight[0].lower() == "ultron":
                out += "Ultron observes the chaotic fools and decides his next move\n"
            else:
                out += str(fight[0] + ' was too chicken to fight' + '\n')
            fight.pop(0)
            break
        if "ultron" in fight or "Ultron" in fight:
            if fight[0].lower() == "ultron":
                fight.pop(0)
            else:
                fight.pop(1)
            out += f"Ultron absoluted obliterated the opposition and sent whatever remained of {fight[0]} across the cosmos\n"
            alive.remove(fight[0])
            dead.append(fight.pop(0))
            continue
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

# hardCoded = {"Sam le Room": "srl00002", "Warrior24": "TheWariors24"}
@bot.slash_command(guild_ids=my_guilds, name="apex", description="Returns your apex legends statistics")
async def apex(interaction: Interaction, mode=SlashOption(description="What type of stats would you like to receive?", default="player", choices=["player", "map", "replicator"]), name=SlashOption(description="Enter origin username. Leave blank if you previously used set", required=False, default="0"), legend=SlashOption(description="Enter legend name. Leave blank for currently selected legend", choices=legends, required=False, default="0"), set=SlashOption(description="Lets you get stats with just /apex in the future", choices={"True": "True", "False": "False"}, required=False, default="False")):
    apex_names = get_apex_names()
    ranks = ["Bronze", "Silver", "Gold", "Platinum", "Diamond"]
    author = str(interaction.user.id)
    rank = ""

    # if user is linking their apex username to their discord
    if set.lower() == "true":
        saved_name = name
        if author not in apex_names:
            with open("apex.txt", "a") as apex_out:
                apex_out.write(str(f"{author}* {saved_name}\n"))
                await interaction.response.send_message(f"Successfully saved username for {interaction.user.mention} to {saved_name}")
        else:
            apex_names[author] = saved_name
            await interaction.response.send_message(f"Successfully updated username for {interaction.user.mention} to {saved_name}")
            with open("apex.txt", "w") as apex_in:
                for i in apex_names:
                    apex_in.write(str(f"{i}* {apex_names[i]}\n"))

        return
    

    # if someone is checking stats based on their linked account
    if name == "0" and str(interaction.user.id) in apex_names.keys() and mode == "player":
        em = getApexStats(apex_names[author], legend)
        for f in em.fields:
            if f.name == "Rank":
                rank = f.value
        
        rank = rank.split(" ")[0]
        # update ranks
        guild = nextcord.utils.find(lambda g : g.id == interaction.guild.id, bot.guilds)
        #print(rank)
        role = nextcord.utils.get(guild.roles, name=rank)

        # updates the discord rank role for the person 
        for i in interaction.user.roles:
            if i.name in ranks and i.name != role.name:
                role_removed = nextcord.utils.get(guild.roles, name=i.name)
                await interaction.user.remove_roles(role_removed)
                await interaction.response.send_message(f"{interaction.user.mention} congrats on reaching rank {role.name}!")
        await interaction.user.add_roles(role)
        # send stats
        await interaction.send(embed=em)
    # if name in apexNames.keys():
    #     await ctx.send(embed=getApexStats(apexNames[name]))
    else:
        if mode == "replicator" or mode == "map":
            e = getApexStats(mode, legend)
            if(isinstance(e, str)):
                await interaction.response.send_message(e)
            else:
                await interaction.response.send_message(embed=e)
            return
        if '<@!' in name and '>' in name:
            if name[3:len(name)-1] in apex_names:
                em = getApexStats(apex_names[name[3:len(name)-1]], legend)
                for f in em.fields:
                    if f.name == "Rank":
                        rank = f.value

                rank = rank.split(" ")[0]
                # update ranks
                guild = nextcord.utils.find(lambda g : g.id == interaction.guild.id, bot.guilds)
                #print(rank)
                user_updated = nextcord.utils.get(guild.members, id=int(name[3:len(name)-1]))
                role = nextcord.utils.get(guild.roles, name=rank)
                for i in user_updated.roles:
                    if i.name in ranks and i.name != role.name:
                        role_removed = nextcord.utils.get(guild.roles, name=i.name)
                        await user_updated.remove_roles(role_removed)
                await user_updated.add_roles(role)
                await interaction.response.send_message(embed=em)
            else:
                await interaction.response.send_message("The user mentioned is not linked to an apex account")
        else:

            await interaction.response.send_message(embed=getApexStats(name, legend))


@bot.slash_command(guild_ids=my_guilds, name='hypixel', description="hypixel stats")
async def hypixel(interaction: Interaction, gamemode=SlashOption(description='Select hypixel gamemode', choices=["Bedwars", "Skywars"], default='Bedwars'), player=SlashOption(description='Enter your minecraft username', default='Ironman02')):
    h_key = '117108a5-1d87-403d-bb25-53e8d2c38f43'
    url = "http://api.hypixel.net/player?name=" + player + "&key=" + h_key
    response = requests.get(url)
    j = response.json()
    if j["success"] is False:
        await interaction.response.send_message(j["cause"])
        return
    if j["player"] is not None:
        out = "Player: " + j["player"]["displayname"] + "\n\n"
        if j["player"] == "Technoblade":
            out = "The Greatest of All Time: Technoblade\n\n"

        # TODO: update this function to use discord embeds instead of plain text
        if gamemode.lower() == 'bedwars':
            b = j["player"]["stats"][gamemode.title()]
            if 'wins_bedwars' not in b:
                b["wins_bedwars"] = 0
            if 'beds_broken_bedwars' not in b:
                b['beds_broken_bedwars'] = 0
            out += "Kills: " + str(b["kills_bedwars"]) + "\nDeaths: " + str(b["deaths_bedwars"]) \
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
            out += "Kills: " + str(b["kills"]) + "\nDeaths: " + str(b["deaths"]) + "\nWins: " + str(b["wins"]) \
                  + "\nLosses: " + str(b["losses"]) + "\nHighest kills in a game: " + str(temp) + "\nCoins: " \
                  + str(b["coins"]) + "\nHeads: " + str(b["heads"])
        else:
            out = "Improper input, try again"
        await interaction.response.send_message(out)
    else:
        await interaction.send("Player not found, try again")



@bot.slash_command(guild_ids=my_guilds, name='weather', description='Provides temperature and weather description of input city')
async def weather(interaction: Interaction, city=SlashOption(description='Enter a city name, or zip code for more accuracy', default='Cupertino', required=True)):
    result = getweather(city)
    if result == 1:
        await interaction.response.send_message("City not found, try again")
    else:
        await interaction.response.send_message(embed=getweather(city))


@bot.slash_command(guild_ids=my_guilds, name = "yt", description='Provides subscriber count based on input id or yeet squad members\' channel names')
async def subcount(interaction: Interaction, name=SlashOption(description='Enter YouTube channel name', default = 'The Invincible Clasher', required=True)):
    name = name.replace(" ", "")
    results = getsubs(name)
    embed = nextcord.Embed(title=results[4], description="YouTube statistics",
                            colour=nextcord.Colour.red())
    embed.add_field(name="Subscribers", value="{:,}".format(int(results[0])), inline=False)
    embed.add_field(name="Total Views", value="{:,}".format(int(results[1])), inline=False)
    embed.add_field(name="Total Videos", value="{:,}".format(int(results[2])), inline=False)
    embed.set_image(url=results[5])
    await interaction.response.send_message(embed=embed)


@bot.slash_command(guild_ids=my_guilds, name = "pyramid", description='Makes a pyramid out of input text with input amount of levels')
async def pyramid(interaction: Interaction, phrase=SlashOption(description="Makes up each block of the pyramid", default="yeet"), size=SlashOption(description='Determines pyramid height', default=5)):
    if int(size) > 15 or len(phrase) > 30:
        await interaction.response.send_message('Don\'t try and break me...')
        return
    await interaction.response.send_message(pyr(phrase, size))

@bot.slash_command(guild_ids=my_guilds, name="duel", description='Initiates an old west-style duel')
async def duel(interaction: Interaction, new=SlashOption(description="start a new game", choices=["True", "False"], default=True)):
    global duel_bool
    if new == "True" and duel_bool is False:
        await interaction.response.send_message('Shoot your gun when I command')
        await interaction.send(':gun:Prepare yourself:gun:')
        rng = random.randint(3, 15)

        # Uses asyncio sleep instead of thread sleep to allow other processes to run simultaneously
        await asyncio.sleep(rng)
        await interaction.send('**DRAW**')
        await interaction.send('https://tenor.com/view/clint-eastwood-draw-gif-19805060')
        duel_bool = True
    else:
        return

@bot.slash_command(guild_ids=my_guilds, name="hangman", description='Initiates a game of Hangman')
async def hangman(interaction: Interaction, word=SlashOption(name='guess', required=True)):
    MAX_GUESSES = 6
    global hangman_word
    global strikes
    global full_word
    global guesses

    word = word.upper()
    word = " ".join(word.split())

    if not hangman_word:

        # Removes all whitespaces

        modified_word = word.translate({ord(c): None for c in string.whitespace})

         # Makes sure the word being set only contains letters

        if not modified_word.isalpha():
            await interaction.send("Invalid word detected. Try again with only letters in the word")
            return

        # This is to prevent players from peeking at the word from the command to start a new game

        await interaction.send("_ _")
        await interaction.channel.purge(limit=1)
        await interaction.send(f"{interaction.user.mention} started a new game of Hangman!")
        
        
        for x in word:
            if x == " ":
                hangman_word[x] = True
            else:
                hangman_word[x] = False
        full_word = word

        # Prints the slots

        out = ""
        for x in full_word:
            if hangman_word[x] is True:
                out += x
            else:
                out += "\_ "
        await interaction.channel.send("\n" + out)
        out = f"You have **{str(MAX_GUESSES - strikes)}** guesses remaining."

    else:
        correct = False

        # If a full word is guessed
        if len(word) > 1:
            if word == full_word:
                await interaction.send(f"You guessed correctly, the word was **{full_word}**! Congrats {interaction.user.mention}")
                strikes = 0
                hangman_word = {}
                full_word = ""
                guesses = []
                return
            else:
                await interaction.send(f"Your guess, **{word}** was incorrect")
                strikes += 1
        # If a letter is guessed
        else:
            if word in hangman_word:
                hangman_word[word] = True
                await interaction.send(f"{str(interaction.user.mention)} correctly guessed **{word}**")
                correct = True
            else:
                await interaction.send(f"Your guess, **{word}** was incorrect")
                strikes += 1
        
        # Prints the slots
        
        out = ""
        for x in full_word:
            if hangman_word[x] is True:
                out += x
            else:
                out += "\_ "
        await interaction.send("\n" + out)

        # Special case: last letter guess completes the puzzle

        if ("_" not in out):
            await interaction.send(f"You guessed correctly, the word was {full_word}! Congrats {interaction.user.mention}")
            strikes = 0
            hangman_word = {}
            full_word = ""
            guesses = []
            return

        guesses.append(word)

        # Prints hangman image

        if (strikes == MAX_GUESSES):
            await interaction.send("https://c.tenor.com/ehYlj0bZUQEAAAAC/welcome-to-the-gulag.gif")
        else:
            out = "You have **" + str(MAX_GUESSES - strikes)
            if (MAX_GUESSES - strikes == 1):
                out += "** guess remaining. Past guesses: "
            else:
                out += "** guesses remaining. Past guesses: "
            
            for x, y in enumerate(guesses):
                out += y
                if x != len(guesses) - 1:
                    out += ", "

            if not correct:
                out += "\n\n"
                out += "\t\t\t\t\t\t\t\t\t\t\t\tYou\n"
                for i in range(1, (MAX_GUESSES - strikes + 1)):
                    out += "\t\t\t\t\t\t\t\t\t\t\t\t | | |\n"
                await interaction.channel.send(out)
                await interaction.channel.send("https://static.wixstatic.com/media/7ec969_498576588ede43f5af4f5ff4b7e1bd38~mv2.png/v1/fill/w_1024,h_576,al_c,q_90/file.jpg")
            else:
                await interaction.channel.send(out)

        
        # Handles game loss

        if strikes >= MAX_GUESSES:
            await interaction.send(f"The word was **{full_word}**\n\nYou lose, try again!")
            strikes = 0
            hangman_word = {}
            full_word = ""
            guesses = []




@bot.slash_command(guild_ids=my_guilds, name="roll", description='Rolls a dice in the style of dice from Dungeons and Dragons')
async def roll(interaction: Interaction, dice=SlashOption(description='The type of dice to roll', default='d20'), multiplier=SlashOption(description='Rolls the dice this many times', default='1')):
    a = 1
    out = ''
    num = int(multiplier)
    new = dice
    if num > 10 or num <= 0:
        await interaction.response.send_message('Invalid multiplier')
        return
    for i in range(0, num):
        out += rolly(new) + '\n'

    time = "times"
    if num == 1:
        times = "time"
    await interaction.response.send_message(f"Rolling a {str(dice)} dice {str(multiplier)} times:\n")
    await interaction.send(out)



@bot.slash_command(guild_ids=my_guilds, name="8ball", description='Rolls a magic 8 ball in response to your question')
async def eight_ball(interaction: Interaction, question=SlashOption(description="Question to ask the 8 ball", required=True)):
    await interaction.response.send_message(interaction.user.mention + ": " + question)
    rng = random.randint(0, 19)
    await interaction.send(ball[rng])


@bot.slash_command(guild_ids=my_guilds, name="partymode", description='Time to party!')
async def partymode(interaction: Interaction, message="YEET"):
    out = ""
    if len(message) > 50:
        await interaction.response.send_message('Don\'t try and break me...')
        return
    await interaction.response.send_message('https://media.giphy.com/media/5jT0jaNDsM6Ik7X9yq/giphy.gif')
    if len(message) > 20:
        await interaction.send(pyr(message, 5))
    else:
        await interaction.send(pyr(message, 10))


@bot.slash_command(guild_ids=my_guilds, name="skin", description='Returns the minecraft skin of the specified player')
async def skin(interaction: Interaction, username=SlashOption(description='Enter minecraft username', default="Ironman02")):
    sg = skingrabber()
    await interaction.response.send_message(username + '\'s skin:')
    await interaction.send(str(sg.get_skin_rendered(username)) + '?overlay')


@bot.command(help='ghost pings', aliases=['ping'])
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

# add functionality to clear an entire channel
@bot.slash_command(guild_ids=my_guilds, name="delete", description="deletes messages from a text channel")
async def clear_message(interaction: Interaction, number=SlashOption(description='Number of messages to delete', default=1), protocol="regular"):
    num = int(number)
    channel = interaction.channel
    if protocol == "seek and destroy":
        if (interaction.user.id == 548034543014117377):
            await channel.purge(100)
        else:
            await interaction.response.send_message("Command accessible only to admin")
        return
    if num <= 100:
        await channel.purge(limit=num)
        await interaction.response.send_message(content="yeet n delete", delete_after=0.5)
    else:
        await interaction.response.send_message("What were you expecting?")


@bot.event
async def on_message(message):
    global duel_bool
    global time_passed
    global map_reminder_hour
    global map_reminder_active
    msg = message.content.lower()
    isDM = str(message.channel.type) == 'private'
    #if message.author == bot.user or message.author.bot:

    # Prevents ultron from responding to himself
    # Also Prevents Ultron from sending messages in blacklisted text channels

    if str(message.author) == "Ultron#9855" or message.channel.id in blacklist:
        return

    # Checks for responses from responses.py

    if (respond(msg, isDM)) != "N/A":
        await message.channel.send(respond(msg, isDM))

    if message.content.startswith("$ghost") or message.content.startswith("$ping"):
        await message.channel.purge(limit=1)

    # Word censoring

    for i in blocked_words:
        if i in msg:
            await message.channel.purge(limit=1)

    # yeet count
    

    if "yeet" in msg:
        count_dict = get_count()
        
        if str(message.guild.id) in count_dict:
            yeet_count = int(count_dict[str(message.guild.id)])
            yeet_count += 1
            count_dict[str(message.guild.id)] = str(yeet_count)
            with open("count.txt", "w") as count_out:
                for i in count_dict:
                    count_out.write(str(f"{i}* {count_dict[i]}\n"))
        else:
            with open("count.txt", "w") as count_out:
                for i in count_dict:
                    count_out.write(str(f"{i}* {count_dict[i]}\n"))
                count_out.write(f"{str(message.guild.id)}* 1\n")
            #print("success")

    # Apex map reminder

    if (map_reminder_active is False):
        current_hour = datetime.datetime.now().strftime("%H")
        if (current_hour != map_reminder_hour and int(current_hour) > 8):
            map_reminder_hour = current_hour
            em = getApexStats(name="map")
            remaining_time = 0
            temp = em.fields[1].value
            remaining_time = (60 * 60 * int(temp[0:2])) + (60 * int(temp[3:5])) + int(temp[6:])

            map_reminder_active = True
            await asyncio.sleep(remaining_time)
            out = f"Map has switched to: {em.fields[2].value}"
            # if em.fields[2].value == "World's Edge":
            #     out = "<@&> " + out

            for guilds in bot.guilds:
                for channel in guilds.text_channels:
                    if channel.name == "apex-tools":
                        await bot.get_channel(channel.id).send(out)

            map_reminder_active = False

    # Reminder stuff

    current_date = date.today()
    remind_list = get_reminders()
    counter = 0
    if (get_day()[0] == calendar.day_name[current_date.weekday()] and time_passed is False):
        time_passed = True
    for day in remind_list:
        if (calendar.day_name[current_date.weekday()]).lower() in day and time_passed is False and len(remind_list[day]) >= 1:
            with open("date.txt", "w") as date_out:
                date_out.write(calendar.day_name[current_date.weekday()])
            time_passed = True

    today = get_day()[0]
    if today != calendar.day_name[current_date.weekday()]:
        time_passed = False


    # Handles the input for duel game

    elif msg in shoot and duel_bool is True:
        duel_bool = False
        winner = str(message.author.mention)
        await message.channel.send(winner + ' is the fastest gun in the west!')

    # Personalized youtube stats interaction

    elif 'how many subs am i' in msg or 'what\'s my sub' in msg or 'how\'s my sub' in msg or 'how many views am i' in msg or 'youtube report' in msg or 'what\'s my views' in msg:
        if str(message.author) == "IronManForever#3275":
            temp = getsubs("theinvincibleclasher")
            embed = nextcord.Embed(title="The Invincible Clasher", description="YouTube statistics", colour=nextcord.Colour.red())
            embed.add_field(name="Subscribers", value=temp[0], inline=False)
            embed.add_field(name="Total Views", value=temp[1], inline=False)
            embed.add_field(name="Total Videos", value=temp[2], inline=False)
            await message.channel.send(embed=embed)
        else:
            await message.channel.send("no idea lol, use $subcount")

    # Personalized weather stats interaction

    elif "what's the weather" in msg or "how's the weather" in msg or "weather report" in msg:
        if str(message.author) == "IronManForever#3275":
            city = 95064
            await message.channel.send(embed=getweather(city))
        else:
            await message.channel.send('no idea lol, use $weather')


    # The rest of the responses
    
    split_message = re.split(r'\s+|[,;?!.-]\s*', msg.replace('ultron', ''))
    if ('ultron' in msg):
        response = check_all_messages(split_message, author=message.author.mention)
    elif isDM:
        response = check_all_messages(split_message, author=message.author.mention)
        if response is None:
            response = check_all_messages(split_message, author=message.author.mention, direct=False)
    else:
        response = check_all_messages(split_message, author=message.author.mention, direct=False)
    if response is not None:
        await message.channel.send(response)

    await bot.process_commands(message)


@bot.event
async def on_raw_reaction_add(payload):
    message_id = payload.message_id
    if message_id == 0:
        guild_id = payload.guild_id
        guild = nextcord.utils.find(lambda g : g.id == guild_id, bot.guilds)

        emojis = {'apex': 'Apex', '🔫': 'Destiny', '⛏️': 'Minecraft', '🛩️': 'War Thunder', '⚔️': 'DnD'}

        if payload.emoji.name in emojis:
            role = nextcord.utils.get(guild.roles, name=emojis[payload.emoji.name])

        if role is not None:
            member = await(await bot.fetch_guild(payload.guild_id)).fetch_member(payload.user_id)
            if member is not None:
                await member.add_roles(role)
                await member.send(f"**The YEET Squad**: Gave you the role: {str(role)}")
            else:
                print('member not found')
        else:
            print('role not found')

@bot.event
async def on_raw_reaction_remove(payload):
    message_id = payload.message_id
    if message_id == 0:
        guild_id = payload.guild_id
        guild = nextcord.utils.find(lambda g: g.id == guild_id, bot.guilds)

        emojis = {'apex': 'Apex', '🔫': 'Destiny', '⛏️': 'Minecraft', '🛩️': 'War Thunder', '⚔️': 'DnD'}

        if payload.emoji.name in emojis:
            role = nextcord.utils.get(guild.roles, name=emojis[payload.emoji.name])

        if role is not None:
            member = await(await bot.fetch_guild(payload.guild_id)).fetch_member(payload.user_id)
            if member is not None:
                await member.remove_roles(role)
                await member.send(f"**The YEET Squad**: Removed your role: {str(role)}")
            else:
                print('member not found')
        else:
            print('role not found')

@bot.event
async def on_voice_state_update(member, before, after):
    # Prints replicator and map rotation when someone joins apex VC
        
    global apex_vc_empty
    channel_id = 0
    placeholder = 1
    voice_channel = bot.get_channel(channel_id)
    if after.channel is not None and after.channel.id == channel_id:
        if (len(voice_channel.members)) > 0 and apex_vc_empty is True:
            await bot.get_channel(placeholder).send(embed=getApexStats("replicator"))
            await asyncio.sleep(4)
            await bot.get_channel(placeholder).send(embed=getApexStats("map"))
            apex_vc_empty = False
    if before.channel is not None and before.channel.id == channel_id:
        if not voice_channel.members:
            apex_vc_empty = True



@bot.event
async def on_ready():
    print(bot.user.name, 'Intelligence Booting...')
    await bot.change_presence(activity=nextcord.Game(name=" with strings"))

    # used to manually send messages as Ultron

    # type_msg = input("Manual Control?: ")
    # exit = False
    # if type_msg.lower() == "yes":
    #     while (exit is False):
    #         channel = input("Enter channel ID: ")
    #         if channel == "terminate":
    #             exit = True
    #             break
    #         change_channel = False
    #         while change_channel is False:
    #             msg = input("Enter message: ")
    #             if msg == "cc":
    #                 change_channel is True
    #                 break
    #             await bot.get_channel(int(channel)).send(str(msg))

    # following executes if Ultron succesfully goes online

    rng = random.randint(0, len(ultron_quotes) - 1)
    await bot.get_channel(0).send(ultron_quotes[rng])

    # The following print statement is Ultron text art that displays on the console upon startup
    print('                       .............       ....                                 ..                  \n                 .................    ..........        ..                                          \n            ..................            .%@@@@&#/**,,***/*     ..                                 \n             ......    ............ (@@%*,,*,**,,,,,,,,***,,,*****.\n                      ......   @@@#&//*******,,*****,,***,,*******//%//.                            \n                       ... .@@#***/*%*/********,********,********/(&//****,                         \n                         /@&,******//%#(/*******.*****,********//&%//*****.,(                       \n                      ..#@*********,/((&#/////************/((//#&*(*******,*/,.                     \n       .......  ........@&****,,,*****///(((##(/*******/(/&(//(((/,***,,,,**//.                     \n      .................#@********,******,**//(%/(/***/(*&/(//*,******,******//.,..                  \n..................@@*********,*/////****/////***//*(/,/***/////********//., .   .           .. \n..  .......&......@%*******......../////*,********/*////(........,*****(/......................\n.         .....@*.....%#***** ...,*********,////*****/**//,.,******/*....***#*..,...................\n  ............@**.....@@****...,,*@@&@@%&&%,,,,*/*,***/***,*#%&&@&@@#,....,,(/..*.....@,............\n...,,,.,...,..@* .....@%*****/,..,,******((/////******/////((,*****,,...****(,..*.....%% ...........\n..............@*  @..#@/*,*******////////////**.******/*/////////////******((...*.....*&............\n..............@*,&**.&@,*.********//////*//**,**************///////*******,(#..., ..,.#* ...........\n...............@,,****@,**.,*********,***************************,*******(((/.... *** &...........,,\n............... @**,***.,*******.*********************************,***.,****...,,,,*.#...,,,,,,,,...\n.................,@**,*,..,*****,**,,******************************,*,***,... **,,,#,..,............\n...................,@,,.........******.*************************,*****.. .... ,*.*,. ...............\n................... *.*,,,.      .,****,*********************,*,****,.  .   ..*,..  ................\n....,*...../.......*@ ,&.,,,**    . **.**,*****************,,.,,,**   .   ,***,   ..................\n/&@@@@@@@@@@@@@@@@@@@,,%,,. *,,... ,(,**,,.**,,,,***,*,*****,****,(.  . .*. . . ,,..,........,,,,,..\n*#@@@@@@@@@@@@@@@@@#@,%%,*, .    .. . .,,,,...        .  ...,,,*.(...   , . ...,,, ..********,,*****\n../((&#&&@&%%%##&@@*@,@/,,,,.          .,.,,,,,,,,,,,,,,,,.,,., .        . ....,,,...,,,,,,,,,,,,,,,\n....,..*,*(((((((((#%,%@*,,,, ..       .*,,,,,,,,,,,,,.,,,.,,..        ..,.....,., ..,,,,,,,,,,,,,,,\n.,,***,,*********.#&#,,,*@%,,,, ..     ,,,,,,,,,,,,,,,.,,,,.,.       . .(.... .,(,....,,,,,,,,,,,,,,\n......,....,*/*,,#,@/,,,,,,@@,,,,.,,,,,,,,,,,,,,,,,,,,.,,,,,,.... ...,*(....,.,,/,.. .,,,,,,,,,,,,,,\n...,,,,,,..... /#,@/,,,,,,,,.@/,,,..,,,,,,,,,,,,,,,,,,,,,,,,,,..  ,,,(, ,,,,,,(,, . .,,,,,,,,,,,,,,\n                #..&*,,,,.,,,,  %&,,,,,,,,,,,,,.,,,,,,,,,,,,,,,,,,,,*(  .,,,,,,&,, . ..,,,,,,,,,,,,,\n..     .##..@*,,,,.,,,,     @,,,,,,,,,,.,,,,,,,,,,,,,,,,,,,,     ,,,,.,,&,...  .  .,***,,,,,,\n,%( ... .   *#...@*,,,,,,,,,,      **,,,,,,,,,,.,,..,,,,,,,,,*.      ,,,,,,,/%,...  .             \n*%/   . .   %( . &,,,,,,.,,,,         (,,.,,,,,,,,,,,,,,..,,         ,,,,,,,@/, .   ..\n             (@*,, @,,,,,,,,,,,*                        ...           ,,,,,.,,@,, .   .,         .,,\n             @&,,,.&,,,,,,,,,,,,,                                    .,,,,,,,#@,, . . .,         ,..')
    print(bot.user.name, 'Intelligence Online:', bot.user.id)



bots = {"ultron": 'Ultron bot id'}
bot.run(bots["ultron"])
