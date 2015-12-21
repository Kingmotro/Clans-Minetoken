package repo.minetoken.clans.utilities;



public class utilTime {

	public static enum TimeUnit {
		BEST,
		DAYS,
		HOURS,
		MINUTES,
		SECONDS,
		FIT,
	}

	public static double convert(long time, TimeUnit unit, int decPoint) {
		if(unit == TimeUnit.BEST) {
			if(time < 60000L) unit = TimeUnit.SECONDS;
			else if(time < 3600000L) unit = TimeUnit.MINUTES;
			else if(time < 86400000L) unit = TimeUnit.HOURS;
			else unit = TimeUnit.DAYS;
		}
		if(unit == TimeUnit.SECONDS) return utilMath.trim(time / 1000.0D, decPoint);
		if(unit == TimeUnit.MINUTES) return utilMath.trim(time / 60000.0D, decPoint);
		if(unit == TimeUnit.HOURS) return utilMath.trim(time / 3600000.0D, decPoint);
		if(unit == TimeUnit.DAYS) return utilMath.trim(time / 86400000.0D, decPoint);
		return utilMath.trim(time, decPoint);
	}

	public static boolean elapsed(long from, long required)
	{
		return System.currentTimeMillis() - from > required;
	}

	public static String convertString(long time, int trim, TimeUnit type)
	{
		if (time == -1L) {
			return "Permanent";
		}
		if (type == TimeUnit.FIT) {
			if (time < 60000L) {
				type = TimeUnit.SECONDS;
			} else if (time < 3600000L) {
				type = TimeUnit.MINUTES;
			} else if (time < 86400000L) {
				type = TimeUnit.HOURS;
			} else {
				type = TimeUnit.DAYS;
			}
		}
		if (type == TimeUnit.DAYS) {
			return utilMath.trim(trim, time / 8.64E7D) + " Days";
		}
		if (type == TimeUnit.HOURS) {
			return utilMath.trim(trim, time / 3600000.0D) + " Hours";
		}
		if (type == TimeUnit.MINUTES) {
			return utilMath.trim(trim, time / 60000.0D) + " Minutes";
		}
		if (type == TimeUnit.SECONDS) {
			return utilMath.trim(trim, time / 1000.0D) + " Seconds";
		}
		return utilMath.trim(trim, time) + " Milliseconds";
	}
}