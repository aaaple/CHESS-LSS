package ISU;

import java.util.Comparator;

/*
 * Comparable interface
 * compares and finds the difference between two pieces based on value
 */
public class SortPieceValue implements Comparator <Piece>{
	public int compare(Piece p1, Piece p2) {
		return Math.abs(p2.value) - Math.abs(p1.value);
	}
}