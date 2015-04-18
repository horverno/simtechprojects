using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Mathematics
{
    public class MyMath
    {
        private double startY; //Kezdőérték f(0)
        private double startTime; //Kezdeti időpont x(0)
        private double endTime; //Meddig fusson a szimuláció 
        private int diffType; //A differenciál egyenlet megadási típusa
        //0 - implicit
        //1 - explicit
        //2 - adaptív

        private double[] xCoordinates; // A lépéseket tartalmazó tömb
        private double[] yCoordinates; // Az y értékeket tartalmazó tömb

        public delegate double Function (double x); //A bemeneti függvény
        /// <summary>
        /// X értékeket tartalmazó tömböt ad vissza, mely startól-finishig interval lépésközzel tartalmazza az értékeket.
        /// </summary>
        /// <param name="start">Kezdőértéke</param>
        /// <param name="finish"></param>
        /// <param name="interval">Lépésköz</param>
        /// <returns></returns>
        static public double[] GetStep(double start, double finish, double interval)
        {
            if (finish < start) throw new ArgumentException("Finish nem lehet nagyobb mint a start");
            if (interval <= 0) throw new ArgumentException("Az lépésköznek nagyobbnak kell lennie mint 0");

            double[] xCoordinates = new double[(int)((finish - start) / interval) + 1];
            int digits = (interval - (int)interval).ToString().Length;

            //double actual = start;
            xCoordinates[0] = start;
            for (int i = 0; i < xCoordinates.Length; ++i)
            {
                xCoordinates[i] = interval * i + start;
                xCoordinates[i] = Math.Round(xCoordinates[i], digits);
                
            }
            xCoordinates[xCoordinates.Length - 1] = finish;
            return xCoordinates;
        } //Lépések kiszámolása
        public MyMath(double _starty, double _starttime, double _endtime, Function f)
        {
            startY=_starty;
            startTime=_starttime;
            endTime=_endtime;

        }

        
    }
}
