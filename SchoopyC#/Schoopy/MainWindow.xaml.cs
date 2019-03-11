using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace Schoopy
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
       
    {
        private static readonly HttpClient client = new HttpClient();
        public MainWindow()
        {
            InitializeComponent();
        }

        private async void buttonLogin_Click(object sender, RoutedEventArgs e)
        {

            //StudenplanWindow win2 = new StudenplanWindow("Admin1234");

            //   win2.Show();
            Teacher t = new Teacher();
            t.username = textBoxUsername.Text;
            t.password = passwordBox.Password.ToString();
            var jsonString = Newtonsoft.Json.JsonConvert.SerializeObject(t);


            var response = await client.PostAsync(@"http://192.168.195.165:8080/WebServiceSchoopy/webresources/teachers/login", new StringContent(jsonString, Encoding.UTF8, "application/json"));
            var responseString = await response.Content.ReadAsStringAsync();


            try
            {
                Teacher t2 = JsonConvert.DeserializeObject<Teacher>(responseString);
                StudenplanWindow win2 = new StudenplanWindow(textBoxUsername.Text);

                win2.Show();
                this.Close();
            }
            catch (Exception ex)
            {
                labelStatus.Content = "Wrong Login data";
                MessageBox.Show(ex.Data.ToString());
            }


        }
    }
}
