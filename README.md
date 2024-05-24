# Engima

Enigma, our maze-solving appliction, is designed to tackle seemingly unsolvable mazes. It intelligently calculates its moves, determining when and how many times to rotate the maze by 90 degrees at specific points. Both the ball and maze movements are visualized, and upon reaching the destination, a graph is generated to display the chosen path.

## Algorithm

The program traverses all possible nodes using a BFS algorithm. It moves from the starting point to all available nodes and adds these nodes in a spearate data structure for rotation.
Once there are no possible nodes available in a particular configuration of the maze, it is rotated.

The established nodes nodes are checked after rotations for three times before they are dropped as this encompasses all the possible future nodes that can be obtained from them.
The program continues to establish new nodes in all possible configurations until either all the nodes are explored or the destination is found. 

## Dependencies
- Java
- Java Swing
- Java AWT

## Setup Instructions

## Directory Structure

## Interface

**Home Screen**
!(Home Screen)[https://github.com/12Danish/Engima/blob/main/assets/HomePage.gif]

**Options Screen**
!(Options Screen)[https://github.com/12Danish/Engima/blob/main/assets/Screenshot%202024-05-24%20133834.gif]

**Sample Maze**
!(Sample Maze)[https://github.com/12Danish/Engima/blob/main/assets/example-maze.gif]

**Sample Graph**
!(Sample Graph)[https://github.com/12Danish/Engima/blob/main/assets/graph-example.jpg]

**Own Maze**
![Create](https://github.com/12Danish/Engima/blob/main/assets/create-maze.jpg)
![Created-Maze](https://github.com/12Danish/Engima/blob/main/assets/maze-created.jpg)

**Demo**
In order to view the demo please check out this link:

!(link)[https://www.linkedin.com/feed/update/urn:li:activity:7199501438249496577/]

Or you can follow the setup instructions and have it run on your machine.

## Learning

This project gave me a firm undertsanding about graphs and their traversal using Breadth First Search. It sharpened our problem solving skills to identify solutions which might not be obvious.
Moreover, it taught us to visualize our solutions, allowing us to present our algorithm and unique approach effectively.

