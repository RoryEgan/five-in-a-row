import requests
import random
import string

addPlayerUrl = 'http://localhost:8080/players'
startGameUrl = 'http://localhost:8080/startGame'
makeMoveUrl = 'http://localhost:8080/makeMove'

s = requests.Session()
s.max_redirects = 10000


def prompt_move(player_id):
    move = str(input("It is your move, please enter a column: "))
    response = s.post(makeMoveUrl, json={'playerId': player_id, 'columnNumber': move})
    if response.status_code == 200:
        print_board_from_response(response)
        parsed_response = response.json()
        if parsed_response['winningPlayer'] is None:
            wait_for_move(player_id)
        else:
            print("GAME OVER, WINNING PLAYER: " + parsed_response['winningPlayer']['name'])
    elif response.status_code == 400:
        print("Invalid move, try again")
        prompt_move(player_id)
    else:
        print(str(response.body))


def opening_move(name, player_id):
    move = str(input("It is your move " + name + ", please enter a column: "))
    response = s.post(makeMoveUrl, json={'playerId': player_id, 'columnNumber': move})
    print_board_from_response(response)
    wait_for_move(player_id)


def print_board_from_response(response):
    parsed_response = response.json()
    board = [parsed_response['board']['grid']]
    for i in range(len(board)):
        for j in range(len(board[i])):
            print(board[i][j])


def wait_for_move(player_id):
    getGameStateUrl = 'http://localhost:8080/getGameState?playerId=' + player_id
    print("It is your opponents move, please wait")
    response = s.get(getGameStateUrl)
    print_board_from_response(response)
    if response.status_code == 200:
        parsed_response = response.json()
        if parsed_response['winningPlayer'] is None:
            prompt_move(player_id)
        else:
            print("GAME OVER, WINNING PLAYER: " + parsed_response['winningPlayer']['name'])


def main():
    name = str(input("Please enter your name: "))
    player_id = random.choice(string.ascii_letters)

    response1 = s.post(addPlayerUrl, json={'name': name, 'playerId': player_id})

    response2 = s.post(startGameUrl, json={'name': name, 'playerId': player_id})
    if response2.status_code == 201:
        print("Game started!")
        parsed_response = response2.json()
        if parsed_response['playerId'] == player_id:
            prompt_move(player_id)
        else:
            wait_for_move(player_id)


if __name__ == "__main__":
    main()
