#!/bin/python

from restgameclient import RestGameClient

class MinesweeperApi(object):
	def __init__(self):
		self.API_KEY_BOARD = "board"
		self.API_KEY_BOARDVIEW = "boardView"
		self.API_KEY_WIDTH = "width"
		self.API_KEY_HEIGHT = "height"
		self.API_KEY_FLAGS = "numberOfFlags"
		self.API_KEY_MINES = "numberOfMines"

		self.API_KEY_MARK = "mark"
		self.API_KEY_UNMARK = "unmark"
		self.API_KEY_REVEAL = "reveal"

class MinesweeperGame(RestGameClient, MinesweeperApi):
	def __init__(self, hostname, port="8080"):
		super().__init__(hostname, port, gameName="minesweeper")
		MinesweeperdApi.__init__(self)

		self. width = 0
		self. height = 0
		self.numberOfFlags = 0
		self.numberOfMines = 0
		self.boardView = []

	def makeMove(self, action, x, y):
		if action in self.links:
			link=links[action]
			if link.endswith("0/0"):
				link= link[0:len(link)-3]
			jData = self.makeRequestAndParseResponse(link+str(x)+"/"+str(y))

			self.width = jData[self.API_KEY_BOARD][self.API_KEY_WIDTH]
			self.height = jData[self.API_KEY_BOARD][self.API_KEY_HEIGHT]

			self.numberOfFlags = jData[self.API_KEY_BOARD][self.API_KEY_FLAGS]
			self.numberOfMines = jData[self.API_KEY_BOARD][self.API_KEY_MINES]

			self.boardView = []
			for viewRow in jData[API_KEY_BOARDVIEW]:
				row = []
				for c in viewRow:
					row.append(str(c))
				self.boardView.append(row)
			
			print(self.boardView)

		else:
			raise RuntimeError("API doesn't allow "+action+" at this point.")

	def mark(self, x, y):
		self.makeMove(self.API_KEY_MARK, x, y)

	def unmark(self, x, y):
		self.makeMove(self.API_KEY_UNMARK, x, y)

	def reveal(self.x, y):
		self.makeMove(self.API_KEY_REVEAL, x, y)

	def gamble(self):
		x = random.randint(0, self.width)
		y = random.randint(0, self.height)
		self.reveal(x, y)

msweeper = MinesweeperGame("localhost")

# # msweeper.register("username", "password", "I'm the first user")
msweeper.login("username", "password")
# # msweeper.startNewGame()
msweeper.rejoinGame("755494111")
msweeper.gamble()
