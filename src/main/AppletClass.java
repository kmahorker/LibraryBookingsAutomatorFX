package main;

import java.applet.*;
import java.awt.*;

public class AppletClass extends Applet{
	
	public void paint(Graphics g) {
		g.drawString("Welcome in Java Applet.", 40, 20);
		Runner.main(null);
	}
	
	

}
