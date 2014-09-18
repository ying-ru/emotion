package emotion.padModel;

import emotion.fuzzy.EmotionFuzzy;
import emotion.jdbc.DataBase;

public class Outcome {
	private EmotionFuzzy fuzzy;
	private CalculatePad pad;
	public Outcome() {
		// TODO Auto-generated constructor stub
		fuzzy = new EmotionFuzzy();
		pad = new CalculatePad();
	}
	
	public int getOutcome() {
		DataBase db = new DataBase();
		int order = db.selectOrder();
		int emotion = (int)Math.round(fuzzy.getOutcome(pad.getValueP(), pad.getValueA(), pad.getValueD()));
		db.insertPAD(emotion + "," + pad.getValueP() + "," + pad.getValueA() + "," + pad.getValueD() + "," + order);
		return emotion;
	}
	
	public static void main(String[] args) throws Exception {
		Outcome value = new Outcome();
		System.out.println(value.getOutcome());
	}
}
