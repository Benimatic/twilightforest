package org.bogdang.modifications.math;

/* Author: Bogdan-G
 * 15.08.2016 version 0.2.0
 * modifer MC MathHelper
 * return cache value, integer values (no fractional)
 * enter float or double type and return
 */
public final class MathHelperLite
{
    private static float[] SIN_TABLE = new float[361];
    private static float[] COS_TABLE = new float[361];
    private static float[] TG_TABLE = new float[361];
    private static float[] CTG_TABLE = new float[361];

    /*
     * Float func
     */

    public static final float sin(final float value)
    {
        //temp - crutch
        final int temp;
        if (value > 0) 
        {
          if ((int)value > 360f) 
          {
            if ((temp=((int)value-((int)(value / 360f))*360)) != 0) 
            {
              return SIN_TABLE[temp];
            } 
            else 
            { 
              return SIN_TABLE[360];
            }
          } 
          else 
          {
            return SIN_TABLE[(int)(value)];
          }
        } 
        else if ((-(int)value) > 360f) 
        {
          if ((temp=(-((int)value-((int)(value / 360f))*360))) != 0) 
          {
            return SIN_TABLE[temp];
          } 
          else 
          {
            return SIN_TABLE[360];
          }
        } 
        else 
        {
          return SIN_TABLE[-(int)(value)];
        }
        //return SIN_TABLE[(int)(value)];//?
    }

    public static final float cos(final float value)
    {
        final int temp;
        if (value > 0) 
        {
          if ((int)value > 360f) 
          {
            if ((temp=((int)value-((int)(value / 360f))*360)) != 0) 
            {
              return COS_TABLE[temp];
            } 
            else 
            { 
              return COS_TABLE[360];
            }
          } 
          else 
          {
            return COS_TABLE[(int)(value)];
          }
        } 
        else if ((-(int)value) > 360f) 
        {
          if ((temp=(-((int)value-((int)(value / 360f))*360))) != 0) 
          {
            return COS_TABLE[temp];
          } 
          else 
          {
            return COS_TABLE[360];
          }
        } 
        else 
        {
          return COS_TABLE[-(int)(value)];
        }
        //return COS_TABLE[(int)(value)];
    }

    public static final float tg(final float value)
    {
        final int temp;
        if (value > 0) 
        {
          if ((int)value > 360f) 
          {
            if ((temp=((int)value-((int)(value / 360f))*360)) != 0) 
            {
              return TG_TABLE[temp];
            } 
            else 
            { 
              return TG_TABLE[360];
            }
          } 
          else 
          {
            return TG_TABLE[(int)(value)];
          }
        } 
        else if ((-(int)value) > 360f) 
        {
          if ((temp=(-((int)value-((int)(value / 360f))*360))) != 0) 
          {
            return TG_TABLE[temp];
          } 
          else 
          {
            return TG_TABLE[360];
          }
        } 
        else 
        {
          return TG_TABLE[-(int)(value)];
        }
        //return TG_TABLE[(int)(value)];
    }

    public static final float tan(final float value)
    {
        final int temp;
        if (value > 0) 
        {
          if ((int)value > 360f) 
          {
            if ((temp=((int)value-((int)(value / 360f))*360)) != 0) 
            {
              return TG_TABLE[temp];
            } 
            else 
            { 
              return TG_TABLE[360];
            }
          } 
          else 
          {
            return TG_TABLE[(int)(value)];
          }
        } 
        else if ((-(int)value) > 360f) 
        {
          if ((temp=(-((int)value-((int)(value / 360f))*360))) != 0) 
          {
            return TG_TABLE[temp];
          } 
          else 
          {
            return TG_TABLE[360];
          }
        } 
        else 
        {
          return TG_TABLE[-(int)(value)];
        }
        //return TG_TABLE[(int)(value)];
    }

    public static final float ctg(final float value)
    {
        final int temp;
        if (value > 0) 
        {
          if ((int)value > 360f) 
          {
            if ((temp=((int)value-((int)(value / 360f))*360)) != 0) 
            {
              return CTG_TABLE[temp];
            } 
            else 
            { 
              return CTG_TABLE[360];
            }
          } 
          else 
          {
            return CTG_TABLE[(int)(value)];
          }
        } 
        else if ((-(int)value) > 360f) 
        {
          if ((temp=(-((int)value-((int)(value / 360f))*360))) != 0) 
          {
            return CTG_TABLE[temp];
          } 
          else 
          {
            return CTG_TABLE[360];
          }
        } 
        else 
        {
          return CTG_TABLE[-(int)(value)];
        }
        //return CTG_TABLE[(int)(value)];
    }

    /*
     * Double func
     */

    public static final double sin(final double value)
    {
        final int temp;
        if (value > 0) 
        {
          if ((int)value > 360d) 
          {
            if ((temp=((int)value-((int)(value / 360d))*360)) != 0) 
            {
              return (double)SIN_TABLE[temp];
            } 
            else 
            { 
              return (double)SIN_TABLE[360];
            }
          } 
          else 
          {
            return (double)SIN_TABLE[(int)(value)];
          }
        } 
        else if ((-(int)value) > 360d) 
        {
          if ((temp=(-((int)value-((int)(value / 360d))*360))) != 0) 
          {
            return (double)SIN_TABLE[temp];
          } 
          else 
          {
            return (double)SIN_TABLE[360];
          }
        } 
        else 
        {
          return (double)SIN_TABLE[-(int)(value)];
        }
        //return (double)SIN_TABLE[(int)(value)];
    }

    public static final double cos(final double value)
    {
        final int temp;
        if (value > 0) 
        {
          if ((int)value > 360d) 
          {
            if ((temp=((int)value-((int)(value / 360d))*360)) != 0) 
            {
              return (double)COS_TABLE[temp];
            } 
            else 
            { 
              return (double)COS_TABLE[360];
            }
          } 
          else 
          {
            return (double)COS_TABLE[(int)(value)];
          }
        } 
        else if ((-(int)value) > 360d) 
        {
          if ((temp=(-((int)value-((int)(value / 360d))*360))) != 0) 
          {
            return (double)COS_TABLE[temp];
          } 
          else 
          {
            return (double)COS_TABLE[360];
          }
        } 
        else 
        {
          return (double)COS_TABLE[-(int)(value)];
        }
        //return (double)COS_TABLE[(int)(value)];
    }

    public static final double tg(final double value)
    {
        final int temp;
        if (value > 0) 
        {
          if ((int)value > 360d) 
          {
            if ((temp=((int)value-((int)(value / 360d))*360)) != 0) 
            {
              return (double)TG_TABLE[temp];
            } 
            else 
            { 
              return (double)TG_TABLE[360];
            }
          } 
          else 
          {
            return (double)TG_TABLE[(int)(value)];
          }
        } 
        else if ((-(int)value) > 360d) 
        {
          if ((temp=(-((int)value-((int)(value / 360d))*360))) != 0) 
          {
            return (double)TG_TABLE[temp];
          } 
          else 
          {
            return (double)TG_TABLE[360];
          }
        } 
        else 
        {
          return (double)TG_TABLE[-(int)(value)];
        }
        //return (double)TG_TABLE[(int)(value)];
    }

    public static final double tan(final double value)
    {
        final int temp;
        if (value > 0) 
        {
          if ((int)value > 360d) 
          {
            if ((temp=((int)value-((int)(value / 360d))*360)) != 0) 
            {
              return (double)TG_TABLE[temp];
            } 
            else 
            { 
              return (double)TG_TABLE[360];
            }
          } 
          else 
          {
            return (double)TG_TABLE[(int)(value)];
          }
        } 
        else if ((-(int)value) > 360d) 
        {
          if ((temp=(-((int)value-((int)(value / 360d))*360))) != 0) 
          {
            return (double)TG_TABLE[temp];
          } 
          else 
          {
            return (double)TG_TABLE[360];
          }
        } 
        else 
        {
          return (double)TG_TABLE[-(int)(value)];
        }
        //return (double)TG_TABLE[(int)(value)];
    }

    public static final double ctg(final double value)
    {
        final int temp;
        if (value > 0) 
        {
          if ((int)value > 360d) 
          {
            if ((temp=((int)value-((int)(value / 360d))*360)) != 0) 
            {
              return (double)CTG_TABLE[temp];
            } 
            else 
            { 
              return (double)CTG_TABLE[360];
            }
          } 
          else 
          {
            return (double)CTG_TABLE[(int)(value)];
          }
        } 
        else if ((-(int)value) > 360d) 
        {
          if ((temp=(-((int)value-((int)(value / 360d))*360))) != 0) 
          {
            return (double)CTG_TABLE[temp];
          } 
          else 
          {
            return (double)CTG_TABLE[360];
          }
        } 
        else 
        {
          return (double)CTG_TABLE[-(int)(value)];
        }
        //return (double)CTG_TABLE[(int)(value)];
    }

    static
    {
        for (int i = 0; i < 361; i++)
        {
            SIN_TABLE[i] = (float)Math.sin((double)i);
        }
        for (int i = 0; i < 361; i++)
        {
            COS_TABLE[i] = (float)Math.cos((double)i);
        }
        for (int i = 0; i < 361; i++)
        {
            TG_TABLE[i] = (float)Math.tan((double)i);
        }
        for (int i = 0; i < 361; i++)
        {
            CTG_TABLE[i] = 1f/((float)Math.tan((double)i));
        }
    }
}