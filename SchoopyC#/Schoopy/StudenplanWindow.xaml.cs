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
            Database d = new Database();
            d.start();

        }

        private void button_Click(object sender, RoutedEventArgs e)
        {
            MessageBox.Show(dataGrid.SelectedCells.Count.ToString());
        }

        private void initStdplan()
        {
          
            col1.Binding = new Binding("Montag");
            col2.Binding = new Binding("Dienstag");
            col3.Binding = new Binding("Mittwoch");
            col4.Binding = new Binding("Donnerstag");
            col5.Binding = new Binding("Freitag");

            Stunde s = new Stunde("mon", "1", "hui");
            Stunde s2 = new Stunde("di", "2", "tui");
            Stunde s3 = new Stunde("mit", "3", "aa1");
            Stunde s4 = new Stunde("don", "4", "aaa");
            Stunde s5 = new Stunde("frei", "5", "444");

            Stundenplan st = new Stundenplan { Montag = s, Dienstag = s2, Mittwoch = s3, Donnerstag = s4, Freitag = s };
            Stundenplan st2 = new Stundenplan { Montag = s2, Dienstag = s, Mittwoch = s3, Donnerstag = s5, Freitag = s4 };
            //Stunde data = new Stunde { Montag = "Harry Potter", Dienstag = "J. K. Rollings", Mittwoch = "Mistery", Donnerstag = "H-I", Freitag = " 3 " };
            // Stunde data2 = new Stunde { Montag = "Deutsch", Dienstag = "Mathe", Mittwoch = "Pos", Donnerstag = "Deutsch", Freitag = " E" };
            //dataGrid.Items.Add(data);
            dataGrid.Items.Add(st);
            dataGrid.Items.Add(st2);


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


    

     /*   private void dataGrid_SelectedCellsChanged_1(object sender, SelectedCellsChangedEventArgs e)
        {

            
            try
            {


                Console.WriteLine();
            }catch(Exception ex)
            {
                Console.WriteLine(ex.StackTrace);
            }



        }

        private void dataGrid_PreviewMouseDown(object sender, MouseButtonEventArgs e)
        {

        }

        private void dataGrid_MouseRightButtonDown(object sender, MouseButtonEventArgs e)
        {
            DependencyObject dep = (DependencyObject)e.OriginalSource;

            while ((dep != null) && !(dep is DataGridCell) && !(dep is DataGridColumnHeader))
            {
                dep = VisualTreeHelper.GetParent(dep);
            }

            if (dep == null)
                return;

            if (dep is DataGridColumnHeader)
            {
                DataGridColumnHeader columnHeader = dep as DataGridColumnHeader;

                // find the property that this cell's column is bound to
                string boundPropertyName = FindBoundProperty(columnHeader.Column);

                int columnIndex = columnHeader.Column.DisplayIndex;

                ClickedItemDisplay.Text = string.Format(
                    "Header clicked [{0}] = {1}",
                    columnIndex, boundPropertyName);


            }

            if (dep is DataGridCell)
            {
                DataGridCell cell = dep as DataGridCell;

                // navigate further up the tree
                while ((dep != null) && !(dep is DataGridRow))
                {
                    dep = VisualTreeHelper.GetParent(dep);
                }

                if (dep == null)
                    return;

                DataGridRow row = dep as DataGridRow;

                object value = ExtractBoundValue(row, cell);

                int columnIndex = cell.Column.DisplayIndex;
                int rowIndex = FindRowIndex(row);

                ClickedItemDisplay.Text = string.Format(
                      "Cell clicked [{0}, {1}] = {2}",
                      rowIndex, columnIndex, value.ToString());

            }
        }
        private int FindRowIndex(DataGridRow row)
        {
            DataGrid dataGrid = ItemsControl.ItemsControlFromItemContainer(row) as DataGrid;

            int index = dataGrid.ItemContainerGenerator.IndexFromContainer(row);

            return index;
        }
        private object ExtractBoundValue(DataGridRow row, DataGridCell cell)
        {
            // find the property that this cell's column is bound to
            string boundPropertyName = FindBoundProperty(cell.Column);

            // find the object that is realted to this row
            object data = row.Item;

            // extract the property value
            PropertyDescriptorCollection properties = TypeDescriptor.GetProperties(data);
            PropertyDescriptor property = properties[boundPropertyName];
            object value = property.GetValue(data);

            return value;
        }

      
        private string FindBoundProperty(DataGridColumn col)
        {
            DataGridBoundColumn boundColumn = col as DataGridBoundColumn;

            // find the property that this column is bound to
            Binding binding = boundColumn.Binding as Binding;
            string boundPropertyName = binding.Path.Path;

            return boundPropertyName;
        }*/


        void b1SetColor(object sender, RoutedEventArgs e)
        {
            MessageBox.Show("Clicked" + sender.ToString());
           //  Stunde s = (Stunde) dataGrid.CurrentCell.Column.ToString();

           // MessageBox.Show(dataGrid.CurrentCell.Column.ToString());
           
            MessageBox.Show(dataGrid.SelectedCells.Count.ToString());
            // MessageBox.Show("Clicked" + dataGrid.SelectedCells[0].ToString());

        }

        

        private void buttonSearch_Click(object sender, RoutedEventArgs e)
        {
           
        }

        private void button_Click_1(object sender, RoutedEventArgs e)
        {
         
            Stundenplan sp = (Stundenplan)dataGrid.SelectedCells[0].Item;
          

            string s = dataGrid.SelectedCells[0].Column.Header.ToString();
            switch (s)
            {
                case "Montag":
                    
                    MessageBox.Show(sp.Montag.ToString());
                    break;
                case "Dienstag":
                   
                    MessageBox.Show(sp.Dienstag.ToString());
                    break;
                case "Mittwoch":
                  
                    MessageBox.Show(sp.Mittwoch.ToString());
                    break;
                case "Donnerstag":
                
                    MessageBox.Show(sp.Donnerstag.ToString());
                    break;
                case "Freitag":
                   
                    MessageBox.Show(sp.Freitag.ToString());
                    break;
            }

        }
    }

}
