package brainwave.preferences;

import java.util.prefs.Preferences;

public class PreferenceManager {
	
	static Preferences prefs;

	public static Preferences loadPreferences() {
		
		prefs = Preferences.userRoot().node(PreferenceManager.class.getName());
		return prefs;
		
	}
	
	
}
