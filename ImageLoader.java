import java.awt.image.BufferedImage;
import java.awt.Color;
import javax.imageio.ImageIO;
import java.io.File;

public class ImageLoader {
	private BufferedImage image;
	
	public ImageLoader(String filename) {
		try {
			File file = new File(filename);
			image = ImageIO.read(file);
		} catch (Exception e) {
			System.out.println("Error when reading image: ");
			e.printStackTrace();
		}
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public BufferedImage getGrayScale() {
		BufferedImage grayscale = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		for(int y = 0; y < image.getHeight(); y++){
			for(int x = 0; x < image.getWidth(); x++){
				Color colour = new Color(image.getRGB(x, y));
				int grey = (int)(0.21 * colour.getRed() + 0.72 * colour.getGreen() + 0.07 * colour.getBlue());
				colour = new Color(grey, grey, grey);
				grayscale.setRGB(x, y, colour.getRGB());
			}
		}
		return grayscale;
	}
	
	public void saveGrayScale(String filename) throws Exception {
		ImageIO.write(getGrayScale(), "png", new File(filename));
	}
	
	public static void main(String[] args) {
		/*ImageLoader imgLoader = new ImageLoader("test.jpg");
		try {
			imgLoader.saveGrayScale("a.png");
		} catch(Exception e) {
			e.printStackTrace();
		}*/
		System.out.println((255 / 26));
	}
}
