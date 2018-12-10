using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Schoopy
{
    public class Stunde
    {


        
        public string Tag { get; set; }
        public string Stundett { get; set; }
        public string Lehrer { get; set; }
     

        public Stunde(string _tag , string _st , string _lehrer)
        {
            this.Tag = _tag;
            this.Stundett = _st;
            this.Lehrer = _lehrer;
        }

        public override string ToString()
        {

            return this.Stundett;
        }
    }
}
