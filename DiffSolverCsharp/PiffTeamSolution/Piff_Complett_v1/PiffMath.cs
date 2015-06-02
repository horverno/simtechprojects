using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Piff_Complett_v1
{
    public class MyMath
    {
        public delegate float Function(float t, float y); //Ha kétváltozós a fv. y'=f(t,y) akkor kellet t is.
        private static float startY; //Kezdőérték f(0)
        private static float startTime; //Kezdeti időpont x(0)
        private static float endTime; //Meddig fusson a szimuláció 
        private static int diffType; //A differenciál egyenlet megadási típusa
        //0 - euler
        //1 - explicit
        //2 - adaptív
        //3 - implicit

        public Function f; //A kapott derivált függvény 
        private static float step; //Lépésköz (deltaT)
        private static float[] xCoordinates; // A lépéseket tartalmazó tömb
        private static float[] yCoordinates; // Az y értékeket tartalmazó tömb

        
        public float Step
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
        public float[] Y
        {
            get
            {
                return yCoordinates;
            }
        } //Az y koordinátákat tartalmazó tömb(yCoordinates) elérése osztályon kívülről

        public float[] X
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
        static private float[] GetStep(float start, float finish, float interval)
        {
            if (finish < start) throw new ArgumentException("Finish nem lehet nagyobb mint a start");
            if (interval <= 0) throw new ArgumentException("Az lépésköznek nagyobbnak kell lennie mint 0");

            float[] xCoordinates = new float[(int)((finish - start) / interval) + 1];
       //     int digits = 5;
            //interval = Math.Round(interval, 5);

            //float actual = start;
            xCoordinates[0] = start;
            for (int i = 0; i < xCoordinates.Length; ++i)
            {
                xCoordinates[i] = interval * i + start;
     //           xCoordinates[i] = Math.Round(xCoordinates[i], digits);

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
        public MyMath(float _starttime, float _endtime, float _starty, float _step, Function _f, int _diffType)
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

            Calculate();
        }

        //runge kutta 4th method
        public void runge(Function f)
        {
            float w, k1, k2, k3, k4;
            yCoordinates = new float[xCoordinates.Length];
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
            float temp = startY;
            yCoordinates = new float[xCoordinates.Length];

            yCoordinates[0] = startY;
            for (int i = 1; i < xCoordinates.Length; ++i)
            {
                temp = step * f(xCoordinates[i], temp);
                yCoordinates[i] = temp;
            }
        }


        public void implicitEulerMethod(Function f)
        {
            float temp = startY;
            float forwardEulerResult = startY;
            yCoordinates = new float[xCoordinates.Length];
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
            float k1, k2;
            float temp = startY;
            yCoordinates = new float[xCoordinates.Length];
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


        public float GetTime()
        {
            return endTime - startTime;
        }

        public float GetStart()
        {
            return startTime;
        }
       
        private void setDefault()
        {
            GetStep(0, 10, .01f);
            startTime = 0;
            endTime = 5;
            startY = 1;
            step = .01f;
        }

        private float testfv(float t, float y)
        {
            return -y + t + 1;
        }

        //
        //Tesztfüggvények vége
        //


    }
}
