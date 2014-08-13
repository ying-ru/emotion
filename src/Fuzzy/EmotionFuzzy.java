package Fuzzy;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;

public class EmotionFuzzy {
	public static void main(String[] args) throws Exception {
		String filename = "pad.fcl";
		FIS fis = FIS.load(filename, true);

		
		if (fis == null) {
			System.err.println("Can't load file: '" + filename + "'");
			System.exit(1);
		}

		// Get default function block
		FunctionBlock fb = fis.getFunctionBlock(null);

		// Set inputs
		fb.setVariable("dirty", 60);
		fb.setVariable("amt", 70);

		// Evaluate
		fb.evaluate();

		// Show output variable's chart
		fb.getVariable("time").defuzzify();

		// Print ruleSet
		System.out.println(fb);
		System.out.println("Time: " + fb.getVariable("time").getValue());
	}
}
