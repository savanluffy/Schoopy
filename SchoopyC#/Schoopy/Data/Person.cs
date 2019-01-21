using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Schoopy
{
    public abstract class Person
    {
        public String firstName { get; set; }
        public String lastName { get; set; }
        public String schoolemail { get; set; }
        public String username { get; set; }
        public String password { get; set; }

        public Person(String _username, String _password)
        {
            this.username = _username;
            this.password = _password;
        }

        public Person()
        {

        }
    }
}
