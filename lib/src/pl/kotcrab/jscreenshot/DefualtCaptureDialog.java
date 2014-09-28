/*******************************************************************************
 * Copyright 2014 Pawel Pastuszak
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package pl.kotcrab.jscreenshot;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;

public class DefualtCaptureDialog extends AbstractCaptureDialog {

	public DefualtCaptureDialog () {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setUndecorated(true);

		JButton cancelButton = new JButton("Cancel");
		JButton acceptButton = new JButton("Accept");

		getContentPane().add(acceptButton, BorderLayout.EAST);
		getContentPane().add(cancelButton, BorderLayout.WEST);

		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				option.cancel();
			}
		});

		acceptButton.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				option.capture();
			}
		});

		pack();
	}

}
