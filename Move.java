package ISU;

import java.util.ArrayList;

public class Move {
	
	public int startX, startY, endX, endY;
	public byte piece, pieceCaptured = 0;
	public String moveName;

	public static ArrayList<Move> findLegalMoves (boolean turn, byte [][] board) {
		ArrayList <Move> legalMoves = new ArrayList <Move> ();
		for (int a = 0; a < 8; a ++) {
			for (int b = 0; b < 8; b++) {
				if (true) {
					//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
					if (board[a][b] == 1 && turn) { // Checking pawn moves (white)--------------------------------------------------------------------------------------------------------------------------
						if (b == 1) {
							if (board[a][b+1] == 0) {
								legalMoves.add(new Move (a,b,a,b+1));
								if (board[a][b+2] == 0)
									legalMoves.add(new Move (a,b,a,b+2));
							}
						}
						else {
							if (board[a][b+1] == 0)
								legalMoves.add(new Move (a,b,a,b+1));
						}
						try { if (board[a+1][b+1] < 0)legalMoves.add(new Move (a,b,a+1,b+1));}
						catch (IndexOutOfBoundsException e) {}
						try { if (board[a-1][b+1] < 0)legalMoves.add(new Move (a,b,a-1,b+1));}
						catch (IndexOutOfBoundsException e) {}
					} //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
					if (board[a][b] == -1 && !turn) { // Checking pawn moves (black)------------------------------------------------------------------------------------------------------------------------
						if (b == 6) {
							if (board[a][b-1] == 0) {
								legalMoves.add(new Move (a,b,a,b-1));
								if (board[a][b-2] == 0)
									legalMoves.add(new Move (a,b,a,b-2));
							}
						}
						else {
							if (board[a][b-1] == 0)
								legalMoves.add(new Move (a,b,a,b-1));
						}
						try { if (board[a+1][b-1] > 0)legalMoves.add(new Move (a,b,a+1,b-1));}
						catch (IndexOutOfBoundsException e) {}
						try { if (board[a-1][b-1] > 0)legalMoves.add(new Move (a,b,a-1,b-1));}
						catch (IndexOutOfBoundsException e) {}
					} //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
					else if (Math.abs(board[a][b]) == 2) { // Checking knight moves-------------------------------------------------------------------------------------------------------------------------
						// All 8 potential moves for a knight
						try { if ((board[a+1][b+2] <= 0 && turn && board[a][b] > 0) || (board[a+1][b+2] >= 0 && !turn && board[a][b] < 0))	legalMoves.add(new Move (a,b,a+1,b+2));}
						catch (IndexOutOfBoundsException e) {}
						try { if ((board[a+1][b-2] <= 0 && turn && board[a][b] > 0) || (board[a+1][b-2] >= 0 && !turn && board[a][b] < 0))	legalMoves.add(new Move (a,b,a+1,b-2));}
						catch (IndexOutOfBoundsException e) {}
						try { if ((board[a-1][b+2] <= 0 && turn && board[a][b] > 0) || (board[a-1][b+2] >= 0 && !turn && board[a][b] < 0))	legalMoves.add(new Move (a,b,a-1,b+2));}
						catch (IndexOutOfBoundsException e) {}
						try { if ((board[a-1][b-2] <= 0 && turn && board[a][b] > 0) || (board[a-1][b-2] >= 0 && !turn && board[a][b] < 0))	legalMoves.add(new Move (a,b,a-1,b-2));}
						catch (IndexOutOfBoundsException e) {}
						try { if ((board[a+2][b+1] <= 0 && turn && board[a][b] > 0) || (board[a+2][b+1] >= 0 && !turn && board[a][b] < 0))	legalMoves.add(new Move (a,b,a+2,b+1));}
						catch (IndexOutOfBoundsException e) {}
						try { if ((board[a+2][b-1] <= 0 && turn && board[a][b] > 0) || (board[a+2][b-1] >= 0 && !turn && board[a][b] < 0))	legalMoves.add(new Move (a,b,a+2,b-1));}
						catch (IndexOutOfBoundsException e) {}
						try { if ((board[a-2][b+1] <= 0 && turn && board[a][b] > 0) || (board[a-2][b+1] >= 0 && !turn && board[a][b] < 0))	legalMoves.add(new Move (a,b,a-2,b+1));}
						catch (IndexOutOfBoundsException e) {}
						try { if ((board[a-2][b-1] <= 0 && turn && board[a][b] > 0) || (board[a-2][b-1] >= 0 && !turn && board[a][b] < 0))	legalMoves.add(new Move (a,b,a-2,b-1));}
						catch (IndexOutOfBoundsException e) {}
					} //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
					else if (Math.abs(board[a][b]) == 3) { // Checking bishop moves-------------------------------------------------------------------------------------------------------------------------
						// This same check repeated 4 times for each direction
						int countx = 0, county = 0;
						while (true) { // Checking up/right diagonal
							countx ++;
							county ++;
							try { // This is the algorithm for checking for moves in a direction other directions/rook checks/queen checks behave in similar ways
								if ((board[a][b] > 0 && turn) || (board[a][b] < 0 && !turn)) {	 // Checks to see if the turn matches the side of the piece it is checking
									if (board[a + countx][b + county] != 0) // Keeps checking as long as the square in front is empty
										if ((board[a + countx][b + county] > 0 && board[a][b] > 0) || ((board[a + countx][b + county] < 0 && board[a][b] < 0)))
											break; // Breaks if next square is occupied by same colour piece
										else {
											legalMoves.add(new Move (a,b,a + countx,b + county));
											break; // Breaks if next square is occupied by diff colour piece but allows for capture
										}
									else
										legalMoves.add(new Move (a,b,a + countx,b + county));
								}
								else break;
							}
							catch (IndexOutOfBoundsException e) {
								break;
							}
						}
						countx = 0;
						county = 0;
						while (true) { // Checking down/right diagonal-------------------------------------------------------------------------------------------------------------
							countx ++;
							county --;
							try {
								if ((board[a][b] > 0 && turn) || (board[a][b] < 0 && !turn)) {
									if (board[a + countx][b + county] != 0)
										if ((board[a + countx][b + county] > 0 && board[a][b] > 0) || ((board[a + countx][b + county] < 0 && board[a][b] < 0)))
											break;
										else {
											legalMoves.add(new Move (a,b,a + countx,b + county));
											break;
										}
									else
										legalMoves.add(new Move (a,b,a + countx,b + county));
								}
								else break;
							}
							catch (IndexOutOfBoundsException e) {
								break;
							}
						}
						countx = 0;
						county = 0;
						while (true) { // Checking up/left diagonal----------------------------------------------------------------------------------------------------------------
							countx --;
							county ++;
							try {
								if ((board[a][b] > 0 && turn) || (board[a][b] < 0 && !turn)) {	
									if (board[a + countx][b + county] != 0)
										if ((board[a + countx][b + county] > 0 && board[a][b] > 0) || ((board[a + countx][b + county] < 0 && board[a][b] < 0)))
											break;
										else {
											legalMoves.add(new Move (a,b,a + countx,b + county));
											break;
										}
									else
										legalMoves.add(new Move (a,b,a + countx,b + county));
								}
								else break;
							}
							catch (IndexOutOfBoundsException e) {
								break;
							}
						}
						countx = 0;
						county = 0;
						while (true) { // Checking down/left diagonal--------------------------------------------------------------------------------------------------------------
							countx --;
							county --;
							try {
								if ((board[a][b] > 0 && turn) || (board[a][b] < 0 && !turn)) {	
									if (board[a + countx][b + county] != 0)
										if ((board[a + countx][b + county] > 0 && board[a][b] > 0) || ((board[a + countx][b + county] < 0 && board[a][b] < 0)))
											break;
										else {
											legalMoves.add(new Move (a,b,a + countx,b + county));
											break;
										}
									else
										legalMoves.add(new Move (a,b,a + countx,b + county));
								}
								else break;
							}
							catch (IndexOutOfBoundsException e) {
								break;
							}
						}
					} //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
					else if (Math.abs(board[a][b]) == 4) { // Checking rook moves-------------------------------------------------------------------------------------------------------------------------------------
						// see Bishop section for a description of the algorithm
						int count = 0;
						while (true) { // Checking right
							count ++;
							try {
								if ((board[a][b] > 0 && turn) || (board[a][b] < 0 && !turn)) {	
									if (board[a + count][b] != 0)
										if ((board[a + count][b] > 0 && board[a][b] > 0) || ((board[a + count][b] < 0 && board[a][b] < 0)))
											break;
										else {
											legalMoves.add(new Move (a,b,a + count,b));
											break;
										}
									else
										legalMoves.add(new Move (a,b,a + count,b));
								}
								else break;
							}
							catch (IndexOutOfBoundsException e) {
								break;
							}
						}
						count = 0;
						while (true) { // Checking left----------------------------------------------------------------------------------------------------------------------------
							count --;
							try {
								if ((board[a][b] > 0 && turn) || (board[a][b] < 0 && !turn)) {	
									if (board[a + count][b] != 0)
										if ((board[a + count][b] > 0 && board[a][b] > 0) || ((board[a + count][b] < 0 && board[a][b] < 0)))
											break;
										else {
											legalMoves.add(new Move (a,b,a + count,b));
											break;
										}
									else
										legalMoves.add(new Move (a,b,a + count,b));
								}
								else break;
							}
							catch (IndexOutOfBoundsException e) {
								break;
							}
						}
						count = 0;
						while (true) { // Checking up------------------------------------------------------------------------------------------------------------------------------
							count ++;
							try {
								if ((board[a][b] > 0 && turn) || (board[a][b] < 0 && !turn)) {	
									if (board[a][b + count] != 0)
										if ((board[a][b + count] > 0 && board[a][b] > 0) || ((board[a][b + count] < 0 && board[a][b] < 0)))
											break;
										else {
											legalMoves.add(new Move (a,b,a,b + count));
											break;
										}
									else
										legalMoves.add(new Move (a,b,a,b + count));
								}
								else break;
							}
							catch (IndexOutOfBoundsException e) {
								break;
							}
						}
						count = 0;
						while (true) { // Checking down----------------------------------------------------------------------------------------------------------------------------
							count --;
							try {
								if ((board[a][b] > 0 && turn) || (board[a][b] < 0 && !turn)) {	
									if (board[a][b + count] != 0)
										if ((board[a][b + count] > 0 && board[a][b] > 0) || ((board[a][b + count] < 0 && board[a][b] < 0)))
											break;
										else {
											legalMoves.add(new Move (a,b,a,b + count));
											break;
										}
									else
										legalMoves.add(new Move (a,b,a,b + count));
								}
								else break;
							}
							catch (IndexOutOfBoundsException e) {
								break;
							}
						}
					} //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
					else if (Math.abs(board[a][b]) == 5) { // queens--------------------------------------------------------------------------------------------------------------------------------------------------
						// see Bishop section for a description of the algorithm
						int countx = 0, county = 0;
						while (true) { // Checking up/right diagonal
							countx ++;
							county ++;
							try {
								if ((board[a][b] > 0 && turn) || (board[a][b] < 0 && !turn)) {
									if (board[a + countx][b + county] != 0)
										if ((board[a + countx][b + county] > 0 && board[a][b] > 0) || ((board[a + countx][b + county] < 0 && board[a][b] < 0)))
											break;
										else {
											legalMoves.add(new Move (a,b,a + countx,b + county));
											break;
										}
									else
										legalMoves.add(new Move (a,b,a + countx,b + county));
								}
								else break;
							}
							catch (IndexOutOfBoundsException e) {
								break;
							}
						}
						countx = 0;
						county = 0;
						while (true) { // Checking down/right diagonal-------------------------------------------------------------------------------------------------------------
							countx ++;
							county --;
							try {
								if ((board[a][b] > 0 && turn) || (board[a][b] < 0 && !turn)) {
									if (board[a + countx][b + county] != 0)
										if ((board[a + countx][b + county] > 0 && board[a][b] > 0) || ((board[a + countx][b + county] < 0 && board[a][b] < 0)))
											break;
										else {
											legalMoves.add(new Move (a,b,a + countx,b + county));
											break;
										}
									else
										legalMoves.add(new Move (a,b,a + countx,b + county));
								}
								else break;
							}
							catch (IndexOutOfBoundsException e) {
								break;
							}
						}
						countx = 0;
						county = 0;
						while (true) { // Checking up/left diagonal----------------------------------------------------------------------------------------------------------------
							countx --;
							county ++;
							try {
								if ((board[a][b] > 0 && turn) || (board[a][b] < 0 && !turn)) {	
									if (board[a + countx][b + county] != 0)
										if ((board[a + countx][b + county] > 0 && board[a][b] > 0) || ((board[a + countx][b + county] < 0 && board[a][b] < 0)))
											break;
										else {
											legalMoves.add(new Move (a,b,a + countx,b + county));
											break;
										}
									else
										legalMoves.add(new Move (a,b,a + countx,b + county));
								}
								else break;
							}
							catch (IndexOutOfBoundsException e) {
								break;
							}
						}
						countx = 0;
						county = 0;
						while (true) { // Checking down/left diagonal--------------------------------------------------------------------------------------------------------------
							countx --;
							county --;
							try {
								if ((board[a][b] > 0 && turn) || (board[a][b] < 0 && !turn)) {	
									if (board[a + countx][b + county] != 0)
										if ((board[a + countx][b + county] > 0 && board[a][b] > 0) || ((board[a + countx][b + county] < 0 && board[a][b] < 0)))
											break;
										else {
											legalMoves.add(new Move (a,b,a + countx,b + county));
											break;
										}
									else
										legalMoves.add(new Move (a,b,a + countx,b + county));
								}
								else break;
							}
							catch (IndexOutOfBoundsException e) {
								break;
							}
						}
						int count = 0;
						while (true) { // Checking right---------------------------------------------------------------------------------------------------------------------------
							count ++;
							try {
								if ((board[a][b] > 0 && turn) || (board[a][b] < 0 && !turn)) {	
									if (board[a + count][b] != 0)
										if ((board[a + count][b] > 0 && board[a][b] > 0) || ((board[a + count][b] < 0 && board[a][b] < 0)))
											break;
										else {
											legalMoves.add(new Move (a,b,a + count,b));
											break;
										}
									else
										legalMoves.add(new Move (a,b,a + count,b));
								}
								else break;
							}
							catch (IndexOutOfBoundsException e) {
								break;
							}
						}
						count = 0;
						while (true) { // Checking left----------------------------------------------------------------------------------------------------------------------------
							count --;
							try {
								if ((board[a][b] > 0 && turn) || (board[a][b] < 0 && !turn)) {	
									if (board[a + count][b] != 0)
										if ((board[a + count][b] > 0 && board[a][b] > 0) || ((board[a + count][b] < 0 && board[a][b] < 0)))
											break;
										else {
											legalMoves.add(new Move (a,b,a + count,b));
											break;
										}
									else
										legalMoves.add(new Move (a,b,a + count,b));
								}
								else break;
							}
							catch (IndexOutOfBoundsException e) {
								break;
							}
						}
						count = 0;
						while (true) { // Checking up------------------------------------------------------------------------------------------------------------------------------
							count ++;
							try {
								if ((board[a][b] > 0 && turn) || (board[a][b] < 0 && !turn)) {	
									if (board[a][b + count] != 0)
										if ((board[a][b + count] > 0 && board[a][b] > 0) || ((board[a][b + count] < 0 && board[a][b] < 0)))
											break;
										else {
											legalMoves.add(new Move (a,b,a,b + count));
											break;
										}
									else
										legalMoves.add(new Move (a,b,a,b + count));
								}
								else break;
							}
							catch (IndexOutOfBoundsException e) {
								break;
							}
						}
						count = 0;
						while (true) { // Checking down----------------------------------------------------------------------------------------------------------------------------
							count --;
							try {
								if ((board[a][b] > 0 && turn) || (board[a][b] < 0 && !turn)) {	
									if (board[a][b + count] != 0)
										if ((board[a][b + count] > 0 && board[a][b] > 0) || ((board[a][b + count] < 0 && board[a][b] < 0)))
											break;
										else {
											legalMoves.add(new Move (a,b,a,b + count));
											break;
										}
									else
										legalMoves.add(new Move (a,b,a,b + count));
								}
								else break;
							}
							catch (IndexOutOfBoundsException e) {
								break;
							}
						}
					}
					else if (Math.abs(board[a][b]) == 6) { // kings
						for (int x = -1; x < 2; x ++) {
							for (int y = -1; y < 2; y ++) {
								try {
									if ((board[a][b] > 0 && turn && board[a+x][b+y] <= 0) || (board[a][b] < 0 && !turn && board[a+x][b+y] >= 0))
										legalMoves.add(new Move (a,b,a+x,b+y));
								}
								catch (IndexOutOfBoundsException e) {}
							}
						}
						if (Chess.wCastleKing && board[5][b] == 0 && board[6][b] == 0 && turn)
							legalMoves.add(new Move (a,b,6,b));
						if (Chess.wCastleQueen && board[3][b] == 0 && board[2][b] == 0 && board[1][b] == 0 && turn)
							legalMoves.add(new Move (a,b,2,b));
						if (Chess.bCastleKing && board[5][b] == 0 && board[6][b] == 0 && !turn)
							legalMoves.add(new Move (a,b,6,b));
						if (Chess.bCastleQueen && board[3][b] == 0 && board[2][b] == 0 && board[1][b] == 0 && !turn)
							legalMoves.add(new Move (a,b,2,b));
					}
				}
			}
		}
		return legalMoves;
	}

	public Move (int sx, int sy, int ex, int ey) {
		startX = sx;
		startY = sy;
		endX = ex;
		endY = ey;
		piece = Chess.board[sx][sy];
		moveName = getMoveName();
	}

	public String getMoveName () {
		if (Math.abs(Chess.board[startX][startY]) == 6 && endX == 6)
			return "O-O";
		else if (Math.abs(Chess.board[startX][startY]) == 6 && endX == 2)
			return "O-O-O";
		char xval = (char) (97 + endX);
		String piece;
		String cap = "";
		if (this.pieceCaptured != 0) {
			cap = "x";
			if (Math.abs(Chess.board[startX][startY]) == 1)
				cap = (char) (97 + startX) + cap;
				}
		if (Math.abs(this.piece) == 6) 		piece = "K";
		else if (Math.abs(this.piece) == 5) piece = "Q";
		else if (Math.abs(this.piece) == 4) piece = "R";
		else if (Math.abs(this.piece) == 3) piece = "B";
		else if (Math.abs(this.piece) == 2) piece = "N";
		else 								piece = "";
		return piece + cap + xval + (endY + 1);
	}

	public String toString () {
		return moveName;
	}

	public boolean equals (Move m) {
		if (this.startX == m.startX && this.startY == m.startY && this.endX == m.endX && this.endY == m.endY)
			return true;
		return false;
	}
}
