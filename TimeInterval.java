public class TimeInterval 
{
	private int startTime;
	private int endTime;
	private String startTimeString;
	private String endTimeString;
	
	public TimeInterval(int s, int e, String ss, String es)
	{
		startTime = s;
		endTime = e;
		startTimeString = ss;
		endTimeString = es;
	}
	
	public int getStart()
	{
		return startTime;
	}
	
	public String startPrint()
	{
		return startTimeString;
	}
	
	public int getEnd()
	{
		return endTime;
	}
	
	public String endPrint()
	{
		return endTimeString;
	}
	
	public boolean overlap(Event e)
	{
		boolean overlap = false;
		
		if(e.getTimeInterval().getStart() >= getStart() && e.getTimeInterval().getStart() <= getEnd())
			overlap = true;
		else if(e.getTimeInterval().getEnd() >= getStart() && e.getTimeInterval().getEnd() <= getEnd())
			overlap = true;
		else if (e.getTimeInterval().getStart() == getStart() || e.getTimeInterval().getStart() == getEnd()
			     || e.getTimeInterval().getEnd() == getStart() || e.getTimeInterval().getEnd() == getEnd())
			overlap = true;
		
		return overlap;
	}
}
