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

namespace Schoopy
{
    /// <summary>
    /// Interaction logic for StudenplanWindow.xaml
    /// </summary>
    public partial class StudenplanWindow : Window
    {
        public StudenplanWindow()
        {
            InitializeComponent();
            initStdplan();
        }

        private void button_Click(object sender, RoutedEventArgs e)
        {

        }

        private void initStdplan()
        {

            this.listView.Items.Add(new Stunde { Montag = "Deutsch", Dienstag = "Deutsch",Mittwoch="POS", Donnerstag="POS" ,Freitag ="BSD" });

            this.listView.Items.Add(new Stunde { Montag = "Deutsch", Dienstag = "Deutsch", Mittwoch = "POS", Donnerstag = "POS", Freitag = "BSD" });

            this.listView.Items.Add(new Stunde { Montag = "Deutsch", Dienstag = "Deutsch", Mittwoch = "POS", Donnerstag = "POS", Freitag = "BSD" });

            this.listView.Items.Add(new Stunde { Montag = "Deutsch", Dienstag = "Deutsch", Mittwoch = "POS", Donnerstag = "POS", Freitag = "BSD" });

            this.listView.Items.Add(new Stunde { Montag = "Deutsch", Dienstag = "Deutsch", Mittwoch = "POS", Donnerstag = "POS", Freitag = "BSD" });

            this.listView.Items.Add(new Stunde { Montag = "Deutsch", Dienstag = "Deutsch", Mittwoch = "POS", Donnerstag = "POS", Freitag = "BSD" });

            this.listView.Items.Add(new Stunde { Montag = "Deutsch", Dienstag = "Deutsch", Mittwoch = "POS", Donnerstag = "POS", Freitag = "BSD" });

            this.listView.Items.Add(new Stunde { Montag = "Deutsch", Dienstag = "Deutsch", Mittwoch = "POS", Donnerstag = "POS", Freitag = "BSD" });

            this.listView.Items.Add(new Stunde { Montag = "Deutsch", Dienstag = "Deutsch", Mittwoch = "POS", Donnerstag = "POS", Freitag = "BSD" });


        }

        private void MenuItem_Click(object sender, RoutedEventArgs e)
        {

        }

        private void MenuItem_Click_1(object sender, RoutedEventArgs e)
        {
            
        }

        private void MenuItem_Click_2(object sender, RoutedEventArgs e)
        {

        }

        private void listView_MouseDoubleClick(object sender, MouseButtonEventArgs e)
        {
            Console.WriteLine(sender.ToString());
        }
    
    }
}
