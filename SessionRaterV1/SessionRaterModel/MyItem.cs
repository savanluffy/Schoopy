using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SessionRaterModel
{
    public class MyItem
    {
        public int Id { get; set; }
        public string Title { get; set; }
        public string Speaker { get; set; }

        public MyItem(int Id, string Title, string Speaker)
        {
            this.Id = Id;
            this.Speaker = Speaker;
            this.Title = Title;
        }
    }
}
