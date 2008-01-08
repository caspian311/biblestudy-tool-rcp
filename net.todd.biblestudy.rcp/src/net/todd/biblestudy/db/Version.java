package net.todd.biblestudy.db;

public class Version
{
	private Integer versionId; 
	private String versionName;
	
	public String getVersionName()
	{
		return versionName;
	}
	public void setVersionName(String versionName)
	{
		this.versionName = versionName;
	}
	public Integer getVersionId()
	{
		return versionId;
	}
	public void setVersionId(Integer versionId)
	{
		this.versionId = versionId;
	}
}
