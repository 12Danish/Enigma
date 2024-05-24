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

## Learning

