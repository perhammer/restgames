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
