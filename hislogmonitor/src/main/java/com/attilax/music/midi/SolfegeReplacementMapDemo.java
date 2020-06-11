package com.attilax.music.midi;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jfugue.midi.MidiFileManager;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
import org.staccato.ReplacementMapPreprocessor;
import org.staccato.maps.SolfegeReplacementMap;

public class SolfegeReplacementMapDemo {
	public static void main(String[] args) throws IOException {
		
		 Map rules = new HashMap() {{
	          put("1", "c");
	          put("2", "d");
	          put("3", "e");
	          put("4", "f");
	          put("5", "g");
	          put("6", "a");
	          put("7", "b"  );
	    }};
	    
	    
		ReplacementMapPreprocessor rmp = ReplacementMapPreprocessor.getInstance();
		rmp.setReplacementMap(rules).setRequireAngleBrackets(false);
		Player player = new Player();
		player.play(new Pattern("do re mi fa so la ti do")); // This will play "C D E F G A B"
		

		// This next example brings back the brackets so durations can be added
		rmp.setRequireAngleBrackets(true);
		player.play(new Pattern("<Do>q <Re>q <Mi>h | <Mi>q <Fa>q <So>h | <So>q <Fa>q <Mi>h | <Mi>q <Re>q <Do>h"));
		
		
		Pattern Pattern1 = new Pattern();   
		Pattern1.add("1155665   4433221    5544332    5544332     1155665    4433221");  
		new MidiFileManager().savePatternToMidi(Pattern1, new File("c:\\0log\\atifirstmid.mid"));
		System.out.println("--");
	}
}