package net.todd.biblestudy.db;

public class NoteStyle
{
	public interface Colors
	{
		String BLUE = "blue";
		String GREEN = "green";
	}
	
	private Integer start;
	private Integer length;
	private String foreground;
	private String background;
	private String fontStyle;
	private boolean underlined;
	
	public Integer getStart()
	{
		return start;
	}
	public void setStart(Integer start)
	{
		this.start = start;
	}
	public Integer getLength()
	{
		return length;
	}
	public void setLength(Integer length)
	{
		this.length = length;
	}
	public String getForeground()
	{
		return foreground;
	}
	public void setForeground(String foreground)
	{
		this.foreground = foreground;
	}
	public String getBackground()
	{
		return background;
	}
	public void setBackground(String background)
	{
		this.background = background;
	}
	public String getFontStyle()
	{
		return fontStyle;
	}
	public void setFontStyle(String fontStyle)
	{
		this.fontStyle = fontStyle;
	}
	public boolean isUnderlined()
	{
		return underlined;
	}
	public void setUnderlined(boolean underlined)
	{
		this.underlined = underlined;
	}
}
