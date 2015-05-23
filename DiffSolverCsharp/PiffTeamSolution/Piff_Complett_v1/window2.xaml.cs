﻿using System;
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
    
    public partial class Window1 : Window
    {
        double kezdoertek;
        double vegertek;
        double lepeskoz;
        public Window1()
        {
            InitializeComponent();
            kezdoertek = Convert.ToDouble(Kezdo.Content);
            vegertek = Convert.ToDouble(Veg.Content);
            lepeskoz = Convert.ToDouble(Lepes.Content);
        }

        private void Back_Click(object sender, RoutedEventArgs e)
        {
            this.Hide();
            ((MainWindow)this.Owner).Visibility = Visibility.Visible;
        }


        private void Kezdőminusz_Click(object sender, RoutedEventArgs e)
        {
            kezdoertek = Convert.ToDouble(Kezdo.Content);
            if (kezdoertek != 0)
            {
                kezdoertek--;
            }
            Kezdo.Content = Convert.ToString(kezdoertek);
        }

        private void Kezdőplusz_Click(object sender, RoutedEventArgs e)
        {
            lepeskoz = Convert.ToDouble(lepeskoz);
            kezdoertek = Convert.ToDouble(Kezdo.Content);
            if (kezdoertek + 1 < vegertek)
            {
                kezdoertek++;
            }
            
            if ((lepeskoz > vegertek - kezdoertek) && (lepeskoz > 1))
            {
                lepeskoz = vegertek - kezdoertek;
            }
            Kezdo.Content = Convert.ToString(kezdoertek);
            Lepes.Content = Convert.ToString(lepeskoz);
        }

        private void Végplusz_Click(object sender, RoutedEventArgs e)
        {
            vegertek = Convert.ToDouble(Veg.Content);
            vegertek++;
            Veg.Content = Convert.ToString(vegertek);
        }
        private void Végminusz_Click(object sender, RoutedEventArgs e)
        {
            lepeskoz = Convert.ToDouble(lepeskoz);
            vegertek = Convert.ToDouble(Veg.Content);
            if ((lepeskoz == (vegertek-kezdoertek))&&(lepeskoz>1))
            {
                
                lepeskoz--;
            }
 
            if (vegertek > kezdoertek+1)
            {
                vegertek--;
            }
            Veg.Content = Convert.ToString(vegertek);
            Lepes.Content = Convert.ToString(lepeskoz);
        }

        private void Lépésplusz_Click(object sender, RoutedEventArgs e)
        {
            lepeskoz = Convert.ToDouble(lepeskoz);
            if (lepeskoz < vegertek - kezdoertek)
            {
                lepeskoz=lepeskoz+0.5;
            }
            Lepes.Content = Convert.ToString(lepeskoz);
        }

        private void Lépésminusz_Click(object sender, RoutedEventArgs e)
        {
            lepeskoz = Convert.ToDouble(lepeskoz);

                        
            if (lepeskoz > 0.5)
            {
                lepeskoz=lepeskoz-0.5;
            }
            Lepes.Content = Convert.ToString(lepeskoz);
        }

        private void kilepes_Click(object sender, RoutedEventArgs e)
        {
            System.Environment.Exit(0);
        }

        private void Window_Closing(object sender, System.ComponentModel.CancelEventArgs e)
        {
            System.Environment.Exit(0);
        }

        private void Tovabbgomb_Click(object sender, RoutedEventArgs e)
        {
            //Készítette Cs J [Math team] 05.23
            //mindenképp példányosítani kell különben null lenne
            //argumentumok sorrendben: (double)start, (double)end, (double)start y, (double) lépés, füügvény, (int) módszer
            App.myMath = new MyMath(0, 100, 5, 20, testfv, 2);
            //outputra navigálás
            OutputWindow outputWindow = new OutputWindow();
            outputWindow.Show();
        }

        //Készítette Cs J [Math team] 05.23
        private double testfv(double t, double y)
        {
            return -y + t + 1;
        }
        

    }
}
