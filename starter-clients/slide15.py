#!/bin/python

from restgameclient import RestGameClient

class SlideApi(object):
	def __init__(self):
		selv.API_KEY_BOARD = "board"
		self.DIRECTION_NORTH = "NORTH"
		self.DIRECTION_SOUTH = "SOUTH"
		self.DIRECTION_EAST = "EAST"
		self.DIRECTION_WEST = "WEST"

class SlideGame(RestGameClient, SlideApi):
	def __init__(self, hostname, port="8080"):
		super().__init__(hostname, port, gameName="slide")
		SlideApi.__init__(self)

slide = SlideGame("localhost")

# # slide.register("username", "password", "I'm the first user")
slide.login("username", "password")
# # slide.startNewGame()
slide.rejoinGame("755494111")
slide.gamble()

