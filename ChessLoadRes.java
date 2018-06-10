package ISU;

// This class loads all the resources
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

// This class loads all the images that will be used
public class ChessLoadRes {
	public static BufferedImage wk,wq,wr,wb,wn,wp,bk,bq,br,bb,bn,bp,back,silk,silq,silr,silb,siln,silp,title;
	
	// creates BufferedImages
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
	
	// Loads images from files to the BufferedImages
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
