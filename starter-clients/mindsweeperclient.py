#!/bin/python

import requests
import json
import random

GAME_HOST = "localhost"
GAME_PORT = "8080"
GAME_INSTANCE = "minesweeper"

API_KEY_GAME_ID = "gameId"
API_KEY_LINKS = "_links"
API_KEY_BOARD = "board"
API_KEY_BOARDVIEW = "boardView"
API_KEY_WIDTH = "width"
API_KEY_HEIGHT = "height"
API_KEY_FLAGS = "numberOfFlags"
API_KEY_MINES = "numberOfMines"

API_KEY_MARK = "mark"
API_KEY_UNMARK = "unmark"
API_KEY_REVEAL = "reveal"

gameId = 0;
links = {}

width = 0
height = 0
numberOfFlags = 0
numberOfMines = 0
boardView = []

def getGameBaseUrl():
	return "http://"+GAME_HOST+":"+GAME_PORT+"/"+GAME_INSTANCE

def makeRequestAndParseResponse(url):
	global gameId
	global links
	global width
	global height
	global numberOfFlags
	global numberOfMines
	global boardView

	print("Requesting "+url)
	resp = requests.get(url)
	if resp.ok:
		jData = json.loads(resp.content)
		gameId = jData[API_KEY_GAME_ID]

		width = jData[API_KEY_BOARD][API_KEY_WIDTH]
		height = jData[API_KEY_BOARD][API_KEY_HEIGHT]

		numberOfFlags = jData[API_KEY_BOARD][API_KEY_FLAGS]
		numberOfMines = jData[API_KEY_BOARD][API_KEY_MINES]

		boardView = []
		for viewRow in jData[API_KEY_BOARDVIEW]:
			row = []
			for c in viewRow:
				row.append(str(c))
			boardView.append(row)
		
		print(boardView)

		links = {}
		for link in jData[API_KEY_LINKS]:
			links[str(link)]=str(jData[API_KEY_LINKS][str(link)]["href"])
	else:
		resp.raise_for_status()

def startNewGame():
	makeRequestAndParseResponse(getGameBaseUrl()+"/new")

def makeMove(action, x, y):
	global links
	if action in links:
		link=links[action]
		if link.endswith("0/0"):
			link= link[0:len(link)-3]
		makeRequestAndParseResponse(link+str(x)+"/"+str(y))
	else:
		print("Action "+action+" not found in links:")
		print(links)

def mark(x, y):
	makeMove(API_KEY_MARK, x, y)

def unmark(x, y):
	makeMove(API_KEY_UNMARK, x, y)

def reveal(x, y):
	makeMove(API_KEY_REVEAL, x, y)

def gamble():
	x = random.randint(0, width)
	y = random.randint(0, height)
	reveal(x, y)

startNewGame()
print("Game "+gameId+" started...")
gamble()

