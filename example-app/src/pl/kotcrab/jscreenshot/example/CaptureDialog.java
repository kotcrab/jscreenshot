/**
 * Copyright 2014 Pawel Pastuszak
 * 
 * This file is part of Screenshoter.
 * 
 * Arget is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Arget is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Arget.  If not, see <http://www.gnu.org/licenses/>.
 */

package pl.kotcrab.jscreenshot.example;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

import pl.kotcrab.jscreenshot.AbstractCaptureDialog;
import pl.kotcrab.jscreenshot.Screenshot;

import com.alee.laf.StyleConstants;
import com.alee.laf.button.WebButton;

public class CaptureDialog extends AbstractCaptureDialog {
	private Insets dialogInsets = new Insets(-2, -2, 2, 0);

	public CaptureDialog () {
		StyleConstants.darkBorderColor = Screenshot.getUiColor();

		getContentPane().setBackground(Color.BLACK);
		setUndecorated(true);

		getRootPane().setBorder(BorderFactory.createLineBorder(Screenshot.getUiColor(), 2));

		WebButton cancelButton = createButton();
		WebButton acceptButton = createButton();

		cancelButton.setIcon(new ImageIcon(getClass().getResource("cross.png")));
		acceptButton.setIcon(new ImageIcon(getClass().getResource("tick.png")));

		getContentPane().add(cancelButton, BorderLayout.WEST);
		getContentPane().add(acceptButton, BorderLayout.EAST);

		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed (ActionEvent e) {
				option.cancel();
			}
		});

		acceptButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed (ActionEvent e) {
				option.capture();
			}
		});

		pack();
	}

	@Override
	public Insets getOffset () {
		return dialogInsets;
	}

	private WebButton createButton () {
		WebButton b = new WebButton();
		b.setMoveIconOnPress(false);
		b.setInnerShadeColor(Screenshot.getUiColor());
		b.setShineColor(Screenshot.getUiColor());
		b.setShadeColor(Screenshot.getUiColor());

		b.setBottomBgColor(new Color(0, 0, 0, 0));
		b.setTopBgColor(new Color(0, 0, 0, 0));
		b.setTopSelectedBgColor(new Color(0, 0, 0, 0));
		b.setBottomSelectedBgColor(new Color(0, 0, 0, 0));
		b.setRolloverDecoratedOnly(true);
		b.setDrawFocus(false);

		return b;
	}

}
