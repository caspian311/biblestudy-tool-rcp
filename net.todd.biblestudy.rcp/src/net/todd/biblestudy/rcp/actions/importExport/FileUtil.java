package net.todd.biblestudy.rcp.actions.importExport;

import java.io.File;

public class FileUtil
{
	public boolean recrusivelyDelete(File file)
	{
		boolean retVal = false;

		if (file != null)
		{
			if (file.isDirectory())
			{
				String[] children = file.list();
				for (int i = 0; i < children.length; i++)
				{
					if (recrusivelyDelete(new File(file, children[i])) == false)
					{
						break;
					}
				}
			}

			retVal = file.delete();
		}

		return retVal;
	}
}
