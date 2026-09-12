public class Event 
{
	private String name;
	private TimeInterval timeInterval;
	private boolean isRegular;
	
	public Event(String n, TimeInterval tI, boolean isR)
	{
		name = n;
		timeInterval = tI;
		isRegular = isR;
	}
	
	public String getName()
	{
		return name;
	}

	public TimeInterval getTimeInterval()
	{
		return timeInterval;
	}
	
	public boolean getIsRegular()
	{
		return isRegular;
	}
}
