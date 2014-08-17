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
		fb.setVariable("p", 60);
		fb.setVariable("a", 70);
		fb.setVariable("d", 70);
		

		// Evaluate
		fb.evaluate();

		// Show output variable's chart
		fb.getVariable("emotion").defuzzify();

		// Print ruleSet
		System.out.println(fb);
		System.out.println("Emotion: " + fb.getVariable("emotion").getValue());
	}
}
