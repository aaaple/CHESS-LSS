package ISU;

public class Move {

	public int startX, startY, endX, endY;
	public byte piece;
	public String moveName;

	public static void findLegalMoves (boolean turn) {
		for (int a = 0; a < 8; a ++) {
			for (int b = 0; b < 8; b++) {
				if (true) {
					//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
					if (Chess.board[a][b] == 1 && turn) { // Checking pawn moves (white)--------------------------------------------------------------------------------------------------------------------------
						if (b == 1) {
							if (Chess.board[a][b+1] == 0) {
								Chess.legalMoves.add(new Move (a,b,a,b+1));
								if (Chess.board[a][b+2] == 0)
									Chess.legalMoves.add(new Move (a,b,a,b+2));
							}
						}
						else {
							if (Chess.board[a][b+1] == 0)
								Chess.legalMoves.add(new Move (a,b,a,b+1));
						}
						try { if (Chess.board[a+1][b+1] < 0)Chess.legalMoves.add(new Move (a,b,a+1,b+1));}
						catch (IndexOutOfBoundsException e) {}
						try { if (Chess.board[a-1][b+1] < 0)Chess.legalMoves.add(new Move (a,b,a-1,b+1));}
						catch (IndexOutOfBoundsException e) {}
					} //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
					if (Chess.board[a][b] == -1 && !turn) { // Checking pawn moves (black)------------------------------------------------------------------------------------------------------------------------
						if (b == 6) {
							if (Chess.board[a][b-1] == 0) {
								Chess.legalMoves.add(new Move (a,b,a,b-1));
								if (Chess.board[a][b-2] == 0)
									Chess.legalMoves.add(new Move (a,b,a,b-2));
							}
						}
						else {
							if (Chess.board[a][b-1] == 0)
								Chess.legalMoves.add(new Move (a,b,a,b-1));
						}
						try { if (Chess.board[a+1][b-1] > 0)Chess.legalMoves.add(new Move (a,b,a+1,b-1));}
						catch (IndexOutOfBoundsException e) {}
						try { if (Chess.board[a-1][b-1] > 0)Chess.legalMoves.add(new Move (a,b,a-1,b-1));}
						catch (IndexOutOfBoundsException e) {}
					} //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
					else if (Math.abs(Chess.board[a][b]) == 2) { // Checking knight moves-------------------------------------------------------------------------------------------------------------------------
						// All 8 potential moves for a knight
						try { if ((Chess.board[a+1][b+2] <= 0 && turn && Chess.board[a][b] > 0) || (Chess.board[a+1][b+2] >= 0 && !turn && Chess.board[a][b] < 0))	Chess.legalMoves.add(new Move (a,b,a+1,b+2));}
						catch (IndexOutOfBoundsException e) {}
						try { if ((Chess.board[a+1][b-2] <= 0 && turn && Chess.board[a][b] > 0) || (Chess.board[a+1][b-2] >= 0 && !turn && Chess.board[a][b] < 0))	Chess.legalMoves.add(new Move (a,b,a+1,b-2));}
						catch (IndexOutOfBoundsException e) {}
						try { if ((Chess.board[a-1][b+2] <= 0 && turn && Chess.board[a][b] > 0) || (Chess.board[a-1][b+2] >= 0 && !turn && Chess.board[a][b] < 0))	Chess.legalMoves.add(new Move (a,b,a-1,b+2));}
						catch (IndexOutOfBoundsException e) {}
						try { if ((Chess.board[a-1][b-2] <= 0 && turn && Chess.board[a][b] > 0) || (Chess.board[a-1][b-2] >= 0 && !turn && Chess.board[a][b] < 0))	Chess.legalMoves.add(new Move (a,b,a-1,b-2));}
						catch (IndexOutOfBoundsException e) {}
						try { if ((Chess.board[a+2][b+1] <= 0 && turn && Chess.board[a][b] > 0) || (Chess.board[a+2][b+1] >= 0 && !turn && Chess.board[a][b] < 0))	Chess.legalMoves.add(new Move (a,b,a+2,b+1));}
						catch (IndexOutOfBoundsException e) {}
						try { if ((Chess.board[a+2][b-1] <= 0 && turn && Chess.board[a][b] > 0) || (Chess.board[a+2][b-1] >= 0 && !turn && Chess.board[a][b] < 0))	Chess.legalMoves.add(new Move (a,b,a+2,b-1));}
						catch (IndexOutOfBoundsException e) {}
						try { if ((Chess.board[a-2][b+1] <= 0 && turn && Chess.board[a][b] > 0) || (Chess.board[a-2][b+1] >= 0 && !turn && Chess.board[a][b] < 0))	Chess.legalMoves.add(new Move (a,b,a-2,b+1));}
						catch (IndexOutOfBoundsException e) {}
						try { if ((Chess.board[a-2][b-1] <= 0 && turn && Chess.board[a][b] > 0) || (Chess.board[a-2][b-1] >= 0 && !turn && Chess.board[a][b] < 0))	Chess.legalMoves.add(new Move (a,b,a-2,b-1));}
						catch (IndexOutOfBoundsException e) {}
					} //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
					else if (Math.abs(Chess.board[a][b]) == 3) { // Checking bishop moves-------------------------------------------------------------------------------------------------------------------------
						// This same check repeated 4 times for each direction
						int countx = 0, county = 0;
						while (true) { // Checking up/right diagonal
							countx ++;
							county ++;
							try { // This is the algorithm for checking for moves in a direction other directions/rook checks/queen checks behave in similar ways
								if ((Chess.board[a][b] > 0 && turn) || (Chess.board[a][b] < 0 && !turn)) {	 // Checks to see if the turn matches the side of the piece it is checking
									if (Chess.board[a + countx][b + county] != 0) // Keeps checking as long as the square in front is empty
										if ((Chess.board[a + countx][b + county] > 0 && Chess.board[a][b] > 0) || ((Chess.board[a + countx][b + county] < 0 && Chess.board[a][b] < 0)))
											break; // Breaks if next square is occupied by same colour piece
										else {
											Chess.legalMoves.add(new Move (a,b,a + countx,b + county));
											break; // Breaks if next square is occupied by diff colour piece but allows for capture
										}
									else
										Chess.legalMoves.add(new Move (a,b,a + countx,b + county));
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
								if ((Chess.board[a][b] > 0 && turn) || (Chess.board[a][b] < 0 && !turn)) {
									if (Chess.board[a + countx][b + county] != 0)
										if ((Chess.board[a + countx][b + county] > 0 && Chess.board[a][b] > 0) || ((Chess.board[a + countx][b + county] < 0 && Chess.board[a][b] < 0)))
											break;
										else {
											Chess.legalMoves.add(new Move (a,b,a + countx,b + county));
											break;
										}
									else
										Chess.legalMoves.add(new Move (a,b,a + countx,b + county));
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
								if ((Chess.board[a][b] > 0 && turn) || (Chess.board[a][b] < 0 && !turn)) {	
									if (Chess.board[a + countx][b + county] != 0)
										if ((Chess.board[a + countx][b + county] > 0 && Chess.board[a][b] > 0) || ((Chess.board[a + countx][b + county] < 0 && Chess.board[a][b] < 0)))
											break;
										else {
											Chess.legalMoves.add(new Move (a,b,a + countx,b + county));
											break;
										}
									else
										Chess.legalMoves.add(new Move (a,b,a + countx,b + county));
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
								if ((Chess.board[a][b] > 0 && turn) || (Chess.board[a][b] < 0 && !turn)) {	
									if (Chess.board[a + countx][b + county] != 0)
										if ((Chess.board[a + countx][b + county] > 0 && Chess.board[a][b] > 0) || ((Chess.board[a + countx][b + county] < 0 && Chess.board[a][b] < 0)))
											break;
										else {
											Chess.legalMoves.add(new Move (a,b,a + countx,b + county));
											break;
										}
									else
										Chess.legalMoves.add(new Move (a,b,a + countx,b + county));
								}
								else break;
							}
							catch (IndexOutOfBoundsException e) {
								break;
							}
						}
					} //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
					else if (Math.abs(Chess.board[a][b]) == 4) { // Checking rook moves-------------------------------------------------------------------------------------------------------------------------------------
						// see Bishop section for a description of the algorithm
						int count = 0;
						while (true) { // Checking right
							count ++;
							try {
								if ((Chess.board[a][b] > 0 && turn) || (Chess.board[a][b] < 0 && !turn)) {	
									if (Chess.board[a + count][b] != 0)
										if ((Chess.board[a + count][b] > 0 && Chess.board[a][b] > 0) || ((Chess.board[a + count][b] < 0 && Chess.board[a][b] < 0)))
											break;
										else {
											Chess.legalMoves.add(new Move (a,b,a + count,b));
											break;
										}
									else
										Chess.legalMoves.add(new Move (a,b,a + count,b));
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
								if ((Chess.board[a][b] > 0 && turn) || (Chess.board[a][b] < 0 && !turn)) {	
									if (Chess.board[a + count][b] != 0)
										if ((Chess.board[a + count][b] > 0 && Chess.board[a][b] > 0) || ((Chess.board[a + count][b] < 0 && Chess.board[a][b] < 0)))
											break;
										else {
											Chess.legalMoves.add(new Move (a,b,a + count,b));
											break;
										}
									else
										Chess.legalMoves.add(new Move (a,b,a + count,b));
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
								if ((Chess.board[a][b] > 0 && turn) || (Chess.board[a][b] < 0 && !turn)) {	
									if (Chess.board[a][b + count] != 0)
										if ((Chess.board[a][b + count] > 0 && Chess.board[a][b] > 0) || ((Chess.board[a][b + count] < 0 && Chess.board[a][b] < 0)))
											break;
										else {
											Chess.legalMoves.add(new Move (a,b,a,b + count));
											break;
										}
									else
										Chess.legalMoves.add(new Move (a,b,a,b + count));
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
								if ((Chess.board[a][b] > 0 && turn) || (Chess.board[a][b] < 0 && !turn)) {	
									if (Chess.board[a][b + count] != 0)
										if ((Chess.board[a][b + count] > 0 && Chess.board[a][b] > 0) || ((Chess.board[a][b + count] < 0 && Chess.board[a][b] < 0)))
											break;
										else {
											Chess.legalMoves.add(new Move (a,b,a,b + count));
											break;
										}
									else
										Chess.legalMoves.add(new Move (a,b,a,b + count));
								}
								else break;
							}
							catch (IndexOutOfBoundsException e) {
								break;
							}
						}
					} //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
					else if (Math.abs(Chess.board[a][b]) == 5) { // queens--------------------------------------------------------------------------------------------------------------------------------------------------
						// see Bishop section for a description of the algorithm
						int countx = 0, county = 0;
						while (true) { // Checking up/right diagonal
							countx ++;
							county ++;
							try {
								if ((Chess.board[a][b] > 0 && turn) || (Chess.board[a][b] < 0 && !turn)) {
									if (Chess.board[a + countx][b + county] != 0)
										if ((Chess.board[a + countx][b + county] > 0 && Chess.board[a][b] > 0) || ((Chess.board[a + countx][b + county] < 0 && Chess.board[a][b] < 0)))
											break;
										else {
											Chess.legalMoves.add(new Move (a,b,a + countx,b + county));
											break;
										}
									else
										Chess.legalMoves.add(new Move (a,b,a + countx,b + county));
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
								if ((Chess.board[a][b] > 0 && turn) || (Chess.board[a][b] < 0 && !turn)) {
									if (Chess.board[a + countx][b + county] != 0)
										if ((Chess.board[a + countx][b + county] > 0 && Chess.board[a][b] > 0) || ((Chess.board[a + countx][b + county] < 0 && Chess.board[a][b] < 0)))
											break;
										else {
											Chess.legalMoves.add(new Move (a,b,a + countx,b + county));
											break;
										}
									else
										Chess.legalMoves.add(new Move (a,b,a + countx,b + county));
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
								if ((Chess.board[a][b] > 0 && turn) || (Chess.board[a][b] < 0 && !turn)) {	
									if (Chess.board[a + countx][b + county] != 0)
										if ((Chess.board[a + countx][b + county] > 0 && Chess.board[a][b] > 0) || ((Chess.board[a + countx][b + county] < 0 && Chess.board[a][b] < 0)))
											break;
										else {
											Chess.legalMoves.add(new Move (a,b,a + countx,b + county));
											break;
										}
									else
										Chess.legalMoves.add(new Move (a,b,a + countx,b + county));
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
								if ((Chess.board[a][b] > 0 && turn) || (Chess.board[a][b] < 0 && !turn)) {	
									if (Chess.board[a + countx][b + county] != 0)
										if ((Chess.board[a + countx][b + county] > 0 && Chess.board[a][b] > 0) || ((Chess.board[a + countx][b + county] < 0 && Chess.board[a][b] < 0)))
											break;
										else {
											Chess.legalMoves.add(new Move (a,b,a + countx,b + county));
											break;
										}
									else
										Chess.legalMoves.add(new Move (a,b,a + countx,b + county));
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
								if ((Chess.board[a][b] > 0 && turn) || (Chess.board[a][b] < 0 && !turn)) {	
									if (Chess.board[a + count][b] != 0)
										if ((Chess.board[a + count][b] > 0 && Chess.board[a][b] > 0) || ((Chess.board[a + count][b] < 0 && Chess.board[a][b] < 0)))
											break;
										else {
											Chess.legalMoves.add(new Move (a,b,a + count,b));
											break;
										}
									else
										Chess.legalMoves.add(new Move (a,b,a + count,b));
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
								if ((Chess.board[a][b] > 0 && turn) || (Chess.board[a][b] < 0 && !turn)) {	
									if (Chess.board[a + count][b] != 0)
										if ((Chess.board[a + count][b] > 0 && Chess.board[a][b] > 0) || ((Chess.board[a + count][b] < 0 && Chess.board[a][b] < 0)))
											break;
										else {
											Chess.legalMoves.add(new Move (a,b,a + count,b));
											break;
										}
									else
										Chess.legalMoves.add(new Move (a,b,a + count,b));
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
								if ((Chess.board[a][b] > 0 && turn) || (Chess.board[a][b] < 0 && !turn)) {	
									if (Chess.board[a][b + count] != 0)
										if ((Chess.board[a][b + count] > 0 && Chess.board[a][b] > 0) || ((Chess.board[a][b + count] < 0 && Chess.board[a][b] < 0)))
											break;
										else {
											Chess.legalMoves.add(new Move (a,b,a,b + count));
											break;
										}
									else
										Chess.legalMoves.add(new Move (a,b,a,b + count));
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
								if ((Chess.board[a][b] > 0 && turn) || (Chess.board[a][b] < 0 && !turn)) {	
									if (Chess.board[a][b + count] != 0)
										if ((Chess.board[a][b + count] > 0 && Chess.board[a][b] > 0) || ((Chess.board[a][b + count] < 0 && Chess.board[a][b] < 0)))
											break;
										else {
											Chess.legalMoves.add(new Move (a,b,a,b + count));
											break;
										}
									else
										Chess.legalMoves.add(new Move (a,b,a,b + count));
								}
								else break;
							}
							catch (IndexOutOfBoundsException e) {
								break;
							}
						}
					}
					else if (Math.abs(Chess.board[a][b]) == 6) { // kings
						for (int x = -1; x < 2; x ++) {
							for (int y = -1; y < 2; y ++) {
								try {
									if ((Chess.board[a][b] > 0 && turn && Chess.board[a+x][b+y] <= 0) || (Chess.board[a][b] < 0 && !turn && Chess.board[a+x][b+y] >= 0))
										Chess.legalMoves.add(new Move (a,b,a+x,b+y));
								}
								catch (IndexOutOfBoundsException e) {}
							}
						}
						if (Chess.whiteCastleKing && Chess.board[5][b] == 0 && Chess.board[6][b] == 0 && turn)
							Chess.legalMoves.add(new Move (a,b,6,b));
						if (Chess.whiteCastleQueen && Chess.board[3][b] == 0 && Chess.board[2][b] == 0 && Chess.board[1][b] == 0 && turn)
							Chess.legalMoves.add(new Move (a,b,2,b));
						if (Chess.blackCastleKing && Chess.board[5][b] == 0 && Chess.board[6][b] == 0 && !turn)
							Chess.legalMoves.add(new Move (a,b,6,b));
						if (Chess.blackCastleQueen && Chess.board[3][b] == 0 && Chess.board[2][b] == 0 && Chess.board[1][b] == 0 && !turn)
							Chess.legalMoves.add(new Move (a,b,2,b));
					}
				}
			}
		}
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
		if (Chess.board[endX][endY] != 0) {
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
