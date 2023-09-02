/* AUTHOR: Gregory Feng
 * DATE: 8/27/23
 * DESCRIPTION: Creates a 3-dimensional cube visualization using matrix operations
 */
import java.awt.*;
import java.util.*;

import javax.swing.*;
public class Main extends JFrame {
	
	public final double space = 200;
	public static double resx = 1920, resy = 1080, rotationDelay = 0.1;
	
	public static ArrayList<Point> atoms = new ArrayList<Point>();
	
	public Main() {
		Point oxygen1 = new Point(200,200,60, "O");
		atoms.add(oxygen1);
		Point oxygen2 = new Point(200,200,-60, "O");
		atoms.add(oxygen2);
		
		Point carbon1 = new Point(300,200,40, "C");
		atoms.add(carbon1);
		
		Point nitrogen1 = new Point(400, 300, -30, "N");
		atoms.add(nitrogen1);
		
		oxygen1.addSingleBond(oxygen2);
		oxygen2.addSingleBond(carbon1);
		carbon1.addSingleBond(nitrogen1);
  		
  		//Projection Setup
		setTitle("3D Projection");
		setSize((int)resx,(int)resy);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		final PaintPanel paintPan = new PaintPanel();
		final JPanel panel = new JPanel();
		add(panel, BorderLayout.LINE_END);
		add(paintPan, BorderLayout.CENTER);
		setVisible(true);
		
		double angle = 45;
		
		while(true) {
			// timer = new Timer(10,e->updateGraphics(angle));
			try {
				Thread.sleep((long)rotationDelay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			angle+=0.000001;
			paintPan.updateGraphics(angle);
		}
	}
	public static void main(String[] args) {
		new Main();
	}
	
}

class PaintPanel extends JPanel {
	
	public final int sizeMultiplier = 2;
	
	public PaintPanel() {
		setBackground(Color.WHITE);
	}	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.WHITE);
		Graphics2D drawImage = (Graphics2D) g;
		/*for(Point p:Main.points) {
			drawImage.setColor(Color.GREEN);
			drawImage.fillOval((int) ((p.twoDx/2+1920/3)),(int) ((p.twoDy/2+1080/3)),10,10);
		}*/
		for(Point p:Main.atoms) {
			drawImage.setColor((Color)p.color);
			drawImage.fillOval((int) ((p.twoDx/2+1920/3)),(int) ((p.twoDy/2+1080/3)),p.size*sizeMultiplier,p.size*sizeMultiplier);
			drawImage.setColor(Color.GRAY);
			for(Point bonds:p.getSingleBondedAtoms()) {
				//DO THIS FIRST
				drawImage.setStroke(new BasicStroke(10));
				drawImage.drawLine((int) ((p.twoDx/2+1920/3))+p.size,(int) ((p.twoDy/2+1080/3))+p.size,(int) (bonds.twoDx/2+1920/3)+bonds.size,(int) (bonds.twoDy/2+1080/3)+bonds.size);
			}
		}
	}
	public void updateGraphics(double angle) {
		/*for(Point p:Main.points) {
			p.updatePoint(angle);
		}*/
		for(Point p:Main.atoms) {
			p.updatePoint(angle);
		}
		repaint();
	}
	public double dist(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow(x2-x1, 2)+Math.pow(y2-y1, 2));
	}
	
}