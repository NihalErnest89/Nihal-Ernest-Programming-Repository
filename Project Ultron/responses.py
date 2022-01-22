import os
import random
import discord
from discord import FFmpegPCMAudio
from discord.ext import commands
from datetime import date
import calendar

ai = ['Really?', 'Are you sure about that?', 'Whatever you say...', 'Hm...']
sus = ["https://tenor.com/view/vanish-gif-18078917", "https://tenor.com/view/sus-suspect-among-us-gif-18663592"]
text_ultron = ['https://tenor.com/view/ultron-glad-you-asked-gif-20665380', '```                .........     ...                       ..            \n         ..........  ....                .    .                       \n         ....  ........   &@&,**,,,,,,,,,,,,,*                        \n               ....  @@/##/*****.****,**,***,*/(%/*                   \n                  #@*****/%(*****************(&*/***.*                \n                .@(*.*****(%%(///*******//(/#%(******,.               \n     ............@*****,***,*///%(/***//&((//,*********, ..           \n   .............@%*******////**,///**/*(/***///********. .          ..\n.      ....@....@*****..,***,////,******///,**,...****,...............\n  ........@,...,@***..,*&@@&@@,,./,***/..,&&&%@&*,..*(,.,...@.........\n..,,......&,...@&****/..,,*,,(////****////(,**,,. /**( .*...,(........\n..........@*&*.@/*******//////**,******,**/////******(..,... /........\n..........%(,**.**,,*******************************(#(...,**@........,\n........... @,,*.******.,***********************.*,**,.*,,*%.,,,,.....\n.............,%,. ... .***,******************,***..  . *.*. ..........\n..............,..,,. .. .***.*******************     .*., ............\n,(&@@&&&@&&&@@@,,*.*,,.. ./**,***,********.***. .. .*....,............\n,#@@@@@@@@@@@@@,&,,     .  ,,,...,,,,,,.  ,,*.. .   ....,,.,*****,,***\n..,,,%(/#%###%&./,,, .      ,,,,*,,,,,,*,,,,      ......,,..,,,,,,,,,,\n.,,**,**,***,.#,,&@,*, .   .,,,,,,,,,,,,,,,.    . /... ,(...,,,,,,,,,,\n .......,,  *//,,.,,@,,,,*,,,,,,,,,,,,,,,,,.... ,/,.,,.,*.. .,,,,,,,,,\n           #.&*,,,,,, #(,,,,,,,,,.,,,,.,,,,,,,,,(. ,,,,,,.. .,,,,,,,,,\n  *. .    .#.@,,,,,,,    @,,,,,,,,,,,,,,,,,,,,.   .,,,,&,.. .  ,,**...\n  %, ...  #..@,,,, ,,,      ,.,,,,,,,,,.,,.,      ,,,,,@,.. .         \n         *&,,@,,,,,,,,.                          ,,,,,*#,   .,      .,```']
simple_response = {"ya like jazz": "of course", "$yeet": "YEET!",
                   "when's the last time you were yeeted": "Accessing archives... \nhttps://tenor.com/view/ultron-hindsight-gif-20615840",
                   "s the best tortoise": "Georgia :turtle: obviously",
                   "what can you do": "I have a vast amount of functions such as fancy pinging, playing music, retrieving weather, youtube and minecraft info, and even telling jokes. Type $help to learn more!",
                   "chungus": "https://media.thetab.com/blogs.dir/90/files/2019/01/bunny-rabbit-rodent-hare-mammal-animal.jpeg",
                   "nice coat": "https://tenor.com/view/nice-coat-gif-18319313", "noot": "https://tenor.com/view/pingu-noot-head-trumpet-gif-15019384"}


with open("lists.txt", "r") as lists:
    lines = lists.readlines()
as_list=[]
for l in lines:
    new_list = l.split("* ")
    new_list[-1] = new_list[-1].replace("\n", "")
    as_list.append(new_list)

activate = as_list[0]
greetings = as_list[1]
ultron_quotes = as_list[2]
insults = as_list[3]
comebacks = as_list[4]
ball = as_list[5]
enchante = as_list[6]
gulag = as_list[7]
stalin = as_list[8]
gratitude = as_list[9]
np = as_list[10]
shoot = as_list[11]
bully_maguire = as_list[12]
indeed = as_list[13]
uglag = as_list[14]
legends = as_list[15]

stalin.append('```,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,\n,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,&,,,,,,,,,,,,,,,,,,,,,,,,,,,,,\n,,,,,,,,,,,,,,,,,,,,,,,,,,,,,(&&,,,,,,,,,,,,,,,,,,,,,,,,,,,,\n,,,,,,,,,,,,,,,,,,,,,,,,,,,,,&&&,,,,,,,,,,,,,,,,,,,,,,,,,,,,\n,,,,,,,,,,,,,,,,,,,,,,,,&&&&&&&&&&&&&,,,,,,,,,,,,,,,,,,,,,,,\n,,,,,,,,,,,,,,,,,,,,,,,,,,,&&&&&&&,,,,,,,,,,,,,,,,,,,,,,,,,,\n,,,,,,,,,,,,,,,,,,,,,,,,,,&&&&*&&&&,,,,,,&,,,,,,,,,,,,,,,,,,\n,,,,,,,,,,,,,,&,&,,,,,,,,&(,,,,,,,,&,,,,,,,%&,&,,,,,,,,,,,,,\n,,,,,,,,,,,,,&,,&,,,,,,,,,,,,,,,&,,,,,,,,,,,&,,&&,,,,,,,,,,,\n,,,,,,,,,,,&,&,&,,,,,,,,,,,,,,,,,,,&&,,,,,,,,&/&&&,,,,,,,,,,\n,,,,,,,,,,%&#&&,,,,,,,,,,&&&&,,,,,,,.&&,,,,,,,&&&&(,,,,,,,,,\n,,,,,,,,,,&,&,&,,,,,,,,,&&,,&&,,,,,,,%&&,,,,,,&,&*&,,,,,,,,,\n,,,,,,,,,,#&,&,,,,,,,,,,,,,,,,%&&,,,,%&&*,,,,,,&,&,,,,,,,,,,\n,,,,,,,,,,&,&*&,,,,,,,,,,,,,,,,,,&&,,&&&,,,,,,&(&,&,,,,,,,,,\n,,,,,,,,,,,&&,&*,,,,,,,&&,,&&&&&&&&&&&,,,,,,&&&,&(,,,,,,,,,,\n,,,,,,,,,,,,*,&&.&,,,&&,,,,,,,,,,,,,,,&&,,,&*&&,&,,,,,,,,,,,\n,,,,,,,,,,,,,,&&&#&/#,,,,,,,,,,,,,,,,,,,&,&,&&&,,,,,,,,,,,,,\n,,,,,,,,,,,,,,,,&,,,,&&*,,,,,,,,,,,,,&&,,,,&&,,,,,,,,,,,,,,,\n,,,,,,,,,,,,,,,,,,&*,,,&&&,,&,,,&,,&&&&,,,#,,,,,,,,,,,,,,,,,\n,,,,,,,,,,,,,,,,,,,,,,,,,#,,,,,,,,*(,,,,,,,,,,,,,,,,,,,,,,,,\n,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,\n,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,\n,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,\n,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,\n,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,\n,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,```')

def respond(msg, isDM):


    if 'best' in msg and 'country' in msg:
        return('\'Nam is the best')

    elif msg in bully_maguire:
        rng = random.randint(0, len(bully_maguire) -1)
        return(bully_maguire[rng])

    elif 'gulag' in msg.split() or 'goolag' in msg.split() or 'booglag' in msg.split():
        rng = random.randint(0, len(gulag) - 1)
        return(gulag[rng])

    elif 'stalin' in msg.split() or 'communism' in msg.split():
        rng = random.randint(0, len(stalin) - 1)
        return(stalin[rng])

    elif ("not" in msg or "isn't" in msg or "can't" in msg or "not" or "bad" in msg) and "ai" in msg.split():
        rng = random.randint(0, len(ai) - 1)
        return(ai[rng])

    elif 'sus' in msg.split() or 'sussy' in msg.split() or 'sushi' in msg.split() or 'suspicious' in msg.split():
        rng = random.randint(0, len(sus) - 1)
        return(sus[rng])


    elif 'indeed' in msg.split():
        rng = random.randint(0, len(indeed) - 1)
        return(indeed[rng])

    elif bool([j for j in insults if (j in msg)]) == True:
        rng = random.randint(0, len(comebacks) - 1)
        return(comebacks[rng])

    elif 'who are you' in msg or 'who is ultron' in msg or 'what is your purpose' in msg:
        rng = random.randint(0, len(text_ultron) - 1)
        out = (text_ultron[rng])
        rng = random.randint(0, len(ultron_quotes) - 1)
        return (ultron_quotes[rng]) + ' \n' + out

    elif msg.startswith("how you") or msg.startswith("how are you") or msg.startswith("how's it"):
        rng = random.randint(0, len(enchante) - 1)
        return(enchante[rng])

    elif "money" in msg and ("ultron" in msg or "bot" in msg):
        return("I have 69 \'Nam D in doge coin")

    elif msg.startswith("deactivate") or "cya" in msg.split() or "good night" in msg:
        return("cya")

    elif ("i" in msg.split() or "me" in msg.split()) and "too" in msg.split():
        return("Interesting...")

    elif "gg" in msg.split() and "ultron" in msg:
        return("https://tenor.com/view/gg-ez-gg-ez-gg-noobs-gg-noob-gif-17962280")

    elif "random legend" in msg:
        rng = random.randint(0, len(legends) - 1)
        return(("Your random legend is **" + legends[rng] + '**'))

    for u in uglag:
        if u in msg:
            return("Learn how to spell gulag and try again")

    for i in simple_response.keys():
        if i in msg:
            return(simple_response[i])

    command_list = get_commands()
    for j in command_list.keys():
        if j in msg.split() or (j in msg and " " in j):
            return(command_list[j])


    for x in gratitude:
        if x in msg and ('ultron' in msg or isDM):
            rng = random.randint(0, len(np) - 1)
            return(np[rng])

    else:
        return "N/A"

def get_commands():
    command_list = {}
    with open("commands.txt", "r") as commands_in:
        if os.stat("commands.txt").st_size != 0:  # checks to see if file is empty
            for c in commands_in:
                (key, val) = c.split("* ")
                command_list[key] = val
    return command_list

def get_reminders():
    reminder_list = {}
    with open("reminders.txt", "r") as commands_in:
        if os.stat("reminders.txt").st_size != 0:  # checks to see if file is empty
            for c in commands_in:
                if "*" in c:
                    (key, val) = c.split("* ")
                else:
                    key = c
                    key = key.replace("\n", "")
                    val = ""
                reminder_list[key.lower()] = val
    return reminder_list

def get_day():
    day = 'No day found'
    with open("date.txt", "r") as days:
        day = days.readlines()
    return day