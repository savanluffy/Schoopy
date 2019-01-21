using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Schoopy
{
    public class Teacher : Person
    {
       public Teacher()
        {


        }

        public Teacher( string _username , string _passwort)
        {
            this.password = _passwort;
            this.username = _username;
        }
    }
}
