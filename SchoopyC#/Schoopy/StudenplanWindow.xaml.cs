using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Controls.Primitives;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;
using Schoopy.Data;

namespace Schoopy
{
    /// <summary>
    /// Interaction logic for StudenplanWindow.xaml
    /// </summary>
    public partial class StudenplanWindow : Window
    {
        Connect c = new Connect();
        string tName;
        public StudenplanWindow( string teachername)
        {
            InitializeComponent();
            initStdplan();
            tName = teachername;


        }

        private void button_Click(object sender, RoutedEventArgs e)
        {
            MessageBox.Show(dataGrid.SelectedCells.Count.ToString());
        }

        private void initStdplan()
        {

            string json = c.Get(@"http://localhost:8080/WebServiceSchoopy/webresources/lessons/teachers/"+ tName);
            Console.WriteLine(c.Get(@"http://localhost:8080/WebServiceSchoopy/webresources/lessons/114b"));

            List<Lesson> deserializedProduct = JsonConvert.DeserializeObject<List<Lesson>>(json);

            col1.Binding = new Binding("Montag");
            col2.Binding = new Binding("Dienstag");
            col3.Binding = new Binding("Mittwoch");
            col4.Binding = new Binding("Donnerstag");
            col5.Binding = new Binding("Freitag");

           

         

            for (int i = 0; i < deserializedProduct.Count; i++)
            {
                Stundenplan curSP = new Stundenplan();
                Lesson l = deserializedProduct[i];
              
                if (l.schoolHour == i + 1)
                {
                    switch (l.weekday)
                    {
                        case Weekday.MONDAY:
                            curSP.Montag = l;
                            break;
                        case Weekday.TUESDAY:
                            curSP.Dienstag = l;
                            break;
                        case Weekday.WEDNESDAY:
                            curSP.Mittwoch = l;
                            break;
                        case Weekday.THURSDAY:
                            curSP.Donnerstag = l;
                            break;
                        case Weekday.FRIDAY:
                            curSP.Freitag = l;
                            break;
                        default:
                            break;
                    }
                }
                dataGrid.Items.Add(curSP);
            }

         

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






     



       

        private void button_Click_1(object sender, RoutedEventArgs e)
        {
            try
            {


                Stundenplan sp = (Stundenplan)dataGrid.SelectedCells[0].Item;


                string s = dataGrid.SelectedCells[0].Column.Header.ToString();
                switch (s)
                {
                    case "Montag":

                        LessonDetail win2 = new LessonDetail(sp.Montag);
                        win2.Show();
                        break;
                    case "Dienstag":
                        LessonDetail win3 = new LessonDetail(sp.Dienstag);
                        win3.Show();
                        break;
                    case "Mittwoch":
                        LessonDetail win4 = new LessonDetail(sp.Mittwoch);
                        win4.Show();
                        break;
                    case "Donnerstag":
                        LessonDetail win5 = new LessonDetail(sp.Donnerstag);
                        win5.Show();
                        break;
                    case "Freitag":
                        LessonDetail win6 = new LessonDetail(sp.Freitag);
                        win6.Show();
                        break;
                }

            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
    }
}
