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

public class Screenshot {
	private static Color uiColor = Color.ORANGE;

	private static Class<? extends AbstractCaptureDialog> captureDialogClass = DefualtCaptureDialog.class;

	public static void take (ScreenshotListener listener) {
		take(false, listener);
	}

	public static void take (boolean primaryMonitorOnly, ScreenshotListener listener) {
		new Editor(primaryMonitorOnly, listener);
	}

	public static Color getUiColor () {
		return uiColor;
	}

	public static void setUiColor (Color uiColor) {
		Screenshot.uiColor = uiColor;
	}

	public static Class<? extends AbstractCaptureDialog> getCaptureDialogClass () {
		return captureDialogClass;
	}

	public static void setCaptureDialogClass (Class<? extends AbstractCaptureDialog> clazz) {
		captureDialogClass = clazz;
	}
}
