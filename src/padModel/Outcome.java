package padModel;

import fuzzy.EmotionFuzzy;

public class Outcome {
	private EmotionFuzzy fuzzy;
	private CalculatePad pad;
	public Outcome() {
		// TODO Auto-generated constructor stub
		fuzzy = new EmotionFuzzy();
		pad = new CalculatePad();
	}
	
	public int getOutcome() {
		int emotion = (int)Math.round(fuzzy.getOutcome(pad.getValueP(), pad.getValueA(), pad.getValueD()));
		return emotion;
	}
	
	public static void main(String[] args) throws Exception {
		Outcome value = new Outcome();
		System.out.println(value.getOutcome());
	}
}
