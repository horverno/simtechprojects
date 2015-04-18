using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace Output_plot
{
    /// <summary>
    /// Interaction logic for WindowsPlot.xaml
    /// </summary>
    public partial class WindowsPlot : Window
    {
         private double[] xValues, yValues;
        public WindowsPlot(double[] xValues, double[] yValues)
        {
            InitializeComponent();
            this.xValues = xValues;
            this.yValues = yValues;
            this.Focus();
        }
        public WindowsPlot()
        {
            InitializeComponent();
        }
    }
}
