package sturla.atitp.imageprocessing;

public class MaskRotator {

	public static Mask rotate45(Mask mask){
		if( !mask.isSquared() || mask.getHeight() != 3){
			throw new IllegalArgumentException();
		}
		
		Mask result = new Mask(mask.getHeight());
		
		
		result.setPixel(-1, -1, mask.getValue(-1, 0));
		result.setPixel(-1, 0, mask.getValue(-1, 1));
		result.setPixel(-1, 1, mask.getValue(0, 1));
		
		result.setPixel(0, -1, mask.getValue(-1, -1));
		result.setPixel(0, 0, mask.getValue(0, 0));
		result.setPixel(0, 1, mask.getValue(1, 1));
		
		result.setPixel(1, -1, mask.getValue(0, -1));
		result.setPixel(1, 0, mask.getValue(1, -1));
		result.setPixel(1, 1, mask.getValue(1, 0));
		
		return result;
	}
	
	public static Mask rotate90(Mask mask){
		if( !mask.isSquared() || mask.getHeight() != 3){
			throw new IllegalArgumentException();
		}
		
		return rotate45(rotate45(mask));
	}

	public static Mask rotate135(Mask mask){
		if( !mask.isSquared() || mask.getHeight() != 3){
			throw new IllegalArgumentException();
		}
		
		return rotate45(rotate45(rotate45(mask)));
	}	
	
//	public static void main(String[] args) {
//		Mask kirsh = MaskFactory.buildMaskBKirsh();
//		System.out.println(kirsh);
//		System.out.println(rotate45(kirsh));
//		System.out.println(rotate90(kirsh));
//		System.out.println(rotate135(kirsh));
//	}

}
