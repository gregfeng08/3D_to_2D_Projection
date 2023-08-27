import java.awt.*;
import java.util.Arrays;

import javax.swing.*;
public class Main extends JFrame {
	
	public static double resx = 1920, resy = 1080, rotationDelay = 1;
	public static Point[] points = new Point[8];
	
	public Main() {
		
		//Points to make a cube
		points[0] = new Point(100,100,100);
  		points[1] = new Point(100,-100,100);
  		points[2] = new Point(-100,100,100);
  		points[3] = new Point(-100,-100,100);
  		points[4] = new Point(100,100,-100);
  		points[5] = new Point(100,-100,-100);
  		points[6] = new Point(-100,100,-100);
  		points[7] = new Point(-100,-100,-100);
  		
		setTitle("3D Projection");
		setSize((int)resx,(int)resy);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		final PaintPanel paintPan = new PaintPanel();
		final JPanel panel = new JPanel();
		add(panel, BorderLayout.EAST);
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
			angle+=0.005;
			paintPan.updateGraphics(angle);
		}
	}
	private Object updateGraphics(double angle) {
		// TODO Auto-generated method stub
		return null;
	}
	// Multiplies a mxn by nxr matrix provided mxr
	
	public static void main(String[] args) {
		new Main();
	}
	
}

class PaintPanel extends JPanel {
	//private Color color = Color.GREEN;
	
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
			drawImage.fillOval((int)p.twoDx/2+1920/3,(int)p.twoDy/2+1080/3,10,10);
		}
	}
	public void updateGraphics(double angle) {
		for(Point p:Main.points) {
			p.updatePoint(angle);
		}
		repaint();
	}
	
}