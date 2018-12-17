using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Threading.Tasks;

namespace Schoopy
{
    class Database
    {
        static HttpClient client = new HttpClient();

        static void ShowProduct(Subject subject)
        {
            Console.WriteLine($"subjectName: {subject.subjectName}");
        }


        public void start()
        {
            RunAsync().GetAwaiter().GetResult();
        }
        

        static async Task<Subject> GetProductAsync(string path)
        {
            Subject lesson = null;
            HttpResponseMessage response = await client.GetAsync(path);
            if (response.IsSuccessStatusCode)
            {
                lesson = await response.Content.ReadAsAsync<Subject>();
            }
            return lesson;
        }

        static async Task<Uri> CreateProductAsync(Subject product)
        {
            HttpResponseMessage response = await client.PostAsJsonAsync(
                "WebServiceSchoopy/webresources/subject", product);
            response.EnsureSuccessStatusCode();

            // return URI of the created resource.
            return response.Headers.Location;
        }





        static async Task RunAsync()
        {
            // Update port # in the following line.
            client.BaseAddress = new Uri("http://localhost:8080/");
            client.DefaultRequestHeaders.Accept.Clear();
            client.DefaultRequestHeaders.Accept.Add(
                new MediaTypeWithQualityHeaderValue("application/json"));

            try
            {

                Subject l = new Subject
                {
                    subjectID = 122,
                    subjectName ="homo",
                    subjectShortcut= "ddd"
                    
                
                };

                var url = await CreateProductAsync(l);
                Console.WriteLine($"Created at {url}");
                // Get the product
                l  = await GetProductAsync(url.PathAndQuery);
                ShowProduct(l);

                

               

                

            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }

            Console.ReadLine();
        }
    }

}

