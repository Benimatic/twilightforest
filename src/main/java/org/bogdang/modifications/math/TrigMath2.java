package org.bogdang.modifications.math;

/**
 * Credits for this class goes to user aioobe on stackoverflow.com
 * Source: http://stackoverflow.com/questions/4454630/j2me-calculate-the-the-distance-between-2-latitude-and-longitude
 * J2ME? wut?
 * Modified by Bogdan-G, 29.11.2016, ver mod 0.2
 */
public class TrigMath2 {

    static final double sq2p1 = 2.414213562373095048802e0;
    static final double sq2m1 = .414213562373095048802e0;
    static final double p4 = .161536412982230228262e2;
    static final double p3 = .26842548195503973794141e3;
    static final double p2 = .11530293515404850115428136e4;
    static final double p1 = .178040631643319697105464587e4;
    static final double p0 = .89678597403663861959987488e3;
    static final double q4 = .5895697050844462222791e2;
    static final double q3 = .536265374031215315104235e3;
    static final double q2 = .16667838148816337184521798e4;
    static final double q1 = .207933497444540981287275926e4;
    static final double q0 = .89678597403663861962481162e3;
    static final double PIO2 = 1.5707963267948966135E0;
    
    static final float sq2p1f = 2.414213562373095048802e0f;
    static final float sq2m1f = .414213562373095048802e0f;
    static final float p4f = .161536412982230228262e2f;
    static final float p3f = .26842548195503973794141e3f;
    static final float p2f = .11530293515404850115428136e4f;
    static final float p1f = .178040631643319697105464587e4f;
    static final float p0f = .89678597403663861959987488e3f;
    static final float q4f = .5895697050844462222791e2f;
    static final float q3f = .536265374031215315104235e3f;
    static final float q2f = .16667838148816337184521798e4f;
    static final float q1f = .207933497444540981287275926e4f;
    static final float q0f = .89678597403663861962481162e3f;
    static final float PIO2f = 1.5707963267948966135E0f;

    private static double mxatan(double arg) {
        double argsq = arg * arg, value;

        value = ((((p4 * argsq + p3) * argsq + p2) * argsq + p1) * argsq + p0);
        value = value / (((((argsq + q4) * argsq + q3) * argsq + q2) * argsq + q1) * argsq + q0);
        return value * arg;
    }

    private static double msatan(double arg) {
        return arg < sq2m1 ? mxatan(arg)
             : arg > sq2p1 ? PIO2 - mxatan(1 / arg)
             : PIO2 / 2 + mxatan((arg - 1) / (arg + 1));
    }

    public static double atan(double arg) {
        return arg > 0 ? msatan(arg) : -msatan(-arg);
    }

    public static double atan2(double arg1, double arg2) {
        if (arg1 + arg2 == arg1)
            return arg1 == 0 ? 0 : arg1 > 0 ? PIO2 : -PIO2;
        arg1 = atan(arg1 / arg2);
        return arg2 < 0 ? arg1 <= 0 ? arg1 + Math.PI : arg1 - Math.PI : arg1;
    }

    private static float mxatan(float arg) {
        float argsq = arg * arg, value;

        value = ((((p4f * argsq + p3f) * argsq + p2f) * argsq + p1f) * argsq + p0f);
        value = value / (((((argsq + q4f) * argsq + q3f) * argsq + q2f) * argsq + q1f) * argsq + q0f);
        return value * arg;
    }

    private static float msatan(float arg) {
        return arg < sq2m1f ? mxatan(arg)
             : arg > sq2p1f ? PIO2f - mxatan(1f / arg)
             : PIO2f / 2 + mxatan((arg - 1f) / (arg + 1f));
    }

    public static float atan(float arg) {
        return arg > 0 ? msatan(arg) : -msatan(-arg);
    }

    public static float atan2(float arg1, float arg2) {
        if (arg1 + arg2 == arg1)
            return arg1 == 0 ? 0 : arg1 > 0 ? PIO2f : -PIO2f;
        arg1 = atan(arg1 / arg2);
        return arg2 < 0 ? arg1 <= 0 ? arg1 + (float)Math.PI : arg1 - (float)Math.PI : arg1;
    }

    public static double atan(int arg) {
        return arg > 0 ? msatan((double)arg) : -msatan(-(double)arg);
    }

    public static double atan2(int arg1, int arg2) {
        if (arg1 + arg2 == arg1)
            return arg1 == 0 ? 0 : arg1 > 0 ? PIO2 : -PIO2;
        arg1 = (int)atan((double)arg1 / (double)arg2);
        return arg2 < 0 ? arg1 <= 0 ? arg1 + Math.PI : arg1 - Math.PI : (double)arg1;
    }

    public static double atan(short arg) {
        return arg > 0 ? msatan((double)arg) : -msatan(-(double)arg);
    }

    public static double atan2(short arg1, short arg2) {
        if (arg1 + arg2 == arg1)
            return arg1 == 0 ? 0 : arg1 > 0 ? PIO2 : -PIO2;
        arg1 = (short)atan((double)arg1 / (double)arg2);
        return arg2 < 0 ? arg1 <= 0 ? arg1 + Math.PI : arg1 - Math.PI : (double)arg1;
    }
}