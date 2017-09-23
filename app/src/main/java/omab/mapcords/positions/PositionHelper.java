package omab.mapcords.positions;

/**
 * Created by Meandow on 9/23/2017.
 */

public  class PositionHelper {
    public static WGS84Position getWgsFromSweRef(double firstValue, double secondValue) {
        return new SWEREF99Position(firstValue, secondValue).toWGS84();
    }

    public static WGS84Position getWgsFromRt90(double firstValue, double secondValue) {
        return new RT90Position(firstValue, secondValue).toWGS84();
    }
}
