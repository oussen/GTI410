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

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import model.CurvesModel;
import model.DocObserver;
import controller.Curves;
import controller.Dispatcher;
import controller.TransformersIndex;

/**
 * <p>Title: CurvesPanel</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004 Sébastien Bois, Eric Paquette</p>
 * <p>Company: (ÉTS) - École de Technologie Supérieure</p>
 * @author unascribed
 * @version $Revision: 1.9 $
 */
public class CurvesPanel extends JPanel implements DocObserver {
	private JPanel upperPanel;
	private JPanel lowerPanel;
	private JLabel curvesLabel;	
	private JComboBox curvesComboBox;
	private JLabel sectionsLabel;
	private JTextField sectionsTextField;
	private JButton alignedButton;
	private JButton symetricButton;
	private Dispatcher dispatcher;
	private TransformersIndex ti;
	private Curves curvesTransformer;
	private JRadioButton newCurve;
	private JRadioButton modifyCurve;
	private JRadioButton append;
	
	/**
	 * 
	 * @param ti
	 */
	public CurvesPanel(Dispatcher d) {
		this.dispatcher = d;
		ti = dispatcher.getTransformersIndex();
		
		Application.getInstance().getActiveDocument().addObserver(this);
		
		setLayout(new GridLayout(2,1));
		
		JPanel upperPanel = new JPanel();
		upperPanel.setLayout(new FlowLayout());
		add(upperPanel);
		
		curvesLabel = new JLabel("Curve type:");
		curvesComboBox = new JComboBox(CurvesModel.CTRL_POINTS_AND_CURVES_VALUES);
		curvesComboBox.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent ae) {
					CurvesPanel.this.ti.getTheCurves().setCurveType((String)curvesComboBox.getSelectedItem());
				}	
			});
		
		sectionsLabel = new JLabel("Sections:");
		sectionsTextField = new JTextField(10);
		setNumberOfSections(ti.getTheCurves().getNumberOfSections());
		sectionsTextField.addFocusListener(
			new FocusListener() {
				public void focusGained(FocusEvent fe) {
					JTextField tf = (JTextField)fe.getSource();
					String text 	= tf.getText();		
					tf.select(0,text.length());	
				}
								
				public void focusLost(FocusEvent fe) {
					JTextField tf = (JTextField)fe.getSource();
					int n = Integer.parseInt(tf.getText());
					CurvesPanel.this.ti.getTheCurves().setNumberOfSections(n);
				};
			});

		alignedButton = new JButton("Aligned");
		alignedButton.setToolTipText("Modify control points tangeants such that they are aligned (G1).");
		alignedButton.addActionListener(
			new AbstractAction() {
				public void actionPerformed(ActionEvent ae) {
					CurvesPanel.this.ti.getTheCurves().alignControlPoint();
				};
			});

		symetricButton = new JButton("Symetric");
		symetricButton.setToolTipText("Modify control points tangeants to be symetric (C1).");
		symetricButton.addActionListener(
			new AbstractAction() {
				public void actionPerformed(ActionEvent ae) {
					CurvesPanel.this.ti.getTheCurves().symetricControlPoint();
				};
			});
		
		upperPanel.add(curvesLabel);
		upperPanel.add(curvesComboBox);
		upperPanel.add(sectionsLabel);
		upperPanel.add(sectionsTextField);
		upperPanel.add(alignedButton);
		upperPanel.add(symetricButton);
		
		lowerPanel = new JPanel();
		lowerPanel.setLayout(new FlowLayout());
		add(lowerPanel);
		
		ButtonGroup bg = new ButtonGroup();
		
		newCurve = new JRadioButton("New curve");
		newCurve.setToolTipText("Create a new curve. Click to add control points.");
		bg.add(newCurve);
		lowerPanel.add(newCurve);

		newCurve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Application.getInstance().getActiveDocument().clearSelectedObjectList();
				dispatcher.setTheCurrentTransformer(ti.getTheCurves());
				ti.setTheDefaultTransformer(ti.getTheCurves());
				ti.getTheCurves().activate();
			}
		});
		
		modifyCurve = new JRadioButton("Modify curve");
		modifyCurve.setToolTipText("Select or modify an existing curve and its control points. Click to select, click-drag to modify.");
		bg.add(modifyCurve);
		lowerPanel.add(modifyCurve);

		modifyCurve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispatcher.setTheCurrentTransformer(ti.getTheSelector());
				ti.setTheDefaultTransformer(ti.getTheSelector());
			}
		});

		append	= new JRadioButton("Append");
		append.setToolTipText("Add control points to the selected curve. Click to add control points.");
		bg.add(append);
		lowerPanel.add(append);
		append.setEnabled(false);
		
		append.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispatcher.setTheCurrentTransformer(ti.getTheCurves());
				ti.setTheDefaultTransformer(ti.getTheCurves());
				ti.getTheCurves().activate();
			}
		});
		
		newCurve.setSelected(true);
		
		curvesTransformer = ti.getTheCurves();
		curvesTransformer.setCurvesPanel(this);
	}
	
	public int getNumberOfSections() {
		return Integer.parseInt(sectionsTextField.getText());
	}
	
	/**
	 * @param i
	 */
	public void setNumberOfSections(int i) {
		sectionsTextField.setText(String.valueOf(i));
	}

	public String getCurveType() {
		return (String)curvesComboBox.getSelectedItem();
	}
	
	public void setCurveType(String curveType) {
		for (int i = 0; i < curvesComboBox.getItemCount(); ++i) {
			if (curveType == (String)curvesComboBox.getItemAt(i)) {
				curvesComboBox.setSelectedIndex(i);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see model.DocObserver#docChanged()
	 */
	public void docChanged() {
	}

	/* (non-Javadoc)
	 * @see model.DocObserver#docSelectionChanged()
	 */
	public void docSelectionChanged() {
		append.setEnabled(!Application.getInstance().getActiveDocument().getSelectedObjects().isEmpty());
	}
}
