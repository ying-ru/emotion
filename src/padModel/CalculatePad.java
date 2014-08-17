package padModel;

public class CalculatePad {

	public CalculatePad() {
		
	}
	
	private void getKinect() {
		
	}
	
	private void getBrainwave() {
		
	}
	
	private void getAccelerometer() {
		
	}
	
//	愉悅度↑：活動量↑、抬頭、身體直立、上臂不向前、Alpha
//	活動量 25% (加速器)
//	Alpha 20% (腦波)
//	抬頭 20% (體感偵測器)
//	身體直立 20% (體感偵測器)
//	上臂不向前 15% (體感偵測器)
	public double getValueP() {
		//db select data and calculate them
		return 0;
	}
	
//	激動度↑：活動量↑、Alpha波↓、Beta波↑、Mu波↓
//	活動量 35% (加速器)
//	Alpha波 25% (腦波)
//	Beta波 25% (腦波)
//	Mu波 15% (腦波)
	public double getValueA() {
		return 0;
	}
	
//	支配度↑：Gamma波↑
//	Gamma波 100% (腦波)
	public double getValueD() {
		return 0;
	}
}
