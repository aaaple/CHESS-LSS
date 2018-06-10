package ISU;

/* 
 * This class loads all the resources
 */

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ChessLoadRes {

	// this class basically loads in all our images
	//it also loads in our music
	//everything is static because we need all the resources at all times, and don't want to have to load in images after the game starts
	public static BufferedImage wk,wq,wr,wb,wn,wp,bk,bq,br,bb,bn,bp,back,silk,silq,silr,silb,siln,silp,title;
	/*
	 * Description: loads assets
	 * Arguments: none
	 * Return Type: void
	 */
	public static void loadAssets(){
		bk    = loadImage ("src/ISU/res/chess_piece_black_king.png");
		bq    = loadImage ("src/ISU/res/chess_piece_black_queen.png");
		br    = loadImage ("src/ISU/res/chess_piece_black_rook.png");
		bb    = loadImage ("src/ISU/res/chess_piece_black_bishop.png");
		bn    = loadImage ("src/ISU/res/chess_piece_black_knight.png");
		bp    = loadImage ("src/ISU/res/chess_piece_black_pawn.png");
		wk    = loadImage ("src/ISU/res/chess_piece_white_king.png");
		wq    = loadImage ("src/ISU/res/chess_piece_white_queen.png");
		wr    = loadImage ("src/ISU/res/chess_piece_white_rook.png");
		wb    = loadImage ("src/ISU/res/chess_piece_white_bishop.png");
		wn    = loadImage ("src/ISU/res/chess_piece_white_knight.png");
		wp    = loadImage ("src/ISU/res/chess_piece_white_pawn.png");
		silk  = loadImage ("src/ISU/res/chess_piece_silhouette_king.png");
		silq  = loadImage ("src/ISU/res/chess_piece_silhouette_queen.png");
		silr  = loadImage ("src/ISU/res/chess_piece_silhouette_rook.png");
		silb  = loadImage ("src/ISU/res/chess_piece_silhouette_bishop.png");
		siln  = loadImage ("src/ISU/res/chess_piece_silhouette_knight.png");
		silp  = loadImage ("src/ISU/res/chess_piece_silhouette_pawn.png");
		back  = loadImage ("src/ISU/res/back.png");
		title = loadImage ("src/ISU/res/title.png");
	}
	/*
	 * Description: method to load image
	 * Arguments: none
	 * Return Type: void
	 */
	private static BufferedImage loadImage(String path)
	{
		try {
			return ImageIO.read(new File(path));
		} catch (IOException e) {
			System.out.println("Error loading image!");
			e.printStackTrace();
			System.exit(1);
		}
		return null;

	}
	
}
