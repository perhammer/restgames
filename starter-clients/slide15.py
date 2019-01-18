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
		self.lastMove = ""

	def slideBlankTile(self, direction):
		if direction in self.links:
			jData = self.makeRequestAndParseResponse(self.links[direction])

			self.board = []
			boardRows = jData[self.API_KEY_BOARD][self.API_KEY_BOARD]
			for row in boardRows:
				myRow = []
				self.board.append(myRow)
				for element in row:
					myRow.append(str(element))

			self.lastMove = direction
		else:
			raise RuntimeError("API doesn't allow moving "+direction+" at this point.")


	def makeRandomMove(self):
		if self.DIRECTION_NORTH in self.links and self.lastMove!=self.DIRECTION_SOUTH:
			self.slideBlankTile(self.DIRECTION_NORTH)
		elif self.DIRECTION_WEST in self.links and self.lastMove!=self.DIRECTION_EAST:
			self.slideBlankTile(self.DIRECTION_WEST)
		elif self.DIRECTION_SOUTH in self.links and self.lastMove!=self.DIRECTION_NORTH:
			self.slideBlankTile(self.DIRECTION_SOUTH)
		elif self.DIRECTION_EAST in self.links and self.lastMove!=self.DIRECTION_WEST:
			self.slideBlankTile(self.DIRECTION_EAST)

	def printBoard(self):
		for row in self.board:
			print(row)

	def slideBlankTile(self, direction):
		if direction in self.links:
			jData = makeRequestAndParseResponse(self.links[direction])

			
			lastMove = direction
		else:
			raise RuntimeError("API doesn't allow sliding "+direction+" at this point.")


slide = SlideGame("joshua-env.sj5nm3sr7a.us-east-2.elasticbeanstalk.com")

# # slide.register("username", "password", "I'm the first user")
slide.login("username", "password")
# # slide.startNewGame()
slide.rejoinGame("755494111")
slide.makeRandomMove()
slide.printBoard()
