/* AUTHOR: Gregory Feng
 * DATE: 8/27/23
 * DESCRIPTION: Creates a 3-dimensional cube visualization using matrix operations
 */
import java.awt.*;
import java.util.*;

import javax.swing.*;
public class Main extends JFrame {
	
	public static double resx = 1920, resy = 1080, rotationDelay = 1;
	//public static Point[] points = new Point[8];
	public static ArrayList<Point> points = new ArrayList<Point>();
	
	public Main() {
		//I don't even know what the shape is for this
		for(double i=-100;i<100;i+=0.5) {
			for(double j=-100;j<100;j+=0.5) {
				for(double k=-100;k<100;k+=0.5) {
					if((i*i+j*j+k*k)==100*100) {
						points.add(new Point(i, j, k));
					}
				}
			}
		}
		
		
		//Points to make a cube
		/*points[0] = new Point(100,100,100);
  		points[1] = new Point(100,-100,100);
  		points[2] = new Point(-100,100,100);
  		points[3] = new Point(-100,-100,100);
  		points[4] = new Point(100,100,-100);
  		points[5] = new Point(100,-100,-100);
  		points[6] = new Point(-100,100,-100);
  		points[7] = new Point(-100,-100,-100);*/
  		
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
		
		double angle = 0;
		
		while(true) {
			// timer = new Timer(10,e->updateGraphics(angle));
			try {
				Thread.sleep((long)rotationDelay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			angle+=0.003;
			paintPan.updateGraphics(angle);
		}
	}
	public static void main(String[] args) {
		new Main();
	}
	
}

class PaintPanel extends JPanel {
	
	public PaintPanel() {
		setBackground(Color.BLACK);
	}	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.BLACK);
		Graphics2D drawImage = (Graphics2D) g;
		for(Point p:Main.points) {
			drawImage.setColor(Color.GREEN);
			drawImage.fillOval((int) ((p.twoDx/2+1920/3)),(int) ((p.twoDy/2+1080/3)),10,10);
		}
	}
	public void updateGraphics(double angle) {
		for(Point p:Main.points) {
			p.updatePoint(angle);
		}
		repaint();
	}
	
}