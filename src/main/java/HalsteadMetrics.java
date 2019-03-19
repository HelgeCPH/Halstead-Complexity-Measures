/**
 * @author  Ahmed Metwally
 */


// This class is intended to calculate all the halstead complexity metrics
public class HalsteadMetrics {

	public int distOperators, distOperands, totOperators, totOperands;
	private int vocabulary = 0;
	private int progLen = 0;
	private double calcProgLen = 0;
	private double volume = 0;
	private double difficulty = 0;
	private double effort = 0;
	private double timeReqProg = 0;
	private double timeDelBugs = 0;


	// Initialize the variables in the constructor
	public HalsteadMetrics(int distOprt, int distOper, int totOprt,
						   int totOper) {
		distOperators = distOprt;
		distOperands = distOper;
		totOperators = totOper;
		totOperands = totOper;
	}

	// calculate the Program vocabulary
	public int getVocabulary() {
		vocabulary = distOperators + distOperands;
		return vocabulary;
	}

	// calculate the Program length
	public int getProglen() {
		progLen = totOperators + totOperands;
		return progLen;
	}

	// calculate the Calculated program length
	public double getCalcProgLen() {
		calcProgLen = distOperators * (Math.log(distOperators) / Math.log(2))
					  + distOperands * (Math.log(distOperands) / Math.log(2));
		return calcProgLen;
	}

	// calculate the volume
	public double getVolume() {
		volume = (totOperators + totOperands)
				 * (Math.log(distOperators + distOperands) / Math.log(2));
		return volume;
	}

	// calculate the difficulty
	public double getDifficulty() {
		difficulty = (distOperators / 2) * (totOperands / (double)distOperands);
		return difficulty;
	}

	// calculate the effort
	public double getEffort() {
		effort = ((distOperators / 2) * (totOperands / (double)distOperands))
				 * ((totOperators + totOperands)
				 * (Math.log(distOperators + distOperands) / Math.log(2)));
		return effort;
	}

	// calculate the Time required to program
	public double getTimeReqProg() {
		timeReqProg = (((distOperators / 2)
						 * (totOperands / (double)distOperands))
						 * ((totOperators + totOperands)
						    * (Math.log(distOperators+distOperands)
						 	   / Math.log(2)))) / 18;
		return timeReqProg;
	}

	// calculate the Number of delivered bugs
	public double getTimeDelBugs() {
		timeDelBugs = ((totOperators + totOperands)
					   * (Math.log(distOperators + distOperands) / Math.log(2)))
					   / 3000;
		return timeDelBugs;
	}
}