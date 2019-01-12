#!/bin/python

from restgameclient import RestGameClient

class SlideApi(object):
	def __init__(self):
		self.API_KEY_BOARD = "board"
		self.DIRECTION_NORTH = "NORTH"
		self.DIRECTION_SOUTH = "SOUTH"
		self.DIRECTION_EAST = "EAST"
		self.DIRECTION_WEST = "WEST"

class SlideGame(RestGameClient, SlideApi):
	def __init__(self, hostname, port="80"):
		super().__init__(hostname, port, gameName="slide")
		SlideApi.__init__(self)

	def slideBlankTile(self, direction):
		if direction in self.links:
			jData = makeRequestAndParseResponse(self.links[direction])

			
			lastMove = direction
		else:
			raise RuntimeError("API doesn't allow sliding "+direction+" at this point.")


slide = SlideGame("joshua-env.sj5nm3sr7a.us-east-2.elasticbeanstalk.com")

# # slide.register("username", "password", "I'm the first user")
slide.login("username", "password")
slide.startNewGame()
# slide.rejoinGame("131450024")
# slide.gamble()

