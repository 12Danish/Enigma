# Enigma

Enigma, our maze-solving appliction, is designed to tackle seemingly unsolvable mazes. It intelligently calculates its moves, determining when and how many times to rotate the maze by 90 degrees at specific points. Both the ball and maze movements are visualized, and upon reaching the destination, a graph is generated to display the chosen path.

## Features:
- Solves sample mazes
- Solves custom mazes provided by users.
- Generates graph
  **Note** : If there is no possible solution to the maze even after rotations the program will not proceed forward with generation of
  maze and graph. In this case please re-define the walls provided.
  
## Algorithm

The program traverses all possible nodes using a BFS algorithm. It moves from the starting point to all available nodes and adds these nodes in a spearate data structure for rotation.
Once there are no possible nodes available in a particular configuration of the maze, it is rotated.

The established nodes nodes are checked after rotations for three times before they are dropped as this encompasses all the possible future nodes that can be obtained from them.
The program continues to establish new nodes in all possible configurations until either all the nodes are explored or the destination is found. 

## Dependencies
- Java
- Java Swing
- Java AWT

## Requirements

- Java Development Kit (JDK) version "21" 2023-09-19 or later
- OpenJDK Runtime Environment (build 21+35-2513)
- OpenJDK 64-Bit Server VM (build 21+35-2513, mixed mode, sharing)
    
## Setup Instructions
- Pull the git code from the repository
 
To run the Maze Solver program, follow these steps:

- Navigate to the `src\Main\` directory in your terminal or command prompt:
   ```bash
   cd src\Main\
   ```
- Open MazeSolverHomePage.java:
                                - VS code :
                                           Right click and select run java.

  
                                           ![run vs](https://github.com/12Danish/Enigma/blob/main/assets/run.jpg)
                                - IntelliJ:
                                           Select run from the top bar.

  
                                           ![run intelli](https://github.com/12Danish/Enigma/blob/main/assets/run-intelli.jpg)
                                       
  
 

## Directory Structure
- src
  - Main:
     - MazeSolverHomePage.java : The main file which should be run to run the project.
     - ThemeSelectionPage.java : Contains code for the options screen.
  - Algo:
        Contains files necessary for the working of algorithm
  - CustomMaze:
                Contains files for custom maze functionality
  - MazeUI:
            Contains files to handle maze rotation and ball movement
  - Tracker:
            This handles graph logic
  - Resources:
              This contains necessary assets like images.
  

## Interface

**Home Screen**
![Home Screen](https://github.com/12Danish/Engima/blob/main/assets/HomePage.gif)

**Options Screen**
![Options Screen](https://github.com/12Danish/Engima/blob/main/assets/Screenshot%202024-05-24%20133834.gif)

**Sample Maze**

![Sample Maze](https://github.com/12Danish/Engima/blob/main/assets/example-maze.gif)

**Sample Graph**

![Sample Graph](https://github.com/12Danish/Engima/blob/main/assets/graph-example.jpg)

## Custom Maze

In the custom maze window first decide the size of the maze. Afterwards fill each cell with numbers between 0 and 15. Keep the starting point as -1 and the ending point as -2. If there is no solution then the program will not run and will not generate a maze or a graph.
In this case try changing the walls configuration(cell numbers) around the ending and starting point. This can happen if you completely
shut off exit and entry from these.

### Maze Wall Representation

In this maze representation:
- 1 indicates the presence of a wall.
- 0 indicates the absence of a wall.
- The four bits represent walls in the following order: North, West, South, East.

For example, `1111` means there are walls on the North, West, South, and East sides.

## Binary Representation

| Number | Binary | Walls          |
|--------|--------|----------------|
| 0      | 0000   | No walls       |
| 1      | 0001   | East wall      |
| 2      | 0010   | South wall     |
| 3      | 0011   | South, East walls |
| 4      | 0100   | West wall      |
| 5      | 0101   | West, East walls |
| 6      | 0110   | West, South walls |
| 7      | 0111   | West, South, East walls |
| 8      | 1000   | North wall     |
| 9      | 1001   | North, East walls |
| 10     | 1010   | North, South walls |
| 11     | 1011   | North, South, East walls |
| 12     | 1100   | North, West walls |
| 13     | 1101   | North, West, East walls |
| 14     | 1110   | North, West, South walls |
| 15     | 1111   | North, West, South, East walls |


![Create](https://github.com/12Danish/Engima/blob/main/assets/create-maze.jpg)
![Created-Maze](https://github.com/12Danish/Engima/blob/main/assets/maze-created.jpg)

## Demo
In order to view the demo please check out this link:

(https://www.linkedin.com/feed/update/urn:li:activity:7199501438249496577/)

Or you can follow the setup instructions and have it run on your machine.

## Learning

This project gave me a firm undertsanding about graphs and their traversal using Breadth First Search. It sharpened our problem solving skills to identify solutions which might not be obvious.
Moreover, it taught us to visualize our solutions, allowing us to present our algorithm and unique approach effectively.

### Small Bugs

- When a maze is generated it produces a small window please resize to full to see the complete maze
- Sometimes the last or top rows of the maze exceed screen size and are not displayed. Try with some of the smaller mazes to have the full maze in view
- In some windows the close window option causes the whole program to shut down. Please minimise windows instead of closing them.
  
