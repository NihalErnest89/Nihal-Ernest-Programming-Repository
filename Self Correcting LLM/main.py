import time
from bs4 import BeautifulSoup
from openai import OpenAI
from prompts import DEFAULT_PROMPT, EXTRA_BEHAVIOR, SCRAPING_PROMPT
from dotenv import load_dotenv
import os


from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.chrome.options import Options



load_dotenv()


# Set up Selenium WebDriver
chrome_driver_path = (
    os.getenv("CHROME_DRIVER")
)

# Set up the Chrome driver
service = Service(chrome_driver_path)
options = webdriver.ChromeOptions()
options.add_argument(
    "--headless"
)  # Run in headless mode to not open a browser window
driver = webdriver.Chrome(service=service, options=options)
client = OpenAI(base_url="http://localhost:1234/v1", api_key="lm-studio")

system_prompt = DEFAULT_PROMPT + "\n\n" + EXTRA_BEHAVIOR

def getFeaturedSnippet(query):
    # Perform a Google search
    search_query = query
    driver.get(f'https://www.google.com/search?q={search_query}')

    # Wait for the results to load
    # time.sleep(5)  # Adjust as necessary

    # Get the page source and parse it with BeautifulSoup
    soup = BeautifulSoup(driver.page_source, 'html.parser')

    # Initialize an empty string to store the formatted text
    formatted_text = ''


    # Iterate over the tags of interest
    for tag in soup.find_all(['h1', 'h2', 'h3', 'span', 'p']):
        if tag.name == 'h1':
            formatted_text += f'\n\n{tag.get_text()}\n' + '-' * len(tag.get_text()) + '\n'
        elif tag.name == 'h2':
            formatted_text += f'\n\n{tag.get_text()}\n' + '=' * len(tag.get_text()) + '\n'
        elif tag.name == 'h3':
            formatted_text += f'\n\n{tag.get_text()}\n' + '~' * len(tag.get_text()) + '\n'
        elif tag.name == 'span':
            formatted_text += f'{tag.get_text()}\n'
        elif tag.name == 'p':
            formatted_text += f'{tag.get_text()}\n\n'

    # Print the formatted_text to a file
    with open('output.txt', 'w', encoding='utf-8') as file:
        file.write(formatted_text)

    # Close the WebDriver
    driver.quit()

    return formatted_text


# Scrapes weather from google.
# Currently hardcoded, will change to dynamic
def scrape_weather(location):
    search_url = f"https://www.google.com/search?q=weather+in+{location}"
    # print(f"searching at {search_url}")
    driver.get(search_url)

    try:
        current_temp = driver.find_element(By.ID, "wob_tm").text
        condition = driver.find_element(By.ID, "wob_dc").text
        humidity = driver.find_element(By.ID, "wob_hm").text
        precipitation = driver.find_element(By.ID, "wob_pp").text
        wind = driver.find_element(By.ID, "wob_ws").text
        time = driver.find_element(By.ID, "wob_dts").text

        weather_info = f"Current temperature: {current_temp}°F\nConditions: {condition}\nHumidity: {humidity}\nPrecipitation: {precipitation}\nWind Speed: {wind}\nTime: {time}"


        return weather_info
    except Exception as e:
        return f"Error: Couldn't retrieve weather information: {e}"

# Checks if the LLM tried to web search
def checkForCommands(message):
    print(f"Performing search ({message})...")
    response = getLlama(message)
    index_search = response.lower().find("search: ")


    if (response.lower().startswith("weather:")):
        weather_summary = scrape_weather(response[9:])
        llm_summary = getLlama(f"Give the weather in a concise, natural way. At most 3 sentences. convert the following into a natural language weather report:\n{weather_summary}", temp=0.5)
        response = llm_summary
    elif (index_search != -1):
        query = response[index_search + len("search: "):].strip()
        result_summary = getFeaturedSnippet(query)
        llm_summary = getLlama(f"The search query was {query}. \n\nThe webpage for you to scan for an accurate answer is:\n{result_summary}.", sys=SCRAPING_PROMPT, temp=0.5)
        response = llm_summary
    
    return response

# Handles LLM input and output
def getLlama(user_prompt, sys=system_prompt, temp=-1):
    final_temp = 0.4
    if temp >= 0:
        final_temp = temp

    # truncate
    temp_prompt = user_prompt
    if len(temp_prompt) > 1000:
        temp_prompt = temp_prompt[:1000]
    # Add the user prompt to the message history

    # Limit history to the last 20 messages
    
    # Add system message with the history
    messages = [{"role": "system", "content": sys}]
    messages.append({"role": "user", "content": user_prompt})

    completion = client.chat.completions.create(
        model="lmstudio-community/Meta-Llama-3.1-8B-Instruct-GGUF",
        messages=messages,
        temperature=final_temp,
    )
    out_message = completion.choices[0].message.content

    return out_message

time.sleep(2)

prompt = "test"
while prompt:
    prompt = input("User: ")

    response = checkForCommands(prompt)

    print(f"Ultron: {response}")