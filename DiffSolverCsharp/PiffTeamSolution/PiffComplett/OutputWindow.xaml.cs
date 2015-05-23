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


namespace PiffTeam
{

    public partial class OutputWindow : Window
    {
        static float[,,] Coords=new float[3,3,3001]; // A lépéseket tartalmazó tömb - 1. fgv sorszam 2. x - y azonosító 3. x - y érték

        const float T0 = 100f; // kezdőérték
        const float TR = 20f; // külső hőmérséklet
        const float k = 0.07f; // hűlési konstans
        readonly static float Zoom = 5;
        const int n = 80;               // Y koordinata terjedelme (altalaban ido) 
        static int co = -1, nr = 0;
   
        public OutputWindow()
        {
            InitializeComponent();
            
        }

        public delegate float func(float t);
        static float NewtonCooling(float t)
        {
            return -k * (t - TR);
        }

        /// <param name="f">hűlési függvény</param>
        /// <param name="y">t0 értéke</param>
        /// <param name="n">db</param>
        /// <param name="h">dettaT</param>
        /// <param name="co">aktuális szín</param>

        public void Euler(func f, float y, int n, float h, int co)
        {
             nr = 0; // ciklusvaltozo, X eredményé

            for (float x = 0; x <= n; x += h){          
        
              Coords[co,0,nr]=(float)x;
              Coords[co,1,nr]=(float)y;
              Coords[co,2,nr]=(float)nr;        

                System.Diagnostics.Debug.WriteLine("\t" + x + "\t" + y + "\t" + nr);
                y += h * f(y);

                nr++; }
            }

        public void Draw()
        {       // co azt azonosítja hogy draw button katt esetén melyik szín jön most, it coord elso dimenzioja
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
                            X1 = (int)Zoom * (Coords[0, 0, x] / ((float)n / 100)) + 50, // a 50 azért kell, hogy elférjen a skála
                            Y1 = (int)Zoom * (100 - (Coords[0, 1, x]-min) / (distance / 100)) + 15,  // az osztas barmilyen intervallumot 100as skalahoz igazit, a kivonas pedig grafikon tetejehez igazitja
                            X2 = (int)Zoom * (Coords[0, 0, x] / ((float)n / 100)) + 52, // a 50 azért kell, hogy elférjen a skála, a 2 pedig, hogy legyen kiterjedése a vonalnak
                            Y2 = (int)Zoom * (100 - (Coords[0, 1, x] - min) / (distance / 100)) + 17
                        });
                        System.Diagnostics.Debug.WriteLine(" X1 " + (Coords[0, 0, x] / ((float)n / 100)) + " Y1 " + ((100 - (Coords[0, 1, x] - min) / (distance / 100))));
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
                            X1 = (int)Zoom * Coords[1, 0, x] + 50,
                            Y1 = (int)Zoom * (100 - (Coords[1, 1, x] - min) / (distance / 100)) + 15, 
                            X2 = (int)Zoom * Coords[1, 0, x] + 2 + 50,
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
                            X1 = (int)Zoom * Coords[2, 0, x] + 50,
                            Y1 = (int)Zoom * (100 - (Coords[2, 1, x] - min) / (distance / 100)) + 15,
                            X2 = (int)Zoom * Coords[2, 0, x] + 2 + 50,
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
            distance = Math.Abs(max - min) ;  //  tavolsag min és max kozott
        }


        private void DisplayScale()
        {
            float distance, min, max;
            GetMaxMin(out max, out min, out distance);

            System.Diagnostics.Debug.WriteLine("min " + min + " max " + max + "\t");

            //szazalekot szamol Y tengelyre 100% a max ertek 0% a miniimum. pl 50% 0.5 szorosa 100nak
            int j=0;
            for (float i = min; j <= 100; i +=(distance/10))

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
              //distance=
                TextBlock testc = new TextBlock();
            //    if(j==0)testc.Text = " " + Math.Round(( min), 2) + "°C";
            //    else if(j==100)testc.Text = " " + Math.Round(( max), 2) + "°C";
           //     else 
                    testc.Text = " " + Math.Round(( i), 2) + "°C";
                    
                Canvas.SetTop(testc, (int)Zoom * (100 - j));
                canvEuler.Children.Add(testc);
                System.Diagnostics.Debug.WriteLine(" j " + j + " i " + i);
                j += 10;
            }               // n
            j = 0;
            for (int i = 0; i <= n; i += n/10)
            {
                
                canvEuler.Children.Add(new Line                 // vonal racs fuggoleges
                {
                    Stroke = System.Windows.Media.Brushes.Gray,
                    StrokeThickness = 0.5,
                    X1 = 50+j * 5,
                    X2 = 50+j * 5,
                    Y1 = 1,
                    Y2 = 2000,
                });

                TextBlock testv = new TextBlock();
                testv.Text = i.ToString() + "s";
                Canvas.SetLeft(testv, (int)Zoom* j+53);
                canvEuler.Children.Add(testv);
                j += 10;
            }
        }

        private void DrawButton_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                float delta = Convert.ToSingle(TxDelta.Text);
                if (co == 2) co = -1; // ciklusvaltozo, a kirajzolt fgv azosítója >> színe
                co++;
           //     Console.WriteLine("x: " + Cursor.Position.X + " y: " + Cursor.Position.Y);
                func f = new func(NewtonCooling);
                Euler(f, T0, n, delta, co);
                if (co == 0) l1check.IsChecked = true;
                if (co == 1) l2check.IsChecked = true;
                if (co == 2) l3check.IsChecked = true;
            }
            catch(Exception)
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
