using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Schoopy.Data
{
    public class Student : Person
    {
        public Room visitedClass { get;  set; }
        public Student(string _username, string _passwort , Room _visitedClass)
        {
            this.password = _passwort;
            this.username = _username;
            this.visitedClass = _visitedClass;
        }

        public Student()
        {
        }
    }
}
