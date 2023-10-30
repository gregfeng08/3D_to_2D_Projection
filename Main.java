/* AUTHOR: Gregory Feng
 * DATE: 8/27/23
 * DESCRIPTION: Creates a 3-dimensional cube visualization using matrix operations
 */
import java.awt.*;
import java.util.*;
import javax.swing.*;

public class Main extends JFrame {
	
	public static double resx = 1920, resy = 1080, rotationDelay = 1;
	public static boolean rotateX = false, rotateY = false, rotateZ = false;
	//public static Point[] points = new Point[8];
	public static ArrayList<Point> points = new ArrayList<Point>();
	
	public Main() {
		//I don't even know what the shape is for this
		/*for(double i=-100;i<100;i+=0.5) {
			for(double j=-100;j<100;j+=0.5) {
				for(double k=-100;k<100;k+=0.5) {
					if((i*i+j*j+k*k)==100*100) {
						points.add(new Point(i, j, k));
					}
				}
			}
		}*/
		
		
		//Points to make a cube
		points.add(new Point(100,100,100));
  		points.add(new Point(100,-100,100));
  		points.add(new Point(-100,100,100));
  		points.add(new Point(-100,-100,100));
  		points.add(new Point(100,100,-100));
  		points.add(new Point(100,-100,-100));
  		points.add(new Point(-100,100,-100));
  		points.add(new Point(-100,-100,-100));
  		
  		//Projection Setup
		setTitle("3D Projection");
		setSize((int)resx,(int)resy);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		PaintPanel paintPan = new PaintPanel();
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, this.EXIT_ON_CLOSE));
		panel.setPreferredSize(new Dimension(200,20));
		
		//Add a label for scale slider
		JLabel label = new JLabel("Scale");
		label.setPreferredSize(new Dimension(50,10));
		
		//Add a slider for scale of the projection
		JSlider scaleSlider = new JSlider(JSlider.HORIZONTAL, 0,4,2);
		scaleSlider.setMajorTickSpacing(1);
		scaleSlider.setMinorTickSpacing(1);
		scaleSlider.setPaintTicks(true);
		scaleSlider.setPaintLabels(true);
		scaleSlider.setPreferredSize(new Dimension(100,20));
		
		//Adding a checkbox for X rotation that are used
		JCheckBox rotationXCheck = new JCheckBox("X-Rotation");
		rotationXCheck.setPreferredSize(new Dimension(50,25));
		
		//Adding a checkbox for Y rotation that are used
		JCheckBox rotationYCheck = new JCheckBox("Y-Rotation");
		rotationXCheck.setPreferredSize(new Dimension(50,25));
		
		//Adding a checkbox for Z rotation that are used
		JCheckBox rotationZCheck = new JCheckBox("Y-Rotation");
		rotationZCheck.setPreferredSize(new Dimension(50,25));
		
		panel.add(Box.createRigidArea(new Dimension(10,50)));
		panel.add(label);
		panel.add(scaleSlider);
		panel.add(Box.createRigidArea(new Dimension(10,20)));
		panel.add(rotationXCheck);
		panel.add(Box.createRigidArea(new Dimension(10,20)));
		panel.add(rotationYCheck);
		panel.add(Box.createRigidArea(new Dimension(10,20)));
		panel.add(rotationZCheck);
		
		
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
			angle+=0.002;
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
			drawImage.fillOval((int) ((p.twoDx/2+1920/3)),(int) ((p.twoDy/2+1080/3)),(int)(10),(int)(10));
		}
	}
	public void updateGraphics(double angle) {
		for(Point p:Main.points) {
			p.updatePoint(angle);
		}
		repaint();
	}	
}

class ActionListener
