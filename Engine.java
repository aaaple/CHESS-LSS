package ISU;

public class Engine {
	
	/*
	int alphaBetaMax( int alpha, int beta, int depthleft ) {
		   if ( depthleft == 0 ) return eval();
		   for (*legal moves*) {
		      score = alphaBetaMin( alpha, beta, depthleft - 1 );
		      if( score >= beta )
		         return beta;   // fail hard beta-cutoff
		      if( score > alpha )
		         alpha = score; // alpha acts like max in MiniMax
		   }
		   return alpha;
		}
		 
		int alphaBetaMin( int alpha, int beta, int depthleft ) {
		   if ( depthleft == 0 ) return -eval();
		   for ( all moves) {
		      score = alphaBetaMax( alpha, beta, depthleft - 1 );
		      if( score <= alpha )
		         return alpha; // fail hard alpha-cutoff
		      if( score < beta )
		         beta = score; // beta acts like min in MiniMax
		   }
		   return beta;
		}
		*/
	
	public static int eval () {
		int eval = 0, temp = 0;
		// checking material balance
		for (int a = 0; a < Chess.piecesW.size(); a++)
			eval += Chess.piecesW.get(a).value;
		for (int a = 0; a < Chess.piecesB.size(); a++)
			eval += Chess.piecesB.get(a).value;
		// looking at pawn structure
		// looking for doubled pawns
		for (int a = 0; a < 8; a ++) {
			for (int b = 1; b < 7; b ++)// checking white side
				if (Chess.board[a][b] == 1)
					temp++;
			if (temp > 1)
				eval -= ((temp - 1) * 50);
			temp = 0;
			for (int b = 1; b < 7; b ++)// checking black side
				if (Chess.board[a][b] == -1)
					temp++;
			if (temp > 1)
				eval += ((temp - 1) * 50);
			temp = 0;
		}
		// looking for isolated pawns
		
		return eval;
	}


}