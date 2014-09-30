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

/** Example implementation of CaptureDialog that will be used if none other is provided via
 * {@link Screenshot#setCaptureDialogClass(Class)}. Depending on your current Look and Feel it will look very ugly or slightly
 * less ugly.
 * @author Pawel Pastuszak
 * @see AbstractCaptureDialog */
public class DefaultCaptureDialog extends AbstractCaptureDialog {

	public DefaultCaptureDialog () {
		setUndecorated(true);

		JButton cancelButton = new JButton("Cancel");
		JButton captureButton = new JButton("Capture");

		getContentPane().add(captureButton, BorderLayout.EAST);
		getContentPane().add(cancelButton, BorderLayout.WEST);

		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				// cancel button pressed so option.cancel must be called
				option.cancel();
			}
		});

		captureButton.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				// capture button pressed so option.cancel must be called
				option.capture();
			}
		});

		pack();
	}

}
