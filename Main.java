/* AUTHOR: Gregory Feng
 * DATE: 8/27/23
 * DESCRIPTION: Creates a 3-dimensional cube visualization using matrix operations
 * TODO:
 * 	-Add custom point input
 * 	-Add equation parsing
 * 	-Pray to god everything works
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.util.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.mariuszgromada.math.mxparser.License;

public class Main extends JFrame {
	
	public static double resx = 1920, resy = 1080, rotationDelay = 1;
	public static boolean rotateX = false, rotateY = false, rotateZ = false;
	//public static Point[] points = new Point[8];
	public static ArrayList<Point> points = new ArrayList<Point>();
	public static int scale = 1;
	public static ArrayList<String[][]> toBeMultiplied = new ArrayList<String[][]>();
	public static String newPoint = "";
	
	public Main() {
		
		License.iConfirmNonCommercialUse("Gregory Feng");
		
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
		panel.setPreferredSize(new Dimension(300,20));
		
		//Add a label for scale slider
		JLabel label = new JLabel("Scale:");
		label.setPreferredSize(new Dimension(50,10));
		
		//Add a slider for scale of the projection
		JSlider scaleSlider = new JSlider(JSlider.HORIZONTAL, 1,4,1);
		scaleSlider.setMajorTickSpacing(1);
		scaleSlider.setMinorTickSpacing(1);
		scaleSlider.setPaintTicks(true);
		scaleSlider.setPaintLabels(true);
		scaleSlider.setMaximumSize(new Dimension(400,40));
		scaleSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				if(!scaleSlider.getValueIsAdjusting()) {
					scale = scaleSlider.getValue();
				}
			}
			
		});
		
		JLabel addPointLabel = new JLabel("Add a point (x,y,z):");
		addPointLabel.setPreferredSize(new Dimension(50,10));
		
		JTextField addPoint = new JTextField();
		addPoint.setMaximumSize(new Dimension(150,20));
		addPoint.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			//	if(e.getActionCommand().equals(())
			}
		});
		
		JButton addPointButton = new JButton("Add!");
		addPointButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equals(("Add!"))) {
					String pointCoords[] = parsePoint(addPoint.getText());
					points.add(new Point(Double.parseDouble(pointCoords[0]),Double.parseDouble(pointCoords[1]),Double.parseDouble(pointCoords[2])));
					addPoint.setText("");
				}
			}
			
			private String[] parsePoint(String s) {
				return s.substring(1,s.length()-1).split(",");
			}
		});
		
		//Add a label for scale slider
		JLabel matrixLabel = new JLabel("Multiply By A Custom Matrix:");
		matrixLabel.setPreferredSize(new Dimension(50,10));
		
		JPanel matrixPanel = new JPanel();
		matrixPanel.setLayout(new GridLayout(3,3));
		matrixPanel.setMaximumSize(new Dimension(450,60));
		
		JTextField customMatrix[][] = new JTextField[3][3];
		for(int i=0;i<customMatrix.length;i++) {
			for(int j=0;j<customMatrix.length;j++) {
				customMatrix[i][j] = new JTextField();
				customMatrix[i][j].setMaximumSize(new Dimension(150,20));
				matrixPanel.add(customMatrix[i][j],i,j);
			}
		}
		
		JButton multiplyButton = new JButton("Multiply!");
		multiplyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equals(("Multiply!"))) {
					String customStringMatrix[][] = new String[3][3];
					for(int i=0;i<customMatrix.length;i++) {
						for(int j=0;j<customMatrix[0].length;j++) {
							customStringMatrix[i][j]=customMatrix[i][j].getText();
							customMatrix[i][j].setText("");
						}
					}
					String temp[] = customStringMatrix[0]; //Not sure why rows are swapped
					customStringMatrix[0]=customStringMatrix[2];
					customStringMatrix[2]=temp;
					toBeMultiplied.add(customStringMatrix);
				}
			}
		});
		
		JButton resetMatrices = new JButton("Clear Matrices!");
		resetMatrices.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equals(("Clear Matrices!"))) {
					toBeMultiplied.clear();
				}
				
			}
			
		});
		
		JButton resetPoints = new JButton("Clear Points!");
		resetPoints.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equals(("Clear Points!"))) {
					points.clear();
				}
				
			}
			
		});
		
		//Adding a checkbox for X rotation that are used
		JCheckBox rotationXCheck = new JCheckBox("X-Rotation");
		rotationXCheck.setPreferredSize(new Dimension(50,25));
		rotationXCheck.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().compareTo("X-Rotation")==0) {
					rotateX=!rotateX;
				}
			}
		});
		
		//Adding a checkbox for Y rotation that are used
		JCheckBox rotationYCheck = new JCheckBox("Y-Rotation");
		rotationYCheck.setPreferredSize(new Dimension(50,25));
		rotationYCheck.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().compareTo("Y-Rotation")==0) {
					rotateY=!rotateY;
				}
			}
		});
		
		//Adding a checkbox for Z rotation that are used
		JCheckBox rotationZCheck = new JCheckBox("Z-Rotation");
		rotationZCheck.setPreferredSize(new Dimension(50,25));
		rotationZCheck.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().compareTo("Z-Rotation")==0) {
					rotateZ=!rotateZ;
				}
			}
		});
		
		panel.add(Box.createRigidArea(new Dimension(10,50)));
		panel.add(label);
		panel.add(scaleSlider);
		panel.add(Box.createRigidArea(new Dimension(10,50)));
		panel.add(addPointLabel);
		panel.add(addPoint);
		panel.add(addPointButton);
		panel.add(resetPoints);
		panel.add(Box.createRigidArea(new Dimension(10,50)));
		panel.add(matrixLabel);
		panel.add(matrixPanel);
		panel.add(multiplyButton);
		panel.add(resetMatrices);
		panel.add(Box.createRigidArea(new Dimension(10,50)));
		panel.add(rotationXCheck);
		panel.add(Box.createRigidArea(new Dimension(10,20)));
		panel.add(rotationYCheck);
		panel.add(Box.createRigidArea(new Dimension(10,20)));
		panel.add(rotationZCheck);
		panel.add(Box.createRigidArea(new Dimension(10,20)));
		
		
		
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
