package org.josvaldor.prospero.energy;

public class Triangle extends Energy{

	public double a, b, c;
	public double A, B, C;
	public Energy i, j, k;

	public Triangle(Energy i, Energy j, Energy k) {
		this.i = i;
		this.j = j;
		this.k = k;
		this.a = this.getDistance(j, k);
		this.b = this.getDistance(i, k);
		this.c = this.getDistance(i, j);
		this.A = (Math.pow(this.b, 2) + Math.pow(this.c, 2) - Math.pow(this.a, 2)) / (2 * this.b * this.c);
		this.A = Math.acos(this.A);
		this.A = Math.toDegrees(this.A);
		this.B = (Math.pow(this.c, 2) + Math.pow(this.a, 2) - Math.pow(this.b, 2)) / (2 * this.a * this.c);
		this.B = Math.acos(this.B);
		this.B = Math.toDegrees(this.B);
		this.C = 180 - this.A - this.B;
	}

	public double getDistance(Energy a, Energy b) {
		return a.position.distance(b.position);
	}

	public boolean thresholdA(double a, double threshold) {
		boolean flag = false;
		if (threshold <=1 && threshold >=0) {
			double value = (this.A>0)?(a/this.A):1;
			if (threshold <= value && value < (1+(1-threshold))) {
				flag = true;
			}
		}
		return flag;
	}
	
	public String toString(){
		return j.name+" "+k.name;
	}
}