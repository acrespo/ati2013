package sturla.atitp.imageprocessing;

public class FourMaskContainer {

	private Mask mask0;
	private Mask mask45;
	private Mask mask90;
	private Mask mask135;
	
	public FourMaskContainer(Mask mask0, Mask mask45, Mask mask90, Mask mask135){
		this.mask0 = mask0;
		this.mask45 = mask45;
		this.mask90 = mask90;
		this.mask135 = mask135;
	}

	public Mask getMask0() {
		return mask0;
	}

	public Mask getMask45() {
		return mask45;
	}

	public Mask getMask90() {
		return mask90;
	}

	public Mask getMask135() {
		return mask135;
	}
	
}
