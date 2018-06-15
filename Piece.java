package ISU;

public class Piece {
	
	public byte name;
	public int value;
	
	/*
	 * constructor, creates a piece and gives it a material value based on which piece it is
	 */
	public Piece (byte b) {
		name = b;
		if		(Math.abs(name) == 1) value = 100;
		else if (Math.abs(name) == 2) value = 300;
		else if (Math.abs(name) == 3) value = 301;
		else if (Math.abs(name) == 4) value = 500;
		else if (Math.abs(name) == 5) value = 900;
		else if (Math.abs(name) == 6) value = 1000000;
		if (b < 0)
			value = -value;
	}
	
	public String toString () {
		if		(Math.abs(name) == 6) return "king";
		else if (Math.abs(name) == 5) return "queen";
		else if (Math.abs(name) == 4) return "rook";
		else if (Math.abs(name) == 3) return "bishop";
		else if (Math.abs(name) == 2) return "knight";
		else 						   return "pawn";
	}
	
	public int compareTo (Piece p) {
		return p.name - this.name;
	}
	
	public boolean equals (Piece p) {
		if (p.name == this.name)
			return true;
		return false;
	}
		
}
