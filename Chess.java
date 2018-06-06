/*
 * This is the complete re-make of the original Chess class with a new design
 * The intent of this is to make further development more smooth
 */

package ISU;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 * main class, starts the GUI
 */
public class Chess extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6601046515365214157L;
	/*
	 * creates a 2d array to represent the board
	 * the pieces are represented by numbers
	 * pawns=1, knights=2, bishops=3, rook=4, queen=5, king=6, 0=unoccupied square negative values represent black
	 */
	public static byte[][] board = new byte [8][8];
	public static int startX, startY, endX, endY;
	public static boolean turn = true, pvp = true, playerSide = true, whiteCastleKing = true, whiteCastleQueen = true, blackCastleKing = true, blackCastleQueen = true;
	public static ArrayList <Piece> piecesB = new ArrayList <Piece> ();
	public static ArrayList <Piece> piecesW = new ArrayList <Piece> ();
	public static ArrayList <Move> legalMoves = new ArrayList <Move> ();
	public static ArrayList <Move> legalMovesNextPlayer = new ArrayList <Move> ();
	public static ArrayList <Move> movesPlayed = new ArrayList <Move> ();
	private static Canvas canvas;
	private static Graphics g;
	private static BufferStrategy bufferStrat;
	private static JFrame frame;

	private static Move currentMove;

	static JButton moves;
	/*
	 * sets the board to starting position and
	 * resets all the pieces to amount of starting pieces
	 */
	public static void setBoard () {
		turn = true;
		whiteCastleKing = true;
		whiteCastleQueen = true;
		blackCastleKing = true;
		blackCastleQueen = true;
		piecesW.clear();
		piecesB.clear();
		for (int a = 0; a < 8; a ++)
			for (int b = 0; b < 8; b ++)
				board[b][a] = 0;
		for (int a = 0; a < 8; a++) {
			board [a][1] = 1;
			board [a][6] = -1;
			piecesW.add(new Piece ((byte)1));
			piecesB.add(new Piece ((byte)-1));
		}
		board [0][0] = 4;
		board [1][0] = 2;
		board [2][0] = 3;
		board [3][0] = 5;
		board [4][0] = 6;
		board [5][0] = 3;
		board [6][0] = 2;
		board [7][0] = 4;
		board [0][7] = -4;
		board [1][7] = -2;
		board [2][7] = -3;
		board [3][7] = -5;
		board [4][7] = -6;
		board [5][7] = -3;
		board [6][7] = -2;
		board [7][7] = -4;
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
	}

	public static void showMoves () {
		for (int a = 0; a < movesPlayed.size(); a++)
			if (a%2==0)
				System.out.print((a/2+1)+ ". " + movesPlayed.get(a).moveName);
			else
				System.out.println(", " + movesPlayed.get(a).moveName);
		System.out.println();
	}

	public static void setGraphics () {
		canvas.createBufferStrategy(3);
		bufferStrat = canvas.getBufferStrategy();
		g=bufferStrat.getDrawGraphics();
	}

	/*
	 * Draws the board
	 * only prints to the console for now
	 */
	public static void drawBoard () {

		g.clearRect(0, 0, 800, 600);
		g.drawImage(ChessLoadRes.back, 0, 0, frame);

		for (int a = 7; a >= 0; a--) {
			for (int b = 7; b >= 0; b--) {
				if (board[b][a] == 1)
					g.drawImage(ChessLoadRes.wp, b*50+50, 350-a*50+50, frame);
				else if (board[b][a] == 2)
					g.drawImage(ChessLoadRes.wn, b*50+50, 350-a*50+50, frame);
				else if (board[b][a] == 3)
					g.drawImage(ChessLoadRes.wb, b*50+50, 350-a*50+50, frame);
				else if (board[b][a] == 4)
					g.drawImage(ChessLoadRes.wr, b*50+50, 350-a*50+50, frame);
				else if (board[b][a] == 5)
					g.drawImage(ChessLoadRes.wq, b*50+50, 350-a*50+50, frame);
				else if (board[b][a] == 6)
					g.drawImage(ChessLoadRes.wk, b*50+50, 350-a*50+50, frame);
				else if (board[b][a] == -1)
					g.drawImage(ChessLoadRes.bp, b*50+50, 350-a*50+50, frame);
				else if (board[b][a] == -2)
					g.drawImage(ChessLoadRes.bn, b*50+50, 350-a*50+50, frame);
				else if (board[b][a] == -3)
					g.drawImage(ChessLoadRes.bb, b*50+50, 350-a*50+50, frame);
				else if (board[b][a] == -4)
					g.drawImage(ChessLoadRes.br, b*50+50, 350-a*50+50, frame);
				else if (board[b][a] == -5)
					g.drawImage(ChessLoadRes.bq, b*50+50, 350-a*50+50, frame);
				else if (board[b][a] == -6)
					g.drawImage(ChessLoadRes.bk, b*50+50, 350-a*50+50, frame);
			}
		}
		bufferStrat.show();
	}

	/*
	 * makes move for white
	 */
	public static void moveWhite () {
		Move.findLegalMoves (turn);
		if (!playerSide && !pvp) { //engine moves
			turn = false;
		}
		else { // player moves
			boolean temp = false;
			currentMove = new Move(startX, startY, endX, endY);
			for (int a = 0; a <legalMoves.size(); a++) {
				if (legalMoves.get(a).equals(currentMove)) {
					temp = true; // checks to see if move is legal
					movesPlayed.add(legalMoves.get(a));
					break;
				}
			}
			if (temp) {
				if (board [startX][startY] == 6 && endX == 6 && whiteCastleKing) { // white castling kingside
					board [5][0] = 4;
					board [7][0] = 0;
				}
				else if (board [startX][startY] == 6 && endX == 2 && whiteCastleQueen) { // white castling queenside
					board [3][0] = 4;
					board [0][0] = 0;
				}
				if (board[startX][startY] == 4 && startX == 0) // this checks to see if a rook or king moved and if so, prevents castling
					whiteCastleQueen = false;
				else if (board[startX][startY] == 4 && startX == 7)
					whiteCastleKing = false;	
				else if (board[startX][startY] == 6) {
					whiteCastleKing = false;
					whiteCastleQueen = false;
				}
				board[endX][endY] = board [startX][startY]; // moves pieces on the board
				board [startX][startY] = 0;
				if (endY == 7 && board[endX][endY] == 1) { // Pawn Promotion when pawn reaches end of the board
					byte choice;
					Object[] options1 = {	"Knight",
											"Bishop",
											"Rook",
											"Queen"};

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
				}
				turn = false;
				legalMoves.clear();
			}
		}
	}

	/*
	 * makes move for black
	 */
	public static void moveBlack () {
		Move.findLegalMoves (turn);
		if (playerSide && !pvp) { //engine moves
			turn = true;
		}
		else { // player moves
			boolean temp = false;
			currentMove = new Move(startX, startY, endX, endY);
			for (int a = 0; a <legalMoves.size(); a++) {
				if (legalMoves.get(a).equals(currentMove)) {
					temp = true; // checks to see if move is legal
					movesPlayed.add(legalMoves.get(a));
					break;
				}
			}
			if (temp) {
				if (board [startX][startY] == -6 && endX == 6 && blackCastleKing) { // white castling kingside
					board [5][7] = -4;
					board [7][7] = 0;
				}
				else if (board [startX][startY] == -6 && endX == 2 && blackCastleQueen) { // white castling queenside
					board [3][7] = -4;
					board [0][7] = 0;
				}
				if (board[startX][startY] == -4 && startX == 0) // this checks to see if a rook or king moved and if so, prevents castling
					blackCastleQueen = false;
				else if (board[startX][startY] == -4 && startX == 7)
					blackCastleKing = false;	
				else if (board[startX][startY] == -6) {
					blackCastleKing = false;
					blackCastleQueen = false;
				}
				board[endX][endY] = board [startX][startY]; // moves pieces on the board
				board [startX][startY] = 0;
				if (endY == 0 && board[endX][endY] == -1) {
					byte choice;
					Object[] options1 = {	"Knight",
											"Bishop",
											"Rook",
											"Queen"};

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
				}
				turn = true;
				legalMoves.clear();
			}
		}
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
		canvas.setPreferredSize(new Dimension(800,600));
		canvas.setFocusable(false);
		frame.add(canvas);
		frame.setVisible(true);
		frame.pack();

		setGraphics();
		drawBoard();

		canvas.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				startX = e.getX()/50-1;
				startY =  8-e.getY()/50;
			}
			public void mouseReleased(MouseEvent e)
			{
				endX = e.getX()/50-1;
				endY = 8-e.getY()/50;
				if (startX >= 0 && startX <=8 && startY >= 0 && startY <=8 && endX >= 0 && endX <=8 && endY >= 0 && endY <=8) { // checks to see if user clicked inside the board
					if (turn)
						moveWhite();
					else
						moveBlack();
					drawBoard();
				}
			}
		});
	}

}
