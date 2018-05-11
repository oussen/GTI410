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
 * <p>Title: ImageDouble</p>
 * <p>Description: Image class where RGBA components are stored as PixelDoubles.</p>
 * <p>Copyright: Copyright (c) 2003 Colin Barré-Brisebois</p>
 * <p>Company: ETS - École de Technologie Supérieure</p>
 * @author unascribed
 * @version $Revision: 1.11 $
 */

public class ImageDouble {
	private PixelDouble[][] pixels = null;
	private int width;
	private int height;
	
	/**
	 * Default constructor with specified width/height.
	 * @param width width of the image
	 * @param height height of the image
	 */
	public ImageDouble(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new PixelDouble[width][height];
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				pixels[x][y] = new PixelDouble();
			}
		}
	}

	/**
	 * Creates an ImageDouble from an already existing ImageDouble.
	 * @param src the ImageDouble  
	 */
	public ImageDouble(ImageDouble src) {
		this(src.getImageWidth(), src.getImageHeight());
		PixelDouble currentPixel = null;
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				currentPixel = src.getPixel(x,y);
				
				pixels[x][y].setAlpha(currentPixel.getAlpha());
				pixels[x][y].setRed(currentPixel.getRed());
				pixels[x][y].setGreen(currentPixel.getGreen());
				pixels[x][y].setBlue(currentPixel.getBlue());
			}
		}	
	}
	
	/**
	 * Returns the PixelDouble array associated to the ImageDouble
	 * @return PixelDouble array
	 */
	public PixelDouble[][] getPixelsArray() {
		return pixels;
	}

	/**
	 * Sets the PixelDouble at the specified coordinates.
	 * @param x x coordinate value
	 * @param y y coordinate value
	 * @param  
	 */	
	public void setPixel(int x, int y, PixelDouble value) {
		pixels[x][y] = value;
	}
	
	/**
	 * Gets the PixelDouble at the specified coordinates
	 * @param x x coordinate value
	 * @param y y coordinate value
	 * @return the PixelDouble at specified coordinates
	 */
	public PixelDouble getPixel(int x, int y) {
		return pixels[x][y];
	}
	
	/**
	 * Gets the ImageDouble's width.
	 * @return ImageDouble's width
	 */
	public int getImageWidth() {
		return width;
	}

	/**
	 * Gets the ImageDouble's height.
	 * @return ImageDouble's height
	 */	
	public int getImageHeight() {
		return height;
	}
}
