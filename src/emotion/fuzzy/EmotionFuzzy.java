package emotion.fuzzy;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;

public class EmotionFuzzy {
	
	public double getOutcome(double p, double a, double d) {
		String filename = "src/emotion/fuzzy/pad.fcl";
		FIS fis = FIS.load(filename, true);
		
		if (fis == null) {
			System.err.println("Can't load file: '" + filename + "'");
			System.exit(1);
		}

		// Get default function block
		FunctionBlock fb = fis.getFunctionBlock(null);

		// Set inputs
		fb.setVariable("p", p);
		fb.setVariable("a", a);
		fb.setVariable("d", d);

		// Evaluate
		fb.evaluate();

		// Show output variable's chart
		fb.getVariable("emotion").defuzzify();

		// Print ruleSet
//		System.out.println(fb);
		System.out.println("Emotion: " + fb.getVariable("emotion").getValue());
		return fb.getVariable("emotion").getValue();
	}
	
	public static void main(String[] args) throws Exception {
		EmotionFuzzy fuzzy = new EmotionFuzzy();
		fuzzy.getOutcome(0.471167088172578, -0.00933124158739014, 0.193297683423769);
	}
}
