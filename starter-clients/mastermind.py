#!/bin/python

from restgameclient import RestGameClient

class MastermindApi(object):
	def __init__(self):
		self.API_KEY_GUESS = "guess"
		self.API_KEY_HINT = "hint"
		self.API_KEY_CORRECT_COLOUR = "CORRECT_COLOUR"
		self.API_KEY_CORRECT_COLOUR_AND_LOCATION = "CORRECT_COLOUR_AND_LOCATION"	

class MastermindGame(RestGameClient, MastermindApi):
	def __init__(self, hostname, port="80"):
		super().__init__(hostname, port, gameName="mastermind")
		MastermindApi.__init__(self)

		self.correctColours = 0
		self.correctLocations = 0

	def makeMove(self, guess):
		if self.API_KEY_GUESS in self.links:
			jData = self.makeRequestAndParseResponse(self.links[self.API_KEY_GUESS]+guess)

			self.correctColours = 0
			self.correctLocations = 0
			for hint in jData[self.API_KEY_HINT]:
				if str(hint)==self.API_KEY_CORRECT_COLOUR:
					self.correctColours+=1
				elif str(hint)==self.API_KEY_CORRECT_COLOUR_AND_LOCATION:
					self.correctLocations+=1
		else:
			raise RuntimeError("API doesn't allow guesses at this point.")

	def guess(self, guess):
		print("Guessing: ["+guess+"]")
		self.makeMove(guess)
		print("Correct colours: "+str(self.correctColours))
		print("Correct locations: "+str(self.correctLocations))
		if self.correctLocations==4:
			print("You guessed the correct combination!")


mmind = MastermindGame("joshua-env.sj5nm3sr7a.us-east-2.elasticbeanstalk.com")

mmind.register("username", "password", "I'm the first user")
mmind.login("username", "password")
mmind.startNewGame()
# mmind.rejoinGame("755494111")

# Colours are: Blue, Green, Yellow, Pink, Orange, Red
mmind.guess("bgpy")
