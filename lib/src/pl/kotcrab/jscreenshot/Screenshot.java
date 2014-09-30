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

import java.awt.Color;

/** Main class of JScreenshot, here you can call method to take a screenshot, set UI color or set custom capture dialog.
 * @author Pawel Pastuszak */
public class Screenshot {
	private static boolean running = false;
	private static Color uiColor = Color.ORANGE;
	private static Class<? extends AbstractCaptureDialog> captureDialogClass = DefaultCaptureDialog.class;

	/** Takes screenshot of all screens */
	public static void take (ScreenshotListener listener) {
		take(false, listener);
	}

	/** Takes screenshot of primary monitor only or all screens
	 * 
	 * @param primaryMonitorOnly if true only one monitor will be captured, if false all screens will captured */
	public static void take (boolean primaryMonitorOnly, ScreenshotListener listener) {
		if (running) throw new IllegalStateException("Screenshot editor is already running!");

		running = true;

		new Editor(primaryMonitorOnly, listener, new EditorListener() {
			public void editorFinished () {
				Screenshot.running = false;
			}
		});
	}

	/** Returns current color of editor UI
	 * @return current color of editor UI */
	public static Color getUiColor () {
		return uiColor;
	}

	/** Sets color used in UI, this will not affect currently running editor
	 * @param uiColor new UI color */
	public static void setUiColor (Color uiColor) {
		Screenshot.uiColor = uiColor;
	}

	/** Returns current capture dialog
	 * @return current capture dialog */
	public static Class<? extends AbstractCaptureDialog> getCaptureDialogClass () {
		return captureDialogClass;
	}

	/** Changes current capture dialog, that will be used with screenshot editor */
	public static void setCaptureDialogClass (Class<? extends AbstractCaptureDialog> clazz) {
		captureDialogClass = clazz;
	}

}

interface EditorListener {
	public void editorFinished ();
}
