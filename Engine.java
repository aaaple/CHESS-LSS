package ISU;

import java.util.ArrayList;

public class Engine {

	public static Move move;



	public static int alphaBetaMax(int alpha, int beta, int depth, byte[][] board) {
		if (depth == 0) {
			return eval(board); // Base case depth reached
		}
		ArrayList <Move> legalMoves = new ArrayList <Move> ();
		legalMoves = Move.findLegalMoves(true, board);
		for (int a = 0; a < legalMoves.size(); a++) {
			byte [][] boardN = new byte [8][8];
			for(int i = 0; i < board.length; i++)
				boardN[i] = board[i].clone();
			Move m = legalMoves.get(a);
			
			boardN[m.startX][m.startY] = boardN[m.endX][m.endY];
			boardN[m.startX][m.startY] = 0;
			if (board[m.endX][m.endY] == 1 && m.endY == 7)
				board[m.endX][m.endY] = 5;
			
			int score = alphaBetaMin(alpha, beta, depth - 1, boardN);
			if(score >= beta) {
				return beta;   // fail hard beta-cutoff
			}
			if(score > alpha) {
				alpha = score; // alpha acts like max in MiniMax
			}
		}
		return alpha;
	}

	public static int alphaBetaMin(int alpha, int beta, int depth, byte[][] board) {
		if (depth == 0) {
			return eval(board); // Base case depth reached
		}
		ArrayList <Move> legalMoves = new ArrayList <Move> ();
		legalMoves = Move.findLegalMoves(false, board);
		for (int a = 0; a < legalMoves.size(); a++) {
			byte [][] boardN = new byte [8][8];
			for(int i = 0; i < board.length; i++)
				boardN[i] = board[i].clone();
			Move m = legalMoves.get(a);
			
			boardN[m.startX][m.startY] = boardN[m.endX][m.endY];
			boardN[m.startX][m.startY] = 0;
			if (board[m.endX][m.endY] == -1 && m.endY == 0)
				board[m.endX][m.endY] = -5;
			
			int score = alphaBetaMax(alpha, beta, depth - 1, boardN);
			if(score <= alpha) {
				return alpha; // fail hard alpha-cutoff
			}
			if(score < beta) {
				beta = score; // beta acts like min in MiniMax
			}
		}
		return beta;
	}

	public static Move generateMove (ArrayList <Move> legalMoves) {
		int num = (int) (Math.random()*legalMoves.size());
		return legalMoves.get(num);
	}

	public static int eval (byte [][] board) {
		int eval = 0, temp = 0;
		// checking material balance
		try {
			for (int a = 0; a < 8; a++)
				for (int b = 0; b < 8; b++)
					if		(board[b][7-a] == 6)	eval += 100000;
					else if (board[b][7-a] == 5)	eval += 900;
					else if (board[b][7-a] == 4)	eval += 500;
					else if (board[b][7-a] == 3)	eval += 300;
					else if (board[b][7-a] == 2)	eval += 300;
					else if (board[b][7-a] == 1)	eval += 100;
					else if	(board[b][7-a] == -6)	eval -= 100000;
					else if (board[b][7-a] == -5)	eval -= 900;
					else if (board[b][7-a] == -4)	eval -= 500;
					else if (board[b][7-a] == -3)	eval -= 300;
					else if (board[b][7-a] == -2)	eval -= 300;
					else if (board[b][7-a] == -1)	eval -= 100;
		}
		catch (Exception e) {}
		// looking at pawn structure
		// looking for doubled pawns
		for (int a = 0; a < 8; a ++) {
			for (int b = 1; b < 7; b ++) // checking white side
				if (board[a][b] == 1)
					temp++;
			if (temp > 1)
				eval -= ((temp - 1) * 50);
			temp = 0;
			for (int b = 1; b < 7; b ++) // checking black side
				if (board[a][b] == -1)
					temp++;
			if (temp > 1)
				eval += ((temp - 1) * 50);
			temp = 0;
		}
		// looking for isolated pawns
		return eval;
	}
}
