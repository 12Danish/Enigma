package Algo;

import java.util.*;

public class MazeSolver {

    int[][] maze;
    BaseNode start;
    ArrayDeque<DetailedNode> queue = new ArrayDeque<>();
    BaseNode end;
    HashSet<BaseNode> visited = new HashSet<>();
    String result = new String();
    ArrayDeque<DetailedNode> rotationNodes = new ArrayDeque<>();
    boolean reachedGoal = false;
    String[] directions = { "N", "W", "S", "E" };
    int[] wallBits = { 8, 4, 2, 1 };
    List<String> exploredPaths = new ArrayList<>();

    public static void main(String[] args) {
        int[][] maze = {
                { 9, 1, 9, 0, 13, 0 },
                { 14, 1, 11, 2, 11, 4 },
                { -1, 2, 11, 0, 0, 15 },
                { 4, 3, 9, 6, 3, -2 }
        };
        MazeSolver m = new MazeSolver(maze);
        System.err.println(m.solve());
        System.out.println("Explored paths: " + m.getExploredPaths());
    }

    public MazeSolver(int[][] maze) {
        // Create a deep copy of the maze array

        this.maze = new int[maze.length][];
        for (int i = 0; i < maze.length; i++) {
            this.maze[i] = Arrays.copyOf(maze[i], maze[i].length);
        }
        this.start = findStartPosition(maze);
        variablePositions(this.maze, this.start.getFirstCoordinate(), this.start.getSecondCoordinate()); // Updating the
                                                                                                         // maze
        this.end = findEndPosition(maze);
        variablePositions(this.maze, this.end.getFirstCoordinate(), this.end.getSecondCoordinate()); // Updating the
                                                                                                     // maze
        this.queue.add(new DetailedNode(this.start.getFirstCoordinate(), this.start.getSecondCoordinate()));
    }

    public final List<String> solve() {
        this.result = null;

        // This loop runs until the destination is found or all the possible nodes in
        // all rotations have been explored
        while (!this.queue.isEmpty() && !reachedGoal) {
            // popping the current
            DetailedNode current = this.queue.poll();
            int a = current.getFirstCoordinate();
            int b = current.getSecondCoordinate();
            visited.add(current);

            for (int k = 0; k < directions.length; k++) {
                String direction = directions[k];
                int wallBit = wallBits[k];
                if ((maze[a][b] & wallBit) == 0) {
                    BaseNode next = move(a, b, direction);
                    int na = next.getFirstCoordinate();
                    int nb = next.getSecondCoordinate();
                    if (na == end.getFirstCoordinate() && nb == end.getSecondCoordinate()) {
                        // Terminating the function once 'X' is reached.
                        this.reachedGoal = true;
                        this.result = current.getPath() + direction;
                        break;

                    } else if ((na >= 0 && na < maze.length) && (nb >= 0 && nb < maze[0].length)
                            && (!this.visited.contains(next))
                            && (this.maze[na][nb] & oppositeDirection(direction)) == 0) {

                        String newPath;
                        if (current.getPath().isEmpty()) {
                            newPath = direction;
                        } else {
                            newPath = current.getPath() + direction;
                        }
                        queue.add(new DetailedNode(na, nb, newPath));
                        exploredPaths.add(newPath); // Store the explored path
                    }
                }
            }

            // Trying to accumulate the nodes where rotation should happen
            if (!this.reachedGoal) {
                rotationNodes.add(current);
                // This will run if the queue has been exhausted, and it will attempt to
                // repopulate it after rotation
                if (this.queue.isEmpty()) {
                    // rotating the maze
                    for (int i = 0; i < this.maze.length; i++) {
                        for (int j = 0; j < this.maze[0].length; j++) {
                            int rotatedCell = rotateCell(this.maze[i][j]);
                            updateCell(this.maze, rotatedCell, i, j);
                        }
                    }

                    for (DetailedNode node : this.rotationNodes) {
                        if (node.getRotations() < 3) {
                            node.extendPath(" ");
                            node.incrementRotations();
                            this.queue.add(node);
                        }
                    }

                    this.rotationNodes.clear();
                }
            }
        }

        // Getting the shortest available path
        if (this.result != null) {
            return convertToList(this.result);
        } else {
            return null;
        }
    }

    // tested
    public final BaseNode findStartPosition(int[][] maze) {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                if (maze[i][j] == -1) {
                    return new BaseNode(i, j);
                }
            }
        }
        return null;
    }

    // tested
    public final BaseNode findEndPosition(int[][] maze) {
        for (int p = 0; p < maze.length; p++) {
            for (int q = 0; q < maze[0].length; q++) {
                if (maze[p][q] == -2) {
                    return new BaseNode(p, q);
                }
            }
        }
        return null;
    }

    // tested
    public final void variablePositions(int[][] maze, int a, int b) {
        maze[a][b] = 0;
    }

    // tested
    public final BaseNode move(int a, int b, String direction) {
        if (direction == "N") {
            return new BaseNode(a - 1, b);
        }
        if (direction == "W") {
            return new BaseNode(a, b - 1);
        }
        if (direction == "S") {
            return new BaseNode(a + 1, b);
        }
        if (direction == "E") {
            return new BaseNode(a, b + 1);
        }
        return null; // Handle invalid direction
    }

    // tested
    public final int oppositeDirection(String direction) {
        switch (direction) {
            case "N":
                return 2;
            case "W":
                return 1;
            case "S":
                return 8;
            case "E":
                return 4;
            default:
                return -1; // Handle invalid direction
        }
    }

    // tested
    public final int rotateCell(int cellValue) {
        int shiftedNum = cellValue << 1;
        if (cellValue > 7) {
            shiftedNum = shiftedNum - 16;
            shiftedNum += 1;
        }
        return shiftedNum;
    }

    public final void updateCell(int[][] maze, int rotatedCell, int i, int j) {
        maze[i][j] = rotatedCell;
    }

    public final List<String> convertToList(String result) {
        ArrayList<String> resultList = new ArrayList<>();
        StringBuilder move = new StringBuilder();
        for (int i = 0; i < result.length(); i++) {
            char step = result.charAt(i);
            if (step == ' ') {
                if (move.length() > 0) { // Check if move is not empty before appending
                    resultList.add(move.toString());

                }
                if (i == 0) {
                    resultList.add("");
                } else if (result.charAt(i - 1) == ' ') {
                    resultList.add("");
                }
                move.setLength(0);
                ;
            } else {
                move.append(step);
            }
        }
        if (move.length() > 0) { // Append any remaining non-empty move
            resultList.add(move.toString());
        }
        return resultList;
    }

    public List<String> getExploredPaths() {
        return exploredPaths;
    }
}

// This only has the ordered pair
class BaseNode {
    private int first;
    private int second;

    public BaseNode(int first, int second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof BaseNode))
            return false;
        BaseNode other = (BaseNode) obj;
        return this.first == other.first && this.second == other.second;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(first) ^ Integer.hashCode(second);
    }

    public int getFirstCoordinate() {
        return this.first;
    }

    public int getSecondCoordinate() {
        return this.second;
    }

}

// ordered pair along with rotations and path
class DetailedNode extends BaseNode {

    private int rotations;
    private String path; // Initializing the path as an empty string

    public DetailedNode(int first, int second, String path) {
        super(first, second);
        this.rotations = 0;
        this.path = path;
    }

    public DetailedNode(int first, int second) {
        super(first, second);
        this.rotations = 0;
        this.path = "";
    }

    public void extendPath(String additionalPath) {
        this.path += additionalPath; // Concatenating the additionalPath to the current path
    }

    public void incrementRotations() {
        rotations += 1;
    }

    public int getRotations() {
        return this.rotations;
    }

    public String getPath() {
        return this.path;
    }
}
