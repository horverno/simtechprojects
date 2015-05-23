using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Piff_Complett_v1
{
    public class MyMath
    {
        public delegate double Function(double t, double y); //Ha kétváltozós a fv. y'=f(t,y) akkor kellet t is.
        private static double startY; //Kezdőérték f(0)
        private static double startTime; //Kezdeti időpont x(0)
        private static double endTime; //Meddig fusson a szimuláció 
        private static int diffType; //A differenciál egyenlet megadási típusa
        //0 - euler
        //1 - explicit
        //2 - adaptív
        //3 - implicit

        public Function f; //A kapott derivált függvény 
        private static double step; //Lépésköz (deltaT)
        private static double[] xCoordinates; // A lépéseket tartalmazó tömb
        private static double[] yCoordinates; // Az y értékeket tartalmazó tömb

        
        public double Step
        {
            get
            {
                return step;
            }
            set
            {
                step = value;
                xCoordinates = GetStep(startTime, endTime, step);
                Calculate();
            }
        } //A lépésköz beállítása, mely hatására az osztály újraszámolja az x illetve az y értékeket is automatikusan
        public double[] Y
        {
            get
            {
                return yCoordinates;
            }
        } //Az y koordinátákat tartalmazó tömb(yCoordinates) elérése osztályon kívülről

        public double[] X
        {
            get
            {
                return xCoordinates;
            }
        }//Az x koordinátákat tartalmazó tömb(xCoordinates) elérése osztályon kívülről
        //A bemeneti függvény


        /// <summary>
        /// X értékeket tartalmazó tömböt ad vissza, mely startól-finishig interval lépésközzel tartalmazza az értékeket.
        /// </summary>
        /// <param name="start">Kezdőértéke</param>
        /// <param name="finish"></param>
        /// <param name="interval">Lépésköz</param>
        /// <returns></returns>
        static private double[] GetStep(double start, double finish, double interval)
        {
            if (finish < start) throw new ArgumentException("Finish nem lehet nagyobb mint a start");
            if (interval <= 0) throw new ArgumentException("Az lépésköznek nagyobbnak kell lennie mint 0");

            double[] xCoordinates = new double[(int)((finish - start) / interval) + 1];
            int digits = 5;
            interval = Math.Round(interval, 5);

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
        /// <summary>
        /// 
        /// </summary>
        /// <param name="_starttime">A szimuláció kezdőidőpontja >0</param>
        /// <param name="_endtime">A szimláció befejezésének időpontja, nagyobb mint a kezdőidőpont</param>
        /// <param name="_starty">A szimuláció kezdeti pillanatában a kezdőérték</param>
        /// <param name="_step">A kezdeti lépésköz</param>
        /// <param name="_f">A differenciálegyenlet</param>
        /// <param name="_diffType">A megoldás típusa 0-3 közötti szám</param>
        public MyMath(double _starttime, double _endtime, double _starty, double _step, Function _f, int _diffType)
        {
            /*Argumentum kivétel dobása, ha 
             * a kezdőidőpont kisebb mint 0 
             * a befejezés időpontja kisebb mint a kezdőidőpont
             * a megoldás típusa nem lehetséges érték
             * ha a lépésköz nagyobb mint a befejezés és kezdőidőpont között eltelt idő
            */
            if (_starttime < 0 || _endtime < _starttime || 
                diffType < 0 || diffType > 3 || step>_endtime-_starttime) throw new ArgumentException("Rossz paraméterek");
            //Értékek beállítása, majd a lépésköz alapján az x értékek kiszámítása
            startY = _starty;
            startTime = _starttime;
            endTime = _endtime;
            step = _step;
            diffType = _diffType;
            f = _f;
            xCoordinates = GetStep(startTime, endTime, step);
        }

        //runge kutta 4th method
        public void runge(Function f)
        {
            double w, k1, k2, k3, k4;
            yCoordinates = new double[xCoordinates.Length];
            w = startY;
            for (int i = 0; i < xCoordinates.Length; i++)
            {
                k1 = step * f(xCoordinates[i], w);
                k2 = step * f(xCoordinates[i] + step / 2, w + k1 / 2);
                k3 = step * f(xCoordinates[i] + step / 2, w + k2 / 2);
                k4 = step * f(xCoordinates[i] + step, w + k3);
                w = w + (k1 + 2 * k2 + 2 * k3 + k4) / 6;
                yCoordinates[i] = w;

            }
        }

        public void eulerMethod(Function f)
        {
            double temp = startY;
            yCoordinates = new double[xCoordinates.Length];

            yCoordinates[0] = startY;
            for (int i = 1; i < xCoordinates.Length; ++i)
            {
                temp = step * f(xCoordinates[i], temp);
                yCoordinates[i] = temp;
            }
        }


        public void implicitEulerMethod(Function f)
        {
            double temp = startY;
            double forwardEulerResult = startY;

            yCoordinates[0] = startY;
            for (int i = 1; i < xCoordinates.Length; ++i)
            {
                forwardEulerResult = temp;
                forwardEulerResult += step * f(xCoordinates[i], temp);
                temp += step * f(xCoordinates[i], forwardEulerResult);
                yCoordinates[i] = temp;

            }
        }

        public void explicitRungeKutta(Function f)
        {
            double k1, k2;
            double temp = startY;
            for (int i = 0; i < xCoordinates.Length; ++i)
            {
                k1 = step * f(xCoordinates[i], temp);
                k2 = step * f(xCoordinates[i] + (step / 2), temp + (k1 / 2));
                temp += k2;
                yCoordinates[i] = temp;
            }
        }

        public void Calculate()
        {
            switch(diffType)
            {
                case 0:
                    {
                        eulerMethod(f);
                        break;
                    }
                case 1:
                    {
                        explicitRungeKutta(f);
                        break;
                    }
               case 2:
                    {
                        runge(f);
                        break;
                    }
                case 3:
                    {
                        implicitEulerMethod(f);
                        break;
                    }
            }
        }

        //
        //Tesztfüggvények
        //
        public void TestRunge()
        {
            setDefault();
            runge(testfv);
        }

        public void TestEuler()
        {
            setDefault();
            eulerMethod(testfv);
        }

        public void TestImplicitEuler()
        {
            setDefault();
            implicitEulerMethod(testfv);
        }

        public void TestExplicitEueler()
        {
            setDefault();
            explicitRungeKutta(testfv);
        }

        private void setDefault()
        {
            GetStep(0, 5, .01f);
            startTime = 0;
            endTime = 5;
            startY = 1;
            step = .01f;
        }

        private double testfv(double t, double y)
        {
            return -y + t + 1;
        }

        //
        //Tesztfüggvények vége
        //


    }
}
