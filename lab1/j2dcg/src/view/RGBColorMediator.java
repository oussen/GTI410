/*
   This file is part of j2dcg.
   j2dcg is free software; you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation; either version 2 of the License, or
   (at your option) any later version.
   j2dcg is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.
   You should have received a copy of the GNU General Public License
   along with j2dcg; if not, write to the Free Software
   Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/

package view;

import java.awt.image.BufferedImage;

import model.ObserverIF;
import model.Pixel;

class RGBColorMediator extends Object implements SliderObserver, ObserverIF {
	ColorSlider redCS;
	ColorSlider greenCS;
	ColorSlider blueCS;
	int red;
	int green;
	int blue;
	BufferedImage redImage;
	BufferedImage greenImage;
	BufferedImage blueImage;
	int imagesWidth;
	int imagesHeight;
	ColorDialogResult result;
	
	RGBColorMediator(ColorDialogResult result, int imagesWidth, int imagesHeight) {
		this.imagesWidth = imagesWidth;
		this.imagesHeight = imagesHeight;
		this.red = result.getPixel().getRed();
		this.green = result.getPixel().getGreen();
		this.blue = result.getPixel().getBlue();
		this.result = result;
		result.addObserver(this);
		
		redImage = new BufferedImage(imagesWidth, imagesHeight, BufferedImage.TYPE_INT_ARGB);
		greenImage = new BufferedImage(imagesWidth, imagesHeight, BufferedImage.TYPE_INT_ARGB);
		blueImage = new BufferedImage(imagesWidth, imagesHeight, BufferedImage.TYPE_INT_ARGB);
		computeRedImage(red, green, blue);
		computeGreenImage(red, green, blue);
		computeBlueImage(red, green, blue); 	
	}
	
	
	/*
	 * @see View.SliderObserver#update(double)
	 */
	public void update(ColorSlider s, int v) {
		boolean updateRed = false;
		boolean updateGreen = false;
		boolean updateBlue = false;
		if (s == redCS && v != red) {
			red = v;
			updateGreen = true;
			updateBlue = true;
		}
		if (s == greenCS && v != green) {
			green = v;
			updateRed = true;
			updateBlue = true;
		}
		if (s == blueCS && v != blue) {
			blue = v;
			updateRed = true;
			updateGreen = true;
		}
		if (updateRed) {
			computeRedImage(red, green, blue);
		}
		if (updateGreen) {
			computeGreenImage(red, green, blue);
		}
		if (updateBlue) {
			computeBlueImage(red, green, blue);
		}
		
		Pixel pixel = new Pixel(red, green, blue, 255);
		result.setPixel(pixel);
	}
	
	public void computeRedImage(int red, int green, int blue) { 
		Pixel p = new Pixel(red, green, blue, 255); 
		for (int i = 0; i<imagesWidth; ++i) {
			p.setRed((int)(((double)i / (double)imagesWidth)*255.0)); 
			int rgb = p.getARGB();
			for (int j = 0; j<imagesHeight; ++j) {
				redImage.setRGB(i, j, rgb);
			}
		}
		if (redCS != null) {
			redCS.update(redImage);
		}
	}
	
	public void computeGreenImage(int red, int green, int blue) {
		Pixel p = new Pixel(red, green, blue, 255); 
		for (int i = 0; i<imagesWidth; ++i) {
			p.setGreen((int)(((double)i / (double)imagesWidth)*255.0)); 
			int rgb = p.getARGB();
			for (int j = 0; j<imagesHeight; ++j) {
				greenImage.setRGB(i, j, rgb);
			}
		}
		if (greenCS != null) {
			greenCS.update(greenImage);
		}
	}
	
	public void computeBlueImage(int red, int green, int blue) { 
		Pixel p = new Pixel(red, green, blue, 255); 
		for (int i = 0; i<imagesWidth; ++i) {
			p.setBlue((int)(((double)i / (double)imagesWidth)*255.0)); 
			int rgb = p.getARGB();
			for (int j = 0; j<imagesHeight; ++j) {
				blueImage.setRGB(i, j, rgb);
			}
		}
		if (blueCS != null) {
			blueCS.update(blueImage);
		}
	}
	
	/**
	 * @return
	 */
	public BufferedImage getBlueImage() {
		return blueImage;
	}

	/**
	 * @return
	 */
	public BufferedImage getGreenImage() {
		return greenImage;
	}

	/**
	 * @return
	 */
	public BufferedImage getRedImage() {
		return redImage;
	}

	/**
	 * @param slider
	 */
	public void setRedCS(ColorSlider slider) {
		redCS = slider;
		slider.addObserver(this);
	}

	/**
	 * @param slider
	 */
	public void setGreenCS(ColorSlider slider) {
		greenCS = slider;
		slider.addObserver(this);
	}

	/**
	 * @param slider
	 */
	public void setBlueCS(ColorSlider slider) {
		blueCS = slider;
		slider.addObserver(this);
	}
	/**
	 * @return
	 */
	public double getBlue() {
		return blue;
	}

	/**
	 * @return
	 */
	public double getGreen() {
		return green;
	}

	/**
	 * @return
	 */
	public double getRed() {
		return red;
	}


	/* (non-Javadoc)
	 * @see model.ObserverIF#update()
	 */
	public void update() {
		// When updated with the new "result" color, if the "currentColor"
		// is aready properly set, there is no need to recompute the images.
		Pixel currentColor = new Pixel(red, green, blue, 255);
		if(currentColor.getARGB() == result.getPixel().getARGB()) return;
		
		red = result.getPixel().getRed();
		green = result.getPixel().getGreen();
		blue = result.getPixel().getBlue();
		
		redCS.setValue(red);
		greenCS.setValue(green);
		blueCS.setValue(blue);
		computeRedImage(red, green, blue);
		computeGreenImage(red, green, blue);
		computeBlueImage(red, green, blue);
		
		// Efficiency issue: When the color is adjusted on a tab in the 
		// user interface, the sliders color of the other tabs are recomputed,
		// even though they are invisible. For an increased efficiency, the 
		// other tabs (mediators) should be notified when there is a tab 
		// change in the user interface. This solution was not implemented
		// here since it would increase the complexity of the code, making it
		// harder to understand.
	}

}

