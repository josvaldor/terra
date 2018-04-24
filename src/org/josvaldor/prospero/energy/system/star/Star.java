package org.josvaldor.prospero.energy.system.star;

import org.josvaldor.prospero.energy.Orbital;
import java.awt.Color;
import java.awt.Graphics2D;

public class Star extends Orbital {

	public Star() {
		super();
		this.name = "star";
	}

	public void draw(Graphics2D g) {
		g.setColor(this.color);
		double x = this.position.getX() * this.scale;
		double y = this.position.getY() * this.scale;
		double radius = 0;
		g.fillOval((int) (x - (radius / 2)), (int) (y - (radius / 2)), (int) radius, (int) radius);
		g.setColor(Color.WHITE);
		g.drawString(this.name.substring(0, 1).toUpperCase() + this.name.substring(1) + "", (int) x, (int) y);
	}
}
