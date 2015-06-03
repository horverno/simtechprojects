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
        public string fuggveny = "";
        public string aktegyenlet = "";
        public string aktvaltozo = "";
        public Boolean newton = false;
        public char valtozo = '\0';
        public MainWindow()
        {
            InitializeComponent();
            modifystep();
        }

        public void modifystep()
        {
            Stepscrollbar.Minimum = 0.01;
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
                if ((infocus != szamlalo1) || (infocus == szamlalo1) && (szamlalo1.Text.Length < 2))
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
            szamlalo1.CaretIndex = szamlalo1.Text.Length;
        }

        private void szamlalo2_GotFocus(object sender, RoutedEventArgs e)
        {
            infocus = szamlalo2;
            szamlalo2.CaretIndex = szamlalo2.Text.Length;
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
            string tartalom = szamlalo1.Text;
            int hossz= szamlalo1.Text.Length;


            szamlalo2.Text = "";
            if (tartalom != "")
            {
                for (int i = 0; i < tartalom.Length; i++)
                {
                    if (char.IsLetter(tartalom[0]) == true && tartalom[0]!='\u1e8c')
                    {

                        if (tartalom[0] == '\u1e8c' || tartalom[0] == '\u1e8a')
                        {
                            if (tartalom.Length > 1)
                            {
                                szamlalo1.Text = aktvaltozo;
                                szamlalo1.CaretIndex = aktvaltozo.Length;
                                break;
                            }
                            newton = true;
                            szamlalo2.Focus();
                        }
                        else
                        {
                            if ((tartalom.Length>1)&&(i!=0)){
                                if (tartalom[i] != '\'')
                                {
                                    szamlalo1.Text = aktvaltozo;
                                    szamlalo1.CaretIndex = aktvaltozo.Length;
                                    break;
                                }
                            }
                            newton = false;
                        }
                        
                    }
                    else{
                        szamlalo1.Text=aktvaltozo;
                        szamlalo1.CaretIndex = aktvaltozo.Length;
                        break;
                    }
                }
                if (szamlalo1.Text.Length == 2)
                {
                    szamlalo2.Focus();
                }
            }
            aktvaltozo = szamlalo1.Text;
           
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
                    if (Convert.ToDouble(step.Text) < 0.01)
                    {
                        Stepscrollbar.Value = 0.01;
                        step.Text = Stepscrollbar.Value.ToString();

                    }


                }
                catch
                {
                    if
                    (Main.IsLoaded == true)
                    {


                        step.Text = "0,01";

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
                    Stepscrollbar.Value = 0.01;
                    step.Text = "0,01";
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
                        Stepscrollbar.Value = 0.01;
                        step.Text = "0,01";
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
            if (vegsoellenorzes(szamlalo1.Text,szamlalo2.Text))
            {
               // uzenet = string.Format("{0}: {1}{2}{2}{3} {4}{2}{5} {6}{2}{2}{7} {8}{2}{2}{9} {10}{2}{2}{11}: {12}",groupBox3.Header.ToString(), szamlalo1.Text + "=" + szamlalo2.Text, Environment.NewLine,lblX_min.Content.ToString(), X_min_scrollbar.Value.ToString(), lblX_max.Content.ToString(),X_max_scrollbar.Value.ToString(),lblStep.Content.ToString(),Stepscrollbar.Value.ToString(),lblY_axis.Content.ToString(),Y_scrollbar.Value.ToString(),groupBox1.Header.ToString(),megoldo(modszer));

                MessageBoxResult valasz = MessageBox.Show(uzenet, Main.Title, MessageBoxButton.OKCancel, MessageBoxImage.Information);
                if (valasz == MessageBoxResult.OK)
                {
                    //tesztfv(20,t);
                    //MessageBox.Show(Evaluate(szamlalo2.Text).ToString());
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
        private float tesztfv(float t, float y)
        {

            //  return -y + t + 1;
            return (float)0.07*(20-t);
            //             return -k * (t - TR);  ezt kéne valahogy betenni newton cooling az output ból azt mondta kéri, outputba van jelmagyarázat
        }

        private float testfv(float t, float y)
        {
            string fuggveny = szamlalo2.Text;
            string baloldal = szamlalo1.Text;
            int hossz = fuggveny.Length;
            string[] adatok=new string[20];
            string szam="";
            int aktual=0;
            int i,eddig;
            double elsoszam =0.0;
            string zarojel="";
            char elsovaltozo = '\0';
            string muvelet="";
            bool valtozonegativ = false;
            string zarojelenbeluliszam = "";
            double zarojelenbelul = 0.0;
            bool zarojelenbelulnegativ = false;
            double masodikszam = 0.0;
            double harmadikszam = 0.0;
            string masodikmuvelet = "";
            string harmadikmuvelet = "";
            i = 0;
            

            while(i<hossz)
            {
                szam = "";
                eddig=0;
                if(char.IsLetter(fuggveny[i])==true){
                    adatok[aktual] = fuggveny.Substring(i, 1);
                    elsovaltozo = adatok[aktual][0];
                    aktual++;
                }
                else if ((char.IsDigit(fuggveny[i]) == true))
                {
                    eddig=i;
                    while ((eddig < hossz) && (char.IsDigit(fuggveny[eddig]) == true || fuggveny[eddig] == ','))
                    {
                        eddig++;
                    }
                   // i = eddig;
                    for (int tol = i; tol <= eddig-1; tol++)
                    {

                        szam = szam + fuggveny.Substring(tol, 1);
                    }
                    adatok[aktual] = szam;
                    aktual++;
                    i = eddig-1;
                }
                else if (fuggveny[i] == '*' || fuggveny[i] == '/')
                {
                    adatok[aktual] = fuggveny.Substring(i, 1);
                    aktual++;
                }
                else if (fuggveny[i] == '+' || fuggveny[i] == '-')
                {
                    adatok[aktual] = fuggveny.Substring(i, 1);
                    aktual++;
                }
                else if (fuggveny[i] == '(')
                {
                    eddig=i;
                    while ((eddig < hossz) && fuggveny[eddig] != ')'){
                        eddig++;
                    }
                    for (int tol = i; tol <= eddig; tol++)
                    {

                        zarojel = zarojel + fuggveny.Substring(tol, 1);
                    }
                    adatok[aktual] = zarojel;
                    aktual++;
                    i = eddig - 1;
                }
                i++;

            }
            
            i=0;
            while (adatok[i] != null)
            {
                try
                {
                    elsoszam = Convert.ToDouble(adatok[i]);
                }
                catch
                {
                    if (adatok[i].Substring(0, 1) == "(")
                    {
                        zarojel = adatok[i];

                    }
                    else if ((adatok[i].Substring(0,1)=="+")||(adatok[i].Substring(0,1)=="-")||(adatok[i].Substring(0,1)=="/")||(adatok[i].Substring(0,1)=="*")){
                        muvelet=adatok[i].Substring(0,1);
                    }
                }
                i++;
            }
            for (i = 0; i < zarojel.Length; i++)
            {
                if(char.IsLetter(zarojel[i])==true){
                    if (zarojel[i - 1] == '-')
                    {
                        valtozonegativ = true;
                    }
                }
                if (char.IsDigit(zarojel[i]) == true)
                {
                    if (zarojel[i - 1] == '-')
                    {
                        zarojelenbelulnegativ = true;
                    }
                    while (char.IsDigit(zarojel[i]) == true)
                    {
                        zarojelenbeluliszam = zarojelenbeluliszam + zarojel.Substring(i, 1);
                        i++;
                    }
                    zarojelenbelul = Convert.ToDouble(zarojelenbeluliszam);
                    if (zarojelenbelulnegativ == true)
                    {
                        zarojelenbelul=zarojelenbelul * (-1);
                    }
                }

            }

            if (muvelet == "/")
            {
                if (valtozonegativ==true){
                   return (float)elsoszam / (-t+(float)zarojelenbelul);
                }
                else return (float)elsoszam / (t + (float)zarojelenbelul);
            }
            else if (muvelet == "*")
            {
                if (valtozonegativ == true)
                {
                    return (float)elsoszam * (-t + (float)zarojelenbelul);
                }
                else return (float)elsoszam * (t + (float)zarojelenbelul);
            }
            else if (muvelet == "+")
            {
                if (valtozonegativ == true)
                {
                    return (float)elsoszam + (-t + (float)zarojelenbelul);
                }
                else return (float)elsoszam + (t + (float)zarojelenbelul);
            }
            else //(muvelet == "-")
            {
                if (valtozonegativ == true)
                {
                    return (float)elsoszam - (-t + (float)zarojelenbelul);
                }
                else return (float)elsoszam - (t + (float)zarojelenbelul);
            }
           
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
        

        public bool duplakarakterell(string input)
        {
            int hossz;
            hossz = input.Length;
            bool hibas=false;
            
            for (int i = 0; i < hossz; i++)
                {
                    if (input[i] == '\u1e8c')
                    {
                        hibas = true;
                    }
                }

            if (hossz > 1)
            {
                for (int i = 0; i < hossz - 1; i++)
                {
                    if (input.Substring(i, 2)[0] == '+' || input.Substring(i, 2)[0] == '-' || input.Substring(i, 2)[0] == '*' || input.Substring(i, 2)[0] == '/')
                    {
                     

                        if ((input.Substring(i, 2)[1] == '+' || input.Substring(i, 2)[1] == '-' || input.Substring(i, 2)[1] == '*' || input.Substring(i, 2)[1] == '/' || input.Substring(i, 2)[1] == '\'' || input.Substring(i, 2)[1] == ',' || input.Substring(i, 2)[1] == ')' ))
                        {
                            hibas = true;
                            
                            break;
                        }
                    }
                    else if (input.Substring(i, 2)[0] == '(' )
                    {
                         if ((input.Substring(i, 2)[1] == '+' || input.Substring(i, 2)[1] == '*' || input.Substring(i, 2)[1] == '/' || input.Substring(i, 2)[1] == '\'' || input.Substring(i, 2)[1] == ',' || input.Substring(i, 2)[1] == '(' || input.Substring(i, 2)[1] == ')'))
                        {
                            hibas = true;
                            
                            break;
                        }
                    }
                    else if (input.Substring(i, 2)[0] == ')')
                    {
                        
                        if ((input.Substring(i, 2)[1] == '\'' || input.Substring(i, 2)[1] == ',' || input.Substring(i, 2)[1] == '('))
                        {
                            hibas = true;
                            
                            break;
                        }
                    }
                    else if (input.Substring(i, 2)[0] == ',')
                    {
                        if(char.IsDigit(input.Substring(i, 2)[1])!=true){

                             hibas = true;
                             break;
                        }
                    }
                    else if (input.Substring(i, 2)[0] == '\'')
                    {

                        if ((input.Substring(i, 2)[1] != '/') && (input.Substring(i, 2)[1] != '*') && (input.Substring(i, 2)[1] != '+') && (input.Substring(i, 2)[1] != '-'))
                        {
                            hibas = true;
                            break;
                        }

                    }


                    else if (char.IsLetter(input.Substring(i, 2)[0]) == true)
                    {

                        if (szamlalo1.Text.Length == 2)
                        {
                            if ((input.Substring(i, 2)[1] != '/') && (input.Substring(i, 2)[1] != '*') && (input.Substring(i, 2)[1] != '+') && (input.Substring(i, 2)[1] != '-') && (input.Substring(i, 2)[1] != ')'))
                            {
                                hibas = true;
                                break;
                            }

                        }
                        else
                        {
                            if ((input.Substring(i, 2)[1] != '\'') && (input.Substring(i, 2)[1] != '/') && (input.Substring(i, 2)[1] != '*') && (input.Substring(i, 2)[1] != '+') && (input.Substring(i, 2)[1] != '-') && (input.Substring(i, 2)[1] != ')'))
                            {
                                hibas = true;
                                break;
                            }

                        }
                    }
                    else if (char.IsDigit(input.Substring(i, 2)[0]) == true)
                    {
                        if ((input.Substring(i, 2)[1] != '/') && (input.Substring(i, 2)[1] != ',') && (input.Substring(i, 2)[1] != ')') && (input.Substring(i, 2)[1] != '*') && (input.Substring(i, 2)[1] != '+') && (input.Substring(i, 2)[1] != '-') && char.IsDigit(input.Substring(i, 2)[1]) != true)
                        {
                            hibas = true;
                            break;
                        }
                    }

                }
                return hibas;
            }
            else return hibas;
        }
        public bool vegsoellenorzes(string baloldal,string jobboldal)
        {
            bool ok = true;
            int hossz = jobboldal.Length;
            int zarojelek = 0;
            int valtozodb = 0;
            if ((szamlalo1.Text == "") || (szamlalo2.Text == ""))
            {
                ok = false;
                MessageBox.Show("Hiányos adatbevitel!");
            }
            else
            {
                
                for (int i = 0; i < hossz; i++)
                {
                    if (jobboldal[i] == '(')
                    {
                        zarojelek++;
                    }
                    if (jobboldal[i] == ')')
                    {
                        zarojelek--;
                    }
                    if (newton == true)
                    {
                        if ((jobboldal[i] == 'X') )
                        {
                            valtozodb++;
                        }
                      
                    }
                    else
                    {
                        if (jobboldal[i] == valtozo)
                        {
                            valtozodb++;
                        }
                    }

                }
                if (newton == false)
                {
                    if (baloldal.Length == 1)
                    {
                        ok = false;
                        MessageBox.Show("Az egyenlet bal oldala nem tartalmaz deriváltat!");
                    }
                }
                if (zarojelek != 0)
                {
                    ok = false;
                    MessageBox.Show("Beviteli hiba! Zárójel nincs bezárva!");
                }
                if (valtozodb == 0)
                {
                    ok = false;
                    MessageBox.Show("Beviteli hiba! A derivált változó nem szerepel az egyenlet jobbb oldalában!");
                }
            }
            return ok;

        }

        private void szamlalo2_TextChanged(object sender, TextChangedEventArgs e)
        {
            string baloldal = szamlalo1.Text;
            
            string egyenlet = szamlalo2.Text;
            //bool valtozo1=false;
            int nyitozarojelek = 0;
            
            int szamok=0;
            int vesszok = 0;
            if (newton == true)
            {
                valtozo = 'X';
            }
            if (szamlalo1.Text != "")
            {
                if (newton == false)
                {
                    valtozo = baloldal[0];
                }
                if (egyenlet != "")
                {
                    if((egyenlet[0]=='\'')||(egyenlet[0]=='*')||(egyenlet[0]=='/')||(egyenlet[0]=='+')||(egyenlet[0]==','))
                    {
                                    szamlalo2.Text = aktegyenlet;
                                    szamlalo2.CaretIndex = aktegyenlet.Length;
                    }
                    else{

                        for (int i = 0; i < egyenlet.Length; i++)
                        {

                            if ((char.IsLetterOrDigit(egyenlet[i]) == true) || egyenlet[i] == '+' || egyenlet[i] == '-' || egyenlet[i] == '/' || egyenlet[i] == '*' || egyenlet[i] == ',' || egyenlet[i] == '(' || egyenlet[i] == ')' || egyenlet[i] == '\'')
                            {
                             if ((newton == false) &&(char.IsLetter(egyenlet[i]) == true)&&(egyenlet[i]!=valtozo))
                                {                              
                                    szamlalo2.Text = aktegyenlet;
                                    szamlalo2.CaretIndex = aktegyenlet.Length;
                                    break;
                                }
                                if (newton == true && egyenlet[i] != 'X')
                                {
                                    szamlalo2.Text = aktegyenlet;
                                    szamlalo2.CaretIndex = aktegyenlet.Length;
                                    break;
                                }
                            
                            if (duplakarakterell(egyenlet) == true)
                            {
                                szamlalo2.Text = aktegyenlet;
                                szamlalo2.CaretIndex = aktegyenlet.Length;
                                break;
                            }
                            if (char.IsDigit(egyenlet[i]) == true)
                            {
                                szamok++;
                                if (szamok > 4)
                                {
                                    szamlalo2.Text = aktegyenlet;
                                    szamlalo2.CaretIndex = aktegyenlet.Length;
                                    break;
                                }
                            }

                            if (egyenlet[i] == ',')
                            {
                                vesszok++;
                                if (vesszok > 1)
                                {
                                    szamlalo2.Text = aktegyenlet;
                                    szamlalo2.CaretIndex = aktegyenlet.Length;
                                    break;
                                }
                            }
                            if (egyenlet[i] == '(')
                            {
                                nyitozarojelek++;
                                if (nyitozarojelek > 1)
                                {
                                    szamlalo2.Text = aktegyenlet;
                                    szamlalo2.CaretIndex = aktegyenlet.Length;
                                    break;
                                }
                            }

                            if (egyenlet[i] == ')')
                            {
                                if (nyitozarojelek == 0)
                                {
                                    szamlalo2.Text = aktegyenlet;
                                    szamlalo2.CaretIndex = aktegyenlet.Length;
                                    break;
                                }
                                else
                                {
                                    nyitozarojelek--;
                                }
                            }

                            if (char.IsDigit(egyenlet[i]) == false)
                            {

                                szamok=0;
                                if (egyenlet[i] != ',')
                                {
                                    vesszok = 0;
                                }
                            }
                            
                            
                        }
                        else
                        {
                            szamlalo2.Text = aktegyenlet;
                            szamlalo2.CaretIndex = aktegyenlet.Length;
                            break;

                        }
                    }

                    }

                }

                aktegyenlet = szamlalo2.Text;
                szamlalo2.CaretIndex = aktegyenlet.Length;

            }
            else
            {
                szamlalo2.Text = "";
            }
        }
        
}
       
    }

