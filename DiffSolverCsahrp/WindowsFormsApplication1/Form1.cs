using System;
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
        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }
    }
}
