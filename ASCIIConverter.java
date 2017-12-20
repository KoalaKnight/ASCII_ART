import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;

public class ASCIIConverter {
	private final char[] characterSet = {'@', '%', '#', '*', '+', '=', '-', ':', '.', ' '};
	private ImageLoader imageLoader;
	
	public void convert(String imageFilename, String outputFilename, int blockW, int blockH) {
		imageLoader = new ImageLoader(imageFilename);
		BufferedImage grayscale = imageLoader.getGrayScale();
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
	
	public int[][] getBlocks(int width, int height, BufferedImage image) {
		int[][] result = new int[(image.getWidth() / width) + 1][(image.getHeight() / height) + 1];
		int resX = 0;
		int resY = 0;
		//Cycle through rows that start blocks
		for(int y = 0; y < image.getHeight(); y += height){
			int total = 0;
			//Cycle through all columns
			for(int x = 0; x < image.getWidth(); x++){
				if(x % width == 0 && (x != 0 || y != 0)){
					result[resX++][resY] = total / (width * height);
					total = 0;
				}
				//Cycle through all rows in this column that are in this block
				for(int n = y; n < y + height && n < image.getHeight(); n++){
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
