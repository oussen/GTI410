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

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import controller.Dispatcher;
import controller.TransformersIndex;

/**
 * 
 * <p>Title: ButtonsPanel</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004 Sébastien Bois, Eric Paquette</p>
 * <p>Company: (ÉTS) - École de Technologie Supérieure</p>
 * @author unascribed
 * @version $Revision: 1.13 $
 */

public class ButtonsPanel extends JPanel 
{
	private JToggleButton jSelect;
	private JToggleButton jFill;
	private JToggleButton jFilter;
	private JToggleButton jCurves;
	
	private JToggleButton jTranslate;
	private JToggleButton jRotate;
	private JToggleButton jScale;
	private JToggleButton jShear;
	
	
	private TransformersIndex ti;
	private Dispatcher dispatcher;
	
	public ButtonsPanel(Dispatcher d)
	{
		ti = d.getTransformersIndex();
		dispatcher = d;
		
		jSelect  = new JToggleButton("Select", true);
		jFill    = new JToggleButton("Fill");
		jFilter  = new JToggleButton("Filter");
		jCurves  = new JToggleButton("Curves");
		
		jTranslate = new JToggleButton("Translate");
		jScale    = new JToggleButton("Scale");
		jShear    = new JToggleButton("Shear");
		jRotate   = new JToggleButton("Rotate");
		
		ButtonGroup buttonsList = new ButtonGroup();
		buttonsList.add(jSelect);
		buttonsList.add(jFill);
		buttonsList.add(jFilter);
		buttonsList.add(jCurves);
		buttonsList.add(jTranslate);
		buttonsList.add(jScale);
		buttonsList.add(jShear);
		buttonsList.add(jRotate);
		
		// set the layout of this panel
		setLayout(new GridLayout(buttonsList.getButtonCount(),1));
		
		add(jSelect);
		add(jFill);
		add(jFilter);
		add(jCurves);
		add(jTranslate);
		add(jScale);
		add(jShear);
		add(jRotate);
				
		jSelect.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JPanel pp = Application.getInstance().getParametersPanel();
				CardLayout cl = (CardLayout)pp.getLayout();
				cl.show(pp, "ColorParameterPanel");
				dispatcher.setTheCurrentTransformer(ti.getTheSelector());
				ti.setTheDefaultTransformer(ti.getTheSelector());
			}
		});
				
		jFill.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JPanel pp = Application.getInstance().getParametersPanel();
				CardLayout cl = (CardLayout)pp.getLayout();
				cl.show(pp, "FillPanel");
				dispatcher.setTheCurrentTransformer(ti.getTheFiller());
				ti.setTheDefaultTransformer(ti.getTheFiller());
			}
		});
		
		jFilter.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JPanel pp = Application.getInstance().getParametersPanel();
				CardLayout cl = (CardLayout)pp.getLayout();
				cl.show(pp, "FilterKernelPanel");
				
				dispatcher.setTheCurrentTransformer(ti.getTheFilter());
				ti.setTheDefaultTransformer(ti.getTheFilter());
			}
		});
		
		jCurves.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JPanel pp = Application.getInstance().getParametersPanel();
				CardLayout cl = (CardLayout)pp.getLayout();
				cl.show(pp, "CurvesPanel");
				
				dispatcher.setTheCurrentTransformer(ti.getTheCurves());
				ti.setTheDefaultTransformer(ti.getTheCurves());
			}
		});
		
		jTranslate.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JPanel pp = Application.getInstance().getParametersPanel();
				CardLayout cl = (CardLayout)pp.getLayout();
				cl.show(pp, "TranslationTransformation");
				
				dispatcher.setTheCurrentTransformer(ti.getTheTranslation());
				ti.setTheDefaultTransformer(ti.getTheTranslation());
			}
		});
		
		jScale.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JPanel pp = Application.getInstance().getParametersPanel();
				CardLayout cl = (CardLayout)pp.getLayout();
				cl.show(pp, "ScalingTransformation");
				
				dispatcher.setTheCurrentTransformer(ti.getTheScale());
				ti.setTheDefaultTransformer(ti.getTheScale());
			}
		});
		
		jShear.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JPanel pp = Application.getInstance().getParametersPanel();
				CardLayout cl = (CardLayout)pp.getLayout();
				cl.show(pp, "ShearingTransformation");
				
				dispatcher.setTheCurrentTransformer(ti.getTheShear());
				ti.setTheDefaultTransformer(ti.getTheShear());
			}
		});
		
		jRotate.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JPanel pp = Application.getInstance().getParametersPanel();
				CardLayout cl = (CardLayout)pp.getLayout();
				cl.show(pp, "RotationTransformation");
				
				dispatcher.setTheCurrentTransformer(ti.getTheRotation());
				ti.setTheDefaultTransformer(ti.getTheRotation());
			}
		});		
	}
}
