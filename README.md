# GUTS Hacker Olympics // RestGames

# Challenges

| Challenge | Description | Points |
|-----------|-------------|--------|
| Minimum Viable| Registered a team account | 10 |
| Dipping A Toe | Attempted a game | 10 |
| WINNING       | Completed a game | 40 |
| Best In Class | Gold, Silver, Bronze highest score or shortest completion time in each game | 150, 100, 50 |
| Tenacious, Not Stubborn | Most moves or longest completion time for successfully completing each game | 50 |
| First Principles | Completing a game by using only Postman or Rested (or similar) | 100 |
| Solving @ Scale | Writing an automatic solver for a game | 250 |

## Connection details
The Game Service is running at joshua-env.sj5nm3sr7a.us-east-2.elasticbeanstalk.com.

## Registering and Logging In
1. POST to the /register end-point with 'teamname' and 'teampass' parameters
1. POST to the /login end-point with the same 'teamname' and 'teampass' parameters, and store the 'Authorization' header for future requests.

All the payloads have to be JSON with a content-type of application/json.

## Available Games
1. Mastermind is available at /mastermind
1. Minesweeper is available at /minesweeper
1. Slide-15 is available at /slide
1. 2048 is available at /2048

## Starting a new game
1. GET the game URL with /new. The response should tell you what to do next
1. Make sure you store the game id in case you need to reconnect to the game later!

## Rejoining an existing game
1. If you need to reconnect to an existing game, GET the game URL with /rejoin/1234 (1234 being the game id)

## How To Play
1. You can hand-craft all the requests and use CURL, type them into a browser, or whatever
1. You can also use a plug-in such as [RESTED](https://addons.mozilla.org/en-US/firefox/addon/rested/), or [Postman](https://www.getpostman.com/)
1. Or, write your own. Sample Python clients are available in [this repo](https://github.com/perhammer/restgames/tree/master/starter-clients)
