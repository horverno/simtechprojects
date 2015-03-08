using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace CsharpEducational
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        const float T0 = 100f; // kezdőérték
        const float TR = 20f; // külső hőmérséklet
        const float k = 0.07f; // hűlési konstans
        readonly static float Zoom = 5;
        const int n = 100;
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

        /// <summary>
        /// Kirajzlás
        /// </summary>
        /// <param name="f">hűlési függvény</param>
        /// <param name="y">t0 értéke</param>
        /// <param name="n">db</param>
        /// <param name="h">dettaT</param>
        public void Euler(func f, float y, int n, float h)
        {
            for (float x = 0; x <= n; x += h)
            {
                System.Diagnostics.Debug.WriteLine("\t" + x + "\t" + y);
                y += h * f(y);
                // pontok (2 széles vonalak) hozzáadása a rajzhoz (x,y) koordináta rendszerben
                canvEuler.Children.Add(new Line
                {
                    Stroke = System.Windows.Media.Brushes.DarkBlue,
                    StrokeThickness = 4,
                    X1 = (int)Zoom * x + 20, // a 20 azért kell, hogy elférjen a skála
                    Y1 = (int)Zoom * (T0 - y),
                    X2 = (int)Zoom * x + 2 + 20, // a 20 azért kell, hogy elférjen a skála, a 2 pedig, hogy legyen kiterjedése a vonalnak
                    Y2 = (int)Zoom * (T0 - y) + 2 
                });
            }
        }
        /// <summary>
        /// Skála megjelenítése a vászonon
        /// </summary>
        public void DisplayScale()
        {
            for (int i = (int)TR; i <= T0; i += 10)
            {
                TextBlock testc = new TextBlock();
                testc.Text = i.ToString();
                Canvas.SetTop(testc, (int)Zoom * (T0 - i));
                canvEuler.Children.Add(testc);
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
        }
    }
}
