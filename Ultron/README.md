# Ultron

```
Nihal Ernest
3 August 2022
```

A discord bot packed with memes and utilities. Designed to do perform a multitude of tasks on discord:
- Implements Server Chat Moderation, Calculator utilities
- Provides live updates of YouTube, Video game, and Weather statistics
- Text chat and music functionality


### Running

- This program can be run by opening and/or executing main.py.
- An internet connection is required to run this program locally.
- Program is currently deployed on Google Cloud Platform for centralized hosting and ease of access across multiple devices

### Files

- README.md	
- main.py	The main python file of the program to be executed. Contains all async function pertaining to the discord commands
- responses.py	Handles and contains the functions involving the bot's conversational functionalities
- utilities.py	Contains utility functions, including statistic retrieval

### Error Handling

- Installation of the required libraries is crucial for the bot to launch and function correctly.
- Certain commands may not function correctly if the required permissions are not granted when adding the bot to a server
- Note that all guild_ids, channel_ids, and bot_tokens have been hidden or removed in this version of the bot. Anyone who uses this code will need to generate a bot token through the discord development portal, and use their own guild or channel ids.
