import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;

/**
*ASCIIConverter is a class used to generate ASCII art from an image.
*
*@author	Toby Flynn
*/
public class ASCIIConverter {
	private final char[] characterSet = {'@', '%', '#', '*', '+', '=', '-', ':', '.', ' '};
	private ImageLoader imageLoader;
	
	/**
	*Method that converts an input image to ASCII art using a specified resolution.
	*
	*@param		imageFilename	the filename (or path) of the input image
	*@param		outputFilename	the filename (or path) of the output text file
	*@param		blockW			the block width (related to the resolution)
	*@param		blockH			the block height (related to the resolution)
	*/
	public void convert(String imageFilename, String outputFilename, int blockW, int blockH) {
		imageLoader = new ImageLoader(imageFilename);
		BufferedImage grayscale = imageLoader.getGrayscale();
		try {
			FileWriter fileWriter = new FileWriter(outputFilename);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			int[][] blocks = getBlocks(blockW, blockH, grayscale);
			for(int y = 0; y < blocks[0].length; y ++){
				for(int x = 0; x < blocks.length; x++){
					int index = blocks[x][y] / 26;
					bufferedWriter.write(characterSet[index]);
				}
				bufferedWriter.newLine();
			}
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	*Method that gets the average colour in a block (related to resolution) of the image.
	*
	*@param		width		the width of a block
	*@param		height		the height of a block
	*@param		image		the grayscale BufferedImage
	*@return				a 2D array of average colours in each block
	*/
	public int[][] getBlocks(int width, int height, BufferedImage image) {
		int[][] result = new int[(image.getWidth() / width) + 1][(image.getHeight() / height) + 1];
		int resX = 0;
		int resY = 0;
		//Cycle through rows that start blocks
		for(int y = 0; y < image.getHeight(); y += height){
			int total = 0;
			//Cycle through all columns
			for(int x = 0; x < image.getWidth(); x++){
				//If at end of block record the average and reset the total
				if(x % width == 0 && (x != 0 || y != 0)){
					result[resX++][resY] = total / (width * height);
					total = 0;
				}
				//Cycle through all rows in this column that are in this block
				for(int n = y; n < y + height && n < image.getHeight(); n++){
					//Add this colour to the total
					Color colour = new Color(image.getRGB(x, n));
					total += colour.getRed();
				}
			}
			resY++;
			resX = 0;
		}
		return result;
	}
	
	public static void main(String[] args) {
		ASCIIConverter converter = new ASCIIConverter();
		converter.convert(args[0], args[1], Integer.parseInt(args[2]), Integer.parseInt(args[3]));
	}
}
