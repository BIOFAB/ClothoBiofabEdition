/*
 * $Id: ClipboardListener.java,v 1.1 2005/05/25 19:56:30 ahmed Exp $
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
 * Listener interface to get notified with the clipboard contents.
 * 
 * @author Ahmed Moustafa (ahmed@users.sf.net)
 */

public interface ClipboardListener {
	
	/**
	 * Notifies the listener with the current contents of the clipboard.
	 * @param contents The current contents of the clipboard
	 */
	public void clipboardCheck (String contents);

}