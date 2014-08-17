package fuzzy;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;

public class EmotionFuzzy {
	
	public static void main(String[] args) throws Exception {
		String filename = "src/Fuzzy/pad.fcl";
		FIS fis = FIS.load(filename, true);

		
		if (fis == null) {
			System.err.println("Can't load file: '" + filename + "'");
			System.exit(1);
		}

		// Get default function block
		FunctionBlock fb = fis.getFunctionBlock(null);

		// Set inputs
		fb.setVariable("p", -0.65);
		fb.setVariable("a", -0.62);
		fb.setVariable("d", -0.33);
		

		// Evaluate
		fb.evaluate();

		// Show output variable's chart
		fb.getVariable("emotion").defuzzify();

		// Print ruleSet
		System.out.println(fb);
		System.out.println("Emotion: " + fb.getVariable("emotion").getValue());
	}
}
