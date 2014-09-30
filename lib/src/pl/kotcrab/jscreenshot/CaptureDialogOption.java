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

/** Contains methods that can capture dialog call, this interface shouldn't be implemented by any class. See
 * {@link AbstractCaptureDialog} if you wan't to create custom dialog.
 * @author Pawel Pastuszak */
public interface CaptureDialogOption {
	/** Tells editor to capture current selection of screen */
	public void capture ();

	/** Tells editor that current capture should be canceled */
	public void cancel ();
}
