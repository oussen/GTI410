package view;


import java.awt.image.BufferedImage;

import model.ObserverIF;
import model.Pixel;

public class CMYKColorMediator extends Object implements SliderObserver, ObserverIF {
	ColorSlider CyanCS;
	ColorSlider MagentaCS;
	ColorSlider YellowCS;
	ColorSlider BlackCS;
	int cyan;
	int magenta;
	int yellow;
	int black;
	BufferedImage cyanImage;
	BufferedImage magentaImage;
	BufferedImage yellowImage;
	BufferedImage blackImage;

	int imagesWidth;
	int imagesHeight;
	ColorDialogResult result;


	CMYKColorMediator(ColorDialogResult result, int imagesWidth, int imagesHeight) {
		this.imagesWidth = imagesWidth;
		this.imagesHeight = imagesHeight;
		this.cyan = result.getPixel().getRed();
		this.magenta = result.getPixel().getGreen();
		this.yellow = result.getPixel().getBlue();
		this.black = result.getPixel().getBlue();
		this.result = result;
		result.addObserver(this);
	   }

		/*
		 * @see View.SliderObserver#update(double)
		 */
		public void update(ColorSlider s, int v) {
			boolean updateCyan = false;
			boolean updateMagenta = false;
			boolean updateYellow = false;
			if (s == MagentaCS && v != magenta) {
				magenta = v;
				updateMagenta = true;
				updateYellow = true;
			}
			if (s == MagentaCS && v != magenta) {
				magenta = v;
				updateCyan = true;
				updateYellow = true;
			}
			if (s == YellowCS && v != yellow) {
				yellow = v;
				updateCyan = true;
				updateMagenta = true;
			}
			if (updateCyan) {
				computeCyanImage(cyan, magenta, yellow);
			}
			if (updateMagenta) {
				computeMagentaImage(cyan, magenta, yellow);
			}
			if (updateYellow) {
				computeYellowImage(cyan, magenta, yellow);
			}

			Pixel pixel = new Pixel(cyan, magenta, yellow, 255);
			result.setPixel(pixel);
		}

		public void computeCyanImage(int cyan, int magenta, int yellow) { 
			int [] cmyTab = CmyToRgb(cyan,magenta,yellow,black);
			Pixel p = new Pixel(cmyTab[0], cmyTab[1], cmyTab[2],255); 
			for (int i = 0; i<imagesWidth; ++i) {
				p.setRed((int)(((double)i / (double)imagesWidth)*255.0)); 
				int rgb = p.getARGB();
				for (int j = 0; j<imagesHeight; ++j) {
					cyanImage.setRGB(i, j, rgb);
				}
			}
			if (CyanCS != null) {
				CyanCS.update(cyanImage);
			}
		}

		public void computeMagentaImage(int cyan, int magenta, int yellow) { 
			Pixel p = new Pixel(cyan, magenta, yellow, 255); 
			for (int i = 0; i<imagesWidth; ++i) {
				p.setGreen(1-(int)(((double)i / (double)imagesWidth)*255.0)); 
				int rgb = p.getARGB();
				for (int j = 0; j<imagesHeight; ++j) {
					magentaImage.setRGB(i, j, rgb);
				}
			}
			if (MagentaCS != null) {
				MagentaCS.update(magentaImage);
			}
		}

		public void computeYellowImage(int cyan, int magenta, int yellow) { 
			Pixel p = new Pixel(cyan, magenta, yellow, 255); 
			for (int i = 0; i<imagesWidth; ++i) {
				p.setBlue(1-(int)(((double)i / (double)imagesWidth)*255.0)); 
				int rgb = p.getARGB();
				for (int j = 0; j<imagesHeight; ++j) {
					yellowImage.setRGB(i, j, rgb);
				}
			}
			if (YellowCS != null) {
				YellowCS.update(yellowImage);
			}
		}

		/*
public void computeBlackImage(int cyan, int magenta, int yellow,int black) { 
	Pixel p = new Pixel(cyan, magenta, yellow, 255); 
	for (int i = 0; i<imagesWidth; ++i) {
		p.setRed((int)(((double)i / (double)imagesWidth)*255.0)); 
		int rgb = p.getARGB();
		for (int j = 0; j<imagesHeight; ++j) {
			cyanImage.setRGB(i, j, rgb);
		}
	}
	if (CyanCS != null) {
		CyanCS.update(cyanImage);
	}
}
		 */

		/**
		 * @return
		 */
		public BufferedImage getYellowImage() {
			return yellowImage;
		}

		/**
		 * @return
		 */
		public BufferedImage getMagentaImage() {
			return magentaImage;
		}

		/**
		 * @return
		 */
		public BufferedImage getRedImage() {
			return cyanImage;
		}

		/**
		 * @param slider
		 */
		public void setRedCS(ColorSlider slider) {
			CyanCS = slider;
			slider.addObserver(this);
		}

		/**
		 * @param slider
		 */
		public void setGreenCS(ColorSlider slider) {
			MagentaCS = slider;
			slider.addObserver(this);
		}

		/**
		 * @param slider
		 */
		public void setBlueCS(ColorSlider slider) {
			YellowCS = slider;
			slider.addObserver(this);
		}
		/**
		 * @return
		 */
		public double getYellow() {
			return yellow;
		}

		/**
		 * @return
		 */
		public double getMagenta() {
			return magenta;
		}

		/**
		 * @return
		 */
		public double getCyan() {
			return cyan;
		}
		
		/*
		 * Source : https://www.rapidtables.com/convert/color/rgb-to-cmyk.html
		 */
		
		
		public int [] RgbToCmy (int r, int g, int b) {
			
	
			int [] rgbTab = new int [3];
			
			int r1 = r/255;
			int g1 = g/255;
			int b1 = b/255;
			int black = 1- Math.min(r,Math.min(g,b));
			
			rgbTab [0] = (1 - r1 - black ) / (1 -black);
			rgbTab [1] = (1 - g1 - black ) / (1 -black);
			rgbTab [2] = (1 - b1 - black ) / (1 -black);
		
			return rgbTab;
			
			
		}
		
		/*
		 * Source : https://www.rapidtables.com/convert/color/cmyk-to-rgb.html
		 */
		
		public int [] CmyToRgb (int cyan, int magenta, int yellow, int black) {
			int [] rgbTab = RgbToCmy(cyan, magenta, yellow);
			int [] cmyTab = new int [3];
			
			cmyTab[0] = 255 * (1-rgbTab[0]*(1-black));
			cmyTab[1] = 255 * (1-rgbTab[1]*(1-black));
			cmyTab[2] = 255 * (1-rgbTab[2]*(1-black));
			
			return cmyTab;
			
			
		}
			
		
			
		


		/* (non-Javadoc)
		 * @see model.ObserverIF#update()
		 */
		public void update() {
			// When updated with the new "result" color, if the "currentColor"
			// is aready properly set, there is no need to recompute the images.
			Pixel currentColor = new Pixel(cyan, magenta, yellow, 255);
			if(currentColor.getARGB() == result.getPixel().getARGB()) return;

			cyan = result.getPixel().getRed();
			magenta = result.getPixel().getGreen();
			yellow = result.getPixel().getBlue();

			CyanCS.setValue(cyan);
			MagentaCS.setValue(magenta);
			YellowCS.setValue(yellow);
			computeCyanImage(cyan, magenta, yellow);
			computeMagentaImage(cyan, magenta, yellow);
			computeYellowImage(cyan, magenta, yellow);

			// Efficiency issue: When the color is adjusted on a tab in the 
			// user interface, the sliders color of the other tabs are recomputed,
			// even though they are invisible. For an increased efficiency, the 
			// other tabs (mediators) should be notified when there is a tab 
			// change in the user interface. This solution was not implemented
			// here since it would increase the complexity of the code, making it
			// harder to understand.
		}

	}