using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace Piff_Complett_v1
{
    /// <summary>
    /// Interaction logic for Window1.xaml
    /// </summary>
    public partial class MainWindow : Window
    {

        public TextBox infocus;
        public int modszer;
        public string modszertext;
        public string aktual;

        public MainWindow()
        {
            InitializeComponent();
            modifystep();
        }

        public void modifystep()
        {
            Stepscrollbar.Minimum = 0.1;
            Stepscrollbar.Maximum = X_max_scrollbar.Value - X_min_scrollbar.Value;
        }




        private void kilepes2_Click(object sender, RoutedEventArgs e)
        {
            System.Environment.Exit(0);
        }


        private void Window_Closing(object sender, System.ComponentModel.CancelEventArgs e)
        {
            System.Environment.Exit(0);
        }

        bool IsDigitsOnly(string str)
        {
            foreach (char c in str)
            {
                if (c < '0' || c > '9')
                    return false;
            }

            return true;
        }



        private void char1_Click(object sender, RoutedEventArgs e)
        {
            int kari;
            string symbol;
            string first;
            string second;
            symbol = sender.ToString();
            symbol = symbol.Substring(symbol.Length - 1);

            if (infocus != null)
            {
                if ((infocus != szamlalo1) || (infocus == szamlalo1) && (szamlalo1.Text.Length != 1))
                {
                    kari = infocus.CaretIndex;
                    first = infocus.Text.Substring(0, kari);
                    second = infocus.Text.Substring(kari);
                    infocus.Text = first + symbol + second;
                    infocus.Focus();
                    infocus.CaretIndex = kari + 1;
                }
            }

        }


        private void szamlalo1_GotFocus(object sender, RoutedEventArgs e)
        {
            infocus = szamlalo1;
        }

        private void szamlalo2_GotFocus(object sender, RoutedEventArgs e)
        {
            infocus = szamlalo2;
        }

        private void rb1_Checked(object sender, RoutedEventArgs e)
        {
            modszer = 0;
            modszertext = rb1.Content.ToString();
            Tovabbgomb.IsEnabled = true;
        }

        private void rb2_Checked(object sender, RoutedEventArgs e)
        {
            modszer = 1;
            modszertext = rb2.Content.ToString();
            Tovabbgomb.IsEnabled = true;
        }

        private void rb3_Checked(object sender, RoutedEventArgs e)
        {
            modszer = 2;
            modszertext = rb3.Content.ToString();
            Tovabbgomb.IsEnabled = true;
        }

        private void rb4_Checked(object sender, RoutedEventArgs e)
        {
            modszer = 3;
            modszertext = rb4.Content.ToString();
            Tovabbgomb.IsEnabled = true;
        }

        public void angolnyelv()
        {
            if (Main.IsLoaded == true)
            {
                Main.Title = "Differential Equation Solver";
                kilepes2.Content = "Quit";
                Tovabbgomb.Content = "Next";
                groupBox1.Header = "Solver Method";
                groupBox2.Header = "Special Characters";
                groupBox3.Header = "Equation";
                nyelv_lbl.Content = "Language:";
                Értékek_gb.Header = "Values";
                lblX_min.Content = "Start Time:";
                lblX_max.Content = "End Time:";
                lblStep.Content = "Interval:";
                lblY_axis.Content = "Y axis minimum:";
            }
        }

        private void magyarnyelv()
        {
            if (Main.IsLoaded == true)
            {
                Main.Title = "Differenciál Egyenlet Megoldó";
                kilepes2.Content = "Kilépés";
                Tovabbgomb.Content = "Tovább";
                groupBox1.Header = "Megoldási módszer";
                groupBox2.Header = "Különleges karakterek";
                groupBox3.Header = "Egyenlet";
                nyelv_lbl.Content = "Nyelv:";
                Értékek_gb.Header = "Értékek";
                lblX_min.Content = "Kezdőidőpont:";
                lblX_max.Content = "Végidőpont:";
                lblStep.Content = "Lépés:";
                lblY_axis.Content = "Y tengely minimum:";
            }
        }


        private void nemetnyelv()
        {
            if (Main.IsLoaded == true)
            {
                Main.Title = "Differentialgleichungslöser";
                kilepes2.Content = "Austritt";
                Tovabbgomb.Content = "Weiter";
                groupBox1.Header = "Gleichungstyp";
                groupBox2.Header = "Spezifische Charaktere";
                groupBox3.Header = "Gleichung";
                nyelv_lbl.Content = "Sprache:";
                Értékek_gb.Header = "Werte";
                lblX_min.Content = "Startzeit:";
                lblX_max.Content = "Endzeit:";
                lblStep.Content = "Intervall:";
                lblY_axis.Content = "Y Welle minimum:";
            }
        }


        private void nyelvvalasztas_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            switch (nyelvvalasztas.SelectedIndex.ToString())
            {
                case "0":
                    magyarnyelv();
                    break;
                case "1":
                    angolnyelv();
                    break;
                case "2":
                    nemetnyelv();
                    break;
            }
        }

        private void szamlalo1_TextChanged(object sender, TextChangedEventArgs e)
        {
            szamlalo2.Focus();
        }


        private void step_TextChanged(object sender, TextChangedEventArgs e)
        {
            step.CaretIndex = step.Text.Length;

            if ((step.Text != "") && (step.Text != "0") && (step.Text != "0,") && (step.Text != "0,0"))
            {
                try
                {
                    Stepscrollbar.Value = Convert.ToDouble(step.Text);

                    if (Convert.ToDouble(step.Text) > (X_max_scrollbar.Value - X_min_scrollbar.Value))
                    {
                        Stepscrollbar.Value = (X_max_scrollbar.Value - X_min_scrollbar.Value);
                        step.Text = Stepscrollbar.Value.ToString();
                    }
                    if (Convert.ToDouble(step.Text) < 0.1)
                    {
                        Stepscrollbar.Value = 0.1;
                        step.Text = Stepscrollbar.Value.ToString();

                    }


                }
                catch
                {
                    if
                    (Main.IsLoaded == true)
                    {


                        step.Text = "0,1";

                        Stepscrollbar.Value = Convert.ToDouble(step.Text);


                    }
                }

            }
        }


        private void step_LostFocus(object sender, RoutedEventArgs e)
        {
            if ((step.Text == "0") || (step.Text == "0,0") || (step.Text == "") || (step.Text[step.Text.Length - 1] == ','))
            {
                if (step.Text == "")
                {
                    Stepscrollbar.Value = 0.1;
                    step.Text = "0,1";
                }
                else
                {
                    if ((step.Text[step.Text.Length - 1] == ',') && (step.Text[0] != '0'))
                    {
                        Stepscrollbar.Value = Convert.ToDouble(step.Text.Substring(0, step.Text.Length - 1));
                        step.Text = step.Text.Substring(0, step.Text.Length - 1);
                    }
                    else
                    {
                        Stepscrollbar.Value = 0.1;
                        step.Text = "0,1";
                    }
                }
            }
            else
            {
                Stepscrollbar.Value = Convert.ToDouble(step.Text);
            }

        }

        private void Y_scrollbar_ValueChanged(object sender, RoutedPropertyChangedEventArgs<double> e)
        {
            Y_tb.Text = Y_scrollbar.Value.ToString();
            Y_tb.Focus();
        }

        private void Y_tb_TextChanged(object sender, TextChangedEventArgs e)
        {
            Y_tb.CaretIndex = Y_tb.Text.Length;

            try
            {
                if (Y_tb.Text != "")
                {
                    Convert.ToInt16(Y_tb.Text);
                    Y_scrollbar.Value = Convert.ToDouble(Y_tb.Text);
                }
            }
            catch
            {
                Y_scrollbar.Value = 0;
                Y_tb.Text = Y_scrollbar.Value.ToString();
            }

        }

        private void Y_tb_LostFocus(object sender, RoutedEventArgs e)
        {
            if (Y_tb.Text == "")
            {
                Y_scrollbar.Value = 0;
                Y_tb.Text = "0";
            }
            else
            {
                Y_scrollbar.Value = Convert.ToDouble(Y_tb.Text);
            }
        }

        private void X_max_scrollbar_ValueChanged(object sender, RoutedPropertyChangedEventArgs<double> e)
        {
            if (Main.IsLoaded == true)
            {
                if (X_max_scrollbar.Value > X_min_scrollbar.Value)
                {
                    X_max.Text = X_max_scrollbar.Value.ToString();
                }
                else
                {
                    X_max_scrollbar.Value = X_min_scrollbar.Value + 1;
                    X_max.Text = X_max_scrollbar.Value.ToString();
                }
                modifystep();
                X_max.Focus();
            }
        }


        private void X_min_scrollbar_ValueChanged(object sender, RoutedPropertyChangedEventArgs<double> e)
        {

            if (X_min_scrollbar.Value < X_max_scrollbar.Value)
            {
                X_min.Text = X_min_scrollbar.Value.ToString();
            }
            else
            {
                X_min_scrollbar.Value = X_max_scrollbar.Value - 1;
                X_min.Text = X_min_scrollbar.Value.ToString();
            }
            modifystep();
            X_min.Focus();
        }

        private void X_min_LostFocus(object sender, RoutedEventArgs e)
        {
            if (X_min.Text == "")
            {
                X_min_scrollbar.Value = 0;
                X_min.Text = "0";
            }
            else if (Convert.ToDouble(X_max.Text) < (Convert.ToDouble(X_min.Text)))
            {
                X_min_scrollbar.Value = (X_max_scrollbar.Value - 1);
                X_min.Text = X_min_scrollbar.Value.ToString();
            }
            else
            {
                X_min_scrollbar.Value = Convert.ToDouble(X_min.Text);
            }
        }

        private void X_max_LostFocus(object sender, RoutedEventArgs e)
        {
            if (X_max.Text == "")
            {
                X_max_scrollbar.Value = 0;
                X_max.Text = "0";
            }
            else if (Convert.ToDouble(X_max.Text) < (Convert.ToDouble(X_min.Text)))
            {
                X_max_scrollbar.Value = (X_min_scrollbar.Value + 1);
                X_max.Text = X_max_scrollbar.Value.ToString();
            }
            else
            {
                X_max_scrollbar.Value = Convert.ToDouble(X_max.Text);

            }
        }

        private void Stepscrollbar_ValueChanged(object sender, RoutedPropertyChangedEventArgs<double> e)
        {

            step.Text = Stepscrollbar.Value.ToString();
            step.Focus();
        }

        private void X_min_TextChanged(object sender, TextChangedEventArgs e)
        {
            X_min.CaretIndex = X_min.Text.Length;

            try
            {
                X_min_scrollbar.Value = Convert.ToDouble(X_min.Text);
            }
            catch
            {
                if
                (Main.IsLoaded == true)
                {


                    X_min.Text = (X_max_scrollbar.Value - 1).ToString();

                }


            }
        }

        private void X_max_TextChanged(object sender, TextChangedEventArgs e)
        {
            X_max.CaretIndex = X_max.Text.Length;

            try
            {
                Convert.ToDouble(X_max.Text);

            }
            catch
            {
                if
                (Main.IsLoaded == true)
                {
                    X_max.Text = (X_min_scrollbar.Value + 1).ToString();
                }


            }
        }

        private void Tovabbgomb_Click(object sender, RoutedEventArgs e)
        {
            
            string uzenet="";
            if (!inputell())
            {
                MessageBox.Show("Hiányos adatbevitel!");
            }
            else
            {
                uzenet = string.Format("{0}: {1}{2}{2}{3} {4}{2}{5} {6}{2}{2}{7} {8}{2}{2}{9} {10}{2}{2}{11}: {12}",groupBox3.Header.ToString(), szamlalo1.Text + "=" + szamlalo2.Text, Environment.NewLine,lblX_min.Content.ToString(), X_min_scrollbar.Value.ToString(), lblX_max.Content.ToString(),X_max_scrollbar.Value.ToString(),lblStep.Content.ToString(),Stepscrollbar.Value.ToString(),lblY_axis.Content.ToString(),Y_scrollbar.Value.ToString(),groupBox1.Header.ToString(),megoldo(modszer));

                MessageBoxResult valasz = MessageBox.Show(uzenet, Main.Title, MessageBoxButton.OKCancel, MessageBoxImage.Information);
                if (valasz == MessageBoxResult.OK)
                {
                    //Készítette Cs J [Math team] 05.23
                    //mindenképp példányosítani kell különben null lenne
                    //argumentumok sorrendben: (double)start, (double)end, (double)start y, (double) lépés, füügvény, (int) módszer
                    App.myMath = new MyMath((float)X_min_scrollbar.Value, (float)X_max_scrollbar.Value, (float)Y_scrollbar.Value, (float)Stepscrollbar.Value, testfv, (int)modszer);
                    //outputra navigálás
                    OutputWindow outputWindow = new OutputWindow();
                    outputWindow.Show();
                }
            }
        }

        //Készítette Cs J [Math team] 05.23
        private float testfv(float t, float y)
        {
            //  return -y + t + 1;
            return -0.07f * (t - y);
            //             return -k * (t - TR);  ezt kéne valahogy betenni newton cooling az output ból azt mondta kéri, outputba van jelmagyarázat
        }

        private string megoldo(int modszer)
        {
            string text="";
            switch (modszer)
            {
                case 0:
                    text = "Euler";
                    break;
                case 1:
                    text = "Explicit Runge-Kutta";
                    break;
                case 2:
                    text = "Adaptiv Runge-Kutta";
                    break;
                case 3:
                    text = "Implicit Euler";
                    break;
            }
            return text;
        }
        private Boolean inputell()
        {
            Boolean ok;
            if ((szamlalo1.Text != "") && (szamlalo2.Text != ""))
            {
                ok = true;
            }
            else ok = false;

            return ok;
        }
       
    }
}
