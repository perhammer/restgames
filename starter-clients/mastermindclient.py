#!/bin/python

import requests
import json

GAME_HOST = "localhost"
GAME_PORT = "8080"
GAME_INSTANCE = "mastermind"

API_KEY_GAME_ID = "gameId"
API_KEY_LINKS = "_links"
API_KEY_GUESS = "guess"
API_KEY_HINT = "hint"
API_KEY_CORRECT_COLOUR = "CORRECT_COLOUR"
API_KEY_CORRECT_COLOUR_AND_LOCATION = "CORRECT_COLOUR_AND_LOCATION"

gameId = 0;
links = {}
correctColours = 0
correctLocations = 0

def getGameBaseUrl():
	return "http://"+GAME_HOST+":"+GAME_PORT+"/"+GAME_INSTANCE

def makeRequestAndParseResponse(url):
	global gameId
	global links
	global correctColours
	global correctLocations

	resp = requests.get(url)
	if resp.ok:
		jData = json.loads(resp.content)
		gameId = jData[API_KEY_GAME_ID]

		correctColours = 0
		correctLocations = 0
		for hint in jData[API_KEY_HINT]:
			if str(hint)==API_KEY_CORRECT_COLOUR:
				correctColours+=1
			elif str(hint)==API_KEY_CORRECT_COLOUR_AND_LOCATION:
				correctLocations+=1

		links = {}
		for link in jData[API_KEY_LINKS]:
			links[str(link)]=jData[API_KEY_LINKS][str(link)]["href"]
	else:
		resp.raise_for_status()

def startNewGame():
	makeRequestAndParseResponse(getGameBaseUrl()+"/new")

def makeMove(guess):
	global links
	if API_KEY_GUESS in links:
		makeRequestAndParseResponse(links[API_KEY_GUESS]+guess)

def guess(guess):
	makeMove(guess)

startNewGame()
print("Game "+gameId+" started...")

while correctLocations!=4:
	guess("bbbb")
	break

print("Correct colour: "+str(correctColours)+", correct locations: "+str(correctLocations))

print("WON!")