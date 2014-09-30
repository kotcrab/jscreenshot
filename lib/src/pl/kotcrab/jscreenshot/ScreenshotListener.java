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

import java.awt.image.BufferedImage;

/** Contains callback function from editor, see {@link ScreenshotAdapter} for empty implemetation
 * @author Pawel Pastuszak
 * @see ScreenshotAdapter */
public interface ScreenshotListener {
	/** Screenshot was taken
	 * @param image captured screenshot */
	public void screenshotTaken (BufferedImage image);

	/** Screenshot taking was canceled by user */
	public void canceled ();
}
