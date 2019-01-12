#!/bin/python

import requests
import json

class RestApi(object):
	def __init__(self):
		self.HTTP_HEADER_CONTENT_TYPE = "Content-Type"
		self.HTTP_APPLICATION_JSON = "application/json"

		self.API_FIELD_TEAMNAME = "teamname"
		self.API_FIELD_TEAMPASS = "teampass"
		self.API_FIELD_TEAMDISPLAYNAME = "displayName"

		self.API_KEY_AUTHORIZATION = "Authorization"
		self.API_KEY_HREF = "href"
		self.API_KEY_GAME_ID = "gameId"
		self.API_KEY_LINKS = "_links"

		self.API_LOCATION_REGISTER = "/register"
		self.API_LOCATION_LOGIN = "/login"
		self.API_LOCATION_NEW = "/new"
		self.API_LOCATION_REJOIN = "/rejoin/"

class RestGameClient(RestApi):
	def __init__(self, hostname, port="80", gameName=""):
		super().__init__()
		self.authorization = ""
		self.hostname = hostname
		self.port = port
		self.gameName = gameName
		self.gameId = ""
		self.links = {}

	def getBaseUrl(self):
		return "http://"+self.hostname+":"+self.port

	def getGameBaseUrl(self):
		return self.getBaseUrl()+"/"+self.gameName


	def register(self, teamname, teampassword, displayname="We didn't specify one"):
		headers={self.HTTP_HEADER_CONTENT_TYPE: self.HTTP_APPLICATION_JSON}
		data={self.API_FIELD_TEAMNAME: teamname, self.API_FIELD_TEAMPASS: teampassword, self.API_FIELD_TEAMDISPLAYNAME: displayname}
		resp = requests.post(self.getBaseUrl()+self.API_LOCATION_REGISTER, data=json.dumps(data), headers=headers)
		if resp.status_code!=requests.codes.ok:
			resp.raise_for_status

	def login(self, teamname, teampassword):
		headers={self.HTTP_HEADER_CONTENT_TYPE: self.HTTP_APPLICATION_JSON}
		data={self.API_FIELD_TEAMNAME: teamname, self.API_FIELD_TEAMPASS: teampassword}
		resp = requests.post(self.getBaseUrl()+self.API_LOCATION_LOGIN, data=json.dumps(data), headers=headers)


		if resp.status_code!=requests.codes.ok:
			resp.raise_for_status

		self.authorization = str( resp.headers[self.API_KEY_AUTHORIZATION] )

	def makeRequestAndParseResponse(self, url):
		headers = {self.API_KEY_AUTHORIZATION: self.authorization}

		resp = requests.get(url, headers=headers)
		if resp.ok:
			jData = json.loads(resp.content)
			self.gameId = jData[self.API_KEY_GAME_ID]

			self.links = {}
			for link in jData[self.API_KEY_LINKS]:
				self.links[str(link)]=jData[self.API_KEY_LINKS][str(link)][self.API_KEY_HREF]
		else:
			resp.raise_for_status()

		return jData

	def startNewGame(self):
		self.makeRequestAndParseResponse(self.getGameBaseUrl()+self.API_LOCATION_NEW)
		print("Game "+self.gameId+" started...")

	def rejoinGame(self, gameIdToJoin):
		self.makeRequestAndParseResponse(self.getGameBaseUrl()+self.API_LOCATION_REJOIN+gameIdToJoin)
		print("Game "+self.gameId+" rejoined...")
