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

import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;

import controller.FillColorsMediator;
import controller.FillTypeRadioButtonMediator;
import controller.ImageLineFiller;
import controller.SliderMediator;

/**
 * 
 * <p>Title: FillPanel</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004 Sébastien Bois, Eric Paquette</p>
 * <p>Company: (ÉTS) - École de Technologie Supérieure</p>
 * @author unascribed
 * @version $Revision: 1.3 $
 */
public class FillTransformation extends JPanel {
	private JPanel thresholdSlidersPanel;
	private JPanel fillcolorRadioButtonPanel;
	private JPanel fillcolorButtonsPanel;
	
	private JSlider hTreshold;
	private JSlider sTreshold;
	private JSlider vTreshold;
	
	private JLabel  hTresholdValue;
	private JLabel  sTresholdValue;
	private JLabel  vTresholdValue;
	
	private JRadioButton rbBoundaryFill;
	private JRadioButton rbFloodFill;

	private Vector rbVector;
	private FillTypeRadioButtonMediator rbMediator;
	
	private JButton bFillColor;
	private JButton bBoundaryColor;

	public FillTransformation(ImageLineFiller filler){
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			
		JPanel slidersPanel = new JPanel();
		slidersPanel.setLayout(new GridLayout(3,1));
		slidersPanel.setBorder(BorderFactory.createTitledBorder(" Threshold "));
		
		JPanel fillcolorRadioButtonPanel = new JPanel();
		fillcolorRadioButtonPanel.setLayout(new GridLayout(2,1));
		fillcolorRadioButtonPanel.setBorder(BorderFactory.createTitledBorder(" Fill type "));
		
		JPanel fillcolorButtonPanel = new JPanel();
		fillcolorButtonPanel.setLayout(new GridLayout(2,2));
		fillcolorButtonPanel.setBorder(BorderFactory.createTitledBorder(" Color type "));
		
		JSlider hThreshold = new JSlider(JSlider.HORIZONTAL, 0, 180, 0);
		hThreshold.setMajorTickSpacing(60);
		hThreshold.setMinorTickSpacing(20);
		hThreshold.setPaintTicks(true);
		hThreshold.setPaintLabels(true);
				
		JSlider sThreshold = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
		sThreshold.setMajorTickSpacing(85);
		sThreshold.setMinorTickSpacing(28);
		sThreshold.setPaintTicks(true);
		sThreshold.setPaintLabels(true);
				
		JSlider vThreshold = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
		vThreshold.setMajorTickSpacing(85);
		vThreshold.setMinorTickSpacing(28);
		vThreshold.setPaintTicks(true);
		vThreshold.setPaintLabels(true);
		
		JLabel hThresholdValue = new JLabel();
		JLabel sThresholdValue = new JLabel();
		JLabel vThresholdValue = new JLabel();

		JPanel hPanel = new JPanel();
		hPanel.setLayout(new BoxLayout(hPanel, BoxLayout.X_AXIS));		
		hPanel.add(new JLabel("Threshold H"));
		hPanel.add(hThreshold);
		hPanel.add(hThresholdValue);
		slidersPanel.add(hPanel);
		
		JPanel sPanel = new JPanel();
		sPanel.setLayout(new BoxLayout(sPanel, BoxLayout.X_AXIS));		
		sPanel.add(new JLabel("Threshold S"));
		sPanel.add(sThreshold);
		sPanel.add(sThresholdValue);
		slidersPanel.add(sPanel);
		
		JPanel vPanel = new JPanel();
		vPanel.setLayout(new BoxLayout(vPanel, BoxLayout.X_AXIS));		
		vPanel.add(new JLabel("Threshold V"));
		vPanel.add(vThreshold);
		vPanel.add(vThresholdValue);
		slidersPanel.add(vPanel);
		
		new SliderMediator(hThreshold, hThresholdValue, 
						   sThreshold, sThresholdValue, 
						   vThreshold, vThresholdValue,
						   filler);
		
		JRadioButton rbBoundaryFill	= new JRadioButton("Boundary fill");
		fillcolorRadioButtonPanel.add(rbBoundaryFill);
		
		JRadioButton rbFloodFill = new JRadioButton("Flood fill");
		fillcolorRadioButtonPanel.add(rbFloodFill);
		
		FillTypeRadioButtonMediator rbMediator	= new FillTypeRadioButtonMediator(rbBoundaryFill, rbFloodFill, filler);
		
		bFillColor    = new JButton();
		bBoundaryColor = new JButton();
		
		FillColorsMediator fillColorsMediator = new FillColorsMediator(filler, bFillColor, bBoundaryColor);
		
		fillcolorButtonPanel.add(bFillColor);
		fillcolorButtonPanel.add(new JLabel(" Fill color "));
		
		fillcolorButtonPanel.add(bBoundaryColor);
		fillcolorButtonPanel.add(new JLabel(" Boundary color "));
		
		new SliderMediator(hThreshold, hThresholdValue, 
						   sThreshold, sThresholdValue, 
						   vThreshold, vThresholdValue,
						   filler);
		
		add(slidersPanel);
		add(fillcolorRadioButtonPanel);
		add(fillcolorButtonPanel);
	}
}
