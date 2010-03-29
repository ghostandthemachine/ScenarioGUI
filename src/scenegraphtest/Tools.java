package scenegraphtest;

import java.util.*;

public class Tools {

	public static float constrain(float value, float lowerConstraint, float upperConstraint) {
		float outputValue;
		if (value < lowerConstraint) {
			outputValue = lowerConstraint;
		} else if (value > upperConstraint) {
			outputValue = upperConstraint;
		} else {
			outputValue = value;
		}
		return outputValue;
	}

	public static float constrain(float value, float lowerConstraint) {
		float outputValue;
		if (value < lowerConstraint) {
			outputValue = lowerConstraint;
		} else {
			outputValue = value;
		}
		return outputValue;
	}

	public static float map(float value, float srcMin, float srcMax, float tgtMin, float tgtMax) {
		// Figure out how 'wide' each range is
		float scaleFactor = (tgtMax - tgtMin) / (srcMax - srcMin);
		float scaledValue = (value - srcMin) * scaleFactor + tgtMin;
		return scaledValue;
	}


		public static float random(float lo, float hi) {
			Random rn = new Random();

			float n = hi - lo + 1;
			float i = rn.nextInt() % n;
			if (i < 0)
				i = -i;
			return lo + i;

	}
		public static double distance(double ax, double ay, double bx, double by) {
			double a = Math.abs(ax - bx);
			double b = Math.abs(bx - by);
			double c = Math.sqrt((a * a) + (b * b));
			return c;

	}
}