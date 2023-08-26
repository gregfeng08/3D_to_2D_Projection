import java.awt.*;
import java.util.Arrays;

import javax.swing.*;
public class Main extends JFrame {
	
	public static double resx = 1920, resy = 1080, rotationDelay = 10, angle = 0;
	public static Point[] points = new Point[4];
	
	public static double[][] conversionMatrix =  {
			{1,0,0},
			{0,1,0}
		};
	
	public static double[][] rotationMatrix = {
			{Math.cos(angle),-Math.sin(angle),0},
			{Math.sin(angle), Math.cos(angle), 0},
			{0,0,1}
	};
	public Main() {
		
		points[0] = new Point(100,100,0);
  		points[1] = new Point(100,-100,0);
  		points[2] = new Point(-100,100,0);
  		points[3] = new Point(-100,-100,0);
  		
		for(Point p:points) {
			double[][] toMatrix = {
					{p.x},
					{p.y},
					{p.z}
			};
			double[][] result = matrixMult(conversionMatrix,toMatrix);
			p.threedx=result[0][0];
			p.threedy=result[1][0];
			//System.out.println(matrixMult(conversionMatrix,toMatrix));
		}
  		
  		
		setTitle("3D Projection");
		setSize((int)resx,(int)resy);
		setLayout(new BorderLayout());
		final PaintPanel paintPan = new PaintPanel();
		final JPanel panel = new JPanel();
		add(panel, BorderLayout.EAST);
		add(paintPan, BorderLayout.CENTER);
		setVisible(true);
		
		while(true) {
			angle+=1;
			try {
				Thread.sleep((long)rotationDelay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			paintPan.updateGraphics();
		}
	}
	// Created only for this scenario, as a result, only generates the 2 points of x and y
	public static double[][] matrixMult(double[][] m1, double[][] m2) {
		double[][] result;
		if(m1[0].length!=m2.length) {
			System.out.println("ERR: Invalid Matrix Dimensions");
			return null;
		} else {
			result = new double[m1.length][m2[0].length];
			//result = new int[20][20];
			//System.out.println(m1.length+"x"+m2[0].length);
			for(int k=0;k<m2[0].length;k++) {
				for(int j=0;j<m1.length;j++) {
					int sum = 0;
					for(int i=0;i<m1[0].length;i++) {
					//	System.out.println(j+","+i+";"+k+","+i);
						sum+=m1[j][i]*m2[i][k];
					}
					//System.out.println(sum);
					result[j][k]=sum;
				}
			}
		}
		return result;
	}
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
			drawImage.fillOval((int)p.threedx/2+1920/2,(int)p.threedy/2+1080/2,10,10);
		}
	}
	public void updateGraphics() {
		for(Point p:Main.points) {
			p.threedx+=1;
			p.threedy+=1;
		}
		repaint();
	}
	
}