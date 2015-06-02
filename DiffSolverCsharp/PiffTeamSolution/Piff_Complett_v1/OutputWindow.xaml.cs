using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;
using System.IO;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace Piff_Complett_v1
{
    public partial class OutputWindow : Window
    {
        static float[, ,] Coords = new float[3, 3, 3001]; // A lépéseket tartalmazó tömb - 1. fgv sorszam 2. x - y azonosító 3. x - y érték

        const float T0 = 100f; // kezdoérték
        const float TR = 20f; // külso homérséklet
        const float k = 0.07f; // hulési konstans
        readonly static float Zoom = 5;
        float n = (float)App.myMath.GetTime();                   // Y koordinata terjedelme (altalaban ido) 
        float start = (float)App.myMath.GetStart();                   // Y koordinata terjedelme (altalaban ido) 

        static int co = -1, nr = 0;

        public OutputWindow()
        {
            InitializeComponent();

            testAtadas();
            TxDelta.Text = App.myMath.Step.ToString();
            NewPlot();
        }
        //készítette Cs J [Math team] 05.23
        public void testAtadas()
        {
            //Y tömb jelenleg vmiért nullt ad vissza ezért commenteltem
            float[] xtomb = App.myMath.X;
            float[] ytomb = App.myMath.Y;
            int xLength = App.myMath.X.Length;
            int yLength = App.myMath.Y.Length;
            string lepes = App.myMath.Step.ToString();
            string fgv = App.myMath.f.ToString();
            MessageBox.Show("X tömb hossza: " + xLength + Environment.NewLine +
                "Y tömb hossza: " + yLength + Environment.NewLine +
                "Lépés: " + lepes + Environment.NewLine + "Függvény: " + fgv);
        }

        /// <param name="f">hulési függvény</param>
        /// <param name="y">t0 értéke</param>
        /// <param name="n">db</param>
        /// <param name="h">dettaT</param>
        /// <param name="co">aktuális szín</param>


        public void Masol(int co)    // kihagyja y 0 erteket 
        {

            for (nr = 0; nr < App.myMath.X.Length - 1; nr++)
            {

                Coords[co, 0, nr] = App.myMath.X[nr + 1];
                Coords[co, 1, nr] = App.myMath.Y[nr + 1];
                Coords[co, 2, nr] = nr;

                System.Diagnostics.Debug.WriteLine("\t" + App.myMath.X[nr + 1] + "\t" + App.myMath.Y[nr + 1] + "\t" + nr);

            }
        }


        /* public void Masol(int co)   // nem hagyja ki y 0 erteket
         {

             for (nr = 0; nr < App.myMath.X.Length; nr++)
             {

                 Coords[co, 0, nr] = App.myMath.X[nr];
                 Coords[co, 1, nr] = App.myMath.Y[nr];
                 Coords[co, 2, nr] = nr;

                 System.Diagnostics.Debug.WriteLine("\t" + App.myMath.X[nr] + "\t" + App.myMath.Y[nr] + "\t" + nr);

             }
         }*/


        public void Draw()
        {
            // co azt azonosítja hogy draw button katt esetén melyik szín jön most, it coord elso dimenzioja
            // isChecked: pipa kivétel esetén el kellene tünjön(mindet újra rajzolva kivéve disabled-et)
            float distance, min, max;
            GetMaxMin(out max, out min, out distance);
            float diff = (100 - max);
            if (l1check.IsChecked == true)
            {
                for (int x = 0; x == Coords[0, 2, x]; x++) //  végig megy az elemeken, sorszám szerint
                {
                    canvEuler.Children.Add(new Line
                    {
                        Stroke = System.Windows.Media.Brushes.DarkBlue,
                        StrokeThickness = 4,
                        X1 = (int)Zoom * ((Coords[0, 0, x] / ((float)n / 100)) - (100) * start / n) + 50, // a 50 azért kell, hogy elférjen a skála
                        Y1 = (int)Zoom * (100 - (Coords[0, 1, x] - min) / (distance / 100)) + 15,  // az osztas barmilyen intervallumot 100as skalahoz igazit, a kivonas pedig grafikon tetejehez igazitja
                        X2 = (int)Zoom * ((Coords[0, 0, x] / ((float)n / 100) - (100) * start / n)) + 52, // a 50 azért kell, hogy elférjen a skála, a 2 pedig, hogy legyen kiterjedése a vonalnak
                        Y2 = (int)Zoom * (100 - (Coords[0, 1, x] - min) / (distance / 100)) + 17
                    });
                    System.Diagnostics.Debug.WriteLine(" X1 " + ((Coords[0, 0, x] / ((float)n / 100)) - (100) * start / n) + " Y1 " + ((100 - (Coords[0, 1, x] - min) / (distance / 100))) + " idoskala: " + n);
                }
            }
            if (l2check.IsChecked == true)
            {
                for (int x = 0; x == Coords[1, 2, x]; x++)
                {
                    canvEuler.Children.Add(new Line
                    {
                        Stroke = System.Windows.Media.Brushes.DarkGreen,
                        StrokeThickness = 4,
                        X1 = (int)Zoom * ((Coords[1, 0, x] / ((float)n / 100)) - (100) * start / n) + 50, // a 50 azért kell, hogy elférjen a skála
                        Y1 = (int)Zoom * (100 - (Coords[1, 1, x] - min) / (distance / 100)) + 15,  // az osztas barmilyen intervallumot 100as skalahoz igazit, a kivonas pedig grafikon tetejehez igazitja
                        X2 = (int)Zoom * ((Coords[1, 0, x] / ((float)n / 100) - (100) * start / n)) + 52, // a 50 azért kell, hogy elférjen a skála, a 2 pedig, hogy legyen kiterjedése a vonalnak
                        Y2 = (int)Zoom * (100 - (Coords[1, 1, x] - min) / (distance / 100)) + 17
                    });
                }
            }
            if (l3check.IsChecked == true)
            {
                for (int x = 0; x == Coords[2, 2, x]; x++)
                {
                    canvEuler.Children.Add(new Line
                    {
                        Stroke = System.Windows.Media.Brushes.DarkRed,
                        StrokeThickness = 4,
                        X1 = (int)Zoom * ((Coords[2, 0, x] / ((float)n / 100)) - (100) * start / n) + 50, // a 50 azért kell, hogy elférjen a skála
                        Y1 = (int)Zoom * (100 - (Coords[2, 1, x] - min) / (distance / 100)) + 15,  // az osztas barmilyen intervallumot 100as skalahoz igazit, a kivonas pedig grafikon tetejehez igazitja
                        X2 = (int)Zoom * ((Coords[2, 0, x] / ((float)n / 100) - (100) * start / n)) + 52, // a 50 azért kell, hogy elférjen a skála, a 2 pedig, hogy legyen kiterjedése a vonalnak
                        Y2 = (int)Zoom * (100 - (Coords[2, 1, x] - min) / (distance / 100)) + 17
                    });
                }
            }
        }


        public void GetMaxMin(out float max, out float min, out float distance)
        {
            min = Coords[0, 1, 0]; max = Coords[0, 1, 0];
            for (int x = 0; x == Coords[0, 2, x]; x++)
            {       // min es max tengelyertekek X síkon elso grafikon szerint rajzolodik fel
                if (Coords[0, 1, x] < min) min = Coords[0, 1, x];
                if (Coords[0, 1, x] > max) max = Coords[0, 1, x];
            }
            distance = Math.Abs(max - min);  //  tavolsag min és max kozott
        }


        private void DisplayScale()
        {
            float distance, min, max;
            GetMaxMin(out max, out min, out distance);

            System.Diagnostics.Debug.WriteLine("min " + min + " max " + max + "\t");

            //szazalekot szamol Y tengelyre 100% a max ertek 0% a miniimum. pl 50% 0.5 szorosa 100nak
            int j = 0;
            for (float i = min; j <= 100; i += (distance / 10))
            {

                canvEuler.Children.Add(new Line                 // vonal racs vizszintes
                {
                    Stroke = System.Windows.Media.Brushes.Gray,
                    StrokeThickness = 0.5,
                    X1 = 1,
                    X2 = 2000,
                    Y1 = j * 5 + 16,
                    Y2 = j * 5 + 16,
                });
                TextBlock testc = new TextBlock();

                testc.Text = " " + Math.Round((i), 2) + "°C";

                Canvas.SetTop(testc, (int)Zoom * (100 - j));
                canvEuler.Children.Add(testc);
                System.Diagnostics.Debug.WriteLine(" j " + j + " i " + i);
                j += 10;
            }
            j = 0;

            for (float i = 0; j <= 100; i += n / 10)
            {

                canvEuler.Children.Add(new Line                 // vonal racs fuggoleges
                {
                    Stroke = System.Windows.Media.Brushes.Gray,
                    StrokeThickness = 0.5,
                    X1 = 50 + j * 5,
                    X2 = 50 + j * 5,
                    Y1 = 1,
                    Y2 = 2000,
                });

                TextBlock testv = new TextBlock();
                testv.Text = Math.Round(i + start, 2).ToString() + "s";
                Canvas.SetLeft(testv, (int)Zoom * j + 53);
                canvEuler.Children.Add(testv);
                j += 10;
            }
        }

        private void DrawButton_Click(object sender, RoutedEventArgs e)
        {
            App.myMath.Step = Convert.ToSingle(TxDelta.Text);
            NewPlot();
        }
        private void NewPlot()
        {
            try
            {

                //App.myMath.Step = Convert.ToSingle(TxDelta.Text);

                if (co == 2) co = -1; // ciklusvaltozo, a kirajzolt fgv azosítója >> színe
                co++;

                Masol(co);
                if (co == 0) l1check.IsChecked = true;
                if (co == 1) l2check.IsChecked = true;
                if (co == 2) l3check.IsChecked = true;
            }
            catch (Exception)
            {
                MessageBox.Show("Please enter a valid number.");
            }
        }

        private void ClearButton_Click(object sender, RoutedEventArgs e)
        {
            canvEuler.Children.Clear();

            l1check.IsChecked = null;
            l2check.IsChecked = null;
            l3check.IsChecked = null;
            Array.Clear(Coords, 0, Coords.Length);
            co = -1;
        }

        private void TxtBtn_Click(object sender, RoutedEventArgs e)
        {
            string TextName = TxtSaveBox.Text;
            StreamWriter sw = new StreamWriter(TextName, true);
            int n = Coords.GetLength(2);
            for (int co = 0; co < 3; co++)
            {
                try
                {
                    sw.WriteLine("Fuggveny szam:" + co);
                    for (int nr = 0; nr == Coords[co, 2, nr]; nr++)
                    {
                        sw.WriteLine("\t" + Coords[co, 0, nr] + "\t" + Coords[co, 1, nr] + "\t" + Coords[co, 2, nr]);
                    }
                }
                catch (Exception ex)
                { Console.WriteLine("Exception: " + ex.Message); }
            } sw.Close();
        }


        private void picbtn_Click(object sender, RoutedEventArgs e)
        {
            RenderTargetBitmap bmp = new RenderTargetBitmap(
            (int)canvEuler.ActualWidth,
            (int)canvEuler.ActualHeight,
            96d, 96d, PixelFormats.Pbgra32);
            bmp.Render(canvEuler);
            BmpBitmapEncoder encoder = new BmpBitmapEncoder();
            encoder.Frames.Add(BitmapFrame.Create(bmp));
            string PicName = PicSaveBox.Text;
            FileStream fs = File.Open(PicName, FileMode.OpenOrCreate);
            encoder.Save(fs);
        }
        private void l1check_Checked(object sender, RoutedEventArgs e)
        {
            canvEuler.Children.Clear();
            DisplayScale();
            Draw();
        }

        private void l2check_Checked(object sender, RoutedEventArgs e)
        {
            canvEuler.Children.Clear();
            DisplayScale();
            Draw();
        }

        private void l3check_Checked(object sender, RoutedEventArgs e)
        {
            canvEuler.Children.Clear();
            DisplayScale();
            Draw();
        }

        private void TxtSaveBox_TextChanged(object sender, TextChangedEventArgs e) { }

        private void PicSaveBox_TextChanged(object sender, TextChangedEventArgs e) { }


    }
}
