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

import java.awt.Rectangle;

class MathUtils {
	/** If rectangle width and/or height is negative, this methods changes them so they are positive. It also changes X and/or Y so
	 * rectangle will cover the same area.
	 * @param rect that bounds will be changed */
	public static void shiftBounds (Rectangle rect) { // rekt m9
		if (rect.width < 0) {
			rect.x = rect.x + rect.width;
			rect.width = rect.width * -1;
		}

		if (rect.height < 0) {
			rect.y = rect.y + rect.height;
			rect.height = rect.height * -1;
		}
	}
}
