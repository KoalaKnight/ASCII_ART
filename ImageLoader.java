import java.awt.image.BufferedImage;
import java.awt.Color;
import javax.imageio.ImageIO;
import java.io.File;

/**
*ImageLoader is a class that loads an image from the hard drive into a BufferedImage. It also has methods for 
*converting the image to grayscale and saving the grayscale image to the hard drive.
*
*@author	Toby Flynn
*/
public class ImageLoader {
	private BufferedImage image;
	
	/**
	*Constructor method for ImageLoader that loads an image.
	*
	*@param		filename	the filename (or path) of the image
	*/
	public ImageLoader(String filename) {
		try {
			File file = new File(filename);
			image = ImageIO.read(file);
		} catch (Exception e) {
			System.out.println("Error when reading image: ");
			e.printStackTrace();
		}
	}
	
	/**
	*Getter method for the image that was loaded in the constructor.
	*
	*@return				the BufferedImage that was loaded in the constructor
	*/
	public BufferedImage getImage() {
		return image;
	}
	
	/**
	*Method that converst the BufferedImage to grayscale (using the luminosity method).
	*
	*@return				the grayscale image
	*/
	public BufferedImage getGrayscale() {
		BufferedImage grayscale = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		//Cycle through each pixel
		for(int y = 0; y < image.getHeight(); y++){
			for(int x = 0; x < image.getWidth(); x++){
				Color colour = new Color(image.getRGB(x, y));
				//Get intensity of grey using the luminosity method
				int grey = (int)(0.21 * colour.getRed() + 0.72 * colour.getGreen() + 0.07 * colour.getBlue());
				colour = new Color(grey, grey, grey);
				//Set colour of pixel in the result BufferedImage
				grayscale.setRGB(x, y, colour.getRGB());
			}
		}
		return grayscale;
	}
	
	/**
	*Method that saves a grayscale version of the BufferedImage to disk.
	*
	*@param		filename	the filename which the image will be saved as
	*@throws	Exception
	*/
	public void saveGrayscale(String filename) throws Exception {
		ImageIO.write(getGrayscale(), "png", new File(filename));
	}
}
