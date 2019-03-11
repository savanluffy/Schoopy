using Microsoft.Win32;
using Newtonsoft.Json;
using Schoopy.Data;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Windows;
using System.Web.Script.Serialization;
namespace Schoopy
{
    /// <summary>
    /// Interaction logic for Dokument.xaml
    /// </summary>
    public partial class Dokument : Window
    {
        string filePath;
        private static readonly HttpClient client = new HttpClient();
        Connect c = new Connect();
        public Dokument()
        {
            InitializeComponent();
            init();
            initComboBox();
        }

        private void init()
        {
            listBox.Items.Clear();
            string json = (c.Get(@"http://192.168.195.165:8080/WebServiceSchoopy/webresources/publicfiles"));

           List<PublicFile> deserializedPublicFile = new JavaScriptSerializer().Deserialize<List<PublicFile>>(json);
            //var Data = serializer.Deserialize<List<UserData>>(json);
            //List<PublicFile> deserializedPublicFile = JsonConvert.DeserializeObject<List<PublicFile>>(json);
            for (int i = 0; i < deserializedPublicFile.Count; i++)
            {
                listBox.Items.Add(deserializedPublicFile.ElementAt(i));
            }
        }

        private async void button_Click(object sender, RoutedEventArgs e)
        {
            try
            {

          
            Room r = (Room)comboBox.SelectedItem;
            OpenFileDialog openFileDialog = new OpenFileDialog();
            if (openFileDialog.ShowDialog() == true)
            {
                filePath = openFileDialog.FileName;
                lbl_msg.Content = "new file was selected: " + filePath;
            }

            string[] tokens = openFileDialog.FileName.Split('\\');
            int cnt = tokens.Length;
            Console.WriteLine(tokens[cnt - 1]);


            byte[] binary = File.ReadAllBytes(filePath);

            Console.WriteLine(binary[2]);



            PrivateFile p = new PrivateFile();
            p.fileId = 0;
            p.fileName = tokens[cnt - 1];
            p.publishDate = new LocalDate(2018, 1, 23);
            p.publisherTeacher = new Teacher("Admin1234", "Admin1234");
            p.fileContent = binary;
            p.publisherStudent = null;
            p.folderRoom = r;
            Console.WriteLine(p.fileContent.ToString());
            var jsonString = new JavaScriptSerializer().Serialize(p);
            //var jsonString = Newtonsoft.Json.JsonConvert.SerializeObject(p);

            var response = await client.PostAsync(@"http://192.168.195.165:8080/WebServiceSchoopy/webresources/privatefiles", new StringContent(jsonString, Encoding.UTF8, "application/json"));
            var responseString = await response.Content.ReadAsStringAsync();
            Console.WriteLine(responseString);

            init();
            MessageBox.Show("File was added to the folder from the class: " + r.ToString());
            }catch(Exception ex)
            {
                MessageBox.Show("Please select a class from the Combobox");
            }

        }

        private async void button1_Click(object sender, RoutedEventArgs e)
        {
            OpenFileDialog openFileDialog = new OpenFileDialog();
            if (openFileDialog.ShowDialog() == true)
            {
                filePath = openFileDialog.FileName;
                lbl_msg.Content = "new file was selected: " + filePath;
            }

            string[] tokens = openFileDialog.FileName.Split('\\');
            int cnt = tokens.Length;
            Console.WriteLine(tokens[cnt - 1]);

            
            byte[] binary = File.ReadAllBytes(filePath);

            Console.WriteLine(binary[2]);
           


            PublicFile p = new PublicFile();
            p.fileId = 0;
            p.fileName = tokens[cnt - 1];
            p.publishDate = new LocalDate(2018, 1, 23);
            p.publisherTeacher = new Teacher("Admin1234","Admin1234");
            p.fileContent = binary;
            Console.WriteLine(p.fileContent.ToString());
            var jsonString = new JavaScriptSerializer().Serialize(p);
            //var jsonString = Newtonsoft.Json.JsonConvert.SerializeObject(p);

            var response = await client.PostAsync(@"http://192.168.195.165:8080/WebServiceSchoopy/webresources/publicfiles", new StringContent(jsonString, Encoding.UTF8, "application/json")) ;
            var responseString = await response.Content.ReadAsStringAsync();
            Console.WriteLine(responseString);

            init();

        }

        private void button2_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                PublicFile p = (PublicFile)listBox.SelectedItem;
                if (p == null)
                {
                    throw new Exception("Please select a file!");
                }
                string json = (c.Get(@"http://192.168.195.165:8080/WebServiceSchoopy/webresources/publicfiles/"+p.fileId));

                PublicFile deserializedPublicFile = new JavaScriptSerializer().Deserialize<PublicFile>(json);
                Blob b = new Blob(deserializedPublicFile.fileName, deserializedPublicFile.fileContent);
                

                SaveFileDialog sfDialog = new SaveFileDialog();
                string tempfile = System.IO.Path.GetTempPath() + Guid.NewGuid().ToString() + System.IO.Path.GetExtension(b.FileName);
                File.WriteAllBytes(tempfile, deserializedPublicFile.fileContent);
                Process.Start(tempfile);

            }catch(Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void initComboBox()
        {
            string json = (c.Get(@"http://192.168.195.165:8080/WebServiceSchoopy/webresources/rooms"));
            List<Room> deserializedRooms = JsonConvert.DeserializeObject<List<Room>>(json);

            foreach (Room r in deserializedRooms)
            {
                comboBox.Items.Add(r);
            }
        }
        
    }
}
