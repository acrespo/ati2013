package sturla.atitp.imageprocessing;

import java.util.ArrayList;
import java.util.List;

import math.geom2d.Point2D;


public class Point extends java.awt.Point {

	private static final long serialVersionUID = 1L;
	
	public Point(int x, int y) {
		super(x, y);
	}
	
	public Point2D toPoint2D() {
		return new Point2D(x, y);
	}
	
	public List<Point> N4() {
		List<Point> resp = new ArrayList<Point>();
			resp.add(new Point(this.x, this.y-1));
			resp.add(new Point(this.x-1, this.y));
			resp.add(new Point(this.x+1,this.y));
			resp.add(new Point(this.x, this.y+1));
		return resp;
	}

}