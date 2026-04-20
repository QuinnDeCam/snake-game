# snake-game

## Prompt 1 
" I'm building a Snake game in Java using Swing. Create a single file called SnakeGame.java. It should have a main method that opens a JFrame window that is 600 by 600 pixels and titled Snake. Inside the frame, add a JPanel subclass called GamePanel. Do not add any game logic yet. Just get the window to open correctly. "
## Result: 
AI created a main method that opens a 600×600 JFrame titled "Snake" and adds a GamePanel JPanel subclass. When run, successfully added a blank window 

## Prompt 2 
Now extend SnakeGame.java. Keep it as one file. Add a dark background grid and draw a starting snake that is three segments long near the center of the board, facing right. Each cell should be a 30x30 pixel square. Draw the snake in green and the background in dark gray. Do not add movement yet.
## Result: 
AI created a green 3 segmented Snake succesfully.

## Prompt 3 
Make the snake move automatically using a Swing timer that ticks every 150 milliseconds. Add arrow key controls so the player can steer, but don't allow the snake to reverse direction. For now, have the snake wrap around the edges instead of dying. Make sure the panel can receive keyboard input.
## Result: 
Failed to make the snake move. The snake did not move, and stayed in the same place from before prompt 2. There was an error regarding the timer, and it's reference. 
Went back to file before prompt 3 was attempted, and it was successful after a second attempt.
Snake moved on it's own, and wrapped around the screen's edges, and was able to be controled with arrow keys

## Prompt 4 
Add a food pellet that spawns at a random empty cell. When the snake eats it, grow by one segment and spawn new food. Add collision detection: hitting a wall or the snake's own body should end the game, stop movement, and show a "Game Over" message with the final score. Display the current score in the top-left corner during play. When the game is over, let the player press R to reset everything and play again.
## Result: 
Successfully spawns food squares in random empty cells (drawn in red at first). Also added Eating/Growing mechanism, where Snake grows by one segment when eating food, score increases by 10.
Successful collision detection where game ends on hitting walls or the snake's own body (no more wrapping)
Score is displayed in top-left corner during play
and a Game Over screen was added, Black overlay with final score and instructions with an option of pressing R to play again.

## Prompt 5
Add a start screen, that is a dark color with green text that displays a start key, as well as a high score saved. When the "Game Over" message is displayed, have an option to go back to the start screen.
## Result: 
result was the same normal start, when played died and game over screen was shown, an option to go to the start screen/ menu was added when S was pressed. Start screen showed a saved high score. 

## Prompt 6
Can the start screen be the very first thing you see when run?
## Result: 
started game with start screen successfully

## Prompt 7
Add a start screen, that is a dark color with green text that displays a start key, as well as a high score saved. When the "Game Over" message is displayed, have an option to go back to the start screen.
## Result: 
result was the same normal start, when played died and game over screen was shown, an option to go to the start screen/ menu was added when S was pressed. Start screen showed a saved high score. 
