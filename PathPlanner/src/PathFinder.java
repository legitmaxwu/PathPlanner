

public class PathFinder {
	private static Map m = new Map(15, 15);

	public static void main (String[] args) {
		
		/*
		m.setStart(0, 0);
		m.setEnd(14, 14);
//		m.setObstacle(1, 0);
//		m.setObstacle(1, 1);
//		m.setObstacle(3, 1);
//		m.setObstacle(3, 2);
//		m.setObstacle(0, 3);
//		m.setObstacle(2, 3);
//		m.setObstacle(1, 4);
		m.setRandomObstacles(0.3);
		
		int pathLength =  m.findShortestPath(m.getStart(), m.getEnd());
		
		m.printProblem();
		m.printDist();
		
		System.out.println("" + pathLength);
		*/
		
		

		m.setStart(0, 0);
		m.setEnd(13, 13);
		
		m.setRandomObstacles(0.1);
		
		int[][] obj = {{1, 0}, {0, 1}, {1, 1}};
		m.printSquareChunk(m.createChunk(m.getStart(), obj));
		int pathLength = m.findShortestPath(m.createChunk(m.getStart(), obj));
		m.printProblem(m.createChunk(m.getStart(), obj));
		m.printDist();
		//m.printExplored();
		System.out.println(pathLength);

		
		
		int[][] path = m.getPath(m.getEnd());
		for (int i = 0; i <= pathLength; i++) {
			for (int j = 0; j < 2; j++)
				System.out.printf("%3d", path[i][j]);
			System.out.println();
		}
	}
}
