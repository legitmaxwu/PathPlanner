import java.util.ArrayList;
import java.util.Stack;

public class Map {
	private static int LENGTH;
	public int getLENGTH() {return LENGTH;}
	private static int WIDTH;
	public int getWIDTH() {return WIDTH;}
	
	Square[][] map;
	public Square[][] getMap() {return map;}
	
	Square start;
	Square end;
	
	Square curr;	
	public Square getCurr() {return curr;}
	public void setCurr(Square sq) {curr = sq;};
	
	SquareChunk ogSC;
	public SquareChunk getOriginalSquareChunk() {return ogSC;}
	public void setOriginalSquareChunk(SquareChunk sc) {ogSC = sc;}
	SquareChunk currSC;
	public SquareChunk getCurrSquareChunk() {return currSC;}
	public void setCurrSquareChunk(SquareChunk sc) {currSC = sc;}
	
	public Square getStart() {return start;}
	public Square getEnd() {return end;}
	
	public void setStart(int i, int j) {
		start = map[i][j];
	}
	public void setEnd(int i, int j) {
		end = map[i][j];
	}
	
	public Map(int l, int w) {
		LENGTH = l;
		WIDTH = w;
		map = new Square[LENGTH][WIDTH];
		
		//initialize map elements
		for (int i = 0; i < LENGTH; i++)
			for (int j = 0; j < WIDTH; j++) {
				map[i][j] = new Square();
				map[i][j].x = i;
				map[i][j].y = j;
			}
		start = map[0][0];
		end = map[LENGTH - 1][WIDTH - 1];
		
		//connect map elements
		for (int i = 0; i < LENGTH; i++) {
			for (int j = 0; j < WIDTH; j++) {
				if (j - 1 >= 0) map[i][j].up = map[i][j - 1];
					else map[i][j].up = null;
				if (j + 1 < WIDTH) map[i][j].down = map[i][j + 1];
					else map[i][j].down = null;
				if (i - 1 >= 0) map[i][j].left = map[i - 1][j];
					else map[i][j].left = null;
				if (i + 1 < LENGTH) map[i][j].right = map[i + 1][j];
					else map[i][j].right = null;
			}
		}
	}
	
	private void shortestPath(Square s, Square e) {
//		if (e.explored == true)
//			return;
		
		int lowerDist = s.dist + 1;
		
//		if ((s.up == null || s.up.explored == true) && (s.down == null || s.down.explored == true) && 
//		(s.left == null || s.left.explored == true) && (s.right == null || s.right.explored == true))
//			return;
		
		if (s.up != null && s.up.dist > lowerDist)
			s.up.dist = lowerDist;
		if (s.down != null && s.down.dist > lowerDist)
			s.down.dist = lowerDist;
		if (s.left != null && s.left.dist > lowerDist)
			s.left.dist = lowerDist;
		if (s.right != null && s.right.dist > lowerDist)
			s.right.dist = lowerDist;
		
		s.explored = true;
		
		Square minDistSq = null;
		int minDist = Integer.MAX_VALUE;
		for (int i = 0; i < LENGTH; i++) {
			for (int j = 0; j < WIDTH; j++) {
				if (map[i][j].dist >= 0 && map[i][j].dist < minDist && map[i][j].explored == false) {
					minDistSq = map[i][j];
					minDist = map[i][j].dist;
				}
			}
		}
		
		if (minDistSq == null)
			return;
		shortestPath(minDistSq, e);

	}
	
	private void shortestPath(SquareChunk s, SquareChunk e) {
//		if (e.head.explored == true)
//			return;
		
		int lowerDist = s.head.dist + 1;

		s.head.explored = true;
		
		boolean moveUp = true;
		boolean moveDown = true;
		boolean moveLeft = true;
		boolean moveRight = true;
		
		for (Square sq : s.chunk) {
			if (sq.up == null || sq.up.dist < 0)
				moveUp = false;
			if (sq.down == null || sq.down.dist < 0 )
				moveDown = false;		
			if (sq.left == null || sq.left.dist < 0)
				moveLeft = false;
			if (sq.right == null || sq.right.dist < 0)
				moveRight = false;
		}
		
		if (moveUp == true && s.head.up.dist > lowerDist)
			s.head.up.dist = lowerDist;
		if (moveDown == true && s.head.down.dist > lowerDist)
			s.head.down.dist = lowerDist;
		if (moveLeft == true && s.head.left.dist > lowerDist)
			s.head.left.dist = lowerDist;
		if (moveRight == true && s.head.right.dist > lowerDist)
			s.head.right.dist = lowerDist;
		
//		printDist();
		
		
		Square minDistSq = null;
		int minDist = Integer.MAX_VALUE;
		
		for (int i = 0; i < LENGTH; i++) {
			for (int j = 0; j < WIDTH; j++) {
				if (map[i][j].dist >= 0 && map[i][j].dist < minDist && map[i][j].explored == false) {
					minDistSq = map[i][j];
					minDist = map[i][j].dist;
				}
			}
		}
		if (minDistSq == null)
			return;
		
//		printSquare(minDistSq.x, minDistSq.y);
//		System.out.println("Square Chunk: ");
//		printSquareChunk(s);
		
		int dX = minDistSq.x - s.head.x;
		int dY = minDistSq.y - s.head.y;
		
		for (int i = 0; i < s.chunk.size(); i++) {
			s.chunk.set(i, map[s.chunk.get(i).x + dX][s.chunk.get(i).y + dY]);
		}
		
		s.head = minDistSq;
		

		shortestPath(s, e);
	}
	
	public int findShortestPath(Square s, Square e) {
		s.dist = 0;
		shortestPath(s, e);
		if (e.dist == Integer.MAX_VALUE)
			return -1;
		return e.dist;
	}
	
	public int findShortestPath(SquareChunk s, SquareChunk e) {
		s.head.dist = 0;
		shortestPath(s, e);
		if (e.head.dist == Integer.MAX_VALUE)
			return -1;
		return e.head.dist;
	}
	
	public int findShortestPath(SquareChunk sc) {
		SquareChunk sc1 = sc;
		SquareChunk sc2 = new SquareChunk(end);
		return findShortestPath(sc1, sc2);
	}
	
	public int[][] getPath(Square e) {
		if (e.dist < 0 || e.dist == Integer.MAX_VALUE) {
			int[][] xd = {{-1}, {-1}};
			return xd;
		}
		int pathLength = e.dist;
		Stack<Square> st = new Stack<Square>();
		int[][] ans = new int[e.dist + 1][2];
		
		while(e.dist > 0) {
			st.push(e);
			int nextDist = e.dist - 1;
			if (e.up != null && e.up.dist == nextDist)
				e = e.up;
			else if (e.down != null && e.down.dist == nextDist)
				e = e.down;
			else if (e.left != null && e.left.dist == nextDist)
				e = e.left;
			else
				e = e.right;
		}
		st.push(e);
		
		Square temp;
		
		for (int i = 0; i <= pathLength; i++) {
			temp = st.pop();
			ans[i][0] = temp.x;
			ans[i][1] = temp.y;
		}
		
		return ans;
	}
	
	public void setObstacle (int i, int j) {
		map[i][j].dist = -1;
	}
	
	public void setRandomObstacles(double frequency) {
		for (int i = 0; i < LENGTH; i++) {
			for (int j = 0; j < WIDTH; j++) {
				if (Math.random() < frequency)
					map[i][j].dist = -1;
			}
		}
	}
	
	public void printDist() {
		for (int i = 0; i < LENGTH; i++) {
			for (int j = 0; j < WIDTH; j++) {
				if (map[i][j].dist == -1)
					System.out.print(" XX");
				else if (map[i][j].dist == Integer.MAX_VALUE)
					System.out.print(" --");
				else
					System.out.printf("%3d", map[i][j].dist);
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public void printProblem() {
		for (int i = 0; i < LENGTH; i++) {
			for (int j = 0; j < WIDTH; j++) {
				if (map[i][j] == start)
					System.out.print(" ST");
				else if (map[i][j] == end)
					System.out.print(" EN");
				else if (map[i][j].dist == -1)
					System.out.print(" XX");
				else
					System.out.print(" --");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public void printProblem(SquareChunk sc) {
		SquareChunk sc2 = new SquareChunk(end);
		int dX = end.x - sc.head.x;
		int dY = end.y - sc.head.y;
		
		int k = 0;
		for (Square sq : sc.chunk) {
			sc2.chunk.add(map[sc.chunk.get(k).x + dX][sc.chunk.get(k).y + dY]);
			k++;
		}
		
		for (int i = 0; i < LENGTH; i++) {
			for (int j = 0; j < WIDTH; j++) {
				if (sc.chunk.contains(map[i][j]))
					System.out.print(" ST");
				else if (sc2.chunk.contains(map[i][j]))
					System.out.print(" EN");
				else if (map[i][j].dist == -1)
					System.out.print(" XX");
				else
					System.out.print(" --");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public void printExplored() {
		for (int i = 0; i < LENGTH; i++) {
			for (int j = 0; j < WIDTH; j++) {
				if (map[i][j].explored == true)
					System.out.print(" EX");
				else
					System.out.print(" --");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public void printSquare(int i, int j) {
		Square s = map[i][j];
		System.out.println("(" + s.x + ", " + s.y + ") | " + "Explored: " + s.explored + " | Dist: " + s.dist);
	}
	
	public void printSquareChunk(SquareChunk sc) {
		for (Square sq : sc.chunk)
			printSquare(sq.x, sq.y);
	}
	
	public SquareChunk newSquareChunk (Square head) {
		return new SquareChunk(head);
	}
	
	public class SquareChunk {
		private Square head;
		public void setHead(Square sq) {head = sq;}
		public Square getHead() {return head;}
		
		private ArrayList<Square> chunk = new ArrayList<Square>();
		public ArrayList<Square> getChunk() {return chunk;}
		
		public SquareChunk(Square sq) {
			head = sq;
			chunk.add(sq);
		}
	}
	
	public SquareChunk createChunk(Square head, int[][] array) {
		SquareChunk sc = new SquareChunk(head);
		for (int i = 0; i < array.length; i++) {
			sc.chunk.add(map[array[i][0]][array[i][1]]);
		}
		return sc;
	}
	
	public SquareChunk copyChunk (SquareChunk sc) {
		SquareChunk sc2 = new SquareChunk(sc.head);
		sc2.chunk.clear();
		for (Square sq : sc.chunk)
			sc2.chunk.add(sq);
		return sc2;
	}
	
	public class Square {
		private int x;
		public int getX() {return x;}
		private int y;
		public int getY() {return y;}
		
		private Square up;
		private Square down;
		private Square left;
		private Square right;
		private boolean explored;
		private int dist;
		public int getDist() {return dist;}
		
		private Square() {
			explored = false;
			dist = Integer.MAX_VALUE;
		}
	}
}

