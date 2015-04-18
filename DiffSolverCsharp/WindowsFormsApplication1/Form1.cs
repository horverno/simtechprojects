using System;
using Mathematics;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Windows.Forms.Integration;

namespace WindowsFormsApplication1
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
            //WPF (output) ablak meghívása így történik

            /*
            var wpfwindows = new Output_plot.WindowsPlot();
            ElementHost.EnableModelessKeyboardInterop(wpfwindows);
            wpfwindows.Show();
           */
            MyMath test = new MyMath( 10, 20,1, .1f, f1);
            test.runge(f1);
            label1.Text = "Done!";
        }

        public static double f1(double t, double y)
        {
            return -y + t + 1;
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            
        }
    }
}
