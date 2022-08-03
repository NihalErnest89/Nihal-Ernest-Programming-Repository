import os
import random
import nextcord
from nextcord import FFmpegPCMAudio
from nextcord.ext import commands
from datetime import date
import calendar

from pkg_resources import require

ai = ['Really?', 'Are you sure about that?', 'Whatever you say...', 'Hm...']
sus = ["https://tenor.com/view/vanish-gif-18078917", "https://tenor.com/view/sus-suspect-among-us-gif-18663592"]
text_ultron = ['https://tenor.com/view/ultron-glad-you-asked-gif-20665380', '```                .........     ...                       ..            \n         ..........  ....                .    .                       \n         ....  ........   &@&,**,,,,,,,,,,,,,*                        \n               ....  @@/##/*****.****,**,***,*/(%/*                   \n                  #@*****/%(*****************(&*/***.*                \n                .@(*.*****(%%(///*******//(/#%(******,.               \n     ............@*****,***,*///%(/***//&((//,*********, ..           \n   .............@%*******////**,///**/*(/***///********. .          ..\n.      ....@....@*****..,***,////,******///,**,...****,...............\n  ........@,...,@***..,*&@@&@@,,./,***/..,&&&%@&*,..*(,.,...@.........\n..,,......&,...@&****/..,,*,,(////****////(,**,,. /**( .*...,(........\n..........@*&*.@/*******//////**,******,**/////******(..,... /........\n..........%(,**.**,,*******************************(#(...,**@........,\n........... @,,*.******.,***********************.*,**,.*,,*%.,,,,.....\n.............,%,. ... .***,******************,***..  . *.*. ..........\n..............,..,,. .. .***.*******************     .*., ............\n,(&@@&&&@&&&@@@,,*.*,,.. ./**,***,********.***. .. .*....,............\n,#@@@@@@@@@@@@@,&,,     .  ,,,...,,,,,,.  ,,*.. .   ....,,.,*****,,***\n..,,,%(/#%###%&./,,, .      ,,,,*,,,,,,*,,,,      ......,,..,,,,,,,,,,\n.,,**,**,***,.#,,&@,*, .   .,,,,,,,,,,,,,,,.    . /... ,(...,,,,,,,,,,\n .......,,  *//,,.,,@,,,,*,,,,,,,,,,,,,,,,,.... ,/,.,,.,*.. .,,,,,,,,,\n           #.&*,,,,,, #(,,,,,,,,,.,,,,.,,,,,,,,,(. ,,,,,,.. .,,,,,,,,,\n  *. .    .#.@,,,,,,,    @,,,,,,,,,,,,,,,,,,,,.   .,,,,&,.. .  ,,**...\n  %, ...  #..@,,,, ,,,      ,.,,,,,,,,,.,,.,      ,,,,,@,.. .         \n         *&,,@,,,,,,,,.                          ,,,,,*#,   .,      .,```']
simple_response = {"ya like jazz": "of course"}
death_messages = [' melted into the ground', ' clicked on a link for free robux', ' got sent to hell',
                  ' disappointed Ultron', ' was stepped on by big chungus', ' fell off the void', ' was tail slapped by an aboleth']


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

gratitude = as_list[9]
np = as_list[10]
shoot = as_list[11]
bully_maguire = as_list[12]
indeed = as_list[13]
uglag = as_list[14]
legends = as_list[15]
kings_canyon = as_list[16]
worlds_edge = as_list[17]
olympus = as_list[18]
storm_point = as_list[19]


def respond(msg, isDM):



    if msg in bully_maguire:
        rng = random.randint(0, len(bully_maguire) -1)
        return(bully_maguire[rng])        

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
        return f"{(ultron_quotes[rng])} \n{out}"

    elif "money" in msg and ("ultron" in msg or "bot" in msg):
        return("I have 69 doge coin")

    elif msg.startswith("deactivate") or "cya" in msg.split() or "good night" in msg:
        return("cya")

    elif ("i" in msg.split() or "me" in msg.split()) and "too" in msg.split():
        return("Interesting...")

    elif "gg" in msg.split() and "ultron" in msg:
        return("https://tenor.com/view/gg-ez-gg-ez-gg-noobs-gg-noob-gif-17962280")


    elif "random drop" in msg or (("where" in msg or "what" in msg) and "drop" in msg and "in" in msg):
        out = ""
        if "king" in msg or "kc" in msg:
            rng = random.randint(0, len(kings_canyon) - 1)
            out = kings_canyon[rng]
        elif "world" in msg:
            rng = random.randint(0, len(worlds_edge) - 1)
            out = worlds_edge[rng]
        elif "olympus" in msg:
            rng = random.randint(0, len(olympus) - 1)
            out = olympus[rng]
        elif "storm" in msg:
            rng = random.randint(0, len(storm_point) - 1)
            out = storm_point[rng]
        else:
            return("Specify a valid map and try again")
        return((f"Land at **{out}**"))
    
    else:
        command_list = get_commands()
        for j in command_list.keys():
            if j in msg.split() or (j in msg and " " in j):
                return(command_list[j])

    for i in simple_response.keys():
        if i in msg:
            return(simple_response[i])


    return "N/A"

# Following two functions were made with reference a tutorial from Code Palace on Youtube

def message_probability(user_message, recognized_words, required_words=[]):
    message_certainty = 0
    has_required_words = True

    for word in user_message:
        if word in recognized_words:
            message_certainty += 1

    percentage = float(message_certainty) / float(len(recognized_words))

    if len(required_words) > 0:
        for word in required_words:
            if word not in user_message:
                has_required_words = False
                break
    
    if has_required_words == True:
        return int(percentage * 100)
    return 0

def check_all_messages(message, author, direct=True):
    highest_prob_list = {}

    def response(bot_response, list_of_words, required_words=[]):
        nonlocal highest_prob_list
        highest_prob_list[bot_response] = message_probability(message, list_of_words, required_words)

    # prevent one letter responses

    if len(message) <= 1 and not direct:
        return None
    
    # Handles directly addressing Ultron

    if (direct == True):
        response(greetings[random.randint(0, len(greetings) - 1)] + author, activate)
        response(enchante[random.randint(0, len(enchante) - 1)], ['how', 'are', 'you', 'doing', 'going', 'goin'], required_words=['how'])
        response(np[random.randint(0, len(np) - 1)], ['thanks', 'thank', 'ty', 'gratitude', 'you', 'lot'])
        response("Thank you!", ['good', 'job', 'bot'])
        response(ball[random.randint(0, len(ball) - 1)], ['did', 'are', 'is', 'will', 'would', 'should', 'am', 'will', 'cant', 'wanna', 'do'])

    # Handles side convo or commands or reactions

    else:
        response(f"Your random legend is ** {legends[random.randint(0, len(legends) - 1)]}**", ['random', 'legend'], required_words=['random', 'legend'])
        response(sus[random.randint(0, len(sus) - 1)], ['sus', 'sussy', 'sushi', 'suspicious'])
        response(ai[random.randint(0, len(ai) - 1)], ['not', "isn't", "can't", 'bad', 'ai'], required_words=['ai'])

    best_match = max(highest_prob_list, key=highest_prob_list.get)
    print(highest_prob_list)

    out = str(highest_prob_list) + "\n"
    if highest_prob_list[best_match] >= 5:
        return(best_match)

def get_commands():
    command_list = {}
    with open("commands.txt", "r") as commands_in:
        if os.stat("commands.txt").st_size != 0:  # checks to see if file is empty
            for c in commands_in:
                (key, val) = c.split("* ")
                command_list[key] = val
    return command_list

# TODO: Make a generic get() function for these dictionaries, because they use the same code, just with different file names

def get_count():
    count = {}
    with open("count.txt", "r") as count_in:
        if os.stat("count.txt").st_size != 0:
            for i in count_in:
                if "*" in i:
                    (key, val) = i.split("* ")
                    val = val.replace("\n", "")
                else:
                    key = i
                    key = key.replace("\n", "")
                    val = ""
                count[key.lower()] = val
    return count

def get_apex_names():
    apex_names = {}
    with open("apex.txt", "r") as apex_in:
        if os.stat("apex.txt").st_size != 0:  # checks to see if file is empty
            for c in apex_in:
                if "*" in c:
                    (key, val) = c.split("* ")
                    val = val.replace("\n", "")
                else:
                    key = c
                    key = key.replace("\n", "")
                    val = ""
                apex_names[key.lower()] = val
    return apex_names

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


