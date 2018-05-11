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
package model;

/**
 * <p>Title: PixelDouble</p>
 * <p>Description: Class that handles Pixel data with doubles for each component.</p>
 * <p>Copyright: Copyright (c) 2003 Colin Barré-Brisebois,  Eric Paquette</p>
 * <p>Company: ETS - École de Technologie Supérieure</p>
 * @author Colin Barré-Brisebois
 * @version $Revision: 1.5 $
 */
public class PixelDouble {
	private double red;
	private double green;
	private double blue;
	private double alpha;
	
	/**
	 * Default constructor.
	 */
	public PixelDouble() {
		red = 0;
		green = 0;
		blue = 0;
		alpha = 0;
	}
	
	/**
	 * Constructor with specified pixel values.
	 * @param red red value
	 * @param green green value
	 * @param blue blue value
	 * @param alpha alpha value
	 */
	public PixelDouble(double red,
					   double green,
					   double blue,
					   double alpha) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}
	
	/**
	 * Copy Constructor.
	 * @param pixelDouble PixelDouble to copy from
	 */
	public PixelDouble(PixelDouble pixelDouble) {
		setRed(pixelDouble.getRed());
		setGreen(pixelDouble.getGreen());
		setBlue(pixelDouble.getBlue());
		setAlpha(pixelDouble.getAlpha());
	}
	
	public PixelDouble(Pixel pixel) {
		setRed(pixel.getRed());
		setGreen(pixel.getGreen());
		setBlue(pixel.getBlue());
		setAlpha(pixel.getAlpha());
	}	

	/**
	 * Sets the red value
	 * @param red red value
	 */
	public void setRed(double red) {
		this.red = red;
	}

	/**
	 * Sets the green value
	 * @param green green value
	 */
	public void setGreen(double green) {
		this.green = green;
	}
	
	/**
	 * Sets the blue value
	 * @param blue blue value
	 */	
	public void setBlue(double blue) {
		this.blue = blue;
	}

	/**
	 * Sets the alpha value
	 * @param alpha alpha value
	 */		
	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}	

	/**
	 * Gets the pixel's red value 
	 * @return pixel's red value
	 */			
	public double getRed() {
		return red;
	}

	/**
	 * Gets the pixel's green value 
	 * @return pixel's green value
	 */	
	public double getGreen() {
		return green;
	}
	
	/**
	 * Gets the pixel's blue value 
	 * @return pixel's blue value
	 */	
	public double getBlue() {
		return blue;
	}
	
	/**
	 * Gets the pixel's alpha value 
	 * @return pixel's alpha value
	 */
	public double getAlpha() {
		return alpha;
	}
}
