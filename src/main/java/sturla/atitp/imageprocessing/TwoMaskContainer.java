package sturla.atitp.imageprocessing;

public class TwoMaskContainer {

	private Mask dx;
	private Mask dy;
	
	public TwoMaskContainer(Mask dx, Mask dy){
		this.dx = dx;
		this.dy = dy;
	}
	
	public Mask getDXMask(){
		return dx;
	}
	
	public Mask getDYMask(){
		return dy;
	}
	
}
