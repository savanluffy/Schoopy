using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Schoopy
{
    public abstract class Person
    {
        public string firstName { get; set; }
        public string lastName { get; set; }
        public string schoolemail { get; set; }
        public string username { get; set; }
        public string password { get; set; }

        public Person(string _username, string _password)
        {
            this.username = _username;
            this.password = _password;
        }

        public Person(string _username, string _password , string _firstname, string lastname, string schoolemail)
        {
            this.username = _username;
            this.password = _password;
            this.firstName = _firstname;
            this.lastName = lastName;
            this.schoolemail = schoolemail;
        }

        public Person()
        {

        }
    }
}
