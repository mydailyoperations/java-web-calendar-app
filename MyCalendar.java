public class MyCalendar 
{
	private HashMap<LocalDate, ArrayList<Event>> myCal;
	private LocalDate currentDate;
	
	public MyCalendar(LocalDate cd)
	{
		myCal = new HashMap<LocalDate, ArrayList<Event>>();
		currentDate = cd;
	}
	
	public void addEvent(LocalDate d, Event e)
	{
		if(myCal.containsKey(d))
		{
			ArrayList<Event> events = myCal.get(d);
			events.add(e);
			myCal.put(d, events);
		}
		else
		{
			ArrayList<Event> events = new ArrayList<Event>();
			events.add(e);
			myCal.put(d, events);
		}
	}
	
	public boolean removeOneTimeEventSpecific(LocalDate d, Event e)
	{
		boolean removed = false;
		if(myCal.containsKey(d))
		{
			ArrayList<Event> events = myCal.get(d);
			Event ev = findEvent(d, e.getName());
			if(!ev.getIsRegular())
				events.remove(e);
			myCal.put(d, events);
			
			if(events.isEmpty())
				myCal.remove(d);
			
			removed = true;
		}
		return removed;
	}	

	public boolean removeOneTimeEventAll(LocalDate d)
	{
		boolean removed = false;
		if(myCal.containsKey(d))
		{
			ArrayList<Event> events = myCal.get(d);
			
			java.util.Iterator<Event> itr = events.iterator();
			while(itr.hasNext())
			{
				Event e = itr.next();
				if(!e.getIsRegular())
					itr.remove();
			}
			
			myCal.put(d, events);
			
			if(events.isEmpty())
				myCal.remove(d);
			
			removed = true;
		}
		
		return removed;
	}	
	
	public boolean removeRegularEvent(LocalDate d, Event e)
	{
		boolean removed = false;
		if(myCal.containsKey(d))
		{
			ArrayList<Event> events = myCal.get(d);

			if(e.getIsRegular())
				events.remove(e);
			myCal.put(d, events);
			
			if(events.isEmpty())
				myCal.remove(d);
			
			removed = true;
		}
		
		return removed;
	}	
	
	public LocalDate currentDay()
	{
		return currentDate;
	}
	
	public LocalDate previousDay()
	{
		return currentDate = currentDate.minusDays(1);
	}
	
	public LocalDate nextDay()
	{
		return currentDate = currentDate.plusDays(1);
	}
	
	public LocalDate previousMonth()
	{
		return currentDate = currentDate.minusMonths(1);
	}
	
	public LocalDate nextMonth()
	{
		return currentDate = currentDate.plusMonths(1);
	}
	
	public Event findEvent(LocalDate d, String name)
	{
		Event event = null;
		if(!myCal.containsKey(d))
			return event;
		
		ArrayList<Event> events = myCal.get(d);
		for(Event ev : events)
		{
			if(name.equals(ev.getName()))
				event = ev;
		}
		return event;
	}
	
	public boolean timeOverlapCheck(LocalDate d, Event e)
	{
		boolean overlap = false;
		if(myCal.containsKey(d))
		{
			ArrayList<Event> events = myCal.get(d);
			for(Event ev : events)
			{
				if(ev.getTimeInterval().overlap(e))
					overlap = true;
			}
		}
		return overlap;
	}
	
	public String getEvents(LocalDate c)
	{
		String eventList = "";
		
		if(!myCal.containsKey(c))
			eventList = "No events listed!";
		else
		{
			sortEventsInOrderByTime(myCal.get(c));
			ArrayList<Event> events = myCal.get(c);
			
			for(Event e : events)
			{
				eventList = eventList + "  " + e.getName() + " | " + 
							e.getTimeInterval().startPrint() + " - " +
							e.getTimeInterval().endPrint() + "\n";
			}
		}
		
		return eventList;
	}
	
	public String getRegEvents(LocalDate c)
	{
		String eventList = "";
		
		if(!myCal.containsKey(c))
			eventList = "No events listed!";
		else
		{
			sortEventsInOrderByTime(myCal.get(c));
			ArrayList<Event> events = myCal.get(c);
		
			for(Event e : events)
			{
				if(e.getIsRegular())
				{
					eventList = eventList + "  " + e.getName() + " | " + 
							e.getTimeInterval().startPrint() + " - " +
							e.getTimeInterval().endPrint() + "\n";
				}

			}
		}
		
		return eventList;
	}
	
	public String getEventListAll()
	{
		String eventList = new String();
		TreeMap<LocalDate, ArrayList<Event>> sortedMyCal = new TreeMap<>();
		sortedMyCal.putAll(myCal);
		
		for(LocalDate day : sortedMyCal.keySet())
		{
			ArrayList<Event> events = myCal.get(day);
			
			if (!eventList.contains(Integer.toString(day.getYear())))
				eventList = eventList + day.getYear() + "\n";

			eventList = eventList + "  " + day.getMonth() + "\n";
			
			eventList = eventList + "    " + day.getDayOfMonth() + " " + day.getDayOfWeek() + "\n";
			
			sortEventsInOrderByTime(events);
			for(Event e : events)
			{
				eventList = eventList + "      " + e.getName() + " | " + 
							e.getTimeInterval().startPrint() + " - " +
							e.getTimeInterval().endPrint() + "\n";
			}
		}
		
		return eventList;
	}
	
	public void sortEventsInOrderByTime(ArrayList<Event> events)
	{
		class sortEventsByTime implements Comparator<Event>
		{
			public int compare(Event e1, Event e2) 
			{
				int event1 = e1.getTimeInterval().getStart();
				int event2 = e2.getTimeInterval().getStart();
				return Integer.compare(event1, event2);
			}
		}
		
		Collections.sort(events, new sortEventsByTime());
	}
	
	public void printOutput(PrintWriter out)
	{
		String eventList = new String();
		TreeMap<LocalDate, ArrayList<Event>> sortedMyCal = new TreeMap<>();
		sortedMyCal.putAll(myCal);
		
		for(LocalDate day : sortedMyCal.keySet())
		{
			ArrayList<Event> events = myCal.get(day);
			
			if (!eventList.contains(Integer.toString(day.getYear())))
			{
				eventList = eventList + day.getYear() + "\n";
				out.println(day.getYear());
			}
			
			eventList = eventList + "  " + day.getMonth() + "\n";
			out.println("  " + day.getMonth());
			
			eventList = eventList + "    " + day.getDayOfMonth() + " " + day.getDayOfWeek() + "\n";
			out.println("    " + day.getDayOfMonth() + " " + day.getDayOfWeek());
			
			sortEventsInOrderByTime(events);
			for(Event e : events)
			{
				eventList = eventList + "      " + e.getName() + " | " + 
							e.getTimeInterval().startPrint() + " - " +
							e.getTimeInterval().endPrint() + "\n";
				out.println("      " + e.getName() + " | " + 
						e.getTimeInterval().startPrint() + " - " +
						e.getTimeInterval().endPrint());
			}
		}
	}
	
    public void printCalendar(LocalDate c)
    {  
    	LocalDate beginning = LocalDate.of(c.getYear(), c.getMonth(), 1);
    	String firstDayString = beginning.getDayOfWeek().toString();
    	
    	int firstDayInt = 0;
		switch(firstDayString){
		case "SUNDAY":
			firstDayInt = 1;
			break;
		case "MONDAY":
			firstDayInt = 2;
			break;
		case "TUESDAY":
			firstDayInt = 3;
			break;
		case "WEDNESDAY":
			firstDayInt = 4;
			break;
		case "THURSDAY":
			firstDayInt = 5;
			break;
		case "FRIDAY":
			firstDayInt = 6;
			break;
		case "SATURDAY":
			firstDayInt = 7;
			break;
		}
    	
    	System.out.println("             " + c.getMonth() + " " + c.getYear());
    	System.out.println(" Su    Mo    Tu    We    Th    Fr    Sa");
    	
    	int weekday = 0;

      for (int firstDaySpaces=1; firstDaySpaces<firstDayInt; firstDaySpaces++) 
    	{
            System.out.print("      ");
            weekday++;
        }
    	
    	LocalDate today = LocalDate.now();
    	
    	for (int day=1; day<=c.lengthOfMonth(); day++)
    	{
    		if ((day == today.getDayOfMonth() && c.getMonthValue() == today.getMonthValue() && c.getYear() == today.getYear())
    				|| myCal.containsKey(beginning))
    		{
        		if (day<10)
        			System.out.print(" [" + day + "]");
        		else
        			System.out.print("[" + day + "]"); 
        		weekday++;
        		if(weekday%7 == 0)
        			System.out.println();
        		else
        			System.out.print("  ");
    		}
    		else
    		{
        		if (day<10)
        			System.out.print("  " + day);
        		else
        			System.out.print(" " + day); 
        		weekday++;
        		if(weekday%7 == 0)
        			System.out.println();
        		else
        			System.out.print("   ");
    		}
    		
    		beginning = beginning.plusDays(1);
    	}
    	
    	System.out.println("\n");
    }
}
