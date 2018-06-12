/*
 * This is the complete re-make of the original Chess class with a new design
 * The intent of this is to make further development more smooth
 */
package ISU;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

// main class
public class Chess extends JPanel{
	private static final long serialVersionUID = 6601046515365214157L;
	// the board is kept in a 2d array, the pieces are represented by numbers
	// pawns=1, knights=2, bishops=3, rook=4, queen=5, king=6, 0=unoccupied square negative values represent black
	public static byte[][] board = new byte [8][8];
	public static int startX, startY, endX, endY;
	public static boolean turn = true, pvp = true, playerSide = true, win = false, whiteCastleKing = true, whiteCastleQueen = true, blackCastleKing = true, blackCastleQueen = true;
	public static ArrayList <Piece> piecesB = new ArrayList <Piece> (), piecesW = new ArrayList <Piece> (), piecesCapturedW = new ArrayList <Piece> (), piecesCapturedB = new ArrayList <Piece> ();
	public static ArrayList <Move> legalMoves = new ArrayList <Move> (), legalMovesNextPlayer = new ArrayList <Move> (), movesPlayed = new ArrayList <Move> ();
	private static Move currentMove;
	private static Canvas canvas;
	private static Graphics g;
	private static BufferStrategy bufferStrat;
	private static JFrame frame;
	private static int whiteT, blackT;

	public static void timer () {
		final Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				if (turn == true)
					whiteT--;
				else if (turn == false)
					blackT--;
				if (whiteT == 0) {
					timer.cancel();
					JOptionPane.showMessageDialog(Chess.frame, "White timed out, black has won. 0-1");
					settings ();
					setBoard ();
				}
				if (blackT == 0) {
					timer.cancel();
					JOptionPane.showMessageDialog(Chess.frame, "Black Timed out, white has won. 1-0");
					settings ();
					setBoard ();
				}
				drawBoard();
			}
		}, 0, 10);
	}

	/*
	 * sets the board to starting position and
	 * resets all the pieces to amount of starting pieces
	 */
	public static void setBoard () {
		win = false;
		turn = true;
		whiteCastleKing = false;
		whiteCastleQueen = false;
		blackCastleKing = false;
		blackCastleQueen = false;
		piecesW.clear();
		piecesB.clear();
		piecesCapturedW.clear();
		piecesCapturedB.clear();
		movesPlayed.clear();
		whiteT = 18000;
		blackT = 18000;
		for (int a = 0; a < 8; a ++)
			for (int b = 0; b < 8; b ++)
				board[b][a] = 0;
//		for (int a = 0; a < 8; a++) {
//			board [a][1] = 1;
//			board [a][6] = -1;
//			piecesW.add(new Piece ((byte)1));
//			piecesB.add(new Piece ((byte)-1));
//		}
//		board [0][0] = 4;
//		board [1][0] = 2;
//		board [2][0] = 3;
//		board [3][0] = 5;
//		board [4][0] = 6;
//		board [5][0] = 3;
//		board [6][0] = 2;
//		board [7][0] = 4;
//		board [0][7] = -4;
//		board [1][7] = -2;
//		board [2][7] = -3;
//		board [3][7] = -5;
//		board [4][7] = -6;
//		board [5][7] = -3;
//		board [6][7] = -2;
//		board [7][7] = -4;
		board [0][7] = -6;
		board [0][5] = 6;
		board [1][1] = 5;
		piecesW.add(new Piece ((byte)6));
		piecesW.add(new Piece ((byte)5));
		piecesW.add(new Piece ((byte)4));
		piecesW.add(new Piece ((byte)4));
		piecesW.add(new Piece ((byte)3));
		piecesW.add(new Piece ((byte)3));
		piecesW.add(new Piece ((byte)2));
		piecesW.add(new Piece ((byte)2));
		piecesB.add(new Piece ((byte)-6));
		piecesB.add(new Piece ((byte)-5));
		piecesB.add(new Piece ((byte)-4));
		piecesB.add(new Piece ((byte)-4));
		piecesB.add(new Piece ((byte)-3));
		piecesB.add(new Piece ((byte)-3));
		piecesB.add(new Piece ((byte)-2));
		piecesB.add(new Piece ((byte)-2));
		Collections.sort(piecesW, new SortPieceValue());
		Collections.sort(piecesB, new SortPieceValue());
	}

	// Formats the moves into chess into algebraic chess notation and returns as a string to print out
	public static String showMoves () {
		String s = "";
		for (int a = 0; a < movesPlayed.size(); a++)
			if (a%2==0)	s += ((a/2+1)+ ". " + movesPlayed.get(a).moveName);
			else		s += (", " + movesPlayed.get(a).moveName+"\n");
		return s;
	}

	public static void setGraphics () {
		canvas.createBufferStrategy(3);
		bufferStrat = canvas.getBufferStrategy();
		g=bufferStrat.getDrawGraphics();
		g.setFont(new Font("Calibri", 0, 24));
		g.setColor(Color.GRAY);
	}

	/*
	 * Draws the board
	 * Draws images of each piece based on where it is on the board
	 */
	public static void drawBoard () {
		g.clearRect(0, 0, 800, 600);
		g.drawImage(ChessLoadRes.back, 0, 0, frame);
		String timeW = "", timeB = "";
		timeW += whiteT/6000;
		timeB += blackT/6000;
		timeW += ":"; //Integer.toString(whiteT%6000/100);
		timeB += ":"; //Integer.toString(blackT%6000/100);
		if (whiteT%6000/100 < 10)	timeW += "0" + Integer.toString(whiteT%6000/100);
		else						timeW +=Integer.toString(whiteT%6000/100);
		if (blackT%6000/100 < 10)	timeB += "0" + Integer.toString(blackT%6000/100);
		else						timeB +=Integer.toString(blackT%6000/100);
		if (whiteT < 1000)	timeW += "." + Integer.toString(whiteT%100);
		if (blackT < 1000)	timeB += "." + Integer.toString(blackT%100);
		g.drawString(timeW, 505, 83);
		g.drawString(timeB, 505, 433);

		for (int a = 7; a >= 0; a--)
			for (int b = 7; b >= 0; b--)
				if (board[b][a] == 1)		g.drawImage(ChessLoadRes.wp, b*50+50, 350-a*50+50, frame);
				else if (board[b][a] == 2)	g.drawImage(ChessLoadRes.wn, b*50+50, 350-a*50+50, frame);
				else if (board[b][a] == 3)	g.drawImage(ChessLoadRes.wb, b*50+50, 350-a*50+50, frame);
				else if (board[b][a] == 4)	g.drawImage(ChessLoadRes.wr, b*50+50, 350-a*50+50, frame);
				else if (board[b][a] == 5)	g.drawImage(ChessLoadRes.wq, b*50+50, 350-a*50+50, frame);
				else if (board[b][a] == 6)	g.drawImage(ChessLoadRes.wk, b*50+50, 350-a*50+50, frame);
				else if (board[b][a] == -1)	g.drawImage(ChessLoadRes.bp, b*50+50, 350-a*50+50, frame);
				else if (board[b][a] == -2)	g.drawImage(ChessLoadRes.bn, b*50+50, 350-a*50+50, frame);
				else if (board[b][a] == -3)	g.drawImage(ChessLoadRes.bb, b*50+50, 350-a*50+50, frame);
				else if (board[b][a] == -4)	g.drawImage(ChessLoadRes.br, b*50+50, 350-a*50+50, frame);
				else if (board[b][a] == -5)	g.drawImage(ChessLoadRes.bq, b*50+50, 350-a*50+50, frame);
				else if (board[b][a] == -6)	g.drawImage(ChessLoadRes.bk, b*50+50, 350-a*50+50, frame);
		Collections.sort(piecesCapturedW, new SortPieceValue());
		Collections.sort(piecesCapturedB, new SortPieceValue());
		int balance = 0;
		for (int a = 0; a < piecesCapturedW.size(); a ++) {
			if		(piecesCapturedW.get(a).name == 6)	g.drawImage(ChessLoadRes.silk, a*25+25, 475, frame);
			else if	(piecesCapturedW.get(a).name == 5)	g.drawImage(ChessLoadRes.silq, a*25+25, 475, frame);
			else if	(piecesCapturedW.get(a).name == 4)	g.drawImage(ChessLoadRes.silr, a*25+25, 475, frame);
			else if	(piecesCapturedW.get(a).name == 3)	g.drawImage(ChessLoadRes.silb, a*25+25, 475, frame);
			else if	(piecesCapturedW.get(a).name == 2)	g.drawImage(ChessLoadRes.siln, a*25+25, 475, frame);
			else if	(piecesCapturedW.get(a).name == 1)	g.drawImage(ChessLoadRes.silp, a*25+25, 475, frame);
			balance -= (piecesCapturedW.get(a).value/100);
		}
		for (int a = 0; a < piecesCapturedB.size(); a ++) {
			if		(piecesCapturedB.get(a).name == -6)	g.drawImage(ChessLoadRes.silk, a*25+25, 505, frame);
			else if	(piecesCapturedB.get(a).name == -5)	g.drawImage(ChessLoadRes.silq, a*25+25, 505, frame);
			else if	(piecesCapturedB.get(a).name == -4)	g.drawImage(ChessLoadRes.silr, a*25+25, 505, frame);
			else if	(piecesCapturedB.get(a).name == -3)	g.drawImage(ChessLoadRes.silb, a*25+25, 505, frame);
			else if	(piecesCapturedB.get(a).name == -2)	g.drawImage(ChessLoadRes.siln, a*25+25, 505, frame);
			else if	(piecesCapturedB.get(a).name == -1)	g.drawImage(ChessLoadRes.silp, a*25+25, 505, frame);
			balance -= (piecesCapturedB.get(a).value/100);
		}
		String s = "";
		if (balance > 0) s = "+";
		g.drawString(s + Integer.toString(balance), Math.max(piecesCapturedB.size(), piecesCapturedW.size()) * 25 + 40, 510);
		bufferStrat.show();
	}

	/*
	 * Moves the pieces on the board
	 * Adds the piece found in the starting cell to the ending cell and replaces the piece in starting cell with 0
	 */
	public static void makeMove (Move m) {
		board[m.endX][m.endY] = board [m.startX][m.startY];
		board [m.startX][m.startY] = 0;
	}

	// makes move for white
	public static void moveWhite () {
		legalMoves.clear();
		legalMoves = Move.findLegalMoves (turn, board);
		if (!playerSide && !pvp) { // if engine moves
			currentMove = Engine.generateMove(legalMoves);
			castle(board, currentMove);
			makeMove (currentMove);
			boolean temp = false;
			for (int a = 0; a < piecesB.size(); a ++)
				if (piecesB.get(a).name == -6)
					temp = true;
			if (!temp)
				win = true;
			turn = false;
		}
		else { // if player moves
			boolean temp = false;
			currentMove = new Move(startX, startY, endX, endY);
			for (int a = 0; a <legalMoves.size(); a++)
				if (legalMoves.get(a).equals(currentMove)) {
					temp = true; // checks to see if move is legal
					movesPlayed.add(legalMoves.get(a));
					break;
				}
			if (temp) {
				castle(board, currentMove);
				if (board[endX][endY] < 0)
					for (int a = 0; a < piecesB.size(); a ++) // checks to see if a piece is captured and removes it from ArrayList
						if (piecesB.get(a).name == board[endX][endY]) {
							piecesB.remove(a);
							piecesCapturedB.add(new Piece (board[endX][endY]));
							break;
						}
				makeMove (currentMove);
				if (endY == 7 && board[endX][endY] == 1) { // Pawn Promotion when pawn reaches end of the board
					byte choice;
					Object[] options1 = {"Knight", "Bishop", "Rook", "Queen"};
					choice = (byte) JOptionPane.showOptionDialog(null,
							null, 
							"Promotion",
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.PLAIN_MESSAGE,
							null,
							options1,
							null);
					choice += 2;
					board[endX][endY] = choice;
					char name;
					if (choice == 5)		name = 'Q';
					else if (choice == 2)	name = 'N';
					else if (choice == 4)	name = 'R';
					else					name = 'B';
					movesPlayed.get(movesPlayed.size()-1).moveName += "=" + name;
					piecesW.remove(piecesW.size() - 1);
					piecesW.add(new Piece ((byte) choice));
					Collections.sort(piecesW, new SortPieceValue());
				}
				legalMovesNextPlayer.clear();
				legalMovesNextPlayer = Move.findLegalMoves(!turn, board);
				checkForMate();
				whiteT += 200;
				temp = false;
				for (int a = 0; a < piecesB.size(); a ++)
					if (piecesB.get(a).name == -6)
						temp = true;
				if (!temp)
					win = true;
				turn = false;
				if  (!pvp)
					moveBlack ();
			}
		}
	}

	// makes move for black
	public static void moveBlack () {
		legalMoves.clear();
		legalMoves = Move.findLegalMoves (turn, board);
		if (playerSide && !pvp) { // if engine moves
			currentMove = Engine.generateMove(legalMoves);
			castle(board, currentMove);
			makeMove (currentMove);
			boolean temp = false;
			for (int a = 0; a < piecesW.size(); a ++) {
				if (piecesW.get(a).name == 6)
					temp = true;
			}
			if (!temp)
				win = true;
			turn = true;
		}
		else { // if player moves
			boolean temp = false;
			currentMove = new Move(startX, startY, endX, endY);
			for (int a = 0; a <legalMoves.size(); a++)
				if (legalMoves.get(a).equals(currentMove)) {
					temp = true; // checks to see if move is legal
					movesPlayed.add(legalMoves.get(a));
					break;
				}
			if (temp) {
				castle(board, currentMove);
				if (board[endX][endY] > 0)
					for (int a = 0; a < piecesB.size(); a ++) // checks to see if a piece is captured and removes it from ArrayList
						if (piecesW.get(a).name == board[endX][endY]) {
							piecesW.remove(a);
							piecesCapturedW.add(new Piece (board[endX][endY]));
							break;
						}
				board[endX][endY] = board [startX][startY]; // moves pieces on the board
				board [startX][startY] = 0;
				if (endY == 0 && board[endX][endY] == -1) {
					byte choice;
					Object[] options1 = {"Knight", "Bishop", "Rook", "Queen"};
					choice = (byte) JOptionPane.showOptionDialog(null,
							null, 
							"Promotion",
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.PLAIN_MESSAGE,
							null,
							options1,
							null);
					choice += 2;
					board[endX][endY] = (byte) -choice;
					char name;
					if (choice == 5)		name = 'Q';
					else if (choice == 2)	name = 'N';
					else if (choice == 4)	name = 'R';
					else					name = 'B';
					movesPlayed.get(movesPlayed.size()-1).moveName += "=" + name;
					piecesB.remove(piecesB.size() - 1);
					piecesB.add(new Piece ((byte) -choice));
					Collections.sort(piecesB, new SortPieceValue());
				}
				legalMovesNextPlayer.clear();
				legalMovesNextPlayer = Move.findLegalMoves(!turn, board);
				checkForMate();
				blackT += 200;
				temp = false;
				for (int a = 0; a < piecesW.size(); a ++)
					if (piecesW.get(a).name == 6)
						temp = true;
				if (!temp)
					win = true;
				turn = true;
				if  (!pvp)
					moveWhite ();
			}
		}
	}

	/*
	 * checks every turn to see if castling is still allowed
	 * moves rook if castling is detected
	 */
	public static void castle (byte[][] board, Move currentMove) {
		if (turn) {
			if (board [currentMove.startX][currentMove.startY] == 6 && currentMove.endX == 6 && whiteCastleKing) { // white castling kingside
				board [5][0] = 4;
				board [7][0] = 0;
			}
			else if (board [currentMove.startX][currentMove.startY] == 6 && currentMove.endX == 2 && whiteCastleQueen) { // white castling queenside
				board [3][0] = 4;
				board [0][0] = 0;
			}
			if (board[currentMove.startX][currentMove.startY] == 4 && currentMove.startX == 0) // this checks to see if a rook or king moved and if so, prevents castling
				whiteCastleQueen = false;
			else if (board[currentMove.startX][currentMove.startY] == 4 && currentMove.startX == 7)
				whiteCastleKing = false;	
			else if (board[currentMove.startX][currentMove.startY] == 6) {
				whiteCastleKing = false;
				whiteCastleQueen = false;
			}
		}
		else {
			if (board [currentMove.startX][currentMove.startY] == -6 && currentMove.endX == 6 && blackCastleKing) { // white castling kingside
				board [5][7] = -4;
				board [7][7] = 0;
			}
			else if (board [currentMove.startX][currentMove.startY] == -6 && currentMove.endX == 2 && blackCastleQueen) { // white castling queenside
				board [3][7] = -4;
				board [0][7] = 0;
			}
			if (board[currentMove.startX][currentMove.startY] == -4 && currentMove.startX == 0) // this checks to see if a rook or king moved and if so, prevents castling
				blackCastleQueen = false;
			else if (board[currentMove.startX][currentMove.startY] == -4 && currentMove.startX == 7)
				blackCastleKing = false;	
			else if (board[currentMove.startX][currentMove.startY] == -6) {
				blackCastleKing = false;
				blackCastleQueen = false;
			}
		}

	}

	public static void checkForMate () {
		boolean checkmate = true, stalemate = true;
		byte mult;
		if (turn)	mult = -1;
		else		mult = 1;
		byte kingX = 0, kingY = 0;
		for (int a = 0; a < 8; a ++)
			for (int b = 0; b < 8; b++)
				if (board[a][b] == 6 * mult) {
					kingX = (byte) a;
					kingY = (byte) b;
				}
		legalMovesNextPlayer = Move.findLegalMoves(!turn, board); // gets all the legal moves of the NEXT player
		ArrayList <Move> kingMoves = new ArrayList <Move> ();
		// Loops through all legal moves (next player) and adds them to a new array list ONLY if the piece being moved is a king
		for (int a = 0; a < legalMovesNextPlayer.size(); a++)
			if (board[legalMovesNextPlayer.get(a).startX][legalMovesNextPlayer.get(a).startY] == 6 * mult)
				kingMoves.add(legalMovesNextPlayer.get(a));
			else
				stalemate = false; // if other pieces can move, it isn't stalemate
		int count = 0;
		for (int a = 0; a < kingMoves.size(); a ++) // loops trough all king moves to see if the opponent is attacking that square
			for (int b = 0; b < legalMoves.size(); b++)
				if (legalMoves.get(b).endX == kingMoves.get(a).endX && legalMoves.get(b).endY == kingMoves.get(a).endY) {
					count ++;
					break;
				}
		if (count != kingMoves.size()) {
			checkmate = false;
			stalemate = false;
		}
		for (int a = 0; a < legalMoves.size(); a++) {
			System.out.println(legalMoves.get(a).endX + "," + legalMoves.get(a).endY);
			if (legalMoves.get(a).endX == kingX && legalMoves.get(a).endY == kingY)
				stalemate = false;
			else
				checkmate = false;
		}
		System.out.println(kingX + "    " + kingY);
		if (checkmate)
			System.out.println("Checkmate");
		if (stalemate)
			System.out.println("stalemate");
	}

	public Chess () {
		drawBoard();
		timer();
		canvas.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				startX = e.getX()/50-1;
				startY =  8-e.getY()/50;
				if (e.getX() > 650 && e.getX() < 770 && e.getY() > 55 && e.getY() < 95)
					JOptionPane.showMessageDialog(Chess.frame, showMoves());
				else if (e.getX() > 650 && e.getX() < 770 && e.getY() > 155 && e.getY() < 195) {
					if (JOptionPane.showConfirmDialog(Chess.frame, "Are you sure you want to resign?") == 0) {
						if (turn)	JOptionPane.showMessageDialog(Chess.frame, "White resigned, black has won. 0-1");
						else		JOptionPane.showMessageDialog(Chess.frame, "Black resigned, white has won. 1-0");
						settings ();
						setBoard ();
					}
				}
				else if (e.getX() > 650 && e.getX() < 770 && e.getY() > 405 && e.getY() < 445)
					if (JOptionPane.showConfirmDialog(Chess.frame, "Are you sure you want to Exit?") == 0) frame.dispose();
			}
			public void mouseReleased(MouseEvent e) {
				endX = e.getX()/50-1;
				endY = 8-e.getY()/50;
				if (startX >= 0 && startX <=8 && startY >= 0 && startY <=8 && endX >= 0 && endX <=8 && endY >= 0 && endY <=8) { // checks to see if user clicked inside the board
					if (turn)	moveWhite();
					else		moveBlack();
					drawBoard();
					if (win) {
						if (!turn)		JOptionPane.showMessageDialog(Chess.frame, "White has won. 1-0");
						else if (turn)	JOptionPane.showMessageDialog(Chess.frame, "Black has won. 0-1");
						settings ();
						setBoard ();
					}
				}
			}
		});
	}

	public static void settings () {
		byte choice;
		Object[] options1 = {"vs human","vs computer"};
		choice = (byte) JOptionPane.showOptionDialog(null,
				null, 
				"",
				JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE,
				null,
				options1,
				null);
		if (choice == 0)
			pvp = true;
		else if (choice == 1) {
			pvp = false;
			options1[0] = "White";
			options1[1] = "Black";
			choice = (byte) JOptionPane.showOptionDialog(null,
					null, 
					"Choose your side",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE,
					null,
					options1,
					null);
			if (choice == 0)
				playerSide = true;
			else if (choice == 1) {
				playerSide = false;
				moveWhite ();
			}
			else frame.dispose();
		}
		else frame.dispose();
	}

	/*
	 * Main class
	 * initializes the JFrame and Graphics and implements a mouse action listener to detect when a player makes a move
	 */
	public static void main(String[] args) {
		setBoard();
		ChessLoadRes.loadAssets();
		canvas = new Canvas();
		frame = new JFrame("Chess");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(800,600));
		frame.setLocationRelativeTo(null);
		frame.add(canvas);
		frame.setVisible(true);
		frame.pack();
		setGraphics();
		settings();
		bufferStrat.show();
		new Chess();
	}
}
