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

import java.awt.Insets;

import javax.swing.JDialog;

/** Implementation of {@link CaptureDialog}. All custom dialogs should extend from this class. Custom dialog should display 2
 * buttons: 'Accept' and 'Cancel'. When 'Accept' button is clicked {@link CaptureDialogOption#capture()} must be called, and when
 * 'Cancel' button is clicked {@link CaptureDialogOption#cancel()} must be called. <br>
 * Dialog constructor must not take any arguments <br>
 * <br>
 * See {@link DefaultCaptureDialog} for example.
 * @author Pawel Pastuszak
 * @see DefaultCaptureDialog */
public abstract class AbstractCaptureDialog extends JDialog implements CaptureDialog {

	private Insets dialogInsets = new Insets(0, 0, 0, 0);

	protected CaptureDialogOption option;

	public AbstractCaptureDialog () {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setAlwaysOnTop(true);
	}

	public void setCaptureDialogOption (CaptureDialogOption opt) {
		this.option = opt;
	}

	public Insets getOffset () {
		return dialogInsets;
	}
}
