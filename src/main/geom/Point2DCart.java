package main.geom;

public class Point2DCart implements Geometry2DCart {
	public int x, y;
	public Point2DCart(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public void rotate(int degrees) {;}
	public void rotate(Point2DCart reference, int degrees) {
		; //TODO
	}
}
