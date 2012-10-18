public class StateAndReward {

	public final static String LEFT = "Left";
	public final static String RIGHT = "Right";
	public final static String FINE_TUNING_LEFT = "FineTuningLeft";
	public final static String FINE_TUNING_RIGHT = "FineTuningRight";
	public final static String FACING_UP = "FacingUp";
	private static double previousAngle = 0f;

	/* State discretization function for the angle controller */
	/*
	 * public static String getStateAngle(double angle, double vx, double vy) {
	 * if(Math.abs(angle) < Math.PI/4f) { if(angle > 0) { return
	 * FINE_TUNING_RIGHT; } else if(angle < 0) { return FINE_TUNING_LEFT; } else
	 * { return FACING_UP; }
	 * 
	 * } else { if(angle > 0) { return LEFT; } else if(angle < 0) { return
	 * RIGHT; } } return null; }
	 */

	public static String getStateAngle(double angle, double vx, double vy) {
		if (Math.abs(angle) < Math.PI / 24) {
			return "0";
		} else if (angle > 0) {
			return "1";
		} else {
			return "2";
		}
		// return Long.toString(Math.round(Math.toDegrees(angle)));
	}

	/* Reward function for the angle controller */
	public static double getRewardAngle(double angle, double vx, double vy) {
		// System.out.println("angle="+angle+", absAngle="+Math.abs(angle));

		return Math.pow((Math.PI - Math.abs(angle)), 2);
	}

	/* State discretization function for the full hover controller */
	public static String getStateHover(double angle, double vx, double vy) {
		if (vx > 1) {
			if (vy > 0) {
				return "0";
			} else {
				return "1";
			}
		} else if (vy > 1) {
			if (vx > 0) {
				return "2";
			} else {
				return "3";
			}
		} else {
			return "4";
		}
	}

	/* Reward function for the full hover controller */
	public static double getRewardHover(double angle, double vx, double vy) {
		return (30 - Math.abs(vx)) * (30 - Math.abs(vy));
	}

	// ///////////////////////////////////////////////////////////
	// discretize() performs a uniform discretization of the
	// value parameter.
	// It returns an integer between 0 and nrValues-1.
	// The min and max parameters are used to specify the interval
	// for the discretization.
	// If the value is lower than min, 0 is returned
	// If the value is higher than min, nrValues-1 is returned
	// otherwise a value between 1 and nrValues-2 is returned.
	//
	// Use discretize2() if you want a discretization method that does
	// not handle values lower than min and higher than max.
	// ///////////////////////////////////////////////////////////
	public static int discretize(double value, int nrValues, double min,
			double max) {
		if (nrValues < 2) {
			return 0;
		}

		double diff = max - min;

		if (value < min) {
			return 0;
		}
		if (value > max) {
			return nrValues - 1;
		}

		double tempValue = value - min;
		double ratio = tempValue / diff;

		return (int) (ratio * (nrValues - 2)) + 1;
	}

	// ///////////////////////////////////////////////////////////
	// discretize2() performs a uniform discretization of the
	// value parameter.
	// It returns an integer between 0 and nrValues-1.
	// The min and max parameters are used to specify the interval
	// for the discretization.
	// If the value is lower than min, 0 is returned
	// If the value is higher than min, nrValues-1 is returned
	// otherwise a value between 0 and nrValues-1 is returned.
	// ///////////////////////////////////////////////////////////
	public static int discretize2(double value, int nrValues, double min,
			double max) {
		double diff = max - min;

		if (value < min) {
			return 0;
		}
		if (value > max) {
			return nrValues - 1;
		}

		double tempValue = value - min;
		double ratio = tempValue / diff;

		return (int) (ratio * nrValues);
	}

}
