#!/bin/python

import requests
import json

GAME_HOST = "localhost"
GAME_PORT = "8080"
GAME_INSTANCE = "mastermind"

HTTP_HEADER_CONTENT_TYPE = "Content-Type"
HTTP_APPLICATION_JSON = "application/json"

API_FIELD_TEAMNAME = "teamname"
API_FIELD_TEAMPASS = "teampass"
API_FIELD_TEAMDISPLAYNAME = ""
API_KEY_AUTHORIZATION = "Authorization"
API_KEY_GAME_ID = "gameId"
API_KEY_LINKS = "_links"
API_KEY_GUESS = "guess"
API_KEY_HINT = "hint"
API_KEY_CORRECT_COLOUR = "CORRECT_COLOUR"
API_KEY_CORRECT_COLOUR_AND_LOCATION = "CORRECT_COLOUR_AND_LOCATION"

authorization = ""
gameId = 0;
links = {}
correctColours = 0
correctLocations = 0

def getBaseUrl():
	return "http://"+GAME_HOST+":"+GAME_PORT+"/"

def getGameBaseUrl():
	return getBaseUrl()+GAME_INSTANCE

def makeRequestAndParseResponse(url):
	global gameId
	global links
	global correctColours
	global correctLocations
	global authorization

	headers = {API_KEY_AUTHORIZATION: authorization}

	resp = requests.get(url, headers=headers)
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
	global gameId
	makeRequestAndParseResponse(getGameBaseUrl()+"/new")
	print("Game "+gameId+" started...")

def makeMove(guess):
	global links
	if API_KEY_GUESS in links:
		makeRequestAndParseResponse(links[API_KEY_GUESS]+guess)
	else:
		raise RuntimeError("API doesn't allow guesses at this point.")

def rejoinGame(gameIdToJoin):
	global gameId
	makeRequestAndParseResponse(getGameBaseUrl()+"/rejoin/"+gameIdToJoin)
	print("Game "+gameId+" rejoined...")

def guess(guess):
	global correctColours
	global correctLocations
	print("Guessing: ["+guess+"]")
	makeMove(guess)
	print("Correct colours: "+str(correctColours))
	print("Correct locations: "+str(correctLocations))
	if correctLocations==4:
		print("You guessed the correct combination!")

def register(teamname, teampassword):
	headers={HTTP_HEADER_CONTENT_TYPE: HTTP_APPLICATION_JSON}
	data={API_FIELD_TEAMNAME: teamname, API_FIELD_TEAMPASS: teampassword}
	resp = requests.post(getBaseUrl()+"register", data=json.dumps(data), headers=headers)

def login(teamname, teampassword):
	global authorization
	headers={HTTP_HEADER_CONTENT_TYPE: HTTP_APPLICATION_JSON}
	data={API_FIELD_TEAMNAME: teamname, API_FIELD_TEAMPASS: teampassword}
	resp = requests.post(getBaseUrl()+"login", data=json.dumps(data), headers=headers)
	authorization = str( resp.headers[API_KEY_AUTHORIZATION] )
	
# register("teamOne", "onePass")
login("teamOne", "onePass")
# startNewGame()
rejoinGame("1005095005")


guess("obgo")


# while correctLocations!=4:
# 	guess("b")
# 	break

# print("Correct colour: "+str(correctColours)+", correct locations: "+str(correctLocations))

# print("WON!")