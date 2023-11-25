import java.util.ArrayList;
import org.mariuszgromada.math.mxparser.*;

public class Point {
	public double x;
	public double y;
	public double z;
	
	public double depth;
	public double dist = 0;
	
	public double twoDx;
	public double twoDy;
	
	public Point(double x_, double y_, double z_) {
		x=x_;
		y=y_;
		z=z_;
	}
	
	public void updatePoint(double angle) {
		double[][] toMatrix = {
				{x},
				{y},
				{z}
		};
		/*
		 * Rotation matrix help from https://www.youtube.com/watch?v=p4Iz0XJY-Qk
		 */
		double[][] rotationMatrixX = {
				{1,0,0},
				{0,Math.cos(angle),-Math.sin(angle)},
				{0,Math.sin(angle),Math.cos(angle)}
		};
		double[][] rotationMatrixY = {
				{Math.cos(angle),0,Math.sin(angle)},
				{0,1,0},
				{-Math.sin(angle),0,Math.cos(angle)}
		};
		double[][] rotationMatrixZ = {
				{Math.cos(angle),-Math.sin(angle),0},
				{Math.sin(angle), Math.cos(angle), 0},
				{0,0,1}
		};
		double[][] rotate = toMatrix;
		for(String[][] m:Main.toBeMultiplied) {
			double[][] toDouble = new double[3][3];
			for(int i=0;i<m.length;i++) {
				for(int j=0;j<m[0].length;j++) {
					Argument t = new Argument("t",angle);
					Expression e = new Expression(m[i][j],t);
					toDouble[i][j]=e.calculate();
				}
			}
			rotate = matrixMult(toDouble,rotate);
		}
		if(Main.rotateY) {
			rotate = matrixMult(rotationMatrixY,rotate);
		}
		if(Main.rotateX) {
			rotate = matrixMult(rotationMatrixX,rotate);
		}
		if(Main.rotateZ) {
			rotate = matrixMult(rotationMatrixZ,rotate);
		}
		
		double z = 1*Main.scale; //Scale
		double[][] conversionMatrix =  {
						{z,0,0},
						{0,z,0}
		};
		double[][] result = matrixMult(conversionMatrix,rotate);
		//double[][] result = matrixMult(conversionMatrix,toMatrix);
		twoDx=result[0][0];
		twoDy=result[1][0];
	}
	
	/*
	//TESTCASE FOR MATRIX MULTIPLY;;WORKING
		double[][] matrixA = {
				{-2, 2, -2},
				{0, 4, 1},
				{1, 1, 3}
		};
		double[][] matrixB = {
				{0, -1, 1},
				{4, -3, -2},
				{3, 2, 1}
		};
		
		double[][] result = matrixMult(matrixA, matrixB);
		for(int i=0;i<result.length;i++) {
			for(int j=0;j<result[0].length;j++) {
				System.out.print(result[i][j]+";");
			}
			System.out.println();
		}*/
	
	public static double[][] matrixMult(double[][] m1, double[][] m2) { //FIX THIS, Matrix multiplies wrong dimensions correctly
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
	
	public static double[][] matrixTransp(double[][] matrix) { //Matrix transpose
		double[][] result;
		result = new double[matrix[0].length][matrix.length];
		for(int i=0;i<matrix[0].length;i++) {
			for(int j=0;j<matrix.length;j++) {
				result[i][j]=matrix[j][i];
			}
		}
		return result;
	}
}
