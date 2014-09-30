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

/** Interface that all custom CaptureDialog's must implemented. This interface shouldn't be implemented directly,
 * {@link AbstractCaptureDialog} should be used.
 * @author Pawel Pastuszak
 * @see AbstractCaptureDialog
 * @see DefaultCaptureDialog */
public interface CaptureDialog {
	/** Called when editor and capture dialog are created, dialog should store given {@link CaptureDialogOption} and call
	 * {@link CaptureDialogOption#cancel()} or {@link CaptureDialogOption#capture()} when right button is pressed.
	 * @see AbstractCaptureDialog
	 * @see DefaultCaptureDialog */
	public void setCaptureDialogOption (CaptureDialogOption opt);

	/** Return custom dialog offset, that can be used to set position of capture dialog better for example when custom border is
	 * used.
	 * @return custom dialog offset */
	public Insets getOffset ();
}
