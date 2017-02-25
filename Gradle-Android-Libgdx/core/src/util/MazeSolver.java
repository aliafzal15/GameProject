package util;

public class MazeSolver {
	private int width, height;
	private int[][] maze; //= new int[width][height]; // The maze
	private boolean[][] wasHere; //= new boolean[width][height];
	private boolean[][] correctPath; //= new boolean[width][height]; // The solution to the maze
	private int startX, startY; // Starting X and Y values of maze
	private int endX, endY;     // Ending X and Y values of maze
	
	private void generateMaze(int[][] locationMatrix){
		height = locationMatrix.length;
		width = locationMatrix[0].length;
		maze = new int[width][height];
		wasHere = new boolean[width][height];
		correctPath = new boolean[width][height];
		for (int i = 0 ; i < height; i++){
			for (int j = 0; j < width; j++){
				if(locationMatrix[i][j] == 1 ){
					maze[i][j] = 2;
				}
				else{
					maze[i][j] = 1;
					if(locationMatrix[i][j] == 2){
						startX = j; startY = i;
					}
					if(locationMatrix[i][j] == 3){
						endX = j; endY = i;
					}
				}
			}
		}

	}
	
	public boolean solveMaze(int[][] locationMatrix) {
	    generateMaze(locationMatrix); // Create Maze (1 = path, 2 = wall)
	    for (int row = 0; row < maze.length; row++)  
	        // Sets boolean Arrays to default values
	        for (int col = 0; col < maze[row].length; col++){
	            wasHere[row][col] = false;
	            correctPath[row][col] = false;
	        }
	    boolean b = recursiveSolve(startX, startY);
	    // Will leave you with a boolean array (correctPath) 
	    // with the path indicated by true values.
	    // If b is false, there is no solution to the maze
	    return b;
	}

	private boolean recursiveSolve(int x, int y) {
	    if (x == endX && y == endY) return true; // If you reached the end
	    if (maze[x][y] == 2 || wasHere[x][y]) return false;  
	    // If you are on a wall or already were here
	    wasHere[x][y] = true;
	    if (x != 0) // Checks if not on left edge
	        if (recursiveSolve(x-1, y)) { // Recalls method one to the left
	            correctPath[x][y] = true; // Sets that path value to true;
	            return true;
	        }
	    if (x != width - 1) // Checks if not on right edge
	        if (recursiveSolve(x+1, y)) { // Recalls method one to the right
	            correctPath[x][y] = true;
	            return true;
	        }
	    if (y != 0)  // Checks if not on top edge
	        if (recursiveSolve(x, y-1)) { // Recalls method one up
	            correctPath[x][y] = true;
	            return true;
	        }
	    if (y != height- 1) // Checks if not on bottom edge
	        if (recursiveSolve(x, y+1)) { // Recalls method one down
	            correctPath[x][y] = true;
	            return true;
	        }
	    return false;
	}
}
