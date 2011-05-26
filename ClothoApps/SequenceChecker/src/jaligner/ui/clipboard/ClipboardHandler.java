/*
 * $Id: ClipboardHandler.java,v 1.1 2005/05/25 19:56:30 ahmed Exp $
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package jaligner.ui.clipboard;

/**
 * Abstract class to handle setting and getting "text" contents of
 * the system clipboard.
 * 
 * @author Ahmed Moustafa (ahmed@users.sf.net)
 */

public interface ClipboardHandler {
	/**
	 * Returns the contents of the system of the clipboard
	 * 
	 * @return String
	 */
	public String getContents();

	/**
	 * Sets the contents of the system of the clipboard
	 * 
	 * @param s the clipboard contents to set
	 */
	public void setContents(String s);
}