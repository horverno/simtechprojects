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

    public partial class MainWindow : Window
    {
        static float[,,] Coords=new float[3,3,3001]; // A lépéseket tartalmazó tömb - 1. fgv sorszam 2. x - y azonosító 3. x - y érték

        const float T0 = 100f; // kezdőérték
        const float TR = 20f; // külső hőmérséklet
        const float k = 0.07f; // hűlési konstans
        readonly static float Zoom = 5;
        const int n = 130;               
        static int co = -1, nr = 0;
   
        public MainWindow()
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
        {       // co azt azonosítja hogy draw button katt esetén melyik szín jön most, X
                // enbabled: pipa kivétel esetén el kellene tünjön(mindet újra rajzolva kivéve disabled-et)
            if (l1check.IsChecked == true)
                {
                    for (int x = 0; x == Coords[0, 2, x]; x++) //  végig megy az elemeken, sorszám szerint
                    {
                        canvEuler.Children.Add(new Line
                        {
                            Stroke = System.Windows.Media.Brushes.DarkBlue,
                            StrokeThickness = 4,
                            X1 = (int)Zoom * Coords[0, 0, x] + 50, // a 50 azért kell, hogy elférjen a skála
                            Y1 = (int)Zoom * (T0 - Coords[0, 1, x]),
                            X2 = (int)Zoom * Coords[0, 0, x] + 2 + 50, // a 50 azért kell, hogy elférjen a skála, a 2 pedig, hogy legyen kiterjedése a vonalnak
                            Y2 = (int)Zoom * (T0 - Coords[0, 1, x]) + 2
                        });
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
                            Y1 = (int)Zoom * (T0 - Coords[1, 1, x]),
                            X2 = (int)Zoom * Coords[1, 0, x] + 2 + 50,
                            Y2 = (int)Zoom * (T0 - Coords[1, 1, x]) + 2
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
                            Y1 = (int)Zoom * (T0 - Coords[2, 1, x]),
                            X2 = (int)Zoom * Coords[2, 0, x] + 2 + 50,
                            Y2 = (int)Zoom * (T0 - Coords[2, 1, x]) + 2
                        });
                    }
                }
        }

        private void DisplayScale()
        {
          for (int i = (int)TR; i <= T0; i += 10)
            {

                TextBlock testc = new TextBlock();
                testc.Text = i.ToString()+"°C";
                Canvas.SetTop(testc, (int)Zoom * (T0 - i));
                canvEuler.Children.Add(testc);
            }
            for (int i = 0; i <= n; i += 10)
            {

                TextBlock testv = new TextBlock();
                testv.Text = i.ToString() + "s";
                Canvas.SetLeft(testv, (int)Zoom* i+40);
                canvEuler.Children.Add(testv);
            }
        }

        private void DrawButton_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                float delta = Convert.ToSingle(TxDelta.Text);
                if (co == 2) co = -1; // ciklusvaltozo, a kirajzolt fgv azosítója >> színe
                co++;

                if (co == 0) l1check.IsChecked = true;
                if (co == 1) l2check.IsChecked = true;
                if (co == 2) l3check.IsChecked = true;
                func f = new func(NewtonCooling);
                Euler(f, T0, n, delta, co);
                Draw();
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
