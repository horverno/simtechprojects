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
        static float[,,] xCoordinates=new float[3,2,200001]; // A lépéseket tartalmazó tömb - 1. fgv sorszam 2. x - y azonosító 3. x - y érték

        const float T0 = 100f; // kezdőérték
        const float TR = 20f; // külső hőmérséklet
        const float k = 0.07f; // hűlési konstans
        readonly static float Zoom = 5;
        const int n = 130;
        static int co = -1, nr = 0;
   
        public MainWindow()
        {
            InitializeComponent();
            DisplayScale();
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

        public void Euler(func f, float y, int n, float h)
        {
            string TextName = TxtSaveBox.Text;
            StreamWriter sw = new StreamWriter(TextName, true);
             nr = 0; // ciklusvaltozo, X eredményé
             if (co == 2) co = -1; // ciklusvaltozo, a kirajzolt fgv azosítója >> színe
             co++;
            sw.WriteLine("Fuggveny szam:" + co);

            if (co == 0) l1check.IsChecked=true;
            if (co == 1) l2check.IsChecked=true;
            if (co == 2) l3check.IsChecked=true;
            
            for (float x = 0; x <= n; x += h){          
                try
            {sw.WriteLine("\t" + x + "\t" + y + "\t" + nr);}
            catch (Exception e)
            {Console.WriteLine("Exception: " + e.Message);}
           
              xCoordinates[co,0,nr]=(float)x;
              xCoordinates[co,1,nr]=(float)y;                
        
                System.Diagnostics.Debug.WriteLine("\t" + x + "\t" + y + "\t" + nr);
                y += h * f(y);

                Draw(xCoordinates[co, 0, nr], xCoordinates[co, 1, nr], co);    // pontok (2 széles vonalak) hozzáadása a rajzhoz (x,y) koordináta rendszerben
                nr++; }          
            sw.Close();}

        public void Draw( float x, float y, int co)
        {
            if (co == 0 && l1check.IsEnabled == true)   // co azt azonosítja hogy draw button katt esetén melyik szín jön most, X
                                                        // enbabled: pipa kivétel esetén el kellene tünjön(mindet újra rajzolva kivéve disabled-et)
//               if (l1check.IsEnabled == true )
            {
                canvEuler.Children.Add(new Line
                {
                    Stroke = System.Windows.Media.Brushes.DarkBlue,
                    StrokeThickness = 4,
                    X1 = (int)Zoom * x + 50, // a 20 azért kell, hogy elférjen a skála
                    Y1 = (int)Zoom * (T0 - y),
                   X2 = (int)Zoom * x + 2 + 50, // a 20 azért kell, hogy elférjen a skála, a 2 pedig, hogy legyen kiterjedése a vonalnak
                    Y2 = (int)Zoom * (T0 - y) + 2
                });
            }
            if (co == 1 && l2check.IsEnabled == true)
          //  if (l2check.IsEnabled == true)
               {
                canvEuler.Children.Add(new Line
                {
                    Stroke = System.Windows.Media.Brushes.DarkGreen,
                    StrokeThickness = 4,
                    X1 = (int)Zoom * x + 50, // a 20 azért kell, hogy elférjen a skála
                    Y1 = (int)Zoom * (T0 - y),
                    X2 = (int)Zoom * x + 2 + 50, // a 20 azért kell, hogy elférjen a skála, a 2 pedig, hogy legyen kiterjedése a vonalnak
                    Y2 = (int)Zoom * (T0 - y) + 2
                });
            }
          // if (l3check.IsEnabled == true )
               if (co == 2 && l3check.IsEnabled == true)
           {
                canvEuler.Children.Add(new Line
                {
                    Stroke = System.Windows.Media.Brushes.DarkRed,
                    StrokeThickness = 4,
                    X1 = (int)Zoom * x + 50, // a 20 azért kell, hogy elférjen a skála
                    Y1 = (int)Zoom * (T0 - y),
                    X2 = (int)Zoom * x + 2 + 50, // a 20 azért kell, hogy elférjen a skála, a 2 pedig, hogy legyen kiterjedése a vonalnak
                    Y2 = (int)Zoom * (T0 - y) + 2
                });
            }
        }

        public void DisplayScale()
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
                func f = new func(NewtonCooling);
                Euler(f, T0, n, delta);
            }
            catch(Exception)
            {
                MessageBox.Show("Please enter a valid number.");
            }
        }

        private void ClearButton_Click(object sender, RoutedEventArgs e)
        {
            canvEuler.Children.Clear();
            DisplayScale();
            l1check.IsChecked = null;
            l2check.IsChecked = null;
            l3check.IsChecked = null;
            Array.Clear(xCoordinates, 0, 0);
            co = -1;
        }


        private void l1check_Checked(object sender, RoutedEventArgs e)
        {
          
        canvEuler.Children.Clear();
        DisplayScale();
            //        Draw(xCoordinates[0, 0, nr], xCoordinates[0, 1, nr], 0);
            //        Draw(xCoordinates[1, 0, nr], xCoordinates[1, 1, nr], 1);
            //        Draw(xCoordinates[2, 0, nr], xCoordinates[2, 1, nr], 2);
        //Draw(xCoordinates[co, 0, nr], xCoordinates[co, 1, nr], co);
        }

        private void l2check_Checked(object sender, RoutedEventArgs e)
        {
    //   canvEuler.Children.Clear();
    //        DisplayScale();
            //          Draw(xCoordinates[co, 0, nr], xCoordinates[co, 1, nr], co);
        }

        private void l3check_Checked(object sender, RoutedEventArgs e)
        {
      //      canvEuler.Children.Clear();
       //     DisplayScale();
            //          Draw(xCoordinates[co, 0, nr], xCoordinates[co, 1, nr], co);
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

        private void TxtSaveBox_TextChanged(object sender, TextChangedEventArgs e)
        {
            string TextName = TxtSaveBox.Text;
        }

        private void TxtBtn_Click(object sender, RoutedEventArgs e)
        {
            string TextName = TxtSaveBox.Text;
        }

        private void PicSaveBox_TextChanged(object sender, TextChangedEventArgs e)
        {
            string PicName = PicSaveBox.Text;
        }
    }
}
