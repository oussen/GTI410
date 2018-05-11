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

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.ImageObserver;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.filechooser.FileFilter;

import controller.MementoIF;

/**
 * <p>Title: ImageX</p>
 * <p>Description: Subclass of a Shape, representing an Image.</p>
 * <p>Copyright: Copyright (c) 2003 Colin Barré-Brisebois, Éric Paquette</p>
 * <p>Company: ETS - École de Technologie Supérieure</p>
 * @author unascribed
 * @version $Revision: 1.21 $
 */
public class ImageX extends Shape implements ShapeObservable {
	private String filename = "";                      
	private int width = 0;
	private int height = 0;
	private boolean isUpdatingPixels = false;
	private Image image = null;
	private int[] pixels = null;
	private PixelGrabber pixelGrabber = null;
	private Toolkit toolkit = Toolkit.getDefaultToolkit();

	public ImageX(String filename) {
		this(0, 0, filename);
		setCenter(getImageWidth()/2,getImageHeight()/2);
   	}
   	
	/**
	 * Creates an ImageX class with an image file
	 */
	public ImageX(int centerX, int centerY, String filename) {
		super(centerX, centerY);
		loadImage(filename);   
	}
	
	public ImageX(int centerX, int centerY, int width, int height) {
		super(centerX, centerY);
		this.filename = ""; 
		this.width = width;
		this.height = height;
		
		pixels = new int[width * height];
		image = toolkit.createImage(new MemoryImageSource(width, height, pixels, 0, width));

		updatePixelGrabber();
	}

	public int getImageWidth() {
		return image.getWidth(null);
	}
    
	public int getImageHeight() {
		return image.getHeight(null);
	}
    
	/**
	 * Loads an image from a specified file
	 */
	public void loadImage(String filename) {     
		ImageIcon tmpIcon = null;
		this.filename = filename; 

		image = toolkit.createImage(this.filename);
        
		tmpIcon = new ImageIcon(image);
		width = tmpIcon.getIconWidth();
		height = tmpIcon.getIconHeight();
                
		updatePixelGrabber();      
	}     
     
	public boolean isThisPointInHook(Point aPt) {
		int distFromCenterX = (int)Math.abs(aPt.getX()-getCenter().getX());
		int distFromCenterY = (int)Math.abs(aPt.getY()-getCenter().getY());
		boolean onX = (distFromCenterX <= width / 2);
		boolean onY = (distFromCenterY <= height / 2);

		if (onX && onY) {
		  return true;
		} else{
		  return false;
		}
	}

	/**
	 * Loads the pixel grabber and stores the pixel data in the pixel array
	 */ 
	private void updatePixelGrabber() {
		pixels = new int[width * height];
            
		try {
			pixelGrabber = new PixelGrabber(image,
											0, 
											0, 
											width,
											height,
											pixels,
											0,
											width);
        
			pixelGrabber.grabPixels();
            
			if ((pixelGrabber.getStatus() & ImageObserver.ABORT) != 0) {
				System.err.println("Image fetch aborded or errored.");
			}
		} catch (InterruptedException e) {
			System.err.println("Interrupted while initializing PixelGrabber.");
		}
	}    
   
   /**
	* Draws the image 
	*/
	public void drawHook(Graphics gfx) {
		gfx.drawImage(image, 
					  (int)(getCenter().getX()-width/2),
					  (int)(getCenter().getY()-height/2), 
					  width, 
					  height, 
					  null);
	}
	
	public Point getPosition() {
		return new Point((int)(getCenter().getX()-width/2),(int)(getCenter().getY()-height/2)); 		
	}
         
	/**
	 * DirectX scene rendering style (Begin()...End()) applied to pixel modification.
	 * Call this method before modifying pixels of the image.
	 */
	public void beginPixelUpdate() {
		isUpdatingPixels = true;
	}

	/**
	 * DirectX scene rendering style (begin()...end()) applied to pixel modification.
	 * Call this method to finish modifying pixels of the image.
	 */
	public void endPixelUpdate() {
		if (isUpdatingPixels) {
			image = toolkit.createImage(new MemoryImageSource(width, height, pixels, 0, width));
			isUpdatingPixels = false;
			notifyObservers();	
		}
	}
	    
	/**
	 * Set an image's pixel ARGB color value with an int.
	 * You have to call this between beginPixelUpdate() and endPixelUpdate().
	 * @param x X coordinate of the pixel.
	 * @param y Y coordinate of the pixel.
	 * @param valueARGB ARGB value of the pixel color.
	 */
	public void setPixel(int x, int y, int valueARGB) {
		if (isUpdatingPixels)
			pixels[y * width + x] = valueARGB;
	}

	/**
	 * Set an image's pixel ARGB color value with an Pixel.
	 * You have to call this between beginPixelUpdate() and endPixelUpdate().
	 * @param x X coordinate of the pixel.
	 * @param y Y coordinate of the pixel.
	 * @param pixel Pixel containing the color value to set
	 */
	public void setPixel(int x, int y, Pixel pixel) {  	
		if (isUpdatingPixels)
			pixels[y * width + x] = pixel.getARGB();
	}

	/**
	 * Get the ARGB int value of a pixel at a specified coordinate. 
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @return the ARGB color value of the pixel
	 */
	public int getPixelInt(int x, int y) {
		return pixels[y * width + x];
	}
    
	/**
	 * Get a COPY of the Pixel at a specified coordinate. 
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @return the Pixel at the specified coordinate
	 */   
	public Pixel getPixel(int x, int y) {
		return new Pixel(pixels[y * width + x]);    	
	}

	public java.awt.Rectangle getRectangle() {
		return new java.awt.Rectangle((int)(getCenter().getX()-width/2),
						   (int)(getCenter().getY()-height/2),
						   width,
						   height);
	}

	public java.awt.Rectangle getTransformedRectangle() {
		Point transformedCenter = getTransformedCenter();
		return new java.awt.Rectangle((int)(transformedCenter.getX()-width/2),
						   (int)(transformedCenter.getY()-height/2),
						   width,
						   height);
	}

	// @see model.ColoredShape#createMemento()
    public MementoIF createMemento() {
		return new ImageMemento(super.createMemento(), filename);
    }
    
    // @see model.ColoredShape#setMemento(controller.MementoIF)
    public void setMemento(MementoIF memento) {
        ImageMemento m = (ImageMemento)memento;
		loadImage(filename);
		super.setMemento(m.getParent());
    }
    
    /** Supports undo. */
	private class ImageMemento implements MementoIF {
		private final MementoIF memParent;
		private final String memFilename;
	    /**
	     * @param m the parent memento
	     * @param s the image filename
	     */
		ImageMemento(MementoIF m, String s) {
			memParent = m;
			memFilename = s;
		}
		MementoIF getParent() { return memParent; }
		String getFilename() { return memFilename; }
	}
	
	/**
	 * <p>Title: ImageXExtensionsFilter</p>
	 * <p>Description: Extension filtering class used by the JFileChooser.
	 * REFERENCE: http://java.sun.com/docs/books/tutorial/uiswing/components/filechooser.html#filters</p>
	 * <p>Copyright: Copyright (c) 2003 Colin Barré-Brisebois (LOG120-LABO4)</p>
	 * <p>Company: ETS - École de Technologie Supérieure</p>
	 * @author unascribed
	 * @version $Revision: 1.21 $
	 */
	public static class ImageXExtensionsFilter extends FileFilter {
		public final static String filters[] = {"gif", "jpeg", "jpg", "png"};

		public static String getExtension(File f) {
			String ext = null;
			String s = f.getName();
			int i = s.lastIndexOf('.');

			if (i > 0 &&  i < s.length() - 1) {
				ext = s.substring(i+1).toLowerCase();
			}
			
			return ext;
		}

		public boolean accept(File f) {
			String extension = getExtension(f);

			if (f.isDirectory()) {
				return true;
			} else if (extension != null) {
				for (int i = 0; i < filters.length; ++i) {
					if (extension.equals(filters[i])) {
						return true;
					}
				}
				return false;
			} else {
				return false;
			}
		}

		public String getDescription() {
			String extensions = new String("*." + filters[0]);
			for (int i = 1; i < filters.length; ++i) {
				extensions += ", *." + filters[i];
			}
			return "Images (" + extensions + ")";
		}
	}    
}