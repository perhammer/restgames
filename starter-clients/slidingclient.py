#!/bin/python

import requests
import json
import sys

GAME_HOST = "localhost"
GAME_PORT = "8080"
GAME_INSTANCE = "slide"

API_KEY_GAME_ID = "gameId"
API_KEY_BOARD = "board"
API_KEY_LINKS = "_links"

DIRECTION_NORTH = "NORTH"
DIRECTION_SOUTH = "SOUTH"
DIRECTION_EAST = "EAST"
DIRECTION_WEST = "WEST"

gameId = 0;
board = []
links = {}
lastMove = ""

def getGameBaseUrl():
	return "http://"+GAME_HOST+":"+GAME_PORT+"/"+GAME_INSTANCE

def makeRequestAndParseResponse(url):
	global gameId
	global board
	global links

	resp = requests.get(url)
	if resp.ok:
		jData = json.loads(resp.content)
		gameId = jData[API_KEY_GAME_ID]

		board = []
		boardRows = jData[API_KEY_BOARD][API_KEY_BOARD]
		for row in boardRows:
			myRow = []
			board.append(myRow)
			for element in row:
				myRow.append(str(element))

		links = {}
		for link in jData[API_KEY_LINKS]:
			links[str(link)]=jData[API_KEY_LINKS][str(link)]["href"]
	else:
		resp.raise_for_status()

def startNewGame():
	makeRequestAndParseResponse(getGameBaseUrl()+"/new")

def slideBlankTile(direction):
	global links
	global lastMove
	if direction in links:
		makeRequestAndParseResponse(links[direction])
		lastMove = direction

def makeMove():
	global links
	global lastMove

	if DIRECTION_NORTH in links and lastMove!=DIRECTION_SOUTH:
		slideBlankTile(DIRECTION_NORTH)
	elif DIRECTION_WEST in links and lastMove!=DIRECTION_EAST:
		slideBlankTile(DIRECTION_WEST)
	elif DIRECTION_SOUTH in links and lastMove!=DIRECTION_NORTH:
		slideBlankTile(DIRECTION_SOUTH)
	elif DIRECTION_EAST in links and lastMove!=DIRECTION_WEST:
		slideBlankTile(DIRECTION_EAST)

def printBoard():
	for row in board:
		print(row)

# startNewGame()
gameId=str(274490483420723862)
print("Game "+gameId+" joined...")
slideBlankTile(DIRECTION_WEST)
printBoard()

