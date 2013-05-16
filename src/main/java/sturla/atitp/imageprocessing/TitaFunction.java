package sturla.atitp.imageprocessing;

import java.util.ArrayList;
import java.util.List;

public class TitaFunction {
	
	private int[][] values;
	private int height;
	private int width;
	
	public TitaFunction(List<Point> selection, int height, int width) {
		values = new int[height][width];
		this.height = height;
		this.width = width;
		for(int x = 0; x < height; x++) {
			for(int y=0; y < width; y++) {
				values[x][y]=3;
			}
		}
		
		for(Point c: selection){
			values[c.y][c.x] = -3;
		}		
		List<Point> in = this.getIn();
		for(Point c: in){
			for(Point n: c.N4()){
				if (outOfBounds(n)) {
					continue;
				}
				if(values[n.y][n.x] == 3){
					values[n.y][n.x] = -1;
				}
			}
		}	
		List<Point> lIn = this.getlIn();
		for(Point c: lIn){
			for(Point n: c.N4()){
				if (outOfBounds(n)) {
					continue;
				}
				if(values[n.y][n.x] == 3){
					values[n.y][n.x] = 1;
				}
			}
		}
//		values[selection.y][selection.x] = -3;
//		values[selection.y-1][selection.x] = -1;
//		values[selection.y][selection.x-1] = -1;
//		values[selection.y+1][selection.x] = -1;
//		values[selection.y][selection.x+1] = -1;		
//		values[selection.y-2][selection.x] = 1;
//		values[selection.y][selection.x-2] = 1;
//		values[selection.y+2][selection.x] = 1;
//		values[selection.y][selection.x+2] = 1;
//		values[selection.y-1][selection.x-1] = 1;
//		values[selection.y-1][selection.x+1] = 1;
//		values[selection.y+1][selection.x-1] = 1;
//		values[selection.y+1][selection.x+1] = 1;		
	}
	
	public boolean isOut(Point c){
		if(outOfBounds(c))
			return false;
		return values[c.y][c.x] == 3;
	}
	
	private boolean outOfBounds(Point c) {
		return c.y < 0 || c.y >= this.height || c.x < 0 || c.x >= this.width;
	}

	public boolean islOut(Point c){
		if(outOfBounds(c))
			return false;
		return values[c.y][c.x] == 1;
	}
	
	public boolean isIn(Point c){
		if(outOfBounds(c))
			return false;
		return values[c.y][c.x] == -3;
	}
	
	public boolean islIn(Point c){
		if(outOfBounds(c))
			return false;
		return values[c.y][c.x] == -1;
	}
	
	public void setOut(Point c){
		if(outOfBounds(c))
			return;
		values[c.y][c.x] = 3;
	}
	
	public void setlOut(Point c){
		if(outOfBounds(c))
			return;
		values[c.y][c.x] = 1;
	}
	
	public void setIn(Point c){
		if(outOfBounds(c))
			return;
		values[c.y][c.x] = -3;
	}
	
	public void setlIn(Point c){
		if(outOfBounds(c))
			return;
		values[c.y][c.x] = -1;
	}

	public List<Point> getlOut() {
		List<Point> ret = new ArrayList<Point>();
		for(int x = 0; x < height; x++){
			for(int y=0; y < width; y++)
				if(values[x][y]==1){
					ret.add(new Point(y, x));
				}
		}
		return ret;
	}
	
	public List<Point> getlIn() {
		List<Point> ret = new ArrayList<Point>();
		for(int x = 0; x < height; x++){
			for(int y=0; y < width; y++)
				if(values[x][y]==-1){
					ret.add(new Point(y, x));
				}
		}
		return ret;
	}

	public List<Point> getIn() {
		List<Point> ret = new ArrayList<Point>();
		for(int x = 0; x < height; x++){
			for(int y=0; y < width; y++)
				if(values[x][y]==-3){
					ret.add(new Point(y, x));
				}
		}
		return ret;
	}

	public List<Point> getOut() {
		List<Point> ret = new ArrayList<Point>();
		for(int x = 0; x < height; x++){
			for(int y=0; y < width; y++)
				if(values[x][y]==3){
					ret.add(new Point(y, x));
				}
		}
		return ret;
	}

	@Override
	public String toString() {
		String resp="";
		for(int x = 0; x < height; x++) {
			for(int y=0; y < width; y++) {
				resp = resp.concat(String.valueOf(values[x][y]) + "\t");
			}
			resp = resp.concat("\t|\n");
		}
		return resp;
	}
	
	

}
