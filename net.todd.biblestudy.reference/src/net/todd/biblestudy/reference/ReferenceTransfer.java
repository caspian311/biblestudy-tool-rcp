package net.todd.biblestudy.reference;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import net.todd.biblestudy.common.ExceptionHandlerFactory;
import net.todd.biblestudy.common.SeverityLevel;

import org.eclipse.swt.dnd.ByteArrayTransfer;
import org.eclipse.swt.dnd.TransferData;

public class ReferenceTransfer extends ByteArrayTransfer
{
	private static final String BIBLE_VERSE = "BIBLE_VERSE";
	private static final int BIBLE_VERSE_ID = registerType(BIBLE_VERSE);

	private static ReferenceTransfer instance;

	private ReferenceTransfer()
	{
	}

	public static ReferenceTransfer getInstance()
	{
		if (instance == null)
		{
			instance = new ReferenceTransfer();
		}

		return instance;
	}

	@Override
	protected int[] getTypeIds()
	{
		return new int[] { BIBLE_VERSE_ID };
	}

	@Override
	protected String[] getTypeNames()
	{
		return new String[] { BIBLE_VERSE };
	}

	@SuppressWarnings("unchecked")
	@Override
	public void javaToNative(Object object, TransferData transferData)
	{
		if (object instanceof List)
		{
			List<BibleVerse> verses = (List<BibleVerse>) object;

			try
			{
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(out);

				oos.writeObject(verses);

				oos.close();
				out.close();

				byte[] byteArray = out.toByteArray();
				super.javaToNative(byteArray, transferData);
			}
			catch (IOException e)
			{
				ExceptionHandlerFactory.getHandler().handle(
						"An error occurred while trying to do the drag and drop thing... sorry.",
						this, e, SeverityLevel.ERROR);
			}
		}
	}

	@Override
	protected Object nativeToJava(TransferData transferData)
	{
		Object object = null;

		byte[] bytes = (byte[]) super.nativeToJava(transferData);

		try
		{
			ByteArrayInputStream in = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(in);

			object = ois.readObject();
		}
		catch (Exception e)
		{
			ExceptionHandlerFactory.getHandler().handle(
					"An error occurred while trying to do the drag and drop thing... sorry.", this,
					e, SeverityLevel.ERROR);
		}

		return object;
	}
}
