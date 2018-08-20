import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MapDrawer extends JFrame {
	private MapPanel mp;
	
	public MapDrawer(Map map){
		setTitle("Path Planner");
		setSize(800,600);
		setLocation(new Point(300,200));
		setLayout(null);    
		setResizable(false);
		
		mp = new MapPanel(map);
		add(mp);
		
		JButton btnTravel = new JButton("Travel");
		btnTravel.setBounds(600,100, 100,30);
		add(btnTravel);
		
		btnTravel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnTravelClick(e);
			}
		});
		
		
		

//		initComponent();    
//		initEvent();   
	}
	
	private void btnTravelClick(ActionEvent evt) {
		try {
			mp.travel();
		}catch(Exception e){
			System.out.println(e);
			JOptionPane.showMessageDialog(null, 
				e.toString(),
				"Error", 
				JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	public static void main(String[] args){
		Map field = new Map(100, 100);
		field.setStart(0, 0);
		field.setEnd(field.getLENGTH() - 4, field.getWIDTH() - 4);
		
		MazeGenerator maze = new MazeGenerator(100);
		maze.generateMaze();
		for (int i = 0; i < maze.getMaze().length; i++) {
			for (int j = 0; j < maze.getMaze()[0].length; j++) {
				if (maze.getMaze()[i][j] == 0)
					field.setObstacle(i, j);
			}
		}
		//field.setRandomObstacles(0.2);
		
		int[][] obj = {};
		field.setOriginalSquareChunk(field.createChunk(field.getStart(), obj));
		field.setCurrSquareChunk(field.copyChunk(field.getOriginalSquareChunk()));
		
		MapDrawer f = new MapDrawer(field);
		f.setVisible(true);
	}
}
