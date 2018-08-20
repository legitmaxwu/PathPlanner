import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JPanel;


public class MapPanel extends JPanel{
	private int WIDTH = 450;
	private int HEIGHT = 450;
	private Map map;
	private int[][] path = {{0, 0}};
	private int maxDist = -1;
	
	public MapPanel(Map mp) {
		
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.GREEN);
        setBounds(50,50, WIDTH,HEIGHT);
        setVisible(true);
        
        map = mp;
	}
	
	public void travel() {
//		map.findShortestPath(map.getStart(), map.getEnd());
//		int[][] instructions = map.getPath(map.getEnd());
//		for (int i = 0; i < instructions.length; i++) {
//			map.setCurr(map.getMap()[instructions[i][0]][instructions[i][1]]);
//			paintImmediately(new Rectangle(0,0, WIDTH, HEIGHT));
//			try {
//			    Thread.sleep(300);
//			}catch(InterruptedException ex) {
//			    Thread.currentThread().interrupt();
//			}
//		}
		map.setCurrSquareChunk(map.copyChunk(map.getOriginalSquareChunk()));
		map.findShortestPath(map.getCurrSquareChunk());
		findMaxDist();
		map.printDist();
		int[][] instructions = map.getPath(map.getEnd());
		
		for (int i = 0; i < instructions.length; i++) {
			for (int j = 0; j < 2; j++)
				System.out.printf("%3d", instructions[i][j]);
			System.out.println();
		}
		
		for (int i = 0; i < instructions.length; i++) {
			map.getCurrSquareChunk().setHead(map.getCurrSquareChunk().getChunk().get(0));
			int dX = instructions[i][0] - map.getCurrSquareChunk().getHead().getX();
			int dY = instructions[i][1] - map.getCurrSquareChunk().getHead().getY();
			
			for (int j = 0; j < map.getCurrSquareChunk().getChunk().size(); j++) {
				map.getCurrSquareChunk().getChunk().set(j, map.getMap()[dX + map.getCurrSquareChunk().getChunk().get(j).getX()][dY + map.getCurrSquareChunk().getChunk().get(j).getY()]);
			}
			//map.printSquareChunk(map.getCurrSquareChunk());
			
			paintImmediately(new Rectangle(0,0, WIDTH, HEIGHT));
			try {
				Thread.sleep(20);
			}catch(InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
		}
		path = instructions;
		paintImmediately(new Rectangle(0,0, WIDTH, HEIGHT));
	}
	
	void findMaxDist() {
		int maxDistance = map.getMap()[0][0].getDist();
		for (int i = 0; i < map.getLENGTH(); i++) {
			for (int j = 0; j < map.getWIDTH(); j++) {
				System.out.print(j);
				if (map.getMap()[i][j].getDist() > maxDist && map.getMap()[i][j].getDist() != Integer.MAX_VALUE)
					maxDistance = map.getMap()[i][j].getDist();
			}
		}
		maxDist = maxDistance;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		

		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				int x = map.getLENGTH() * i / HEIGHT;
				int y = map.getWIDTH() * j / WIDTH;
				boolean onPath = false;
				for (int k = 0; k < path.length; k++)
					if (path[k][0] == x && path[k][1] == y)
						onPath = true;
				
				if (map.getMap()[x][y] == map.getStart())
					g.setColor(Color.YELLOW);
				else if (map.getMap()[x][y] == map.getEnd())
					g.setColor(Color.RED);
				else if (map.getMap()[x][y].getDist() == -1)
					g.setColor(Color.CYAN);
				else if (onPath)
					g.setColor(Color.WHITE);
				else if (maxDist == -1)
					g.setColor(Color.BLACK);
				else
					g.setColor(new Color(Color.HSBtoRGB(0.8f * (float)map.getMap()[x][y].getDist() / (float)maxDist, 0.5f, 0.7f)));
//				if (map.getMap()[x][y] == map.getCurr())
//					g.setColor(Color.GREEN);
				if (map.getCurrSquareChunk().getChunk().contains(map.getMap()[x][y]))
					g.setColor(Color.GREEN);
				g.fillRect(i,j, 1,1);
			}
		}
	}
}
